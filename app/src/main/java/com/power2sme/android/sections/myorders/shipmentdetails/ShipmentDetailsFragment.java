package com.power2sme.android.sections.myorders.shipmentdetails;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.entities.tracking.Invoice;
import com.power2sme.android.entities.tracking.Order;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.myorders.tracking.TrackDeliveryActivity;

import java.util.ArrayList;

public class ShipmentDetailsFragment extends SuperFragment implements ShipmentDetailsRecyclerAdapter.OnLocateMapClickListener
{
	private String smeId;
	private static final String TAG="OrderDetailsFragment";
	View rootView;

	RecyclerView mListView_invoices;

	ArrayList<Invoice> mInvoiceList;

	ShipmentDetailsRecyclerAdapter mShipmentDetailsRecyclerAdapter;

	Order orderTracking;
//	SalesOrder salesOrder;
	String orderDate;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_ShipmentDetailsFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_shipment_details;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.shipment_details_fragment, container, false);

		orderTracking = getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_TRACKING_ORDER);
		orderDate= getFragmentDataBundle().getString(Constants.BUNDLE_KEY_SALESORDER_DATE);

        MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
		SharedPreferences prefs = myAccountApplication.getPrefs();
		smeId = prefs.getString(Constants.PREFERENCE_CUSTOMER_SMEID, "");
        
        initUIComponents();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();
        return rootView;
    }
    private void initUIComponents()
	{

		mListView_invoices=(RecyclerView)rootView.findViewById(R.id.ListView_shipmentDetailsList_recyler);
		mListView_invoices.setLayoutManager(new LinearLayoutManager(baseActivity));
		mInvoiceList=(ArrayList)orderTracking.getInvoices();
		mShipmentDetailsRecyclerAdapter = new ShipmentDetailsRecyclerAdapter(mInvoiceList, orderDate, orderTracking.getOrder_number());
		mListView_invoices.setAdapter(mShipmentDetailsRecyclerAdapter);
		mShipmentDetailsRecyclerAdapter.setOnSalesOrderClickListener(this);
	}
	private void applyActionBarNavigationListSettings()
	{
		baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}
	@Override
	public void onLocateMapButtonClick(String invoice_no)
	{
		Intent intent = new Intent(getActivity(), TrackDeliveryActivity.class);
		intent.putExtra(Constants.BUNDLE_KEY_ORDER_ID, invoice_no);
		startActivity(intent);
	}
}
