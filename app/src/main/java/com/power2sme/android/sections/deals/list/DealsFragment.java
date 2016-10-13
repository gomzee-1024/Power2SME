package com.power2sme.android.sections.deals.list;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.dtos.response.ResponseGetDealsDto;
import com.power2sme.android.entities.v3.Deal_v3;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.deals.list.expandable.ExpandableDealRecyclerViewAdapter;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;

import java.util.ArrayList;
import java.util.List;

public class DealsFragment extends SuperFragment implements IDealsView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener, ExpandableDealRecyclerViewAdapter.OnItemClickListener {
    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;


    private static final String TAG = "DealsFragment";
    //	DealFilters dealFilters;
    SuperRecyclerView ListView_deals;
    ExpandableDealRecyclerViewAdapter adapter;
    private RecyclerView.Adapter mWrappedAdapter;
    View rootView;
    IDealsPresentor iDealsPresentor;
    DealsType selectedDealType = DealsType.ACTIVE;
    Deal_v3 selectedDeal;

    private static final int RESULTS_PER_PAGE = 10;
    private int dealsTotalRecordsCount;
    private int pageCounter = 0;
    //    String filterValue="";
    LinearLayout LinearLayout_searchViewParent;
    EditText EditText_searchQuery;
    ImageButton ImageButton_search;

    LinearLayout LinearLayout_filterParent;
    TextView TextView_searchResultCount;
    TextView TextView_viewAll;
    View mNoInternetConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_DealsFragment));
    }

    @Override
    public int getScreenTitleResId() {
        return R.string.screen_title_deals;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    	baseActivity=(ContainerActivity)getActivity();
//    	baseActivity.setDrawerMenuEnabled(true, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_deals));
//      baseActivity.setSubHeader(this.getClass());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.deals_fragment, container, false);
        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        initUI();
        setHasOptionsMenu(true);
        selectedDealType = DealsType.ACTIVE;
        if (getFragmentDataBundle() != null && getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL) != null) {
            selectedDeal = getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL);
            ArrayList<Deal_v3> tmpList = new ArrayList<Deal_v3>();
            tmpList.add(selectedDeal);
            ResponseGetDealsDto responseGetDealsDto = new ResponseGetDealsDto();
            responseGetDealsDto.setData(tmpList);
            responseGetDealsDto.setTotalRecord("1");
            showDeals("", responseGetDealsDto, false);
            ListView_deals.getSwipeToRefresh().setEnabled(false);
            LinearLayout_searchViewParent.setVisibility(LinearLayout.GONE);
            mRecyclerViewExpandableItemManager.expandGroup(0);
        }
        else
        {
            iDealsPresentor = new DealsPresentorImpl(baseActivity, this);
            onRefresh();
        }
//		applyActionBarNavigationListSettings();
        return rootView;
    }

    private void initUI() {
        mNoInternetConnection = rootView.findViewById(R.id.no_connection);
        mNoInternetConnection.setVisibility(View.GONE);
        LinearLayout_filterParent = (LinearLayout) rootView.findViewById(R.id.LinearLayout_filterParent);
        TextView_searchResultCount = (TextView) rootView.findViewById(R.id.TextView_searchResultCount);
        TextView_viewAll = (TextView) rootView.findViewById(R.id.TextView_viewAll);
        TextView_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText_searchQuery.setText("");
                LinearLayout_filterParent.setVisibility(LinearLayout.GONE);
                onRefresh();
            }
        });
        LinearLayout_searchViewParent = (LinearLayout) rootView.findViewById(R.id.LinearLayout_searchViewParent);
        EditText_searchQuery = (EditText) rootView.findViewById(R.id.EditText_searchQuery);
        ImageButton_search = (ImageButton) rootView.findViewById(R.id.ImageButton_search);
        ListView_deals = (SuperRecyclerView) rootView.findViewById(R.id.ListView_deals);
//        ListView_deals.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ListView_deals.setLayoutManager(new LinearLayoutManager(baseActivity));
        adapter = new ExpandableDealRecyclerViewAdapter(getActivity(), new ArrayList<Deal_v3>());
        adapter.setOnItemClickListener(this);

        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(adapter);

        ListView_deals.setAdapter(mWrappedAdapter);
        mRecyclerViewExpandableItemManager.attachRecyclerView(ListView_deals.getRecyclerView());


        ListView_deals.setRefreshListener(this);
        ListView_deals.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        ListView_deals.getSwipeToRefresh().setEnabled(false);
        ListView_deals.setupMoreListener(this, 1);
        ((LinearLayout) rootView.findViewById(R.id.LinearLayout_emptyViewParent)).setVisibility(LinearLayout.GONE);
        ((Button) rootView.findViewById(R.id.Button_getAQuote)).setVisibility(LinearLayout.GONE);

        ImageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDeals();
            }
        });
        EditText_searchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (EditText_searchQuery.getText().toString().length() > 0) {
                        searchDeals();
                        InputMethodManager imm = (InputMethodManager) baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(EditText_searchQuery.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });

//        EditText_searchQuery.addTextChangedListener(new TextWatcher()
//        {
//            public void afterTextChanged(Editable s)
//            {}
//            public void beforeTextChanged(CharSequence s, int start, int count, int after)
//            {}
//            public void onTextChanged(CharSequence s, int start,int before, int count)
//            {
//                searchDeals();
//            }
//        });
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
    }

    private void changeVisibility(Boolean isEmpty) {
        if (isEmpty) {
            ListView_deals.setVisibility(View.GONE);
            mNoInternetConnection.setVisibility(View.VISIBLE);
        } else {
            ListView_deals.setVisibility(View.VISIBLE);
            mNoInternetConnection.setVisibility(View.GONE);
        }
    }
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState)
//    {
//        super.onActivityCreated(savedInstanceState);
//        getView().getParent().getParent().getParent().getLayoutParams();
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) getView().getLayoutParams();
//        MyAppBarLayoutBehavior behavior = (MyAppBarLayoutBehavior) lp.getBehavior();
//        behavior.setLayout(getView());
//    }

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


//        if (flag == Constants.FLAG_DEALS) {
//            int len = EditText_searchQuery.getText().length();
//            if (len > 0) {
//                UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
//            }
//        }

        if ((uiMessage.getUiMessageType() == UIMessageType.NETWORK_NOT_AVAILABLE)) {
            changeVisibility(true);
            return;
        }
        ListView_deals.getSwipeToRefresh().setEnabled(true);
    }

    @Override
    public void showDeals(String filterValue, ResponseGetDealsDto responseRFQsDto, boolean isLoadmore) {
        List<Deal_v3> dealList;
        if (responseRFQsDto == null || responseRFQsDto.getData() == null) {
            dealList = new ArrayList<Deal_v3>();
        } else {
            dealList = responseRFQsDto.getData();
        }

        if (responseRFQsDto != null) {
            dealsTotalRecordsCount = responseRFQsDto.getTotalRecord();
        }

        if (!isLoadmore) {
            adapter.clear();
        }

        adapter.addAll(dealList);
        adapter.notifyDataSetChanged();

        if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_ID) != null) {
            ListView_deals.getSwipeToRefresh().setEnabled(false);
        } else {
            ListView_deals.getSwipeToRefresh().setEnabled(true);
        }

        if (this.isVisible() && LinearLayout_filterParent.getVisibility() == LinearLayout.VISIBLE)
        {
            TextView_searchResultCount.setText(getString(R.string.deals_label_filter_result_count) + dealsTotalRecordsCount);
        }

        if(adapter.getItemCount()<=0 && dealsTotalRecordsCount<=0)
        {
            ((LinearLayout) rootView.findViewById(R.id.LinearLayout_emptyViewParent)).setVisibility(LinearLayout.VISIBLE);

            String searchChar = EditText_searchQuery.getText().toString();
            if(searchChar==null || searchChar.isEmpty())
            {
                ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.deals_not_found));
            }
            else
            {
                ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.no_result_found));
            }
            LinearLayout_searchViewParent.setVisibility(View.GONE);
        }
        else
        {
            LinearLayout_searchViewParent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void navigateToPostOrder(Deal_v3 deal) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL, deal);
        baseActivity.openChildActivityFragment(AddRFQFragment.class, bundle, true, true, false);
    }

    public void navigateInterestedCustomer(Deal_v3 deal) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL, deal);
        baseActivity.openChildActivityFragment(InterestedCustomerFragment.class, bundle, true, true, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.deals_list_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setQueryHint("Filter Deals");
//
//        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener()
//        {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item)
//            {
//                searchView.setQuery(filterValue, false);
//                return false;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item)
//            {
//                filterValue = ""+searchView.getQuery();
//                return false;
//            }
//        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//        {
//            @Override
//            public boolean onQueryTextSubmit(String s)
//            {
//                searchDeals(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s)
//            {
//                return false;
//            }
//        });
        inflater.inflate(R.menu.default_blank_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchDeals()
    {
        Utils.hideKeyboard(getActivity());
        String filterVal = getFilterValue();
        if (filterVal == null || filterVal.trim().length() == 0) {
            EditText_searchQuery.setError(getString(R.string.deals_validation_enter_your_query_here));
        } else {
            dealsTotalRecordsCount = 0;
            EditText_searchQuery.setError(null);
            onRefresh();
        }
    }

//	private void applyActionBarNavigationListSettings()
//    {
//		dealFilters=DealFilters.ACTIVE;//default filter
//		baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//		final DealFilters[] dealFiltersArr = DealFilters.values();
//		//SpinnerAdapter dealFiltersAdapter = new ArrayAdapter<DealFilters>(baseActivity, android.R.layout.simple_spinner_dropdown_item, dealFiltersArr);
//		SpinnerAdapter dealFiltersAdapter = new ArrayAdapter<DealFilters>(baseActivity, R.layout.filter_layout, dealFiltersArr);
//        baseActivity.getSupportActionBar().setListNavigationCallbacks(dealFiltersAdapter , new OnNavigationListener()
//        {
//			@Override
//			public boolean onNavigationItemSelected(int itemPosition, long arg1)
//			{
//				dealFilters = dealFiltersArr[itemPosition];
//				TextView_dealsHeads.setText(dealFilters.toString());
//				switch(dealFilters)
//				{
//					case ACTIVE:
//					{
//						TextView_dealsHeads.setText(baseActivity.getString(R.string.deals_label_activedeals));
//						selectedDealType=DealsType.ACTIVE;
//						onRefresh();
//						break;
//					}
////					case UPCOMING:
////					{
////						TextView_dealsHeads.setText(baseActivity.getString(R.string.deals_label_upcomingdeals));
////						selectedDealType=DealsType.UPCOMING;
////						onRefresh();
////						break;
////					}
////					case EXPIRED:
////					{
////						TextView_dealsHeads.setText(baseActivity.getString(R.string.deals_label_expireddeals));
////						selectedDealType=DealsType.EXPIRED;
////						onRefresh();
////						break;
////					}
//				}
//				return false;
//			}
//		});
//    }

    //////////////////////////////////////////
    @Override
    public void onDealSelected(Deal_v3 deal) {
        navigateToPostOrder(deal);
    }

    @Override
    public void onCustomerInterested(Deal_v3 deal) {
        navigateInterestedCustomer(deal);
    }

    @Override
    public void onRefresh() {
        pageCounter = 0;
        iDealsPresentor.getDeals(pageCounter, RESULTS_PER_PAGE, getFilterValue(), false);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        if (overallItemsCount < dealsTotalRecordsCount) {
            ListView_deals.getMoreProgressView().setVisibility(View.VISIBLE);
            pageCounter = pageCounter + 1;
            iDealsPresentor.getDeals(pageCounter, RESULTS_PER_PAGE, getFilterValue(), true);
        } else {
            ListView_deals.getMoreProgressView().setVisibility(View.GONE);
        }
    }

    private String getFilterValue() {
        String str = EditText_searchQuery.getText().toString();
        if (str != null && str.trim().length() > 0)
        {
            LinearLayout_filterParent.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            str="";
        }
        return str;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save current state to support screen rotation, etc...
        if (mRecyclerViewExpandableItemManager != null) {
            outState.putParcelable(
                    SAVED_STATE_EXPANDABLE_ITEM_MANAGER,
                    mRecyclerViewExpandableItemManager.getSavedState());
        }
    }

    @Override
    public void onDestroyView() {
//        if (mRecyclerViewExpandableItemManager != null) {
//            mRecyclerViewExpandableItemManager.release();
//            mRecyclerViewExpandableItemManager = null;
//        }
//
//        if (mWrappedAdapter != null)
//        {
//            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
//            mWrappedAdapter = null;
//        }
        super.onDestroyView();
    }
}
