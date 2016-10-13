package com.power2sme.android.sections.myorders.shipmentdetails;

import com.power2sme.android.dtos.response.ResponseShipmentDetailsDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnShipmentDetailsListener 
{
	void onShipmentDetailsStart();
	void onShipmentDetailsEnd();
	void onShipmentDetailsSuccess(ResponseShipmentDetailsDto responseShipmentDetailsDto);
	void onShipmentDetailsError(ACError error);
}
