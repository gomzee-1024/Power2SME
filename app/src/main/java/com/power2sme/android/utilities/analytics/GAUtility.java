package com.power2sme.android.utilities.analytics;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;

public class GAUtility 
{
	Context context;
	public static GoogleAnalytics analytics;
	public static Tracker tracker;
	public GAUtility(Context context)
	{
		this.context=context;

		if((Constants.isDevMode) ||(Constants.isInternalBuild))
			return;

		initTracker();
	}

	private void initTracker()
	{
		analytics = GoogleAnalytics.getInstance(context);
		analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
		analytics.setLocalDispatchPeriod(1800);
		analytics.setDryRun(false);

		tracker = analytics.newTracker(context.getString(R.string.google_analytics_tracking_id));
		tracker.enableExceptionReporting(true);
		tracker.enableAdvertisingIdCollection(true);
		tracker.enableAutoActivityTracking(false);
		tracker.enableExceptionReporting(false);
	}

	public void trackScreenView(String screenName)
	{
		if((Constants.isDevMode) ||(Constants.isInternalBuild))
			return;

		if(tracker==null)
			initTracker();

		if(tracker!=null && screenName!=null)
		{
			tracker.setScreenName(screenName);
			tracker.send(new HitBuilders.AppViewBuilder().build());
		}
	}
	
//	What actions are my users performing? (Events)
	public void trackActionEvent(String category, String action, String lebel, int value)
	{
		if((Constants.isDevMode) || (Constants.isInternalBuild))
			return;

		if(tracker==null)
			initTracker();

		if(tracker!=null)
		{
			tracker.send(new HitBuilders.EventBuilder()
					.setCategory(category)
					.setAction(action)
					.setLabel(lebel)
					.setValue(value)
					.build());
		}
	}

	public void trackProductPurchasing(String productName, double productPrice, String transactionId, String category, String action)
	{
		if((Constants.isDevMode) ||(Constants.isInternalBuild))
			return;

		Product product = new Product()
	    	.setName(productName)
	    	.setPrice(productPrice);

		ProductAction productAction = new ProductAction(ProductAction.ACTION_PURCHASE).setTransactionId(transactionId);

		// Add the transaction data to the event.
		HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder()
	    	.setCategory(category)
	    	.setAction(action)
	    	.addProduct(product)
	    	.setProductAction(productAction);

		// Send the transaction data with the event.
		tracker.setScreenName("In-Game Store");
		tracker.send(builder.build());
	}
}
