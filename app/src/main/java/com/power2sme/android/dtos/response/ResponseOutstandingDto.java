package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.Outstanding;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOutstandingDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private Outstanding data;

	//////////////////////////////////////////////
	
	/**
	 * @return the data
	 */
	public Outstanding getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Outstanding data) {
		this.data = data;
	}
	
	

}
