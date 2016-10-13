package com.power2sme.android;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.power2sme.android.push.NotificationBroadcastReceiver;
import com.power2sme.android.utilities.logging.ACLogger;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class GCMIntentService extends IntentService 
{
    public static int NOTIFICATION_ID = 1;
    private static final String TAG = "GcmIntentService";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
 
    public GCMIntentService() 
    {
        super("GcmIntentService");
    }
 
    @Override
    protected void onHandleIntent(Intent intent) 
    {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
 
        if (!extras.isEmpty()) 
        {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) 
            {
//                sendNotification(extras);
            } 
            else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) 
            {
//                sendNotification(extras);
            // If it's a regular GCM message, do some work.
            } 
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) 
            {
                // This loop represents the service doing some work.
                for (int i=0; i<5; i++) 
                {
                    ACLogger.log(TAG, "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try 
                    {
                        Thread.sleep(5000);
                    } 
                    catch (InterruptedException e) 
                    {
                    }
                }
                ACLogger.log(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                sendNotification(extras);
                ACLogger.log(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }
 
    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(Bundle extras) 
    {
    	String detail = extras.getString("detail");
    	String title = extras.getString("title");
        String imageUrl = extras.getString("imageUrl");
    	if((title==null || title.trim().length()==0) && (detail==null || detail.trim().length()==0))
    	{
    		//title=getString(R.string.app_name);
            return;
    	}
    	NOTIFICATION_ID = (int) (System.currentTimeMillis() / 1000L);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent notificationIntent = null;
        String data = extras.getString("data");

        if(data!=null && data.length()>0)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(data);
                String type = jsonObject.isNull("type")?"":jsonObject.getString("type");
                if(type.equals("4"))// Digital Marketing Type
                {
                    String landingUrl = jsonObject.isNull("landingUrl")?"":jsonObject.getString("landingUrl");
                    if(landingUrl!=null && landingUrl.length()>0)
                    {
                        notificationIntent = new Intent(Intent.ACTION_VIEW);
                        notificationIntent.setData(Uri.parse(landingUrl));
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        if(notificationIntent==null)
        {
            notificationIntent = new Intent(this, ContainerActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notificationIntent.putExtra("data", data);
        }

        if(imageUrl!=null && imageUrl.length()>0 && imageUrl.startsWith("http"))
        {
            imageUrl=imageUrl.replaceAll(" ", "%20");
            NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            Bitmap remotePicture=null;
            try
            {
                remotePicture = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            bigPictureStyle.bigPicture(remotePicture);
            bigPictureStyle.setBigContentTitle(title);
            bigPictureStyle.setSummaryText(detail);
            mBuilder.setStyle(bigPictureStyle);
        }
        else
        {
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle = bigTextStyle.bigText(detail);
            mBuilder.setStyle(bigTextStyle);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentTitle(title)
                .setContentText(detail)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setVibrate(new long[] { 0, 100, 200, 300})
                .setPriority(NotificationCompat.PRIORITY_MAX);

        mBuilder.setDeleteIntent(getDeleteIntent(NOTIFICATION_ID));

        Set<String> pnIdsSet = ((MyAccountApplication)getApplication()).getPrefs().getStringSet("PUSH_IDS", null);
        if(pnIdsSet==null)
        {
            pnIdsSet=new HashSet<String>();
        }
        pnIdsSet.add(""+NOTIFICATION_ID);
        ((MyAccountApplication)getApplication()).getPrefs().edit().putStringSet("PUSH_IDS", pnIdsSet).commit();

        mBuilder.setContentIntent(pendingIntent);
        Notification n = mBuilder.build();
        
        n.flags |= Notification.FLAG_SHOW_LIGHTS;
        n.flags |= Notification.FLAG_AUTO_CANCEL;
        n.defaults = Notification.DEFAULT_ALL;      
        n.when = System.currentTimeMillis();
        
        mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, n);
    }

    protected PendingIntent getDeleteIntent(int pnId)
    {
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.setAction("notification_cancelled");
        intent.putExtra("PN_ID", ""+pnId);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}