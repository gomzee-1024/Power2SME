package com.power2sme.android.dtos.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.Update;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGetAccountUpdatesDto extends ResponseBaseDTO implements Parcelable
{
	@JsonProperty("Data")
	private List<Update> data;
	////////////////////////////////////////////////////////////
	public ResponseGetAccountUpdatesDto(){}
	public ResponseGetAccountUpdatesDto(Parcel in)
	{
		in.readTypedList(data, Update.CREATOR);
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
	public static final Creator<ResponseGetAccountUpdatesDto> CREATOR = new Creator<ResponseGetAccountUpdatesDto>()
	{
		public ResponseGetAccountUpdatesDto createFromParcel(Parcel in) 
		{
			return new ResponseGetAccountUpdatesDto(in);
		}
		
		public ResponseGetAccountUpdatesDto[] newArray (int size) 
		{
			return new ResponseGetAccountUpdatesDto[size];
		}
	};
	////////////////////////////////////////////////////////////
	public List<Update> getData() 
	{
		return data;
	}

	public void setData(List<Update> data) 
	{
		this.data = data;
	}
}
