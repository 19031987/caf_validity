package com.caf.valididty.repository;

import com.caf.valididty.domain.Userchange;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Userchange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserchangeRepository extends JpaRepository<Userchange, Long> {

}
