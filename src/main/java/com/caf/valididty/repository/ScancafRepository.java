package com.caf.valididty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.caf.valididty.domain.Scancaf;


/**
 * Spring Data JPA repository for the Scancaf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScancafRepository extends JpaRepository<Scancaf, Long> {

	@Query(value ="SELECT * FROM cafvalidity_v2.getdata where CentralBarcode =?1 ",nativeQuery=true)
	Object getCaf(String id);

	@Query(value ="SELECT * FROM cafvalidity_v2.scancaf where sourcebox =?1 and jhi_user =?2 order by userdate desc LIMIT 1",nativeQuery=true)
	Scancaf findByCategory(String sourcebox, String currentUserLogin);

	/*@Query(value ="SELECT sub.*" +
			"FROM (SELECT CASE WHEN count_category_1 = 300 THEN category_1 END category_1," +
			"             CASE WHEN count_category_2 = 300 THEN category_2 END category_2," +
			"             CASE WHEN count_category_3 = 300 THEN category_3 END category_3," +
			"             CASE WHEN count_category_4 = 300 THEN category_4 END category_4," +
			"             CASE WHEN count_category_5 = 300 THEN category_5 END category_5" +
			"      FROM scancaf" +
			"      WHERE scancaf.jhi_user = ?1 " +
			"      ORDER BY scancaf.userdate) as sub" +
			"WHERE sub.category_1 IS NOT NULL" +
			"      OR sub.category_2 IS NOT NULL" +
			"      OR sub.category_3 IS NOT NULL" +
			"      OR sub.category_4 IS NOT NULL" +
			"      OR sub.category_5 IS NOT NULL",nativeQuery=true)
	List<Object> getComletedBox(String currentUserLogin);

	@Query(value ="select * from scancaf order by ?1 desc limit 1",nativeQuery=true)
	Scancaf getCatLatest(String id);*/

	/*@Query(value ="select * from scancaf order by category_1,category_2,category_3,category_4,category_5,category_rv,category_na desc limit 1",nativeQuery=true)
	Scancaf getCatALl();*/

	@Query(value ="select * from scancaf where centralBarcode=?1 ",nativeQuery=true)
	Scancaf findByBarcode( String cafbarcode);

	@Query(value ="select * from scancaf where category_1=?1 and boxstatus='FIRST_LEVEL' and colorcode=?2",nativeQuery=true)
	List<Scancaf> findByOutBoxCompletionCat1(String category1, String colorcode);
	@Query(value ="select * from scancaf where category_2=?1 and boxstatus='FIRST_LEVEL' and colorcode=?2",nativeQuery=true)
	List<Scancaf> findByOutBoxCompletionCat2(String category1, String colorcode);
	@Query(value ="select * from scancaf where category_3=?1 and boxstatus='FIRST_LEVEL' and colorcode=?2",nativeQuery=true)
	List<Scancaf> findByOutBoxCompletionCat3(String category1, String colorcode);
	@Query(value ="select * from scancaf where category_4=?1 and boxstatus='FIRST_LEVEL' and colorcode=?2",nativeQuery=true)
	List<Scancaf> findByOutBoxCompletionCat4(String category1, String colorcode);
	@Query(value ="select * from scancaf where category_5=?1 and boxstatus='FIRST_LEVEL' and colorcode=?2",nativeQuery=true)
	List<Scancaf> findByOutBoxCompletionCat5(String category1, String colorcode);

	@Query(value ="select category_1 from scancaf order by category_1 desc limit 1",nativeQuery=true)
	String getCategory1();
	@Query(value ="select category_2 from scancaf order by category_2 desc limit 1",nativeQuery=true)
	String getCategory2();
	@Query(value ="select category_3 from scancaf order by category_3 desc limit 1",nativeQuery=true)
	String getCategory3();
	@Query(value ="select category_4 from scancaf order by category_4 desc limit 1",nativeQuery=true)
	String getCategory4();
	@Query(value ="select category_5 from scancaf order by category_5 desc limit 1",nativeQuery=true)
	String getCategory5();

	@Query(value ="select * from scancaf where jhi_user=?1 order by scancaf.id desc LIMIT 1",nativeQuery=true)
	Scancaf findByuserOrderByDsc(String user);
	
	@Query(value ="select category_rv from scancaf order by category_rv desc limit 1",nativeQuery=true)
	String getCategoryRv();
	
	@Query(value ="select category_na from scancaf order by category_na desc limit 1",nativeQuery=true)
	String getCategoryNA();

}
