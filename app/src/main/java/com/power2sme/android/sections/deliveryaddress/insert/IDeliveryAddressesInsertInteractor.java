package com.power2sme.android.sections.deliveryaddress.insert;

import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.entities.State;

public interface IDeliveryAddressesInsertInteractor 
{
	void insertDeliveryAddress(DeliveryAddress deliveryAddress, OnDeliveryAddressInsertListener onDeliveryAddressInsertListener);
	void getStateList(OnStateRetreivalListener onStateRetreivalListener, boolean isSerelizable);
	void getCityList(State state, OnCityRetreivalListener onCityRetreivalListener, boolean isSerelizable);
	void updateDeliveryAddress(DeliveryAddress deliveryAddress, OnUpdateDeliveryAddressListener onUpdateDeliveryAddressListener);
}
