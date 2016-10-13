package com.power2sme.android.sections.deals.list;

import com.power2sme.android.dtos.response.ResponseGetDealsDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnRetreivalDealsListener 
{
	void onRetreivalDealsStart();
	void onRetreivalDealsEnd();
	void onRetreivalDealsSuccess(String filterValue, ResponseGetDealsDto responseGetDealsDto, boolean isLoadmore);
	void onRetreivalDealsError(String filterValue, ACError error, boolean isLoadmore);
}
