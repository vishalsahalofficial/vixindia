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
public class Location {
  @JsonProperty("name")
  private String name;

  @JsonProperty("region")
  private String region;

  @JsonProperty("country")
  private String country;

  @JsonProperty("lat")
  private double lat;

  @JsonProperty("lon")
  private double lon;

  @JsonProperty("tz_id")
  private String tzId;

  @JsonProperty("localtime_epoch")
  private long localtimeEpoch;

  @JsonProperty("localtime")
  private String localtime;
}

