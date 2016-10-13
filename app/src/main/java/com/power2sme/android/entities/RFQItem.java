package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RFQItem implements Parcelable
{
	@JsonProperty("ItemName")
	private String itemName;
	@JsonProperty("LineAmount")
	private String lineAmount;
	@JsonProperty("Quantity")
	private String quantity;
	@JsonProperty("UOM")
	private String uOM;
	@JsonProperty("Rate")
	private String rate;
	
	////////////////////////////////////////////////////////////
	public RFQItem(){}
	public RFQItem(Parcel in)
	{
		itemName=in.readString();
		lineAmount=in.readString();
		quantity=in.readString();
		uOM=in.readString();
		rate=in.readString();
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(itemName);
		dest.writeString(lineAmount);
		dest.writeString(quantity);
		dest.writeString(uOM);
		dest.writeString(rate);
	}
	public static final Creator<RFQItem> CREATOR = new Creator<RFQItem>()
 	{
 		public RFQItem createFromParcel(Parcel in) 
 		{
 			return new RFQItem(in);
 		}
 	
 		public RFQItem[] newArray (int size) 
 		{
 			return new RFQItem[size];
 		}
 	};
	////////////////////////////////////////////////////////////
//	@JsonProperty("ItemCategory")
//	private String itemCategory;
//	@JsonProperty("ItemCode")
//	private String itemCode;
//	@JsonProperty("ItemDescription")
//	private String itemDescription;
//	@JsonProperty("LineNo")
//	private long lineNo;
//	@JsonProperty("No")
//	private String no;
//	@JsonProperty("Rate")
//	private double rate;
//	@JsonProperty("SubCategory1")
//	private String subCategory1;
//	@JsonProperty("SubCategory2")
//	private String subCategory2;
//	@JsonProperty("SubCategory3")
//	private String subCategory3;
//	@JsonProperty("SubCategory4")
//	private String subCategory4;
	
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return Utils.checkStringForNull(itemName);
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * @return the lineAmount
	 */
	public String getLineAmount() 
	{
		return Utils.getCommaSeparatedNumber(lineAmount);
	}
	/**
	 * @param lineAmount the lineAmount to set
	 */
	public void setLineAmount(String lineAmount) {
		this.lineAmount = lineAmount;
	}
	/**
	 * @return the quantity
	 */
	public Double getQuantity() 
	{
		try
		{
			return Double.parseDouble(quantity);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) 
	{
		
		this.quantity = quantity;
	}
	/**
	 * @return the uOM
	 */
	public String getuOM() {
		return Utils.checkStringForNull(uOM);
	}
	/**
	 * @param uOM the uOM to set
	 */
	public void setuOM(String uOM) {
		this.uOM = uOM;
	}
	public String getRate() 
	{
		try
		{
			if(lineAmount!=null && lineAmount.length()>0 && quantity!=null && quantity.length()>0)
			{
				Double rfqTotalPrice = Double.parseDouble(lineAmount);
				Double rfqItemQuantity = Double.parseDouble(quantity);
				double unitPrice = (double)rfqTotalPrice/rfqItemQuantity;
				return Utils.getCommaSeparatedNumber(String.valueOf(unitPrice));	
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
}
