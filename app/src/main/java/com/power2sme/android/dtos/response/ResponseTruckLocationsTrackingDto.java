package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTruckLocationsTrackingDto
{
	@JsonProperty("error")
	private String error;
	@JsonProperty("error_message")
	private String error_message;
	@JsonProperty("invocie_number")
	private String invocie_number;
	@JsonProperty("current_lat")
	private String current_lat;
	@JsonProperty("current_long")
	private String current_long;
	@JsonProperty("current_address")
	private String current_address;
	@JsonProperty("start_lat")
	private String start_lat;
	@JsonProperty("start_long")
	private String start_long;
	@JsonProperty("start_address")
	private String start_address;
	@JsonProperty("end_lat")
	private String end_lat;
	@JsonProperty("end_long")
	private String end_long;
	@JsonProperty("end_address")
	private String end_address;

	public String getInvocie_number() {
		return invocie_number;
	}

	public double getCurrent_lat()
	{
		try
		{
			return current_lat != null && current_lat.length() > 0 ? Double.parseDouble(current_lat) : 0d;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}

	public double getCurrent_long()
	{
		try
		{
			return current_long != null && current_long.length() > 0 ? Double.parseDouble(current_long) : 0d;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}

	public String getCurrent_address() {
		return current_address;
	}

	public double getStart_lat() {
		try
		{
			return start_lat != null && start_lat.length() > 0 ? Double.parseDouble(start_lat) : 0d;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}

	public double getStart_long()
	{
		try
		{
			return start_long != null && start_long.length() > 0 ? Double.parseDouble(start_long) : 0d;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}

	public String getStart_address() {
		return start_address;
	}

	public double getEnd_lat() {
		try
		{
			return end_lat != null && end_lat.length() > 0 ? Double.parseDouble(end_lat) : 0d;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}

	public double getEnd_long() {
		try
		{
			return end_long != null && end_long.length() > 0 ? Double.parseDouble(end_long) : 0d;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}

	public String getEnd_address() {
		return end_address;
	}

	public String getError() {
		return error;
	}

	public String getError_message() {
		return error_message;
	}
}
