package com.caf.valididty.repository.search;

import com.caf.valididty.domain.Scancaf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Scancaf entity.
 */
public interface ScancafSearchRepository extends ElasticsearchRepository<Scancaf, Long> {
}
