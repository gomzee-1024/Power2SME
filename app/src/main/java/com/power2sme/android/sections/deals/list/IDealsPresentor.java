package com.power2sme.android.sections.deals.list;

public interface IDealsPresentor
{
	void getDeals(long pageIndex, long pageSize, String filterValue, boolean isLoadmore);
}
