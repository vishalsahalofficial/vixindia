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
public class CurrentLocation {

  @JsonProperty("ip")
  private String ip;

  @JsonProperty("city")
  private String city;

  @JsonProperty("region")
  private String region;

  @JsonProperty("country")
  private String country;

  @JsonProperty("loc")
  private String location;

  @JsonProperty("org")
  private String provider;

  @JsonProperty("postal")
  private String pincode;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("readme")
  private String readme;

}
