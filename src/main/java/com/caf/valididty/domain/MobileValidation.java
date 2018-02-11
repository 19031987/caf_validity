package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MobileValidation.
 */
@Entity
@Table(name = "mobile_validation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mobilevalidation")
public class MobileValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobilenumber")
    private Long mobilenumber;

    @Column(name = "activation_date")
    private String activationDate;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "user_date")
    private LocalDate userDate;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMobilenumber() {
        return mobilenumber;
    }

    public MobileValidation mobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
        return this;
    }

    public void setMobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public MobileValidation activationDate(String activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public MobileValidation customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public MobileValidation colorCode(String colorCode) {
        this.colorCode = colorCode;
        return this;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getUser() {
        return user;
    }

    public MobileValidation user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getUserDate() {
        return userDate;
    }

    public MobileValidation userDate(LocalDate userDate) {
        this.userDate = userDate;
        return this;
    }

    public void setUserDate(LocalDate userDate) {
        this.userDate = userDate;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MobileValidation mobileValidation = (MobileValidation) o;
        if (mobileValidation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mobileValidation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MobileValidation{" +
            "id=" + getId() +
            ", mobilenumber='" + getMobilenumber() + "'" +
            ", activationDate='" + getActivationDate() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", colorCode='" + getColorCode() + "'" +
            ", user='" + getUser() + "'" +
            ", userDate='" + getUserDate() + "'" +
            "}";
    }
}
