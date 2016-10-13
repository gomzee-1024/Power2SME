package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestAddCampaignDto extends RequestBaseDTO
{
	@JsonProperty("Firstname")
	private String firstName;
	@JsonProperty("Lastname")
	private String lastName;
	@JsonProperty("Mobile")
	private String mobile;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Product")
	private String product;
	@JsonProperty("Brand")
	private String brand;
	@JsonProperty("Grade")
	private String grade;
	@JsonProperty("DlvAddress")
	private String dlvAddress;
	@JsonProperty("Statename")
	private String stateName;
	@JsonProperty("CityName")
	private String cityName;
	@JsonProperty("Qty")
	private String qty;
	@JsonProperty("Type")
	private String type;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getDlvAddress() {
		return dlvAddress;
	}
	public void setDlvAddress(String dlvAddress) {
		this.dlvAddress = dlvAddress;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
