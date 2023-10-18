package com.example.weatherapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccuWeatherService {
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public String callExternalApi() {
        String apiUrl = "https://api.example.com/data"; // Replace with the API URL you want to call
        return restTemplate.getForObject(apiUrl, String.class);
    }
}