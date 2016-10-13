package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.ServerPrefix;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetServerPrefixDto extends ResponseBaseDTO
{
	@JsonCreator
	public ResponseGetServerPrefixDto(){}
	
	@JsonProperty("Data")
	private ServerPrefix data;

	public ServerPrefix getData() {
		return data;
	}

	public void setData(ServerPrefix data) {
		this.data = data;
	}
	
}
