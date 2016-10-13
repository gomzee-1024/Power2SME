package com.power2sme.android.sections.accountupdates;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseGetAccountUpdatesDto;

public class AccountUpdatesInteractorImpl implements IAccountUpdatesInteractor 
{
	Context context;
	public AccountUpdatesInteractorImpl(Context context)
	{
		this.context=context;
	}
	@Override
	public void getAccountUpdates(final long currentPage, final long PageSize, final String strsmeid, final boolean isLoadmore, final OnAccountUpdatesRetrievalListenere onAccountUpdatesRetrievalListenere)
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto>>(context, new INetworkAsyncTaskCallbacks<ResponseGetAccountUpdatesDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onAccountUpdatesRetrievalListenere.onAccountUpdatesRetrievalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getAccountUpdates(currentPage, PageSize, strsmeid);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto> networkAsyncTaskResponse) 
			{
				onAccountUpdatesRetrievalListenere.onAccountUpdatesRetrievalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onAccountUpdatesRetrievalListenere.onAccountUpdatesRetrievalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto=(ResponseGetAccountUpdatesDto) networkAsyncTaskResponse.getResultObject();
					onAccountUpdatesRetrievalListenere.onAccountUpdatesRetrievalSuccess(responseGetAccountUpdatesDto, isLoadmore);
				}
			}
		}).execute();
	}
}
