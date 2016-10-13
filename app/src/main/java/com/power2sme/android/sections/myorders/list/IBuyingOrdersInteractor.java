package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.entities.SalesOrder;

public interface IBuyingOrdersInteractor
{
	void uploadOrder(SalesOrder salesOrder, OnPostReOrderListener onUploadOrderListener);
	void getDeliveryAddress(OnDeliveryAddressRetrievalListener onDeliveryAddressRetrievalListener);
	void searchOrder(final long currentPageIndex, final long ordersPerPage, final String smeId, final String orderNo, final OrderTypes orderTypes, OnSearchOrderListener onSearchOrderListener);
	void getOrders(long currentPageIndex, long ordersPerPage, String smeId, String orderNo, OrderTypes orderTypes, OnOrderRetrievalListener onOrderRetrievalListener, boolean isLoadmore);
//	void uploadPO(File file, String fileName, OnUploadPOListener onUploadPOListener);
	void getTrackingStatus(String smeId, OnTrackingStatusListener onTrackingStatusListener);
}
