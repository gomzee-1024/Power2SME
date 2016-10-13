package com.power2sme.android.sections.home;

import com.power2sme.android.dtos.response.ResponseAccountSummeryDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnAccountSummeryRequestListener 
{
	void onAccountSummeryRequestStart();
	void onAccountSummeryRequestEnd();
	void onAccountSummeryRequestSuccess(ResponseAccountSummeryDto responseAccountSummeryDto);
	void onAccountSummeryRequestError(ACError error);
}
