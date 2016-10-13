package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralInformation implements Parcelable
{
	@JsonProperty("Address")
	private String address;
	@JsonProperty("City")
	private String city;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("MobilePhoneNumber")
	private String mobilePhoneNumber;
	@JsonProperty("FirstName")
	private String FirstName;
	@JsonProperty("LastName")
	private String LastName;
	@JsonProperty("Number")
	private String number;
	@JsonProperty("SecondaryAddress")
	private String secondaryAddress;
	@JsonProperty("State")
	private String state;
	@JsonProperty("Website")
	private String website;
	
	public GeneralInformation(){}
	public GeneralInformation(Parcel in)
 	{
		address = in.readString();
		city = in.readString();
		email = in.readString();
		mobilePhoneNumber = in.readString();
		FirstName = in.readString();
		LastName = in.readString();
		number = in.readString();
		secondaryAddress = in.readString();
		state = in.readString();
		website = in.readString();
	}
 	@Override
 	public int describeContents() 
 	{
 		return 0;
 	}
 	@Override
 	public void writeToParcel(Parcel dest, int flags) 
 	{
 		dest.writeString(address);
 		dest.writeString(city);
 		dest.writeString(email);
 		dest.writeString(mobilePhoneNumber);
 		dest.writeString(FirstName);
 		dest.writeString(LastName);
 		dest.writeString(number);
 		dest.writeString(secondaryAddress);
 		dest.writeString(state);
 		dest.writeString(website);
	}
 	public static final Creator<GeneralInformation> CREATOR = new Creator<GeneralInformation>()
 	{
 		public GeneralInformation createFromParcel(Parcel in) 
 		{
 			return new GeneralInformation(in);
 		}
 	
 		public GeneralInformation[] newArray (int size) 
 		{
 			return new GeneralInformation[size];
 		}
 	};
	
	
	public String getAddress() {
		return Utils.checkStringForNull(address);
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return Utils.checkStringForNull(city);
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return Utils.checkStringForNull(email);
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobilePhoneNumber() {
		return Utils.checkStringForNull(mobilePhoneNumber);
	}
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}
	public String getNumber() {
		return Utils.checkStringForNull(number);
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSecondaryAddress() {
		return Utils.checkStringForNull(secondaryAddress);
	}
	public void setSecondaryAddress(String secondaryAddress) {
		this.secondaryAddress = secondaryAddress;
	}
	public String getState() {
		return Utils.checkStringForNull(state);
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWebsite() {
		return Utils.checkStringForNull(website);
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return Utils.checkStringForNull(FirstName);
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
		return Utils.checkStringForNull(LastName);
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	}
}
