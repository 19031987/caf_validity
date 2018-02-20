package com.caf.valididty.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(System.class)
public abstract class System_ {

	public static volatile SingularAttribute<System, String> systemname;
	public static volatile SingularAttribute<System, Long> id;
	public static volatile SingularAttribute<System, String> user;
	public static volatile SingularAttribute<System, LocalDate> userdate;

}

