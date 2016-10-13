package com.power2sme.android.sections.deliveryaddress.insert;

import com.power2sme.android.dtos.response.ResponseDeliveryAddressInsertDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnDeliveryAddressInsertListener 
{
	void onDeliveryAddressInsertStart();
	void onDeliveryAddressInsertEnd();
	void onDeliveryAddressInsertSuccess(ResponseDeliveryAddressInsertDto responseDeliveryAddressInsertDto);
	void onDeliveryAddressInsertError(ACError error);
}
