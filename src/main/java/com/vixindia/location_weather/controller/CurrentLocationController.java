package com.vixindia.location_weather.controller;

import com.vixindia.location_weather.model.CurrentLocation;
import com.vixindia.location_weather.model.WeatherResponse;
import com.vixindia.location_weather.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrentLocationController {

  @Autowired
  LocationService locationService;

  @GetMapping("/current_location_weather")
  public String getCurrentLocation() {

    CurrentLocation locationDetails = locationService.getLocation();

    WeatherResponse weatherForCity = locationService.getWeatherForCity(
        locationDetails.getCity());

    return "Weather at your Location : " + weatherForCity.getLocation().getName()+" at "+weatherForCity.getLocation().getLocaltime()+ " is "+weatherForCity.getCurrent().getTempC()+"'C . and your Telecom Provider is : "+locationDetails.getProvider();
  }
}
