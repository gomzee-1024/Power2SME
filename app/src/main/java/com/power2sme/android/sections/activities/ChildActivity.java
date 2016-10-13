package com.power2sme.android.sections.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.power2sme.android.IContainerActivityView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.conf.Constants.LoginStatus;
import com.power2sme.android.sections.accountupdates.AccountUpdatesFragment;
import com.power2sme.android.sections.agentlogin.CustomerSearchResultsFragment;
import com.power2sme.android.sections.contactupdate.ContactInfoEditFragment;
import com.power2sme.android.sections.contactupdate.farmerinfo.KAMDetailsFragment;
import com.power2sme.android.sections.deals.list.DealsFragment;
import com.power2sme.android.sections.deliveryaddress.list.DeliveryAddressesListFragment;
import com.power2sme.android.sections.login.OnViewTouchEventListener;
import com.power2sme.android.sections.msgscreens.NoInternetFragment;
import com.power2sme.android.sections.msgscreens.ServerErrorFragment;
import com.power2sme.android.sections.myorders.list.BuyingOrdersFragment;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.sections.myrfqs.list.BuyingRFQsFragment;
import com.power2sme.android.sections.settings.AboutUsFragment;
import com.power2sme.android.sections.smekhabar.SMEKhabarFragment;
import com.power2sme.android.sections.splash.ISplashPresentor;
import com.power2sme.android.sections.splash.ISplashView;
import com.power2sme.android.sections.splash.SplashPresentorImpl;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.updater.AppUpdater;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class ChildActivity extends BaseAppCompatActivity implements IContainerActivityView {
    public static boolean onBackPressedGotoHomeFlag = false;
    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 101;
    int menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        menuId = intent.getIntExtra(Constants.BUNDLE_KEY_MENU_ID, -1);
        Bundle bundle = intent.getExtras();
        goToLandPage(bundle, menuId);

        if(intent.getBooleanExtra(Constants.BUNDLE_KEY_IS_CHECK_UPDATE, false))
        {
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
                                    AppUpdater.checkForAppUpdate(ChildActivity.this);
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
    }

    @Override
    public int getActivityLayoutResourceId() {
        return R.layout.activity_child;
    }

    @Override
    public ProgressBar getProgressBar() {
        return (SmoothProgressBar) findViewById(R.id.SmoothProgressBar_actionProgressBar);
    }

    @Override
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    private void openLoginFragment(Bundle bundle, int targetSectionId) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        //bundle.putInt(Constants.BUNDLE_KEY_MENU_ID, targetSectionId);
        //openChildActivityFragment(LoginSignupFormFragment.class, bundle, false, true, true);

        //// TODO: 28/9/15

        //redirect to Login activity here.
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, targetSectionId);
        intent.putExtra(Constants.BUNDLE_KEY_TARGET_FRAGMENT_NAME, targetSectionId);
        startActivityForResult(intent, ChildActivity.LOGIN_ACTIVITY_REQUEST_CODE);
    }

    private void goToLandPage(Bundle bundle, int menuId) {
        MyAccountApplication myAccountApplication = ((MyAccountApplication) getApplicationContext());

        switch (menuId) {
            case Constants.SERVER_ERROR_FRAGMENT_CODE: {
                openChildActivityFragment(ServerErrorFragment.class, bundle, false, true, true);
                break;
            }
            case Constants.NETWORK_ERROR_FRAGMENT_CODE: {
                openChildActivityFragment(NoInternetFragment.class, bundle, false, true, true);
                break;
            }
            case Constants.CUSTOMER_SEARCH_RESULT_FRAGMENT_CODE: {
                openChildActivityFragment(CustomerSearchResultsFragment.class, bundle, false, true, true);
                break;
            }
            //           case R.id.drawer_menu_item_login:
            //          {
////                Intent intent = new Intent();
////                intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_home);
////                setResult(RESULT_OK, intent);

            //            openLoginFragment(bundle, R.id.drawer_menu_item_home);
            //            break;
            //       }
            case R.id.drawer_menu_item_add_new_rfq: {
                bundle.putBoolean(Constants.BUNDLE_KEY_RFQ_FROM_MENU, true);
                openChildActivityFragment(AddRFQFragment.class, bundle, false, true, true);
                break;
            }
            case R.id.drawer_menu_item_account_updates: {
                if (myAccountApplication.getLoginStatus() == LoginStatus.NOT_LOGGED_IN) {
                    openLoginFragment(bundle, R.id.drawer_menu_item_account_updates);
                } else {
                    openChildActivityFragment(AccountUpdatesFragment.class, bundle, false, true, true);
                }
                break;
            }
            case R.id.drawer_menu_item_contact_details: {
                if (myAccountApplication.getLoginStatus() != LoginStatus.NOT_LOGGED_IN) {
                    openChildActivityFragment(ContactInfoEditFragment.class, bundle, false, true, true);
                } else {
                    openLoginFragment(bundle, R.id.drawer_menu_item_contact_details);
                }
                break;
            }
            case R.id.drawer_menu_item_deals: {
                openChildActivityFragment(DealsFragment.class, bundle, false, true, true);
                break;
            }
            case R.id.drawer_menu_item_myrfq: {
                if (myAccountApplication.getLoginStatus() == LoginStatus.NOT_LOGGED_IN) {
                    openLoginFragment(bundle, R.id.drawer_menu_item_myrfq);
                } else {
                    openChildActivityFragment(BuyingRFQsFragment.class, bundle, false, true, true);
                }
                break;
            }
            case R.id.drawer_menu_item_myorders: {
                if (myAccountApplication.getLoginStatus() == LoginStatus.NOT_LOGGED_IN) {
                    openLoginFragment(bundle, R.id.drawer_menu_item_myorders);
                } else {
                    openChildActivityFragment(BuyingOrdersFragment.class, bundle, false, true, true);
                }
                break;
            }
            case R.id.drawer_menu_item_shipping_address: {
                if (myAccountApplication.getLoginStatus() == LoginStatus.NOT_LOGGED_IN) {
                    openLoginFragment(bundle, R.id.drawer_menu_item_shipping_address);
                } else {
                    openChildActivityFragment(DeliveryAddressesListFragment.class, bundle, false, true, true);
                }
                break;
            }
            case R.id.drawer_menu_item_news: {
                openChildActivityFragment(SMEKhabarFragment.class, bundle, false, true, true);
                break;
            }
            case R.id.drawer_menu_item_contact_us: {
                SharedPreferences prefs = myAccountApplication.getPrefs();
                String name = prefs.getString(Constants.PREFERENCE_FARMER_FULLNAME, "");
                String email = prefs.getString(Constants.PREFERENCE_FARMER_EMAIL, "");
                String mobile = prefs.getString(Constants.PREFERENCE_FARMER_MOBILE, "");
                if ((name == null) || (email == null) || (myAccountApplication.getLoginStatus() == Constants.LoginStatus.NOT_LOGGED_IN))
                {
                    startCallToKAM(true);
                    finish();
                } else {
                    openChildActivityFragment(KAMDetailsFragment.class, bundle, false, true, true);
                }
                break;
            }
            case R.id.drawer_menu_item_aboutus: {
                openChildActivityFragment(AboutUsFragment.class, bundle, false, true, true);
                break;
            }
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isAppInBackground = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAppInBackground = false;
    }


//    public void openLoginScreen(int targetSectionMenuId, Bundle bundle)
//    {
//        if(bundle==null)
//        {
//            bundle=new Bundle();
//        }
//        Intent childActivityIntent = new Intent(this, ChildActivity.class);
//        childActivityIntent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_login);
//        setResult(RESULT_OK, childActivityIntent);
//        startActivityForResult(childActivityIntent, ChildActivity.LOGIN_ACTIVITY_REQUEST_CODE);
//    }

//    public void openLogin(Bundle bundle) {
//        if(bundle==null)
//        {
//            bundle=new Bundle();
//        }
//        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
//        startActivity(loginActivityIntent);
//        //loginActivityIntent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_login);
//        //setResult(RESULT_OK, loginActivityIntent);
//       // startActivityForResult(loginActivityIntent, ChildActivity.LOGIN_ACTIVITY_REQUEST_CODE);
//    }


//    @Override
//    public void setTitle(CharSequence title)
//    {
//        //mTitle = title;
//        //getSupportActionBar().setTitle(mTitle);
//    }
//
//    /**
//     * When using the ActionBarDrawerToggle, you must call it during
//     * onPostCreate() and onConfigurationChanged()...
//     */
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState)
//    {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        if(mDrawerToggle!=null)
//        {
//            mDrawerToggle.syncState();
//        }
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }

//    public void showProgressDialog(ProgressTypes progressTypes)
//    {
//    	if(progressTypes==ProgressTypes.INTERACTION_ALLOWED)
//    	{
////    		setSupportProgressBarIndeterminateVisibility(true);
//    		SmoothProgressBar_actionProgressBar.setVisibility(SmoothProgressBar.VISIBLE);
//    	}
//    	else
//    	{
//    		DialogFragment newFragment = ProgressDialogFragment.newInstance(R.string.please_wait);
//    		newFragment.setCancelable(false);
//            newFragment.show(getSupportFragmentManager(), "dialog");
//    	}
//    }
//    public void hideProgressDialog(ProgressTypes progressTypes)
//    {
//    	if(progressTypes==ProgressTypes.INTERACTION_ALLOWED)
//    	{
////    		setSupportProgressBarIndeterminateVisibility(false);
//    		SmoothProgressBar_actionProgressBar.setVisibility(SmoothProgressBar.GONE);
//    	}
//    	else if(progressTypes==ProgressTypes.INTERACTION_NOT_ALLOWED)
//    	{
//    		Fragment dialogFragment = getSupportFragmentManager().findFragmentByTag("dialog");
//            if (dialogFragment != null)
//            {
//                DialogFragment df = (DialogFragment) dialogFragment;
//                df.dismissAllowingStateLoss();
//            }
//    	}
//    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(this);
        if (menuId == R.id.drawer_menu_item_login) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }

        if (onBackPressedGotoHomeFlag) {
            onBackPressedGotoHomeFlag = false;
            finish();
        } else {
            if(!isFinishing()) {
                super.onBackPressed();
            }
        }
    }

    private void showAppExitDialog() {
        ChildActivity.this.finish();
    }

    @Override
    public void showProgress(ProgressTypes progressTypes, int flag) {
        showProgressDialog(progressTypes);
    }

    @Override
    public void hideProgress(ProgressTypes progressTypes, int flag) {
        hideProgressDialog(progressTypes);
    }

    @Override
    public void showUIMessage(UIMessage uiMessage, int flag) {
        if (uiMessage.getUiMessageType() == UIMessageType.SUCCESS) {
            if (flag == Constants.FLAG_LOGOUT) {
                Utils.logoutApp(this, true, null);
            } else if (flag == Constants.FLAG_CHANGE_PASSWORD) {
                UIMessageUtility.displayUIMessage(this, uiMessage);
            }
        } else {
            UIMessageUtility.displayUIMessage(this, uiMessage);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (onViewTouchEventListener != null) {
            return onViewTouchEventListener.onViewTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    OnViewTouchEventListener onViewTouchEventListener;

    public void setOnViewTouchEventListener(OnViewTouchEventListener iViewTouchEventListener) {
        this.onViewTouchEventListener = onViewTouchEventListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  if (requestCode == LoginActivity.RC_SIGN_IN) {
            if (resultCode != Activity.RESULT_OK) {
                LoginActivity.mSignInClicked = false;
            }
            LoginActivity.mIntentInProgress = false;
            if (!LoginActivity.mGoogleApiClient.isConnecting()) {
                LoginActivity.mGoogleApiClient.connect();
            }
        }*/

        if (requestCode == ChildActivity.LOGIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                setResult(RESULT_CANCELED);
                finish();
            } else if (resultCode == Activity.RESULT_OK) {
                menuId = data.getIntExtra(Constants.BUNDLE_KEY_MENU_ID, -1);
                Bundle bundle = data.getExtras();
                goToLandPage(bundle, menuId);
            }
        } else if (requestCode == BaseAppCompatActivity.ERROR_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                setResult(RESULT_CANCELED);
                finish();
            } else if (resultCode == Activity.RESULT_OK) {
                menuId = data.getIntExtra(Constants.BUNDLE_KEY_MENU_ID, -1);
                Bundle bundle = data.getExtras();
                goToLandPage(bundle, menuId);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart()
    {
        BetterSpinner.activeSpinner=null;
        super.onStart();
    }

    @Override
    protected void onDestroy()
    {
        BetterSpinner.activeSpinner=null;
        super.onDestroy();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////// DYNAMIC PERMISSION /////////////////////
    //////////////////////////////////////////////////////////////
    Snackbar permissionSnackbar;
    private void startCallToKAM(boolean isCheckForPermission)
    {
        if(!isCheckForPermission)
        {
            MyAccountApplication myAccountApplication = ((MyAccountApplication) getApplicationContext());
            SharedPreferences prefs = myAccountApplication.getPrefs();
            String mobile = prefs.getString(Constants.PREFERENCE_FARMER_MOBILE, "");
            Utils.startCall(this, mobile);
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
        {
            startCallToKAM(false);
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE))
            {
                permissionSnackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.drawermenu_kamcall_call_permission_retry_msg), Snackbar.LENGTH_INDEFINITE);
                permissionSnackbar.setActionTextColor(Color.RED);
                permissionSnackbar.show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Constants.PERMISSION_REQUEST_PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(permissionSnackbar!=null)
        {
            permissionSnackbar.dismiss();
            permissionSnackbar=null;
        }

        switch (requestCode)
        {
            case Constants.PERMISSION_REQUEST_PHONE_CALL:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    startCallToKAM(false);
                }
                else
                {
                    // user denied permission
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE))
                    {
                        //user checked dont ask checkbox and denied
                        permissionSnackbar= Utils.getPermissionDisabledSnackBar(ChildActivity.this, getString(R.string.drawermenu_kamcall_call_permission_disable_msg));
                        permissionSnackbar.show();
                    }
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}