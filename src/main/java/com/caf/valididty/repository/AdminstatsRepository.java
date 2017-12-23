package com.caf.valididty.repository;

import com.caf.valididty.domain.Adminstats;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Adminstats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminstatsRepository extends JpaRepository<Adminstats, Long> {

}
