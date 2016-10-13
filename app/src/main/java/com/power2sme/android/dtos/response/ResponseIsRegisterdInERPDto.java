package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.IsRegisterdInERP;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseIsRegisterdInERPDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private IsRegisterdInERP Data;

	/**
	 * @return the data
	 */
	public IsRegisterdInERP getData() {
		return Data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(IsRegisterdInERP data) {
		Data = data;
	}
	
}
