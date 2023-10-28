package com.vixindia.signup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {

  @JsonProperty("name")
  private String name;
  @JsonProperty("pan_number")
  private String panNumber;
  @JsonProperty("mobile_number")
  private Long mobileNumber;
  @JsonProperty("email")
  private String email;
}
