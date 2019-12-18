package com.caf.valididty.repository;

import com.caf.valididty.domain.Boxassign;
import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.domain.System;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Boxassign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxassignRepository extends JpaRepository<Boxassign, Long> {

	@Query(value ="select * from boxassign order by id desc limit 1",nativeQuery=true)
	Boxassign getCatAll();

	Boxassign findByuser(String currentUserLogin);

}
