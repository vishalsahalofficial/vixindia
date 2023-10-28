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
public class WeatherResponse {
  @JsonProperty("location")
  private Location location;
  @JsonProperty("current")
  private CurrentWeather current;

}
