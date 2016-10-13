package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.dtos.response.ResponseRFQsDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnRFQsRetrievalListener 
{
	void onRFQsRetrievalStart();
	void onRFQsRetrievalEnd();
	void onRFQsRetrievalSuccess(RFQTypes rfqTypes, ResponseRFQsDto responseRFQsDto, boolean isLoadmore);
	void onRFQsRetrievalError(ACError error);
}
