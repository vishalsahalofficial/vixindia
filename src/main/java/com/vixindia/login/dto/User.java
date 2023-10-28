package com.vixindia.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

  @JsonProperty("id")
  private int id;
  @JsonProperty("mobile_number")
  private String mobileNumber;
  @JsonProperty("otp")
  private String otp;
  @JsonProperty("otp_try_count")
  private Integer otpTryCount;
}