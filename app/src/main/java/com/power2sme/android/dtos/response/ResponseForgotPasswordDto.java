package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseForgotPasswordDto extends ResponseBaseDTO
{
	@JsonProperty("frdPwd")
	private String frdPwd;

	public String getFrdPwd() {
		return frdPwd;
	}

	public void setFrdPwd(String frdPwd) {
		this.frdPwd = frdPwd;
	}
	
}
