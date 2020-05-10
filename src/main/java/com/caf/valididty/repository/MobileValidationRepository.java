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



@Query(value ="select category_1 from mobile_validation  order by category_1 desc LIMIT 1",nativeQuery=true)
String getCategory1();
@Query(value ="select category_2 from mobile_validation  order by category_2 desc LIMIT 1",nativeQuery=true)
String getCategory2();
@Query(value ="select category_3 from mobile_validation  order by category_3 desc LIMIT 1",nativeQuery=true)
String getCategory3();
@Query(value ="select catergory_4 from mobile_validation  order by catergory_4 desc LIMIT 1",nativeQuery=true)
String getCategory4();
@Query(value ="select catergory_5 from mobile_validation  order by catergory_5 desc limit 1",nativeQuery=true)
String getCategory5();
@Query(value ="select category_rv from mobile_validation  order by category_rv desc LIMIT 1",nativeQuery=true)
String getCategoryRv();
@Query(value ="select category_na from mobile_validation  order by category_na desc LIMIT 1",nativeQuery=true)
String getCategoryNA();
    @Query(value ="select * from mobile_validation where mobilevalidation=?1 ",nativeQuery=true)
    MobileValidation findByMobileNumber(Long mobilenumber);

    @Query(value ="select m.* from mobile_validation as m where m.color_code='green' and m.jhi_user=? order by m.id desc LIMIT 1",nativeQuery=true)
    MobileValidation getCategory1ByUser(String currentUserLogin);

    @Query(value ="select COUNT(*) from mobile_validation where jhi_user=? and DATE(user_date)= curdate()",nativeQuery=true)
    int getCountOfUser(String currentUserLogin);
}
