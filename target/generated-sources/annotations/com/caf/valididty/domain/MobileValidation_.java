package com.caf.valididty.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MobileValidation.class)
public abstract class MobileValidation_ {

	public static volatile SingularAttribute<MobileValidation, Long> mobilenumber;
	public static volatile SingularAttribute<MobileValidation, String> colorCode;
	public static volatile SingularAttribute<MobileValidation, Long> id;
	public static volatile SingularAttribute<MobileValidation, String> activationDate;
	public static volatile SingularAttribute<MobileValidation, String> user;
	public static volatile SingularAttribute<MobileValidation, String> customerName;
	public static volatile SingularAttribute<MobileValidation, LocalDate> userDate;

}

