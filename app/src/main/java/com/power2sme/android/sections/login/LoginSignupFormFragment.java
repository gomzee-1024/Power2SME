package com.power2sme.android.sections.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.signup.SignupFragment;

public class LoginSignupFormFragment extends SuperFragment {
    private static final String TAG = "LoginSignupFormFragment";
    View rootView;

    //Button Button_topRegister;
    Button Button_Login;
    Button Button_SignUp;

    int targetSectionID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_LoginSignupFormFragment));
    }

    @Override
    public int getScreenTitleResId() {
        return R.string.screen_title_login;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.login_signup_form_fragment, container, false);
        initUIComponents();
        setupAdaptersAndListeners();
        setHasOptionsMenu(true);
        applyActionBarNavigationListSettings();
        Intent intent = new Intent();
        Bundle b = getFragmentDataBundle();
        targetSectionID = b.getInt(Constants.BUNDLE_KEY_MENU_ID, -1);
        intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, targetSectionID);
        getActivity().setResult(Activity.RESULT_CANCELED, intent);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void applyActionBarNavigationListSettings() {
        ActionBar actionBar;
        actionBar = baseActivity.getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
    }

    private void setupAdaptersAndListeners() {
        Button_Login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.openChildActivityFragment(LoginFragment.class, getFragmentDataBundle(), true, false, true);
            }
        });
        Button_SignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.openChildActivityFragment(SignupFragment.class, getFragmentDataBundle(), true, false, true);

            }
        });
    }

    private void initUIComponents() {

        Button_Login = (Button) rootView.findViewById(R.id.Button_signin);
        Button_SignUp = (Button) rootView.findViewById(R.id.Button_signup);

    }


    @Override
    public void onResume() {
        super.onResume();
        //uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onPause() {
        super.onPause();
        //uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //uiHelper.onSaveInstanceState(outState);
    }


}