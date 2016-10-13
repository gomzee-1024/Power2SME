package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.Urgency_v3;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetUrgencyListDto  extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private ArrayList<Urgency_v3> data;

	public ArrayList<Urgency_v3> getData()
	{
		return data;
	}

	public void setData(ArrayList<Urgency_v3> data)
	{
		this.data = data;
	}
}
