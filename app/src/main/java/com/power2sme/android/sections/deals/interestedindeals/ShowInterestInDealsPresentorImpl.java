package com.power2sme.android.sections.deals.interestedindeals;

import android.content.Context;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseAddNewRFQDto;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.myrfqs.add.OnAddRFQListener;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class ShowInterestInDealsPresentorImpl implements IShowInterestInDealsPresentor, OnAddRFQListener
{
	Context context;
	IShowInterestInDealsView iShowInterestInDealsView;
	IShowInterestInDealsInteractor iShowInterestInDealsInteractor;
	
	public ShowInterestInDealsPresentorImpl(Context context, IShowInterestInDealsView iShowInterestInDealsView)
	{
		this.context=context;
		this.iShowInterestInDealsView=iShowInterestInDealsView;
		this.iShowInterestInDealsInteractor= new ShowInterestInDealsInteractorImpl(context);
	}
	///////////////////////////////////////////////////////////////////////////
	@Override
	public void addNewRFQ(NewRFQ_v3 newRFQ)
	{
		iShowInterestInDealsInteractor.addNewRFQ(newRFQ, this);
	}
	@Override
	public void onAddRFQStart() 
	{
        ACLogger.log("#################### EVENT : onAddRFQStart ####################");
		iShowInterestInDealsView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_ADD_RFQ);
	}

	@Override
	public void onAddRFQEnd() 
	{
		iShowInterestInDealsView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_ADD_RFQ);
        ACLogger.log("#################### EVENT : onAddRFQEnd ####################");
	}

	@Override
	public void onAddRFQSuccess(ResponseAddNewRFQDto responseAddNewRFQDto) 
	{
		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("RFQ", "Submitted", "Success", 1);
		boolean isUploadPO = false;
		UIMessage uiMessage=new UIMessage(UIMessageType.DIALOG_OK, context.getString(R.string.addrfq_success));
        iShowInterestInDealsView.showUIMessage(uiMessage, Constants.FLAG_ADD_RFQ);
	}

	@Override
	public void onAddRFQError(ACError error) 
	{
		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("RFQ", "Submitted", "Failure - "+error.getMessage(), 1);
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.addrfq_error), 
				null
				);

		iShowInterestInDealsView.showUIMessage(uiMessage, Constants.FLAG_ADD_RFQ);
	}
}
