package com.power2sme.android.sections.msgscreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.power2sme.android.ContainerActivity;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.sections.SuperFragment;

public class ServerErrorFragment extends SuperFragment
{
	View rootView;
	Button Button_goToHomePage;
	Button Button_goToPreviousPage;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_ServerErrorFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_home;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
//    	baseActivity=(ContainerActivity)getActivity();
//    	baseActivity.setDrawerMenuEnabled(true, R.drawable.actionbar_icon, "");
//		baseActivity.setSubHeader(this.getClass());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.server_error_fragment, container, false);
        
        Button_goToHomePage=(Button)rootView.findViewById(R.id.Button_goToHomePage);
    	Button_goToPreviousPage=(Button)rootView.findViewById(R.id.Button_goToPreviousPage);
    	
    	Button_goToPreviousPage.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent();
				Bundle b = getFragmentDataBundle();
				int val = b.getInt(Constants.BUNDLE_KEY_MENU_ID, -1);
				intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, val);
				getActivity().setResult(Activity.RESULT_OK, intent);

				baseActivity.onBackPressed();
			}
		});
    	Button_goToHomePage.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent intent = new Intent();
				Bundle b = getFragmentDataBundle();
				int val = b.getInt(Constants.BUNDLE_KEY_MENU_ID, -1);
				intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, val);
				baseActivity.setResult(Activity.RESULT_OK, intent);
				baseActivity.finish();
				Intent containerActivityIntent = new Intent ( baseActivity, ContainerActivity.class );
				startActivity(containerActivityIntent);
			}
		});


		Intent intent = new Intent();
		Bundle b = getFragmentDataBundle();
		int val = b.getInt(Constants.BUNDLE_KEY_MENU_ID, -1);
		intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, val);
		getActivity().setResult(Activity.RESULT_CANCELED, intent);

		return rootView;
    }
}
