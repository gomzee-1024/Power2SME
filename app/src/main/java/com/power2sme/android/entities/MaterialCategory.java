package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialCategory  implements Serializable, Parcelable
{
	@JsonProperty("ItemCategoryUom")
	private List<UnitOfMeasure> ItemCategoryUom;
	@JsonProperty("ItemCategoryId")
	private String ItemCategoryId;
	@JsonProperty("ItemCategoryName")
	private String ItemCategoryName;
	
	public MaterialCategory(){}
	public MaterialCategory(Parcel in)
	{
		ItemCategoryUom = new ArrayList<UnitOfMeasure>();

		in.readTypedList(ItemCategoryUom, UnitOfMeasure.CREATOR);
		ItemCategoryId = in.readString();
		ItemCategoryName = in.readString();
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeTypedList(ItemCategoryUom);
		dest.writeString(ItemCategoryId);
		dest.writeString(ItemCategoryName);
	}
	public static final Creator<MaterialCategory> CREATOR = new Creator<MaterialCategory>()
 	{
 		public MaterialCategory createFromParcel(Parcel in) 
 		{
 			return new MaterialCategory(in);
 		}
 	
 		public MaterialCategory[] newArray (int size) 
 		{
 			return new MaterialCategory[size];
 		}
 	};
 	
 	@Override	
 	public boolean equals(Object o) 
 	{
 		if((o instanceof MaterialCategory) && ((MaterialCategory)o).toString().equals(this.toString()))
 		{
 			return true;
 		}
 		else
 		{
 			return false;
 		}
 	}
 	///////////////////////////////////////
	
	/**
	 * @return the itemCategoryUom
	 */
	public List<UnitOfMeasure> getItemCategoryUom() {
		return ItemCategoryUom;
	}

	/**
	 * @param itemCategoryUom the itemCategoryUom to set
	 */
	public void setItemCategoryUom(List<UnitOfMeasure> itemCategoryUom) {
		ItemCategoryUom = itemCategoryUom;
	}

	@Override
	public String toString() 
	{
		return getItemCategoryName();
	}
	
	/**
	 * @return the itemCategoryId
	 */
	public String getItemCategoryId() {
		return Utils.checkStringForNull(ItemCategoryId);
	}
	/**
	 * @param itemCategoryId the itemCategoryId to set
	 */
	public void setItemCategoryId(String itemCategoryId) {
		ItemCategoryId = itemCategoryId;
	}
	/**
	 * @return the itemCategoryName
	 */
	public String getItemCategoryName() {
		return Utils.checkStringForNull(ItemCategoryName);}
	/**
	 * @param itemCategoryName the itemCategoryName to set
	 */
	public void setItemCategoryName(String itemCategoryName) {
		ItemCategoryName = itemCategoryName;
	}
	
}
