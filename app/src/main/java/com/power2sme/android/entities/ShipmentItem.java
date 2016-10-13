package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentItem 
{
	@JsonProperty("Description")
	private String Description;
	@JsonProperty("Quantity")
	private long Quantity;
	@JsonProperty("TotalAmount")
	private double TotalAmount;
	@JsonProperty("UnitPrice")
	private double UnitPrice;
	@JsonProperty("UnitofMeasure")
	private String UnitofMeasure;
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Utils.checkStringForNull(Description);
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/**
	 * @return the quantity
	 */
	public long getQuantity() {
		return Quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(long quantity) {
		Quantity = quantity;
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
	 * @return the unitPrice
	 */
	public double getUnitPrice() {
		return UnitPrice;
	}
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	/**
	 * @return the unitofMeasure
	 */
	public String getUnitofMeasure() {
		return Utils.checkStringForNull(UnitofMeasure);
	}
	/**
	 * @param unitofMeasure the unitofMeasure to set
	 */
	public void setUnitofMeasure(String unitofMeasure) {
		UnitofMeasure = unitofMeasure;
	}
	
	
}
