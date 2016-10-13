package com.power2sme.android.sections.settings;

import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.sections.SuperFragment;

public class AboutUsFragment extends SuperFragment
{
	private static final String TAG="AboutP2SMEFragment";
	TextView TextView_aboutPower2SME;
	TextView TextView_privacyPolicy;
	TextView TextView_termsAndConditions;
	TextView TextView_rateThisApp;
	
	View rootView;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_AboutUsFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_about_power2sme;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
//    	baseActivity=(ContainerActivity)getActivity();
//    	baseActivity.setDrawerMenuEnabled(true, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_about_power2sme));
//		baseActivity.setSubHeader(this.getClass());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.about_us_fragment, container, false);
        initUIComponents();
		setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();
        return rootView;
    }
    
    private void setupAdaptersAndListeners() 
    {
    	TextView_aboutPower2SME.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
//				baseActivity.openSectionChildFragment(getString(R.string.fragment_about_power2sme), null, true);
				baseActivity.openChildActivityFragment(AboutP2SMEFragment.class, null, true, true, false);
			}
		});
    	TextView_privacyPolicy.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
//				baseActivity.openSectionChildFragment(getString(R.string.fragment_privacy_policy), null, true);
				baseActivity.openChildActivityFragment(PrivacyPolicyFragment.class, null, true, true, false);
			}
		});
    	TextView_termsAndConditions.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
//				baseActivity.openSectionChildFragment(getString(R.string.fragment_terms_and_condition), null, true);
				baseActivity.openChildActivityFragment(TermsAndConditionsFragment.class, null, true, true, false);
			}
		});
    	TextView_rateThisApp.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
    			try
    			{
    				String packageName = v.getContext().getApplicationContext().getPackageName();
    				v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
    			}
    			catch(ActivityNotFoundException anfe)
    			{
					baseActivity.openChildActivityFragment(RateThisAppFragment.class, null, true, true, false);
    			}
			}
		});		
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
		TextView_aboutPower2SME = (TextView) rootView.findViewById(R.id.TextView_aboutPower2SME);
		TextView_privacyPolicy = (TextView) rootView.findViewById(R.id.TextView_privacyPolicy);
		TextView_termsAndConditions = (TextView) rootView.findViewById(R.id.TextView_termsAndConditions);
		TextView_rateThisApp = (TextView) rootView.findViewById(R.id.TextView_rateThisApp);
	}
}
