package com.power2sme.android.sections.deliveryaddress.insert;

import com.power2sme.android.dtos.response.ResponseUpdateDeliveryAddressDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnUpdateDeliveryAddressListener
{
	void onUpdateDeliveryAddressStart();
	void onUpdateDeliveryAddressEnd();
	void onUpdateDeliveryAddressSuccess(ResponseUpdateDeliveryAddressDto responseUpdateDeliveryAddressDto);
	void onUpdateDeliveryAddressError(ACError error);
}
