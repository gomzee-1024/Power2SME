package com.power2sme.android.sections.myrfqs.add;

import com.power2sme.android.dtos.response.ResponseAddNewRFQDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnAddRFQListener 
{
	void onAddRFQStart();
	void onAddRFQEnd();
	void onAddRFQSuccess(ResponseAddNewRFQDto responseAddNewRFQDto);
	void onAddRFQError(ACError error);
}
