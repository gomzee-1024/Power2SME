package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDeviceIdRegisterDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private String Data;
}
