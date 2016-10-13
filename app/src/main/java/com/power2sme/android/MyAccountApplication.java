package com.power2sme.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.utilities.analytics.GAUtility;
import com.power2sme.android.utilities.gpstracker.GPSTracker;
import com.power2sme.android.utilities.styels.StylesManager;

import io.fabric.sdk.android.Fabric;

//import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyAccountApplication extends MultiDexApplication
{
	private GAUtility gaUtility;
	public GPSTracker gpsTracker;

	public static final String TAG = MyAccountApplication.class
			.getSimpleName();

	private RequestQueue mRequestQueue;

	private static MyAccountApplication mInstance;

	@Override
	public void onCreate()
	{
		super.onCreate();

		//send crash reports to fabric only when app is not in debug mode
		if(!Constants.isDevMode) {
			Fabric.with(this, new Crashlytics());
		}
		StylesManager.getInstance(this).initTypefaces();
		gaUtility=new GAUtility(getApplicationContext());
//		initImageLoader(this);
		mInstance=this;

	}

	public static synchronized MyAccountApplication getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}


	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}


	public double getUserCurrentLatitude()
	{
		if(gpsTracker.canGetLocation())
		{
			return gpsTracker.getLatitude();
		}
		return 0d;
	}
	public double getUserCurrentLongitude()
	{
		if(gpsTracker.canGetLocation())
		{
			return gpsTracker.getLongitude();
		}
		return 0d;
	}
	public void initGPSTracker(Context context)
	{
		gpsTracker = new GPSTracker(context);
		if(!gpsTracker.canGetLocation())
		{
			//gpsTracker.showSettingsAlert();
		}
	}

	public SharedPreferences getPrefs()
	{
		return getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
	}

	public GAUtility getGAUtility()
	{
		return gaUtility;
	}
//	public static void initImageLoader(Context context) {
//		// This configuration tuning is custom. You can tune every option, you may tune some of them,
//		// or you can create default configuration by
//		//  ImageLoaderConfiguration.createDefault(this);
//		// method.
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//				.threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory()
//				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
//				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
//				.tasksProcessingOrder(QueueProcessingType.LIFO)
//				.writeDebugLogs() // Remove for release app
//				.build();
//		// Initialize ImageLoader with configuration.
//		ImageLoader.getInstance().init(config);
//	}


	public boolean isKAM()
	{
		return getPrefs().getBoolean(Constants.PREFERENCE_IS_KAM, false);
	}

	public Constants.LoginStatus getLoginStatus()
	{
		MyAccountApplication myAccountApplication = ((MyAccountApplication) getApplicationContext());
		SharedPreferences prefs = myAccountApplication.getPrefs();

		String smeId = prefs.getString(Constants.PREFERENCE_CUSTOMER_SMEID, null);
		String agentEmailId = prefs.getString(Constants.PREFERENCE_AGENT_EMAIL_ID, null);
		boolean isRegisteredInERP = prefs.getBoolean(Constants.PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP, false);

		Constants.LoginStatus loginStatus = null;

		if(agentEmailId!=null && agentEmailId.length()>0)
		{
			if(smeId!=null && smeId.length()>0)
			{
				loginStatus = Constants.LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_SELECTED;
			}
			else
			{
				loginStatus = Constants.LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_NOT_SELECTED_YET;
			}
		}
		else if(smeId!=null && smeId.length()>0 && isRegisteredInERP)
		{
			loginStatus = Constants.LoginStatus.SMEID_WITH_ERPREGISTERATION;
		}
		else if(smeId!=null && smeId.length()>0 && !isRegisteredInERP)
		{
			loginStatus = Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION;
		}
		else
		{
			loginStatus = Constants.LoginStatus.NOT_LOGGED_IN;
		}
		return loginStatus;
	}

	@Override
	protected void attachBaseContext(Context base)
	{
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
