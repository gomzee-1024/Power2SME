package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Designation  implements Serializable
{
	@JsonProperty("DesignationId")
	private String DesignationId;
	@JsonProperty("DesignationName")
	private String DesignationName;
	
	@Override
	public String toString() 
	{
		return getDesignationName();
	}
	
	@Override
	public boolean equals(Object o) 
	{
		if(o instanceof Designation && ((Designation)o).toString().equals(this.toString()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	////////////////////////////////////////////////////////////////////
	/**
	 * @return the designationId
	 */
	public String getDesignationId() {
		return Utils.checkStringForNull(DesignationId);
	}
	/**
	 * @param designationId the designationId to set
	 */
	public void setDesignationId(String designationId) {
		DesignationId = designationId;
	}
	/**
	 * @return the designationName
	 */
	public String getDesignationName() {
		return Utils.checkStringForNull(DesignationName);
	}
	/**
	 * @param designationName the designationName to set
	 */
	public void setDesignationName(String designationName) {
		DesignationName = designationName;
	}
	
	
}
