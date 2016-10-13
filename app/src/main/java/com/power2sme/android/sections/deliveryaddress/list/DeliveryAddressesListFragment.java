package com.power2sme.android.sections.deliveryaddress.list;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.deliveryaddress.insert.DeliveryAddressesInsertFragment;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.styels.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressesListFragment extends SuperFragment implements IDeliveryAddressesListView, SwipeRefreshLayout.OnRefreshListener, DeliveryAddressesRecyclerAdapter.DeliveryAddressesListener {
    private static final String TAG = "DeliveryAddressesFragment";
    View rootView;
    ImageButton ImageButton_addNewDeliveryAddress;
    SuperRecyclerView ListView_deliveryAddresses;
    DeliveryAddressesRecyclerAdapter adapter;
    IDeliveryAddressesListPresentor iDeliveryAddressesListPresentor;
    public static boolean mRefreshShippingAddress = true;
    View mNoInternetConnection;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_DeliveryAddressesListFragment));
    }

    @Override
    public int getScreenTitleResId()
    {
        return R.string.screen_title_delivery_address_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.delivery_address_fragment, container, false);
        initUIComponents();
        setupAdaptersAndListeners();
        setHasOptionsMenu(true);
        applyActionBarNavigationListSettings();
        iDeliveryAddressesListPresentor = new DeliveryAddressesListPresentorImpl(baseActivity, this);
        if (mRefreshShippingAddress)
        {
            if(adapter!=null && adapter.getItemCount()==0){
                ListView_deliveryAddresses.getEmptyView().setVisibility(View.GONE);
            }
            onRefresh();
        }
        else
        {
            mRefreshShippingAddress = true;
            adapter.notifyDataSetChanged();
            ListView_deliveryAddresses.getSwipeToRefresh().setEnabled(true);
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_updates_section_actionbar_top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void applyActionBarNavigationListSettings() {
        baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    private void setupAdaptersAndListeners() {
        ImageButton_addNewDeliveryAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//				baseActivity.openSectionChildFragment(getString(R.string.fragment_insert_delivery_address), null, true);
                baseActivity.openChildActivityFragment(DeliveryAddressesInsertFragment.class, null, true, true, false);
            }
        });
    }

    private void initUIComponents() {
        ListView_deliveryAddresses = (SuperRecyclerView) rootView.findViewById(R.id.ListView_deliveryAddresses);
        mNoInternetConnection = rootView.findViewById(R.id.no_connection);
        mNoInternetConnection.setVisibility(View.GONE);
        ListView_deliveryAddresses.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ListView_deliveryAddresses.setLayoutManager(new LinearLayoutManager(baseActivity));

        if (adapter == null)
            adapter = new DeliveryAddressesRecyclerAdapter(new ArrayList<DeliveryAddress>());

        adapter.setDeliveryAddressesListener(this);
        ListView_deliveryAddresses.setAdapter(adapter);
        ListView_deliveryAddresses.setRefreshListener(this);
        ListView_deliveryAddresses.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        ListView_deliveryAddresses.getSwipeToRefresh().setEnabled(false);
        Button retry = (Button) rootView.findViewById(R.id.Button_retryForInternet);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext())) {
                    changeVisibility(false);
                    onRefresh();
                } else {
                    UIMessageUtility.displayUIMessage(baseActivity, new UIMessage(UIMessageType.ERROR, "Please check your Network connection."));
                }
            }
        });

        ImageButton_addNewDeliveryAddress = (ImageButton) rootView.findViewById(R.id.ImageButton_addNewDeliveryAddress);
    }

    private void changeVisibility(Boolean isEmpty) {
        if (isEmpty) {
            ListView_deliveryAddresses.setVisibility(View.GONE);
            mNoInternetConnection.setVisibility(View.VISIBLE);
            ImageButton_addNewDeliveryAddress.setVisibility(View.GONE);
        } else {
            ListView_deliveryAddresses.setVisibility(View.VISIBLE);
            mNoInternetConnection.setVisibility(View.GONE);
            ImageButton_addNewDeliveryAddress.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showDeliveryAddressList(List<DeliveryAddress> deliveryAddressList) {
        if (deliveryAddressList != null){
            adapter.clear();
            adapter.addAll(deliveryAddressList);
            adapter.notifyDataSetChanged();
            ListView_deliveryAddresses.getSwipeToRefresh().setEnabled(true);
        }
    }

    @Override
    public void showProgress(ProgressTypes progressTypes, int flag) {
        baseActivity.showProgressDialog(progressTypes);
    }

    @Override
    public void hideProgress(ProgressTypes progressTypes, int flag) {
        baseActivity.hideProgressDialog(progressTypes);
    }

    @Override
    public void showUIMessage(UIMessage uiMessage, int flag) {
        if (!isAdded()) {
            return;
        }
        if ((uiMessage.getUiMessageType() == UIMessageType.NETWORK_NOT_AVAILABLE)) {
            changeVisibility(true);
            return;
        }

        ListView_deliveryAddresses.getSwipeToRefresh().setEnabled(true);

        if (uiMessage.getUiMessageType() == UIMessageType.SUCCESS) {
            if (flag == Constants.FLAG_DELETE_DELIVERY_ADDRESS) {
                onRefresh();
            }
        }
    }

    @Override
    public void onRefresh() {
        iDeliveryAddressesListPresentor.getDeliveryAddressList("");
    }

    @Override
    public void onDeleteDeliveryAddressClicked(final DeliveryAddress deliveryAddress) {
        new AlertDialog.Builder(baseActivity)
                .setMessage("Shipping address \"" + deliveryAddress.getSmeAddress() + "\" will be deleted permanently?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        iDeliveryAddressesListPresentor.deleteDeliveryAddress(deliveryAddress.getCode());
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onEditDeliveryAddressClicked(DeliveryAddress deliveryAddress) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_KEY_SELECTED_DELIVERY_ADDRESS, deliveryAddress);
        baseActivity.openChildActivityFragment(DeliveryAddressesInsertFragment.class, bundle, true, true, false);
        mRefreshShippingAddress = false;
    }
}
