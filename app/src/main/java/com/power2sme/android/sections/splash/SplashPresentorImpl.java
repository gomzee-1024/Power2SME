package com.power2sme.android.sections.splash;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseGetServerPrefixDto;
import com.power2sme.android.dtos.response.ResponseIsRegisterdInERPDto;
import com.power2sme.android.entities.AndroidServerConf;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

public class SplashPresentorImpl implements ISplashPresentor, OnServerPrefixLoadingListener, OnIsRegisteredInERPCheckingListener
{
	ISplashInteractor iSplashInteractor;
	ISplashView iSplashView;
	Context context;
	public SplashPresentorImpl(Context context, ISplashView iSplashView)
	{
		this.context=context;
		this.iSplashView=iSplashView;
		this.iSplashInteractor = new SplashInteractorImpl(context);
	}
	
	@Override
	public void isRegisteredInERP(String smeId) 
	{
		iSplashInteractor.isRegisteredInERP(smeId, this);		
	}
	
	@Override
	public void onIsRegisteredInERPCheckingStart() 
	{
        ACLogger.log("#################### EVENT : onIsRegisteredInERPCheckingStart ####################");
    }
	@Override
	public void onIsRegisteredInERPCheckingEnd() 
	{
        ACLogger.log("#################### EVENT : onIsRegisteredInERPCheckingEnd ####################");
    }
	@Override
	public void onIsRegisteredInERPCheckingSuccess(ResponseIsRegisterdInERPDto responseIsRegisterdInERPDto) 
	{
		if(responseIsRegisterdInERPDto!=null && responseIsRegisterdInERPDto.getData()!=null)
		{
			MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
			Editor editor = myAccountApplication.getPrefs().edit();
			editor.putBoolean(Constants.PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP, responseIsRegisterdInERPDto.getData().isIsRegisterdInERP());
			editor.commit();
			iSplashView.showUIMessage(new UIMessage(UIMessageType.SUCCESS, ""), Constants.FLAG_ISREGISTERD_IN_ERP);
		}
		else
		{
			onIsRegisteredInERPCheckingError(new ACError(ACErrorCodes.SERVER_ERROR, context.getString(R.string.server_error)));
		}
	}
	@Override
	public void onIsRegisteredInERPCheckingError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				error.getMessage(), 
				null
				);
		
		iSplashView.showUIMessage(uiMessage, Constants.FLAG_ISREGISTERD_IN_ERP);
	}

	////////////////////////////////////////////////////////////////////////
    @Override
    public void getAPIUrlPrefix(String prefixApiUrl)
    {
        iSplashInteractor.getAPIUrlPrefix(prefixApiUrl, this);
    }

	@Override
	public void onServerPrefixLoadingStart() 
	{
        ACLogger.log("#################### EVENT : onServerPrefixLoadingStart ####################");
		iSplashView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
    }

	@Override
	public void onServerPrefixLoadingEnd() 
	{
        ACLogger.log("#################### EVENT : onServerPrefixLoadingEnd ####################");
		iSplashView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
    }

	@Override
	public void onServerPrefixLoadingSuccess(ResponseGetServerPrefixDto responseGetServerPrefixDto) 
	{
		if(responseGetServerPrefixDto!=null && responseGetServerPrefixDto.getData()!=null)
		{
			MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
			Editor edt = myAccountApplication.getPrefs().edit();

			AndroidServerConf androidServerConf = responseGetServerPrefixDto.getData().getAndroidServerConf();
			if(androidServerConf!=null)
			{
				edt.putString(Constants.PREFERENCE_SERVER_APP_MAX_VERSION, androidServerConf.getMaxversion());
				edt.putString(Constants.PREFERENCE_SERVER_APP_MIN_VERSION, androidServerConf.getMinversion());
				edt.putString(Constants.PREFERENCE_SERVER_PREFIX, androidServerConf.getServerPrefix());
			}

			edt.commit();
			iSplashView.showUIMessage(new UIMessage(UIMessageType.SUCCESS, ""), Constants.FLAG_APIURL_PREFIX);
		}
	}

	@Override
	public void onServerPrefixLoadingError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				error.getMessage(), 
				null
				);
		
		iSplashView.showUIMessage(uiMessage, Constants.FLAG_APIURL_PREFIX);
	}
}

