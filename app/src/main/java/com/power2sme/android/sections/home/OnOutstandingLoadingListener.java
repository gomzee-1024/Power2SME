package com.power2sme.android.sections.home;

import com.power2sme.android.dtos.response.ResponseOutstandingDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnOutstandingLoadingListener 
{
	void onOutstandingLoadingStart();
	void onOutstandingLoadingEnd();
	void onOutstandingLoadingSuccess(ResponseOutstandingDto responseOutstandingDto);
	void onOutstandingLoadingError(ACError error);
}
