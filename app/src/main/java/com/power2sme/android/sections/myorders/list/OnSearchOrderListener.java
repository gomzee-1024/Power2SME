package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnSearchOrderListener 
{
	void onSearchOrderStart();
	void onSearchOrderEnd();
	void onSearchOrderSuccess(OrderTypes orderTypes, ResponseSalesOrdersDto responseSalesOrdersDto);
	void onSearchOrderError(ACError error, String orderID);
}
