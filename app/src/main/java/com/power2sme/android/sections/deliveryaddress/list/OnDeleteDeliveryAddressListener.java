package com.power2sme.android.sections.deliveryaddress.list;

import com.power2sme.android.dtos.response.ResponseDeleteDeliveryAddressDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnDeleteDeliveryAddressListener
{
	void onDeleteDeliveryAddressStart();
	void onDeleteDeliveryAddressEnd();
	void onDeleteDeliveryAddressSuccess(ResponseDeleteDeliveryAddressDto responseDeleteDeliveryAddressDto);
	void onDeleteDeliveryAddressError(ACError error);
}
