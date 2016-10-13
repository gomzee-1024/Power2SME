package com.power2sme.android.sections.deals.postorder;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.request.RequestAddCampaignDto;
import com.power2sme.android.dtos.response.ResponseAddCampaignDto;
import com.power2sme.android.dtos.response.ResponseGetDealsByIDDto;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class DealsPostOrderFragmentPresentorImpl implements IDealsPostOrderFragmentPresentor,OnPostDealsOrderListener,OnGetDealsByIDListener
{
	Context context;
	IDealsPostOrderView iDealsPostOrderFragmentView;
	IDealsPostOrderFragmentInteractor iDealsPostOrderFragmentInteractor;
	public DealsPostOrderFragmentPresentorImpl(Context context, IDealsPostOrderView iDealsPostOrderFragmentView)
	{
		this.context=context;
		this.iDealsPostOrderFragmentView=iDealsPostOrderFragmentView;
		iDealsPostOrderFragmentInteractor=new DealsPostOrderFragmentInteractorImpl(context);
	}
	@Override
	public void postOrder(RequestAddCampaignDto campaign) 
	{
		iDealsPostOrderFragmentInteractor.postOrder(campaign, this);
	}
	@Override
	public void onPostDealsOrderStart() 
	{
        ACLogger.log("#################### EVENT : onPostDealsOrderStart ####################");
		iDealsPostOrderFragmentView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_DEALS_POST_ORDER);
	}
	@Override
	public void onPostDealsOrderEnd() 
	{
		iDealsPostOrderFragmentView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_DEALS_POST_ORDER);
        ACLogger.log("#################### EVENT : onPostDealsOrderEnd ####################");
	}
	@Override
	public void onPostDealsOrderSuccess(ResponseAddCampaignDto responseAddCampaignDto) 
	{
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.deals_order_post_success));
		iDealsPostOrderFragmentView.showUIMessage(uiMessage, Constants.FLAG_DEALS_POST_ORDER);
	}
	@Override
	public void onPostDealsOrderError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.deals_order_post_error), 
				null
				);
		
		iDealsPostOrderFragmentView.showUIMessage(uiMessage, Constants.FLAG_DEALS_POST_ORDER);		
	}
	/////////////////////////////////////////////////////
	
	@Override
	public void getDealById(String dealId) 
	{
		iDealsPostOrderFragmentInteractor.getDealById(dealId, this);
	}
	@Override
	public void onGetDealsByIDStart() 
	{
        ACLogger.log("#################### EVENT : onGetDealsByIDStart ####################");
		iDealsPostOrderFragmentView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_DEALS_BI_ID);
	}
	@Override
	public void onGetDealsByIDEnd() 
	{
		iDealsPostOrderFragmentView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_DEALS_BI_ID);
        ACLogger.log("#################### EVENT : onGetDealsByIDEnd ####################");
	}
	@Override
	public void onGetDealsByIDSuccess( ResponseGetDealsByIDDto responseGetDealsByIDDto) 
	{
		iDealsPostOrderFragmentView.showDeal(responseGetDealsByIDDto.getData());
	}
	@Override
	public void onGetDealsByIDError(ACError error) 
	{
		iDealsPostOrderFragmentView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_DEALS_BI_ID);
	}
}
