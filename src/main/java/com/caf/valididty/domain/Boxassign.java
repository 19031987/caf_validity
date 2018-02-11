package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Boxassign.
 */
@Entity
@Table(name = "boxassign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "boxassign")
public class Boxassign implements Serializable {

    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "boxassign")
    private String boxassign;

    @Column(name = "churntype")
    private String churntype;

    @Column(name = "jhi_system")
    private String system;

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

    public Boxassign user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBoxassign() {
        return boxassign;
    }

    public Boxassign boxassign(String boxassign) {
        this.boxassign = boxassign;
        return this;
    }

    public void setBoxassign(String boxassign) {
        this.boxassign = boxassign;
    }

    public String getChurntype() {
        return churntype;
    }

    public Boxassign churntype(String churntype) {
        this.churntype = churntype;
        return this;
    }

    public void setChurntype(String churntype) {
        this.churntype = churntype;
    }

    public String getSystem() {
        return system;
    }

    public Boxassign system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
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
        Boxassign boxassign = (Boxassign) o;
        if (boxassign.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boxassign.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Boxassign{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", boxassign='" + getBoxassign() + "'" +
            ", churntype='" + getChurntype() + "'" +
            ", system='" + getSystem() + "'" +
            "}";
    }
}
