package com.power2sme.android.sections.deals.list;


public enum DealFilters 
{
//	ALL(R.string.deals_all_filter),
	ACTIVE("Active Deals");
//	UPCOMING("Upcoming Deals"),
//	EXPIRED("Expired Deals");
	
	private String value;
	private DealFilters(String value)
	{
		this.value=value;
	}
	@Override
	public String toString() 
	{
		return value;
	}
}
