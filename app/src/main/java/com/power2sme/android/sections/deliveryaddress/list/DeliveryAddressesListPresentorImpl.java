package com.power2sme.android.sections.deliveryaddress.list;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseDeleteDeliveryAddressDto;
import com.power2sme.android.dtos.response.ResponseDeliveryAddressDto;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

import java.util.ArrayList;

public class DeliveryAddressesListPresentorImpl implements IDeliveryAddressesListPresentor,OnDeliveryAddressListRetrievalListener, OnDeleteDeliveryAddressListener
{
	Context context;
	IDeliveryAddressesListView iDeliveryAddressesListView;
	IDeliveryAddressesListInteractor iDeliveryAddressesInteractor;
	public DeliveryAddressesListPresentorImpl(Context context, IDeliveryAddressesListView iDeliveryAddressesListView)
	{
		this.context=context;
		this.iDeliveryAddressesListView=iDeliveryAddressesListView;
		iDeliveryAddressesInteractor=new DeliveryAddressesListInteractorImpl(context);
	}
/////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void getDeliveryAddressList(String smeId) 
	{
		iDeliveryAddressesInteractor.getDeliveryAddressList(smeId, this);
	}

	@Override
	public void onDeliveryAddressListRetrievalStart() 
	{
        ACLogger.log("#################### EVENT : onDeliveryAddressListRetrievalStart ####################");
		iDeliveryAddressesListView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
	}
	@Override
	public void onDeliveryAddressListRetrievalEnd() 
	{
		iDeliveryAddressesListView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
        ACLogger.log("#################### EVENT : onDeliveryAddressListRetrievalEnd ####################");
	}
	@Override
	public void onDeliveryAddressListRetrievalSuccess(ResponseDeliveryAddressDto responseDeliveryAddressDto) 
	{
		iDeliveryAddressesListView.showDeliveryAddressList(responseDeliveryAddressDto.getData());
	}
	@Override
	public void onDeliveryAddressListRetrievalError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.delivery_address_retreival_error),
                context.getString(R.string.delivery_address_not_available)
				);
		
		iDeliveryAddressesListView.showUIMessage(uiMessage, 0);
		
		if(error.getErrorCodes() == ACErrorCodes.API_ERRORCODE_NORECORDSFOUND)
		{
			iDeliveryAddressesListView.showDeliveryAddressList(new ArrayList<DeliveryAddress>());
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void deleteDeliveryAddress(String deliveryAddressCode)
	{
		iDeliveryAddressesInteractor.deleteDeliveryAddress(deliveryAddressCode, this);
	}

	@Override
	public void onDeleteDeliveryAddressStart() {
		ACLogger.log("#################### EVENT : onDeleteDeliveryAddressStart ####################");
		iDeliveryAddressesListView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_DELETE_DELIVERY_ADDRESS);
	}

	@Override
	public void onDeleteDeliveryAddressEnd() {
		iDeliveryAddressesListView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_DELETE_DELIVERY_ADDRESS);
		ACLogger.log("#################### EVENT : onDeleteDeliveryAddressEnd ####################");
	}

	@Override
	public void onDeleteDeliveryAddressSuccess(ResponseDeleteDeliveryAddressDto responseDeleteDeliveryAddressDto)
	{
		iDeliveryAddressesListView.showUIMessage(new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.delivery_address_delete_success)), Constants.FLAG_DELETE_DELIVERY_ADDRESS);
	}

	@Override
	public void onDeleteDeliveryAddressError(ACError error)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.delivery_address_delete_error),
				context.getString(R.string.delivery_address_not_available)
		);
		iDeliveryAddressesListView.showUIMessage(uiMessage, Constants.FLAG_DELETE_DELIVERY_ADDRESS);
	}
}
