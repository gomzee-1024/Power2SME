package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReOrder 
{
	@JsonProperty("OrderNo")
	private String OrderNo;
	@JsonProperty("CustomerName")
	private String CustomerName;
	@JsonProperty("CustomerNumber")
	private String CustomerNumber;
	@JsonProperty("DeliveryAddress")
	private String DeliveryAddress;
	@JsonProperty("Farmer")
	private String Farmer;
	@JsonProperty("WishListLine")
	private List<SalesOrderItem> WishListLine;
	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return Utils.checkStringForNull(OrderNo);
	}
	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.OrderNo = orderNo;
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
		this.CustomerName = customerName;
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
		this.CustomerNumber = customerNumber;
	}
	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return Utils.checkStringForNull(DeliveryAddress);
	}
	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.DeliveryAddress = deliveryAddress;
	}
	/**
	 * @return the farmer
	 */
	public String getFarmer() {
		return Utils.checkStringForNull(Farmer);
	}
	/**
	 * @param farmer the farmer to set
	 */
	public void setFarmer(String farmer) {
		this.Farmer = farmer;
	}
	/**
	 * @return the createWishListLine
	 */
	public List<SalesOrderItem> getWishListLine() {
		return WishListLine;
	}
	/**
	 * @param createWishListLine the createWishListLine to set
	 */
	public void setWishListLine(List<SalesOrderItem> WishListLine) {
		this.WishListLine = WishListLine;
	}
	
	
}
