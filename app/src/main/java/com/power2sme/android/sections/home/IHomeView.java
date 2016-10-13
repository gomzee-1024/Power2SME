package com.power2sme.android.sections.home;

import com.power2sme.android.entities.Outstanding;
import com.power2sme.android.sections.IBaseView;

public interface IHomeView extends IBaseView 
{
	void navigateToAccountUpdates();
	void navigateToDeals();
//	void navigateToNews();
	void navigateToNewRFQ();
	void showOutstanding(Outstanding data);
}
