package com.power2sme.android.sections.msgscreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.home.HomeFragment;
import com.power2sme.android.sections.splash.ISplashPresentor;
import com.power2sme.android.sections.splash.ISplashView;
import com.power2sme.android.sections.splash.SplashPresentorImpl;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;

public class NoInternetFragment extends SuperFragment implements ISplashView
{
	View rootView;
	Button Button_retryForInternet;
	TextView TextView_noInternetMessage;
	ISplashPresentor iSplashPresentor;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_NoInternetFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return -1;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.nointernet_fragment, container, false);
        iSplashPresentor = new SplashPresentorImpl(baseActivity, this);

        String fragmentMessage = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_MESSAGE);
        if(fragmentMessage!=null)
        {
        	TextView_noInternetMessage=(TextView)rootView.findViewById(R.id.TextView_noInternetMessage);
        	TextView_noInternetMessage.setText(fragmentMessage);
        }

        Button_retryForInternet=(Button)rootView.findViewById(R.id.Button_retryForInternet);
        Button_retryForInternet.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				String targetFragmentName = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_NAME);
				if(NetworkUtils.isNetworkAvailable(baseActivity)){
					if(targetFragmentName!=null && targetFragmentName.equals(HomeFragment.class.getName()))
					{
						MyAccountApplication myAccountApplication = (MyAccountApplication)getActivity().getApplication();
						int buildType = myAccountApplication.getPrefs().getInt(Constants.PREFERENCE_BUILD_TYPE, -1);
						if(buildType==Constants.BUILD_TYPE_LIVE)
						{
							iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_LIVE);
						}
						else if(buildType==Constants.BUILD_TYPE_UAT)
						{
							iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_UAT);
						}
						else if(buildType==Constants.BUILD_TYPE_CUSTOM)
						{
//							String customPrefixUrl = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_SERVER_PREFIX, null);
							showUIMessage(new UIMessage(UIMessageType.SUCCESS, ""), Constants.FLAG_APIURL_PREFIX);
						}

					}
					else
					{
						navigateToHome(UIMessageType.SUCCESS);
					}
				}else{
					appStartUpError();
				}
			}
		});

		Intent intent = new Intent();
		Bundle b = getFragmentDataBundle();
		int val = b.getInt(Constants.BUNDLE_KEY_MENU_ID, -1);
		intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, val);
		getActivity().setResult(Activity.RESULT_CANCELED, intent);

        return rootView;
    }

	@Override
	public void showProgress(ProgressTypes progressTypes, int flag)
	{
		baseActivity.showProgressDialog(progressTypes);
	}

	@Override
	public void hideProgress(ProgressTypes progressTypes, int flag)
	{
		baseActivity.hideProgressDialog(progressTypes);
	}

	@Override
	public void showUIMessage(UIMessage uiMessage, int flag)
	{
		if(!isAdded())
		{
			return;
		}
		if(uiMessage.getUiMessageType()==UIMessageType.UNAUTHORIZE)
		{
			Utils.showSessionInvalidateDialog(baseActivity, null);
		}
		else if(uiMessage.getUiMessageType()==UIMessageType.SUCCESS)
		{
			if(flag==Constants.FLAG_APIURL_PREFIX)
			{
				MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
		        String smeId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_SMEID, null);
		        if(smeId!=null)
		        {
		        	iSplashPresentor.isRegisteredInERP(smeId);
		        }
		        else
		        {
		        	navigateToHome(UIMessageType.SUCCESS);
		        }
			}
			else if(flag==Constants.FLAG_ISREGISTERD_IN_ERP)
			{
				navigateToHome(UIMessageType.SUCCESS);
			}
		}
		else if(uiMessage.getUiMessageType()==UIMessageType.ERROR)
		{
			if(flag==Constants.FLAG_APIURL_PREFIX)
			{
				UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
				navigateToHome(UIMessageType.SUCCESS);
			}
			else if(flag==Constants.FLAG_ISREGISTERD_IN_ERP)
			{
				UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
			}
			else
			{
				UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
			}
		}
	}

	@Override
	public void navigateToHome(UIMessageType uiMessageType)
	{
		String targetFragmentName = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_NAME);
		if(targetFragmentName!=null && targetFragmentName.equals(HomeFragment.class.getName()))
		{
			Bundle bun = getFragmentDataBundle();
			boolean isAddToBackStack = getFragmentDataBundle().getBoolean(Constants.BUNDLE_KEY_IS_ADD_TO_BACKSTACK, false);
			boolean isCheckNetwork = getFragmentDataBundle().getBoolean(Constants.BUNDLE_KEY_IS_CHECK_NETWORK, false);
			boolean isClearBackstack = getFragmentDataBundle().getBoolean(Constants.BUNDLE_KEY_IS_CLEAR_BACKSTACK, false);
			Class targetFragmentClass= HomeFragment.class;
			try
			{
				targetFragmentClass = Class.forName(targetFragmentName);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			baseActivity.openContainerActivityFragment(targetFragmentClass, bun, isAddToBackStack, isCheckNetwork, isClearBackstack);
		}
		else
		{
			baseActivity.onBackPressed();
		}

	}

	@Override
	public void appStartUpError()
	{
		showUIMessage(new UIMessage(UIMessageType.ERROR, "Please check your Network connection."), 0);
	}
}
