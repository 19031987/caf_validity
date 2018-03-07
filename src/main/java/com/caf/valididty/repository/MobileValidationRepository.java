package com.caf.valididty.repository;

import com.caf.valididty.domain.MobileValidation;
import com.caf.valididty.domain.Scancaf;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MobileValidation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobileValidationRepository extends JpaRepository<MobileValidation, Long> {

@Query(value="select * from getmobiledata where  mobilenumber=?",nativeQuery=true)
	List<MobileValidation> getMobileNumber(Long mobilenumber);
@Query(value ="select * from mobile_validation where jhi_user=?1 order by id desc LIMIT 1",nativeQuery=true)
MobileValidation findByuserOrderByDsc(String user);

@Query(value ="select category_1 from scancaf where colorcode='green' order by id desc LIMIT 1",nativeQuery=true)
String getCategory1();
@Query(value ="select category_2 from scancaf where colorcode='white' order by id desc LIMIT 1",nativeQuery=true)
String getCategory2();
@Query(value ="select category_3 from scancaf where colorcode='yellow' order by id desc LIMIT 1",nativeQuery=true)
String getCategory3();
@Query(value ="select category_4 from scancaf where colorcode='blue' order by id desc LIMIT 1",nativeQuery=true)
String getCategory4();
@Query(value ="select category_5 from scancaf where colorcode='red' order by id desc LIMIT 1",nativeQuery=true)
String getCategory5();
    @Query(value ="select * from mobile_validation where mobilevalidation=?1 ",nativeQuery=true)
    MobileValidation findByMobileNumber(Long mobilenumber);
}
