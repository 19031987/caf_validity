package com.caf.valididty.repository.search;

import com.caf.valididty.domain.Boxassign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Boxassign entity.
 */
public interface BoxassignSearchRepository extends ElasticsearchRepository<Boxassign, Long> {
}
