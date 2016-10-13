package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.Shipment;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseShipmentDetailsDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private List<Shipment> Data;

	/**
	 * @return the data
	 */
	public List<Shipment> getData() {
		return Data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<Shipment> data) {
		Data = data;
	}
	
	
}
