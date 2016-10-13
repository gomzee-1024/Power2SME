package com.power2sme.android.sections.deliveryaddress.insert;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseDeliveryAddressInsertDto;
import com.power2sme.android.dtos.response.ResponseGetCitiesDto;
import com.power2sme.android.dtos.response.ResponseGetStatesDto;
import com.power2sme.android.dtos.response.ResponseUpdateDeliveryAddressDto;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.entities.State;

public class DeliveryAddressesInsertInteractorImpl implements IDeliveryAddressesInsertInteractor 
{
	Context context;
	public DeliveryAddressesInsertInteractorImpl(Context context) 
	{
		this.context=context;
	}
	@Override
	public void insertDeliveryAddress(final DeliveryAddress deliveryAddress,final OnDeliveryAddressInsertListener onDeliveryAddressInsertListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse <ResponseDeliveryAddressInsertDto> >(context, new INetworkAsyncTaskCallbacks<ResponseDeliveryAddressInsertDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onDeliveryAddressInsertListener.onDeliveryAddressInsertStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().insertDeliveryAdresses(deliveryAddress);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto> networkAsyncTaskResponse) 
			{
				onDeliveryAddressInsertListener.onDeliveryAddressInsertEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDeliveryAddressInsertListener.onDeliveryAddressInsertError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseDeliveryAddressInsertDto responseDeliveryAddressInsertDto=(ResponseDeliveryAddressInsertDto) networkAsyncTaskResponse.getResultObject();
					onDeliveryAddressInsertListener.onDeliveryAddressInsertSuccess(responseDeliveryAddressInsertDto);
				}
			}
		}).execute();
	}
	@Override
	public void getStateList(final OnStateRetreivalListener onStateRetreivalListener,final boolean isSerelizable) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseGetStatesDto > >(context, new INetworkAsyncTaskCallbacks<ResponseGetStatesDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onStateRetreivalListener.onStateRetreivalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseGetStatesDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseGetStatesDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetStatesDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getStates();
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseGetStatesDto> networkAsyncTaskResponse) 
			{
				onStateRetreivalListener.onStateRetreivalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onStateRetreivalListener.onStateRetreivalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseGetStatesDto responseGetStatesDto=(ResponseGetStatesDto) networkAsyncTaskResponse.getResultObject();
					onStateRetreivalListener.onStateRetreivalSuccess(responseGetStatesDto, isSerelizable);
				}
			}
		}).execute();
	}
	
	@Override
	public void getCityList(final State state, final OnCityRetreivalListener onCityRetreivalListener, final boolean isSerelizable) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseGetCitiesDto > >(context, new INetworkAsyncTaskCallbacks() 
    	{
			@Override
			public void onPreExecute() 
			{
				onCityRetreivalListener.onCityRetreivalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseGetCitiesDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseGetCitiesDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetCitiesDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getCities(state);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse networkAsyncTaskResponse) 
			{
				onCityRetreivalListener.onCityRetreivalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onCityRetreivalListener.onCityRetreivalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseGetCitiesDto responseGetCitiesDto=(ResponseGetCitiesDto) networkAsyncTaskResponse.getResultObject();
					onCityRetreivalListener.onCityRetreivalSuccess(responseGetCitiesDto, state, isSerelizable);
				}
			}
		}).execute();
	}

	@Override
	public void updateDeliveryAddress(final DeliveryAddress deliveryAddress, final OnUpdateDeliveryAddressListener onUpdateDeliveryAddressListener) {
		new NetworkAsyncTask < NetworkAsyncTaskResponse <ResponseUpdateDeliveryAddressDto> >(context, new INetworkAsyncTaskCallbacks<ResponseUpdateDeliveryAddressDto>()
		{
			@Override
			public void onPreExecute()
			{
				onUpdateDeliveryAddressListener.onUpdateDeliveryAddressStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().updateDeliveryAdresses(deliveryAddress);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto> networkAsyncTaskResponse)
			{
				onUpdateDeliveryAddressListener.onUpdateDeliveryAddressEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onUpdateDeliveryAddressListener.onUpdateDeliveryAddressError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseUpdateDeliveryAddressDto responseUpdateDeliveryAddressDto=(ResponseUpdateDeliveryAddressDto) networkAsyncTaskResponse.getResultObject();
					onUpdateDeliveryAddressListener.onUpdateDeliveryAddressSuccess(responseUpdateDeliveryAddressDto);
				}
			}
		}).execute();
	}
}
