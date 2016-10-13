package com.power2sme.android.sections.deliveryaddress.insert;

import com.power2sme.android.dtos.response.ResponseGetStatesDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnStateRetreivalListener 
{
	void onStateRetreivalStart();
	void onStateRetreivalEnd();
	void onStateRetreivalSuccess(ResponseGetStatesDto responseGetStatesDto, boolean isSerelizable);
	void onStateRetreivalError(ACError error);
}
