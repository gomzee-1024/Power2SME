package com.power2sme.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.power2sme.android.utilities.logging.ACLogger;

public class GCMUtils 
{
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static GoogleCloudMessaging gcm;
    private static String regid;
    public 	static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static void initGCM(Activity activity, String firstLaunchFlag)
	{
        if (checkPlayServices(activity))
        {
            gcm = GoogleCloudMessaging.getInstance(activity);
            regid = getRegistrationId(activity);
            if (regid==null || regid.trim().length()==0) 
        	{
            	new GCMRegisterApp(activity, gcm, getAppVersion(activity), firstLaunchFlag).execute();
        	}
            else
            {
            	ACLogger.log("Device already Registered");
            }
        }
	}
	public static String getRegistrationId(Context context)
    {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId==null || registrationId.length()<=0) 
        {
        	ACLogger.log("Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) 
        {
            ACLogger.log("App version changed.");
            return "";
        }
        return registrationId;
    }
    private static int getAppVersion(Context context) 
    {
        try 
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } 
        catch (NameNotFoundException e) 
        {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    private static SharedPreferences getGCMPreferences(Context context) 
    {
    	return context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE);
    }
    private static boolean checkPlayServices(Activity activity) 
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) 
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) 
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity ,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } 
            else 
            {
                ACLogger.log("This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }
}
