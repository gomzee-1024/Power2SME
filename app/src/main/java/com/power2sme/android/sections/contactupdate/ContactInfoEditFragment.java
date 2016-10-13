package com.power2sme.android.sections.contactupdate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.power2sme.android.ContainerActivity;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.entities.City;
import com.power2sme.android.entities.ContactInfo;
import com.power2sme.android.entities.Designation;
import com.power2sme.android.entities.State;
import com.power2sme.android.entities.v3.Organization_v3;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.utilities.styels.StyleTypes;
import com.power2sme.android.utilities.styels.StylesManager;
import com.power2sme.android.utilities.customviews.SpinnerHintAdapter;
import com.power2sme.android.utilities.Utils;

public class ContactInfoEditFragment extends SuperFragment implements IContactInfoEditView, TextView.OnEditorActionListener
{
	View rootView;
	
	EditText EditText_companyName;
	EditText EditText_fullName;
	EditText EditText_contactNumber;
	EditText EditText_email;
	EditText EditText_street;
	EditText EditText_city;
	EditText EditText_state;
	EditText EditText_pincode;
	TextView TextView_Location;
	TextView TextView_CompanyName;
	Button Button_submit;
	
	IContactInfoEditPresentor iContactInfoEditPresentor;
	
	ContactInfo contactInfo;
	State selectedState;
//	Designation userDesignation;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_ContactInfoEditFragment));
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Button_submit.setEnabled(false);
		Button_submit.setClickable(false);
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_contact_edit;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
    	if(myAccountApplication.getLoginStatus() == Constants.LoginStatus.NOT_LOGGED_IN)
		{
    		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.message_fragment, container, false);
    		((TextView)rootView.findViewById(R.id.TextView_message)).setText("Available to Customers only.");
			((Button)rootView.findViewById(R.id.Button_getAQuote)).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(baseActivity, ChildActivity.class);
					intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
					startActivity(intent);
				}
			});
    		return rootView;
		}
    	
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.contact_info_edit_fragment, container, false);
        iContactInfoEditPresentor = new ContactInfoEditPresentorImpl(baseActivity, this);
        initUIComponents();
		setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();

		SharedPreferences prefs = myAccountApplication.getPrefs();
		iContactInfoEditPresentor.getContactInfo("");

		if(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITH_ERPREGISTERATION)
		{
			EditText_companyName.setEnabled(false);
			EditText_companyName.setClickable(false);
			EditText_companyName.setTextColor(getResources().getColor(R.color.textfield_grayedout_color));
		}
		else
		{
			EditText_companyName.setEnabled(true);
			EditText_companyName.setClickable(true);
			EditText_companyName.setTextColor(getResources().getColor(R.color.black));
		}
		return rootView;
    }
    private void initUIData()
    {
    	if(contactInfo!=null && contactInfo.getOrganisation()!=null)
    	{
    		EditText_companyName.setText(contactInfo.getOrganisation().getCompany_name().trim());
    		EditText_fullName.setText(contactInfo.getOrganisation().getContactPerson().trim());
    		EditText_contactNumber.setText(contactInfo.getOrganisation().getPhone());
			EditText_email.setText(contactInfo.getOrganisation().getEmail());

			EditText_city.setText(contactInfo.getOrganisation().getBill_city());
			EditText_state.setText(contactInfo.getOrganisation().getBill_state());
			EditText_street.setText(contactInfo.getOrganisation().getBill_street());
			EditText_pincode.setText(contactInfo.getOrganisation().getBill_pincode());

			TextView_CompanyName.setText(contactInfo.getOrganisation().getCompany_name().trim());
			String Title_Location=contactInfo.getOrganisation().getBill_city();
			if(contactInfo.getOrganisation().getBill_pincode()!=null && contactInfo.getOrganisation().getBill_pincode().length()>0)
			{
				Title_Location = ", " + contactInfo.getOrganisation().getBill_pincode();
			}
			TextView_Location.setText(Title_Location);

			EditText_companyName.addTextChangedListener(textWatcher);
			EditText_fullName.addTextChangedListener(textWatcher);
			EditText_contactNumber.addTextChangedListener(textWatcher);
			EditText_email.addTextChangedListener(textWatcher);
			EditText_city.addTextChangedListener(textWatcher);
			EditText_state.addTextChangedListener(textWatcher);
			EditText_street.addTextChangedListener(textWatcher);
			EditText_pincode.addTextChangedListener(textWatcher);
    	}
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

	private void setupAdaptersAndListeners() 
	{
		Button_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				submitData();
			}
		});
	}
	private void submitData(){
		if (isFeildsValidated()) {
			ContactInfo contactInfo = new ContactInfo();
			Organization_v3 org = new Organization_v3();
			org.setCompany_name(EditText_companyName.getText().toString());
			org.setContactPerson(EditText_fullName.getText().toString());
			org.setPhone(EditText_contactNumber.getText().toString());
			org.setEmail(EditText_email.getText().toString());
			org.setBill_city(EditText_city.getText().toString());
			org.setBill_state(EditText_state.getText().toString());
			org.setBill_street(EditText_street.getText().toString());
			org.setBill_pincode(EditText_pincode.getText().toString());
			contactInfo.setOrganisation(org);
			iContactInfoEditPresentor.editContactInfo(contactInfo);
		}
	}

	private void initUIComponents()
	{
		EditText_companyName = (EditText)rootView.findViewById(R.id.EditText_companyName);
		EditText_fullName = (EditText)rootView.findViewById(R.id.EditText_fullName);
		EditText_contactNumber = (EditText)rootView.findViewById(R.id.EditText_contactNumber);
		Utils.setMobileNumberWatcher(EditText_contactNumber);
		EditText_email = (EditText)rootView.findViewById(R.id.EditText_email);
		EditText_email.setClickable(false);
		EditText_email.setEnabled(false);
		EditText_email.setTextColor(getResources().getColor(R.color.textfield_grayedout_color));
		Utils.setEmailWatcher(EditText_email);
		EditText_street = (EditText)rootView.findViewById(R.id.EditText_street);
		EditText_city = (EditText)rootView.findViewById(R.id.EditText_city);
		EditText_city.setClickable(false);
		EditText_city.setEnabled(false);
		EditText_city.setTextColor(getResources().getColor(R.color.textfield_grayedout_color));
		EditText_state = (EditText)rootView.findViewById(R.id.EditText_state);
		EditText_state.setClickable(false);
		EditText_state.setEnabled(false);
		EditText_state.setTextColor(getResources().getColor(R.color.textfield_grayedout_color));
		EditText_pincode = (EditText)rootView.findViewById(R.id.EditText_pincode);
		Utils.setPincodeWatcher(EditText_pincode);
		EditText_pincode.setOnEditorActionListener(this);
		Button_submit  = (Button) rootView.findViewById(R.id.Button_submit);
		TextView_CompanyName=(TextView)rootView.findViewById(R.id.TextView_CompanyName);
		TextView_Location=(TextView)rootView.findViewById(R.id.TextView_Location);
	}

	TextWatcher textWatcher=new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{

		}

		@Override
		public void afterTextChanged(Editable s)
		{
			Button_submit.setEnabled(true);
			Button_submit.setClickable(true);
		}
	};

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
		if(!showErrorMessageFragment(uiMessage.getUiMessageType(), ContactInfoEditFragment.class, null))
		{
			UIMessageUtility.displayUIMessage(baseActivity, uiMessage);	
			if(uiMessage.getUiMessageType()==UIMessageType.SUCCESS && flag==Constants.FLAG_EDIT_CONTACT_INFO_EDITING)
			{
				updateLocationTextView();
				baseActivity.onBackPressed();
			}

		}
	}

	@Override
	public void showContactInfo(ContactInfo contactInfo) 
	{
		this.contactInfo=contactInfo;
		
		//refresh internal data to manage session chaanges
		MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
		SharedPreferences prefs = myAccountApplication.getPrefs();
		Editor editor = prefs.edit();
		editor.putString(Constants.PREFERENCE_CUSTOMER_FULLNAME, contactInfo.getOrganisation().getContactPerson());
		editor.putString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, contactInfo.getOrganisation().getCompany_name());
		editor.putString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER, contactInfo.getOrganisation().getPhone());
		editor.putString(Constants.PREFERENCE_CUSTOMER_EMAIL, contactInfo.getOrganisation().getEmail());
		editor.commit();
		
		initUIData();
	}
	private boolean isFeildsValidated()
	{
		boolean successFlag=true;

		if(EditText_companyName.getText().toString().length() > 0)
		{
			EditText_companyName.setError(null);
		}
		else
		{
			EditText_companyName.setError(baseActivity.getString(R.string.contactupdate_validationerror_companyname));
			successFlag=false;
		}

		if(EditText_fullName.getText().toString().length() > 0)
		{
			EditText_fullName.setError(null);
		}
		else
		{
			EditText_fullName.setError(baseActivity.getString(R.string.contactupdate_validationerror_fullname));
			successFlag=false;
		}

		if(Utils.isValidMobileNumber(EditText_contactNumber))
		{
			EditText_contactNumber.setError(null);
		}
		else
		{
			EditText_contactNumber.setError(baseActivity.getString(R.string.contactupdate_validationerror_mobilenumber));
			successFlag=false;
		}

		if(EditText_email.getText().toString().length() > 0)
		{
			EditText_email.setError(null);
		}
		else
		{
			EditText_email.setError(baseActivity.getString(R.string.contactupdate_validationerror_email));
			successFlag=false;
		}

		if(Utils.isValidPinCode(EditText_pincode))
		{
			EditText_pincode.setError(null);
		}
		else
		{
			EditText_pincode.setError(baseActivity.getString(R.string.contactupdate_validationerror_pincode));
			successFlag=false;
		}

		contactInfo.getOrganisation().setCompany_name(contactInfo.getOrganisation().getCompany_name() == null ? "" : contactInfo.getOrganisation().getCompany_name());
		contactInfo.getOrganisation().setContactPerson((contactInfo.getOrganisation().getContactPerson() == null ? "" : contactInfo.getOrganisation().getContactPerson()));
		contactInfo.getOrganisation().setPhone(contactInfo.getOrganisation().getPhone() == null ? "" : contactInfo.getOrganisation().getPhone());
		contactInfo.getOrganisation().setEmail(contactInfo.getOrganisation().getEmail() == null ? "" : contactInfo.getOrganisation().getEmail());
		contactInfo.getOrganisation().setBill_city(contactInfo.getOrganisation().getBill_city() == null ? "" : contactInfo.getOrganisation().getBill_city());
		contactInfo.getOrganisation().setBill_state(contactInfo.getOrganisation().getBill_state() == null ? "" : contactInfo.getOrganisation().getBill_state());
		contactInfo.getOrganisation().setBill_street(contactInfo.getOrganisation().getBill_street() == null ? "" : contactInfo.getOrganisation().getBill_street());
		contactInfo.getOrganisation().setBill_pincode(contactInfo.getOrganisation().getBill_pincode() == null ? "" : contactInfo.getOrganisation().getBill_pincode());

		if(successFlag 
				&& contactInfo.getOrganisation().getCompany_name().equals(EditText_companyName.getText().toString())
				&& contactInfo.getOrganisation().getContactPerson().equals(EditText_fullName.getText().toString())
				&& contactInfo.getOrganisation().getPhone().equals(EditText_contactNumber.getText().toString())
				&& contactInfo.getOrganisation().getEmail().equals(EditText_email.getText().toString())
				&& contactInfo.getOrganisation().getBill_city().equals(EditText_city.getText().toString())
				&& contactInfo.getOrganisation().getBill_state().equals(EditText_state.getText().toString())
				&& contactInfo.getOrganisation().getBill_street().equals(EditText_street.getText().toString())
				&& contactInfo.getOrganisation().getBill_pincode().equals(EditText_pincode.getText().toString())

		)
		{
			successFlag=false;
		}
		return successFlag;
	}


	/**
	 * Method to update location textview text in Header
	 */
	public void updateLocationTextView()
	{
		String Title_Location=EditText_city.getText()+", "
				//+EditText_state.getText()+", "
				+EditText_pincode.getText();
		TextView_Location.setText(Title_Location);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if(actionId== EditorInfo.IME_ACTION_DONE){
			submitData();
			return true;
		}
		return false;
	}
}
