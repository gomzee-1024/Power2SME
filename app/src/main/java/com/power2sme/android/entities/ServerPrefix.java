package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerPrefix 
{
	@JsonCreator
	public ServerPrefix(){}
	
	@JsonProperty("android")
	private AndroidServerConf androidServerConf;

	public AndroidServerConf getAndroidServerConf() {
		return androidServerConf;
	}

	public void setAndroidServerConf(AndroidServerConf androidServerConf) {
		this.androidServerConf = androidServerConf;
	}
}
