package com.power2sme.android.sections.smekhabar;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;

import java.util.ArrayList;

import nl.matshofman.saxrssreader.RssItem;

//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SMEKhabarFragment extends SuperFragment implements ISMEKhabarView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SMEKhabarRecyclerAdapter.OnItemClickListener, NetworkUtils.onNetworkStateListener {
    private static final String TAG = "SMEKhabarFragment";
    View rootView;
    private SuperRecyclerView ListView_smeKhabar;
    SMEKhabarRecyclerAdapter adapter;
    ISMEKhabarPresentor iSMEKhabarPresentor;
    View mNoInternetConnection;
    int pageCounter = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_SMEKhabarFragment));
        NetworkUtils.setNetworkListener(this);
    }

    @Override
    public int getScreenTitleResId() {
        return R.string.screen_title_sme_khabar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//    	baseActivity=(ContainerActivity)getActivity();
//    	baseActivity.setDrawerMenuEnabled(true, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_sme_khabar));
//		baseActivity.setSubHeader(this.getClass());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.smekhabar_fragment, container, false);
        iSMEKhabarPresentor = new SMEKhabarPresentorImpl(baseActivity, this);
        initUI();
        setHasOptionsMenu(true);

        if(NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext()))
        {
            if (!(getFragmentDataBundle() != null && getFragmentDataBundle().getParcelableArrayList("RSS_ITEM_LIST") != null))
            {
                changeVisibility(false);
                onRefresh();
            }
        }
        else
        {
            changeVisibility(true);
        }

//        if (!(getFragmentDataBundle() != null && getFragmentDataBundle().getParcelableArrayList("RSS_ITEM_LIST") != null)
//                && NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext()))
//        {
//            changeVisibility(false);
//            onRefresh();
//        }
//        else
//        {
//            changeVisibility(true);
//        }
        return rootView;
    }

    private void initUI() {
        ListView_smeKhabar = (SuperRecyclerView) rootView.findViewById(R.id.ListView_smeKhabar);
        //ListView_smeKhabar.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ListView_smeKhabar.setLayoutManager(new LinearLayoutManager(baseActivity));
        adapter = new SMEKhabarRecyclerAdapter(new ArrayList<RssItem>());
        adapter.setOnItemClickListener(this);
        ListView_smeKhabar.setAdapter(adapter);
        ListView_smeKhabar.setRefreshListener(this);
        ListView_smeKhabar.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        ListView_smeKhabar.setupMoreListener(this, 1);
        mNoInternetConnection = rootView.findViewById(R.id.no_connection);
        mNoInternetConnection.setVisibility(View.GONE);
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
        ListView_smeKhabar.getSwipeToRefresh().setEnabled(false);
    }

    private void changeVisibility(Boolean isEmpty) {
        if (isEmpty) {
            ListView_smeKhabar.setVisibility(View.GONE);
            mNoInternetConnection.setVisibility(View.VISIBLE);
        } else {
            ListView_smeKhabar.setVisibility(View.VISIBLE);
            mNoInternetConnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//		AnimateFirstDisplayListener.displayedImages.clear();
        NetworkUtils.removeNetworkListerner();
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
        if (uiMessage.getUiMessageType() == UIMessageType.NETWORK_NOT_AVAILABLE) {
            changeVisibility(true);
            return;
        }
        if (!showErrorMessageFragment(uiMessage.getUiMessageType(), SMEKhabarFragment.class, null)) {
            UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
        }

        ListView_smeKhabar.getSwipeToRefresh().setEnabled(true);
    }

    @Override
    public void showSMENews(ArrayList<RssItem> smeNews, boolean isLoadmore)
    {
        if(smeNews!=null)
        {
            if (!isLoadmore) {
                adapter.clear();
            }
            adapter.addAll(smeNews);
        }
        adapter.notifyDataSetChanged();
        ListView_smeKhabar.getSwipeToRefresh().setEnabled(true);
    }

    @Override
    public void onStop() {
        saveBundle();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        getSavedBundle();
    }

    private void saveBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (getFragmentDataBundle() == null) {
            bundle = new Bundle();
        }
        bundle.putParcelableArrayList("RSS_ITEM_LIST", adapter.data);
        setFragmentDataBundle(bundle);
    }

    private void getSavedBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (bundle != null) {
            ArrayList<RssItem> rrsItems = bundle.getParcelableArrayList("RSS_ITEM_LIST");
            if (rrsItems != null && rrsItems.size() > 0)
                showSMENews(rrsItems, false);
        }
    }
//	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener
//	{
//		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
//		{
//			if (loadedImage != null)
//			{
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay)
//				{
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        pageCounter = pageCounter + 1;
        iSMEKhabarPresentor.getSMENews(pageCounter, true);
    }

    @Override
    public void onRefresh() {
        pageCounter = 1;
        iSMEKhabarPresentor.getSMENews(pageCounter, false);
    }

    @Override
    public void onItemClick(View v, RssItem rssItem) {
        Bundle argBundle = new Bundle();
        argBundle.putString(Constants.BUNDLE_KEY_WEB_CONTENT, rssItem.getLink());
        baseActivity.openChildActivityFragment(SMENewsDetailsFragment.class, argBundle, true, true, false);
    }

    @Override
    public void onNetworkStateChange(Boolean state) {
        changeVisibility(!state);
        if (state) {
            onRefresh();
        }
    }

}
