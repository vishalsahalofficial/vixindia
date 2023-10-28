package com.vixindia.signup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * MobileVerificationRequest
 */
public class MobileVerificationRequest {

    @JsonProperty("mobile_number")
    private Long mobileNumber;

    public MobileVerificationRequest mobileNumber(Long mobileNumber) {
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
        MobileVerificationRequest mobileVerificationRequest = (MobileVerificationRequest) o;
        return Objects.equals(this.mobileNumber, mobileVerificationRequest.mobileNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobileNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MobileVerificationRequest {\n");

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

