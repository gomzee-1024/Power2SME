package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.ContactInfo;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseContactInfoListDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private ContactInfo data;

	public ContactInfo getData() {
		return data;
	}

	public void setData(ContactInfo data) {
		this.data = data;
	}
}
