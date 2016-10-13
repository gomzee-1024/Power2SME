package com.power2sme.android.utilities.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.power2sme.android.utilities.logging.ACLogger;


/**
 * Created by tausif on 6/4/15.
 */

public class P2SCampaignTrackingReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle b = intent.getExtras();

        ACLogger.log("P2SCampaignTrackingReceiver=>Action : " + intent.getAction());
        ACLogger.log("P2SCampaignTrackingReceiver=>Data   : " + intent.getDataString());
        ACLogger.log("P2SCampaignTrackingReceiver=>Intent : " + intent.toString());
        ACLogger.log("P2SCampaignTrackingReceiver=>Referer: " + intent.getStringExtra("referrer"));

        new CampaignTrackingReceiver().onReceive(context, intent);
    }
}