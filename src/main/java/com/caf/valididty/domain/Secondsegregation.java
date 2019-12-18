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
 * A Secondsegregation.
 */
@Entity
@Table(name = "secondsegregation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "secondsegregation")
public class Secondsegregation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "boxname")
    private String boxname;

    @Column(name = "cafcode")
    private String cafcode;

    @Column(name = "firstleveluser")
    private String firstleveluser;

    @Column(name = "segregatedcount")
    private Integer segregatedcount;

    @Column(name = "wrongsegregatedcount")
    private Integer wrongsegregatedcount;

    @Column(name = "notsegregatedcount")
    private Integer notsegregatedcount;

    @Column(name = "jhi_user")
    private String user;

    @Column(name = "userdate")
    private Instant userdate;

    @Column(name = "colorcode")
    private String colorcode;

    @Column(name = "status")
    private String status;

    @Column(name = "lastcafentered")
    private String lastcafentered;

    @Column(name = "count")
    private Integer count;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoxname() {
        return boxname;
    }

    public Secondsegregation boxname(String boxname) {
        this.boxname = boxname;
        return this;
    }

    public void setBoxname(String boxname) {
        this.boxname = boxname;
    }

    public String getCafcode() {
        return cafcode;
    }

    public Secondsegregation cafcode(String cafcode) {
        this.cafcode = cafcode;
        return this;
    }

    public void setCafcode(String cafcode) {
        this.cafcode = cafcode;
    }

    public String getFirstleveluser() {
        return firstleveluser;
    }

    public Secondsegregation firstleveluser(String firstleveluser) {
        this.firstleveluser = firstleveluser;
        return this;
    }

    public void setFirstleveluser(String firstleveluser) {
        this.firstleveluser = firstleveluser;
    }

    public Integer getSegregatedcount() {
        return segregatedcount;
    }

    public Secondsegregation segregatedcount(Integer segregatedcount) {
        this.segregatedcount = segregatedcount;
        return this;
    }

    public void setSegregatedcount(Integer segregatedcount) {
        this.segregatedcount = segregatedcount;
    }

    public Integer getWrongsegregatedcount() {
        return wrongsegregatedcount;
    }

    public Secondsegregation wrongsegregatedcount(Integer wrongsegregatedcount) {
        this.wrongsegregatedcount = wrongsegregatedcount;
        return this;
    }

    public void setWrongsegregatedcount(Integer wrongsegregatedcount) {
        this.wrongsegregatedcount = wrongsegregatedcount;
    }

    public Integer getNotsegregatedcount() {
        return notsegregatedcount;
    }

    public Secondsegregation notsegregatedcount(Integer notsegregatedcount) {
        this.notsegregatedcount = notsegregatedcount;
        return this;
    }

    public void setNotsegregatedcount(Integer notsegregatedcount) {
        this.notsegregatedcount = notsegregatedcount;
    }

    public String getUser() {
        return user;
    }

    public Secondsegregation user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Instant getUserdate() {
        return userdate;
    }

    public Secondsegregation userdate(Instant userdate) {
        this.userdate = userdate;
        return this;
    }

    public void setUserdate(Instant userdate) {
        this.userdate = userdate;
    }

    public String getColorcode() {
        return colorcode;
    }

    public Secondsegregation colorcode(String colorcode) {
        this.colorcode = colorcode;
        return this;
    }

    public void setColorcode(String colorcode) {
        this.colorcode = colorcode;
    }

    public String getStatus() {
        return status;
    }

    public Secondsegregation status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastcafentered() {
        return lastcafentered;
    }

    public Secondsegregation lastcafentered(String lastcafentered) {
        this.lastcafentered = lastcafentered;
        return this;
    }

    public void setLastcafentered(String lastcafentered) {
        this.lastcafentered = lastcafentered;
    }

    public Integer getCount() {
        return count;
    }

    public Secondsegregation count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
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
        Secondsegregation secondsegregation = (Secondsegregation) o;
        if (secondsegregation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secondsegregation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Secondsegregation{" +
            "id=" + getId() +
            ", boxname='" + getBoxname() + "'" +
            ", cafcode='" + getCafcode() + "'" +
            ", firstleveluser='" + getFirstleveluser() + "'" +
            ", segregatedcount='" + getSegregatedcount() + "'" +
            ", wrongsegregatedcount='" + getWrongsegregatedcount() + "'" +
            ", notsegregatedcount='" + getNotsegregatedcount() + "'" +
            ", user='" + getUser() + "'" +
            ", userdate='" + getUserdate() + "'" +
            ", colorcode='" + getColorcode() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastcafentered='" + getLastcafentered() + "'" +
            ", count='" + getCount() + "'" +
            "}";
    }
}
