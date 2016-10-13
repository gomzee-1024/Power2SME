package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.dtos.response.ResponseRFQsDto;
import com.power2sme.android.sections.IBaseView;

public interface IBuyingRFQsView extends IBaseView 
{
	void showRFQs(RFQTypes rfqTypes, ResponseRFQsDto responseRFQsDto, boolean isLoadmore);
	void refreshList();
}
