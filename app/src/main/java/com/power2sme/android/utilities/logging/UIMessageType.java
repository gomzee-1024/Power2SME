package com.power2sme.android.utilities.logging;

public enum UIMessageType
{
	SUCCESS("SUCCESS"),
	ERROR("ERROR"),
	DEBUG("DEBUG"),
	WARNING("WARNING"),
	INFO("INFO"),
	UNAUTHORIZE("UNAUTHORIZE"),
    EMAIL_ALREADY_EXIST("EMAIL_ALREADY_EXIST"),
	SERVER_ERROR("SERVER_ERROR"),
	NETWORK_NOT_AVAILABLE("NETWORK_NOT_AVAILABLE"),
	DIALOG_OK("DIALOG_OK"),
	DIALOG_OK_BACK("DIALOG_OK_BACK");
	
	private String value;
	private UIMessageType(String value)
	{
		this.value=value;
	}
	@Override
	public String toString() 
	{
		return value;
	}
}
