package com.power2sme.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
 
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver 
{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        if(intent!=null && intent.getExtras()!=null && intent.getExtras().getString("registration_id")!=null)
        {
            String regId = intent.getExtras().getString("registration_id");
            if(regId != null && !regId.equals(""))
            {
//                #TODO Have to put code for sending all configured email ids on users mobile to our server
            }
        }

        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(), GCMIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}