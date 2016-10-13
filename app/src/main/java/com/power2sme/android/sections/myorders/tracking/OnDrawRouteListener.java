package com.power2sme.android.sections.myorders.tracking;

import com.power2sme.android.utilities.logging.ACError;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public interface OnDrawRouteListener
{
	void onDrawRouteStart();
	void onDrawRouteEnd();
	void onDrawRouteSuccess(Hashtable<String, List<List<HashMap<String,String>>>> geoPoints);
	void onDrawRouteError(ACError error);
}
