package com.power2sme.android.sections.smekhabar;

import com.power2sme.android.utilities.logging.ACError;

import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssItem;

public interface OnSMENewsRetreivalListener 
{
	void onSMENewsRetreivalStart();
	void onSMENewsRetreivalEnd();
	void onSMENewsRetreivalSuccess(ArrayList<RssItem> smeNews, boolean isLoadmore);
	void onSMENewsRetreivalError(ACError error);
}
