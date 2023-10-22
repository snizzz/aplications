package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DirectionModel {
    @JsonProperty("Degrees")
    private int Degrees;
    @JsonProperty("Localized")
    private String Localized;
    @JsonProperty("English")
    private String English;

    @Override
    public String toString(){
        return Degrees+" stopni, "+Localized;
    }
}
