package com.power2sme.android.sections.myorders.tracking;

import com.google.android.gms.maps.model.LatLng;

public interface ITrackingMapInteractor
{
	void getTruckLocations(String orderId, OnTruckLocationsTracking onTruckLocationsTracking);
	void drawRouteOnMap(LatLng sourceLatLong, LatLng destinationLatLong, LatLng currentLatLong, OnDrawRouteListener onDrawRouteListener);
}
