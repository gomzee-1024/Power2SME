package com.power2sme.android.sections.deals.list;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseGetDealsDto;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;

public class DealsPresentorImpl implements IDealsPresentor,OnRetreivalDealsListener
{
	Context context;
	IDealsView iDealsFragmentView;
	IDealsInteractor iDealsInteractor;
	public DealsPresentorImpl(Context context, IDealsView iDealsFragmentView)
	{
		this.context=context;
		this.iDealsFragmentView=iDealsFragmentView;
		this.iDealsInteractor=new DealsInteractorImpl(context);
	}
	////////////////////////////////////////////////////////////////
	@Override
	public void getDeals(long pageIndex, long pageSize, String filterValue, boolean isLoadmore)
	{
		iDealsInteractor.getDeals(pageIndex, pageSize, filterValue, this, isLoadmore);
	}

	@Override
	public void onRetreivalDealsStart() 
	{
        ACLogger.log("#################### EVENT : onRetreivalDealsStart ####################");
		iDealsFragmentView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_DEALS);		
	}

	@Override
	public void onRetreivalDealsEnd() 
	{
		iDealsFragmentView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_DEALS);
        ACLogger.log("#################### EVENT : onRetreivalDealsEnd ####################");
	}

	@Override
	public void onRetreivalDealsSuccess(String filterValue, ResponseGetDealsDto responseGetDealsDto, boolean isLoadmore)
	{
		iDealsFragmentView.showDeals(filterValue, responseGetDealsDto, isLoadmore);
	}

	@Override
	public void onRetreivalDealsError(String filterValue, ACError error, boolean isLoadmore)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.deals_not_found), 
				null
				);
		
		iDealsFragmentView.showUIMessage(uiMessage, Constants.FLAG_DEALS);
		iDealsFragmentView.showDeals(filterValue, null, isLoadmore);
	}
}
