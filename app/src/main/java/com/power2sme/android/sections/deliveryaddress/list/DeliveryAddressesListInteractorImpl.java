package com.power2sme.android.sections.deliveryaddress.list;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseDeleteDeliveryAddressDto;
import com.power2sme.android.dtos.response.ResponseDeliveryAddressDto;

public class DeliveryAddressesListInteractorImpl implements IDeliveryAddressesListInteractor 
{
	Context context;
	public DeliveryAddressesListInteractorImpl(Context context)
	{
		this.context=context;
	}
	
	@Override
	public void getDeliveryAddressList(final String smeId, final OnDeliveryAddressListRetrievalListener onDeliveryAddressListRetrievalListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse <ResponseDeliveryAddressDto> >(context, new INetworkAsyncTaskCallbacks<ResponseDeliveryAddressDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onDeliveryAddressListRetrievalListener.onDeliveryAddressListRetrievalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseDeliveryAddressDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseDeliveryAddressDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeliveryAddressDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getDeliveryAdresses(smeId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseDeliveryAddressDto> networkAsyncTaskResponse) 
			{
				onDeliveryAddressListRetrievalListener.onDeliveryAddressListRetrievalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDeliveryAddressListRetrievalListener.onDeliveryAddressListRetrievalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseDeliveryAddressDto responseDeliveryAddressDto=(ResponseDeliveryAddressDto) networkAsyncTaskResponse.getResultObject();
					onDeliveryAddressListRetrievalListener.onDeliveryAddressListRetrievalSuccess(responseDeliveryAddressDto);
				}
			}
		}).execute();
	}

	@Override
	public void deleteDeliveryAddress(final String deliveryAddressCode, final OnDeleteDeliveryAddressListener onDeleteDeliveryAddressListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse <ResponseDeleteDeliveryAddressDto> >(context, new INetworkAsyncTaskCallbacks<ResponseDeleteDeliveryAddressDto>()
		{
			@Override
			public void onPreExecute()
			{
				onDeleteDeliveryAddressListener.onDeleteDeliveryAddressStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().deleteDeliveryAddress(deliveryAddressCode);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto> networkAsyncTaskResponse)
			{
				onDeleteDeliveryAddressListener.onDeleteDeliveryAddressEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDeleteDeliveryAddressListener.onDeleteDeliveryAddressError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseDeleteDeliveryAddressDto responseDeleteDeliveryAddressDto=(ResponseDeleteDeliveryAddressDto) networkAsyncTaskResponse.getResultObject();
					onDeleteDeliveryAddressListener.onDeleteDeliveryAddressSuccess(responseDeleteDeliveryAddressDto);
				}
			}
		}).execute();
	}
}
