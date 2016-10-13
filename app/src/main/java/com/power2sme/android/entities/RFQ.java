package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class RFQ implements Parcelable
{
	@JsonProperty("ApproxOrderValue")
	private String approxOrderValue;
	@JsonProperty("Rfqdate")
    private String rfqDate;
	@JsonProperty("Rfqno")
    private String rfqNo;
	@JsonProperty("TaxationPreference")
    private String taxationPreference;
	@JsonProperty("Status")
    private String Status;
	@JsonProperty("TotalAmount")
    private String TotalAmount;
	@JsonProperty("Type")
    private String Type;
	@JsonProperty("LineData")
	private List<RFQItem> lineData;
	
	///////////////////////////////////////////////////////////////////////
	public RFQ(){}
	public RFQ(Parcel in)
	{
		lineData = new ArrayList<RFQItem>();

		approxOrderValue=in.readString();
	    rfqDate=in.readString();
	    rfqNo=in.readString();
	    taxationPreference=in.readString();
	    Status=in.readString();
	    TotalAmount=in.readString();
	    Type=in.readString();
	    in.readTypedList(lineData, RFQItem.CREATOR);
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(approxOrderValue);
		dest.writeString(rfqDate);
		dest.writeString(rfqNo);
		dest.writeString(taxationPreference);
		dest.writeString(Status);
		dest.writeString(TotalAmount);
		dest.writeString(Type);
		dest.writeTypedList(lineData);
	}
	public static final Creator<RFQ> CREATOR = new Creator<RFQ>()
 	{
 		public RFQ createFromParcel(Parcel in) 
 		{
 			return new RFQ(in);
 		}
 	
 		public RFQ[] newArray (int size) 
 		{
 			return new RFQ[size];
 		}
 	};
	///////////////////////////////////////////////////////////////////////
	/**
	 * @return the approxOrderValue
	 */
	public String getApproxOrderValue() {
		return Utils.checkStringForNull(approxOrderValue);
	}
	/**
	 * @param approxOrderValue the approxOrderValue to set
	 */
	public void setApproxOrderValue(String approxOrderValue) {
		this.approxOrderValue = approxOrderValue;
	}
	/**
	 * @return the rfqDate
	 */
	public String getRfqDate() {
		return rfqDate;
	}
	/**
	 * @param rfqDate the rfqDate to set
	 */
	public void setRfqDate(String rfqDate) {
		this.rfqDate = rfqDate;
	}
	/**
	 * @return the rfqNo
	 */
	public String getRfqNo() {
		return Utils.checkStringForNull(rfqNo);
	}
	/**
	 * @param rfqNo the rfqNo to set
	 */
	public void setRfqNo(String rfqNo) {
		this.rfqNo = rfqNo;
	}
	/**
	 * @return the taxationPreference
	 */
	public String getTaxationPreference() {
		return Utils.checkStringForNull(taxationPreference);
	}
	/**
	 * @param taxationPreference the taxationPreference to set
	 */
	public void setTaxationPreference(String taxationPreference) {
		this.taxationPreference = taxationPreference;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return Utils.checkStringForNull(Status);
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		Status = status;
	}
	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return Utils.getCommaSeparatedNumber(TotalAmount);
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}
	/**
	 * @return the type
	 */
	public int getType() 
	{
		try
		{
			return Integer.parseInt(Type);	
		}
		catch(Exception ex)
		{}
		return 0;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}
	/**
	 * @return the lineData
	 */
	public List<RFQItem> getLineData() {
		return lineData;
	}
	/**
	 * @param lineData the lineData to set
	 */
	public void setLineData(List<RFQItem> lineData) {
		this.lineData = lineData;
	}
	
//	@JsonProperty("CustomerRequoteRequest")
//	private String CustomerRequoteRequest;
//	@JsonProperty("Attachment")
//	private String Attachment;
//	@JsonProperty("BillingType")
//	private String billingType;
//	@JsonProperty("CustFreightAmount")
//	private String custFreightAmount;
//	@JsonProperty("FreightInclude")
//	private String freightInclude;
//	@JsonProperty("IncludeCstVat")
//	private String includeCstVat;
//	@JsonProperty("IncludeExcise")
//	private String includeExcise;
//	@JsonProperty("LoadingCharges")
//    private String loadingCharges;
//	@JsonProperty("LoadingExclusive")
//    private String loadingExclusive;
//	@JsonProperty("LowerPriceRequest")
//	private String LowerPriceRequest;
//	@JsonProperty("OrderAcceptanceByCustomer")
//	private String OrderAcceptanceByCustomer;
//	@JsonProperty("OrderAcceptanceRequest")
//	private String OrderAcceptanceRequest;
//	@JsonProperty("RequireTimeFrame")
//    private String requireTimeFrame;
//	@JsonProperty("SalesQuoteGenerated")
//	private String SalesQuoteGenerated;
//	@JsonProperty("ItemCode")
//    private String itemCode;
//	@JsonProperty("PaymentMode")
//    private String paymentCode;
//	@JsonProperty("PaymentTerms")
//    private String paymentTerms;
//	@JsonProperty("SCMDate")
//    private String scmDate;
	
	
}
