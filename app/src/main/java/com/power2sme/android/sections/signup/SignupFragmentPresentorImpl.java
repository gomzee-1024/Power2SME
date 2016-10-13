package com.power2sme.android.sections.signup;

import android.content.Context;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.request.RequestRegisterOrgDto;
import com.power2sme.android.dtos.response.ResponseSMESignupDto;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class SignupFragmentPresentorImpl implements ISignupFragmentPresentor, OnUserRegisteration
{
	ISignupFragmentView iSignupFragmentView;
	ISignupFragmentInteractor iSignupFragmentInteractor;
	Context context;
	public SignupFragmentPresentorImpl(Context context, ISignupFragmentView iSignupFragmentView)
	{
		this.context=context;
		this.iSignupFragmentView=iSignupFragmentView;
		iSignupFragmentInteractor=new SignupFragmentInteractorImpl(context);
	}
	//////////////////////////////////////////////////
	@Override
	public void onUserRegisterationStart() 
	{
        ACLogger.log("#################### EVENT : onUserRegisterationStart ####################");
		iSignupFragmentView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_REGISTER_USER);		
	}
	@Override
	public void onUserRegisterationEnd() 
	{
		iSignupFragmentView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_REGISTER_USER);
        ACLogger.log("#################### EVENT : onUserRegisterationEnd ####################");
	}
	@Override
	public void onUserRegisterationSuccess(ResponseSMESignupDto responseSMESignupDto) 
	{
		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
		if(responseSMESignupDto!=null && responseSMESignupDto.getData()!=null)
		{
			Utils.saveLoginResponseCustomerInfo(context, responseSMESignupDto.getData().getCustomerLogin());
			Utils.saveLoginResponseEmployeeInfo(context, responseSMESignupDto.getData().getEmployee());
		}
		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("Login", "Success", "Power2sme SignUp.", 1);
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.signup_success));
		iSignupFragmentView.showUIMessage(uiMessage, Constants.FLAG_REGISTER_USER);
	}

	@Override
	public void onUserRegisterationError(ACError error)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.signup_response_error), 
				null
				);
		
		if(error.getErrorCodes()==ACErrorCodes.EMAIL_ALREADY_REGISTERED)
		{
			uiMessage=new UIMessage(UIMessageType.ERROR, context.getString(R.string.signup_response_emailalreadyregistered));
		}
		((MyAccountApplication)((BaseAppCompatActivity)context).getApplication()).getGAUtility().trackActionEvent("Login", "Falure", "Power2sme SignUp("+uiMessage.getMessage()+")", 1);
		iSignupFragmentView.showUIMessage(uiMessage, Constants.FLAG_REGISTER_USER);		
	}
	@Override
	public void registerP2SMEAccount(RequestRegisterOrgDto registerOrgDto)
	{
		iSignupFragmentInteractor.registerP2SMEAccount(registerOrgDto, this);
	}
}
