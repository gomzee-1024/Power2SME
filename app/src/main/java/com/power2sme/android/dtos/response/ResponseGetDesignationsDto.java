package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.Designation;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetDesignationsDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private List<Designation> data;

	/**
	 * @return the data
	 */
	public List<Designation> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<Designation> data) {
		this.data = data;
	}
}
