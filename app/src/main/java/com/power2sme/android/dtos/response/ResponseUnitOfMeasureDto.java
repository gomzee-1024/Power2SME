package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.UnitOfMeasure;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUnitOfMeasureDto  extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private ArrayList<UnitOfMeasure> data;

	/**
	 * @return the data
	 */
	public ArrayList<UnitOfMeasure> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<UnitOfMeasure> data) {
		this.data = data;
	}
	
	
}
