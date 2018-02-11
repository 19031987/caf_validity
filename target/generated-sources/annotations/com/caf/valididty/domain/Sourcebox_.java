package com.caf.valididty.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sourcebox.class)
public abstract class Sourcebox_ {

	public static volatile SingularAttribute<Sourcebox, LocalDate> createddate;
	public static volatile SingularAttribute<Sourcebox, String> createduser;
	public static volatile SingularAttribute<Sourcebox, Long> id;
	public static volatile SingularAttribute<Sourcebox, String> sourceboxname;

}

