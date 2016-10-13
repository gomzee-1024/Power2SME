package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryAddress implements Parcelable, Cloneable
{
	@JsonProperty("Code")
	private String Code="";
	@JsonProperty("CustomerNumber")
	private String CustomerNumber="";
	@JsonProperty("CustomerName")
	private String CustomerName="";
	@JsonProperty("smeAddress")
	private String smeAddress="";
	@JsonProperty("smeAddress2")
	private String smeAddress2="";
	@JsonProperty("smeCity")
	private String smeCity="";
	@JsonProperty("smeContact")
	private String smeContact="";
	@JsonProperty("smeEmail")
	private String smeEmail="";
	@JsonProperty("smePostCode")
	private String smePostCode="";
	@JsonProperty("smeName")
	private String smeName="";
	@JsonProperty("smePhoneNo")
	private String smePhoneNo="";
	@JsonProperty("smeState")
	private String smeState="";
	
	private int selectedPosition=-1;

	public Object getClone() throws CloneNotSupportedException
	{
		return this.clone();
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getSelectedPosition() 
	{
		return selectedPosition;
	}
	
	public void setSelectedPosition(int selectedPosition) 
	{
		this.selectedPosition = selectedPosition;
	}
	
	public DeliveryAddress(){}
	public DeliveryAddress(DeliveryAddress srcAddress)
	{
		this.Code= srcAddress.getCode();
		this.CustomerNumber=srcAddress.getCustomerNumber();
		this.CustomerName=srcAddress.getCustomerName();
		this.smeAddress=srcAddress.getSmeAddress();
		this.smeAddress2=srcAddress.getSmeAddress2();
		this.smeCity=srcAddress.getSmeCity();
		this.smeContact=srcAddress.getSmeContact();
		this.smeEmail=srcAddress.getSmeEmail();
		this.smePostCode=srcAddress.getSmePostCode();
		this.smeName=srcAddress.getSmeName();
		this.smePhoneNo=srcAddress.getSmePhoneNo();
		this.smeState=srcAddress.getSmeState();
	}
	public DeliveryAddress(Parcel in)
 	{
		Code = in.readString();
		CustomerNumber = in.readString();
		CustomerName = in.readString();
		smeAddress = in.readString();
		smeAddress2 = in.readString();
		smeCity = in.readString();
		smeContact = in.readString();
		smeEmail = in.readString();
		smePostCode = in.readString();
		smeName = in.readString();
		smePhoneNo = in.readString();
		smeState = in.readString();
	}
 	@Override
 	public int describeContents() 
 	{
 		return 0;
 	}
 	@Override
 	public void writeToParcel(Parcel dest, int flags) 
 	{
 		dest.writeString(Code);
 		dest.writeString(CustomerNumber);
 		dest.writeString(CustomerName);
 		dest.writeString(smeAddress);
 		dest.writeString(smeAddress2);
 		dest.writeString(smeCity);
 		dest.writeString(smeContact);
 		dest.writeString(smeEmail);
 		dest.writeString(smePostCode);
 		dest.writeString(smeName);
 		dest.writeString(smePhoneNo);
 		dest.writeString(smeState);
	}
 	public static final Creator<DeliveryAddress> CREATOR = new Creator<DeliveryAddress>()
 	{
 		public DeliveryAddress createFromParcel(Parcel in) 
 		{
 			return new DeliveryAddress(in);
 		}
 	
 		public DeliveryAddress[] newArray (int size) 
 		{
 			return new DeliveryAddress[size];
 		}
 	};
	
	
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return Utils.checkStringForNull(Code);
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		Code = code;
	}
	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return Utils.checkStringForNull(CustomerNumber);
	}
	/**
	 * @param customerNumber the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		CustomerNumber = customerNumber;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return Utils.checkStringForNull(CustomerName);
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getSmeAddress() {
		return Utils.checkStringForNull(smeAddress);
	}
	public void setSmeAddress(String smeAddress) {
		this.smeAddress = smeAddress;
	}
	public String getSmeAddress2() {
		return Utils.checkStringForNull(smeAddress2);
	}
	public void setSmeAddress2(String smeAddress2) {
		this.smeAddress2 = smeAddress2;
	}
	public String getSmeCity() {
		return Utils.checkStringForNull(smeCity);
	}
	public void setSmeCity(String smeCity) {
		this.smeCity = smeCity;
	}
	public String getSmeContact() {
		return Utils.checkStringForNull(smeContact);
	}
	public void setSmeContact(String smeContact) {
		this.smeContact = smeContact;
	}
	public String getSmeEmail() {
		return Utils.checkStringForNull(smeEmail);
	}
	public void setSmeEmail(String smeEmail) {
		this.smeEmail = smeEmail;
	}
	public String getSmePostCode() {
		return Utils.checkStringForNull(smePostCode);
	}
	public void setSmePostCode(String smePostCode) {
		this.smePostCode = smePostCode;
	}
	public String getSmeName() {
		return Utils.checkStringForNull(smeName);
	}
	public void setSmeName(String smeName) {
		this.smeName = smeName;
	}
	public String getSmePhoneNo() {
		return Utils.checkStringForNull(smePhoneNo);
	}
	public void setSmePhoneNo(String smePhoneNo) {
		this.smePhoneNo = smePhoneNo;
	}
	public String getSmeState() {
		return Utils.checkStringForNull(smeState);
	}
	public void setSmeState(String smeState) {
		this.smeState = smeState;
	}
	
	@Override
	public String toString() 
	{
		return getCode();
	}
}
