package com.power2sme.android.sections.accountupdates;

public interface IAccountUpdatesPresentor
{
	void getAccountUpdates(long currentPage, long PageSize, String strsmeid, boolean isLoadmore);
}
