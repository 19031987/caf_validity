package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Suspendedusers.
 */
@Entity
@Table(name = "suspendedusers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "suspendedusers")
public class Suspendedusers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobilenumber")
    private Long mobilenumber;

    @Column(name = "centralbarcode")
    private String centralbarcode;

    @Column(name = "count")
    private Integer count;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "userdate")
    private ZonedDateTime userdate;

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

    public Suspendedusers mobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
        return this;
    }

    public void setMobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getCentralbarcode() {
        return centralbarcode;
    }

    public Suspendedusers centralbarcode(String centralbarcode) {
        this.centralbarcode = centralbarcode;
        return this;
    }

    public void setCentralbarcode(String centralbarcode) {
        this.centralbarcode = centralbarcode;
    }

    public Integer getCount() {
        return count;
    }

    public Suspendedusers count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUser() {
        return user;
    }

    public Suspendedusers user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ZonedDateTime getUserdate() {
        return userdate;
    }

    public Suspendedusers userdate(ZonedDateTime userdate) {
        this.userdate = userdate;
        return this;
    }

    public void setUserdate(ZonedDateTime userdate) {
        this.userdate = userdate;
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
        Suspendedusers suspendedusers = (Suspendedusers) o;
        if (suspendedusers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), suspendedusers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Suspendedusers{" +
            "id=" + getId() +
            ", mobilenumber='" + getMobilenumber() + "'" +
            ", centralbarcode='" + getCentralbarcode() + "'" +
            ", count='" + getCount() + "'" +
            ", user='" + getUser() + "'" +
            ", userdate='" + getUserdate() + "'" +
            "}";
    }
}
