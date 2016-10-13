package com.power2sme.android.sections.smekhabar;

import android.content.Context;
import android.os.AsyncTask;

import com.power2sme.android.R;
import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseSMESignupDto;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;

import java.io.InputStream;
import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class SMEKhabarInteractorImpl implements ISMEKhabarInteractor 
{
	Context context;
	public SMEKhabarInteractorImpl(Context context)
	{
		this.context=context;
	}
	@Override
	public void getSMENews(int pageNumber, OnSMENewsRetreivalListener onSMENewsRetreivalListener, boolean isLoadmore) 
	{
		new RRDDownloader(pageNumber, onSMENewsRetreivalListener, isLoadmore).execute();
	}
	private class RRDDownloader extends AsyncTask<Void, Void, ArrayList<RssItem>>
    {
		OnSMENewsRetreivalListener onSMENewsRetreivalListener;
		int pageNumber;
		boolean isLoadmore;
		public RRDDownloader(int pageNumber, OnSMENewsRetreivalListener onSMENewsRetreivalListener, boolean isLoadmore)
		{
			this.onSMENewsRetreivalListener=onSMENewsRetreivalListener;
			this.pageNumber=pageNumber;
			this.isLoadmore=isLoadmore;
		}
		@Override
		protected void onPreExecute() 
		{
			onSMENewsRetreivalListener.onSMENewsRetreivalStart();
			super.onPreExecute();
		}
		@Override
		protected ArrayList<RssItem> doInBackground(Void... params) 
		{
			ArrayList<RssItem> rssItems=null;
			try
	        {
				NetworkAsyncTaskResponse<ResponseSMESignupDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					return null;
				}

				InputStream is = factoryResponse.getiDataProvider().getNewsFeeds(pageNumber);
				RssFeed feed = RssReader.read(is);
	            rssItems = feed.getRssItems();
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
			return rssItems;
		}
		@Override
		protected void onPostExecute(ArrayList<RssItem> result) 
		{
			onSMENewsRetreivalListener.onSMENewsRetreivalEnd();
			if(result!=null)
			{
				onSMENewsRetreivalListener.onSMENewsRetreivalSuccess(result, isLoadmore);
			}
			else
			{
				ACError error = new ACError(ACErrorCodes.SERVER_ERROR, context.getString(R.string.more_news_pages_not_found)); 
				onSMENewsRetreivalListener.onSMENewsRetreivalError(error);
			}
			super.onPostExecute(result);
		}
    }
}
