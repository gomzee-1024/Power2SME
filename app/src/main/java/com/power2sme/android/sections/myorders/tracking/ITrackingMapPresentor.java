package com.power2sme.android.sections.myorders.tracking;

import com.google.android.gms.maps.model.LatLng;

public interface ITrackingMapPresentor
{
	void getTruckLocations(String orderId);
	void drawRouteOnMap(LatLng sourceLatLong, LatLng destinationLatLong, LatLng currentLatLong);
}
