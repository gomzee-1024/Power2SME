package com.power2sme.android.sections.myorders.shipmentdetails;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.dtos.response.ResponseShipmentDetailsDto;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;

public class ShipmentDetailsPresentorImpl implements IShipmentDetailsPresentor, OnShipmentDetailsListener, OnOrderDetailsListener 
{
	Context context;
	IShipmentDetailsView iBuyingOrdersView;
	IShipmentDetailsInteractor iOrderDetailsInteractor;
	public ShipmentDetailsPresentorImpl(Context context, IShipmentDetailsView iBuyingOrdersView)
	{
		this.context=context;
		this.iBuyingOrdersView=iBuyingOrdersView;
		iOrderDetailsInteractor=new ShipmentDetailsInteractorImpl(context);
	}
	///////////////////////////////////////////////////////////////////
	@Override
	public void onOrderDetailsStart() 
	{
        ACLogger.log("#################### EVENT : onOrderDetailsStart ####################");
		iBuyingOrdersView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_ORDER_DETAIL);
	}
	
	@Override
	public void onOrderDetailsEnd() 
	{
		iBuyingOrdersView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_ORDER_DETAIL);
        ACLogger.log("#################### EVENT : onOrderDetailsEnd ####################");
	}
	
	@Override
	public void onOrderDetailsSuccess(ResponseSalesOrdersDto responseSalesOrdersDto) 
	{
		if(responseSalesOrdersDto.getData()!=null && responseSalesOrdersDto.getData().size()>0)
			iBuyingOrdersView.showOrderDetails(responseSalesOrdersDto.getData().get(0));
		else
			onOrderDetailsError(null);
	}
	
	@Override
	public void onOrderDetailsError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myorder_detail_error), 
				null
				);
		
		iBuyingOrdersView.showUIMessage(uiMessage, Constants.FLAG_ORDER_DETAIL);
	}
	
	@Override
	public void getOrderDetails(String orderId) 
	{
		iOrderDetailsInteractor.getOrderDetails(orderId, this);		
	}
//////////////////////////////////////////////////////////////
	@Override
	public void getShipmentDetails(String orderId) 
	{
		iOrderDetailsInteractor.getShipmentDetails(orderId, this);
	}

	@Override
	public void onShipmentDetailsStart() 
	{
        ACLogger.log("#################### EVENT : onShipmentDetailsStart ####################");
		iBuyingOrdersView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_SHIPMENT_DETAIL);		
	}

	@Override
	public void onShipmentDetailsEnd() 
	{
		iBuyingOrdersView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_SHIPMENT_DETAIL);
        ACLogger.log("#################### EVENT : onShipmentDetailsEnd ####################");
	}

	@Override
	public void onShipmentDetailsSuccess(ResponseShipmentDetailsDto responseShipmentDetailsDto) 
	{
		iBuyingOrdersView.showShipmentDetails(responseShipmentDetailsDto.getData());
	}

	@Override
	public void onShipmentDetailsError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.shipmentdetails_error), 
				context.getString(R.string.shipmentdetails_not_found)
				);
		
		iBuyingOrdersView.showUIMessage(uiMessage, Constants.FLAG_SHIPMENT_DETAIL);
	}
}
