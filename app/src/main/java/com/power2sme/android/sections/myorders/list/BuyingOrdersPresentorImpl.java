package com.power2sme.android.sections.myorders.list;

import android.content.Context;
import android.content.SharedPreferences;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.dtos.response.ResponseTrackingStatusDto;
import com.power2sme.android.entities.SalesOrder;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;

import java.util.ArrayList;

public class BuyingOrdersPresentorImpl implements IBuyingOrdersPresentor,OnOrderRetrievalListener,OnSearchOrderListener, OnTrackingStatusListener//,OnUploadPOListener
{
	Context context;
	IBuyingOrdersView iBuyingOrdersView;
	IBuyingOrdersInteractor iBuyingOrdersInteractor;
	public BuyingOrdersPresentorImpl(Context context, IBuyingOrdersView iBuyingOrdersView)
	{
		this.context=context;
		this.iBuyingOrdersView=iBuyingOrdersView;
		iBuyingOrdersInteractor=new BuyingOrdersInteractorImpl(context);
	}
	@Override
	public void uploadOrder(SalesOrder salesOrder) 
	{
		
	}
	@Override
	public void getDeliveryAddress() 
	{
		
	}
	
	/////////////////////////////////////////////////////////////////
	@Override
	public void getOrders(long currentPageIndex,long ordersPerPage, String smeId, String orderNo, OrderTypes orderTypes, boolean isLoadmore) 
	{
		iBuyingOrdersInteractor.getOrders(currentPageIndex,ordersPerPage, smeId, orderNo,orderTypes, this, isLoadmore);
	}

	@Override
	public void onOrderRetrievalStart() 
	{
        ACLogger.log("#################### EVENT : onOrderRetrievalStart ####################");
		iBuyingOrdersView.showProgress(ProgressTypes.INTERACTION_ALLOWED, 0);
	}
	
	@Override
	public void onOrderRetrievalEnd() 
	{
		iBuyingOrdersView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, 0);
        ACLogger.log("#################### EVENT : onOrderRetrievalEnd ####################");
	}
	
	@Override
	public void onOrderRetrievalSuccess(OrderTypes orderTypes, ResponseSalesOrdersDto responseSalesOrdersDto, boolean isLoadmore) 
	{
		iBuyingOrdersView.showOrders(responseSalesOrdersDto, isLoadmore);
	}
	
	@Override
	public void onOrderRetrievalError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myorders_retrieval_error), 
				context.getString(R.string.myorders_not_found)
				);
		iBuyingOrdersView.showUIMessage(uiMessage, 0);
		ResponseSalesOrdersDto responseSalesOrdersDto=new ResponseSalesOrdersDto();
		responseSalesOrdersDto.setData(new ArrayList<SalesOrder>());
		iBuyingOrdersView.showOrders(responseSalesOrdersDto, false);
	}
	//////////////////////////////////////////////////////////
	@Override
	public void searchOrder(String orderID) 
	{
		//iBuyingOrdersInteractor.searchOrder(searchQuery, this);
		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
		SharedPreferences prefs = myAccountApplication.getPrefs();
		String smeId = prefs.getString(Constants.PREFERENCE_CUSTOMER_SMEID, "");
		iBuyingOrdersInteractor.searchOrder(0,50, smeId, orderID,OrderTypes.ALL, this);
	}
	@Override
	public void onSearchOrderStart() 
	{
        ACLogger.log("#################### EVENT : onSearchOrderStart ####################");
		iBuyingOrdersView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
	}
	@Override
	public void onSearchOrderEnd() 
	{
		iBuyingOrdersView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
        ACLogger.log("#################### EVENT : onSearchOrderEnd ####################");
	}
	@Override
	public void onSearchOrderSuccess(OrderTypes orderTypes, ResponseSalesOrdersDto responseSalesOrdersDto) 
	{
		iBuyingOrdersView.showOrders(responseSalesOrdersDto, false);
	}
	@Override
	public void onSearchOrderError(ACError error, String orderID) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myorder_search_error), 
				context.getString(R.string.myorder_search_not_found)
				);
		iBuyingOrdersView.showUIMessage(uiMessage, 0);		
	}

	/////////////////////////////////////////////////////////////////////////////

	@Override
	public void getTrackingStatus(String smeId) {
		iBuyingOrdersInteractor.getTrackingStatus(smeId, this);
	}

	@Override
	public void onTrackingStatusStart() {
		ACLogger.log("#################### EVENT : onTrackingStatusStart ####################");
		iBuyingOrdersView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
	}

	@Override
	public void onTrackingStatusEnd() {
		iBuyingOrdersView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
		ACLogger.log("#################### EVENT : onTrackingStatusEnd ####################");
	}

	@Override
	public void onTrackingStatusSuccess(ResponseTrackingStatusDto trackingDto) {
		iBuyingOrdersView.showTrackingStatus(trackingDto);
	}

	@Override
	public void onTrackingStatusError(ACError error) {
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.myorder_search_error),
				context.getString(R.string.myorder_search_not_found)
		);
		iBuyingOrdersView.showUIMessage(uiMessage, 0);
	}
}

