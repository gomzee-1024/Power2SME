package com.power2sme.android.sections.market;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.sections.SuperFragment;

public class MarketPlaceHomeFragment extends SuperFragment
{
	private static final String TAG="MarketPlaceHomeFragment";
	View rootView;
	
	WebView webView;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_MarketPlaceHomeFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_market_place_home;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
//    	baseActivity=(ContainerActivity)getActivity();
//    	baseActivity.setDrawerMenuEnabled(false, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_market_place_home));
//		baseActivity.setSubHeader(this.getClass());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.webbrowser_fragment, container, false);
//        baseActivity.setTitle(R.string.screen_title_about_p2sme);
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
//		webView.getSettings().setJavaScriptEnabled(true); // enable javascript
//		webView.setWebViewClient(new WebViewClient()
//		{
//			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
//			{
////                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
//			}
//		});
//		webView.setWebChromeClient(new WebChromeClient()
//		{
//			public void onProgressChanged(WebView view, int progress)
//			{
//				baseActivity.showProgressDialog(com.power2sme.android.ProgressTypes.INTERACTION_ALLOWED);
//				if (progress == 100)
//				{
//					baseActivity.hideProgressDialog(com.power2sme.android.ProgressTypes.INTERACTION_ALLOWED);
//				}
//			}
//		});
//		webView .loadUrl(APIs.URL_MARKET_PLACE);




//		webView .loadUrl("http://uat-marketplace.power2sme.com/product-type-en/");

//////////////////////////////////////////////////////////////////////////////
//		String html = "<html><head><style>#header, .tygh-top-panel, #floating-social-icons, .footerarea {display:none; visibility: hidden;}</style></head>";
//
//		webView.setWebViewClient(new WebViewClient() {
//			@Override
//			public void onReceivedError(WebView view, int errorCode,
//										String description, String failingUrl) {
//				// Handle the error
//			}
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//		});
//
//		webView.setInitialScale(1);
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setUseWideViewPort(true);
//		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//		webView.setScrollbarFadingEnabled(false);
//		webView.loadUrl("http://uat-marketplace.power2sme.com/product-type-en/");
//		webView.loadData(html, "text/html", "utf-8");

//		///////////////////////////////////////////////////////////////////////////////
		webView.setBackgroundColor(Color.parseColor("#ffffff"));
		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webView.loadUrl(url);
				return false;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				webView.loadUrl("javascript:document.getElementsByTagName('html')[0].innerHTML+='<style>#header, .tygh-top-panel, #floating-social-icons, .footerarea {display:none; visibility: hidden;}</style>';");

			}
		});
		webView.loadUrl(APIs.URL_MARKET_PLACE);
	}
}
