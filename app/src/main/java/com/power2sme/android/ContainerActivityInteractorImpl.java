package com.power2sme.android;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseChangePasswordDto;
import com.power2sme.android.dtos.response.ResponseLogoutDto;
import com.power2sme.android.sections.OnPasswordChangeListener;

public class ContainerActivityInteractorImpl implements IContainerActivityInteractor 
{
	Context context;
	public ContainerActivityInteractorImpl(Context context)
	{
		this.context=context;
	}
	
	@Override
	public void logoutApp(final String emailId, final OnLogoutListener onLogoutListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseLogoutDto > >(new INetworkAsyncTaskCallbacks< ResponseLogoutDto >() 
    	{
			@Override
			public void onPreExecute() 
			{
				onLogoutListener.onLogoutStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseLogoutDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseLogoutDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseLogoutDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().logoutApp(emailId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseLogoutDto> networkAsyncTaskResponse) 
			{
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onLogoutListener.onLogoutError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseLogoutDto responseLogoutDto=(ResponseLogoutDto) networkAsyncTaskResponse.getResultObject();
					onLogoutListener.onLogoutSuccess(responseLogoutDto);
				}
				onLogoutListener.onLogoutEnd();
			}
		}).execute();
	}

	@Override
	public void changePassword(final String oldPassword, final String newPassword, final OnPasswordChangeListener onPasswordChangeListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseChangePasswordDto > >(new INetworkAsyncTaskCallbacks< ResponseChangePasswordDto >()
		{
			@Override
			public void onPreExecute()
			{
				onPasswordChangeListener.onPasswordChangeStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseChangePasswordDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseChangePasswordDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseChangePasswordDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().changePassword(oldPassword, newPassword);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseChangePasswordDto> networkAsyncTaskResponse)
			{
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onPasswordChangeListener.onPasswordChangeError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseChangePasswordDto responseLogoutDto=(ResponseChangePasswordDto) networkAsyncTaskResponse.getResultObject();
					onPasswordChangeListener.onPasswordChangeSuccess();
				}
				onPasswordChangeListener.onPasswordChangeEnd();
			}
		}).execute();
	}
}
