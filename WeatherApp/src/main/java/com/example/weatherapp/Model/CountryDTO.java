package com.example.weatherapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CountryDTO {
    @JsonProperty("ID")
    private String ID;
    @JsonProperty("LocalizedName")
    private String LocalizedName;
}
