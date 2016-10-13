package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.City;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetCitiesDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private List<City> data;

	public List<City> getData() {
		return data;
	}

	public void setData(List<City> data) {
		this.data = data;
	}
	
}
