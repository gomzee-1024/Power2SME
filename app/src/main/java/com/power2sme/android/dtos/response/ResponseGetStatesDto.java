package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.State;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetStatesDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private List<State> data;

	public List<State> getData() 
	{
		return data;
	}

	public void setData(List<State> data) 
	{
		this.data = data;
	}
	
}
