package com.caf.valididty.repository;

import com.caf.valididty.domain.Scancaf;
import com.caf.valididty.domain.Secondsegregation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Secondsegregation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecondsegregationRepository extends JpaRepository<Secondsegregation, Long> {

	@Query(value ="SELECT s FROM Scancaf s where s.centralbarcode= :cafCode and s.category1 = :boxname order by s.id desc")
	Scancaf findByBoxName(@Param("boxname") String boxname, @Param("cafCode") String cafcode);

	Secondsegregation findBycafcode(String cafcode);

	Secondsegregation findTop1ByboxnameOrderByIdDesc(String boxname);

	Secondsegregation findTop1ByuserOrderByIdDesc(String currentUserLogin);

}
