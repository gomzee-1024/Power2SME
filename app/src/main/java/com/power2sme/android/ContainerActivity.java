package com.power2sme.android;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.android.Util;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.conf.Constants.LoginStatus;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.activities.LoginActivity;
import com.power2sme.android.sections.agentlogin.CustomerSelectionFragment;
import com.power2sme.android.sections.chat.ChatActivity;
import com.power2sme.android.sections.home.HomeFragment;
import com.power2sme.android.sections.login.OnViewTouchEventListener;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.sections.settings.WebBrowserFragment;
import com.power2sme.android.sections.splash.ISplashPresentor;
import com.power2sme.android.sections.splash.ISplashView;
import com.power2sme.android.sections.splash.SplashPresentorImpl;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.updater.AppUpdater;

import org.json.JSONObject;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class ContainerActivity extends BaseAppCompatActivity implements DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener
{
    public static final int CHILD_ACTIVITY_REQUEST_CODE=100;
    public static final int LOGIN_ACTIVITY_REQUEST_CODE=110;
    public DrawerLayout mDrawerLayout;
    public NavigationView mNavigationView;
    IContainerActivityPresentor iContainerActivityPresentor;
    View menuHeaderView;
    OnViewTouchEventListener onViewTouchEventListener;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mCurrentMenuItem;
    private boolean onBackPressedGotoHomeFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isAppInBackground=false;
        doDrawerMenuSettings(null);
        iContainerActivityPresentor = new ContainerActivityPresentorImpl(this, this);

        int oldAppVersion = ((MyAccountApplication)getApplication()).getPrefs().getInt(Constants.PREFERENCE_APP_VERSION, 0);
        final String firstLaunchFlag = oldAppVersion==0?"0":"1";

        if(Utils.isAppUpgraded(this))
        {
            final SharedPreferences prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            prefs.edit().remove(GCMUtils.PROPERTY_REG_ID).commit();
            new Handler().post(new Runnable()
            {
                @Override
                public void run()
                {
                    GCMUtils.initGCM(ContainerActivity.this, firstLaunchFlag);
                }
            });
        }
        goToLandPage();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public int getActivityLayoutResourceId()
    {
        return R.layout.activity_home;
    }

    @Override
    public ProgressBar getProgressBar() {
        return (SmoothProgressBar)findViewById(R.id.SmoothProgressBar_actionProgressBar);
    }

    @Override
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    private void goToLandPage()
    {
        final Intent pushNotificationIntent = isPushNotificationResponse();
        if(pushNotificationIntent==null)
        {
            boolean isNotCampaignResponse = isCompaignDataExist();
            if(isNotCampaignResponse)
            {
                MyAccountApplication myAccountApplication = (MyAccountApplication)getApplication();
                if(myAccountApplication.isKAM())
                {
                    if(myAccountApplication.getLoginStatus() == LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_SELECTED)
                    {
                        openContainerActivityFragment(HomeFragment.class, getIntent().getExtras(), false, true, true);
                    }
                    else
                    {
                        openContainerActivityFragment(CustomerSelectionFragment.class, getIntent().getExtras(), false, true, true);
                    }
                }
                else
                {
                    openContainerActivityFragment(HomeFragment.class, getIntent().getExtras(), false, true, true);
                }
            }

            ISplashPresentor iSplashPresentor = new SplashPresentorImpl(this, new ISplashView() {
                @Override
                public void navigateToHome(UIMessageType uiMessageType) {

                }

                @Override
                public void appStartUpError() {

                }

                @Override
                public void showProgress(ProgressTypes progressTypes, int flag) {

                }

                @Override
                public void hideProgress(ProgressTypes progressTypes, int flag) {

                }

                @Override
                public void showUIMessage(UIMessage uiMessage, int flag)
                {
                    if(flag==Constants.FLAG_APIURL_PREFIX)
                    {
                        if(uiMessage.getUiMessageType()==UIMessageType.SUCCESS)
                        {
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    AppUpdater.checkForAppUpdate(ContainerActivity.this);
                                }
                            });
                        }
                    }
                }
            });

            if(Constants.isDevMode)
            {
                APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID = "http://uat-track.power2sme.com/api/v1/getOrderLocation";
                APIs.URL_TRACKING_STATUS = "http://uat-track.power2sme.com/api/v1/getTrackingStatus";
                iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_UAT);
            }
            else
            {
                APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID= "http://track.power2sme.com/api/v1/getOrderLocation";
                APIs.URL_TRACKING_STATUS 				= "http://track.power2sme.com/api/v1/getTrackingStatus";
                iSplashPresentor.getAPIUrlPrefix(APIs.URL_SERVER_PREFIX_LIVE);
            }
        }
        else
        {
            pushNotificationIntent.putExtra(Constants.BUNDLE_KEY_IS_CHECK_UPDATE, true);
            startActivityForResult(pushNotificationIntent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
        }
    }

    private boolean isCompaignDataExist()
    {
        boolean goFlag =true;
        Intent intent = getIntent();
        if(intent!=null)
        {
            Bundle b = intent.getExtras();
            String uriString = b == null ? null : b.getString(Constants.BUNDLE_KEY_WEB_CONTENT);
            if(uriString!=null)
            {
                ACLogger.log("URI String : " + uriString);
                goFlag=false;
                openContainerActivityFragment(HomeFragment.class, null, false, true, true);
                openContainerActivityFragment(WebBrowserFragment.class, b, true, true, false);
            }
        }
        return goFlag;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isAppInBackground=true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //if (getIntent().getBooleanExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, false)) {
        doDrawerMenuSettings(null);
        //goToLandPage();

        // }
        isAppInBackground=false;
    }

    public void doDrawerMenuSettings(Bundle savedInstanceState /*, boolean isLogoutApp*/)
    {
        final MyAccountApplication myAccountApplication = ((MyAccountApplication) getApplicationContext());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
        if(menuHeaderView==null)
        {
            menuHeaderView = LayoutInflater.from(this).inflate(R.layout.drawer_header_layout, null);
            mNavigationView.addHeaderView(menuHeaderView);
        }

        mNavigationView.getMenu().clear();
        TextView TextView_userName = (TextView)menuHeaderView.findViewById(R.id.TextView_userName);
        TextView TextView_emailId = (TextView)menuHeaderView.findViewById(R.id.TextView_emailId);
        TextView TextView_smeId = (TextView)menuHeaderView.findViewById(R.id.TextView_smeId);

        TextView_userName.setVisibility(TextView.GONE);
        TextView_emailId.setVisibility(TextView.GONE);
        TextView_smeId.setVisibility(TextView.GONE);

        SharedPreferences prefs = myAccountApplication.getPrefs();
        String fullName = prefs.getString(Constants.PREFERENCE_CUSTOMER_FULLNAME, "");
        String emailStr = prefs.getString(Constants.PREFERENCE_CUSTOMER_EMAIL, "");
        String smeIdStr = prefs.getString(Constants.PREFERENCE_CUSTOMER_SMEID, "");
        String kamFullName = prefs.getString(Constants.PREFERENCE_AGENT_FULLNAME, "");
        String kamEmailStr = prefs.getString(Constants.PREFERENCE_AGENT_EMAIL_ID, "");

        if(myAccountApplication.isKAM())
        {
            if(myAccountApplication.getLoginStatus()==LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_SELECTED)
            {
                TextView_userName.setText(kamFullName);
                TextView_emailId.setText("Acting on behalf of:"+fullName);
                TextView_smeId.setText(emailStr);
                mNavigationView.inflateMenu(R.menu.drawer_state_kam_loggedin);

                TextView_userName.setVisibility(TextView.VISIBLE);
                TextView_emailId.setVisibility(TextView.VISIBLE);
                TextView_smeId.setVisibility(TextView.VISIBLE);
            }
            else
            {
                //String kamFullName = prefs.getString(Constants.PREFERENCE_AGENT_FULLNAME, "");
                //String kamEmailStr = prefs.getString(Constants.PREFERENCE_AGENT_EMAIL_ID, "");

                TextView_userName.setText(kamFullName);
                TextView_emailId.setText(kamEmailStr);
                mNavigationView.inflateMenu(R.menu.drawer_state_kam_nonloggedin);

                TextView_userName.setVisibility(TextView.VISIBLE);
                TextView_emailId.setVisibility(TextView.VISIBLE);
                TextView_smeId.setVisibility(TextView.GONE);
            }
        }
        else
        {
            if(myAccountApplication.getLoginStatus()==LoginStatus.NOT_LOGGED_IN)
            {
                TextView_userName.setText("");
                TextView_emailId.setText("Guest");
                TextView_smeId.setText("");
                mNavigationView.inflateMenu(R.menu.drawer_state_nonloggedin);

                TextView_userName.setVisibility(TextView.GONE);
                TextView_emailId.setVisibility(TextView.VISIBLE);
                TextView_smeId.setVisibility(TextView.GONE);
            }
            else
            {
                TextView_userName.setText(fullName);
                TextView_emailId.setText(emailStr);
                TextView_smeId.setText("Company ID: "+smeIdStr);
                mNavigationView.inflateMenu(R.menu.drawer_state_loggedin);

                if(fullName!=null && fullName.length()>0)
                    TextView_userName.setVisibility(TextView.VISIBLE);
                else
                    TextView_userName.setVisibility(TextView.GONE);

                if(emailStr!=null && emailStr.length()>0)
                    TextView_emailId.setVisibility(TextView.VISIBLE);
                else
                    TextView_emailId.setVisibility(TextView.GONE);

                TextView_smeId.setVisibility(TextView.VISIBLE);
            }
        }
        setupNavDrawer();
    }

    public Intent isPushNotificationResponse()
    {
        Intent intent = getIntent();
        if(intent!=null)
        {
            Bundle b = intent.getExtras();
            String pushNotifJsonData = b==null?null:b.getString("data");
            ACLogger.log("PUSH DATA : " + pushNotifJsonData);
            if(pushNotifJsonData!=null && pushNotifJsonData.length()>0)
            {
                try
                {
                    JSONObject pushNotifJsonObject = new JSONObject(pushNotifJsonData);
                    String pushNotifJsonObjectType = (pushNotifJsonObject==null || pushNotifJsonObject.isNull("type"))?null:pushNotifJsonObject.getString("type");
                    if(pushNotifJsonObjectType!=null)
                    {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

                        JsonFactory jsonFactory = new JsonFactory();

                        /**
                         * handling the case if user logged in as KAM but not selected any customer at that time if any push notification received (actually it is a server bug, user user not logged in with customer then he must not receive push notification) then discard push data and show screen for customer selection to KAM User.
                         */
                        MyAccountApplication myAccountApplication = (MyAccountApplication)getApplication();
                        if(myAccountApplication.isKAM())
                        {
                            if(myAccountApplication.getLoginStatus() != LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_SELECTED)
                            {
                                return null;
                            }
                        }

                        if(pushNotifJsonObjectType.equals("1"))//RFQ
                        {
                            String pushNotifJsonObjectId = pushNotifJsonObject.isNull("id")?null:pushNotifJsonObject.getString("id");
                            if(pushNotifJsonObjectId!=null)
                            {
                                Intent childActivityIntent=new Intent(this, ChildActivity.class);
                                childActivityIntent.putExtra(Constants.BUNDLE_KEY_RFQ_ID, pushNotifJsonObjectId);
                                childActivityIntent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_myrfq);
//                                startActivityForResult(childActivityIntent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
                                setIntent(null);
                                onBackPressedGotoHomeFlag=true;
                                return childActivityIntent;
                            }
                        }
//                        else if(pushNotifJsonObjectType.equals("2"))//DEALS
//                        {
//                            String pushNotifJsonObjectId = pushNotifJsonObject.isNull("id")?null:pushNotifJsonObject.getString("id");
//                            if(pushNotifJsonObjectId!=null)
//                            {
//                                openContainerActivityFragment(AddRFQFragment.class, null, true, true, false);
//                                Intent childActivityIntent=new Intent(this, ChildActivity.class);
//                                childActivityIntent.putExtra(Constants.BUNDLE_KEY_DEAL_ID, pushNotifJsonObjectId);
//                                childActivityIntent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
////                                startActivityForResult(childActivityIntent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
//                                setIntent(null);
//                                onBackPressedGotoHomeFlag=true;
//                                return childActivityIntent;
//                            }
//                        }
                        else if(pushNotifJsonObjectType.equals("3"))//Order
                        {
                            String pushNotifJsonObjectId = pushNotifJsonObject.isNull("id")?null:pushNotifJsonObject.getString("id");
                            if(pushNotifJsonObjectId!=null)
                            {
                                Intent childActivityIntent=new Intent(this, ChildActivity.class);
                                childActivityIntent.putExtra(Constants.BUNDLE_KEY_ORDER_ID, pushNotifJsonObjectId);
                                childActivityIntent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_myorders);
//                                startActivityForResult(childActivityIntent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
                                setIntent(null);
                                onBackPressedGotoHomeFlag=true;
                                return childActivityIntent;
                            }
                        }
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    private void logout()
	{
        final MyAccountApplication myAccountApplication = (MyAccountApplication)getApplicationContext();
        if(myAccountApplication.isKAM())
        {
            String companyName = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, "");
            new AlertDialog.Builder(this)
                    .setIcon(getResources().getDrawable(R.drawable.ic_action_alert_warning))
                    .setTitle("CAUTION")
                    .setMessage("You are about to logout from "+companyName+" account. Press \"OK\" to continue, else press \"CANCEL\" to return to SME selection.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            String emailId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_EMAIL, "");
                            isAppInBackground=false;
                            iContainerActivityPresentor.logoutApp(emailId);
                            Utils.logoutApp(ContainerActivity.this, false, null);
                            Intent intent =new Intent(ContainerActivity.this, ContainerActivity.class);
                            intent.putExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, true);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton(getString(R.string.label_cancel), null)
                    .show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.logout_conformation_dlg_title))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.label_continue), new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            String emailId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_EMAIL, "");
                            isAppInBackground=false;
                            iContainerActivityPresentor.logoutApp(emailId);

                            Utils.logoutApp(ContainerActivity.this, false, null);
                            Intent intent =new Intent(ContainerActivity.this, ContainerActivity.class);
                            intent.putExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, true);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton(getString(R.string.label_cancel), null)
                    .show();
        }

    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        doDrawerMenuSettings(null);
        Intent pushIntent = isPushNotificationResponse();
        if(pushIntent==null)
        {
            if(intent.getBooleanExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, false))
            {
                goToLandPage();
            }
        }
        else
        {
            pushIntent.putExtra(Constants.BUNDLE_KEY_IS_CHECK_UPDATE, true);
            startActivityForResult(pushIntent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onBackPressed()
    {
        Utils.hideKeyboard(this);

        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawers();
            return;
        }

    	if( getSupportFragmentManager().getBackStackEntryCount() > 0 )
    	{
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
    		BackStackEntry backEntry = (BackStackEntry) getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1);
            String backStackEntryName = backEntry.getName();
    		if(backStackEntryName!=null)
    		{
    			if(backStackEntryName.equals("HomeFragment") || backStackEntryName.equals("ContactInfoSelectionFragment"))
        		{
                    showAppExitDialog();
        		}
        		else
        		{
        			if(onBackPressedGotoHomeFlag)
            		{
        				onBackPressedGotoHomeFlag=false;
        				if(getSupportFragmentManager().getBackStackEntryCount()==1)
        				{
                            openContainerActivityFragment(HomeFragment.class, null, false, true, true);
        				}
            			else
            			{
                            super.onBackPressed();
                        }
            		}
        			else
        			{
                        super.onBackPressed();
                    }
                }
            }
    	}
    	else
    	{
    		if(onBackPressedGotoHomeFlag)
    		{
                openContainerActivityFragment(HomeFragment.class, null, false, true, true);
    			onBackPressedGotoHomeFlag=false;
    		}
    		else
    		{
                showAppExitDialog();
            }
        }
    }

	private void showAppExitDialog()
	{
        ContainerActivity.this.finish();
    }

    @Override
	public void showProgress(ProgressTypes progressTypes, int flag)
	{
        showProgressDialog(progressTypes);
    }

    @Override
	public void hideProgress(ProgressTypes progressTypes, int flag)
	{
        hideProgressDialog(progressTypes);
    }

    @Override
	public void showUIMessage(UIMessage uiMessage, int flag)
	{
        if(flag==Constants.FLAG_LOGOUT)
        {
            Utils.logoutApp(this, true, null);
            doDrawerMenuSettings(null);
            return;
        }

		if(uiMessage.getUiMessageType()==UIMessageType.SUCCESS)
		{
			if(flag==Constants.FLAG_CHANGE_PASSWORD)
            {
                UIMessageUtility.displayUIMessage(this, uiMessage);
            }
		}
		else
		{
            UIMessageUtility.displayUIMessage(this, uiMessage);
        }
    }

    @Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if(onViewTouchEventListener!=null)
		{
            return onViewTouchEventListener.onViewTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

	public void setOnViewTouchEventListener(OnViewTouchEventListener iViewTouchEventListener)
	{
		this.onViewTouchEventListener=onViewTouchEventListener;
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE)
        {
            int menuId = Constants.sCONTAINERACTIVITY;
            if(resultCode == Activity.RESULT_OK && data!=null)
            {
                if(data.getBooleanExtra("EXIT_ME", false))
                {
                    ContainerActivity.this.finish();
                    return;
                }


                menuId = data.getIntExtra(Constants.BUNDLE_KEY_MENU_ID, -1);
                if(data.getBooleanExtra(Constants.BUNDLE_KEY_REFRESH_DRAWER_MENU, false))
                {
                    doDrawerMenuSettings(null);
                }
            }
            selectItem(menuId);
        }
        else if(requestCode == ContainerActivity.LOGIN_ACTIVITY_REQUEST_CODE)
        {
            selectItem(Constants.sCONTAINERACTIVITY);
        }
    }


    ////////////////////////////////////// NEW NAVIGATION DRAWER METHIDS //////////////////////////////////
    private void setupNavDrawer()
    {
        if(mDrawerLayout == null)
        {
            return;
        }
        mDrawerLayout.setDrawerListener(this);

        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this
                , mDrawerLayout
                , toolbar
                , R.string.drawer_open
                , R.string.drawer_close);

        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset)
    {
        mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
    }

    @Override
    public void onDrawerOpened(View drawerView)
    {
        mDrawerToggle.onDrawerOpened(drawerView);
    }

    public void openDrawer()
    {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onDrawerClosed(View drawerView)
    {
        mDrawerToggle.onDrawerClosed(drawerView);
    }

    @Override
    public void onDrawerStateChanged(int newState)
    {
        mDrawerToggle.onDrawerStateChanged(newState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        selectItem(id);
        mDrawerLayout.closeDrawers();
        mDrawerToggle.syncState();
        return false;
    }
    private void selectItem(int menuId)
    {
        MyAccountApplication myAccountApplication = ((MyAccountApplication) getApplicationContext());
        if(menuId==Constants.sCONTAINERACTIVITY)
        {
            openContainerActivityFragment(HomeFragment.class, null, false, true, true);
        }
        else if(menuId == R.id.drawer_menu_item_chat)
        {
            Intent intent = new Intent(this,ChatActivity.class);
            startActivity(intent);
        }
        else if(menuId == R.id.drawer_menu_item_switch_customer)
        {
            openContainerActivityFragment(CustomerSelectionFragment.class, null, false, true, true);
        }
        else if(menuId==R.id.drawer_menu_item_logout)
        {
            if(NetworkUtils.isNetworkAvailable(ContainerActivity.this))
            {
                logout();
            }
            else {
                UIMessage uiMessage = new UIMessage(UIMessageType.SUCCESS, getString(R.string.home_logout_error));
                UIMessageUtility.displayUIMessage(this, uiMessage);
            }
        }
        else if(menuId==R.id.drawer_menu_item_login) {
            Intent intent=new Intent(this, LoginActivity.class);
            intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, menuId);
            startActivityForResult(intent, ContainerActivity.LOGIN_ACTIVITY_REQUEST_CODE);
        }
        else
        {
            if(menuId==R.id.drawer_menu_item_add_new_rfq
                    || menuId==R.id.drawer_menu_item_account_updates
                    || menuId==R.id.drawer_menu_item_contact_details
                    || menuId==R.id.drawer_menu_item_myrfq
                    || menuId==R.id.drawer_menu_item_myorders
                    || menuId==R.id.drawer_menu_item_shipping_address)
            {
                Intent in = getIntent();
                if(in!=null) {
                    in.putExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, true);
                }
            }

            Intent intent=new Intent(this, ChildActivity.class);
            intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, menuId);
            startActivityForResult(intent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
        }
    }


    public <T extends Fragment> void setSubHeader(Class<T> fragmentClass)
	{
		boolean flag=false;
        LinearLayout LinearLayout_subHeader = (LinearLayout) findViewById(R.id.LinearLayout_subHeader);
		MyAccountApplication myAccountApplication = (MyAccountApplication)getApplicationContext();
		if(myAccountApplication.getLoginStatus() != Constants.LoginStatus.NOT_LOGGED_IN)
		{
			if(myAccountApplication.isKAM())
			{
                View subHeaderView = LayoutInflater.from(this).inflate(R.layout.subheader_kam, null);

                TextView TextView_CompanyName = (TextView) subHeaderView.findViewById(R.id.TextView_CompanyName);
                TextView_CompanyName.setText(myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, ""));

                TextView TextView_contactUs = (TextView) subHeaderView.findViewById(R.id.TextView_contactUs);
				TextView_contactUs.setOnClickListener(new View.OnClickListener()
				{
                    @Override
					public void onClick(View v)
					{
                        openContainerActivityFragment(CustomerSelectionFragment.class, null, true, true, false);
                    }
                });
                LinearLayout_subHeader.removeAllViews();
                LinearLayout_subHeader.addView(subHeaderView);
				flag=true;
                LinearLayout_subHeader.setVisibility(LinearLayout.VISIBLE);
            }
		}
		else
		{
            LinearLayout_subHeader.setVisibility(LinearLayout.GONE);
        }

		if(flag)
		{
            LinearLayout_subHeader.setVisibility(LinearLayout.VISIBLE);
		}
		else
		{
            LinearLayout_subHeader.setVisibility(LinearLayout.GONE);
        }
    }
}