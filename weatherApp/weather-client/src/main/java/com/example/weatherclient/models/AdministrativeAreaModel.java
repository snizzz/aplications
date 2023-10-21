package com.example.weatherclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdministrativeAreaModel {
	@JsonProperty("ID")
	private String ID;
	@JsonProperty("LocalizedName")
	private String LocalizedName;
	@JsonProperty("EnglishName")
	private String EnglishName;
	@JsonProperty("Level")
	private int Level;
	@JsonProperty("LocalizedType")
	private String LocalizedType;
	@JsonProperty("EnglishType")
	private String EnglishType;
	@JsonProperty("CountryID")
	private String CountryID;
}
