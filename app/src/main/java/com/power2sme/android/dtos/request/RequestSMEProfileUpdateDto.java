package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestSMEProfileUpdateDto  extends RequestBaseDTO
{
	@JsonProperty("strSMEid")
	private String strSMEid;
	@JsonProperty("strFirstName")
	private String strFirstName;
	@JsonProperty("strLastName")
	private String strLastName;
	@JsonProperty("strAddress")
	private String strAddress;
	@JsonProperty("strSecondaryAddress")
	private String strSecondaryAddress;
	@JsonProperty("MobilephoneNumber")
	private String mobilephoneNumber;
	@JsonProperty("strEmail")
	private String strEmail;
	@JsonProperty("intCity")
	private String intCity;
	@JsonProperty("intState")
	private String intState;
	@JsonProperty("strWebsite")
	private String strWebsite;
	public String getStrSMEid() {
		return strSMEid;
	}
	public void setStrSMEid(String strSMEid) {
		this.strSMEid = strSMEid;
	}
	public String getStrFirstName() {
		return strFirstName;
	}
	public void setStrFirstName(String strFirstName) {
		this.strFirstName = strFirstName;
	}
	public String getStrLastName() {
		return strLastName;
	}
	public void setStrLastName(String strLastName) {
		this.strLastName = strLastName;
	}
	public String getStrAddress() {
		return strAddress;
	}
	public void setStrAddress(String strAddress) {
		this.strAddress = strAddress;
	}
	public String getStrSecondaryAddress() {
		return strSecondaryAddress;
	}
	public void setStrSecondaryAddress(String strSecondaryAddress) {
		this.strSecondaryAddress = strSecondaryAddress;
	}
	public String getMobilephoneNumber() {
		return mobilephoneNumber;
	}
	public void setMobilephoneNumber(String mobilephoneNumber) {
		this.mobilephoneNumber = mobilephoneNumber;
	}
	public String getStrEmail() {
		return strEmail;
	}
	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}
	public String getIntCity() {
		return intCity;
	}
	public void setIntCity(String intCity) {
		this.intCity = intCity;
	}
	public String getIntState() {
		return intState;
	}
	public void setIntState(String intState) {
		this.intState = intState;
	}
	public String getStrWebsite() {
		return strWebsite;
	}
	public void setStrWebsite(String strWebsite) {
		this.strWebsite = strWebsite;
	}
}
