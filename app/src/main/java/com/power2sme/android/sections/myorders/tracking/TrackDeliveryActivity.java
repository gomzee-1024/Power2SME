package com.power2sme.android.sections.myorders.tracking;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseTruckLocationsTrackingDto;
import com.power2sme.android.utilities.logging.UIMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class TrackDeliveryActivity extends AppCompatActivity implements OnMapReadyCallback, ITrackingMapView
{
// #TODO (Task #4640) polylineOption coming null from google (R&D)
    private  static LatLng sourceLatLong;
    private  static LatLng destinationLatLong;
    private  static LatLng currentLatLong;

    GoogleMap map;
    Context context;

    ITrackingMapPresentor iTrackingMapPresentor;
    private String invoiceNumber;

    TextView TextView_currentAddress;
    TextView TextView_sourceAddress;
    TextView TextView_destinationAddress;
    LinearLayout currentLocParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_track_delivery);
        initUI();
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        invoiceNumber = getIntent().getStringExtra(Constants.BUNDLE_KEY_ORDER_ID);
        iTrackingMapPresentor=new TrackingMapPresentorImpl(this, this);
        iTrackingMapPresentor.getTruckLocations(invoiceNumber);
    }

    private void initUI()
    {
        TextView_currentAddress=(TextView)findViewById(R.id.TextView_currentAddress);
        currentLocParent=(LinearLayout)findViewById(R.id.currentLocParent);
        currentLocParent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                moveToLocationsOnMap();
            }
        });

        TextView_sourceAddress=(TextView)findViewById(R.id.TextView_sourceAddress);
        TextView_destinationAddress=(TextView)findViewById(R.id.TextView_destinationAddress);
    }

    @Override
    public void showTruckLocations(ResponseTruckLocationsTrackingDto locDto)
    {
        if(locDto!=null)
        {
            TextView_currentAddress.setText(locDto.getCurrent_address());
            TextView_sourceAddress.setText(locDto.getStart_address());
            TextView_destinationAddress.setText(locDto.getEnd_address());

            sourceLatLong=new LatLng(locDto.getStart_lat(), locDto.getStart_long());
            destinationLatLong=new LatLng(locDto.getEnd_lat(), locDto.getEnd_long());
            currentLatLong=new LatLng(locDto.getCurrent_lat(), locDto.getCurrent_long());

            iTrackingMapPresentor.drawRouteOnMap(sourceLatLong, destinationLatLong, currentLatLong);
        }
    }

    @Override
    public void drawRouteOnMap(Hashtable<String, List<List<HashMap<String,String>>>> geoPoints) {

        List<List<HashMap<String,String>>> sourceToCurrentList  = geoPoints.get("sourceToCurrent");
        List<List<HashMap<String,String>>> currentToDestiationList  = geoPoints.get("currentToDestiation");

        PolylineOptions polylineOptions1 = getPolylineOptions(sourceToCurrentList, getResources().getColor(R.color.source_to_current_list));
        PolylineOptions polylineOptions2 = getPolylineOptions(currentToDestiationList, getResources().getColor(R.color.current_to_destiation_list));

        getMapReady(polylineOptions1, polylineOptions2);
    }

    private PolylineOptions getPolylineOptions(List<List<HashMap<String,String>>> geoPoints, int color)
    {
        ArrayList<LatLng> points=null;
        PolylineOptions polylineOptions=null;
        for(int i=0;i<geoPoints.size();i++)
        {
            points=new ArrayList<LatLng>();
            polylineOptions=new PolylineOptions();
            polylineOptions.color(color);
            List<HashMap<String,String>> path=geoPoints.get(i);
            for(int j=0;j<path.size();j++)
            {
                HashMap<String,String> point=path.get(j);
                double lat= Double.parseDouble(point.get("lat"));
                double lng= Double.parseDouble(point.get("lng"));
                LatLng position=new LatLng(lat,lng);
                points.add(position);
            }
            polylineOptions.addAll(points);

        }
        return polylineOptions;
    }
    @Override
    public void showProgress(ProgressTypes progressTypes, int flag) {

    }

    @Override
    public void hideProgress(ProgressTypes progressTypes, int flag) {

    }

    @Override
    public void showUIMessage(UIMessage uiMessage, int flag) {

    }



    /**
     * Override Method for OnMapReadyCallback
     */
    @Override
    public void onMapReady(GoogleMap googleMap){

        map=googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    public void getMapReady(PolylineOptions polylineOptions1, PolylineOptions polylineOptions2){
        if(currentLatLong!=null && sourceLatLong!=null && destinationLatLong!=null && polylineOptions1!=null && polylineOptions2!=null)
        {
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.title("get");
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 08.0f));

            BitmapDescriptor iconSource = BitmapDescriptorFactory.fromResource(R.drawable.source_map_pointer);
            map.addMarker(new MarkerOptions().icon(iconSource).position(sourceLatLong).title("SOURCE"));

            BitmapDescriptor iconDestination = BitmapDescriptorFactory.fromResource(R.drawable.destination_map_pointer);
            map.addMarker(new MarkerOptions().icon(iconDestination).position(destinationLatLong).title("DESTINATION"));

            BitmapDescriptor iconCurrent = BitmapDescriptorFactory.fromResource(R.drawable.current_map_pointer);
            map.addMarker(new MarkerOptions().icon(iconCurrent).position(currentLatLong).title("CURRENT POSITION"));
            moveToLocationsOnMap();
            map.addPolyline(polylineOptions1);
            map.addPolyline(polylineOptions2);
        }
    }

    private void moveToLocationsOnMap()
    {
        if(currentLatLong!=null && sourceLatLong!=null && destinationLatLong!=null)
        {
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            boundsBuilder.include(sourceLatLong);
            boundsBuilder.include(currentLatLong);
            boundsBuilder.include(destinationLatLong);
            LatLngBounds bounds = boundsBuilder.build();
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
    }
}
