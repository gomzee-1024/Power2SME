package com.power2sme.android.entities.tracking;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Parcelable
{
	@JsonProperty("order_number")
	private String order_number;
	@JsonProperty("invoices")
	private List<Invoice> invoices;

	public Order(){}
	public Order(Parcel in)
	{
		order_number = in.readString();
		in.readTypedList(invoices, Invoice.CREATOR);
	}
	@Override
	public int describeContents()
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(order_number);
		dest.writeTypedList(invoices);
	}
	public static final Creator<Order> CREATOR = new Creator<Order>()
 	{
 		public Order createFromParcel(Parcel in)
 		{
 			return new Order(in);
 		}

 		public Order[] newArray (int size)
 		{
 			return new Order[size];
 		}
 	};

	public String getOrder_number() {
		return Utils.checkStringForNull(order_number);
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}
}
