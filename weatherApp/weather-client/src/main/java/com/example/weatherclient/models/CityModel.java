package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CityModel {
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
	CountryModel Country;
	@JsonProperty("AdministrativeArea")
	AdministrativeAreaModel AdministrativeArea;
	@JsonProperty("GeoPosition")
	GeoPositionModel GeoPosition;
	@JsonProperty("TimeZone")
	TimeZone TimeZone;

	@Override
	public String toString(){
		return LocalizedName;
	}
}
