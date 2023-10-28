package com.vixindia.signup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * MobileValidationRequest
 */
@Data
@Builder
public class MobileValidationRequest {

  @JsonProperty("otp")
  @ApiModelProperty(required = true, value = "enter otp")
  @NotNull
  @Size(min = 4, max = 10)
  private String otp;

  @ApiModelProperty(example = "9858653500", required = true, value = "enter mobile  number")
  @NotNull
  @JsonProperty("mobile_number")
  private Long mobileNumber;
}

