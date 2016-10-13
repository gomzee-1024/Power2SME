package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City implements Serializable
{
	@JsonProperty("Cityid")
	private long cityId;
	@JsonProperty("CityName")
	private String cityName;
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return Utils.checkStringForNull(cityName);
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Override
	public String toString() 
	{
		return getCityName();
	}
}
