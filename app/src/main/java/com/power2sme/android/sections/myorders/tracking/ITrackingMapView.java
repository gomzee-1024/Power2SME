package com.power2sme.android.sections.myorders.tracking;

import com.power2sme.android.dtos.response.ResponseTruckLocationsTrackingDto;
import com.power2sme.android.sections.IBaseView;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public interface ITrackingMapView extends IBaseView
{
	void showTruckLocations(ResponseTruckLocationsTrackingDto ResponseTruckLocationsTrackingDto);
	void drawRouteOnMap(Hashtable<String, List<List<HashMap<String,String>>>> geoPoints);
}
