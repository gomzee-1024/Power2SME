package com.power2sme.android.sections.myorders.shipmentdetails;


public interface IShipmentDetailsInteractor
{
	void getOrderDetails(String orderID, OnOrderDetailsListener onOrderDetailsListener);
	void getShipmentDetails(String orderId, OnShipmentDetailsListener onShipmentDetailsListener);
}
