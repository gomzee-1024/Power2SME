package com.power2sme.android.sections.home;


public interface IHomeInteractor 
{
	void getOutstanding(String strsmeid, OnOutstandingLoadingListener onOutstandingLoadingListener);
	void sendAccountSummeryRequest(OnAccountSummeryRequestListener onAccountSummeryRequestListener);
}
