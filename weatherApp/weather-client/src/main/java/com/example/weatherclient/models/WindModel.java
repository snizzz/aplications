package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WindModel {
    @JsonProperty("Direction")
    private DirectionModel Direction;
    @JsonProperty("Speed")
    private SpeedModel Speed;
}
