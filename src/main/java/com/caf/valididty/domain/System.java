package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A System.
 */
@Entity
@Table(name = "system")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "system")
public class System implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "systemname")
    private String systemname;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "userdate")
    private LocalDate userdate;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemname() {
        return systemname;
    }

    public System systemname(String systemname) {
        this.systemname = systemname;
        return this;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public String getUser() {
        return user;
    }

    public System user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getUserdate() {
        return userdate;
    }

    public System userdate(LocalDate userdate) {
        this.userdate = userdate;
        return this;
    }

    public void setUserdate(LocalDate userdate) {
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
        System system = (System) o;
        if (system.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), system.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "System{" +
            "id=" + getId() +
            ", systemname='" + getSystemname() + "'" +
            ", user='" + getUser() + "'" +
            ", userdate='" + getUserdate() + "'" +
            "}";
    }
}
