package com.power2sme.android.sections.deals.postorder;

import com.power2sme.android.dtos.request.RequestAddCampaignDto;

public interface IDealsPostOrderFragmentPresentor 
{
	void postOrder(RequestAddCampaignDto campaign);
	void getDealById(String dealId);
}
