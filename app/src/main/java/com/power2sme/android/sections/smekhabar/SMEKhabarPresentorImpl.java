package com.power2sme.android.sections.smekhabar;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;

import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssItem;

public class SMEKhabarPresentorImpl implements ISMEKhabarPresentor,OnSMENewsRetreivalListener 
{
	Context context;
	ISMEKhabarView iSMEKhabarView;
	ISMEKhabarInteractor iSMEKhabarInteractor;
	public SMEKhabarPresentorImpl(Context context, ISMEKhabarView iSMEKhabarView)
	{
		this.context=context;
		this.iSMEKhabarView=iSMEKhabarView;
		this.iSMEKhabarInteractor=new SMEKhabarInteractorImpl(context);
	}
	@Override
	public void getSMENews(int pageNumber, boolean isLoadmore) 
	{
		iSMEKhabarInteractor.getSMENews(pageNumber, this, isLoadmore);
	}
	@Override
	public void onSMENewsRetreivalStart() 
	{
        ACLogger.log("#################### EVENT : onSMENewsRetreivalStart ####################");
		iSMEKhabarView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_NEWS);
	}
	@Override
	public void onSMENewsRetreivalEnd() 
	{
		iSMEKhabarView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_NEWS);
        ACLogger.log("#################### EVENT : onSMENewsRetreivalEnd ####################");
	}
	@Override
	public void onSMENewsRetreivalSuccess(ArrayList<RssItem> smeNews, boolean isLoadmore) 
	{
		iSMEKhabarView.showSMENews(smeNews, isLoadmore);
	}
	@Override
	public void onSMENewsRetreivalError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.sme_news_retreival_error), 
				context.getString(R.string.more_news_pages_not_found)
				);
		iSMEKhabarView.showUIMessage(uiMessage, Constants.FLAG_NEWS);
		iSMEKhabarView.showSMENews(null, false);
	}
}
