package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.Deal_v3;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetDealsByIDDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private Deal_v3 data;

	/**
	 * @return the data
	 */
	public Deal_v3 getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Deal_v3 data) {
		this.data = data;
	}

	
}
