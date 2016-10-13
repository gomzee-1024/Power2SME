package com.power2sme.android.sections.myorders.shipmentdetails;

import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnOrderDetailsListener 
{
	void onOrderDetailsStart();
	void onOrderDetailsEnd();
	void onOrderDetailsSuccess(ResponseSalesOrdersDto responseSalesOrdersDto);
	void onOrderDetailsError(ACError error);
}
