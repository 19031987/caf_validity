package com.caf.valididty.repository;

import com.caf.valididty.domain.MobileValidation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MobileValidation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileValidationRepository extends JpaRepository<MobileValidation, Long> {

}
