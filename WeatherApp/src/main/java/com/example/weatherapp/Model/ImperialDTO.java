package com.example.weatherapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ImperialDTO {
    @JsonProperty("Value")
    private double Value;
    @JsonProperty("Unit")
    private String Unit;
    @JsonProperty("UnitType")
    private int UnitType;
}
