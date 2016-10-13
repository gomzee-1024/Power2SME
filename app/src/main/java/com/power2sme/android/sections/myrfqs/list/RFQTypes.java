package com.power2sme.android.sections.myrfqs.list;


public enum RFQTypes 
{
	ALL_RFQ_ONLY("All RFQs/Quotes"),
	OPEN_RFQ_ONLY("Open RFQs"),
	PRICE_QUOTES_ONLY("Price Quotes");
	String value;
	private RFQTypes(String value)
	{
		this.value=value;
	}
	@Override
	public String toString() 
	{
		return ""+value;
	}
}
