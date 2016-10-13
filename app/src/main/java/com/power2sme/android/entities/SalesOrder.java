package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrder implements Parcelable
{
    @JsonProperty("farmerId")
	private String farmerId;
    @JsonProperty("postingDate")
    private String postingDate;
    @JsonProperty("shipToAddress")
    private String shipToAddress;
    @JsonProperty("docType")
    private String docType;
    @JsonProperty("status")
    private String status;
    @JsonProperty("orderDate")
    private String orderDate;
    @JsonProperty("billToAddress")
    private String billToAddress;
    @JsonProperty("postingDesc")
    private String postingDesc;
    @JsonProperty("totalOutstandingAmt")
    private String totalOutstandingAmt;
    @JsonProperty("statusCode")
    private String statusCode;
    @JsonProperty("shipmentDate")
    private String shipmentDate;
    @JsonProperty("orderNo")
    private String orderNo;
    @JsonProperty("billToCustNo")
    private String billToCustNo;
    @JsonProperty("sellToAddress")
    private String sellToAddress;
    @JsonProperty("billingType")
    private String billingType;
    @JsonProperty("salesLine")
    private List<SalesOrderItem> salesLine;
    @JsonProperty("sellToCustNo")
    private String sellToCustNo;
    @JsonProperty("paymentTermsCode")
    private String paymentTermsCode;
    @JsonProperty("totalAmt")
    private String totalAmt;
    @JsonProperty("deviceId")
    private String deviceId;



    public SalesOrder(){}
	public SalesOrder(Parcel in)
	{
        salesLine=new ArrayList<SalesOrderItem>();

        farmerId = in.readString();
        postingDate = in.readString();
        shipToAddress = in.readString();
        docType = in.readString();
        status = in.readString();
        orderDate = in.readString();
        billToAddress = in.readString();
        postingDesc = in.readString();
        totalOutstandingAmt = in.readString();
        statusCode = in.readString();
        shipmentDate = in.readString();
        orderNo = in.readString();
        billToCustNo = in.readString();
        sellToAddress = in.readString();
        billingType = in.readString();
        in.readTypedList(salesLine, SalesOrderItem.CREATOR);
        sellToCustNo = in.readString();
        paymentTermsCode = in.readString();
        totalAmt = in.readString();
        deviceId = in.readString();
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
        dest.writeString(farmerId);
        dest.writeString(postingDate);
        dest.writeString(shipToAddress);
        dest.writeString(docType);
        dest.writeString(status);
        dest.writeString(orderDate);
        dest.writeString(billToAddress);
        dest.writeString(postingDesc);
        dest.writeString(totalOutstandingAmt);
        dest.writeString(statusCode);
        dest.writeString(shipmentDate);
        dest.writeString(orderNo);
        dest.writeString(billToCustNo);
        dest.writeString(sellToAddress);
        dest.writeString(billingType);
        dest.writeTypedList(salesLine);
        dest.writeString(sellToCustNo);
        dest.writeString(paymentTermsCode);
        dest.writeString(totalAmt);
        dest.writeString(deviceId);

	}

	public static final Creator<SalesOrder> CREATOR = new Creator<SalesOrder>()
	{
		public SalesOrder createFromParcel(Parcel in)
		{
			return new SalesOrder(in);
		}

		public SalesOrder[] newArray (int size)
		{
			return new SalesOrder[size];
		}
	};

    public String getTotalAmt()
    {
        return Utils.getCommaSeparatedNumber(totalAmt);
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getFarmerId() {
        return Utils.checkStringForNull(farmerId);
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getPostingDate() {
        return Utils.checkStringForNull(postingDate);
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getShipToAddress() {
        return Utils.checkStringForNull(shipToAddress);
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public String getDocType() {
        return Utils.checkStringForNull(docType);
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getStatus() {
        return Utils.checkStringForNull(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return Utils.checkStringForNull(orderDate);
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getBillToAddress() {
        return Utils.checkStringForNull(billToAddress);
    }

    public void setBillToAddress(String billToAddress) {
        this.billToAddress = billToAddress;
    }

    public String getPostingDesc() {
        return Utils.checkStringForNull(postingDesc);
    }

    public void setPostingDesc(String postingDesc) {
        this.postingDesc = postingDesc;
    }

    public String getTotalOutstandingAmt()
    {
        return Utils.getCommaSeparatedNumber(totalOutstandingAmt);
    }

    public void setTotalOutstandingAmt(String totalOutstandingAmt) {
        this.totalOutstandingAmt = totalOutstandingAmt;
    }

    public int getStatusCode()
    {
        try
        {
            if(statusCode!=null && statusCode.length()>0)
            {
                return Integer.parseInt(statusCode);
            }
        }
        catch(Exception ex)
        {}
        return 1;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getShipmentDate() {
        return Utils.checkStringForNull(shipmentDate);
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getOrderNo() {
        return Utils.checkStringForNull(orderNo);
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBillToCustNo() {
        return Utils.checkStringForNull(billToCustNo);
    }

    public void setBillToCustNo(String billToCustNo) {
        this.billToCustNo = billToCustNo;
    }

    public String getSellToAddress() {
        return Utils.checkStringForNull(sellToAddress);
    }

    public void setSellToAddress(String sellToAddress) {
        this.sellToAddress = sellToAddress;
    }

    public String getBillingType() {
        return Utils.checkStringForNull(billingType);
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public List<SalesOrderItem> getSalesLine() {
        return salesLine;
    }

    public void setSalesLine(List<SalesOrderItem> salesLine) {
        this.salesLine = salesLine;
    }

    public String getSellToCustNo() {
        return Utils.checkStringForNull(sellToCustNo);
    }

    public void setSellToCustNo(String sellToCustNo) {
        this.sellToCustNo = sellToCustNo;
    }

    public String getPaymentTermsCode() {
        return Utils.checkStringForNull(paymentTermsCode);
    }

    public void setPaymentTermsCode(String paymentTermsCode) {
        this.paymentTermsCode = paymentTermsCode;
    }

    public String getDeviceId() {
        return Utils.checkStringForNull(deviceId);
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    //	@JsonProperty("ShipmentStatus")
//	private String ShipmentStatus;
//
//	@JsonProperty("BasicAmount")
//	private String basicAmount;
//	@JsonProperty("CustomerName")
//	private String customerName;
//	@JsonProperty("CustomerNumber")
//	private String customerNumber;
//
//	@JsonProperty("DueDate")
//	private String DueDate;
//
//	@JsonProperty("Farmer")
//	private String farmer;
//	@JsonProperty("LineItem")
//    private List<SalesOrderItem> lineItem;
//	@JsonProperty("Billtype")
//    private String billType;
//	@JsonProperty("OrderDate")
//	private String orderDate;
//	@JsonProperty("OrderNo")
//	private String orderNo;
//	@JsonProperty("OrderStatus")
//	private String orderStatus;
//	@JsonProperty("PayTerm")
//	private String payTerm;
//	@JsonProperty("PostingDate")
//	private String postingDate;
//	@JsonProperty("ShipmentDate")
//	private String shipmentDate;
//	@JsonProperty("OutStandingAmount")
//	private String OutStandingAmount;
//
//	@JsonProperty("OrderTime")
//	private String OrderTime;
//
//
//	public SalesOrder(){}
//	public SalesOrder(Parcel in)
//	{
//		//basicAmmount = in.readDouble();
//		basicAmount = in.readString();
//		customerName = in.readString();
//		customerNumber = in.readString();
//		farmer = in.readString();
//		in.readTypedList(lineItem, SalesOrderItem.CREATOR);
//	    billType = in.readString();
//		orderDate = in.readString();
//		orderNo = in.readString();
//		orderStatus = in.readString();
//		payTerm = in.readString();
//		postingDate = in.readString();
//		shipmentDate = in.readString();
//		ShipmentStatus = in.readString();
//		OrderTime = in.readString();
//	}
//
//	@Override
//	public int describeContents()
//	{
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags)
//	{
//		dest.writeString(basicAmount);
//		dest.writeString(customerName);
//		dest.writeString(customerNumber);
//		dest.writeString(farmer);
//		dest.writeTypedList(lineItem);
//		dest.writeString(billType);
//		dest.writeString(orderDate);
//		dest.writeString(orderNo);
//		dest.writeString(orderStatus);
//		dest.writeString(payTerm);
//		dest.writeString(postingDate);
//		dest.writeString(shipmentDate);
//		dest.writeString(ShipmentStatus);
//		dest.writeString(OrderTime);
//	}
//
//	public static final Creator<SalesOrder> CREATOR = new Creator<SalesOrder>()
// 	{
// 		public SalesOrder createFromParcel(Parcel in)
// 		{
// 			return new SalesOrder(in);
// 		}
//
// 		public SalesOrder[] newArray (int size)
// 		{
// 			return new SalesOrder[size];
// 		}
// 	};
//
//
//	/**
//	 * @return the orderTime
//	 */
//	public String getOrderTime() {
//		return OrderTime;
//	}
//	/**
//	 * @param orderTime the orderTime to set
//	 */
//	public void setOrderTime(String orderTime) {
//		OrderTime = orderTime;
//	}
//	/**
//	 * @return the shipmentStatus
//	 */
//	public String getShipmentStatus() {
//		return ShipmentStatus;
//	}
//	/**
//	 * @param shipmentStatus the shipmentStatus to set
//	 */
//	public void setShipmentStatus(String shipmentStatus) {
//		ShipmentStatus = shipmentStatus;
//	}
//	/**
//	 * @param basicAmount the basicAmount to set
//	 */
//	public void setBasicAmount(String basicAmount) {
//		this.basicAmount = basicAmount;
//	}
//	/**
//	 * @param outStandingAmount the outStandingAmount to set
//	 */
//	public void setOutStandingAmount(String outStandingAmount) {
//		OutStandingAmount = outStandingAmount;
//	}
//	/**
//	 * @return the basicAmmount
//	 */
//	public String getBasicAmount()
//	{
//		return Utils.getCommaSeparatedNumber(basicAmount);
////		try
////		{
////			if(basicAmount!=null && basicAmount.length()>0)
////				return Double.parseDouble(basicAmount.trim());
////		}
////		catch(Exception ex)
////		{
////		}
////		return 0;
//	}
//	/**
//	 * @param basicAmmount the basicAmmount to set
//	 */
//	public void setBasicAmount(double basicAmount) {
//		this.basicAmount = String.valueOf(basicAmount);
//	}
//	/**
//	 * @return the customerName
//	 */
//	public String getCustomerName() {
//		return customerName;
//	}
//	/**
//	 * @param customerName the customerName to set
//	 */
//	public void setCustomerName(String customerName) {
//		this.customerName = customerName;
//	}
//	/**
//	 * @return the customerNumber
//	 */
//	public String getCustomerNumber() {
//		return customerNumber;
//	}
//	/**
//	 * @param customerNumber the customerNumber to set
//	 */
//	public void setCustomerNumber(String customerNumber) {
//		this.customerNumber = customerNumber;
//	}
//	/**
//	 * @return the lineItem
//	 */
//	public List<SalesOrderItem> getLineItem() {
//		return lineItem;
//	}
//	/**
//	 * @param lineItem the lineItem to set
//	 */
//	public void setLineItem(List<SalesOrderItem> lineItem) {
//		this.lineItem = lineItem;
//	}
//	/**
//	 * @return the billType
//	 */
//	public String getBillType() {
//		return billType;
//	}
//	/**
//	 * @param billType the billType to set
//	 */
//	public void setBillType(String billType) {
//		this.billType = billType;
//	}
//	/**
//	 * @return the orderDate
//	 */
//	public String getOrderDate() {
//		return orderDate;
//	}
//	/**
//	 * @param orderDate the orderDate to set
//	 */
//	public void setOrderDate(String orderDate) {
//		this.orderDate = orderDate;
//	}
//	/**
//	 * @return the orderNo
//	 */
//	public String getOrderNo() {
//		return orderNo;
//	}
//	/**
//	 * @param orderNo the orderNo to set
//	 */
//	public void setOrderNo(String orderNo) {
//		this.orderNo = orderNo;
//	}
//	/**
//	 * @return the orderStatus
//	 */
//	public int getOrderStatus()
//	{
//		try
//		{
//			if(orderStatus!=null && orderStatus.trim().length()>0)
//			{
//				return Integer.parseInt(orderStatus);
//			}
//		}
//		catch(Exception ex)
//		{
//		}
//		return 0;
//	}
//	/**
//	 * @param orderStatus the orderStatus to set
//	 */
//	public void setOrderStatus(String orderStatus) {
//		this.orderStatus = orderStatus;
//	}
//	/**
//	 * @return the payTerm
//	 */
//	public String getPayTerm() {
//		return payTerm;
//	}
//	/**
//	 * @param payTerm the payTerm to set
//	 */
//	public void setPayTerm(String payTerm) {
//		this.payTerm = payTerm;
//	}
//	/**
//	 * @return the postingDate
//	 */
//	public String getPostingDate() {
//		return postingDate;
//	}
//	/**
//	 * @param postingDate the postingDate to set
//	 */
//	public void setPostingDate(String postingDate) {
//		this.postingDate = postingDate;
//	}
//	/**
//	 * @return the shipmentDate
//	 */
//	public String getShipmentDate() {
//		return shipmentDate;
//	}
//	/**
//	 * @param shipmentDate the shipmentDate to set
//	 */
//	public void setShipmentDate(String shipmentDate) {
//		this.shipmentDate = shipmentDate;
//	}
//	/**
//	 * @return the farmer
//	 */
//	public String getFarmer() {
//		return farmer;
//	}
//	/**
//	 * @param farmer the farmer to set
//	 */
//	public void setFarmer(String farmer) {
//		this.farmer = farmer;
//	}
//	/**
//	 * @return the dueDate
//	 */
//	public String getDueDate() {
//		return DueDate;
//	}
//	/**
//	 * @param dueDate the dueDate to set
//	 */
//	public void setDueDate(String dueDate) {
//		DueDate = dueDate;
//	}
//	/**
//	 * @return the outStandingAmount
//	 */
//	public double getOutStandingAmount() {
//		return Double.parseDouble(OutStandingAmount);
//	}
//	/**
//	 * @param outStandingAmount the outStandingAmount to set
//	 */
//	public void setOutStandingAmount(double outStandingAmount)
//	{
//		OutStandingAmount = String.valueOf(outStandingAmount);
//	}
	
}
