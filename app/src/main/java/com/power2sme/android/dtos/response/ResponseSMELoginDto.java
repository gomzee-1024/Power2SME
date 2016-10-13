package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.LoginResponseData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSMELoginDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private LoginResponseData data;

	public LoginResponseData getData() {
		return data;
	}

	public void setData(LoginResponseData data) {
		this.data = data;
	}
}
