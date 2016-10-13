package com.power2sme.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.power2sme.android.utilities.gcm.GCMPresentorImpl;
import com.power2sme.android.utilities.gcm.IGCMPresentor;
import com.power2sme.android.utilities.gcm.IGCMView;
import com.power2sme.android.utilities.logging.ACLogger;

import java.io.IOException;
 
 
public class GCMRegisterApp extends AsyncTask<Void, Void, String> implements IGCMView
{
private static final String TAG = "GCMRelated";
Context ctx;
GoogleCloudMessaging gcm;
String SENDER_ID = "448730031229";
String regid = null; 
private int appVersion;
	String firstLaunchFlag;
public GCMRegisterApp(Context ctx, GoogleCloudMessaging gcm, int appVersion, String firstLaunchFlag)
{
	this.firstLaunchFlag=firstLaunchFlag;
	this.ctx = ctx;
	this.gcm = gcm;
	this.appVersion = appVersion;
}
@Override
protected void onPreExecute()
{
	super.onPreExecute();
}
@Override
protected String doInBackground(Void... arg0) 
{
String msg = "";
try 
        {
            if (gcm == null) 
            {
                gcm = GoogleCloudMessaging.getInstance(ctx);
            }
            regid = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regid;
            sendRegistrationIdToBackend();
        } 
		catch (IOException ex) 
        {
            msg = "Error :" + ex.getMessage();
			ex.printStackTrace();

        }
		return msg;
}
 
	private void storeRegistrationId(Context ctx, String regid) 
	{
		final SharedPreferences prefs = ctx.getSharedPreferences(ctx.getString(R.string.app_name), Context.MODE_PRIVATE);
		ACLogger.log(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("registration_id", regid);
		editor.putInt("appVersion", appVersion);
		editor.commit();
	}
 
 
	private boolean sendRegistrationIdToBackend() 
	{
		IGCMPresentor iGCMPresentor = new GCMPresentorImpl(ctx, this);  
		iGCMPresentor.registerDeviceRegisterationId(regid, firstLaunchFlag);
		return true;
	}
 
	@Override
	protected void onPostExecute(String result) 
	{
		super.onPostExecute(result);
		ACLogger.log("Registration Completed. Now you can see the notifications");
        ACLogger.log(TAG, result);
	}
	
	@Override
	public void onGCMRegisterSuccess() 
	{
		storeRegistrationId(ctx, regid);		
	}
}