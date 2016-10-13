package com.power2sme.android.sections.deliveryaddress.insert;

import com.power2sme.android.dtos.response.ResponseGetCitiesDto;
import com.power2sme.android.entities.State;
import com.power2sme.android.utilities.logging.ACError;

public interface OnCityRetreivalListener 
{
	void onCityRetreivalStart();
	void onCityRetreivalEnd();
	void onCityRetreivalSuccess(ResponseGetCitiesDto responseGetCitiesDto, State state, boolean isSerelizable);
	void onCityRetreivalError(ACError error);
}
