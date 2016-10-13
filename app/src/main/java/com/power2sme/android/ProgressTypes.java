package com.power2sme.android;

public enum ProgressTypes 
{
	INTERACTION_NOT_ALLOWED(0),
	INTERACTION_ALLOWED(1);
	
	int value;
	private ProgressTypes(int value)
	{
		this.value=value;
	}
}