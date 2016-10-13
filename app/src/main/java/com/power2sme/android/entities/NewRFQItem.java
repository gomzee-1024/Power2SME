package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewRFQItem implements Parcelable
{
	@JsonProperty("Category")
	private String Category;
	@JsonProperty("SubCategory1")
	private String SubCAtegory1;
	@JsonProperty("SubCategory2")
	private String SubCAtegory2;
	@JsonProperty("SubCategory3")
	private String SubCAtegory3;
	@JsonProperty("ItemDetails")
	private String ItemDetails;
	@JsonProperty("Quantity")
	private String Quantity;
	@JsonProperty("UOM")
	private String UOM;
	@JsonProperty("Urgency")
	private String Urgency;
	@JsonProperty("Grade")
	private String Grade; 
	
	public NewRFQItem(){}
	public NewRFQItem(Parcel in)
	{
		Category = in.readString();
		SubCAtegory1 = in.readString();
		SubCAtegory2 = in.readString();
		SubCAtegory3 = in.readString();
		ItemDetails = in.readString();
		Quantity = in.readString();
		UOM = in.readString();
		Urgency = in.readString();
		Grade = in.readString();
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(Category);
		dest.writeString(SubCAtegory1);
		dest.writeString(SubCAtegory2);
		dest.writeString(SubCAtegory3);
		dest.writeString(ItemDetails);
		dest.writeString(Quantity);
		dest.writeString(UOM);
		dest.writeString(Urgency);
		dest.writeString(Grade);
	}
	public static final Creator<NewRFQItem> CREATOR = new Creator<NewRFQItem>()
 	{
 		public NewRFQItem createFromParcel(Parcel in) 
 		{
 			return new NewRFQItem(in);
 		}
 	
 		public NewRFQItem[] newArray (int size) 
 		{
 			return new NewRFQItem[size];
 		}
 	};
	///////////////////////////////////////////////

 	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return Category;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return Utils.checkStringForNull(Grade);
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		Grade = grade;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		Category = category;
	}
	/**
	 * @return the subCAtegory1
	 */
	public String getSubCAtegory1() {
		return Utils.checkStringForNull(SubCAtegory1);
	}
	/**
	 * @param subCAtegory1 the subCAtegory1 to set
	 */
	public void setSubCAtegory1(String subCAtegory1) {
		SubCAtegory1 = subCAtegory1;
	}
	/**
	 * @return the subCAtegory2
	 */
	public String getSubCAtegory2() {
		return Utils.checkStringForNull(SubCAtegory2);
	}
	/**
	 * @param subCAtegory2 the subCAtegory2 to set
	 */
	public void setSubCAtegory2(String subCAtegory2) {
		SubCAtegory2 = subCAtegory2;
	}
	/**
	 * @return the subCAtegory3
	 */
	public String getSubCAtegory3() {
		return Utils.checkStringForNull(SubCAtegory3);
	}
	/**
	 * @param subCAtegory3 the subCAtegory3 to set
	 */
	public void setSubCAtegory3(String subCAtegory3) {
		SubCAtegory3 = subCAtegory3;
	}
	/**
	 * @return the itemDetails
	 */
	public String getItemDetails() {
		return Utils.checkStringForNull(ItemDetails);
	}
	/**
	 * @param itemDetails the itemDetails to set
	 */
	public void setItemDetails(String itemDetails) {
		ItemDetails = itemDetails;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return Utils.checkStringForNull(Quantity);
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	/**
	 * @return the uOM
	 */
	public String getUOM() {
		return Utils.checkStringForNull(UOM);
	}
	/**
	 * @param uOM the uOM to set
	 */
	public void setUOM(String uOM) {
		UOM = uOM;
	}
	/**
	 * @return the urgency
	 */
	public String getUrgency() {
		return Utils.checkStringForNull(Urgency);
	}
	/**
	 * @param urgency the urgency to set
	 */
	public void setUrgency(String urgency) {
		Urgency = urgency;
	}
	
	
}
