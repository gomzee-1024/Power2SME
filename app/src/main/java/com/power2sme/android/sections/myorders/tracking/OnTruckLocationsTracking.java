package com.power2sme.android.sections.myorders.tracking;

import com.power2sme.android.dtos.response.ResponseTruckLocationsTrackingDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnTruckLocationsTracking 
{
	void onTruckLocationsTrackingStart();
	void onTruckLocationsTrackingEnd();
	void onTruckLocationsTrackingSuccess(ResponseTruckLocationsTrackingDto ResponseTruckLocationsTrackingDto);
	void onTruckLocationsTrackingError(ACError error);
}
