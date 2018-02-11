package com.caf.valididty.repository;

import com.caf.valididty.domain.System;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the System entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemRepository extends JpaRepository<System, Long> {

	System findBysystemname(String system);

}
