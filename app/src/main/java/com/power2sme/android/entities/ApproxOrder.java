package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApproxOrder  implements Serializable
{
	@JsonProperty("Value")
	private String Value;
	@JsonProperty("Key")
	private String Key;
	/**
	 * @return the value
	 */
	public String getValue() {
		return Utils.checkStringForNull(Value);
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		Value = value;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return Utils.checkStringForNull(Key);
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		Key = key;
	}
	
	
}
