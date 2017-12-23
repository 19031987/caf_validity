package com.caf.valididty.repository.search;

import com.caf.valididty.domain.Adminstats;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Adminstats entity.
 */
public interface AdminstatsSearchRepository extends ElasticsearchRepository<Adminstats, Long> {
}
