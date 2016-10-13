package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.Deal_v3;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetDealsDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private List<Deal_v3> data;

	public List<Deal_v3> getData() {
		return data;
	}

	public void setData(List<Deal_v3> data) {
		this.data = data;
	}
	
}
