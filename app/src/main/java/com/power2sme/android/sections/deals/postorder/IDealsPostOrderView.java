package com.power2sme.android.sections.deals.postorder;

import com.power2sme.android.dtos.request.RequestAddCampaignDto;
import com.power2sme.android.entities.v3.Deal_v3;
import com.power2sme.android.sections.IBaseView;

public interface IDealsPostOrderView extends IBaseView 
{
	void postOrder(RequestAddCampaignDto campaign);
	void showDeal(Deal_v3 deal);
}
