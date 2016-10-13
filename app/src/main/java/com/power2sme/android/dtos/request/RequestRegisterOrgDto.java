package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.Organization_v3;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRegisterOrgDto extends RequestBaseDTO
{
	@JsonProperty("object_type_id")
	private String object_type_id;
	@JsonProperty("organisation")
	private Organization_v3 organisation;

	public String getObject_type_id() {
		return object_type_id;
	}

	public void setObject_type_id(String object_type_id) {
		this.object_type_id = object_type_id;
	}

	public Organization_v3 getOrganisation() {
		return organisation;
	}

	public void setOrganisation(Organization_v3 organisation) {
		this.organisation = organisation;
	}
}
