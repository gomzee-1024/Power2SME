package com.power2sme.android.sections.deals.postorder;

import com.power2sme.android.dtos.response.ResponseAddCampaignDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnPostDealsOrderListener 
{
	void onPostDealsOrderStart();
	void onPostDealsOrderEnd();
	void onPostDealsOrderSuccess(ResponseAddCampaignDto responseAddCampaignDto);
	void onPostDealsOrderError(ACError error);
}
