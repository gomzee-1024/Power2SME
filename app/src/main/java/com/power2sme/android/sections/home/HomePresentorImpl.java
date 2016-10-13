package com.power2sme.android.sections.home;

import android.content.Context;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseAccountSummeryDto;
import com.power2sme.android.dtos.response.ResponseOutstandingDto;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class HomePresentorImpl implements IHomePresentor, OnOutstandingLoadingListener, OnAccountSummeryRequestListener
{
	Context context;
	IHomeView iHomeView;
	IHomeInteractor iHomeInteractor;
	public HomePresentorImpl(Context context, IHomeView iAccountUpdatesView)
	{
		this.context=context;
		this.iHomeView=iAccountUpdatesView;
		iHomeInteractor=new HomeInteractorImpl(context);
	}
	//////////////////////////////////////////////////////////////////////////////
	@Override
	public void getOutstanding(String strsmeid) 
	{
		iHomeInteractor.getOutstanding(strsmeid, this);
	}
	
	@Override
	public void onOutstandingLoadingStart() 
	{
        ACLogger.log("#################### EVENT : onOutstandingLoadingStart ####################");
		iHomeView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_OUTSTANDING);
	}
	
	@Override
	public void onOutstandingLoadingEnd() 
	{
		iHomeView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_OUTSTANDING);
        ACLogger.log("#################### EVENT : onOutstandingLoadingEnd ####################");
	}
	
	@Override
	public void onOutstandingLoadingSuccess(ResponseOutstandingDto responseOutstandingDto) 
	{
		iHomeView.showOutstanding(responseOutstandingDto.getData());
	}
	
	@Override
	public void onOutstandingLoadingError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				"Outstanding amount not found.", 
				"Outstanding amount not found."
				);
		
		iHomeView.showUIMessage(uiMessage, Constants.FLAG_OUTSTANDING);
	}
	
/////////////////////////////////////////////////////////
	
	@Override
	public void sendAccountSummeryRequest() 
	{
		iHomeInteractor.sendAccountSummeryRequest(this);
	}

	@Override
	public void onAccountSummeryRequestStart() 
	{
        ACLogger.log("#################### EVENT : onAccountSummeryRequestStart ####################");
		iHomeView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_REQUEST_STATEMENT);
	}

	@Override
	public void onAccountSummeryRequestEnd() 
	{
		iHomeView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_REQUEST_STATEMENT);
        ACLogger.log("#################### EVENT : onAccountSummeryRequestEnd ####################");
	}

	@Override
	public void onAccountSummeryRequestSuccess(ResponseAccountSummeryDto responseAccountSummeryDto) 
	{
		((MyAccountApplication)context.getApplicationContext()).getGAUtility().trackActionEvent("Account", "Request Account Statement", "Success", 1);
		iHomeView.showUIMessage(new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.your_account_statement_will_be_sent_within_48_hours_)), Constants.FLAG_REQUEST_STATEMENT);
	}

	@Override
	public void onAccountSummeryRequestError(ACError error) 
	{
		((MyAccountApplication)context.getApplicationContext()).getGAUtility().trackActionEvent("Account", "Request Account Statement", "Failure", 1);
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				"Request Account Statement have some problem, Please try after some time.",
				"Request Account Statement have some problem, Please try after some time."
				);
		
		iHomeView.showUIMessage(uiMessage, Constants.FLAG_REQUEST_STATEMENT);
	}
}