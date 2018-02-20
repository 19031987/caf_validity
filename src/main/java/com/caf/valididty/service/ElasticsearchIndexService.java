package com.caf.valididty.service;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.caf.valididty.domain.*;
import com.caf.valididty.domain.System;
import com.caf.valididty.repository.*;
import com.caf.valididty.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToMany;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    private final AdminstatsRepository adminstatsRepository;

    private final AdminstatsSearchRepository adminstatsSearchRepository;

    private final BoxassignRepository boxassignRepository;

    private final BoxassignSearchRepository boxassignSearchRepository;

    private final MobileValidationRepository mobileValidationRepository;

    private final MobileValidationSearchRepository mobileValidationSearchRepository;

    private final ScancafRepository scancafRepository;

    private final ScancafSearchRepository scancafSearchRepository;

    private final SourceboxRepository sourceboxRepository;

    private final SourceboxSearchRepository sourceboxSearchRepository;

    private final SuspendedusersRepository suspendedusersRepository;

    private final SuspendedusersSearchRepository suspendedusersSearchRepository;

    private final SystemRepository systemRepository;

    private final SystemSearchRepository systemSearchRepository;

    private final UserRepository userRepository;

    private final UserSearchRepository userSearchRepository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    private static final Lock reindexLock = new ReentrantLock();

    public ElasticsearchIndexService(
        UserRepository userRepository,
        UserSearchRepository userSearchRepository,
        AdminstatsRepository adminstatsRepository,
        AdminstatsSearchRepository adminstatsSearchRepository,
        BoxassignRepository boxassignRepository,
        BoxassignSearchRepository boxassignSearchRepository,
        MobileValidationRepository mobileValidationRepository,
        MobileValidationSearchRepository mobileValidationSearchRepository,
        ScancafRepository scancafRepository,
        ScancafSearchRepository scancafSearchRepository,
        SourceboxRepository sourceboxRepository,
        SourceboxSearchRepository sourceboxSearchRepository,
        SuspendedusersRepository suspendedusersRepository,
        SuspendedusersSearchRepository suspendedusersSearchRepository,
        SystemRepository systemRepository,
        SystemSearchRepository systemSearchRepository,
        ElasticsearchTemplate elasticsearchTemplate) {
        this.userRepository = userRepository;
        this.userSearchRepository = userSearchRepository;
        this.adminstatsRepository = adminstatsRepository;
        this.adminstatsSearchRepository = adminstatsSearchRepository;
        this.boxassignRepository = boxassignRepository;
        this.boxassignSearchRepository = boxassignSearchRepository;
        this.mobileValidationRepository = mobileValidationRepository;
        this.mobileValidationSearchRepository = mobileValidationSearchRepository;
        this.scancafRepository = scancafRepository;
        this.scancafSearchRepository = scancafSearchRepository;
        this.sourceboxRepository = sourceboxRepository;
        this.sourceboxSearchRepository = sourceboxSearchRepository;
        this.suspendedusersRepository = suspendedusersRepository;
        this.suspendedusersSearchRepository = suspendedusersSearchRepository;
        this.systemRepository = systemRepository;
        this.systemSearchRepository = systemSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Async
    @Timed
    public void reindexAll() {
        if(reindexLock.tryLock()) {
            try {
                reindexForClass(Adminstats.class, adminstatsRepository, adminstatsSearchRepository);
                reindexForClass(Boxassign.class, boxassignRepository, boxassignSearchRepository);
                reindexForClass(MobileValidation.class, mobileValidationRepository, mobileValidationSearchRepository);
                reindexForClass(Scancaf.class, scancafRepository, scancafSearchRepository);
                reindexForClass(Sourcebox.class, sourceboxRepository, sourceboxSearchRepository);
                reindexForClass(Suspendedusers.class, suspendedusersRepository, suspendedusersSearchRepository);
                reindexForClass(System.class, systemRepository, systemSearchRepository);
                reindexForClass(User.class, userRepository, userSearchRepository);

                log.info("Elasticsearch: Successfully performed reindexing");
            } finally {
                reindexLock.unlock();
            }
        } else {
            log.info("Elasticsearch: concurrent reindexing attempt");
        }
    }

    @SuppressWarnings("unchecked")
    private <T, ID extends Serializable> void reindexForClass(Class<T> entityClass, JpaRepository<T, ID> jpaRepository,
                                                              ElasticsearchRepository<T, ID> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            // if a JHipster entity field is the owner side of a many-to-many relationship, it should be loaded manually
            List<Method> relationshipGetters = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getType().equals(Set.class))
                .filter(field -> field.getAnnotation(ManyToMany.class) != null)
                .filter(field -> field.getAnnotation(ManyToMany.class).mappedBy().isEmpty())
                .filter(field -> field.getAnnotation(JsonIgnore.class) == null)
                .map(field -> {
                    try {
                        return new PropertyDescriptor(field.getName(), entityClass).getReadMethod();
                    } catch (IntrospectionException e) {
                        log.error("Error retrieving getter for class {}, field {}. Field will NOT be indexed",
                            entityClass.getSimpleName(), field.getName(), e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

            int size = 100;
            for (int i = 0; i <= jpaRepository.count() / size; i++) {
                Pageable page = new PageRequest(i, size);
                log.info("Indexing page {} of {}, size {}", i, jpaRepository.count() / size, size);
                Page<T> results = jpaRepository.findAll(page);
                results.map(result -> {
                    // if there are any relationships to load, do it now
                    relationshipGetters.forEach(method -> {
                        try {
                            // eagerly load the relationship set
                            ((Set) method.invoke(result)).size();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
                    return result;
                });
                elasticsearchRepository.save(results.getContent());
            }
        }
        log.info("Elasticsearch: Indexed all rows for {}", entityClass.getSimpleName());
    }
}
