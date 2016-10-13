package com.power2sme.android.sections.deals.postorder;

import com.power2sme.android.dtos.request.RequestAddCampaignDto;

public interface IDealsPostOrderFragmentInteractor 
{
	void postOrder(RequestAddCampaignDto campaign, OnPostDealsOrderListener onPostDealsOrderListener);
	void getDealById(String dealId, OnGetDealsByIDListener onGetDealsByIDListener);
}
