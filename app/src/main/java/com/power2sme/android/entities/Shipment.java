package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipment 
{
	@JsonProperty("Data")
	private List<ShipmentItem> Data;
	@JsonProperty("DriverName")
	private String DriverName;
	@JsonProperty("DriverMobile")
	private String DriverNumber;
	@JsonProperty("DueDate")
	private String DueDate;
	@JsonProperty("InvoiceNumber")
	private String InvoiceNumber;
	@JsonProperty("LRRRDate")
	private String LRRRDate;
	@JsonProperty("OnHold")
	private String OnHold;
	@JsonProperty("OrderNo")
	private String OrderNumber;
	@JsonProperty("OutstandingAmount")
	private String OutstandingAmount;
	@JsonProperty("ShipToAddress")
	private String ShipToAddress;
	@JsonProperty("ShipToAddress2")
	private String ShipToAddress2;
	@JsonProperty("ShipToCity")
	private String ShipToCity;
	@JsonProperty("ShipToName")
	private String ShipToName;
	@JsonProperty("ShipmentDate")
	private String ShipmentDate;
	@JsonProperty("ShipmentTime")
	private String ShipmentTime;
	@JsonProperty("TotalAmount")
	private double TotalAmount;
	@JsonProperty("VehicleNumber")
	private String VehicleNumber;
	/**
	 * @return the data
	 */
	public List<ShipmentItem> getData() {
		return Data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<ShipmentItem> data) {
		Data = data;
	}
	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return Utils.checkStringForNull(DriverName);
	}
	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		DriverName = driverName;
	}
	/**
	 * @return the driverNumber
	 */
	public String getDriverNumber() {
		return Utils.checkStringForNull(DriverNumber);
	}
	/**
	 * @param driverNumber the driverNumber to set
	 */
	public void setDriverNumber(String driverNumber) {
		DriverNumber = driverNumber;
	}
	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return Utils.checkStringForNull(DueDate);
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return Utils.checkStringForNull(InvoiceNumber);
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}
	/**
	 * @return the lRRRDate
	 */
	public String getLRRRDate() {
		return Utils.checkStringForNull(LRRRDate);
	}
	/**
	 * @param lRRRDate the lRRRDate to set
	 */
	public void setLRRRDate(String lRRRDate) {
		LRRRDate = lRRRDate;
	}
	/**
	 * @return the onHold
	 */
	public String getOnHold() {
		return Utils.checkStringForNull(OnHold);
	}
	/**
	 * @param onHold the onHold to set
	 */
	public void setOnHold(String onHold) {
		OnHold = onHold;
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
		OrderNumber = orderNumber;
	}
	/**
	 * @return the outstandingAmount
	 */
	public double getOutstandingAmount() {
		return Double.parseDouble(OutstandingAmount);
	}
	/**
	 * @param outstandingAmount the outstandingAmount to set
	 */
	public void setOutstandingAmount(double outstandingAmount) {
		OutstandingAmount = String.valueOf(outstandingAmount);
	}
	/**
	 * @return the shipToAddress
	 */
	public String getShipToAddress() {
		return Utils.checkStringForNull(ShipToAddress);
	}
	/**
	 * @param shipToAddress the shipToAddress to set
	 */
	public void setShipToAddress(String shipToAddress) {
		ShipToAddress = shipToAddress;
	}
	/**
	 * @return the shipToAddress2
	 */
	public String getShipToAddress2() {
		return Utils.checkStringForNull(ShipToAddress2);
	}
	/**
	 * @param shipToAddress2 the shipToAddress2 to set
	 */
	public void setShipToAddress2(String shipToAddress2) {
		ShipToAddress2 = shipToAddress2;
	}
	/**
	 * @return the shipToCity
	 */
	public String getShipToCity() {
		return Utils.checkStringForNull(ShipToCity);
	}
	/**
	 * @param shipToCity the shipToCity to set
	 */
	public void setShipToCity(String shipToCity) {
		ShipToCity = shipToCity;
	}
	/**
	 * @return the shipToName
	 */
	public String getShipToName() {
		return Utils.checkStringForNull(ShipToName);
	}
	/**
	 * @param shipToName the shipToName to set
	 */
	public void setShipToName(String shipToName) {
		ShipToName = shipToName;
	}
	/**
	 * @return the shipmentDate
	 */
	public String getShipmentDate() {
		return Utils.checkStringForNull(ShipmentDate);
	}
	/**
	 * @param shipmentDate the shipmentDate to set
	 */
	public void setShipmentDate(String shipmentDate) {
		ShipmentDate = shipmentDate;
	}
	/**
	 * @return the shipmentTime
	 */
	public String getShipmentTime() {
		return Utils.checkStringForNull(ShipmentTime);
	}
	/**
	 * @param shipmentTime the shipmentTime to set
	 */
	public void setShipmentTime(String shipmentTime) {
		ShipmentTime = shipmentTime;
	}
	/**
	 * @return the totalAmount
	 */
	public double getTotalAmount() {
		return TotalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		TotalAmount = totalAmount;
	}
	/**
	 * @return the vehicleNumber
	 */
	public String getVehicleNumber() {
		return Utils.checkStringForNull(VehicleNumber);
	}
	/**
	 * @param vehicleNumber the vehicleNumber to set
	 */
	public void setVehicleNumber(String vehicleNumber) {
		VehicleNumber = vehicleNumber;
	}
	
}
