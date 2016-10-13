package com.power2sme.android.entities.tracking;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice implements Parcelable
{
	@JsonProperty("invoice_number")
	private String invoice_number;
	@JsonProperty("vehicle_number")
	private String vehicle_number;
	@JsonProperty("driver_name")
	private String driver_name;
	@JsonProperty("driver_number")
	private String driver_number;
	@JsonProperty("materials")
	private List<Material> materials;
	@JsonProperty("current_address")
	private String current_address;
	@JsonProperty("estimated_schedule")
	private EstimatedSchedule estimated_schedule;
	@JsonProperty("shipment_time")
	private ShipmentTime shipment_time;
	@JsonProperty("download_link")
	private String download_link;

	public Invoice(){}
	public Invoice(Parcel in)
	{
		invoice_number = in.readString();
		vehicle_number = in.readString();
		driver_name = in.readString();
		driver_number = in.readString();
		in.readTypedList(materials, Material.CREATOR);
		current_address = in.readString();
		estimated_schedule = in.readParcelable(EstimatedSchedule.class.getClassLoader());
		shipment_time = in.readParcelable(ShipmentTime.class.getClassLoader());
		download_link = in.readString();

	}
	@Override
	public int describeContents()
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(invoice_number);
		dest.writeString(vehicle_number);
		dest.writeString(driver_name);
		dest.writeString(driver_number);
		dest.writeTypedList(materials);
		dest.writeString(current_address);
		dest.writeParcelable(estimated_schedule, flags);
		dest.writeParcelable(shipment_time, flags);
		dest.writeString(download_link);
	}
	public static final Creator<Invoice> CREATOR = new Creator<Invoice>()
 	{
 		public Invoice createFromParcel(Parcel in)
 		{
 			return new Invoice(in);
 		}

 		public Invoice[] newArray (int size)
 		{
 			return new Invoice[size];
 		}
 	};

	public String getInvoice_number() {
		return invoice_number;
	}

	public String getVehicle_number() {
		return vehicle_number;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public String getDriver_number() {
		return driver_number;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public String getCurrent_address() {
		return current_address;
	}

	public EstimatedSchedule getEstimated_schedule() {
		return estimated_schedule;
	}

	public ShipmentTime getShipment_time() {
		return shipment_time;
	}

	public String getDownload_link() {
		return download_link;
	}
}
