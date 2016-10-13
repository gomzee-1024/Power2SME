package com.power2sme.android.sections.myorders.list;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.dtos.response.ResponseTrackingStatusDto;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.entities.SalesOrder;
import com.power2sme.android.entities.tracking.Order;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.myorders.shipmentdetails.ShipmentDetailsFragment;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.sections.splash.ISplashPresentor;
import com.power2sme.android.sections.splash.ISplashView;
import com.power2sme.android.sections.splash.SplashPresentorImpl;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.styels.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class BuyingOrdersFragment extends SuperFragment implements IBuyingOrdersView,ISplashView, OrdersRecyclerAdapter.OnSalesOrderClickListener, SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    private static final int RESULTS_PER_PAGE = 10;
    private static final String TAG = "BuyingOrdersFragment";
    public static Hashtable<String, Order> trackingStatusMap;
    View rootView;
    OrderTypes orderTypes;
    SuperRecyclerView ListView_orders;
    OrdersRecyclerAdapter adapter;
    IBuyingOrdersPresentor iBuyingOrdersPresentor;
    ISplashPresentor iSplashPresentor;
    EditText EditText_searchQuery;
    LinearLayout LinearLayout_searchViewParent;
    LinearLayout LinearLayout_search;
    MyAccountApplication myAccountApplication;
    View mNoInternetConnection;
    boolean isCheckForERPRegisteration=true;
    private int ordersTotalRecordsCount;
    private int pageCounter = 0;
    private String smeId;
    private boolean screenLaunched=false;
    private boolean isRefreshTrackingOrder=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_BuyingOrdersFragment));

    }

    @Override
    public int getScreenTitleResId() {
        return R.string.screen_title_orders;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        screenLaunched=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_ORDER_ID) != null) {
            setScreenTitle(R.string.screen_title_orders_details);
        } else {
            setScreenTitle(R.string.screen_title_orders);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        trackingStatusMap = null;
        myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());

        iBuyingOrdersPresentor = new BuyingOrdersPresentorImpl(baseActivity, this);
        iSplashPresentor = new SplashPresentorImpl(baseActivity, this);

        String smeId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_SMEID, null);
        if (smeId != null && smeId.length() > 0)
        {
            iBuyingOrdersPresentor.getTrackingStatus(smeId);
        }

        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.buying_orders_fragment, container, false);


        initUIComponents();
        setupAdaptersAndListeners();
        setHasOptionsMenu(true);


        final LinearLayout LinearLayout_viewAllMyOrdersParent = (LinearLayout) rootView.findViewById(R.id.LinearLayout_viewAllMyOrdersParent);
        if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_ORDER_ID) != null)
        {
            setScreenTitle(R.string.screen_title_orders_details);
            //LinearLayout_searchViewParent.setVisibility(LinearLayout.GONE);
            String orderId = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_ORDER_ID);
            iBuyingOrdersPresentor.getOrders(0, 1, smeId, orderId, OrderTypes.ALL, false);
//			baseActivity.setDrawerMenuEnabled(false, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_orders_details));

            LinearLayout_viewAllMyOrdersParent.setVisibility(LinearLayout.VISIBLE);
            TextView TextView_viewAllMyOrders = (TextView) rootView.findViewById(R.id.TextView_viewAllMyOrders);

            baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

            TextView_viewAllMyOrders.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setScreenTitle(R.string.screen_title_orders);
                    getFragmentDataBundle().remove(Constants.BUNDLE_KEY_ORDER_ID);
                    applyActionBarNavigationListSettings();
                   // LinearLayout_searchViewParent.setVisibility(LinearLayout.VISIBLE);
//                    baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
                    LinearLayout_viewAllMyOrdersParent.setVisibility(LinearLayout.GONE);
                }
            });
        }
        else
        {
            LinearLayout_viewAllMyOrdersParent.setVisibility(LinearLayout.GONE);
            //LinearLayout_searchViewParent.setVisibility(LinearLayout.VISIBLE);
//            baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            applyActionBarNavigationListSettings();
        }

        if (myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION) {
            ((LinearLayout) rootView.findViewById(R.id.LinearLayout_emptyViewParent)).setVisibility(LinearLayout.VISIBLE);
            ((Button) rootView.findViewById(R.id.Button_getAQuote)).setVisibility(LinearLayout.VISIBLE);
            ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.order_undelivered_list_empty_message));
            ((Button) rootView.findViewById(R.id.Button_getAQuote)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(baseActivity, ChildActivity.class);
                    intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
                    startActivity(intent);
                }
            });
        } else {
            ((LinearLayout) rootView.findViewById(R.id.LinearLayout_emptyViewParent)).setVisibility(LinearLayout.GONE);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.default_blank_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void applyActionBarNavigationListSettings()
    {
        onRefresh();
        //baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        orderTypes = OrderTypes.ALL;//default filter
        final OrderTypes[] orderTypesArr = OrderTypes.values();
        SpinnerAdapter orderFiltersAdapter = new ArrayAdapter<OrderTypes>(baseActivity, R.layout.filter_layout, orderTypesArr);
        baseActivity.getSupportActionBar().setListNavigationCallbacks(orderFiltersAdapter, new OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long arg1) {
                EditText_searchQuery.setText("");
                if(orderTypes!=orderTypesArr[itemPosition])
                {
                    isRefreshTrackingOrder = true;
                }
                orderTypes = orderTypesArr[itemPosition];
                if (isRefreshTrackingOrder) {
                    onRefresh();
                } else {
                    isRefreshTrackingOrder = true;
                }

                if (getFragmentDataBundle() != null && getFragmentDataBundle().getParcelableArrayList("ORDERS_ITEM_LIST") != null)
                {
                    if (getFragmentDataBundle() != null && getFragmentDataBundle().containsKey("ORDERS_ITEM_LIST"))
                    {
                        getFragmentDataBundle().remove("ORDERS_ITEM_LIST");
                    }
                }

//                if (!(getFragmentDataBundle() != null && getFragmentDataBundle().getParcelableArrayList("ORDERS_ITEM_LIST") != null))
//                {
//                    if (isRefreshTrackingOrder) {
//                        onRefresh();
//                    } else {
//                        isRefreshTrackingOrder = true;
//                    }
//                } else {
//                    if (getFragmentDataBundle() != null && getFragmentDataBundle().containsKey("ORDERS_ITEM_LIST")) {
//                        getFragmentDataBundle().remove("ORDERS_ITEM_LIST");
//                    }
//                }
                return false;
            }
        });
    }

    private void setupAdaptersAndListeners() {


        LinearLayout_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(getActivity());
                if (EditText_searchQuery.getText().toString().length() > 0)
                {
                    EditText_searchQuery.setError(null);
                    orderTypes = OrderTypes.ALL;
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    pageCounter = 0;
                    ordersTotalRecordsCount = 0;
                    iBuyingOrdersPresentor.getOrders(pageCounter, RESULTS_PER_PAGE, "", EditText_searchQuery.getText().toString(), orderTypes, false);
                } else {
                    EditText_searchQuery.setError(baseActivity.getString(R.string.myorders_validationerror_searchquery));
                }
            }
        });
        EditText_searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    EditText_searchQuery.setError(null);
                }
            }
        });

        EditText_searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utils.hideKeyboard(getActivity());
                    if (EditText_searchQuery.getText().toString().length() > 0) {
                        EditText_searchQuery.setError(null);
                        orderTypes = OrderTypes.ALL;
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                        pageCounter = 0;
                        ordersTotalRecordsCount = 0;
                        iBuyingOrdersPresentor.getOrders(pageCounter, RESULTS_PER_PAGE, "", EditText_searchQuery.getText().toString(), orderTypes, false);
                    } else {
                        EditText_searchQuery.setError(baseActivity.getString(R.string.myorders_validationerror_searchquery));
                    }

                    InputMethodManager imm = (InputMethodManager) baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(EditText_searchQuery.getWindowToken(), 0);
                }
                return false;
            }
        });
    }
    private void initUIComponents() {
        ListView_orders = (SuperRecyclerView) rootView.findViewById(R.id.ListView_orders);
        mNoInternetConnection = rootView.findViewById(R.id.no_connection);
        mNoInternetConnection.setVisibility(View.GONE);
        ListView_orders.setLayoutManager(new LinearLayoutManager(baseActivity));
        adapter = new OrdersRecyclerAdapter(new ArrayList<SalesOrder>());
        adapter.setOnSalesOrderClickListener(this);
        ListView_orders.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ListView_orders.setAdapter(adapter);
        ListView_orders.setRefreshListener(this);
        ListView_orders.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        ListView_orders.setupMoreListener(this, 1);
        LinearLayout_searchViewParent = (LinearLayout) rootView.findViewById(R.id.LinearLayout_searchViewParent);
        LinearLayout_search = (LinearLayout) rootView.findViewById(R.id.LinearLayout_search_myorders);
        EditText_searchQuery = (EditText) rootView.findViewById(R.id.EditText_searchQuery);
        EditText_searchQuery.setVisibility(EditText.VISIBLE);
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

        ListView_orders.getSwipeToRefresh().setEnabled(false);
    }

    private void changeVisibility(Boolean isEmpty) {
        if (isEmpty) {
            ListView_orders.setVisibility(View.GONE);
            mNoInternetConnection.setVisibility(View.VISIBLE);
        } else {
            ListView_orders.setVisibility(View.VISIBLE);
            mNoInternetConnection.setVisibility(View.GONE);
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

		/*if(!showErrorMessageFragment(uiMessage.getUiMessageType(), BuyingOrdersFragment.class, null))
        {
			UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
		}*/
        if ((uiMessage.getUiMessageType() == UIMessageType.NETWORK_NOT_AVAILABLE)) {
            changeVisibility(true);
            return;
        }

        if(uiMessage.getUiMessageType()==UIMessageType.SUCCESS && flag==Constants.FLAG_ISREGISTERD_IN_ERP)
        {
            onRefresh();
        }

        ListView_orders.getSwipeToRefresh().setEnabled(true);
    }

    @Override
    public void uploadOrder(SalesOrder salesOrder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showDeliveryAddress(List<DeliveryAddress> deliveryAddressList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void searchOrder(List<SalesOrder> salesOrdersList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void showOrders(ResponseSalesOrdersDto responseSalesOrdersDto, boolean isLoadmore)
    {
        List<SalesOrder> undeliveredSalesOrdersList = responseSalesOrdersDto.getData();
        ordersTotalRecordsCount = responseSalesOrdersDto.getTotalRecord();
        if (!isLoadmore) {
            adapter.clear();
        }
        adapter.addAll(new ArrayList<SalesOrder>(undeliveredSalesOrdersList));
        adapter.notifyDataSetChanged();

        if (adapter.getItemCount() == 0 && ordersTotalRecordsCount==0)
        {
            ((LinearLayout) rootView.findViewById(R.id.LinearLayout_emptyViewParent)).setVisibility(LinearLayout.VISIBLE);
            ((Button) rootView.findViewById(R.id.Button_getAQuote)).setVisibility(LinearLayout.VISIBLE);
            LinearLayout_searchViewParent.setVisibility(LinearLayout.GONE);
            String searchChar = EditText_searchQuery.getText().toString();
            if(searchChar==null || searchChar.isEmpty())
            {
                if(orderTypes==OrderTypes.PREVIOUS_ORDERS_ONLY)
                {
                    ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.order_delivered_list_empty_message));
                }
                else
                {
                    ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.order_undelivered_list_empty_message));
                }
            }
            else
            {
                ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.no_result_found));
            }

            ((Button) rootView.findViewById(R.id.Button_getAQuote)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(baseActivity, ChildActivity.class);
                    intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
                    startActivity(intent);
                }
            });
        }
        else
        {
            isRefreshTrackingOrder=false;

            if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_ORDER_ID) != null)
            {
                baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            }
            else
            {
                baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            }

            LinearLayout_searchViewParent.setVisibility(LinearLayout.VISIBLE);
        }

        if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_ORDER_ID) != null) {
            ListView_orders.getSwipeToRefresh().setEnabled(false);
        } else {
            ListView_orders.getSwipeToRefresh().setEnabled(true);
        }
    }

    @Override
    public void showTrackingStatus(ResponseTrackingStatusDto trackingDto)
    {
        trackingStatusMap = new Hashtable<String, Order>();
        if (trackingDto != null && trackingDto.getOrders() != null && trackingDto.getOrders().size() > 0)
        {
            List<Order> orderList = trackingDto.getOrders();
            for (Order order : orderList)
            {
                trackingStatusMap.put(order.getOrder_number(), order);
            }
        }
        else
        {
            trackingStatusMap.clear();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReOrderButtonClick(SalesOrder salesOrder) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(Constants.BUNDLE_KEY_REORDER_ITEM, salesOrder);
//        baseActivity.openChildActivityFragment(AddRFQFragment.class, bundle, true, true, false);
        Intent intent=new Intent(baseActivity, ChildActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
        intent.putExtra(Constants.BUNDLE_KEY_REORDER_ITEM, salesOrder);
        startActivity(intent);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        if (overallItemsCount < ordersTotalRecordsCount) {
            pageCounter = pageCounter + 1;
            iBuyingOrdersPresentor.getOrders(pageCounter, RESULTS_PER_PAGE, "", "", orderTypes, true);
        }
    }

    @Override
    public void onRefresh() {
        boolean isRegisteredInERP = myAccountApplication.getPrefs().getBoolean(Constants.PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP, false);
        if(!isRegisteredInERP && isCheckForERPRegisteration)
        {
            String smeId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_SMEID, null);
            iSplashPresentor.isRegisteredInERP(smeId);
            isCheckForERPRegisteration=false;
        }
        else
        {
            pageCounter = 0;
            String searchChar = EditText_searchQuery.getText().toString();
            if(searchChar !=null && searchChar.isEmpty()){
                searchChar = "";
            }

            SharedPreferences prefs = myAccountApplication.getPrefs();
            smeId = prefs.getString(Constants.PREFERENCE_CUSTOMER_SMEID, "");
            if (smeId != null && smeId.length() > 0)
            {
                if(screenLaunched)
                {
                    iBuyingOrdersPresentor.getTrackingStatus(smeId);
                }
                else
                {
                    screenLaunched=true;
                }
            }

            iBuyingOrdersPresentor.getOrders(pageCounter, RESULTS_PER_PAGE, "", searchChar, orderTypes, false);
        }
    }

    @Override
    public void onStop() {
        if (!(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)) {
            saveBundle();
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)) {
            getSavedBundle();
        }
    }

    private void saveBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (getFragmentDataBundle() == null) {
            bundle = new Bundle();
        }
        if (adapter != null && adapter.data != null)
            bundle.putParcelableArrayList("ORDERS_ITEM_LIST", adapter.data);
        setFragmentDataBundle(bundle);
    }

    private void getSavedBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (bundle != null) {
            ArrayList<SalesOrder> salesOrders = bundle.getParcelableArrayList("ORDERS_ITEM_LIST");
            if (salesOrders != null && salesOrders.size() > 0) {
                ResponseSalesOrdersDto responseSalesOrdersDto = new ResponseSalesOrdersDto();
                responseSalesOrdersDto.setData(salesOrders);
                if (adapter.getItemCount() == 0)
                    showOrders(responseSalesOrdersDto, false);
            }
        }
    }
    private SalesOrder salesOrder;
    private Order orderTracking;
    @Override
    public void onViewShipmentLocationClicked(SalesOrder salesOrder, Order orderTracking)
    {
        this.salesOrder=salesOrder;
        this.orderTracking=orderTracking;
        Bundle argBundle = new Bundle();
        argBundle.putParcelable(Constants.BUNDLE_KEY_TRACKING_ORDER, orderTracking);
        argBundle.putString(Constants.BUNDLE_KEY_SALESORDER_DATE, salesOrder.getOrderDate());
        baseActivity.openChildActivityFragment(ShipmentDetailsFragment.class, argBundle, true, true, false);
//        checkLocationPermission(true);
    }

    @Override
    public void navigateToHome(UIMessageType uiMessageType)
    {

    }

    @Override
    public void appStartUpError()
    {

    }

    //////////////////////////////////////////////////////////////
    ///////////////////// DYNAMIC PERMISSION /////////////////////
    //////////////////////////////////////////////////////////////
    Snackbar permissionSnackbar;
//    private void checkLocationPermission(boolean isCheckForPermission)
//    {
//        if(!isCheckForPermission)
//        {
//            Bundle argBundle = new Bundle();
//            argBundle.putParcelable(Constants.BUNDLE_KEY_TRACKING_ORDER, orderTracking);
//            argBundle.putString(Constants.BUNDLE_KEY_SALESORDER_DATE, salesOrder.getOrderDate());
//            baseActivity.openChildActivityFragment(ShipmentDetailsFragment.class, argBundle, true, true, false);
//            return;
//        }
//
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
//        {
//            checkLocationPermission(false);
//        }
//        else
//        {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
//            {
//                permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.order_tracking_location_permission_retry_msg));
//                permissionSnackbar.show();
//            }
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_READ_LOCATION);
//        }
//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(permissionSnackbar!=null)
        {
            permissionSnackbar.dismiss();
            permissionSnackbar=null;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
//            case Constants.PERMISSION_REQUEST_READ_LOCATION:
//            {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                {
//                    checkLocationPermission(false);
//                }
//                else
//                {
//                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
//                    {
//                        //user checked dont ask checkbox and denied
//                        permissionSnackbar= Utils.getPermissionDisabledSnackBar(getActivity(), getActivity().getString(R.string.order_tracking_location_permission_disabled_msg));
//                        permissionSnackbar.show();
//                    }
//                }
//            }
        }
    }
}
