package com.power2sme.android.sections.deliveryaddress.list;

public interface IDeliveryAddressesListInteractor
{
	void getDeliveryAddressList(String smeId, OnDeliveryAddressListRetrievalListener onDeliveryAddressRetrievalListener);
	void deleteDeliveryAddress(String deliveryAddressCode, OnDeleteDeliveryAddressListener onDeleteDeliveryAddressListener);
}
