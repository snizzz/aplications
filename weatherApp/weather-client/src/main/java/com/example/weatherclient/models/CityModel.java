package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CityModel {
	@JsonProperty("Version")
	private int Version;
	@JsonProperty("Key")
	private String Key;
	@JsonProperty("Type")
	private String Type;
	@JsonProperty("Rank")
	private int Rank;
	@JsonProperty("LocalizedName")
	private String LocalizedName;
	@JsonProperty("Country")
	private CountryModel Country;
	@JsonProperty("AdministrativeArea")
	private AdministrativeAreaModel AdministrativeArea;
	@JsonProperty("GeoPosition")
	private GeoPositionModel GeoPosition;
	@JsonProperty("TimeZone")
	private TimeZoneModel TimeZone;
	@JsonProperty("Details")
	private DetailsModel Details;

	@Override
	public String toString(){
		return LocalizedName;
	}
}
