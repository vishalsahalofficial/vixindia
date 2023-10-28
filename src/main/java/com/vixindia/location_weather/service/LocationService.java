package com.vixindia.location_weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vixindia.location_weather.model.CurrentLocation;
import com.vixindia.location_weather.model.WeatherResponse;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

  private final RestTemplate restTemplate;
  private final String API_URL = "https://api.weatherapi.com/v1/current.json";
  private final String API_KEY = "4fcdeaa0ba0b4732b7a112545232810";


  public LocationService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  @SneakyThrows
  public CurrentLocation getLocation() {
    String apiUrl = "https://ipinfo.io/json";
    String template = restTemplate.getForObject(apiUrl, String.class);
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(template, CurrentLocation.class);
  }

  public WeatherResponse getWeatherForCity(String city) {
    String apiUrl = String.format("%s?q=%s&key=%s", API_URL, city, API_KEY);
    return restTemplate.getForObject(apiUrl, WeatherResponse.class);
  }
}
