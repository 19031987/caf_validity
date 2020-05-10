package com.caf.valididty.repository;

import com.caf.valididty.domain.MobileValidation;
import com.caf.valididty.domain.Secondsegregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Secondsegregation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecondsegregationRepository extends JpaRepository<Secondsegregation, Long> {

    @Query(value = "SELECT s FROM MobileValidation s where s.barcodeName = :cafCode and s.category1 = :boxname ")
    MobileValidation findByBoxName(@Param("boxname") String boxname, @Param("cafCode") String cafcode);

    Secondsegregation findBycafcode(String cafcode);

    Secondsegregation findTop1ByboxnameOrderByIdDesc(String boxname);

    Secondsegregation findTop1ByuserOrderByIdDesc(String currentUserLogin);

}
