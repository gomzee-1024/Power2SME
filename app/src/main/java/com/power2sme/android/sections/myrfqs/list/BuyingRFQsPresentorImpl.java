package com.power2sme.android.sections.myrfqs.list;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseRFQsDto;
import com.power2sme.android.dtos.response.ResponseUploadPODto;
import com.power2sme.android.entities.v3.Wishlist_v3;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

import java.io.File;
import java.util.ArrayList;

public class BuyingRFQsPresentorImpl implements IBuyingRFQsPresentor,OnAcceptQuoteListener,OnRequestQuoteListener,OnRFQsRetrievalListener, OnPOUploadListener, OnRFQDetailsListener 
{
	Context context;
	IBuyingRFQsView iBuyingRFQsView;
	IBuyingRFQsInteractor iBuyingRFQsInteractor;
	public BuyingRFQsPresentorImpl(Context context, IBuyingRFQsView iBuyingRFQsView)
	{
		this.context=context;
		this.iBuyingRFQsView=iBuyingRFQsView;
		this.iBuyingRFQsInteractor=new BuyingRFQsInteractorImpl(context);
	}
/////////////////////////////////////////////////////////
	@Override
	public void acceptQuote(Wishlist_v3 rfq, String orderNo, String smeId)
	{
		iBuyingRFQsInteractor.acceptQuote(rfq, orderNo, smeId, this);		
	}
	@Override
	public void onAcceptQuoteStart() 
	{
        ACLogger.log("#################### EVENT : onAcceptQuoteStart ####################");
		iBuyingRFQsView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_ACCEPT_QUOTE);		
	}
	@Override
	public void onAcceptQuoteEnd() 
	{
		iBuyingRFQsView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_ACCEPT_QUOTE);
        ACLogger.log("#################### EVENT : onAcceptQuoteEnd ####################");
	}
	@Override
	public void onAcceptQuoteSuccess(Wishlist_v3 rfq)
	{
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.myrfqs_accept_quote_success));
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_ACCEPT_QUOTE);
		rfq.setRfqCode("5");
		rfq.setRfqStatus("PO copy awaited");
		iBuyingRFQsView.refreshList();
	}
	@Override
	public void onAcceptQuoteError(Wishlist_v3 rfq, ACError error)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myrfqs_accept_quote_error), 
				null
				);
		
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_ACCEPT_QUOTE);	
	}
/////////////////////////////////////////////////////////
	@Override
	public void requestRequote(Wishlist_v3 rfq, String orderNo, String smeId)
	{
		iBuyingRFQsInteractor.requestRequote(rfq, orderNo, smeId, this);
	}
	@Override
	public void onRequestQuoteStart() 
	{
        ACLogger.log("#################### EVENT : onRequestQuoteStart ####################");
		iBuyingRFQsView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_REQUEST_RE_QUOTE);
	}
	@Override
	public void onRequestQuoteEnd() 
	{
		iBuyingRFQsView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_REQUEST_RE_QUOTE);
        ACLogger.log("#################### EVENT : onRequestQuoteEnd ####################");
	}
	@Override
	public void onRequestQuoteSuccess(Wishlist_v3 rfq)
	{
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.myrfqs_request_quote_success));
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_REQUEST_RE_QUOTE);		
	}
	@Override
	public void onRequestQuoteError(Wishlist_v3 rfq, ACError error)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myrfqs_request_quote_error), 
				null
				);
		
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_REQUEST_RE_QUOTE);		
	}
/////////////////////////////////////////////////////////
	@Override
	public void getRFQs(long currentPage, long pageSize, String smeId, RFQTypes rfqTypes, boolean isLoadmore) 
	{
		iBuyingRFQsInteractor.getRFQs(currentPage, pageSize, smeId, rfqTypes, this, isLoadmore);
	}
	@Override
	public void onRFQsRetrievalStart() 
	{
        ACLogger.log("#################### EVENT : onRFQsRetrievalStart ####################");
		iBuyingRFQsView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_RFQ_LOADING);
	}
	@Override
	public void onRFQsRetrievalEnd() 
	{
		iBuyingRFQsView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_RFQ_LOADING);
        ACLogger.log("#################### EVENT : onRFQsRetrievalEnd ####################");
	}
	@Override
	public void onRFQsRetrievalSuccess(RFQTypes rfqTypes, ResponseRFQsDto responseRFQsDto, boolean isLoadmore) 
	{
		iBuyingRFQsView.showRFQs(rfqTypes, responseRFQsDto, isLoadmore);
	}
	@Override
	public void onRFQsRetrievalError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myrfqs_retrieval_error), 
				context.getString(R.string.myrfqs_no_rfqs)
				);
		
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_RFQ_LOADING);
		ResponseRFQsDto responseRFQsDto = new ResponseRFQsDto();
		responseRFQsDto.setData(new ArrayList<Wishlist_v3>());
		iBuyingRFQsView.showRFQs(RFQTypes.ALL_RFQ_ONLY, responseRFQsDto, false);
	}
/////////////////////////////////////////////////////////
	@Override
	public void uploadPO(String rfqNo, File file, String smeId)
	{
		iBuyingRFQsInteractor.uploadPO(rfqNo, file, smeId, this);
	}

	@Override
	public void onPOUploadStart() 
	{
        ACLogger.log("#################### EVENT : onPOUploadStart ####################");
		iBuyingRFQsView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_PO_UPLOAD);
	}

	@Override
	public void onPOUploadEnd() 
	{
		iBuyingRFQsView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_PO_UPLOAD);
        ACLogger.log("#################### EVENT : onPOUploadEnd ####################");
	}

	@Override
	public void onPOUploadSuccess(ResponseUploadPODto responseUploadPODto)
	{
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.myrfqs_po_upload_success));
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_PO_UPLOAD);
		iBuyingRFQsView.refreshList();
	}

	@Override
	public void onPOUploadError(ACError error)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myrfqs_po_upload_error), 
				null
				);
		
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_PO_UPLOAD);		
	}
//////////////////////////////////////////////////////////////////
	@Override
	public void getRFQDetails(String rfqId) 
	{
		iBuyingRFQsInteractor.getRFQDetails(rfqId, this);
	}

	@Override
	public void onRFQDetailsStart() 
	{
        ACLogger.log("#################### EVENT : onRFQDetailsStart ####################");
		iBuyingRFQsView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_RFQ_DETAIL);	
	}

	@Override
	public void onRFQDetailsEnd() 
	{
		iBuyingRFQsView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_RFQ_DETAIL);
        ACLogger.log("#################### EVENT : onRFQDetailsEnd ####################");
	}

	@Override
	public void onRFQDetailsSuccess(ResponseRFQsDto responseRFQsDto) 
	{
		iBuyingRFQsView.showRFQs(null, responseRFQsDto, false);
	}

	@Override
	public void onRFQDetailsError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myrfqs_details_error), 
				context.getString(R.string.myrfqs_no_rfqs)
				);
		
		iBuyingRFQsView.showUIMessage(uiMessage, Constants.FLAG_RFQ_DETAIL);
	}
}
