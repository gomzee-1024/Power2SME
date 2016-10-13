package com.power2sme.android.sections.login;

import android.content.Context;

import com.power2sme.android.R;
import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseForgotPasswordDto;
import com.power2sme.android.dtos.response.ResponseSMELoginDto;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;

public class LoginFragmentInteractorImpl implements ILoginFragmentInteractor 
{
	Context context;
	public LoginFragmentInteractorImpl(Context context)
	{
		this.context=context;
	}
	@Override
	public boolean validateCredentials(String userName, String password, OnLoginCredentialsValidateListener onLoginCredentialsValidateListener) 
	{
		onLoginCredentialsValidateListener.onLoginCredentialsValidationStart();
		//TODO define regex pattern for password validation at login screen
		if((userName!=null && userName.trim().length()>0) && 
				(password!=null && password.trim().length()>0))
		{
			onLoginCredentialsValidateListener.onLoginCredentialsValidationEnd();
			onLoginCredentialsValidateListener.onValidLoginCredentials();
			return true;
		}
		else
		{
			ACError error=new ACError(ACErrorCodes.INVALID_LOGIN_CREDENTIALS, context.getString(R.string.invalid_login_credentials));
			onLoginCredentialsValidateListener.onLoginCredentialsValidationEnd();
			onLoginCredentialsValidateListener.onInvalidLoginCredentials(error);
			return false;
		}
	}

	@Override
	public void loginByP2SMEAccount(final String userName, final String password,final OnLoginCompletedListener onLoginCompletedListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseSMELoginDto > >(context, new INetworkAsyncTaskCallbacks() 
    	{
			@Override
			public void onPreExecute() 
			{
				onLoginCompletedListener.onLoginStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseSMELoginDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseSMELoginDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMELoginDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().smeLogin(userName, password);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse networkAsyncTaskResponse) 
			{
				onLoginCompletedListener.onLoginEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onLoginCompletedListener.onLoginError(networkAsyncTaskResponse.getError(), LoginType.POWER2SME_LOGIN);
				}
				else
				{
					ResponseSMELoginDto responseSMELoginDto=(ResponseSMELoginDto) networkAsyncTaskResponse.getResultObject();
					onLoginCompletedListener.onLoginSuccess(responseSMELoginDto, LoginType.POWER2SME_LOGIN);
				}
			}
		}).execute();
	}

	@Override
	public void loginBySocialNetwork(final SocialNetworkTypes socialNetworkTypes, final String email, final String firstName, final String lastName,final OnLoginCompletedListener onLoginCompletedListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseSMELoginDto > >(context, new INetworkAsyncTaskCallbacks() 
    	{
			@Override
			public void onPreExecute() 
			{
				onLoginCompletedListener.onLoginStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseSMELoginDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseSMELoginDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMELoginDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().loginBySocialNetwork(socialNetworkTypes.toString(), email, firstName, lastName);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse networkAsyncTaskResponse) 
			{
				onLoginCompletedListener.onLoginEnd();
				
				LoginType loginType = null;
				if(socialNetworkTypes == SocialNetworkTypes.FACEBOOK)
				{
					loginType=LoginType.FACEBOOK;
				}
				else if(socialNetworkTypes == SocialNetworkTypes.GOOGLE)
				{
					loginType=LoginType.GOOGLE;
				}
					
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onLoginCompletedListener.onLoginError(networkAsyncTaskResponse.getError(), loginType);
				}
				else
				{
					ResponseSMELoginDto responseSMELoginDto=(ResponseSMELoginDto) networkAsyncTaskResponse.getResultObject();
					onLoginCompletedListener.onLoginSuccess(responseSMELoginDto, loginType);
				}
			}
		}).execute();
	}

	@Override
	public void forgotPassword(final String userName, final OnForgotPasswordRecoveryListener onForgotPasswordCompletedListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseForgotPasswordDto > >(context, new INetworkAsyncTaskCallbacks() 
    	{
			@Override
			public void onPreExecute() 
			{
				onForgotPasswordCompletedListener.onForgotPasswordRecoveryStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseForgotPasswordDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseForgotPasswordDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseForgotPasswordDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().forgotPassword(userName);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse networkAsyncTaskResponse) 
			{
				onForgotPasswordCompletedListener.onForgotPasswordRecoveryEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onForgotPasswordCompletedListener.onForgotPasswordRecoveryError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseForgotPasswordDto responseForgotPasswordDto=(ResponseForgotPasswordDto) networkAsyncTaskResponse.getResultObject();
					onForgotPasswordCompletedListener.onForgotPasswordRecoverySuccess(responseForgotPasswordDto);
				}
			}
		}).execute();
	}

	@Override
	public boolean validateUserName(String userName, OnUserNameValidateListener onUserNameValidateListener) 
	{
		onUserNameValidateListener.onUserNameValidationStart();
		//#TODO create pattern for validation of user name
		if(userName!=null && userName.length()>0)
		{
			onUserNameValidateListener.onUserNameValidationEnd();
			onUserNameValidateListener.onUserNameValidationSuccess();
			return true;
		}
		else
		{
			ACError error=new ACError(ACErrorCodes.INVALID_USERNAME, context.getString(R.string.invalid_username));
			onUserNameValidateListener.onUserNameValidationEnd();
			onUserNameValidateListener.onUserNameValidationError(error);
			return false;
		}
	}

	@Override
	public boolean validatePassword(String password, OnPasswordValidateListener onPasswordValidateListener) 
	{
		onPasswordValidateListener.onPasswordValidationStart();
		//#TODO create pattern for validation of password
		if(password!=null && password.length()>0)
		{
			onPasswordValidateListener.onPasswordValidationEnd();
			onPasswordValidateListener.onPasswordValidationSuccess();
			return true;
		}
		else
		{
			ACError error=new ACError(ACErrorCodes.INVALID_PASSWORD, context.getString(R.string.invalid_password));
			onPasswordValidateListener.onPasswordValidationEnd();
			onPasswordValidateListener.onPasswordValidationError(error);
			return false;
		}
	}
}
