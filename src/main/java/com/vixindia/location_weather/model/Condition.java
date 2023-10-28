package com.vixindia.location_weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Condition {
  @JsonProperty("text")
  private String text;

  @JsonProperty("icon")
  private String icon;

  @JsonProperty("code")
  private int code;
}

