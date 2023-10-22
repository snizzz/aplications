package com.example.weatherclient.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherModel {
    @JsonProperty("LocalObservationDateTime ")
    private LocalDateTime LocalObservationDateTime;
    @JsonProperty("EpochTime")
    private long EpochTime;
    @JsonProperty("WeatherText ")
    private String WeatherText;
    @JsonProperty("WeatherIcon ")
    private int WeatherIcon;
    @JsonProperty("HasPrecipitation")
    private boolean HasPrecipitation;
    @JsonProperty("PrecipitationType")
    private Object PrecipitationType;
    @JsonProperty("IsDayTime")
    private boolean IsDayTime;
    @JsonProperty("Temperature")
    private TemperatureModel Temperature;
    @JsonProperty("MobileLink")
    private String MobileLink;
    @JsonProperty("Link")
    private String Link;
    @JsonProperty("RelativeHumidity")
    private int RelativeHumidity;
    @JsonProperty("Wind")
    private WindModel Wind;
    @JsonProperty("Pressure")
    private PressureModel Pressure;
}
