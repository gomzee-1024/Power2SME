package com.power2sme.android.sections.smekhabar;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.sections.SuperFragment;

public class SMENewsDetailsFragment extends SuperFragment
{
	private static final String TAG="SMENewsDetailsFragment";
	View rootView;
	
	WebView webView;
	String webPage;
	private ShareActionProvider mShareActionProvider;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_SMENewsDetailsFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_sme_khabar;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
//    	baseActivity=(ContainerActivity)getActivity();
//    	baseActivity.setDrawerMenuEnabled(false, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_sme_khabar));
//		baseActivity.setSubHeader(this.getClass());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.webbrowser_fragment, container, false);
        
        webPage = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_WEB_CONTENT);
        
//        baseActivity.setTitle(R.string.screen_title_about_p2sme);
        initUIComponents();
//		setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();
        return rootView;
    }
    
    private Intent getDefaultShareIntent()
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT,"URL:"+ webPage);
        return intent;
    }
    
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
        inflater.inflate(R.menu.news_detail_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
		super.onCreateOptionsMenu(menu, inflater);
	}
    
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) 
//    {
//    	if (item.getItemId() == R.id.action_share) 
//    	{
//    		Utils.shareContent();
//        }
//    	return super.onOptionsItemSelected(item);
//    }
    
	private void applyActionBarNavigationListSettings()
    {
		baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }	
	private void initUIComponents() 
	{
		webView = (WebView) rootView.findViewById(R.id.webView1);
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
		
//		webView.loadDataWithBaseURL("", webPage, "text/html", "UTF-8", "");
		webView.loadUrl(webPage);
	}
}
