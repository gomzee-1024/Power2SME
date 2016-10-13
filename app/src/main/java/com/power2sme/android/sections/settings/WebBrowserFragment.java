package com.power2sme.android.sections.settings;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.utilities.logging.ACLogger;

public class WebBrowserFragment extends SuperFragment
{
	private static final String TAG="PrivacyPolicyFragment";
	View rootView;
	
	WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_WebBrowserFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_home;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
//    	baseActivity=(ContainerActivity)getActivity();
//    	baseActivity.setDrawerMenuEnabled(false, R.drawable.p2s_logo, "Power2SME");
//		baseActivity.setSubHeader(this.getClass());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.webbrowser_fragment, container, false);
//      baseActivity.setTitle(R.string.screen_title_privacy_policy);
        initUIComponents();
//		setupAdaptersAndListeners();
        setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();
        return rootView;
    }
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
        inflater.inflate(R.menu.default_blank_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	private void applyActionBarNavigationListSettings()
    {
		baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }	
	
	private void initUIComponents() 
	{
		webView = (WebView) rootView.findViewById(R.id.webView1);

		webView.getSettings().setUserAgentString(
				webView.getSettings().getUserAgentString()+ " " + getString(R.string.app_name)
		);

		webView.getSettings().setJavaScriptEnabled(true); // enable javascript
		webView.setWebViewClient(new WebViewClient() 
		{
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
//                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

		webView.setWebChromeClient(new WebChromeClient()
		{
	         public void onProgressChanged(WebView view, int progress) 
	         {
	        	 baseActivity.showProgressDialog(com.power2sme.android.ProgressTypes.INTERACTION_ALLOWED);
                 if(progress == 100)
                 {
                	 baseActivity.hideProgressDialog(com.power2sme.android.ProgressTypes.INTERACTION_ALLOWED);
                 }
	         }
		});
		webView .loadUrl(loadUrl());
	}

	private String loadUrl()
	{
		String url = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_WEB_CONTENT);
		if(url==null)
		{
			ACLogger.log("web url not found for WebBrowserFragment.");
		}
		else
		{
			url = url.replaceAll("p2s_app://", "http://");
			url = url.replaceAll("p2s_app2://", "http://");
		}

		return url;
	}
}
