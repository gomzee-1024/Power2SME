package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.DeliveryAddress;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDeliveryAddressDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private List<DeliveryAddress> data;

	public List<DeliveryAddress> getData() {
		return data;
	}

	public void setData(List<DeliveryAddress> data) {
		this.data = data;
	}
	
	
}
