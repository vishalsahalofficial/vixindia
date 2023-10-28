package com.vixindia.signup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * MobileValidationRequest
 */

public class MobileValidationRequest {
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("mobile_number")
    private Long mobileNumber;

    public MobileValidationRequest otp(String otp) {
        this.otp = otp;
        return this;
    }

    /**
     * enter otp
     *
     * @return otp
     */
    @ApiModelProperty(required = true, value = "enter otp")
    @NotNull

    @Size(min = 4, max = 10)
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


    public MobileValidationRequest mobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    /**
     * enter mobile  number
     *
     * @return mobileNumber
     */
    @ApiModelProperty(example = "9858653500", required = true, value = "enter mobile  number")
    @NotNull


    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MobileValidationRequest mobileValidationRequest = (MobileValidationRequest) o;
        return Objects.equals(this.otp, mobileValidationRequest.otp) &&
                Objects.equals(this.mobileNumber, mobileValidationRequest.mobileNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(otp, mobileNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MobileValidationRequest {\n");

        sb.append("    otp: ").append(toIndentedString(otp)).append("\n");
        sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

