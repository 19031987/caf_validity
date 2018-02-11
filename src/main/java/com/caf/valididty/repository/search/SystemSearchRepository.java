package com.caf.valididty.repository.search;

import com.caf.valididty.domain.System;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the System entity.
 */
public interface SystemSearchRepository extends ElasticsearchRepository<System, Long> {
}
