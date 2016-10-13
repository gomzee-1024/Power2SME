package com.power2sme.android.sections.splash;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseGetServerPrefixDto;
import com.power2sme.android.dtos.response.ResponseIsRegisterdInERPDto;

public class SplashInteractorImpl implements ISplashInteractor
{
	Context context;
	public SplashInteractorImpl(Context context)
	{
		this.context=context;
	}

	@Override
	public void getAPIUrlPrefix(final String prefixApiUrl, final OnServerPrefixLoadingListener onServerPrefixLoadingListener)
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseGetServerPrefixDto>>(new INetworkAsyncTaskCallbacks<ResponseGetServerPrefixDto>()
    	{
			@Override
			public void onPreExecute()
			{
				onServerPrefixLoadingListener.onServerPrefixLoadingStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetServerPrefixDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getServerPrefix(prefixApiUrl);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> networkAsyncTaskResponse)
			{
				onServerPrefixLoadingListener.onServerPrefixLoadingEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onServerPrefixLoadingListener.onServerPrefixLoadingError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseGetServerPrefixDto responseGetServerPrefixDto=(ResponseGetServerPrefixDto) networkAsyncTaskResponse.getResultObject();
					onServerPrefixLoadingListener.onServerPrefixLoadingSuccess(responseGetServerPrefixDto);
				}
			}
		}).execute();
	}
	@Override
	public void isRegisteredInERP(final String smeId, final OnIsRegisteredInERPCheckingListener onIsRegisteredInERPCheckingListener) 
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto>>(new INetworkAsyncTaskCallbacks<ResponseIsRegisterdInERPDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onIsRegisteredInERPCheckingListener.onIsRegisteredInERPCheckingStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().isRegisterdInERP(smeId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto> networkAsyncTaskResponse) 
			{
				onIsRegisteredInERPCheckingListener.onIsRegisteredInERPCheckingEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onIsRegisteredInERPCheckingListener.onIsRegisteredInERPCheckingError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseIsRegisterdInERPDto responseIsRegisterdInERPDto=(ResponseIsRegisterdInERPDto) networkAsyncTaskResponse.getResultObject();
					onIsRegisteredInERPCheckingListener.onIsRegisteredInERPCheckingSuccess(responseIsRegisterdInERPDto);
				}
			}
		}).execute();
	}
}
