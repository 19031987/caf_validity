package com.caf.valididty.repository.search;

import com.caf.valididty.domain.Sourcebox;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sourcebox entity.
 */
public interface SourceboxSearchRepository extends ElasticsearchRepository<Sourcebox, Long> {
}
