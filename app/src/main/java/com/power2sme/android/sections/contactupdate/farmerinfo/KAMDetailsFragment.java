package com.power2sme.android.sections.contactupdate.farmerinfo;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.entities.ContactInfo;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.contactupdate.ContactInfoEditPresentorImpl;
import com.power2sme.android.sections.contactupdate.IContactInfoEditPresentor;
import com.power2sme.android.sections.contactupdate.IContactInfoEditView;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageUtility;

public class KAMDetailsFragment extends SuperFragment implements IContactInfoEditView
{
	View rootView;
    TextView TextView_name;
    TextView TextView_email;
    TextView TextView_mobile;
    MyAccountApplication myAccountApplication;
    IContactInfoEditPresentor iContactInfoEditPresentor;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_contactus));
	}

    @Override
    public int getScreenTitleResId() {
        return R.string.screen_title_contact_us;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.kam_details_fragment, container, false);
        initUIComponents();
        initUIData();
        setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();
        iContactInfoEditPresentor = new ContactInfoEditPresentorImpl(baseActivity, this);
        iContactInfoEditPresentor.getContactInfo("");
        return rootView;
    }

    private void setupAdaptersAndListeners()
    {
        TextView_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.sendEmail(baseActivity, TextView_email.getText().toString());
            }
        });

        TextView_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startCallToKAM(true);
            }
        });
    }

    private void initUIData()
    {
        SharedPreferences prefs = myAccountApplication.getPrefs();
        String name = prefs.getString(Constants.PREFERENCE_FARMER_FULLNAME, "");
        String email = prefs.getString(Constants.PREFERENCE_FARMER_EMAIL, "");
        String mobile = prefs.getString(Constants.PREFERENCE_FARMER_MOBILE, "");

        TextView_name.setText(name);
        TextView_email.setText(email);
        TextView_mobile.setText(mobile.trim().length()>0?mobile:Utils.getDefaultP2SCustomerCareNo());
    }
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
        inflater.inflate(R.menu.default_blank_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	private void applyActionBarNavigationListSettings()
    {
		baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }
	private void initUIComponents()
	{
        TextView_name = (TextView) rootView.findViewById(R.id.TextView_name);
        TextView_email = (TextView) rootView.findViewById(R.id.TextView_email);
        TextView_mobile = (TextView) rootView.findViewById(R.id.TextView_mobile);
	}

    @Override
    public void showProgress(ProgressTypes progressTypes, int flag)
    {
        baseActivity.showProgressDialog(progressTypes);
    }

    @Override
    public void hideProgress(ProgressTypes progressTypes, int flag)
    {
        baseActivity.hideProgressDialog(progressTypes);
    }
    @Override
    public void showUIMessage(UIMessage uiMessage, int flag)
    {
        if(!isAdded())
        {
            return;
        }
        if(!showErrorMessageFragment(uiMessage.getUiMessageType(), KAMDetailsFragment.class, null))
        {
            UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
        }
    }
    @Override
    public void showContactInfo(ContactInfo contactInfo)
    {
        if(contactInfo!=null && contactInfo.getCustomerLogin()!=null && contactInfo.getOrganisation()!=null)
        {
            //refresh internal data to manage session chaanges
            MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
            SharedPreferences prefs = myAccountApplication.getPrefs();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constants.PREFERENCE_CUSTOMER_FULLNAME, contactInfo.getOrganisation().getContactPerson());
            editor.putString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, contactInfo.getOrganisation().getCompany_name());
            editor.putString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER, contactInfo.getOrganisation().getPhone());
            editor.putString(Constants.PREFERENCE_CUSTOMER_EMAIL, contactInfo.getOrganisation().getEmail());

            String kamNameStr= "";
            if(contactInfo.getCustomerLogin().getFarmer_first_name()!=null && contactInfo.getCustomerLogin().getFarmer_first_name().length()>0)
            {
                kamNameStr=contactInfo.getCustomerLogin().getFarmer_first_name();
            }
            if(contactInfo.getCustomerLogin().getFarmer_last_name()!=null && contactInfo.getCustomerLogin().getFarmer_last_name().length()>0)
            {
                kamNameStr=kamNameStr+" "+contactInfo.getCustomerLogin().getFarmer_last_name();
            }
            editor.putString(Constants.PREFERENCE_FARMER_FULLNAME, kamNameStr);
            editor.putString(Constants.PREFERENCE_FARMER_EMAIL, contactInfo.getCustomerLogin().getFarmer_email());
            editor.putString(Constants.PREFERENCE_FARMER_MOBILE, contactInfo.getCustomerLogin().getFarmer_mobile());

            editor.commit();

            initUIData();
        }

    }

    //////////////////////////////////////////////////////////////
    ///////////////////// DYNAMIC PERMISSION /////////////////////
    //////////////////////////////////////////////////////////////
    Snackbar permissionSnackbar;
    private void startCallToKAM(boolean isCheckForPermission)
    {
        if(!isCheckForPermission)
        {
            Utils.startCall(baseActivity, TextView_mobile.getText().toString());
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity() ,Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
        {
            startCallToKAM(false);
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE))
            {
                permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.kamdetails_kamcall_call_permission_retry_msg));
                permissionSnackbar.show();
            }
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, Constants.PERMISSION_REQUEST_PHONE_CALL);
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
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE))
                    {
                        //user checked dont ask checkbox and denied
                        permissionSnackbar= Utils.getPermissionDisabledSnackBar(getActivity(), getActivity().getString(R.string.kamdetails_kamcall_call_permission_disable_msg));
                        permissionSnackbar.show();
                    }
                }
                return;
            }
        }
    }
}
