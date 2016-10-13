package com.power2sme.android.sections.accountupdates;

public interface IAccountUpdatesInteractor
{
	void getAccountUpdates(long currentPage, long PageSize, String strsmeid, boolean isLoadmore, OnAccountUpdatesRetrievalListenere onAccountUpdatesRetrievalListenere);
}
