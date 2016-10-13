package com.power2sme.android.utilities.gcm;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseDeviceIdRegisterDto;

public class GCMInteractorImpl implements IGCMInteractor 
{
	Context context;
	public GCMInteractorImpl(Context context)
	{
		this.context=context;
	}
	
	@Override
	public void registerDeviceRegisterationId(final String regId, final OnDeviceIdRegisterListener onDeviceIdRegisterListener,final String firstLaunchFlag)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseDeviceIdRegisterDto > >(new INetworkAsyncTaskCallbacks< ResponseDeviceIdRegisterDto >() 
    	{
			@Override
			public void onPreExecute() 
			{
				onDeviceIdRegisterListener.onDeviceIdRegisterStart();
			}
			@Override
			public NetworkAsyncTaskResponse< ResponseDeviceIdRegisterDto > doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().registerDeviceRegisterationId(regId, firstLaunchFlag);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse< ResponseDeviceIdRegisterDto > networkAsyncTaskResponse) 
			{
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDeviceIdRegisterListener.onDeviceIdRegisterError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseDeviceIdRegisterDto responseDeviceIdRegisterDto=(ResponseDeviceIdRegisterDto) networkAsyncTaskResponse.getResultObject();
					onDeviceIdRegisterListener.onDeviceIdRegisterSuccess(responseDeviceIdRegisterDto);
				}
				onDeviceIdRegisterListener.onDeviceIdRegisterEnd();
			}
		}).execute();
	}
}
