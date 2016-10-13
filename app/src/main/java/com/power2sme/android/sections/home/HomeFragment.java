package com.power2sme.android.sections.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.conf.Constants.LoginSources;
import com.power2sme.android.conf.Constants.LoginStatus;
import com.power2sme.android.dtos.response.ResponseGetAccountUpdatesDto;
import com.power2sme.android.dtos.response.ResponseGetDealsDto;
import com.power2sme.android.entities.Outstanding;
import com.power2sme.android.entities.Update;
import com.power2sme.android.entities.v3.Deal_v3;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.accountupdates.AccountUpdatesPresentorImpl;
import com.power2sme.android.sections.accountupdates.IAccountUpdatesPresentor;
import com.power2sme.android.sections.accountupdates.IAccountUpdatesView;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.activities.LoginActivity;
import com.power2sme.android.sections.deals.list.DealsPresentorImpl;
import com.power2sme.android.sections.deals.list.IDealsPresentor;
import com.power2sme.android.sections.deals.list.IDealsView;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.styels.StyleTypes;
import com.power2sme.android.utilities.styels.StylesManager;
import com.power2sme.android.utilities.updater.AppUpdater;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.matshofman.saxrssreader.RssItem;

public class HomeFragment extends SuperFragment implements IHomeView,IAccountUpdatesView, IDealsView
{
	public static Outstanding outstanding;
	public static List<Update> updatesList;
	public static ArrayList<RssItem> newsList;
	public static List<Deal_v3> dealsList;
	public static final float DENSITY_DEFAULT_SCALE = 1.0f / DisplayMetrics.DENSITY_DEFAULT;

	View rootView;
	IHomePresentor iHomePresentor;
	IAccountUpdatesPresentor iAccountUpdatesPresentor;
	IDealsPresentor iDealsPresentor;
	ProgressBar ProgressBar_outstandingAmount;
	LinearLayout LinearLayout_outstandingParent;
	TextView TextView_outstanding;
	TextView TextView_creditLimit;
	TextView TextView_requestAccountStatement;
	Button Button_getAQuote;

	TextView TextView_accountUpdatesLabel;
	TextView TextView_viewAllAccountUpdates;
	TextView TextView_login;
	LinearLayout LinearLayout_accountUpdatesItemsParent;
	LinearLayout LinearLayout_accountUpdatesItemNotFountParent;
	TextView TextView_downloadAccountUpdatesMessage;
	Button Button_downloadAccountUpdatesTryAgain;
	ProgressBar ProgressBar_downloadAccountUpdates;

	TextView TextView_dealsLabel;
	TextView TextView_viewAllDeals;
	LinearLayout LinearLayout_smeDealsItemsParent;
	ImageView mBannerImage;
	
	LinearLayout LinearLayout_dealsParent;
	LinearLayout LinearLayout_dealsNotFountParent;
	TextView TextView_downloadDealsMessage;
	Button Button_downloadDealsTryAgain;
	ProgressBar ProgressBar_downloadDeals;
	Constants.LoginStatus status;
    MyAccountApplication myAccountApplication;
	String mBannerUrl;

    PullToRefreshScrollView mPullRefreshScrollView;

    @Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_HomeFragment));

		iHomePresentor=new HomePresentorImpl(baseActivity, this);
		iAccountUpdatesPresentor=new AccountUpdatesPresentorImpl(baseActivity, this);
		iDealsPresentor = new DealsPresentorImpl(baseActivity, this);
		myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
		switch (getResources().getDisplayMetrics().densityDpi) {
			case DisplayMetrics.DENSITY_LOW:
				mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_ldpi.jpg";
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_mdpi.jpg";
				break;
			case DisplayMetrics.DENSITY_HIGH:
				mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_hdpi.jpg";
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_xhdpi.jpg";
				break;
			case DisplayMetrics.DENSITY_XXHIGH:
				mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_xxhdpi.jpg";
				break;
			case DisplayMetrics.DENSITY_XXXHIGH:
				mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_xxxhdpi.jpg";
				break;
			default:
				double sizeScale = DENSITY_DEFAULT_SCALE*(getResources().getDisplayMetrics().densityDpi);
				if(sizeScale<0.75){
					mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_ldpi.jpg";
				}else if(sizeScale>0.75 && sizeScale<1.0){
					mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_mdpi.jpg";
				}else if(sizeScale>1.0 && sizeScale<1.5){
					mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_hdpi.jpg";
				}else if(sizeScale>1.5 && sizeScale<2.0){
					mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_xhdpi.jpg";
				}else if(sizeScale>2.0 && sizeScale<3.0){
					mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_xxhdpi.jpg";
				}else{
					mBannerUrl = APIs.BANNER_IMG_URL+"smeshops_banner_xxxhdpi.jpg";
				}
				break;
		}
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.app_name;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, container, false);

        mPullRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.superRootScrollView);
		mBannerImage = (ImageView)rootView.findViewById(R.id.banner_view);
		Picasso.with(getActivity().getApplicationContext())
				.load(mBannerUrl)
				.into(mBannerImage);
		mBannerImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri webpage = Uri.parse("https://www.smeshops.com/?utm_source=androidapp&utm_medium=banner&utm_campaign=p2s_andrd_app");
				Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
				startActivity(webIntent);
			}
		});
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				updateUI();
				//iDealsPresentor.getDeals(0, 10, "", false);
				iAccountUpdatesPresentor.getAccountUpdates(0, 3, "", false);
			}
		});
        initUIComponents();
		setupAdaptersAndListeners();
		//updateUI();
//		if(dealsList==null || dealsList.size()==0)
//			iDealsPresentor.getDeals(0, 10, "", false);
//		else
//			initDealsSectionData();
		return rootView;
    }

	@Override
	public void onStart()
	{
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		/*if(getFragmentDataBundle().getBoolean(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, false))
		{
			*/
		updateUI();
		//getFragmentDataBundle().remove(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE);
		/*}*/
	}

	private void updateUI()
	{
		status = myAccountApplication.getLoginStatus();
		if(status == LoginStatus.NOT_LOGGED_IN)
		{
			TextView_login.setVisibility(TextView.VISIBLE);
			TextView_viewAllAccountUpdates.setVisibility(TextView.GONE);
			TextView_login.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					Intent intent = new Intent(baseActivity, LoginActivity.class);
					intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_login);
					intent.putExtra(Constants.BUNDLE_KEY_LOGIN_SOURCE, LoginSources.DrawerMenu.toString());
					baseActivity.startActivity(intent);

				}
			});
			LinearLayout_accountUpdatesItemsParent.setVisibility(LinearLayout.GONE);
			LinearLayout_accountUpdatesItemNotFountParent.setVisibility(LinearLayout.GONE);
			LinearLayout_outstandingParent.setVisibility(LinearLayout.GONE);
		}
		else if(status == LoginStatus.SMEID_WITH_ERPREGISTERATION ||
				status == LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_SELECTED)
		{
			LinearLayout_accountUpdatesItemsParent.setVisibility(LinearLayout.VISIBLE);
			TextView_login.setVisibility(TextView.GONE);
			TextView_viewAllAccountUpdates.setVisibility(TextView.VISIBLE);
			String strsmeid = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_SMEID, null);
			if(strsmeid!=null)
			{
				if(updatesList==null || updatesList.size()==0)
					iAccountUpdatesPresentor.getAccountUpdates(0, 3, strsmeid, false);
				else
					initAccountUpdatesSectionData();
			}
			else
			{
				//#TODO implement login again
			}

			LinearLayout_outstandingParent.setVisibility(LinearLayout.VISIBLE);
//			if(outstanding!=null)
//			{
//				setOutstandingAmount(outstanding);
//			}
//			else
//			{
				iHomePresentor.getOutstanding("");
//			}
		}
		else if(status == LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)
		{
			LinearLayout_accountUpdatesItemsParent.setVisibility(LinearLayout.VISIBLE);
			TextView_login.setVisibility(TextView.GONE);
			TextView_viewAllAccountUpdates.setVisibility(TextView.GONE);
			errorAccountUpdatesDownloading(baseActivity.getString(R.string.home_validationerror_accountupdates_notavailable));
			Button_downloadAccountUpdatesTryAgain.setVisibility(Button.GONE);
		}
		else
		{
			LinearLayout_outstandingParent.setVisibility(LinearLayout.GONE);
			LinearLayout_accountUpdatesItemsParent.setVisibility(LinearLayout.GONE);
			TextView_login.setVisibility(TextView.GONE);
			TextView_viewAllAccountUpdates.setVisibility(TextView.GONE);
			LinearLayout_accountUpdatesItemNotFountParent.setVisibility(LinearLayout.GONE);

			((View)rootView.findViewById(R.id.divider1)).setVisibility(View.GONE);
			((LinearLayout)rootView.findViewById(R.id.LinearLayout_accountUpdatesParent)).setVisibility(LinearLayout.GONE);
		}
	}
	@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    private void setOutstandingAmount(Outstanding outstanding)
	{
		TextView_outstanding.setText(baseActivity.getString(R.string.inr)+outstanding.getOutstandingAmount());
		TextView_creditLimit.setText(baseActivity.getString(R.string.inr)+outstanding.getCreditLimit());
	}
	
	private void initUIComponents() 
	{
		ProgressBar_outstandingAmount = (ProgressBar)rootView.findViewById(R.id.ProgressBar_outstandingAmount);
    	LinearLayout_outstandingParent = (LinearLayout)rootView.findViewById(R.id.LinearLayout_outstandingParent); 
    	TextView_outstanding = (TextView)rootView.findViewById(R.id.TextView_outstanding);
		TextView_creditLimit = (TextView)rootView.findViewById(R.id.TextView_creditLimit);
    	TextView_requestAccountStatement = (TextView)rootView.findViewById(R.id.TextView_requestAccountStatement);
    	
    	Button_getAQuote = (Button)rootView.findViewById(R.id.Button_getAQuote);
    	TextView_login = (TextView)rootView.findViewById(R.id.TextView_login);
    	
    	StylesManager.getInstance(baseActivity).setButtonStyle(Button_getAQuote, StyleTypes.Button_SquareYellow);
    	StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_login, StyleTypes.TextView_body1_accent1);
    	
    	TextView_accountUpdatesLabel = (TextView)rootView.findViewById(R.id.TextView_accountUpdatesLabel);
    	TextView_viewAllAccountUpdates = (TextView)rootView.findViewById(R.id.TextView_viewAllAccountUpdates);
    	LinearLayout_accountUpdatesItemsParent = (LinearLayout)rootView.findViewById(R.id.LinearLayout_accountUpdatesItemsParent);
    	LinearLayout_accountUpdatesItemNotFountParent = (LinearLayout)rootView.findViewById(R.id.LinearLayout_accountUpdatesItemNotFountParent);
    	TextView_downloadAccountUpdatesMessage = (TextView)rootView.findViewById(R.id.TextView_downloadAccountUpdatesMessage);
    	Button_downloadAccountUpdatesTryAgain = (Button)rootView.findViewById(R.id.Button_downloadAccountUpdatesTryAgain);
    	ProgressBar_downloadAccountUpdates = (ProgressBar)rootView.findViewById(R.id.ProgressBar_downloadAccountUpdates);
    	
    	StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_accountUpdatesLabel, StyleTypes.TextView_Subhead1);
    	StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_viewAllAccountUpdates, StyleTypes.TextView_body2);
    	StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_downloadAccountUpdatesMessage, StyleTypes.TextView_body2);
    	StylesManager.getInstance(baseActivity).setButtonStyle(Button_downloadAccountUpdatesTryAgain, StyleTypes.Button_SquareYellow);
    	
    	TextView_dealsLabel = (TextView)rootView.findViewById(R.id.TextView_dealsLabel);
    	TextView_viewAllDeals = (TextView)rootView.findViewById(R.id.TextView_viewAllDeals);
    	TextView_downloadDealsMessage = (TextView)rootView.findViewById(R.id.TextView_downloadDealsMessage);
    	LinearLayout_dealsParent = (LinearLayout)rootView.findViewById(R.id.LinearLayout_dealsParent);
    	LinearLayout_smeDealsItemsParent = (LinearLayout)rootView.findViewById(R.id.LinearLayout_smeDealsItemsParent);
    	LinearLayout_dealsNotFountParent = (LinearLayout)rootView.findViewById(R.id.LinearLayout_dealsNotFountParent);
    	Button_downloadDealsTryAgain = (Button)rootView.findViewById(R.id.Button_downloadDealsTryAgain);
    	ProgressBar_downloadDeals = (ProgressBar)rootView.findViewById(R.id.ProgressBar_downloadDeals);
//
//    	StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_dealsLabel, StyleTypes.TextView_Subhead1);
//    	StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_viewAllDeals, StyleTypes.TextView_body2);
//    	StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_downloadDealsMessage, StyleTypes.TextView_body2);
//    	StylesManager.getInstance(baseActivity).setButtonStyle(Button_downloadDealsTryAgain, StyleTypes.Button_SquareYellow);

    	startAccountUpdatesDownloading();
	}
	private void setupAdaptersAndListeners() 
	{
		Button_getAQuote.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(baseActivity, ChildActivity.class);
				intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
				startActivity(intent);
			}
		});
		
		TextView_viewAllAccountUpdates.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				navigateToAccountUpdates();
			}
		});
		TextView_requestAccountStatement.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				iHomePresentor.sendAccountSummeryRequest();
		}
		});

		TextView_viewAllDeals.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				navigateToDeals();
			}
		});
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
        inflater.inflate(R.menu.my_updates_section_actionbar_top_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
//	private void applyActionBarNavigationListSettings()
//    {
//		baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//		hideProgress(ProgressTypes.INTERACTION_ALLOWED, 0);
//    }
	
	@Override
	public void showProgress(ProgressTypes progressTypes, int flag) 
	{
		if(flag==Constants.FLAG_OUTSTANDING)
		{
			ProgressBar_outstandingAmount.setVisibility(ProgressBar.VISIBLE);
		}
		else
		{
			baseActivity.showProgressDialog(progressTypes);	
		}
	}

	@Override
	public void hideProgress(ProgressTypes progressTypes, int flag) 
	{
		if(flag==Constants.FLAG_OUTSTANDING)
		{
			ProgressBar_outstandingAmount.setVisibility(ProgressBar.GONE);
		}
		else
		{
			baseActivity.hideProgressDialog(progressTypes);	
		}	
	}
	
	@Override
	public void showUIMessage(UIMessage uiMessage, int flag) 
	{
		if(!isVisible())
		{
			return;
		}
		if(uiMessage.getUiMessageType()==UIMessageType.ERROR)
		{
			if(flag == Constants.FLAG_ACOUNT_UPDATES)
			{
				if(uiMessage.getMessage()!=null && uiMessage.getMessage().indexOf(getString(R.string.accountupdates_not_available))!=-1)
				{
					errorAccountUpdatesDownloading(baseActivity.getString(R.string.home_validationerror_accountupdates_notavailable));
                    Button_downloadAccountUpdatesTryAgain.setVisibility(Button.GONE);
				}
				else
				{
					errorAccountUpdatesDownloading(baseActivity.getString(R.string.home_validationerror_accountupdates_servererror));
				}
			}
			else
			{
				UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
			}
		}
		else
		{
			UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
		}
        mPullRefreshScrollView.onRefreshComplete();
	}

	////////////////////////////// DEALS SECTION //////////////////////////////


	private void initDealsSectionData()
	{
		if(!isVisible())
		{
			return;
		}

		if(dealsList!=null && dealsList.size()>0)
		{
			visibleDealsSection();
			LinearLayout_smeDealsItemsParent.removeAllViews();
			for(int i=0;i<dealsList.size();i++)
			{
				if(i>=10)
					break;

				final Deal_v3 deal = dealsList.get(i);
				View view=LayoutInflater.from(baseActivity).inflate(R.layout.home_sections_deals_list_item, null);

				TextView TextView_DealId=(TextView)view.findViewById(R.id.TextView_DealId);
				TextView TextView_ProductName=(TextView)view.findViewById(R.id.TextView_productName);
				TextView TextView_startingRate=(TextView)view.findViewById(R.id.TextView_startingRate);
				TextView TextView_productDetails=(TextView)view.findViewById(R.id.TextView_productDetails);
				ImageView ImageView_categoryDrawable=(ImageView)view.findViewById(R.id.ImageView_categoryDrawable);


				TextView_DealId.setText(deal.getId());
				TextView_ProductName.setText(deal.getLongDescription());

				if(deal.getPriceSpace()!=null
						&& deal.getPriceSpace().size()>0)
				{
					String minPrice = deal.getPriceSpace().get(0).getRatevisibletocustomer();
					String maxPrice = deal.getPriceSpace().get(deal.getPriceSpace().size()-1).getRatevisibletocustomer();
					TextView_startingRate.setText("Price/"+deal.getUom()+": "+getString(R.string.inr)+minPrice +" - "+getString(R.string.inr)+maxPrice);
				}

				if(deal!=null && deal.getSku()!=null && deal.getSku().getCategory()!=null && deal.getSku().getCategory().length()>0)
				{
					TextView_productDetails.setText(deal.getSku().getCategory());

					int resId = Utils.getCategoryDrawableResId(getContext(), deal.getSku().getCategory());
					if(resId!=-1)
					{
						ImageView_categoryDrawable.setBackgroundResource(resId);
					}
					else
					{
						TextDrawable drawable = Utils.getCategoryDrawable(getContext(), deal.getSku().getCategory());
						ImageView_categoryDrawable.setBackgroundDrawable(drawable);
					}
				}
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						navigateToPostOrder(deal);
					}
				});
				LinearLayout_smeDealsItemsParent.addView(view);
			}
			successDealsDownloading();
		}
		else
		{
			hideDealsSection();
		}
	}

	private void hideDealsSection()
	{
		LinearLayout_dealsParent.setVisibility(LinearLayout.GONE);
		LinearLayout_smeDealsItemsParent.setVisibility(LinearLayout.GONE);
		LinearLayout_dealsNotFountParent.setVisibility(LinearLayout.GONE);
	}

	private void visibleDealsSection()
	{
		LinearLayout_dealsParent.setVisibility(LinearLayout.VISIBLE);
		LinearLayout_smeDealsItemsParent.setVisibility(LinearLayout.GONE);
		LinearLayout_dealsNotFountParent.setVisibility(LinearLayout.GONE);
	}
	private void startDealsDownloading()
	{
		LinearLayout_smeDealsItemsParent.setVisibility(LinearLayout.GONE);
		LinearLayout_dealsNotFountParent.setVisibility(LinearLayout.VISIBLE);
		TextView_downloadDealsMessage.setText("Loading.");
		Button_downloadDealsTryAgain.setVisibility(Button.GONE);
		ProgressBar_downloadDeals.setVisibility(ProgressBar.VISIBLE);
	}

	private void successDealsDownloading()
	{
		LinearLayout_smeDealsItemsParent.setVisibility(LinearLayout.VISIBLE);
		LinearLayout_dealsNotFountParent.setVisibility(LinearLayout.GONE);
	}

	private void errorDealsDownloading(String errorMessage)
	{
		LinearLayout_smeDealsItemsParent.setVisibility(LinearLayout.GONE);
		LinearLayout_dealsNotFountParent.setVisibility(LinearLayout.VISIBLE);
		TextView_downloadDealsMessage.setText(errorMessage);
		Button_downloadDealsTryAgain.setVisibility(Button.VISIBLE);
		Button_downloadDealsTryAgain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDealsDownloading();
				iDealsPresentor.getDeals(0, 10, "", false);
			}
		});
		ProgressBar_downloadDeals.setVisibility(ProgressBar.GONE);
	}

	@Override
	public void navigateToDeals()
	{
		Intent intent=new Intent(baseActivity, ChildActivity.class);
		intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_deals);
		startActivity(intent);
	}

	@Override
	public void navigateToPostOrder(Deal_v3 deal)
	{
		Intent intent=new Intent(baseActivity, ChildActivity.class);
		intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_deals);
		intent.putExtra(Constants.BUNDLE_KEY_SELECTED_DEAL, deal);
		startActivity(intent);
	}
	@Override
	public void showDeals(String filterValue, ResponseGetDealsDto responseRFQsDto, boolean isLoadmore)
	{
		if(isAdded() && isVisible()) {
			if (responseRFQsDto != null && responseRFQsDto.getData() != null && responseRFQsDto.getData().size() > 0)
			{
				this.dealsList = responseRFQsDto.getData();
			}
			else
			{
				this.dealsList = new ArrayList<Deal_v3>();
				UIMessageUtility.displayUIMessage(baseActivity, new UIMessage(UIMessageType.SUCCESS, getString(R.string.deals_not_found)));
			}
		}
		initDealsSectionData();
	}

	////////////////////////////// ACCOUNT UPDATES SECTION //////////////////////////////
	
	private void initAccountUpdatesSectionData()
	{
		if(updatesList!=null && updatesList.size()>0)
		{
			LinearLayout_accountUpdatesItemsParent.removeAllViews();
			for(int i=0;i<updatesList.size();i++)
			{
				if(i>=3)
					break;
				final Update update = updatesList.get(i);
				View view=LayoutInflater.from(baseActivity).inflate(R.layout.home_account_updates_list_item, null);
				TextView TextView_accountUpdatesItem = (TextView)view.findViewById(R.id.TextView_accountUpdatesItem);
				StylesManager.getInstance(baseActivity).setTextViewStyle(TextView_accountUpdatesItem, StyleTypes.TextView_body1);
				TextView_accountUpdatesItem.setText(update.getMessage());
				
				view.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						if(update.getOrderType().equals("RFQ"))
						{
							Intent intent=new Intent(baseActivity, ChildActivity.class);
							intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_myrfq);
							intent.putExtra(Constants.BUNDLE_KEY_RFQ_ID, update.getOrderNumber());
							startActivity(intent);
						}
						else if(update.getOrderType().equals("Order"))
						{
							Intent intent=new Intent(baseActivity, ChildActivity.class);
							intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_myorders);
							intent.putExtra(Constants.BUNDLE_KEY_ORDER_ID, update.getOrderNumber());
							startActivity(intent);
						}
					}
				});
				LinearLayout_accountUpdatesItemsParent.addView(view);
				
			}
			successAccountUpdatesDownloading();
		}
	}
	private void startAccountUpdatesDownloading()
	{
		LinearLayout_accountUpdatesItemsParent.setVisibility(LinearLayout.GONE);
		LinearLayout_accountUpdatesItemNotFountParent.setVisibility(LinearLayout.VISIBLE);
		TextView_downloadAccountUpdatesMessage.setText("Loading.");
		Button_downloadAccountUpdatesTryAgain.setVisibility(Button.GONE);
		ProgressBar_downloadAccountUpdates.setVisibility(ProgressBar.VISIBLE);
	}
	private void successAccountUpdatesDownloading()
	{
		LinearLayout_accountUpdatesItemsParent.setVisibility(LinearLayout.VISIBLE);
		LinearLayout_accountUpdatesItemNotFountParent.setVisibility(LinearLayout.GONE);
	}
	private void errorAccountUpdatesDownloading(String message)
	{
		LinearLayout_accountUpdatesItemsParent.setVisibility(LinearLayout.GONE);
		LinearLayout_accountUpdatesItemNotFountParent.setVisibility(LinearLayout.VISIBLE);
		TextView_downloadAccountUpdatesMessage.setText(message);
		Button_downloadAccountUpdatesTryAgain.setVisibility(Button.VISIBLE);
		Button_downloadAccountUpdatesTryAgain.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				startAccountUpdatesDownloading();
				String strsmeid = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_SMEID, null);
				iAccountUpdatesPresentor.getAccountUpdates(0, 3, strsmeid, false);
			}
		});
		ProgressBar_downloadAccountUpdates.setVisibility(ProgressBar.GONE);
	}
	@Override
	public void navigateToAccountUpdates() 
	{
		MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
		if(myAccountApplication.getLoginStatus()== LoginStatus.NOT_LOGGED_IN)
		{
			Utils.openLoginScreen(baseActivity, LoginSources.MY_UPDATES, new Bundle());
		}
		else
		{
			Intent intent=new Intent(baseActivity, ChildActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_account_updates);
			startActivity(intent);
		}	
	}
	@Override
	public void showAccountUpdates(ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto, boolean isLoadMore)
	{
		List<Update> updatesList = responseGetAccountUpdatesDto.getData();
		this.updatesList=updatesList;
		initAccountUpdatesSectionData();
        mPullRefreshScrollView.onRefreshComplete();
	}
	////////////////////////////////////ADD TO NEW RFQ///////////////////////////////////
	
	@Override
	public void navigateToNewRFQ() 
	{
		Intent intent=new Intent(baseActivity, ChildActivity.class);
		intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
		baseActivity.startActivity(intent);
	}
	
	
	@Override
	public void showOutstanding(Outstanding outstanding) 
	{
		this.outstanding=outstanding;
		setOutstandingAmount(outstanding);
	}
}
