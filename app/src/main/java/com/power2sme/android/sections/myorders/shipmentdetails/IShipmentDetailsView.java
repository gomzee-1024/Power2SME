package com.power2sme.android.sections.myorders.shipmentdetails;

import com.power2sme.android.entities.SalesOrder;
import com.power2sme.android.entities.Shipment;
import com.power2sme.android.sections.IBaseView;

import java.util.List;

public interface IShipmentDetailsView extends IBaseView
{
	void showOrderDetails(SalesOrder salesOrder);
	void showShipmentDetails(List<Shipment> shipmentArrayList);
}
