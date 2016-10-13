package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnOrderRetrievalListener 
{
	void onOrderRetrievalStart();
	void onOrderRetrievalEnd();
	void onOrderRetrievalSuccess(OrderTypes orderTypes, ResponseSalesOrdersDto responseSalesOrdersDto, boolean isLoadmore);
	void onOrderRetrievalError(ACError error);
}
