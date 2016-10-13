package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class IsRegisterdInERP
{
	@JsonProperty("IsRegisteredInErp")
	boolean IsRegisteredInErp;

	/**
	 * @return the isRegisterdInERP
	 */
	public boolean isIsRegisterdInERP() {
		return IsRegisteredInErp;
	}

	/**
	 * @param isRegisterdInERP the isRegisterdInERP to set
	 */
	public void setIsRegisterdInERP(boolean IsRegisteredInErp) {
		IsRegisteredInErp = IsRegisteredInErp;
	}
	
	
}
