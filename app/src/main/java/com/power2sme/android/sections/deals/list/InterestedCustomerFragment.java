package com.power2sme.android.sections.deals.list;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.entities.LeadSource;
import com.power2sme.android.entities.v3.Deal_v3;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.entities.v3.Opportunity_v3;
import com.power2sme.android.entities.v3.Organization_v3;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.deals.interestedindeals.IShowInterestInDealsPresentor;
import com.power2sme.android.sections.deals.interestedindeals.IShowInterestInDealsView;
import com.power2sme.android.sections.deals.interestedindeals.ShowInterestInDealsPresentorImpl;
import com.power2sme.android.sections.login.LoginType;
import com.power2sme.android.sections.myrfqs.list.BuyingRFQsFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.EmailAutoCompleteAdapter;
import com.power2sme.android.utilities.customviews.InputFilterMinMax;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;

/**
 * Created by power2sme on 2/5/15.
 */
public class InterestedCustomerFragment extends SuperFragment  implements IShowInterestInDealsView
{
    View rootView;
    EditText EditText_companyName;
    EditText EditText_fullName;
    EditText EditText_mobileNumber;
    AutoCompleteTextView EditText_email;
    Button Button_submit;
    MyAccountApplication myAccountApplication;
    Deal_v3 selectedDeal;

    IShowInterestInDealsPresentor iShowInterestInDealsPresentor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_InterestedCustomerFragment));
    }

    @Override
    public int getScreenTitleResId() {
        return R.string.interested_customer_contact_details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        baseActivity = (ContainerActivity) getActivity();
//        baseActivity.setDrawerMenuEnabled(false, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_contact_us));
//        baseActivity.setSubHeader(this.getClass());
        iShowInterestInDealsPresentor = new ShowInterestInDealsPresentorImpl(baseActivity, this);
        myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.ineterested_customer_fragment, container, false);
        initUIComponents();
        setupAdaptersAndListeners();
        setHasOptionsMenu(true);
        applyActionBarNavigationListSettings();

        if(getFragmentDataBundle()!=null && getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL)!=null)
        {
            selectedDeal = getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL);
            initData();
        }

        return rootView;
    }

    private void initData()
    {
        SharedPreferences prefs = myAccountApplication.getPrefs();
        EditText_companyName.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, ""));
        EditText_fullName.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_FULLNAME, ""));
        EditText_mobileNumber.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER, ""));
        EditText_email.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_EMAIL, ""));

        if(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITH_ERPREGISTERATION)
        {
            EditText_companyName.setEnabled(false);
        }
        else
        {
            EditText_companyName.setEnabled(true);
        }

        if(myAccountApplication.getLoginStatus() == Constants.LoginStatus.NOT_LOGGED_IN)
        {
            EditText_email.setEnabled(true);
        }
        else
        {
            EditText_email.setEnabled(false);
        }
    }

    private void setupAdaptersAndListeners()
    {
        addEmailSuggesionAdapter(true);
//        final EmailAutoCompleteAdapter emailAutoCompleteAdapter = new EmailAutoCompleteAdapter(getActivity(), R.layout.auto_complete_list_item);
//        if(PermissionManager.getInstance().isAllowContact(getActivity(), getString(R.string.permission_msg_account), null))
//        {
//            EditText_email.setAdapter(emailAutoCompleteAdapter);
//        }
//
//
//        EditText_email.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                if(event.getAction()==MotionEvent.ACTION_UP)
//                {
//                    if(EditText_email.getAdapter()==null && Nammu.checkPermission(Manifest.permission.GET_ACCOUNTS))
//                    {
//                        EditText_email.setAdapter(emailAutoCompleteAdapter);
//                        emailAutoCompleteAdapter.notifyDataSetChanged();
//                    }
//                    EditText_email.showDropDown();
//                }
//                return false;
//            }
//        });

        EditText_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (Utils.isValidEmailId(EditText_email)) {
                        EditText_email.setError(null);
                    } else {
                        EditText_email.setError(baseActivity.getString(R.string.login_validationerror_emailid));
                    }
                }
            }
        });

        Button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInputs()) {
                    return;
                }

                NewRFQ_v3 newRFQ = new NewRFQ_v3();
                //organization
                Organization_v3 organization = new Organization_v3();
                organization.setCompany_name(EditText_companyName.getText().toString());
                organization.setContactPerson(EditText_fullName.getText().toString());
                organization.setEmail(EditText_email.getText().toString());
                organization.setPhone(EditText_mobileNumber.getText().toString());
                organization.setLeadSource(LeadSource.Android_Deal.toString());
                newRFQ.setObject_type_id("2");
                newRFQ.setOrganisation(organization);
                newRFQ.setDeal(selectedDeal);
                Opportunity_v3 opportunity = new Opportunity_v3();
                opportunity.setLeadSource(LeadSource.Android_Deal.toString());
                newRFQ.setOpportunity(opportunity);
                iShowInterestInDealsPresentor.addNewRFQ(newRFQ);
            }
        });
    }

    private boolean validateInputs()
    {
        boolean flag=true;

        if(EditText_companyName.getText().toString().length()>0)
        {
            EditText_companyName.setError(null);
        }
        else
        {
            EditText_companyName.setError(baseActivity.getString(R.string.addrfq_validationerror_companyname));
            EditText_companyName.requestFocus();
            flag=false;
        }

        if(Utils.isValidMobileNumber(EditText_mobileNumber))
        {
            EditText_mobileNumber.setError(null);
        }
        else
        {
            EditText_mobileNumber.setError(baseActivity.getString(R.string.addrfq_validationerror_mobilenumber));
            EditText_mobileNumber.requestFocus();
            flag=false;
        }

        if(Utils.isValidEmailId(EditText_email))
        {
            EditText_email.setError(null);
        }
        else
        {
            EditText_email.setError(baseActivity.getString(R.string.addrfq_validationerror_emailid));
            EditText_email.requestFocus();
            flag=false;
        }
        return flag;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.default_blank_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void applyActionBarNavigationListSettings() {
        baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    private void initUIComponents() {
        EditText_companyName = (EditText) rootView.findViewById(R.id.EditText_companyName);
        EditText_fullName = (EditText) rootView.findViewById(R.id.EditText_fullName);
        EditText_mobileNumber = (EditText) rootView.findViewById(R.id.EditText_mobileNumber);
        Utils.setMobileNumberWatcher(EditText_mobileNumber);
        EditText_mobileNumber.setFilters(new InputFilter[]{new InputFilterMinMax(10)});
        EditText_email = (AutoCompleteTextView) rootView.findViewById(R.id.EditText_email);
        Button_submit = (Button) rootView.findViewById(R.id.Button_submit);


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
        if(!isAdded())
        {
            return;
        }
        Bundle bundle = getFragmentDataBundle();
        if(bundle==null)
        {
            bundle=new Bundle();
            setFragmentDataBundle(bundle);
        }

        if(uiMessage.getUiMessageType()== UIMessageType.UNAUTHORIZE)
        {
            bundle.putBoolean("SaveDataFlag", true);
            bundle.putBoolean("RepeatLastActionFlag", true);
            bundle.putString(Constants.BUNDLE_KEY_LOGIN_SOURCE, Constants.LoginSources.INTERESTED_CUSTOMER.toString());
            Utils.showSessionInvalidateDialog(baseActivity, bundle);
            return;
        }

        if(uiMessage.getUiMessageType()==UIMessageType.EMAIL_ALREADY_EXIST)
        {
            bundle.putString("Populate_Email", EditText_email.getText().toString());
            bundle.putBoolean("SaveDataFlag", true);
            bundle.putBoolean("RepeatLastActionFlag", true);
            bundle.putString(Constants.BUNDLE_KEY_LOGIN_SOURCE, Constants.LoginSources.INTERESTED_CUSTOMER.toString());
            Utils.showEmailAlreadyExistDialog(baseActivity, bundle);
            return;
        }

        if(!showErrorMessageFragment(uiMessage.getUiMessageType(), InterestedCustomerFragment.class, bundle))
        {
            if(
                    (uiMessage.getUiMessageType()==UIMessageType.SUCCESS
                            || uiMessage.getUiMessageType()==UIMessageType.DIALOG_OK_BACK
                            || uiMessage.getUiMessageType()==UIMessageType.DIALOG_OK)
                            && flag==Constants.FLAG_ADD_RFQ)
            {
                Button_submit.setEnabled(true);
                Button_submit.setClickable(true);
                if(myAccountApplication.getLoginStatus() == Constants.LoginStatus.NOT_LOGGED_IN)
                {
                    ((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackActionEvent("Login", "Success", LoginType.RFQ_CREATION + "", 1);
                    SharedPreferences.Editor editor = myAccountApplication.getPrefs().edit();
                    editor.putString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, EditText_companyName.getText().toString().trim());
                    editor.putString(Constants.PREFERENCE_CUSTOMER_FULLNAME, EditText_fullName.getText().toString().trim());
                    editor.putString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER, EditText_mobileNumber.getText().toString().trim());
                    editor.putString(Constants.PREFERENCE_CUSTOMER_EMAIL, EditText_email.getText().toString().trim());
                    editor.commit();
                }


                new AlertDialog.Builder(getActivity())
                        .setMessage(uiMessage.getMessage())
                        .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {

                                dialog.dismiss();
                                navigateToMyRFQs();

                            }
                        })
                        .show();
            }
            else if(uiMessage.getUiMessageType()==UIMessageType.ERROR)
            {
                switch(flag)
                {
                    case Constants.FLAG_UNITS_LOADING:
                    case Constants.FLAG_MATERIAL_CATEGORY_LOADING:
                    case Constants.FLAG_STATES_LOADING:
                    {
                        baseActivity.onBackPressed();
                    }
                    case Constants.FLAG_ADD_RFQ:
                    {
//                        Button_submit.setEnabled(true);
//                        Button_submit.setClickable(true);
                        break;
                    }
                }
                UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
            }

        }
    }


    @Override
    public void navigateToMyRFQs()
    {
        baseActivity.openChildActivityFragment(BuyingRFQsFragment.class, null, true, true, true);
    }


    //////////////////////////////////////////////////////////////
    ///////////////////// DYNAMIC PERMISSION /////////////////////
    //////////////////////////////////////////////////////////////
    Snackbar permissionSnackbar;
//    private void checkLocationPermission(boolean isCheckForPermission)
//    {
//        if(!isCheckForPermission)
//        {
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
//                permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.interestedondeals_latlongdeviceregister_location_permission_retry_msg));
//                permissionSnackbar.show();
//            }
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_READ_LOCATION);
//        }
//    }

    private void addEmailSuggesionAdapter(boolean isCheckForPermission)
    {
        if(!isCheckForPermission)
        {
            final EmailAutoCompleteAdapter emailAutoCompleteAdapter = new EmailAutoCompleteAdapter(getActivity(), R.layout.auto_complete_list_item);
            EditText_email.setAdapter(emailAutoCompleteAdapter);
            EditText_email.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    if (event.getAction() == MotionEvent.ACTION_UP)
                    {
                        if (EditText_email.getAdapter() == null)
                        {
                            EditText_email.setAdapter(emailAutoCompleteAdapter);
                            emailAutoCompleteAdapter.notifyDataSetChanged();
                        }
                        EditText_email.showDropDown();
                    }
                    return false;
                }
            });
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity() ,Manifest.permission.GET_ACCOUNTS)== PackageManager.PERMISSION_GRANTED)
        {
            addEmailSuggesionAdapter(false);
//            checkLocationPermission(true);
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.GET_ACCOUNTS))
            {
                permissionSnackbar=Snackbar.make(getActivity().findViewById(android.R.id.content), getActivity().getString(R.string.interestedondeals_emailsuggession_account_permission_retry_msg), Snackbar.LENGTH_INDEFINITE);
                permissionSnackbar.setActionTextColor(Color.RED);
                permissionSnackbar.show();
            }
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS}, Constants.PERMISSION_REQUEST_READ_ACCOUNTS);
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
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case Constants.PERMISSION_REQUEST_READ_ACCOUNTS:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    addEmailSuggesionAdapter(false);
                }
//                checkLocationPermission(true);
                return;
            }
//            case Constants.PERMISSION_REQUEST_READ_LOCATION:
//            {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                {
//                    checkLocationPermission(false);
//                }
//            }
        }
    }

}