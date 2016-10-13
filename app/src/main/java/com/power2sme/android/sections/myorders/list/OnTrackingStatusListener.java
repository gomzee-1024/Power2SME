package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.dtos.response.ResponseTrackingStatusDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnTrackingStatusListener
{
	void onTrackingStatusStart();
	void onTrackingStatusEnd();
	void onTrackingStatusSuccess(ResponseTrackingStatusDto trackingDto);
	void onTrackingStatusError(ACError error);
}
