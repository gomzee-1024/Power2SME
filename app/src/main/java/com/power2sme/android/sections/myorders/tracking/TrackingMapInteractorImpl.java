package com.power2sme.android.sections.myorders.tracking;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseGeoPointsDto;
import com.power2sme.android.dtos.response.ResponseTruckLocationsTrackingDto;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class TrackingMapInteractorImpl implements ITrackingMapInteractor 
{
	Context context;
	public TrackingMapInteractorImpl(Context context)
	{
		this.context=context;
	}
	@Override
	public void getTruckLocations(final String orderId, final OnTruckLocationsTracking onTruckLocationsTracking) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseTruckLocationsTrackingDto > >(context, new INetworkAsyncTaskCallbacks<ResponseTruckLocationsTrackingDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onTruckLocationsTracking.onTruckLocationsTrackingStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getTruckLocations(orderId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto> networkAsyncTaskResponse) 
			{
				onTruckLocationsTracking.onTruckLocationsTrackingEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onTruckLocationsTracking.onTruckLocationsTrackingError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseTruckLocationsTrackingDto responseSMESignupDto=(ResponseTruckLocationsTrackingDto) networkAsyncTaskResponse.getResultObject();
					onTruckLocationsTracking.onTruckLocationsTrackingSuccess(responseSMESignupDto);
				}
			}
		}).execute();		
	}

	/////////////////////////////////////////////////////////////

	@Override
	public void drawRouteOnMap(final LatLng sourceLatLong, final LatLng destinationLatLong, final LatLng currentLatLong, final OnDrawRouteListener onDrawRouteListener) {
		new NetworkAsyncTask < NetworkAsyncTaskResponse < Hashtable<String, List<List<HashMap<String,String>>>> > >(context, new INetworkAsyncTaskCallbacks<Hashtable<String, List<List<HashMap<String,String>>>>>()
		{
			@Override
			public void onPreExecute()
			{
				onDrawRouteListener.onDrawRouteStart();
			}
			@Override
			public NetworkAsyncTaskResponse<Hashtable<String, List<List<HashMap<String,String>>>>> doInBackground()
			{
				NetworkAsyncTaskResponse<Hashtable<String, List<List<HashMap<String,String>>>>> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<Hashtable<String, List<List<HashMap<String,String>>>>>();
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				ResponseGeoPointsDto sourceToCurrent = factoryResponse.getiDataProvider().drawRouteOnMap(sourceLatLong.latitude, sourceLatLong.longitude, currentLatLong.latitude, currentLatLong.longitude);
				ResponseGeoPointsDto currentToDestiation = factoryResponse.getiDataProvider().drawRouteOnMap(currentLatLong.latitude, currentLatLong.longitude, destinationLatLong.latitude, destinationLatLong.longitude);
				Hashtable<String, List<List<HashMap<String,String>>>> table = new Hashtable<String, List<List<HashMap<String,String>>>>();
				table.put("sourceToCurrent", sourceToCurrent.getGeoPoints());
				table.put("currentToDestiation", currentToDestiation.getGeoPoints());
				networkAsyncTaskResponse.setResultObject(table);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<Hashtable<String, List<List<HashMap<String,String>>>>> networkAsyncTaskResponse)
			{
				onDrawRouteListener.onDrawRouteEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDrawRouteListener.onDrawRouteError(networkAsyncTaskResponse.getError());
				} else
				{
					onDrawRouteListener.onDrawRouteSuccess(networkAsyncTaskResponse.getResultObject());
				}
			}
		}).execute();
	}
}
