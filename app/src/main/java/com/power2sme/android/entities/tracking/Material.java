package com.power2sme.android.entities.tracking;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Material implements Parcelable
{
	@JsonProperty("material")
	private String material;
	@JsonProperty("quantity")
	private String quantity;
	@JsonProperty("units")
	private String units;

	public Material(){}
	public Material(Parcel in)
	{
		material = in.readString();
		quantity = in.readString();
		units = in.readString();
	}
	@Override
	public int describeContents()
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(material);
		dest.writeString(quantity);
		dest.writeString(units);
	}

	public static final Creator<Material> CREATOR = new Creator<Material>()
 	{
 		public Material createFromParcel(Parcel in)
 		{
 			return new Material(in);
 		}

 		public Material[] newArray (int size)
 		{
 			return new Material[size];
 		}
 	};

	public String getMaterial() {
		return material;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getUnits() {
		return units;
	}
}
