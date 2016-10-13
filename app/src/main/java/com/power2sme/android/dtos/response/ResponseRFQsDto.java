package com.power2sme.android.dtos.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.Wishlist_v3;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseRFQsDto extends ResponseBaseDTO implements Parcelable
{
	@JsonProperty("Data")
	private List<Wishlist_v3> data;

	////////////////////////////////////////////////////////////
	public ResponseRFQsDto(){}
	public ResponseRFQsDto(Parcel in)
	{
		in.readTypedList(data, Wishlist_v3.CREATOR);
	}
	@Override
	public int describeContents() 
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeTypedList(data);
	}
	public static final Creator<ResponseRFQsDto> CREATOR = new Creator<ResponseRFQsDto>()
	{
		public ResponseRFQsDto createFromParcel(Parcel in) 
		{
			return new ResponseRFQsDto(in);
		}
		
		public ResponseRFQsDto[] newArray (int size) 
		{
			return new ResponseRFQsDto[size];
		}
	};
	////////////////////////////////////////////////////////////

	public List<Wishlist_v3> getData() {
		return data;
	}

	public void setData(List<Wishlist_v3> data) {
		this.data = data;
	}
}
