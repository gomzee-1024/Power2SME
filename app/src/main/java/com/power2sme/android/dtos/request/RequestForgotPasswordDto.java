package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestForgotPasswordDto extends RequestBaseDTO
{
	@JsonProperty("userId")
	private String stremailid;

	/**
	 * @return the stremailid
	 */
	public String getStremailid() {
		return stremailid;
	}

	/**
	 * @param stremailid the stremailid to set
	 */
	public void setStremailid(String stremailid) {
		this.stremailid = stremailid;
	}
}
