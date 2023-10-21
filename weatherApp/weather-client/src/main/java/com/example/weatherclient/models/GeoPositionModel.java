package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GeoPositionModel {
    @JsonProperty("Latitude")
    float Latitude;
    @JsonProperty("Longitude")
    float Longitude;
    @JsonProperty("Elevation")
    Elevation Elevation;
}
