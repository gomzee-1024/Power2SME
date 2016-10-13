package com.power2sme.android.sections.myrfqs.add;

public enum NewRFQTypes 
{
	iRFQ(1), DEALS(2), REORDER(3);
	int value;
	private NewRFQTypes(int value)
	{
		this.value=value;
	}
	public int getValue()
	{
		return value;
	}
}
