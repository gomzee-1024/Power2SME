package com.power2sme.android.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.power2sme.android.MyAccountApplication;

import java.util.Set;

/**
 * Created by power2sme on 16/03/16.
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if(action.equals("notification_cancelled"))
        {
            String pnId = intent.getStringExtra("PN_ID");
            if(pnId!=null)
            {
                Set<String> pnIdsSet = ((MyAccountApplication)context.getApplicationContext()).getPrefs().getStringSet("PUSH_IDS", null);
                if(pnIdsSet!=null)
                {
                    if(pnIdsSet.remove(pnId))
                    {
                        ((MyAccountApplication)context.getApplicationContext()).getPrefs().edit().putStringSet("PUSH_IDS", pnIdsSet).commit();
                    }
                }
            }
        }
    }
}
