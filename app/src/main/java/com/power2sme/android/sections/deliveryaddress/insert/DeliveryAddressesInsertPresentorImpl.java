package com.power2sme.android.sections.deliveryaddress.insert;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseDeliveryAddressInsertDto;
import com.power2sme.android.dtos.response.ResponseUpdateDeliveryAddressDto;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.sections.deliveryaddress.list.DeliveryAddressesListFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class DeliveryAddressesInsertPresentorImpl implements IDeliveryAddressesInsertPresentor,OnDeliveryAddressInsertListener, OnUpdateDeliveryAddressListener//, OnStateRetreivalListener,OnCityRetreivalListener,
{
	Context context;
	IDeliveryAddressesInsertView iDeliveryAddressesUpdateView;
	IDeliveryAddressesInsertInteractor iDeliveryAddressesUpdateInteractor;
	public DeliveryAddressesInsertPresentorImpl(Context context, IDeliveryAddressesInsertView iDeliveryAddressesUpdateView)
	{
		this.context=context;
		this.iDeliveryAddressesUpdateView=iDeliveryAddressesUpdateView;
		this.iDeliveryAddressesUpdateInteractor=new DeliveryAddressesInsertInteractorImpl(context);
	}
	//////////////////////////////////////////////////////////////////////
	@Override
	public void insertDeliveryAddress(DeliveryAddress deliveryAddress) 
	{
		iDeliveryAddressesUpdateInteractor.insertDeliveryAddress(deliveryAddress, this);
	}

	@Override
	public void onDeliveryAddressInsertStart() 
	{
        ACLogger.log("#################### EVENT : onDeliveryAddressInsertStart ####################");
		iDeliveryAddressesUpdateView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_INSERT_DELIVERY_ADDRESS);
	}
	@Override
	public void onDeliveryAddressInsertEnd() 
	{
		iDeliveryAddressesUpdateView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_INSERT_DELIVERY_ADDRESS);
        ACLogger.log("#################### EVENT : onDeliveryAddressInsertEnd ####################");
	}
	@Override
	public void onDeliveryAddressInsertSuccess(ResponseDeliveryAddressInsertDto responseDeliveryAddressInsertDto) 
	{
		DeliveryAddressesInsertFragment.srcDeliveryAddress = responseDeliveryAddressInsertDto.getData();
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.delivery_address_insert_success));
		iDeliveryAddressesUpdateView.showUIMessage(uiMessage, Constants.FLAG_INSERT_DELIVERY_ADDRESS);

	}
	@Override
	public void onDeliveryAddressInsertError(ACError error) 
	{
		DeliveryAddressesInsertFragment.srcDeliveryAddress=null;
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.delivery_address_insert_error), 
				null
				);
		
		iDeliveryAddressesUpdateView.showUIMessage(uiMessage, Constants.FLAG_INSERT_DELIVERY_ADDRESS);	
	}

	///////////////////////////////////////////////////////////////////////

	@Override
	public void updateDeliveryAddress(DeliveryAddress deliveryAddress)
	{
		iDeliveryAddressesUpdateInteractor.updateDeliveryAddress(deliveryAddress, this);
	}

	@Override
	public void onUpdateDeliveryAddressStart()
	{
		ACLogger.log("#################### EVENT : onUpdateDeliveryAddressStart ####################");
		iDeliveryAddressesUpdateView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_UPDATE_DELIVERY_ADDRESS);
	}

	@Override
	public void onUpdateDeliveryAddressEnd()
	{
		iDeliveryAddressesUpdateView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_UPDATE_DELIVERY_ADDRESS);
		ACLogger.log("#################### EVENT : onUpdateDeliveryAddressEnd ####################");
	}

	@Override
	public void onUpdateDeliveryAddressSuccess(ResponseUpdateDeliveryAddressDto responseUpdateDeliveryAddressDto)
	{
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.update_delivery_address_success));
		iDeliveryAddressesUpdateView.showUIMessage(uiMessage, Constants.FLAG_UPDATE_DELIVERY_ADDRESS);
		DeliveryAddressesListFragment.mRefreshShippingAddress=true;
	}

	@Override
	public void onUpdateDeliveryAddressError(ACError error)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.update_delivery_address_error),
				null
		);

		iDeliveryAddressesUpdateView.showUIMessage(uiMessage, Constants.FLAG_UPDATE_DELIVERY_ADDRESS);
	}
}
