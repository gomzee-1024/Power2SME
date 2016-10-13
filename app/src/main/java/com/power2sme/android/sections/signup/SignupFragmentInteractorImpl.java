package com.power2sme.android.sections.signup;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.request.RequestRegisterOrgDto;
import com.power2sme.android.dtos.response.ResponseSMESignupDto;

public class SignupFragmentInteractorImpl implements ISignupFragmentInteractor 
{
	Context context;
	public SignupFragmentInteractorImpl(Context context)
	{
		this.context=context;
	}

	@Override
	public void registerP2SMEAccount(final RequestRegisterOrgDto registerOrgDto,final OnUserRegisteration onUserRegisteration)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseSMESignupDto > >(context, new INetworkAsyncTaskCallbacks<ResponseSMESignupDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onUserRegisteration.onUserRegisterationStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseSMESignupDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseSMESignupDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMESignupDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().smeSignup(registerOrgDto);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseSMESignupDto> networkAsyncTaskResponse) 
			{
				onUserRegisteration.onUserRegisterationEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onUserRegisteration.onUserRegisterationError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseSMESignupDto responseSMESignupDto=(ResponseSMESignupDto) networkAsyncTaskResponse.getResultObject();
					onUserRegisteration.onUserRegisterationSuccess(responseSMESignupDto);
				}
			}
		}).execute();
	}
}
