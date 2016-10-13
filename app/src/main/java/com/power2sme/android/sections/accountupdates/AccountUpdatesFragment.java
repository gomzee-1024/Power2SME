package com.power2sme.android.sections.accountupdates;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.dtos.response.ResponseGetAccountUpdatesDto;
import com.power2sme.android.entities.Update;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.myorders.list.BuyingOrdersFragment;
import com.power2sme.android.sections.myrfqs.list.BuyingRFQsFragment;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.styels.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class AccountUpdatesFragment extends SuperFragment implements IAccountUpdatesView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener, UpdatesRecyclerAdapter.OnItemClickListener {
    private static final int RESULTS_PER_PAGE = 15;
    private int updatesTotalRecordsCount;
    View rootView;
    SuperRecyclerView ListView_updates;
    UpdatesRecyclerAdapter adapter;
    IAccountUpdatesPresentor iAccountUpdatesPresentor;
    private int pageCounter = 0;
    MyAccountApplication myAccountApplication;
    View mNoInternetConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_AccountUpdatesFragment));
    }

    @Override
    public int getScreenTitleResId() {
        return R.string.screen_title_updates;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    	baseActivity.setDrawerMenuEnabled(true, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_updates));
//		baseActivity.setSubHeader(this.getClass());

        myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
//        if (myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)
//        {
//            rootView = LayoutInflater.from(getActivity()).inflate(R.layout.message_fragment, container, false);
//            ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.empty_meesage_for_account_updates));
//            ((Button) rootView.findViewById(R.id.Button_getAQuote)).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(baseActivity, ChildActivity.class);
//                    intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
//                    startActivity(intent);
//                }
//            });
//            return rootView;
//        }

        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.my_updates_fragment, container, false);
        iAccountUpdatesPresentor = new AccountUpdatesPresentorImpl(baseActivity, this);
        initUIComponents();
        setupAdaptersAndListeners();
        setHasOptionsMenu(true);
        applyActionBarNavigationListSettings();

        if (!(getFragmentDataBundle() != null && getFragmentDataBundle().getParcelableArrayList("UPDATES_ITEM_LIST") != null))
        {
            onRefresh();
        }

        return rootView;
    }

    private void initUIComponents() {
        ListView_updates = (SuperRecyclerView) rootView.findViewById(R.id.ListView_updates);
        ListView_updates.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mNoInternetConnection = rootView.findViewById(R.id.no_connection);
        mNoInternetConnection.setVisibility(View.GONE);
        ListView_updates.setLayoutManager(new LinearLayoutManager(baseActivity));
        adapter = new UpdatesRecyclerAdapter(new ArrayList<Update>());
        adapter.setOnItemClickListener(this);
        ListView_updates.setAdapter(adapter);
        ListView_updates.setRefreshListener(this);
        ListView_updates.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        ListView_updates.setupMoreListener(this, 1);
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
        ListView_updates.getSwipeToRefresh().setEnabled(false);
    }

    private void changeVisibility(Boolean isEmpty) {
        if (isEmpty) {
            ListView_updates.setVisibility(View.GONE);
            mNoInternetConnection.setVisibility(View.VISIBLE);
        } else {
            ListView_updates.setVisibility(View.VISIBLE);
            mNoInternetConnection.setVisibility(View.GONE);
        }
    }

    private void setupAdaptersAndListeners() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_updates_section_actionbar_top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void applyActionBarNavigationListSettings() {
        baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        hideProgress(ProgressTypes.INTERACTION_ALLOWED, 0);
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
    public void showUIMessage(UIMessage uiMessage, int flag)
    {
        if (!isAdded()) {
            return;
        }

        /*if(!showErrorMessageFragment(uiMessage.getUiMessageType(), AccountUpdatesFragment.class, null))
		{
			UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
		}*/

        if ((uiMessage.getUiMessageType() == UIMessageType.NETWORK_NOT_AVAILABLE))
        {
            changeVisibility(true);
            return;
        }
        else
        {
            if (adapter.getItemCount() == 0)
            {
                ((LinearLayout) rootView.findViewById(R.id.LinearLayout_emptyViewParent)).setVisibility(LinearLayout.VISIBLE);
                ((Button) rootView.findViewById(R.id.Button_getAQuote)).setVisibility(LinearLayout.VISIBLE);
                ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.empty_meesage_for_account_updates));
                ((Button) rootView.findViewById(R.id.Button_getAQuote)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(baseActivity, ChildActivity.class);
                        intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
                        startActivity(intent);
                    }
                });
            }
        }
        ListView_updates.getSwipeToRefresh().setEnabled(true);
    }

    @Override
    public void showAccountUpdates(ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto, boolean isLoadmore) {
        List<Update> updatesList = responseGetAccountUpdatesDto.getData();
        if (updatesList != null)
        {
            updatesTotalRecordsCount = responseGetAccountUpdatesDto.getTotalRecord();
            if (!isLoadmore) {
                adapter.clear();
            }

            adapter.addAll(updatesList);
            adapter.notifyDataSetChanged();
            ListView_updates.getSwipeToRefresh().setEnabled(true);
        }
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        if (overallItemsCount < updatesTotalRecordsCount) {
            pageCounter = pageCounter + 1;
            iAccountUpdatesPresentor.getAccountUpdates(pageCounter, RESULTS_PER_PAGE, "", true);
        }
    }

    @Override
    public void onPause() {
        if (!(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)) {
            saveBundle();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)) {
            getSavedBundle();
        }
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        adapter.notifyDataSetChanged();
        pageCounter = 0;
        iAccountUpdatesPresentor.getAccountUpdates(pageCounter, RESULTS_PER_PAGE, "", false);
    }

    @Override
    public void onItemClick(Update update) {
        if (update.getOrderType().equals("RFQ")) {
            Bundle argBundle = new Bundle();
            argBundle.putString(Constants.BUNDLE_KEY_RFQ_ID, update.getOrderNumber());
            baseActivity.openChildActivityFragment(BuyingRFQsFragment.class, argBundle, true, true, false);
        } else if (update.getOrderType().equals("Order")) {
            Bundle argBundle = new Bundle();
            argBundle.putString(Constants.BUNDLE_KEY_ORDER_ID, update.getOrderNumber());
            baseActivity.openChildActivityFragment(BuyingOrdersFragment.class, argBundle, true, true, false);
        }
    }

    //	@Override
//	public void onStop()
//	{
//		if(!(myAccountApplication.getLoginStatus()==Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION))
//		{
//			saveBundle();
//		}
//		super.onStop();
//	}
//	@Override
//	public void onStart()
//	{
//		super.onStart();
//		if(!(myAccountApplication.getLoginStatus()==Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION))
//		{
//			getSavedBundle();
//		}
//	}
    private void saveBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (bundle == null) {
            bundle = new Bundle();
        }

        if (adapter != null && adapter.data != null) {
            bundle.putParcelableArrayList("UPDATES_ITEM_LIST", adapter.data);
        }
        setFragmentDataBundle(bundle);
    }

    private void getSavedBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (bundle != null) {
            if (adapter == null || adapter.data == null || adapter.data.size() == 0) {
                ArrayList<Update> updatesItems = bundle.getParcelableArrayList("UPDATES_ITEM_LIST");
                if (updatesItems != null && updatesItems.size() > 0) {
                    ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto = new ResponseGetAccountUpdatesDto();
                    responseGetAccountUpdatesDto.setData(updatesItems);
                    showAccountUpdates(responseGetAccountUpdatesDto, false);
                }
            }
        }
    }
}
