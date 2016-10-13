package com.power2sme.android.sections.deals.postorder;

import com.power2sme.android.dtos.response.ResponseGetDealsByIDDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnGetDealsByIDListener 
{
	void onGetDealsByIDStart();
	void onGetDealsByIDEnd();
	void onGetDealsByIDSuccess(ResponseGetDealsByIDDto responseGetDealsByIDDto);
	void onGetDealsByIDError(ACError error);
}
