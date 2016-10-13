package com.power2sme.android.sections.accountupdates;

import com.power2sme.android.dtos.response.ResponseGetAccountUpdatesDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnAccountUpdatesRetrievalListenere 
{
	void onAccountUpdatesRetrievalStart();
	void onAccountUpdatesRetrievalEnd();
	void onAccountUpdatesRetrievalSuccess(ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto, boolean isLoadmore);
	void onAccountUpdatesRetrievalError(ACError error);
}
