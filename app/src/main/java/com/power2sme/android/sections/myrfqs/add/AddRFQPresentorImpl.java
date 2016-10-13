package com.power2sme.android.sections.myrfqs.add;

import android.content.Context;
import android.widget.ProgressBar;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseAddNewRFQDto;
import com.power2sme.android.dtos.response.ResponseUploadPODto;
import com.power2sme.android.entities.UnitOfMeasure;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.entities.v3.TaxationPreference_v3;
import com.power2sme.android.entities.v3.Urgency_v3;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.myrfqs.list.OnPOUploadListener;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

import java.io.File;
import java.util.ArrayList;

public class AddRFQPresentorImpl implements
		IAddRFQPresentor,
		OnAddRFQListener,
		OnShowUnitsListener,
		OnUrgencyListLoadingListener,
		OnTaxPreferenceLoadingListener,
		OnPOUploadListener,
		OnItemCategoryFetchListener,
		OnItemSubCategoryFetchListener

{
	Context context;
	IAddRFQView iAddRFQView;
	IAddRFQInteractor iAddRFQInteractor;
	
	public AddRFQPresentorImpl(Context context, IAddRFQView iAddRFQView)
	{
		this.context=context;
		this.iAddRFQView=iAddRFQView;
		this.iAddRFQInteractor= new AddRFQInteractorImpl(context);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void addNewRFQ(NewRFQ_v3 newRFQ)
	{
		iAddRFQInteractor.addNewRFQ(newRFQ, this);
	}
	@Override
	public void onAddRFQStart() 
	{
        ACLogger.log("#################### EVENT : onAddRFQStart ####################");
		iAddRFQView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_ADD_RFQ);
	}

	@Override
	public void onAddRFQEnd() 
	{
		iAddRFQView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_ADD_RFQ);
        ACLogger.log("#################### EVENT : onAddRFQEnd ####################");
	}

	@Override
	public void onAddRFQSuccess(ResponseAddNewRFQDto responseAddNewRFQDto) 
	{
		String msg="Thank you! We will update you with the price quote shortly.";

//		"Thank you! System will update you with quote shortly."//if price is not available
//		"Thank for placing order with us, your total order value is %$1;<rupeessymbol>. Please check your order details in "

		if(responseAddNewRFQDto!=null && responseAddNewRFQDto.getData()!=null && responseAddNewRFQDto.getData().getWishlist()!=null && responseAddNewRFQDto.getData().getWishlist().getGrandTotal()!=null)//price avalable
		{
			try
			{
				String price = responseAddNewRFQDto.getData().getWishlist().getGrandTotal();
				float val = Float.parseFloat(price);
				if(val>0f)
				{
					msg="Thanks for placing order with us, your total order value is "+context.getString(R.string.inr)+price+", please check your order details on your email or SMS.";
				}
			}
			catch(Exception ex)
			{}
		}

		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("RFQ", "Submitted", "Success", 1);
		boolean isUploadPO = false;
		UIMessage uiMessage=new UIMessage(UIMessageType.DIALOG_OK, msg);
        if(responseAddNewRFQDto!=null && responseAddNewRFQDto.getData()!=null && responseAddNewRFQDto.getData().getOpportunity()!=null)
		{
			if(responseAddNewRFQDto.getData().getOpportunity().getRfqNo()!=null)
			{
				isUploadPO = true;
				iAddRFQView.navigateToMyRFQs(responseAddNewRFQDto.getData().getOpportunity().getRfqNo(), msg);
			}
		}
		if(!isUploadPO)
		{
			iAddRFQView.showUIMessage(uiMessage, Constants.FLAG_ADD_RFQ);
		}
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
		
		iAddRFQView.showUIMessage(uiMessage, Constants.FLAG_ADD_RFQ);	
	}
	/////////////////////////////////////////////////////////////////////////
	@Override
	public void getUnitsList(String category, BetterSpinner spinner, ProgressBar progressBar)
	{
		iAddRFQInteractor.getUnitsList(category, spinner, progressBar, this);
	}

	@Override
	public void onShowUnitsStart(BetterSpinner spinner, ProgressBar progressBar)
	{
		ACLogger.log("#################### EVENT : onShowUnitsStart ####################");
		iAddRFQView.showProgress(spinner, progressBar, ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_UNITS_LOADING);
	}

	@Override
	public void onShowUnitsEnd(BetterSpinner spinner, ProgressBar progressBar)
	{
		iAddRFQView.hideProgress(spinner, progressBar, ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_UNITS_LOADING);
        ACLogger.log("#################### EVENT : onShowUnitsEnd ####################");
	}

	@Override
	public void onShowUnitsError(BetterSpinner spinner, ProgressBar progressBar, ACError error)
	{
		BetterSpinner.activeSpinner=null;
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.addrfq_units_of_measure_dialog_error), 
				null
				);
		
		iAddRFQView.showUIMessage(spinner, progressBar,  uiMessage, Constants.FLAG_UNITS_LOADING);
	}
	@Override
	public void onShowUnitsSuccess(String category, BetterSpinner spinner, ProgressBar progressBar, ArrayList<UnitOfMeasure> units)
	{
		iAddRFQView.showUnitsList(category, spinner, progressBar, units);
	}

	///////////////////////////////////////////////////////////////////////////////

	@Override
	public void getUrgencyList(BetterSpinner spinner, ProgressBar progressBar)
	{
		iAddRFQInteractor.getUrgencyList(spinner, progressBar, this);
	}
	
	@Override
	public void onUrgencyListLoadingStart(BetterSpinner spinner,ProgressBar progressBar)
	{
		ACLogger.log("#################### EVENT : onUrgencyListLoadingStart ####################");
		iAddRFQView.showProgress(spinner, progressBar, ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_URGENCY_LIST_LOADING);
	}
	
	@Override
	public void onUrgencyListLoadingEnd(BetterSpinner spinner, ProgressBar progressBar)
	{
		iAddRFQView.hideProgress(spinner, progressBar, ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_URGENCY_LIST_LOADING);
        ACLogger.log("#################### EVENT : onUrgencyListLoadingEnd ####################");
	}
	
	@Override
	public void onUrgencyListLoadingSuccess(BetterSpinner spinner, ProgressBar progressBar, ArrayList<Urgency_v3> urgencyList)
	{
		iAddRFQView.showUrgencyList(spinner, progressBar, urgencyList);
	}
	
	@Override
	public void onUrgencyListLoadingError(BetterSpinner spinner, ProgressBar progressBar, ACError error)
	{
		BetterSpinner.activeSpinner=null;
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.addrfq_urgency_list_download_error), 
				null
				);
		iAddRFQView.showUIMessage(spinner, progressBar, uiMessage, Constants.FLAG_URGENCY_LIST_LOADING);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void getTaxationPrefsList(BetterSpinner spinner, ProgressBar progressBar)
	{
		iAddRFQInteractor.getTaxationPrefsList(spinner, progressBar, this);
	}

    @Override
	public void onTaxPreferenceLoadingStart(BetterSpinner spinner, ProgressBar progressBar)
	{
		ACLogger.log("#################### EVENT : onTaxPreferenceLoadingStart ####################");
		iAddRFQView.showProgress(spinner, progressBar, ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_TAXATION_PREFERENCE_LOADING);
	}
	
	@Override
	public void onTaxPreferenceLoadingEnd(BetterSpinner spinner, ProgressBar progressBar)
	{
		iAddRFQView.hideProgress(spinner, progressBar, ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_TAXATION_PREFERENCE_LOADING);
        ACLogger.log("#################### EVENT : onTaxPreferenceLoadingEnd ####################");
	}
	@Override
	public void onTaxPreferenceLoadingSuccess(BetterSpinner spinner, ProgressBar progressBar, ArrayList<TaxationPreference_v3> preferenceList)
	{
		iAddRFQView.showTaxationPrefsList(spinner, progressBar, preferenceList);
	}
	
	@Override
	public void onTaxPreferenceLoadingError(BetterSpinner spinner, ProgressBar progressBar, ACError error)
	{
		BetterSpinner.activeSpinner=null;
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.addrfq_taxation_prefs_list_download_error), 
				null
				);
		iAddRFQView.showUIMessage(spinner, progressBar, uiMessage, Constants.FLAG_TAXATION_PREFERENCE_LOADING);
	}

    ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void uploadPO(String rfqNo, File file, String smeId)
    {
        iAddRFQInteractor.uploadPO(rfqNo, file, smeId, this);
    }

	@Override
    public void onPOUploadStart()
    {
        ACLogger.log("#################### EVENT : onPOUploadStart ####################");
        iAddRFQView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_PO_UPLOAD);
    }

    @Override
    public void onPOUploadEnd()
    {
        iAddRFQView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_PO_UPLOAD);
        ACLogger.log("#################### EVENT : onPOUploadEnd ####################");
    }

    @Override
    public void onPOUploadSuccess(ResponseUploadPODto responseUploadPODto)
    {
		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("RFQ", "PO Uploaded", "Success", 1);
        UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.myrfqs_po_upload_success));
        iAddRFQView.showUIMessage(uiMessage, Constants.FLAG_PO_UPLOAD);
		iAddRFQView.navigateToMyRFQs(null, context.getString(R.string.myrfqs_po_upload_success));
    }

    @Override
    public void onPOUploadError(ACError error)
    {
		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("RFQ", "PO Uploaded", "Failure - " + error.getMessage(), 1);
        UIMessage uiMessage = Utils.getUIErrorMessage(
                context,
                error,
                context.getString(R.string.myrfqs_po_upload_error),
                null
        );
        iAddRFQView.showUIMessage(uiMessage, Constants.FLAG_PO_UPLOAD);
    }

	//////////////////////////////////////////////////////////

	@Override
	public void getItemCategories(BetterSpinner spinner, ProgressBar progressBar)
	{
		iAddRFQInteractor.getItemCategories(spinner, progressBar,  this);
	}

	@Override
	public void onItemCategoryFetchStart(BetterSpinner spinner, ProgressBar progressBar) {
		ACLogger.log("#################### EVENT : onSKUCategoryFetchStart ####################");
		iAddRFQView.showProgress(spinner, progressBar, ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_SKU_CATEGORY);
	}

	@Override
	public void onItemCategoryFetchEnd(BetterSpinner spinner, ProgressBar progressBar) {
		ACLogger.log("#################### EVENT : onSKUCategoryFetchEnd ####################");
		iAddRFQView.hideProgress(spinner, progressBar, ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_SKU_CATEGORY);
	}

	@Override
	public void onItemCategoryFetchSuccess(BetterSpinner spinner, ProgressBar progressBar, ArrayList<String> categoryList) {
		iAddRFQView.showItemCategoryList(spinner, progressBar, categoryList);
	}

	@Override
	public void onItemCategoryFetchError(BetterSpinner spinner, ProgressBar progressBar, ACError error) {
		BetterSpinner.activeSpinner=null;
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.addrfq_sku_category_fetch_error),
				null
		);
		iAddRFQView.showUIMessage(uiMessage, Constants.FLAG_SKU_CATEGORY);
	}

	///////////////////////////////////////////////////////////

	@Override
	public void getItemSubCategories(BetterSpinner spinner, ProgressBar progressBar, String category)
	{
		iAddRFQInteractor.getItemSubCategories(spinner, progressBar, category, this);
	}
	@Override
	public void onItemSubCategoryFetchStart(BetterSpinner spinner, ProgressBar progressBar) {
		ACLogger.log("#################### EVENT : onSKUSubCategoryFetchStart ####################");
		iAddRFQView.showProgress(spinner, progressBar, ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_SKU_SUB_CATEGORY);
	}

	@Override
	public void onItemSubCategoryFetchEnd(BetterSpinner spinner, ProgressBar progressBar) {
		ACLogger.log("#################### EVENT : onSKUSubCategoryFetchEnd ####################");
		iAddRFQView.hideProgress(spinner, progressBar, ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_SKU_SUB_CATEGORY);
	}

	@Override
	public void onItemSubCategoryFetchSuccess(BetterSpinner spinner, ProgressBar progressBar, String category, ArrayList<String> categorySubList)
	{
		iAddRFQView.showItemSubCategoryList(spinner, progressBar, category, categorySubList);
	}

	@Override
	public void onItemSubCategoryFetchError(BetterSpinner spinner, ProgressBar progressBar,ACError error) {
		BetterSpinner.activeSpinner=null;
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.addrfq_sku_sub_category_fetch_error),
				null
		);
		iAddRFQView.showUIMessage(uiMessage, Constants.FLAG_SKU_SUB_CATEGORY);
	}
	///////////////////////////////////////////////////////////

}
