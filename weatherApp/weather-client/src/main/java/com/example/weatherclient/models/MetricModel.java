package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MetricModel {
    @JsonProperty("Value")
    private double Value;
    @JsonProperty("Unit")
    private String Unit;
    @JsonProperty("UnitType")
    private int UnitType;
}
