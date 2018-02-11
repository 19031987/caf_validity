package com.caf.valididty.repository.search;

import com.caf.valididty.domain.MobileValidation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MobileValidation entity.
 */
public interface MobileValidationSearchRepository extends ElasticsearchRepository<MobileValidation, Long> {
}
