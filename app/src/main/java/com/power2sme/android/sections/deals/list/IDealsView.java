package com.power2sme.android.sections.deals.list;

import com.power2sme.android.dtos.response.ResponseGetDealsDto;
import com.power2sme.android.entities.v3.Deal_v3;
import com.power2sme.android.sections.IBaseView;

public interface IDealsView extends IBaseView
{
	void showDeals(String filterValue, ResponseGetDealsDto responseGetDealsDto, boolean isLoadmore);
	void navigateToPostOrder(Deal_v3 deal);
}
