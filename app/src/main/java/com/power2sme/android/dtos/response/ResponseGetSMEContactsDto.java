package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.power2sme.android.entities.ContactInfo;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetSMEContactsDto extends ResponseBaseDTO
{
	private List<ContactInfo> data;

	List<ContactInfo> getData() {
		return data;
	}

	void setData(List<ContactInfo> data) {
		this.data = data;
	}
	
}
