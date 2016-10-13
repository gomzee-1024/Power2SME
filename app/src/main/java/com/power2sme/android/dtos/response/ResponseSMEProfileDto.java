package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.SMEProfile;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSMEProfileDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private SMEProfile smeProfile;

	public SMEProfile getSmeProfile() {
		return smeProfile;
	}

	public void setSmeProfile(SMEProfile smeProfile) {
		this.smeProfile = smeProfile;
	}
}
