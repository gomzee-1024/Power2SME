package com.power2sme.android.utilities.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.power2sme.android.conf.Constants;

public class ACLogger 
{
	private final static String TAG = "MTKDebug"; 

	public static void log(String msg)
	{
		if(Constants.isDevMode || Constants.isInternalBuild)
		{
			log(TAG, msg);
		}
	}
    public static void log(String tag, String msg)
    {
        if(Constants.isDevMode || Constants.isInternalBuild)
        {
            Log.d(tag, msg);
        }
    }
	public static void debugAlert(Context context, String tag, String msg)
	{
		if(Constants.isDevMode || Constants.isInternalBuild)
		{
			Toast.makeText(context, tag+" : "+msg, Toast.LENGTH_SHORT).show();
		}
	}
}
