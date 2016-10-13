package com.power2sme.android.dtos.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.SalesOrder;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSalesOrdersDto extends ResponseBaseDTO implements Parcelable
{
	@JsonProperty("Data")
	private List<SalesOrder> data;
	////////////////////////////////////////////////////////////
	public ResponseSalesOrdersDto(){}
	public ResponseSalesOrdersDto(Parcel in)
	{
		in.readTypedList(data, SalesOrder.CREATOR);
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
	public static final Creator<ResponseSalesOrdersDto> CREATOR = new Creator<ResponseSalesOrdersDto>()
	{
		public ResponseSalesOrdersDto createFromParcel(Parcel in) 
		{
			return new ResponseSalesOrdersDto(in);
		}
		
		public ResponseSalesOrdersDto[] newArray (int size) 
		{
			return new ResponseSalesOrdersDto[size];
		}
	};
	////////////////////////////////////////////////////////////
	public List<SalesOrder> getData() {
		return data;
	}

	public void setData(List<SalesOrder> data) {
		this.data = data;
	}
	
}
