package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDeliveryAddressDto extends RequestBaseDTO
{
	@JsonProperty("code")
	private String code;
	@JsonProperty("custNo")
	private String custNo;
	@JsonProperty("custName")
	private String custName;
	@JsonProperty("smeAddress")
	private String smeAddress;
	@JsonProperty("smeAddress2")
	private String smeAddress2;
	@JsonProperty("smeCity")
	private String smeCity;
	@JsonProperty("smeContact")
	private String smeContact;
	@JsonProperty("smeEmail")
	private String smeEmail;
	@JsonProperty("smePostCode")
	private String smePostCode;
	@JsonProperty("smeName")
	private String smeName;
	@JsonProperty("smePhoneNo")
	private String smePhoneNo;
	@JsonProperty("smeState")
	private String smeState;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSmeAddress() {
		return smeAddress;
	}
	public void setSmeAddress(String smeAddress) {
		this.smeAddress = smeAddress;
	}
	public String getSmeAddress2() {
		return smeAddress2;
	}
	public void setSmeAddress2(String smeAddress2) {
		this.smeAddress2 = smeAddress2;
	}
	public String getSmeCity() {
		return smeCity;
	}
	public void setSmeCity(String smeCity) {
		this.smeCity = smeCity;
	}
	public String getSmeContact() {
		return smeContact;
	}
	public void setSmeContact(String smeContact) {
		this.smeContact = smeContact;
	}
	public String getSmeEmail() {
		return smeEmail;
	}
	public void setSmeEmail(String smeEmail) {
		this.smeEmail = smeEmail;
	}
	public String getSmePostCode() {
		return smePostCode;
	}
	public void setSmePostCode(String smePostCode) {
		this.smePostCode = smePostCode;
	}
	public String getSmeName() {
		return smeName;
	}
	public void setSmeName(String smeName) {
		this.smeName = smeName;
	}
	public String getSmePhoneNo() {
		return smePhoneNo;
	}
	public void setSmePhoneNo(String smePhoneNo) {
		this.smePhoneNo = smePhoneNo;
	}
	public String getSmeState() {
		return smeState;
	}
	public void setSmeState(String smeState) {
		this.smeState = smeState;
	}

	
}
