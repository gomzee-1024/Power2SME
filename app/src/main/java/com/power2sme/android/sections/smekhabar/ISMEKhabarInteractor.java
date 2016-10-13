package com.power2sme.android.sections.smekhabar;

public interface ISMEKhabarInteractor
{
	void getSMENews(int pageNumber, OnSMENewsRetreivalListener onSMENewsRetreivalListener, boolean isLoadmore);
}
