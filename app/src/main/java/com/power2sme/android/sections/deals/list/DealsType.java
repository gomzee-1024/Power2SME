package com.power2sme.android.sections.deals.list;

public enum DealsType
{
//	ALL("all"),
	ACTIVE("active"),
	UPCOMING("upcoming"),
	EXPIRED("expired");
	private String value;
	private DealsType(String value)
	{
		this.value=value;
	}
	@Override
	public String toString() 
	{
		return value;
	}
}
