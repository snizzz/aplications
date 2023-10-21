package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryModel {
    @JsonProperty("ID")
    private String ID;
    @JsonProperty("LocalizedName")
    private String LocalizedName;
}
