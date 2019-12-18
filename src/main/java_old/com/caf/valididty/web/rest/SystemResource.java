package com.caf.valididty.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caf.valididty.domain.System;
import com.caf.valididty.repository.SystemRepository;
import com.caf.valididty.repository.search.SystemSearchRepository;
import com.caf.valididty.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing System.
 */
@RestController
@RequestMapping("/api")
public class SystemResource {

    private final Logger log = LoggerFactory.getLogger(SystemResource.class);

    private static final String ENTITY_NAME = "system";

    private final SystemRepository systemRepository;

    private final SystemSearchRepository systemSearchRepository;
    public SystemResource(SystemRepository systemRepository, SystemSearchRepository systemSearchRepository) {
        this.systemRepository = systemRepository;
        this.systemSearchRepository = systemSearchRepository;
    }

    /**
     * POST  /systems : Create a new system.
     *
     * @param system the system to create
     * @return the ResponseEntity with status 201 (Created) and with body the new system, or with status 400 (Bad Request) if the system has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/systems")
    @Timed
    public ResponseEntity<System> createSystem(@RequestBody System system) throws URISyntaxException {
        log.debug("REST request to save System : {}", system);
        if (system.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new system cannot already have an ID")).body(null);
        }
        System result = systemRepository.save(system);
        systemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /systems : Updates an existing system.
     *
     * @param system the system to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated system,
     * or with status 400 (Bad Request) if the system is not valid,
     * or with status 500 (Internal Server Error) if the system couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/systems")
    @Timed
    public ResponseEntity<System> updateSystem(@RequestBody System system) throws URISyntaxException {
        log.debug("REST request to update System : {}", system);
        if (system.getId() == null) {
            return createSystem(system);
        }
        System result = systemRepository.save(system);
        systemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, system.getId().toString()))
            .body(result);
    }

    /**
     * GET  /systems : get all the systems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of systems in body
     */
    @GetMapping("/systems")
    @Timed
    public List<System> getAllSystems() {
        log.debug("REST request to get all Systems");
        return systemRepository.findAll();
        }

    /**
     * GET  /systems/:id : get the "id" system.
     *
     * @param id the id of the system to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the system, or with status 404 (Not Found)
     */
    @GetMapping("/systems/{id}")
    @Timed
    public ResponseEntity<System> getSystem(@PathVariable Long id) {
        log.debug("REST request to get System : {}", id);
        System system = systemRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(system));
    }

    /**
     * DELETE  /systems/:id : delete the "id" system.
     *
     * @param id the id of the system to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/systems/{id}")
    @Timed
    public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
        log.debug("REST request to delete System : {}", id);
        systemRepository.delete(id);
        systemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * SEARCH  /_search/systems?query=:query : search for the system corresponding
     * to the query.
     *
     * @param query the query of the system search
     * @return the result of the search
     */
    @GetMapping("/_search/systems")
    @Timed
    public List<System> searchSystems(@RequestParam String query) {
        log.debug("REST request to search Systems for query {}", query);
        return StreamSupport
            .stream(systemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    
  
}
