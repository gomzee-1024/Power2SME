package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Update implements Parcelable
{
	@JsonProperty("Id")
	private String Message;
	@JsonProperty("OrderNumber")
	private String OrderNumber;
	@JsonProperty("UpdateType")
	private String OrderType;
	@JsonProperty("totalrecord")
	private String totalrecord;
	@JsonProperty("Pages")
	private String Pages;
	@JsonProperty("RowNumber")
	private String MUDate;
	@JsonProperty("MUDate")
	private String RowNumber;
	@JsonProperty("Data")
	private String Data;
	///////////////////////////////////////////////////////////////////
	public Update(){}
	public Update(Parcel in)
	{
		Message=in.readString();
		OrderNumber=in.readString();
		OrderType=in.readString();
		totalrecord=in.readString();
		Pages=in.readString();
		MUDate=in.readString();
		RowNumber=in.readString();
		Data=in.readString();
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(Message);
		dest.writeString(OrderNumber);
		dest.writeString(OrderType);
		dest.writeString(totalrecord);
		dest.writeString(Pages);
		dest.writeString(MUDate);
		dest.writeString(RowNumber);
		dest.writeString(Data);
	}
	public static final Creator<Update> CREATOR = new Creator<Update>()
 	{
 		public Update createFromParcel(Parcel in) 
 		{
 			return new Update(in);
 		}
 	
 		public Update[] newArray (int size) 
 		{
 			return new Update[size];
 		}
 	};
	///////////////////////////////////////////////////////////////////
	/**
	 * @return the message
	 */
	public String getMessage() {
		return Utils.checkStringForNull(Message);
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String Message) {
		System.err.println("message set::"+Message);
		this.Message = Message;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return Utils.checkStringForNull(OrderNumber);
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		System.err.println("ordernm set::"+orderNumber);
		OrderNumber = orderNumber;
	}
	/**
	 * @return the orderType
	 */
	public String getOrderType() {
		return Utils.checkStringForNull(OrderType);
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		System.err.println("orderType set::"+orderType);
		OrderType = orderType;
	}
	/**
	 * @return the totalrecord
	 */
	public String getTotalrecord() {
		return Utils.checkStringForNull(totalrecord);
	}
	/**
	 * @param totalrecord the totalrecord to set
	 */
	public void setTotalrecord(String totalrecord) {
		this.totalrecord = totalrecord;
	}
	/**
	 * @return the pages
	 */
	public String getPages() {
		return Utils.checkStringForNull(Pages);
	}
	/**
	 * @param pages the pages to set
	 */
	public void setPages(String pages) {
		Pages = pages;
	}
	/**
	 * @return the mUDate
	 */
	public String getMUDate() {
		return Utils.checkStringForNull(MUDate);
	}
	/**
	 * @param mUDate the mUDate to set
	 */
	public void setMUDate(String mUDate) {
		MUDate = mUDate;
	}
	/**
	 * @return the rowNumber
	 */
	public String getRowNumber() {
		return Utils.checkStringForNull(RowNumber);
	}
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(String rowNumber) {
		RowNumber = rowNumber;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return Utils.checkStringForNull(Data);
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		Data = data;
	}
}
