package com.vixindia.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("id")
    private int id;
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @JsonProperty("otp")
    private String otp;

    @JsonProperty("otp_try_count")
    private Integer otpTryCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getOtpTryCount() {
        return otpTryCount;
    }

    public void setOtpTryCount(Integer otpTryCount) {
        this.otpTryCount = otpTryCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", otp='" + otp + '\'' +
                ", otpTryCount=" + otpTryCount +
                '}';
    }
}