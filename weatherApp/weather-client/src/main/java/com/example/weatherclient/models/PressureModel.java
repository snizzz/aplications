package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PressureModel {
    @JsonProperty("Metric")
    private MetricModel Metric;
    @JsonProperty("Imperial")
    private ImperialModel Imperial;
}
