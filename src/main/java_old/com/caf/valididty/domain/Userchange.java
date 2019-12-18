package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Userchange.
 */
@Entity
@Table(name = "userchange")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "userchange")
public class Userchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "changeuser")
    private String changeuser;

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

    public Userchange user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getChangeuser() {
        return changeuser;
    }

    public Userchange changeuser(String changeuser) {
        this.changeuser = changeuser;
        return this;
    }

    public void setChangeuser(String changeuser) {
        this.changeuser = changeuser;
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
        Userchange userchange = (Userchange) o;
        if (userchange.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userchange.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Userchange{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", changeuser='" + getChangeuser() + "'" +
            "}";
    }
}
