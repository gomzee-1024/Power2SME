package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.dtos.response.ResponseTrackingStatusDto;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.entities.SalesOrder;
import com.power2sme.android.sections.IBaseView;

import java.util.List;

public interface IBuyingOrdersView extends IBaseView
{
	void uploadOrder(SalesOrder salesOrder);
	void showDeliveryAddress(List<DeliveryAddress> deliveryAddressList);
	void searchOrder(List<SalesOrder> salesOrdersList);
	void showOrders(ResponseSalesOrdersDto responseSalesOrdersDto, boolean isLoadmore);
	void showTrackingStatus(ResponseTrackingStatusDto trackingDto);
}
