package com.power2sme.android.sections.smekhabar;

import com.power2sme.android.sections.IBaseView;

import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssItem;

public interface ISMEKhabarView extends IBaseView
{
	void showSMENews(ArrayList<RssItem> smeNews, boolean isLoadmore);
}
