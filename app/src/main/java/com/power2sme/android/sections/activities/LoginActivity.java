package com.power2sme.android.sections.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.power2sme.android.ContainerActivity;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.sections.agentlogin.CustomerSelectionFragment;
import com.power2sme.android.sections.deals.list.DealsFragment;
import com.power2sme.android.sections.login.ILoginFragmentPresentor;
import com.power2sme.android.sections.login.ILoginFragmentView;
import com.power2sme.android.sections.login.LoginFragment;
import com.power2sme.android.sections.login.LoginFragmentPresentorImpl;
import com.power2sme.android.sections.login.LoginSignupFormFragment;
import com.power2sme.android.sections.login.SocialNetworkTypes;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.social.linkedin.LinkedInLoginActivity;

import java.util.Arrays;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class LoginActivity extends BaseAppCompatActivity implements ILoginFragmentView, LoginFragment.ILoginCallback, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;
    public static ILoginFragmentPresentor iLoginFragmentPresentor;
    private final int LINKEDIN_LOGIN_REQUEST_CODE = 1001;
    public int menuId;
    LoginButton fbLoginButton;
    TextView linkedInLoginButton;
    private Toolbar toolbar;
    private GoogleApiClient mGoogleApiClient;
    ////////////FB Login////////////
    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    // set the text in google button
    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView mTextView = (TextView) v;
                mTextView.setTextSize(16);
                mTextView.setText(buttonText);
                return;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(LoginActivity.this, callback);
        uiHelper.onCreate(savedInstanceState);
        Intent intent = getIntent();
        menuId = intent.getIntExtra(Constants.BUNDLE_KEY_MENU_ID, -1);
        Bundle bundle = intent.getExtras();
        goToLandPage(bundle, menuId);
        iLoginFragmentPresentor = new LoginFragmentPresentorImpl(this, this);


        //Google login
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        setGooglePlusButtonText(signInButton, "LOGIN WITH GOOGLE");


        //facebook login setup
        fbLoginButton = (LoginButton) findViewById(R.id.fbLoginButton_login);
        /*fbLoginButton.setBackgroundResource(R.drawable.fb_icon);
        fbLoginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        fbLoginButton.setCompoundDrawablePadding(0);
        fbLoginButton.setPadding(0, 0, 0, 0);
        fbLoginButton.setText("");*/
        fbLoginButton.setReadPermissions(Arrays.asList("email"));
        fbLoginButton.setSessionStatusCallback(new Session.StatusCallback() {
            @SuppressWarnings("deprecation")
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    ACLogger.log("Facebook Login Access Token : " + session.getAccessToken());
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                String email = (String) user.asMap().get("email");
                                String name = (String) user.asMap().get("name");
                                NetworkUtils.deleteCookie(LoginActivity.this);
                                iLoginFragmentPresentor.loginBySocialNetwork(SocialNetworkTypes.FACEBOOK, email, name, "");
                            } else {
                                showUIMessage(new UIMessage(UIMessageType.ERROR, getString(R.string.fb_login_error)), 0);
                            }
                        }
                    });
                }
            }
        });

        linkedInLoginButton = (TextView) findViewById(R.id.linkedInLoginButton_login);
        linkedInLoginButton.setTypeface(Typeface.DEFAULT_BOLD);
        linkedInLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LinkedInLoginActivity.class);
                startActivityForResult(intent, LINKEDIN_LOGIN_REQUEST_CODE);
            }
        });

    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            ACLogger.log("Logged in...");
            MyAccountApplication myAccountApplication = ((MyAccountApplication) LoginActivity.this.getApplicationContext());
            if (myAccountApplication.getLoginStatus() == Constants.LoginStatus.NOT_LOGGED_IN) {
                session.closeAndClearTokenInformation();
            }
        } else if (state.isClosed()) {
            ACLogger.log("Logged out...");
        }
    }
    //facebook login

    private void goToLandPage(Bundle bundle, int menuId) {
        bundle.putInt(Constants.BUNDLE_KEY_MENU_ID, menuId);
        openChildActivityFragment(LoginSignupFormFragment.class, bundle, false, true, true);
    }

    @Override
    public int getActivityLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public ProgressBar getProgressBar() {
        return (SmoothProgressBar) findViewById(R.id.smooth_progressbar_layout);
    }

    @Override
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar_login);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeyboard(this);
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    public void toChildActivity() {
        Intent intent = new Intent(this, ChildActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, menuId);
        startActivityForResult(intent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LINKEDIN_LOGIN_REQUEST_CODE) {
                String email = data.getStringExtra(Constants.PREFERENCE_CUSTOMER_EMAIL);
                String fullName = data.getStringExtra(Constants.PREFERENCE_CUSTOMER_FULLNAME);
                NetworkUtils.deleteCookie(LoginActivity.this);
                iLoginFragmentPresentor.loginBySocialNetwork(SocialNetworkTypes.LINKEDIN, email, fullName, "");
            }
        }

        uiHelper.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acc = result.getSignInAccount();
                iLoginFragmentPresentor.loginBySocialNetwork(SocialNetworkTypes.GOOGLE, acc.getEmail(), acc.getDisplayName(), "");
                revokeAccess();
            } else {
                showUIMessage(new UIMessage(UIMessageType.ERROR, getString(R.string.google_login_error)), 0);
            }

        }
        if (requestCode == ChildActivity.LOGIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                setResult(RESULT_CANCELED);
                finish();
            } else if (resultCode == Activity.RESULT_OK) {
                menuId = data.getIntExtra(Constants.BUNDLE_KEY_MENU_ID, -1);
                Bundle bundle = data.getExtras();
                toChildActivity();
                //goToLandPage(bundle, menuId);
            }
        } else if (requestCode == BaseAppCompatActivity.ERROR_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                setResult(RESULT_CANCELED);
                finish();
            } else if (resultCode == Activity.RESULT_OK) {
                menuId = data.getIntExtra(Constants.BUNDLE_KEY_MENU_ID, -1);
                Bundle bundle = data.getExtras();
                toChildActivity();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAppInBackground = true;
        uiHelper.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isAppInBackground = false;
        uiHelper.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
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
    public void navigateToHome() {
        getIntent().removeExtra("SUCCESS_FLAG");
    }

    @Override
    public void navigateToDeals() {
        openChildActivityFragment(DealsFragment.class, null, true, true, true);
        hideProgress(ProgressTypes.INTERACTION_ALLOWED, 0);
    }

    @Override
    public void navigateToSelectCustomerScreen() {
        openChildActivityFragment(CustomerSelectionFragment.class, null, false, true, true);
    }

    @Override
    public void showUIMessage(UIMessage uiMessage, int flag) {
        String str = uiMessage.getUiMessageType().toString();
        if (flag == Constants.FLAG_LOGIN)
        {
            if (uiMessage.getUiMessageType() == UIMessageType.SUCCESS)
            {
                isAppInBackground = false;
                Constants.LoginStatus loginStatus = ((MyAccountApplication) getApplicationContext()).getLoginStatus();
                if (
                        ((menuId == R.id.drawer_menu_item_account_updates) ||
                        (menuId == R.id.drawer_menu_item_myrfq) ||
                        (menuId == R.id.drawer_menu_item_contact_details) ||
                        (menuId == R.id.drawer_menu_item_myorders) ||
                        (menuId == R.id.drawer_menu_item_shipping_address))
                        &&
                        (loginStatus!=Constants.LoginStatus.LOGGED_IN_AS_KAM_WITH_CUSTOMER_NOT_SELECTED_YET)
                   )
                {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, menuId);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(LoginActivity.this, ContainerActivity.class);
                    intent.putExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, true);
                    startActivity(intent);
                }
            }
            else {
                UIMessageUtility.displayUIMessage(this, uiMessage);
            }
        } else {
            UIMessageUtility.displayUIMessage(this, uiMessage);
        }
    }

    @Override
    public void loginByP2SMEAccount(String userName, String password) {
        iLoginFragmentPresentor.loginByP2SMEAccount(userName, password);
    }

    @Override
    public void forgotPassword(String value) {
        iLoginFragmentPresentor.forgotPassword(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_in_button:
                signIn();
        }
    }

    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Google Sign In Error case
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                // it is disconnecting the account from app
            }
        });
    }
}