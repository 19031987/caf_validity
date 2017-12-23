package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Adminstats.
 */
@Entity
@Table(name = "adminstats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "adminstats")
public class Adminstats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "usercount")
    private Integer usercount;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public Adminstats user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getUsercount() {
        return usercount;
    }

    public Adminstats usercount(Integer usercount) {
        this.usercount = usercount;
        return this;
    }

    public void setUsercount(Integer usercount) {
        this.usercount = usercount;
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
        Adminstats adminstats = (Adminstats) o;
        if (adminstats.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adminstats.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adminstats{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", usercount='" + getUsercount() + "'" +
            "}";
    }
}
