package com.caf.valididty.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the MobileValidation entity.
 */
public class MobileValidationDTO implements Serializable {

    private Long id;

    private Long mobilenumber;

    private String activationDate;

    private String customerName;

    private String colorCode;

    private String user;

    private LocalDate userDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getUserDate() {
        return userDate;
    }

    public void setUserDate(LocalDate userDate) {
        this.userDate = userDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MobileValidationDTO mobileValidationDTO = (MobileValidationDTO) o;
        if(mobileValidationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mobileValidationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MobileValidationDTO{" +
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
