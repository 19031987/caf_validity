package com.caf.valididty.domain;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Suspendedusers.class)
public abstract class Suspendedusers_ {

	public static volatile SingularAttribute<Suspendedusers, String> centralbarcode;
	public static volatile SingularAttribute<Suspendedusers, Long> mobilenumber;
	public static volatile SingularAttribute<Suspendedusers, Integer> count;
	public static volatile SingularAttribute<Suspendedusers, Long> id;
	public static volatile SingularAttribute<Suspendedusers, String> user;
	public static volatile SingularAttribute<Suspendedusers, ZonedDateTime> userdate;

}

