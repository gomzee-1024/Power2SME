package com.power2sme.android.sections.deliveryaddress.list;

public interface IDeliveryAddressesListPresentor
{
	void getDeliveryAddressList(String smeId);
	void deleteDeliveryAddress(String deliveryAddressCode);
}
