package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Sourcebox.
 */
@Entity
@Table(name = "sourcebox")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "sourcebox")
public class Sourcebox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sourceboxname")
    private String sourceboxname;

    @Column(name = "createduser")
    private String createduser;

    @Column(name = "createddate")
    private Instant createddate;

    @Column(name = "vehical_number")
    private String vehicalNumber;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceboxname() {
        return sourceboxname;
    }

    public Sourcebox sourceboxname(String sourceboxname) {
        this.sourceboxname = sourceboxname;
        return this;
    }

    public void setSourceboxname(String sourceboxname) {
        this.sourceboxname = sourceboxname;
    }

    public String getCreateduser() {
        return createduser;
    }

    public Sourcebox createduser(String createduser) {
        this.createduser = createduser;
        return this;
    }

    public void setCreateduser(String createduser) {
        this.createduser = createduser;
    }

    public Instant getCreateddate() {
        return createddate;
    }

    public Sourcebox createddate(Instant createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(Instant createddate) {
        this.createddate = Instant.now();
    }

    public String getVehicalNumber() {
        return vehicalNumber;
    }

    public Sourcebox vehicalNumber(String vehicalNumber) {
        this.vehicalNumber = vehicalNumber;
        return this;
    }

    public void setVehicalNumber(String vehicalNumber) {
        this.vehicalNumber = vehicalNumber;
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
        Sourcebox sourcebox = (Sourcebox) o;
        if (sourcebox.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourcebox.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sourcebox{" +
            "id=" + getId() +
            ", sourceboxname='" + getSourceboxname() + "'" +
            ", createduser='" + getCreateduser() + "'" +
            ", createddate='" + getCreateddate() + "'" +
            ", vehicalNumber='" + getVehicalNumber() + "'" +
            "}";
    }
}
