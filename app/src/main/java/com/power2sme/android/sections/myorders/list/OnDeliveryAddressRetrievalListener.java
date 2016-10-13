package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.dtos.response.ResponseDeliveryAddressDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnDeliveryAddressRetrievalListener 
{
	void onDeliveryAddressRetrievalStart();
	void onDeliveryAddressRetrievalEnd();
	void onDeliveryAddressRetrievalSuccess(ResponseDeliveryAddressDto responseDeliveryAddressDto);
	void onDeliveryAddressRetrievalError(ACError error);
}
