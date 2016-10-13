package com.power2sme.android;

import android.content.Context;

import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseLogoutDto;
import com.power2sme.android.sections.IBaseView;
import com.power2sme.android.sections.OnPasswordChangeListener;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class ContainerActivityPresentorImpl implements IContainerActivityPresentor, OnLogoutListener, OnPasswordChangeListener
{
	Context context;
	IBaseView iContainerActivityView;
	IContainerActivityInteractor iContainerActivityInteractor;
	public ContainerActivityPresentorImpl(Context context, IBaseView iContainerActivityView)
	{
		this.context=context;
		this.iContainerActivityView=iContainerActivityView;
		iContainerActivityInteractor=new ContainerActivityInteractorImpl(context);
	}
	@Override
	public void logoutApp(String emailId)
	{
		iContainerActivityInteractor.logoutApp(emailId, this);
	}
	@Override
	public void onLogoutStart() 
	{
        ACLogger.log("#################### EVENT : onLogoutStart ####################");
		iContainerActivityView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_LOGOUT);
	}
	@Override
	public void onLogoutEnd() 
	{
		iContainerActivityView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_LOGOUT);
        ACLogger.log("#################### EVENT : onLogoutEnd ####################");
	}
	@Override
	public void onLogoutSuccess(ResponseLogoutDto responseLogoutDto) 
	{
		iContainerActivityView.showUIMessage(new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.home_logout_success)), Constants.FLAG_LOGOUT);
	}
	@Override
	public void onLogoutError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.home_logout_error),
				null
				);
		iContainerActivityView.showUIMessage(uiMessage, Constants.FLAG_LOGOUT);
	}

	////////////////////////////////////////////////////////////

	@Override
	public void changePassword(String oldPassword, String newPassword)
	{
		iContainerActivityInteractor.changePassword(oldPassword, newPassword, this);
	}

	@Override
	public void onPasswordChangeStart() {
		ACLogger.log("#################### EVENT : onPasswordChangeStart ####################");
		iContainerActivityView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_CHANGE_PASSWORD);
	}

	@Override
	public void onPasswordChangeEnd() {
		iContainerActivityView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_CHANGE_PASSWORD);
		ACLogger.log("#################### EVENT : onPasswordChangeEnd ####################");
	}

	@Override
	public void onPasswordChangeSuccess()
	{
		iContainerActivityView.showUIMessage(new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.password_change_success)), Constants.FLAG_CHANGE_PASSWORD);
	}

	@Override
	public void onPasswordChangeError(ACError error)
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.password_change_error),
				null
		);
		iContainerActivityView.showUIMessage(uiMessage, Constants.FLAG_CHANGE_PASSWORD);
	}
}
