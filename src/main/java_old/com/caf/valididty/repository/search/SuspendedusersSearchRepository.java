package com.caf.valididty.repository.search;

import com.caf.valididty.domain.Suspendedusers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Suspendedusers entity.
 */
public interface SuspendedusersSearchRepository extends ElasticsearchRepository<Suspendedusers, Long> {
}
