package com.power2sme.android.sections.signup;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.power2sme.android.ContainerActivity;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.dtos.request.RequestRegisterOrgDto;
import com.power2sme.android.entities.v3.Organization_v3;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.contactupdate.IContactInfoEditPresentor;
import com.power2sme.android.sections.login.LoginSignupFormFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.EmailAutoCompleteAdapter;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;

public class SignupFragment extends SuperFragment implements ISignupFragmentView
{
	private static final String TAG="LoginFragment";
	private static SignupFragment signupFragment;
	LoginSignupFormFragment loginSignupFormFragment;
	View rootView;
	Button  Button_register;
	AutoCompleteTextView EditText_Email;
	EditText EditText_mobileNumber;
	TextView TextView_Signup_Contract;
	ISignupFragmentPresentor iLoginFragmentPresentor;
	IContactInfoEditPresentor iContactInfoEditPresentor;
	int targetSectionID;

	public static SignupFragment getInstance()
	{
		if(signupFragment==null)
			signupFragment=new SignupFragment();
		return signupFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_SignupFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_signup;
	}

	public View getView(LoginSignupFormFragment loginSignupFormFragment, BaseAppCompatActivity baseActivity, LayoutInflater inflater)
	{
		this.loginSignupFormFragment=loginSignupFormFragment;
		super.baseActivity = baseActivity;
        rootView = inflater.inflate(R.layout.signup_fragment, null);
        initUIComponents();
		setupAdaptersAndListeners();
		iLoginFragmentPresentor=new SignupFragmentPresentorImpl(baseActivity, this);
		return rootView;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.signup_fragment, container, false);
        initUIComponents();
		setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();
		iLoginFragmentPresentor=new SignupFragmentPresentorImpl(baseActivity, this);

		Intent intent = new Intent();
		Bundle b = getFragmentDataBundle();
		targetSectionID = b.getInt(Constants.BUNDLE_KEY_MENU_ID, -1);
		intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, targetSectionID);
		getActivity().setResult(Activity.RESULT_CANCELED, intent);

		// if user will not give location permission here then device registeration service will not get lat long of device
//		if(PermissionManager.getInstance().isAllowLocation(getActivity(), getString(R.string.permission_msg_location), null))
//		{
//
//		}


		return rootView;
    }
    
    private void setupAdaptersAndListeners() 
	{
    	Button_register.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				if(isInputsValid())
				{
					NetworkUtils.deleteCookie(baseActivity);
					RequestRegisterOrgDto registerOrgDto= new RequestRegisterOrgDto();
					Organization_v3 org=new Organization_v3();
					registerOrgDto.setOrganisation(org);

					org.setEmail(EditText_Email.getText().toString());
					org.setPhone(EditText_mobileNumber.getText().toString());

					iLoginFragmentPresentor.registerP2SMEAccount(registerOrgDto);
					InputMethodManager imm = (InputMethodManager)baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(EditText_mobileNumber.getWindowToken(), 0);
				}
			}
		});
	}
    
	private void initUIComponents() 
	{
		Button_register=(Button)rootView.findViewById(R.id.Button_register);
		EditText_Email=(AutoCompleteTextView)rootView.findViewById(R.id.EditText_Email);
		addEmailSuggesionAdapter(true);
		EditText_Email.requestFocus();

		EditText_Email.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					if (Utils.isValidEmailId(EditText_Email)) {
						EditText_Email.setError(null);
					} else {
						EditText_Email.setError(baseActivity.getString(R.string.login_validationerror_emailid));
					}
				}
			}
		});

		EditText_mobileNumber=(EditText)rootView.findViewById(R.id.EditText_mobileNumber);
		Utils.setMobileNumberWatcher(EditText_mobileNumber);

		String contract_string=getString(R.string.signup_contract_text);
		SpannableString ss = new SpannableString(contract_string);
		ClickableSpan clickableSpan = new ClickableSpan() {
			@Override
			public void onClick(View textView) {
			}
		};
		String terms=getString(R.string.signup_contract_Terms_of_service_label);
		String policy=getString(R.string.signup_contract_Privacy_policy_label);
		ss.setSpan(new MyClickableSpan(terms), contract_string.indexOf(terms), contract_string.indexOf(terms)+terms.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new MyClickableSpan(policy), contract_string.indexOf(policy), contract_string.indexOf(policy)+policy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		TextView_Signup_Contract = (TextView)rootView.findViewById(R.id.TextView_contract_signup);
		TextView_Signup_Contract.setText(ss);
		TextView_Signup_Contract.setMovementMethod(LinkMovementMethod.getInstance());
		TextView_Signup_Contract.setHighlightColor(Color.TRANSPARENT);
	}

	private void showAlertBox(String url)
	{
		AlertDialog.Builder alert;
		alert = new AlertDialog.Builder(getContext());
		String terms=getString(R.string.signup_contract_Terms_of_service_label);
		String policy=getString(R.string.signup_contract_Privacy_policy_label);
		WebView wv = new WebView(getContext());
		View view=View.inflate(getContext(),R.layout.dialog_title,null);

		TextView textView_webview_title =(TextView) view.findViewById(R.id.Dialog_title);
		textView_webview_title.setText(url);
		alert.setCustomTitle(view);
		if(terms.equals(url))
		wv.loadUrl(APIs.URL_TERMS_AND_CONDITION);
		if(policy.equals(url))
			wv.loadUrl(APIs.URL_PRIVACY_POLICY);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		alert.setView(wv);
		alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		alert.show();
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
		if(!showErrorMessageFragment(uiMessage.getUiMessageType(), LoginSignupFormFragment.class, null))
		{
			if(uiMessage.getUiMessageType()==UIMessageType.ERROR)
			{
				switch(flag)
				{
					case Constants.FLAG_DESIGNATION_LOADING:
					case Constants.FLAG_STATES_LOADING:
					{
						baseActivity.onBackPressed();
					}
				}
			}
			else if(uiMessage.getUiMessageType()==UIMessageType.SUCCESS)
			{
				if(flag == Constants.FLAG_REGISTER_USER)
				{
//					baseActivity.setIntent(new Intent(baseActivity, ContainerActivity.class));
//					Intent intent = new Intent(baseActivity, ContainerActivity.class);
//					baseActivity.startActivity(intent);

					Intent intent = new Intent(baseActivity, ContainerActivity.class);
					intent.putExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, true);
					startActivity(intent);
				}
			}
			UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
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
		ActionBar actionBar;
		actionBar=baseActivity.getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setHomeAsUpIndicator(R.drawable.cancel);
	}

	@Override
	public void navigateToLoginScreen() {
		// TODO Auto-generated method stub

	}

	private boolean isInputsValid()
	{
		boolean successFlag=true;
		if(Utils.isValidEmailId(EditText_Email))
		{
			EditText_Email.setError(null);
		}
		else
		{
			EditText_Email.setError(baseActivity.getString(R.string.signup_validationerror_emailid));
			EditText_Email.requestFocus();
			successFlag=false;
		}
		return successFlag;
	}
	
	private class MyClickableSpan extends ClickableSpan {
		private final String mText;
		private MyClickableSpan(final String text) {
			mText = text;
		}
		@Override
		public void onClick(final View widget) {
			String terms=getString(R.string.signup_contract_Terms_of_service_label);
			String policy=getString(R.string.signup_contract_Privacy_policy_label);
			if(mText.equals(terms)) {
				showAlertBox(terms);
			}if(mText.equals(policy))
				showAlertBox(policy);

		}
	}

	//////////////////////////////////////////////////////////////
	///////////////////// DYNAMIC PERMISSION /////////////////////
	//////////////////////////////////////////////////////////////
	Snackbar permissionSnackbar;
//	private void checkLocationPermission(boolean isCheckForPermission)
//	{
//		if(!isCheckForPermission)
//		{
//			return;
//		}
//
//		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
//		{
//			checkLocationPermission(false);
//		}
//		else
//		{
//			if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
//			{
//				permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.signup_latlongdeviceregister_location_permission_retry_msg));
//				permissionSnackbar.show();
//			}
//			requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_READ_LOCATION);
//		}
//	}

	private void addEmailSuggesionAdapter(boolean isCheckForPermission)
	{
		if(!isCheckForPermission)
		{
			final EmailAutoCompleteAdapter emailAutoCompleteAdapter = new EmailAutoCompleteAdapter(getActivity(), R.layout.auto_complete_list_item);
			EditText_Email.setAdapter(emailAutoCompleteAdapter);
			EditText_Email.setOnTouchListener(new View.OnTouchListener()
			{
				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					if (event.getAction() == MotionEvent.ACTION_UP)
					{
						if (EditText_Email.getAdapter() == null)
						{
							EditText_Email.setAdapter(emailAutoCompleteAdapter);
							emailAutoCompleteAdapter.notifyDataSetChanged();
						}
						EditText_Email.showDropDown();
					}
					return false;
				}
			});
			return;
		}

		if (ContextCompat.checkSelfPermission(getActivity() ,Manifest.permission.GET_ACCOUNTS)== PackageManager.PERMISSION_GRANTED)
		{
			addEmailSuggesionAdapter(false);
//			checkLocationPermission(true);
		}
		else
		{

			if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.GET_ACCOUNTS))
			{
				permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.signup_emailsuggession_account_permission_retry_msg));
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
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode)
		{
			case Constants.PERMISSION_REQUEST_READ_ACCOUNTS:
			{
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					addEmailSuggesionAdapter(false);
				}
//				checkLocationPermission(true);
				return;
			}
//			case Constants.PERMISSION_REQUEST_READ_LOCATION:
//			{
//				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//				{
//					checkLocationPermission(false);
//				}
//			}
		}
	}
}

