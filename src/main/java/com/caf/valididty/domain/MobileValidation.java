package com.caf.valididty.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A MobileValidation.
 */
@Entity
@Table(name = "mobile_validation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "MobileValidation")
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
    private LocalDateTime userDate;

    @Column(name = "isselected")
    private Boolean isselected;

    @Column(name = "category_1")
    private String category1;

    @Column(name = "category_2")
    private String category2;

    @Column(name = "category_3")
    private String category3;

    @Column(name = "catergory_4")
    private String catergory4;

    @Column(name = "catergory_5")
    private String catergory5;

    @Column(name = "count_category_1")
    private Integer countCategory1;

    @Column(name = "count_category_2")
    private Integer countCategory2;

    @Column(name = "count_category_3")
    private Integer countCategory3;

    @Column(name = "count_category_4")
    private Integer countCategory4;

    @Column(name = "count_category_5")
    private Integer countCategory5;

    @Column(name = "sourcebox")
    private String sourcebox;

    @Column(name = "category_rv")
    private String categoryRv;

    @Column(name = "count_category_rv")
    private Integer countCategoryRv;

    @Column(name = "category_na")
    private String categoryNA;

    @Column(name = "count_category_na")
    private Integer countCategoryNA;

    @Column(name = "barcode")
    private Integer barcode;

    @Column(name = "user_count")
    private String userCount;

    @Column(name = "barcode_name")
    private String barcodeName;

    @Column(name = "lot")
    private String lot;

    @Column(name = "fathername")
    private String fathername;

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

    public LocalDateTime getUserDate() {
        return userDate;
    }

    public MobileValidation userDate(LocalDateTime userDate) {
        this.userDate = userDate;
        return this;
    }

    public void setUserDate(LocalDateTime userDate) {
        this.userDate = userDate;
    }

    public Boolean isIsselected() {
        return isselected;
    }

    public MobileValidation isselected(Boolean isselected) {
        this.isselected = isselected;
        return this;
    }

    public void setIsselected(Boolean isselected) {
        this.isselected = isselected;
    }

    public String getCategory1() {
        return category1;
    }

    public MobileValidation category1(String category1) {
        this.category1 = category1;
        return this;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public MobileValidation category2(String category2) {
        this.category2 = category2;
        return this;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public MobileValidation category3(String category3) {
        this.category3 = category3;
        return this;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getCatergory4() {
        return catergory4;
    }

    public MobileValidation catergory4(String catergory4) {
        this.catergory4 = catergory4;
        return this;
    }

    public void setCatergory4(String catergory4) {
        this.catergory4 = catergory4;
    }

    public String getCatergory5() {
        return catergory5;
    }

    public MobileValidation catergory5(String catergory5) {
        this.catergory5 = catergory5;
        return this;
    }

    public void setCatergory5(String catergory5) {
        this.catergory5 = catergory5;
    }

    public Integer getCountCategory1() {
        return countCategory1;
    }

    public MobileValidation countCategory1(Integer countCategory1) {
        this.countCategory1 = countCategory1;
        return this;
    }

    public void setCountCategory1(Integer countCategory1) {
        this.countCategory1 = countCategory1;
    }

    public Integer getCountCategory2() {
        return countCategory2;
    }

    public MobileValidation countCategory2(Integer countCategory2) {
        this.countCategory2 = countCategory2;
        return this;
    }

    public void setCountCategory2(Integer countCategory2) {
        this.countCategory2 = countCategory2;
    }

    public Integer getCountCategory3() {
        return countCategory3;
    }

    public MobileValidation countCategory3(Integer countCategory3) {
        this.countCategory3 = countCategory3;
        return this;
    }

    public void setCountCategory3(Integer countCategory3) {
        this.countCategory3 = countCategory3;
    }

    public Integer getCountCategory4() {
        return countCategory4;
    }

    public MobileValidation countCategory4(Integer countCategory4) {
        this.countCategory4 = countCategory4;
        return this;
    }

    public void setCountCategory4(Integer countCategory4) {
        this.countCategory4 = countCategory4;
    }

    public Integer getCountCategory5() {
        return countCategory5;
    }

    public MobileValidation countCategory5(Integer countCategory5) {
        this.countCategory5 = countCategory5;
        return this;
    }

    public void setCountCategory5(Integer countCategory5) {
        this.countCategory5 = countCategory5;
    }

    public String getSourcebox() {
        return sourcebox;
    }

    public MobileValidation sourcebox(String sourcebox) {
        this.sourcebox = sourcebox;
        return this;
    }

    public void setSourcebox(String sourcebox) {
        this.sourcebox = sourcebox;
    }

    public String getCategoryRv() {
        return categoryRv;
    }

    public MobileValidation categoryRv(String categoryRv) {
        this.categoryRv = categoryRv;
        return this;
    }

    public void setCategoryRv(String categoryRv) {
        this.categoryRv = categoryRv;
    }

    public Integer getCountCategoryRv() {
        return countCategoryRv;
    }

    public MobileValidation countCategoryRv(Integer countCategoryRv) {
        this.countCategoryRv = countCategoryRv;
        return this;
    }

    public void setCountCategoryRv(Integer countCategoryRv) {
        this.countCategoryRv = countCategoryRv;
    }

    public String getCategoryNA() {
        return categoryNA;
    }

    public MobileValidation categoryNA(String categoryNA) {
        this.categoryNA = categoryNA;
        return this;
    }

    public void setCategoryNA(String categoryNA) {
        this.categoryNA = categoryNA;
    }

    public Integer getCountCategoryNA() {
        return countCategoryNA;
    }

    public MobileValidation countCategoryNA(Integer countCategoryNA) {
        this.countCategoryNA = countCategoryNA;
        return this;
    }

    public void setCountCategoryNA(Integer countCategoryNA) {
        this.countCategoryNA = countCategoryNA;
    }

    public Integer getBarcode() {
        return barcode;
    }

    public MobileValidation barcode(Integer barcode) {
        this.barcode = barcode;
        return this;
    }

    public void setBarcode(Integer barcode) {
        this.barcode = barcode;
    }

    public String getUserCount() {
        return userCount;
    }

    public MobileValidation userCount(String userCount) {
        this.userCount = userCount;
        return this;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getBarcodeName() {
        return barcodeName;
    }

    public MobileValidation barcodeName(String barcodeName) {
        this.barcodeName = barcodeName;
        return this;
    }

    public void setBarcodeName(String barcodeName) {
        this.barcodeName = barcodeName;
    }

    public String getLot() {
        return lot;
    }

    public MobileValidation lot(String lot) {
        this.lot = lot;
        return this;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
    public String getFathername() {
        return fathername;
    }

    public MobileValidation fathername(String fathername) {
        this.fathername = fathername;
        return this;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
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
        MobileValidation MobileValidation = (MobileValidation) o;
        if (MobileValidation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), MobileValidation.getId());
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
            ", isselected='" + isIsselected() + "'" +
            ", category1='" + getCategory1() + "'" +
            ", category2='" + getCategory2() + "'" +
            ", category3='" + getCategory3() + "'" +
            ", catergory4='" + getCatergory4() + "'" +
            ", catergory5='" + getCatergory5() + "'" +
            ", countCategory1='" + getCountCategory1() + "'" +
            ", countCategory2='" + getCountCategory2() + "'" +
            ", countCategory3='" + getCountCategory3() + "'" +
            ", countCategory4='" + getCountCategory4() + "'" +
            ", countCategory5='" + getCountCategory5() + "'" +
            ", sourcebox='" + getSourcebox() + "'" +
            ", categoryRv='" + getCategoryRv() + "'" +
            ", countCategoryRv='" + getCountCategoryRv() + "'" +
            ", categoryNA='" + getCategoryNA() + "'" +
            ", countCategoryNA='" + getCountCategoryNA() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", userCount='" + getUserCount() + "'" +
            ", barcodeName='" + getBarcodeName() + "'" +
            ", lot='" + getLot() + "'" +
            ", fathername='" + getFathername() + "'" +
            "}";
    }
}
