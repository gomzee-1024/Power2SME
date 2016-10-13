package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.LoginResponseData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSMESignupDto extends ResponseBaseDTO
{
	@JsonProperty("Data")
	private LoginResponseData data;

	/**
	 * @return the data
	 */
	public LoginResponseData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(LoginResponseData data) {
		this.data = data;
	}

    @Override
    public String toString()
    {
        return "ResponseSMESignupDto: "+getErrorCode()+"="+getMessage()+"="+getStatus();
    }
}
