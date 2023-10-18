package com.example.weatherapp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CityDTO {
	@JsonProperty("Version")
	int Version;
	@JsonProperty("Key")
	String Key;
	@JsonProperty("Type")
	String Type;
	@JsonProperty("Rank")
	int Rank;
	@JsonProperty("LocalizedName")
	String LocalizedName;
	@JsonProperty("Country")
	CountryDTO Country;
	@JsonProperty("AdministrativeArea")
	AdministrativeAreaDTO AdministrativeArea;
}
