package com.power2sme.android.sections.splash;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.power2sme.android.ContainerActivity;
import com.power2sme.android.GCMUtils;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.entities.UnitOfMeasure;
import com.power2sme.android.entities.v3.TaxationPreference_v3;
import com.power2sme.android.entities.v3.Urgency_v3;
import com.power2sme.android.sections.myrfqs.add.AddRFQPresentorImpl;
import com.power2sme.android.sections.myrfqs.add.IAddRFQPresentor;
import com.power2sme.android.sections.myrfqs.add.IAddRFQView;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SplashActivity extends Activity implements ISplashView,IAddRFQView
{
	private long maxSplashTime = 5 * 1000; //5 sec  ,Video Size is max 5 sec
	private long startSplashTime;
	private long endSplashTime;
	private static final String TAG="SplashActivity";
	ISplashPresentor iSplashPresentor;
	IAddRFQPresentor iAddRFQPresentor;
	private SplashVideoView mSplashVideoView;
	private int mPosition = 0;
	private String mCustomDomain=null;

	private boolean isAppInBackground=false;
	private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES=0;

	@Override
	protected void onPause() {
		isAppInBackground=true;
		super.onPause();
	}

	@Override
	protected void onResume() {
		isAppInBackground=false;
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		startSplashTime = System.currentTimeMillis();

//		Intent int1 = getIntent();
//        Bundle b = int1.getExtras();
//        if (b != null) {
//            Constants.isInternalBuild = b.getBoolean("ISINTERNALBUILD", false);
//            Constants.isDevMode = b.getBoolean("ISDEVMODE", false);
//            Constants.serverType = b.getInt("SERVERTYPE", 0);
//            mCustomDomain = b.getString("CUSTOMDOMAIN");
//        } else {
//            //Error
//            ACLogger.log("Please set the parameters in testing App");
//        }

        setContentView(R.layout.splash_activity);

		mSplashVideoView=(SplashVideoView)findViewById(R.id.video_view);
		mSplashVideoView.setZOrderOnTop(true);
		initVideoView();
		initializeLookupMarkupTable();
        if (checkPlayServices())
        {
            ((MyAccountApplication)getApplication()).getGAUtility().trackScreenView("SplashActivity");

            if(((MyAccountApplication)getApplication()).getPrefs().getBoolean("APP_FIRST_LAUNCHED", true))
            {
				if(!Utils.isTestDevice(this))
				{
					ACLogger.log("App first time launched");
					SharedPreferences.Editor editor = ((MyAccountApplication)getApplication()).getPrefs().edit();
					editor.putBoolean("APP_FIRST_LAUNCHED", false);
					editor.commit();
					((MyAccountApplication)getApplication()).getGAUtility().trackActionEvent("Application", "App Launch", "First Launch after Installation", 1);
				}
            }

            iSplashPresentor = new SplashPresentorImpl(this, this);

			if(Constants.isInternalBuild)
			{
                MyAccountApplication myAccountApplication = (MyAccountApplication) getApplication();
                SharedPreferences.Editor editor = myAccountApplication.getPrefs().edit();
                switch (Constants.serverType) {
                    case 0: //Live url is selected
                        APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID = "http://track.power2sme.com/api/v1/getOrderLocation";
                        APIs.URL_TRACKING_STATUS = "http://track.power2sme.com/api/v1/getTrackingStatus";
                        editor.putInt(Constants.PREFERENCE_BUILD_TYPE, Constants.BUILD_TYPE_LIVE);
                        editor.commit();
                        iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_LIVE);
                        break;
                    case 1: //UAT url
                        APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID = "http://uat-track.power2sme.com/api/v1/getOrderLocation";
                        APIs.URL_TRACKING_STATUS = "http://uat-track.power2sme.com/api/v1/getTrackingStatus";
                        editor.putInt(Constants.PREFERENCE_BUILD_TYPE, Constants.BUILD_TYPE_UAT);
                        editor.commit();
                        iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_UAT);
                        break;
                    case 2: //custom url
                        APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID = "http://track.power2sme.com/api/v1/getOrderLocation";
                        APIs.URL_TRACKING_STATUS = "http://track.power2sme.com/api/v1/getTrackingStatus";
                        if (mCustomDomain != null) {
                            if (mCustomDomain.startsWith("http://") || mCustomDomain.startsWith("https://")) {
                                editor.putInt(Constants.PREFERENCE_BUILD_TYPE, Constants.BUILD_TYPE_CUSTOM);
                                editor.putString(Constants.PREFERENCE_SERVER_PREFIX, mCustomDomain);
                                editor.commit();
                                showUIMessage(new UIMessage(UIMessageType.SUCCESS, ""), Constants.FLAG_APIURL_PREFIX);
                            }
                        } else {
                            showUIMessage(new UIMessage(UIMessageType.ERROR, getString(R.string.splash_domain_selector_dialog_validation_error)), Constants.FLAG_CHANGE_PASSWORD);
                        }
                        break;
                }
            }
			else
			{
				if(Constants.isDevMode)
				{
					APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID = "http://uat-track.power2sme.com/api/v1/getOrderLocation";
					APIs.URL_TRACKING_STATUS = "http://uat-track.power2sme.com/api/v1/getTrackingStatus";
					iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_UAT);
				}
				else
				{
					APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID= "http://track.power2sme.com/api/v1/getOrderLocation";
					APIs.URL_TRACKING_STATUS 				= "http://track.power2sme.com/api/v1/getTrackingStatus";
					iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_LIVE);
				}
			}
            iAddRFQPresentor = new AddRFQPresentorImpl(this, this);
        }

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				GCMUtils.initGCM(SplashActivity.this, "1");
			}
		});
	}

	@Override
	public void showProgress(ProgressTypes progressTypes, int flag) 
	{
//		containerActivity.showProgressDialog(progressTypes);
	}

	@Override
	public void hideProgress(ProgressTypes progressTypes, int flag) 
	{
//		containerActivity.hideProgressDialog(progressTypes);		
	}
	@Override
	protected void onNewIntent(Intent intent) 
	{
		this.setIntent(intent);
		super.onNewIntent(intent);
	}

//	@Override
//	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
//	{
////		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//		Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//
//	}

	String[] allPermissions = {
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.GET_ACCOUNTS,
			Manifest.permission.CALL_PHONE,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.READ_EXTERNAL_STORAGE
	};

	private String[] getNeededPermissions()
	{
		ArrayList<String> neededPermissionsList = new ArrayList<String>();
		for(String permission : allPermissions)
		{
			if (ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED)
			{
				neededPermissionsList.add(permission);
			}
		}
		String[] neededPermissionArray=new String[neededPermissionsList.size()];
		return neededPermissionsList.toArray(neededPermissionArray);
	}

	/**
     * opens application home screen
     * @param uiMessageType this message will display on home screen
     */
	@Override
	public void navigateToHome(final UIMessageType uiMessageType)
	{
		final String[] neededPermissionArray = getNeededPermissions();
		if(neededPermissionArray.length>0)
		{
			if(!SplashActivity.this.isFinishing())
			{
				new AlertDialog.Builder(this)
						.setIcon(getResources().getDrawable(R.drawable.ic_action_alert_warning))
						.setMessage("This app requires certain permissions to function properly. Please allow the usage of same in upcoming permission dialog(s).")
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								ActivityCompat.requestPermissions(SplashActivity.this, neededPermissionArray, Constants.PERMISSION_REQUEST_ALL);
							}
						})
						.show();
			}
		}
		else
		{
			_navigateToHome(uiMessageType);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		isAppInBackground=false;
		switch (requestCode)
		{
			case Constants.PERMISSION_REQUEST_ALL:
			{
				_navigateToHome(UIMessageType.SUCCESS);
				return;
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private void _navigateToHome(UIMessageType uiMessageType)
	{
		final Intent intent = new Intent ( SplashActivity.this, ContainerActivity.class );
		intent.putExtra("SUCCESS_FLAG", uiMessageType.toString());
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

		Intent intent1 = getIntent();

		String uriString = intent1.getDataString();
		if(uriString!=null)
		{
			intent.putExtra(Constants.BUNDLE_KEY_WEB_CONTENT, uriString);
		}

		Bundle bundle = intent1.getExtras();


		String pushNotifJsonData = bundle==null?null:bundle.getString("data");
		if(pushNotifJsonData!=null && pushNotifJsonData.length()>0)
		{
			intent.putExtra("data", pushNotifJsonData);
		}

		endSplashTime = System.currentTimeMillis();
		long splashTimeSlice = endSplashTime - startSplashTime;
		if((splashTimeSlice >= maxSplashTime)||(isVideoCompleted))
		{
			mSplashVideoView.HideProgessBar();
			if(!isAppInBackground)
			{
				startActivity(intent);
			}
			overridePendingTransition(0, 0);
		}
		else
		{
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					mSplashVideoView.HideProgessBar();
					if (!isAppInBackground)
						startActivity(intent);
					overridePendingTransition(0, 0);
				}
			}, (maxSplashTime - splashTimeSlice));
		}
	}

	@Override
	public void showUIMessage(UIMessage uiMessage, int flag) 
	{
		if(uiMessage.getUiMessageType()==UIMessageType.UNAUTHORIZE)
		{
			navigateToHome(UIMessageType.UNAUTHORIZE);
		}
		else if(uiMessage.getUiMessageType()==UIMessageType.SUCCESS)
		{
			if(flag==Constants.FLAG_APIURL_PREFIX)
			{
				MyAccountApplication myAccountApplication = ((MyAccountApplication) getApplicationContext());
				Constants.LoginStatus loginStatus = myAccountApplication.getLoginStatus();
				boolean goFlag=true;
				if(loginStatus!=Constants.LoginStatus.NOT_LOGGED_IN &&
						loginStatus!=Constants.LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_NOT_SELECTED_YET)
				{
					boolean isRegisteredInERP = myAccountApplication.getPrefs().getBoolean(Constants.PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP, false);
					if(!isRegisteredInERP)
					{
						String smeId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_SMEID, null);
						iSplashPresentor.isRegisteredInERP(smeId);
						goFlag=false;
					}
				}

				if(goFlag)
		        {
		        	navigateToHome(UIMessageType.SUCCESS);
		        }	
			}
			else if(flag==Constants.FLAG_ISREGISTERD_IN_ERP)
			{
				navigateToHome(UIMessageType.SUCCESS);
			}
		}
		else if(uiMessage.getUiMessageType()==UIMessageType.ERROR || uiMessage.getUiMessageType()==UIMessageType.SERVER_ERROR || uiMessage.getUiMessageType()==UIMessageType.NETWORK_NOT_AVAILABLE)
		{
			if(flag==Constants.FLAG_APIURL_PREFIX || flag==Constants.FLAG_ISREGISTERD_IN_ERP)
			{
				String msg="";
				if(!NetworkUtils.isNetworkAvailable(this))
				{
					msg = "Check your connection and try again.";
				}
				else
				{
					msg = "Our servers are under maintenance, please try after some time.";
				}
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
						.setMessage(msg)
						.setCancelable(false)
						.setPositiveButton(getString(R.string.label_continue), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});
				if(!isFinishing()){
					dialogBuilder.show();
				}
			}
		}
	}

    /**
     *  this is a table, that is used to store item catagories and subcategory list , units of measure list and other static one time downloadable content.
     */
	private void initializeLookupMarkupTable()
	{
		boolean IsInitLookupTable=true;
		Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(this);
		if(lookupMasterTable!=null)
		{
			if(lookupMasterTable.containsKey(Constants.LOOKUP_MASTER_TABLE_KEY_DATA_EXPIRY) && lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_DATA_EXPIRY)!=null)
			{
				long dataExpiry = Long.valueOf(String.valueOf(lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_DATA_EXPIRY)));
				Object lastUpdateTimeObj = lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_LAST_UPDATE_TIME);

				long lastUpdateTime = Long.valueOf(String.valueOf(lastUpdateTimeObj!=null?lastUpdateTimeObj:0l));
				if(System.currentTimeMillis() - lastUpdateTime >= dataExpiry)
				{
					lookupMasterTable.clear();
				}
				else
				{
					IsInitLookupTable = false;
				}
			}
			else
			{
				lookupMasterTable=new Hashtable<String, Object>();
			}
		}
		else
		{
			lookupMasterTable=new Hashtable<String, Object>();
		}
		if(IsInitLookupTable)
		{
			lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_LAST_UPDATE_TIME, System.currentTimeMillis());
			Utils.serializeLookupMasterTable(this, lookupMasterTable);
		}
	}

    /**
     * this method will be called if any error comes at plash screen
     */
	@Override
	public void appStartUpError() 
	{
		ACLogger.log("appStartUpError");
	}

	@Override
	public void showProgress(BetterSpinner spinner, ProgressBar progressBar, ProgressTypes progressTypes, int flag) {

	}

	@Override
	public void hideProgress(BetterSpinner spinner, ProgressBar progressBar, ProgressTypes progressTypes, int flag) {

	}

	@Override
	public void showUIMessage(BetterSpinner spinner, ProgressBar progressBar, UIMessage uiMessage, int flag) {

	}

	@Override
	public void showUnitsList(String category, BetterSpinner spinner, ProgressBar progressBar, List<UnitOfMeasure> unitsList)
	{
		if(unitsList!=null)
		{
			Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(SplashActivity.this);
			lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_UNITS_LIST+":"+category, unitsList);
			Utils.serializeLookupMasterTable(SplashActivity.this, lookupMasterTable);	
		}
	}

	@Override
	public void showUrgencyList(BetterSpinner spinner, ProgressBar progressBar, List<Urgency_v3> urgencyList)
	{
		if(urgencyList!=null)
		{
			Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(SplashActivity.this);
			lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_URGENCY_LIST, urgencyList);
			Utils.serializeLookupMasterTable(SplashActivity.this, lookupMasterTable);	
		}
	}

	@Override
	public void showTaxationPrefsList(BetterSpinner spinner, ProgressBar progressBar, List<TaxationPreference_v3> taxationPrefList)
	{
		if(taxationPrefList!=null)
		{
			Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(SplashActivity.this);
			lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_TAX_PREFERENCE_LIST, taxationPrefList);
			Utils.serializeLookupMasterTable(SplashActivity.this, lookupMasterTable);	
		}
	}

	@Override
	public void navigateToMyRFQs(String rfqNo, String message) {

	}

	@Override
	public void showItemCategoryList(BetterSpinner spinner, ProgressBar progressBar, List<String> categories) {

	}

	@Override
	public void showItemSubCategoryList(BetterSpinner spinner, ProgressBar progressBar, String category, List<String> subCategories) {

	}

	/**
     * check for gogole play service version if it is older than app needed version then show dialog for update it. if not updated then stop app.
     * @return
     */
    private boolean checkPlayServices()
    {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(status))
            {
                showErrorDialog(status);
            }
            else
            {
                Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    void showErrorDialog(int code)
    {
        GooglePlayServicesUtil.getErrorDialog(code, this, REQUEST_CODE_RECOVER_PLAY_SERVICES, new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                onBackPressed();
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_CODE_RECOVER_PLAY_SERVICES:
                if (resultCode == RESULT_CANCELED)
                {
                    Toast.makeText(this, "Google Play Services must be updated to run this app.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//we use onSaveInstanceState in order to store the video playback position for orientation change
		savedInstanceState.putInt("Position", mSplashVideoView.getCurrentPosition());
		mSplashVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		//we use onRestoreInstanceState in order to play the video playback from the stored
		mPosition = savedInstanceState.getInt("Position");
		mSplashVideoView.seekTo(mPosition);
	}
	Boolean isVideoCompleted = false;
	private void initVideoView() {

		try {
			mSplashVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash));
		} catch (Exception e) {
			e.printStackTrace();

		}
		mSplashVideoView.requestFocus();
		mSplashVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			public void onPrepared(MediaPlayer mediaPlayer) {
				mSplashVideoView.setZOrderOnTop(false);
				mediaPlayer.setLooping(true);
				mSplashVideoView.seekTo(mPosition);
				if (mPosition == 0) {
					mSplashVideoView.start();
				} else {
					mSplashVideoView.pause();
				}
				isVideoCompleted = false;
			}
		});
		mSplashVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				isVideoCompleted = true;
			}
		});
	}

}
