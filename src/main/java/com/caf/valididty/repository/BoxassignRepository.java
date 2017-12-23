package com.caf.valididty.repository;

import com.caf.valididty.domain.Boxassign;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Boxassign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxassignRepository extends JpaRepository<Boxassign, Long> {

}
