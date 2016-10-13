package com.power2sme.android.sections.deliveryaddress.list;

import com.power2sme.android.dtos.response.ResponseDeliveryAddressDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnDeliveryAddressListRetrievalListener 
{
	void onDeliveryAddressListRetrievalStart();
	void onDeliveryAddressListRetrievalEnd();
	void onDeliveryAddressListRetrievalSuccess(ResponseDeliveryAddressDto responseDeliveryAddressDto);
	void onDeliveryAddressListRetrievalError(ACError error);
}
