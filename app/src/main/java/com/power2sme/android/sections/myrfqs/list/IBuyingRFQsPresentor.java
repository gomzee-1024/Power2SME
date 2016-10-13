package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.entities.v3.Wishlist_v3;

import java.io.File;

public interface IBuyingRFQsPresentor 
{
	void acceptQuote(Wishlist_v3 rfq, String orderNo, String smeId);
	void requestRequote(Wishlist_v3 rfq, String orderNo, String smeId);
	void getRFQs(long currentPage, long pageSize, String smeId, RFQTypes rfqTypes, boolean isLoadmore);
	void uploadPO(String rfqNo, File file, String smeId);
	void getRFQDetails(String rfqId);
}
