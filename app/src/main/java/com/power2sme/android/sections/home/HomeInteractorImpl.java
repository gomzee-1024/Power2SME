package com.power2sme.android.sections.home;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseAccountSummeryDto;
import com.power2sme.android.dtos.response.ResponseOutstandingDto;

public class HomeInteractorImpl implements IHomeInteractor 
{
	Context context;
	public HomeInteractorImpl(Context context)
	{
		this.context=context;
	}
	
	@Override
	public void getOutstanding(final String strsmeid, final OnOutstandingLoadingListener onOutstandingLoadingListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse <ResponseOutstandingDto> >(context, new INetworkAsyncTaskCallbacks<ResponseOutstandingDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onOutstandingLoadingListener.onOutstandingLoadingStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseOutstandingDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseOutstandingDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseOutstandingDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getOutstandingAmout(strsmeid);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseOutstandingDto> networkAsyncTaskResponse) 
			{
				onOutstandingLoadingListener.onOutstandingLoadingEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onOutstandingLoadingListener.onOutstandingLoadingError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseOutstandingDto responseOutstandingDto=(ResponseOutstandingDto) networkAsyncTaskResponse.getResultObject();
					onOutstandingLoadingListener.onOutstandingLoadingSuccess(responseOutstandingDto);
				}
			}
		}).execute();
	}

	@Override
	public void sendAccountSummeryRequest(final OnAccountSummeryRequestListener onAccountSummeryRequestListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse <ResponseAccountSummeryDto> >(context, new INetworkAsyncTaskCallbacks<ResponseAccountSummeryDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onAccountSummeryRequestListener.onAccountSummeryRequestStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseAccountSummeryDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseAccountSummeryDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAccountSummeryDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getAccountSummery();
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseAccountSummeryDto> networkAsyncTaskResponse) 
			{
				onAccountSummeryRequestListener.onAccountSummeryRequestEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onAccountSummeryRequestListener.onAccountSummeryRequestError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseAccountSummeryDto responseAccountSummeryDto=(ResponseAccountSummeryDto) networkAsyncTaskResponse.getResultObject();
					onAccountSummeryRequestListener.onAccountSummeryRequestSuccess(responseAccountSummeryDto);
				}
			}
		}).execute();		
	}
}
