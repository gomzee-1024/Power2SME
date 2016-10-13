package com.power2sme.android.sections.myorders.list;

public enum OrderTypes
{
	ALL("All Orders"),
	PREVIOUS_ORDERS_ONLY("Delivered Orders"),
	UNDELIVERED_ORDERS_ONLY("Un-delivered Orders");
	String value;
	private OrderTypes(String value)
	{
		this.value=value;
	}
	@Override
	public String toString() 
	{
		return ""+value;
	}
}
