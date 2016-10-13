package com.power2sme.android.sections.login;

import android.content.Context;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseForgotPasswordDto;
import com.power2sme.android.dtos.response.ResponseSMELoginDto;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.home.HomeFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class LoginFragmentPresentorImpl implements ILoginFragmentPresentor, OnLoginCompletedListener, OnForgotPasswordRecoveryListener 
{
	ILoginFragmentView iLoginFragmentView;
	ILoginFragmentInteractor iLoginFragmentInteractor;
	Context context;
	public LoginFragmentPresentorImpl(Context context, ILoginFragmentView iLoginFragmentView)
	{
		this.context = context;
		this.iLoginFragmentView = iLoginFragmentView;
		iLoginFragmentInteractor = new LoginFragmentInteractorImpl(context);
	}
	//////////////////////////////////////////////////////////////////////////
	@Override
	public void forgotPassword(String emailId) 
	{
		iLoginFragmentInteractor.forgotPassword(emailId, this);	
	}
	@Override
	public void onForgotPasswordRecoveryStart() 
	{
        ACLogger.log("#################### EVENT : onForgotPasswordRecoveryStart ####################");
		iLoginFragmentView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
	}
	@Override
	public void onForgotPasswordRecoveryEnd() 
	{
		iLoginFragmentView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
        ACLogger.log("#################### EVENT : onForgotPasswordRecoveryEnd ####################");
	}
	@Override
	public void onForgotPasswordRecoverySuccess(ResponseForgotPasswordDto responseForgotPasswordDto) 
	{
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.password_recovery_email_sent));
		iLoginFragmentView.showUIMessage(uiMessage, 0);
	}
	@Override
	public void onForgotPasswordRecoveryError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.error_forgot_password), 
				null
				);
		iLoginFragmentView.showUIMessage(uiMessage, 0);
	}
	//////////////////////////////////////////////////////////////////////////
	@Override
	public void loginByP2SMEAccount(String userName, String password) 
	{
		iLoginFragmentInteractor.loginByP2SMEAccount(userName, password, this);
	}
	@Override
	public void onLoginStart() 
	{
        ACLogger.log("#################### EVENT : onLoginStart ####################");
		iLoginFragmentView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
	}
	@Override
	public void onLoginEnd() 
	{
		iLoginFragmentView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
        ACLogger.log("#################### EVENT : onLoginEnd ####################");
	}
	@Override
	public void onLoginSuccess(ResponseSMELoginDto responseSMELoginDto, LoginType loginType) 
	{
		HomeFragment.outstanding=null;
		HomeFragment.updatesList=null;
		HomeFragment.dealsList=null;
		HomeFragment.newsList=null;

		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
		if(responseSMELoginDto!=null && responseSMELoginDto.getData()!=null/* && responseSMELoginDto.getData().isRegisteredInErp()*/)
		{
			Utils.saveLoginResponseCustomerInfo(context, responseSMELoginDto.getData().getCustomerLogin());
			Utils.saveLoginResponseEmployeeInfo(context, responseSMELoginDto.getData().getEmployee());
			((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("Login", "Success", loginType + "", 1);
			UIMessage m = new UIMessage(UIMessageType.SUCCESS, "");
			iLoginFragmentView.showUIMessage(m, Constants.FLAG_LOGIN);
		}
	}
	@Override
	public void onLoginError(ACError error, LoginType loginType) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.server_error),
				null
				);

		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("Login", "Falure", loginType+" - "+uiMessage.getMessage(), 1);
		iLoginFragmentView.showUIMessage(uiMessage, Constants.FLAG_LOGIN);
	}
	//////////////////////////////////////////////////////////////////////////
	@Override
	public void loginBySocialNetwork(SocialNetworkTypes socialNetworkType, String email, String firstName, String lastName) 
	{
		iLoginFragmentInteractor.loginBySocialNetwork(socialNetworkType, email, firstName, lastName, this);
	}
	//////////////////////////////////////////////////////////////////////////
	
	@Override
	public void skipToDeals() 
	{
		iLoginFragmentView.navigateToDeals();
	}
}
