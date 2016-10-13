package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.dtos.response.ResponseRFQsDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnRFQDetailsListener 
{
	void onRFQDetailsStart();
	void onRFQDetailsEnd();
	void onRFQDetailsSuccess(ResponseRFQsDto responseRFQsDto);
	void onRFQDetailsError(ACError error);
}
