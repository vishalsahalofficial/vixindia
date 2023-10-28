package com.vixindia.signup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * MobileVerificationRequest
 */
@Data
@Builder
public class MobileVerificationRequest {

  @JsonProperty("mobile_number")
  @ApiModelProperty(example = "9858653500", required = true, value = "enter mobile  number")
  @NotNull
  private Long mobileNumber;
}