package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestGetCitiesDto extends RequestBaseDTO
{
	@JsonProperty("stateid")
	private String stateid;

	public String getStateid() {
		return stateid;
	}

	public void setStateid(String stateid) {
		this.stateid = stateid;
	}
	
	
}
