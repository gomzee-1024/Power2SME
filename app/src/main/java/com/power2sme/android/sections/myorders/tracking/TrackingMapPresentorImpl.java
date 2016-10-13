package com.power2sme.android.sections.myorders.tracking;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.dtos.response.ResponseTruckLocationsTrackingDto;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class TrackingMapPresentorImpl implements ITrackingMapPresentor, OnTruckLocationsTracking,OnDrawRouteListener
{
	Context context;
	ITrackingMapView iTrackingMapView;
	ITrackingMapInteractor iTrackingMapInteractor;
	
	public TrackingMapPresentorImpl(Context context, ITrackingMapView iTrackingMapView)
	{
		this.context=context;
		this.iTrackingMapView=iTrackingMapView;
		iTrackingMapInteractor=new TrackingMapInteractorImpl(context);
	}
/////////////////////////////////////////////////////////////////////////
	@Override
	public void getTruckLocations(String orderId) 
	{
		iTrackingMapInteractor.getTruckLocations(orderId, this);
	}

	@Override
	public void onTruckLocationsTrackingStart() 
	{
        ACLogger.log("#################### EVENT : onTruckLocationsTrackingStart ####################");
		iTrackingMapView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
	}

	@Override
	public void onTruckLocationsTrackingEnd() 
	{
		iTrackingMapView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
        ACLogger.log("#################### EVENT : onTruckLocationsTrackingEnd ####################");
	}

	@Override
	public void onTruckLocationsTrackingSuccess(ResponseTruckLocationsTrackingDto ResponseTruckLocationsTrackingDto)
	{
		iTrackingMapView.showTruckLocations(ResponseTruckLocationsTrackingDto);
	}

	@Override
	public void onTruckLocationsTrackingError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.myorder_truck_tracking_error), 
				null
				);
		iTrackingMapView.showUIMessage(uiMessage, 0);
	}

	////////////////////////////////////////////////////////////////

	@Override
	public void drawRouteOnMap(LatLng sourceLatLong, LatLng destinationLatLong, LatLng currentLatLong) {
		iTrackingMapInteractor.drawRouteOnMap(sourceLatLong, destinationLatLong, currentLatLong , this);
	}

	@Override
	public void onDrawRouteStart() {
		ACLogger.log("#################### EVENT : onDrawRouteStart ####################");
		iTrackingMapView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
	}

	@Override
	public void onDrawRouteEnd() {
		iTrackingMapView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, 0);
		ACLogger.log("#################### EVENT : onDrawRouteEnd ####################");
	}

	@Override
	public void onDrawRouteSuccess(Hashtable<String, List<List<HashMap<String,String>>>> geoPoints) {
		iTrackingMapView.drawRouteOnMap(geoPoints);
	}

	@Override
	public void onDrawRouteError(ACError error) {
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context,
				error,
				context.getString(R.string.myorder_truck_tracking_error),
				null
		);
		iTrackingMapView.showUIMessage(uiMessage, 0);
	}
}
