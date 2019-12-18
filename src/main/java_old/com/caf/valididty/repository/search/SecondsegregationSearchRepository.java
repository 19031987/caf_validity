package com.caf.valididty.repository.search;

import com.caf.valididty.domain.Secondsegregation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Secondsegregation entity.
 */
public interface SecondsegregationSearchRepository extends ElasticsearchRepository<Secondsegregation, Long> {
}
