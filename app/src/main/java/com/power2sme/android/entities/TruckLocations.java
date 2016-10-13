package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TruckLocations 
{
	@JsonProperty("msg")
	private String title;
	
	@JsonProperty("lat")
	private String lattitude;
	
	@JsonProperty("long")
	private String longitude;
	
	
	public String getTitle() {
		return Utils.checkStringForNull(title);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getLattitude() 
	{
		try
		{
			if(lattitude!=null && lattitude.length()>0)
			{
				return Double.parseDouble(lattitude);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}
	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongitude() 
	{
		try
		{
			if(longitude!=null && longitude.length()>0)
			{
				return Double.parseDouble(longitude);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return 0d;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
}
