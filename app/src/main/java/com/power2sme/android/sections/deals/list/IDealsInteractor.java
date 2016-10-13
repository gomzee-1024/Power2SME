package com.power2sme.android.sections.deals.list;


public interface IDealsInteractor 
{
	void getDeals(long pageIndex, long pageSize, String filterValue, OnRetreivalDealsListener onDealsRetreivalListener, boolean isLoadmore);
}
