package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.entities.SalesOrder;

public interface IBuyingOrdersPresentor 
{
	void uploadOrder(SalesOrder salesOrder);
	void getDeliveryAddress();
	void searchOrder(String searchQuery);
	void getOrders(long currentPageIndex, long ordersPerPage, String smeId, String orderNo, OrderTypes orderTypes, boolean isLoadmore);
//	void uploadPO(File file, String fileName);
	void getTrackingStatus(String smeId);
}
