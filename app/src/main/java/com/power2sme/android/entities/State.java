package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class State  implements Serializable
{
	@JsonProperty("StateName")
	private String stateName;
	@JsonProperty("Stateid")
	private long stateId;
	
	public String getStateName() {
		return Utils.checkStringForNull(stateName);
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	
	@Override
	public String toString() 
	{
		return getStateName();
	}
	
}
