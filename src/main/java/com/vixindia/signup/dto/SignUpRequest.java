package com.vixindia.signup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SignUpRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("pan_number")
    private String panNumber;

    @JsonProperty("mobile_number")
    private Long mobileNumber;
    @JsonProperty("email")
    private String email;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPanNumber() {
        return panNumber;
    }
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }
    public Long getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "SignUpRequest{" +
                "name='" + name + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", email='" + email + '\'' +
                '}';
    }
}
