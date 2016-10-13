package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestLoginBySocialNetworkDto extends RequestBaseDTO
{
	@JsonProperty("userId")
	private String EmailId;
	@JsonProperty("SignUpMode")
	private String SignUpMode;
	@JsonProperty("FirstName")
	private String FirstName;
	@JsonProperty("LastName")
	private String LastName;
    @JsonProperty("LeadSource")
    private String LeadSource;


    public String getLeadSource() {
        return LeadSource;
    }

    public void setLeadSource(String leadSource) {
        LeadSource = leadSource;
    }

    /**
	 * @return the emailId
	 */
	public String getEmailId() {
		return EmailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		EmailId = emailId;
	}
	/**
	 * @return the signUpMode
	 */
	public String getSignUpMode() {
		return SignUpMode;
	}
	/**
	 * @param signUpMode the signUpMode to set
	 */
	public void setSignUpMode(String signUpMode) {
		SignUpMode = signUpMode;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return FirstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return LastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	
	
}
