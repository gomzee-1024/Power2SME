package com.power2sme.android.sections.deliveryaddress.list;

import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.sections.IBaseView;

import java.util.List;

public interface IDeliveryAddressesListView extends IBaseView
{
	void showDeliveryAddressList(List<DeliveryAddress> deliveryAddressList);
}
