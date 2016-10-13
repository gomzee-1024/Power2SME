package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitOfMeasure  implements Serializable, Parcelable
{
	@JsonProperty("Description")
	private String UnitOfMeasureID;
	
	
	@JsonProperty("Code")
	private String UnitOfMeasureName;
	
	public UnitOfMeasure(){}
	public UnitOfMeasure(Parcel in)
	{
		UnitOfMeasureID = in.readString();
		UnitOfMeasureName = in.readString();
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(UnitOfMeasureID);
		dest.writeString(UnitOfMeasureName);
	}
	public static final Creator<UnitOfMeasure> CREATOR = new Creator<UnitOfMeasure>()
 	{
 		public UnitOfMeasure createFromParcel(Parcel in) 
 		{
 			return new UnitOfMeasure(in);
 		}
 	
 		public UnitOfMeasure[] newArray (int size) 
 		{
 			return new UnitOfMeasure[size];
 		}
 	};
 	
	@Override
	public String toString() 
	{
		return getUnitOfMeasureName();
	}
	@Override
	public boolean equals(Object o) 
	{
		if(o instanceof UnitOfMeasure && ((UnitOfMeasure)o).toString().equals(this.toString()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	///////////////////////////////////////////////////////////////////////
	/**
	 * @return the unitOfMeasureID
	 */
	public String getUnitOfMeasureID() {
		return Utils.checkStringForNull(UnitOfMeasureID);	}
	/**
	 * @param unitOfMeasureID the unitOfMeasureID to set
	 */
	public void setUnitOfMeasureID(String unitOfMeasureID) {
		UnitOfMeasureID = unitOfMeasureID;
	}
	/**
	 * @return the unitOfMeasureName
	 */
	public String getUnitOfMeasureName() {
		return Utils.checkStringForNull(UnitOfMeasureName);
	}
	/**
	 * @param unitOfMeasureName the unitOfMeasureName to set
	 */
	public void setUnitOfMeasureName(String unitOfMeasureName) {
		UnitOfMeasureName = unitOfMeasureName;
	}
	
	
}
