package com.example.weatherapp.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TemperatureDTO {
    @JsonProperty("Metric")
    private MetricDTO Metric;
    @JsonProperty("Imperial")
    private ImperialDTO Imperial;
}
