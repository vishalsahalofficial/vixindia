package com.vixindia.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

  @JsonProperty("message")
  private String message;
  @JsonProperty("result")
  private Object result;
  @JsonProperty("status")
  private Integer status;

}
