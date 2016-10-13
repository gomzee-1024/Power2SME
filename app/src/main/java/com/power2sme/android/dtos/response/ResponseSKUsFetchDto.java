package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.SKU_v3;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSKUsFetchDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private List<SKU_v3> SKUs;

	public List<SKU_v3> getSKUs() {
		return SKUs;
	}
}
