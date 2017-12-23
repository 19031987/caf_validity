package com.caf.valididty.repository;

import com.caf.valididty.domain.Suspendedusers;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Suspendedusers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuspendedusersRepository extends JpaRepository<Suspendedusers, Long> {

}
