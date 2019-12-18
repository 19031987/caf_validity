package com.caf.valididty.repository;

import com.caf.valididty.domain.Sourcebox;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sourcebox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceboxRepository extends JpaRepository<Sourcebox, Long> {

	Sourcebox findBySourceboxname(String id);

}
