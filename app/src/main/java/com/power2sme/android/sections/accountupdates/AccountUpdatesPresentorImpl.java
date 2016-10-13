package com.power2sme.android.sections.accountupdates;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseGetAccountUpdatesDto;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;

public class AccountUpdatesPresentorImpl implements IAccountUpdatesPresentor,OnAccountUpdatesRetrievalListenere 
{
	Context context;
	IAccountUpdatesView iAccountUpdatesView;
	IAccountUpdatesInteractor iAccountUpdatesInteractor;
	public AccountUpdatesPresentorImpl(Context context, IAccountUpdatesView iAccountUpdatesView)
	{
		this.context=context;
		this.iAccountUpdatesView=iAccountUpdatesView;
		iAccountUpdatesInteractor=new AccountUpdatesInteractorImpl(context);
	}
    ///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void getAccountUpdates(long currentPage, long PageSize, String strsmeid, boolean isLoadmore)
	{
		iAccountUpdatesInteractor.getAccountUpdates(currentPage, PageSize, strsmeid,isLoadmore,  this);
	}
	@Override
	public void onAccountUpdatesRetrievalStart() 
	{
        ACLogger.log("#################### EVENT : onAccountUpdatesRetrievalStart ####################");
		iAccountUpdatesView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_ACOUNT_UPDATES);		
	}
	@Override
	public void onAccountUpdatesRetrievalEnd() 
	{
		iAccountUpdatesView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_ACOUNT_UPDATES);
        ACLogger.log("#################### EVENT : onAccountUpdatesRetrievalEnd ####################");
	}
	@Override
	public void onAccountUpdatesRetrievalSuccess(ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto, boolean isLoadmore)
	{
		iAccountUpdatesView.showAccountUpdates(responseGetAccountUpdatesDto, isLoadmore);
	}
	@Override
	public void onAccountUpdatesRetrievalError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.accountupdates_retrieval_error),
                context.getString(R.string.accountupdates_not_available)
				);
		
		iAccountUpdatesView.showUIMessage(uiMessage, Constants.FLAG_ACOUNT_UPDATES);		
	}
}
