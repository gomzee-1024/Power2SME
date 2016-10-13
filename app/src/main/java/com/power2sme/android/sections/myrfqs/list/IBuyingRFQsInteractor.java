package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.entities.v3.Wishlist_v3;

import java.io.File;

public interface IBuyingRFQsInteractor 
{
	void acceptQuote(Wishlist_v3 rfq, String orderNo, String smeId, OnAcceptQuoteListener onAcceptQuoteListener);
	void requestRequote(Wishlist_v3 rfq, String orderNo, String smeId, OnRequestQuoteListener onRequestQuoteListener);
	void getRFQs(long currentPage, long pageSize, String smeId, RFQTypes rfqTypes, OnRFQsRetrievalListener onRFQsRetrievalListener, boolean isLoadmore);
	void uploadPO(String rfqNo, File file, String smeId, OnPOUploadListener onPOUploadListener);
	void getRFQDetails(String rfqId, OnRFQDetailsListener onRFQDetailsListener);
}
