package com.power2sme.android.sections.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.EmailAutoCompleteAdapter;
import com.power2sme.android.utilities.customviews.InputTextDialogCallback;

public class LoginFragment extends SuperFragment implements OnClickListener
{
	private static final String TAG="LoginFragment";
	View rootView;
	
	EditText EditText_password;
	ImageButton ImageButton_showPassword;
	AutoCompleteTextView EditText_userName;
	Button Button_login;
	TextView TextView_forgotPassword;
	CheckBox CheckBox_rememberUsernamePassword;
	Context context;
	ILoginCallback mCallback;
	private static LoginFragment loginFragment;
	public static LoginFragment getInstance()
	{
		if(loginFragment==null)
			loginFragment=new LoginFragment();
		return loginFragment;
	}

	public interface ILoginCallback {
		public void loginByP2SMEAccount(String email,String password);
		public void forgotPassword(String value);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try{
			mCallback = (ILoginCallback)context;
		}catch (ClassCastException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_LoginFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_sign_in;
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.login_fragment, container, false);
        context=container.getContext();
        initUIComponents();
		setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();

		// if user will not give location permission here then device registeration service will not get lat long of device
//		if(PermissionManager.getInstance().isAllowLocation(getActivity(), getString(R.string.permission_msg_location), null))
//		{
//
//		}


		return rootView;
    }

    private void setupAdaptersAndListeners() 
	{
		Button_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String userName = EditText_userName.getText().toString().trim();
				String password = EditText_password.getText().toString().trim();
				if (isInputsValidated(userName, password)) {
					saveInRememberPassword(userName, password);
					NetworkUtils.deleteCookie(baseActivity);
					mCallback.loginByP2SMEAccount(userName, password);
					InputMethodManager imm = (InputMethodManager) baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(EditText_password.getWindowToken(), 0);
				}
			}
		});
		TextView_forgotPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showInputTextDialogWithOkCancelButton(baseActivity, baseActivity.getString(R.string.please_enter_registered_email_id), EditText_userName.getText().toString(), new InputTextDialogCallback() {
					@Override
					public void onComplete(String value) {
						if (value != null)
							mCallback.forgotPassword(value);
					}
				});
			}
		});


		ImageButton_showPassword.setOnClickListener(this);
	}
    
    private boolean isInputsValidated(String userName, String password)
    {
		boolean successFlag=true;
		if(password.length()==0)
		{
			EditText_password.setError(baseActivity.getString(R.string.login_validationerror_empty_password));
			successFlag=false;
			EditText_password.requestFocus();
		}
		else
		{
			EditText_password.setError(null);
		}

		if(userName!=null && userName.trim().length()>0)
		{
			if(Utils.isValidEmailId(EditText_userName))
			{
				EditText_userName.setError(null);
			}
			else
			{
				EditText_userName.setError(baseActivity.getString(R.string.login_validationerror_emailid));
				successFlag=false;
				EditText_userName.requestFocus();
			}
		}
		else
		{
			EditText_userName.setError(baseActivity.getString(R.string.login_validationerror_empty_emailid));
			successFlag=false;
			EditText_userName.requestFocus();
		}

		
    	return successFlag;
    }

	private void saveInRememberPassword(String userName, String password)
	{
		MyAccountApplication app = (MyAccountApplication) rootView.getContext().getApplicationContext();
		SharedPreferences.Editor edt = app.getPrefs().edit();
		if(CheckBox_rememberUsernamePassword.isChecked())
		{
			edt.putString(Constants.PREFERENCE_REMEMBERED_USERNAME, userName);
			edt.putString(Constants.PREFERENCE_REMEMBERED_PASSWORD, Utils.encryptPassword(password));
			edt.putBoolean(Constants.PREFERENCE_IS_REMEMBERED_CREDENTIAL, true);
		}
		else
		{
			edt.putBoolean(Constants.PREFERENCE_IS_REMEMBERED_CREDENTIAL, false);
		}
		edt.commit();
	}
	private void initWithRememberPassword()
	{
		MyAccountApplication app = (MyAccountApplication)rootView.getContext().getApplicationContext();
		boolean isPopulateCredential = app.getPrefs().getBoolean(Constants.PREFERENCE_IS_REMEMBERED_CREDENTIAL, false);
		if(isPopulateCredential)
		{
			String previousUserName = app.getPrefs().getString(Constants.PREFERENCE_REMEMBERED_USERNAME, "");
			String previousPaswword = app.getPrefs().getString(Constants.PREFERENCE_REMEMBERED_PASSWORD, "");
			EditText_password.setText(Utils.decryptPassword(previousPaswword));
			EditText_userName.setText(previousUserName);
			CheckBox_rememberUsernamePassword.setChecked(true);
		}
		else
		{
			CheckBox_rememberUsernamePassword.setChecked(false);
		}
	}
	private void initUIComponents() 
	{
		Button_login=(Button)rootView.findViewById(R.id.Button_login);
		TextView_forgotPassword=(TextView)rootView.findViewById(R.id.TextView_forgotPassword);
		EditText_password=(EditText)rootView.findViewById(R.id.EditText_password);
		EditText_userName=(AutoCompleteTextView)rootView.findViewById(R.id.EditText_userName);

		if(getFragmentDataBundle().containsKey(Constants.BUNDLE_KEY_EMAIL))
		{
			EditText_userName.setText(getFragmentDataBundle().getString(Constants.BUNDLE_KEY_EMAIL));
		}

		ImageButton_showPassword=(ImageButton)rootView.findViewById(R.id.ImageButton_showPassword);
		CheckBox_rememberUsernamePassword=(CheckBox)rootView.findViewById(R.id.CheckBox_rememberUsernamePassword);
		CheckBox_rememberUsernamePassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					MyAccountApplication app = (MyAccountApplication) rootView.getContext().getApplicationContext();
					SharedPreferences.Editor edt = app.getPrefs().edit();
					edt.remove(Constants.PREFERENCE_REMEMBERED_USERNAME);
					edt.remove(Constants.PREFERENCE_REMEMBERED_PASSWORD);
					edt.remove(Constants.PREFERENCE_IS_REMEMBERED_CREDENTIAL);
					edt.commit();
				}
			}
		});

		initWithRememberPassword();

		if(getFragmentDataBundle().getString("Populate_Email")!=null)
		{
			EditText_userName.setText(getFragmentDataBundle().getString("Populate_Email"));
		}

//		final EmailAutoCompleteAdapter emailAutoCompleteAdapter = new EmailAutoCompleteAdapter(getActivity(), R.layout.auto_complete_list_item);
//
//		if(PermissionManager.getInstance().isAllowContact(getActivity(), getString(R.string.permission_msg_account), null))
//		{
//			EditText_userName.setAdapter(emailAutoCompleteAdapter);
//		}
		addEmailSuggesionAdapter(true);


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
	
	public static void showInputTextDialogWithOkCancelButton(Context context,final String title, String prefilledEmail, final InputTextDialogCallback inputTextDialogCallback)
	{
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.input_text_dialog_with_ok_cancel_button, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(promptsView);
		final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
		userInput.setText(prefilledEmail);
//		Utils.setEmailWatcher(userInput);
		TextView textView1 = (TextView) promptsView.findViewById(R.id.textView1);
		textView1.setText(title);
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK", null)
			.setNegativeButton("Cancel",null);	
		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.setOnShowListener(new DialogInterface.OnShowListener() 
		{
		    @Override
		    public void onShow(DialogInterface dialog) 
		    {
		        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
		        positiveButton.setOnClickListener(new OnClickListener()
		        {
		            @Override
		            public void onClick(View view) 
		            {
	            		if(Utils.isValidEmailId(userInput))
	            		{
	            			userInput.setError(null);
				    		inputTextDialogCallback.onComplete(userInput.getText().toString());
				    		alertDialog.cancel();
	            		}
	            		else
	            		{
	            			userInput.setError("Please enter valid email id.");
	            		}
		            }
		        });
		        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
		        negativeButton.setOnClickListener(new OnClickListener()
		        {
		            @Override
		            public void onClick(View view) 
		            {
		            	inputTextDialogCallback.onComplete(null);
		            	alertDialog.cancel();
		            }
		        });
		    }
		});
		alertDialog.show();
	}

	@Override
	public void onClick(View view) {

		switch(view.getId()) {
			case R.id.ImageButton_showPassword:
				showHidePassword();
				break;
			default:break;
		}
	}

	boolean flag_visiblePassword=false;
	public void showHidePassword()
	{
		if(!flag_visiblePassword) {
			flag_visiblePassword=true;
			EditText_password.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		else
		{
			flag_visiblePassword=false;
			EditText_password.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

		}
		EditText_password.setSelection(EditText_password.getText().length());


	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallback =null;
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
//		if (ContextCompat.checkSelfPermission(getActivity() ,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
//		{
//			checkLocationPermission(false);
//		}
//		else
//		{
//			if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
//			{
//				permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.login_latlongdeviceregister_location_permission_retry_msg));
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
			EditText_userName.setAdapter(emailAutoCompleteAdapter);
			EditText_userName.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					if(event.getAction()==MotionEvent.ACTION_UP)
					{
						if(EditText_userName.getAdapter()==null)
						{
							EditText_userName.setAdapter(emailAutoCompleteAdapter);
							emailAutoCompleteAdapter.notifyDataSetChanged();
						}
						EditText_userName.showDropDown();
					}
					return false;
				}
			});
			EditText_userName.requestFocus();
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
				permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.login_emailsuggession_account_permission_retry_msg));
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

