package com.power2sme.android.sections.accountupdates;

import com.power2sme.android.dtos.response.ResponseGetAccountUpdatesDto;
import com.power2sme.android.sections.IBaseView;

public interface IAccountUpdatesView extends IBaseView 
{
	void showAccountUpdates(ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto, boolean isLoadmore);
}
