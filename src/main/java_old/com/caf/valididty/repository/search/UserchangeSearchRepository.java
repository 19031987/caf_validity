package com.caf.valididty.repository.search;

import com.caf.valididty.domain.Userchange;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Userchange entity.
 */
public interface UserchangeSearchRepository extends ElasticsearchRepository<Userchange, Long> {
}
