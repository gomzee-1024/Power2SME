package com.power2sme.android.utilities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.callbacks.DialogsSingleChoiceCallback;
import com.power2sme.android.callbacks.ForgotPasswordDialogCallback;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.entities.v3.CustomerLogin_v3;
import com.power2sme.android.entities.v3.Employee_v3;
import com.power2sme.android.entities.v3.Organization_v3;
import com.power2sme.android.sections.accountupdates.AccountUpdatesFragment;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.activities.LoginActivity;
import com.power2sme.android.sections.agentlogin.CustomerSearchResultsFragment;
import com.power2sme.android.sections.agentlogin.CustomerSelectionFragment;
import com.power2sme.android.sections.contactupdate.ContactInfoEditFragment;
import com.power2sme.android.sections.contactupdate.farmerinfo.KAMDetailsFragment;
import com.power2sme.android.sections.deals.list.DealsFragment;
import com.power2sme.android.sections.deals.list.InterestedCustomerFragment;
import com.power2sme.android.sections.deliveryaddress.insert.DeliveryAddressesInsertFragment;
import com.power2sme.android.sections.deliveryaddress.list.DeliveryAddressesListFragment;
import com.power2sme.android.sections.home.HomeFragment;
import com.power2sme.android.sections.login.LoginFragment;
import com.power2sme.android.sections.login.LoginSignupFormFragment;
import com.power2sme.android.sections.market.MarketPlaceHomeFragment;
import com.power2sme.android.sections.msgscreens.NoInternetFragment;
import com.power2sme.android.sections.msgscreens.ServerErrorFragment;
import com.power2sme.android.sections.myorders.list.BuyingOrdersFragment;
import com.power2sme.android.sections.myorders.shipmentdetails.ShipmentDetailsFragment;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.sections.myrfqs.list.BuyingRFQsFragment;
import com.power2sme.android.sections.settings.AboutP2SMEFragment;
import com.power2sme.android.sections.settings.AboutUsFragment;
import com.power2sme.android.sections.settings.PrivacyPolicyFragment;
import com.power2sme.android.sections.settings.RateThisAppFragment;
import com.power2sme.android.sections.settings.TermsAndConditionsFragment;
import com.power2sme.android.sections.settings.WebBrowserFragment;
import com.power2sme.android.sections.signup.SignupFragment;
import com.power2sme.android.sections.smekhabar.SMEKhabarFragment;
import com.power2sme.android.sections.smekhabar.SMENewsDetailsFragment;
import com.power2sme.android.utilities.customviews.InputFilterMinMax;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Pattern;

public class Utils 
{
	private static Hashtable<String, Object> lookupMasterTable;
	public static Hashtable<String, Object> deserializeLookupMasterTable(Context context)
	{
		try
		{
			if(lookupMasterTable==null)
			{
				FileInputStream fis = context.openFileInput("app_settings.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				lookupMasterTable = (Hashtable<String, Object>) ois.readObject();
				lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_DATA_EXPIRY, Constants.CACHE_EXPIRY_TIME);
				return lookupMasterTable;
			}
			else
			{
				return lookupMasterTable;
			}
		}
		catch(Exception ex)
		{

		}
		return null;
	}
	public static void serializeLookupMasterTable(Context activity, Hashtable<String, Object> lookupMasterTable)
	{
		try
		{
			FileOutputStream fos = activity.openFileOutput("app_settings.ser", activity.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lookupMasterTable);
			oos.close();
			fos.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static String getOrderStatus(Context context, int status)
	{
		try
		{
			switch(status)
			{
				case 1:
					return context.getString(R.string.myorders_orderstate_delivery_in_progress);
				case 2:
					return context.getString(R.string.myorders_orderstate_delivered);
				case 3:
					return context.getString(R.string.myorders_orderstate_to_be_shipped);
				default:
					return "";
			}
		}
		catch(Exception ex)
		{}
		return "";
	}
	public static String getAppVersionCode(Context context)
    {
        try
        {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return ""+info.versionCode;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public static String getAppVersionName(Context context)
    {
        try
        {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return ""+info.versionName;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public static String getDeviceId(Context context)
    {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
	public static boolean isValidPinCode(final EditText editText)
	{
		String pincode = editText.getText().toString().trim();
		if (pincode == null || pincode.length()==0) 
		{
	        return false;
	    } 
		else 
		{
			try
			{
				Integer.parseInt(pincode);
				return pincode.length()==6;
			}
			catch(NumberFormatException ex)
			{
				return false;
			}
	    }
	}
	public static boolean isValidMobileNumber(String mobileNumber)
	{
		if (mobileNumber == null || mobileNumber.length()==0) 
		{
	        return false;
	    } 
		else 
		{
	        return mobileNumber.length()==10 && android.util.Patterns.PHONE.matcher(mobileNumber).matches();
	    }
	}
	public static boolean isValidMobileNumber(final EditText editText)
	{
		final String mobileNumber = editText.getText().toString().trim();
		return isValidMobileNumber(mobileNumber);
	}
	public static boolean isValidEmailId(final EditText editText)
	{
		final String emailid = editText.getText().toString().trim();
		if (emailid == null || emailid.length()==0) 
		{
	        return false;
	    } 
		else 
		{
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailid).matches();
	    }
	}
	
	public static String getFormattedDate(String date)
	{
		if(date==null)
			return "";
//		2014-06-11 00:00:00.0
//		SimpleDateFormat formatterFROM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.Z");
//		SimpleDateFormat formatterTO = new SimpleDateFormat("dd MMM yyyy");
//		try
//		{
//			Date date1 = formatterFROM.parse(date);
//			String convertedDate = formatterFROM.format(date);
//			return convertedDate;
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
		return date;
	}
	public static void showSingleChoiceDialogWithCancelBtn(String title, Context context, final String[] options, final DialogsSingleChoiceCallback dialogsSingleChoiceCallback)
	{
		new AlertDialog.Builder(context)
				.setTitle(title)
				.setSingleChoiceItems(options, 0, null)
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						dialog.dismiss();
						int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
						dialogsSingleChoiceCallback.onClose(selectedPosition, options[selectedPosition]);
					}
				})
				.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				})
				.show();
	}
	public static void showSingleChoiceDialog(String title, Context context, final String[] options, final DialogsSingleChoiceCallback dialogsSingleChoiceCallback)
	{
		new AlertDialog.Builder(context)
		.setTitle(title)
        .setSingleChoiceItems(options, 0, null)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() 
        {
            public void onClick(DialogInterface dialog, int whichButton) 
            {
                dialog.dismiss();
                int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                dialogsSingleChoiceCallback.onClose(selectedPosition, options[selectedPosition]);
            }
        })
		.show();
	}
	public static File getFile(Context context, Bitmap bitmap, String targetFileName)
	{
		try
		{
			File f =new File(context.getCacheDir(), targetFileName);
			f.createNewFile();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100 , bos);
			byte[] bitmapdata = bos.toByteArray();
			ACLogger.log("Uploaded file size: "+bitmapdata.length);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapdata);
			return f;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	private String convertStreamToString(InputStream is) 
	{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	
	        String line = null;
	        try {
	                while ((line = reader.readLine()) != null) {
	                        sb.append(line);
	                }
	        } catch (IOException e) {
	                e.printStackTrace();
	        } finally {
	                try {
	                        is.close();
	                } catch (IOException e) {
	                        e.printStackTrace();
	                }
	        }
	        return sb.toString();
	}
	
	public static boolean validateQuantity(EditText editText, boolean isRequestFocus)
	{
		boolean successFlag = true;
		try
		{
			float val = Float.parseFloat(editText.getText().toString().trim());
			if(val > 0f)
			{
				if(matchPattern(editText.getText().toString(), "[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?(\\.[0-9][0-9]?)?"))
				{
					editText.setError(null);
				}
				else
				{
					successFlag=false;
				}
			}
			else
			{
				successFlag=false;
			}
		}
		catch(Exception ex)
		{
			successFlag=false;
		}
		return successFlag;
	}

	public static UIMessage getUIErrorMessage(Fragment context, ACError error, String eventMessage, String recordNotFoundMessage)
	{
		return getUIErrorMessage(context.getActivity(), error, eventMessage, recordNotFoundMessage);
	}
	public static UIMessage getUIErrorMessage(Context context, ACError error, String eventMessage, String recordNotFoundMessage)
	{
		UIMessage uiMessage=null;
		
		if(error==null)
		{
			return new UIMessage(UIMessageType.SERVER_ERROR, eventMessage);
		}
		else if(error.getErrorCodes()==ACErrorCodes.NETWORK_NOTAVAILABLE_ERROR)
		{
			uiMessage=new UIMessage(UIMessageType.NETWORK_NOT_AVAILABLE, context.getString(R.string.network_not_available));
		}
		else if(error.getErrorCodes()==ACErrorCodes.API_ERRORCODE_FORBIDDEN)
		{
			uiMessage=new UIMessage(UIMessageType.UNAUTHORIZE, context.getString(R.string.server_error_access_forbidden));
		}
		else if(error.getErrorCodes()==ACErrorCodes.API_ERRORCODE_DB || error.getErrorCodes()==ACErrorCodes.API_ERRORCODE_INTERNALSERVER || error.getErrorCodes()==ACErrorCodes.API_ERRORCODE_DATEFORMATERROR)
		{
			uiMessage=new UIMessage(UIMessageType.SERVER_ERROR, context.getString(R.string.server_error));
		}
		else if(error.getErrorCodes()==ACErrorCodes.API_ERRORCODE_NORECORDSFOUND)
		{
			if(recordNotFoundMessage!=null)
			{
				uiMessage=new UIMessage(UIMessageType.ERROR, recordNotFoundMessage);
			}
			else
			{
				uiMessage=new UIMessage(UIMessageType.ERROR, context.getString(R.string.records_not_found));
			}
		}
		else if(error.getErrorCodes()==ACErrorCodes.API_ERRORCODE_INPUTERROR)
		{
			uiMessage=new UIMessage(UIMessageType.ERROR, error.getMessage());
		}
		else if(error.getErrorCodes() == ACErrorCodes.API_ERRORCODE_EMAIL_ALREADY_EXIST_NEED_RELOGIN)
		{
			uiMessage=new UIMessage(UIMessageType.EMAIL_ALREADY_EXIST, error.getMessage());
		}
		else
		{
			uiMessage=new UIMessage(UIMessageType.SERVER_ERROR, eventMessage);
		}
		return uiMessage;
	}
	
	public static void hideKeyboard(Activity activity) 
	{
		if(activity!=null && activity.getCurrentFocus()!=null && activity.getCurrentFocus().getWindowToken()!=null)
		{
			InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

    public static void sendEmail(Context context, String emailId)
    {
        boolean isSuccess = false;
        try
        {
            if(emailId!=null && emailId.length()>0 && android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches())
            {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailId, null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                isSuccess=true;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        if(isSuccess)
        {
//            Toast.makeText(context, "Phone Number \""+phoneNumber+"\" is invalid.", Toast.LENGTH_SHORT).show();
        }
    }
	public static String getDefaultP2SCustomerCareNo()
	{
		return "18001032322";
	}
	public static void startCall(Context context, String phoneNumber)
	{

        boolean isSuccess=false;
		try
		{
            if(phoneNumber!=null && phoneNumber.length()>0)
            {
                phoneNumber = phoneNumber.replaceAll("-", "");
                phoneNumber = phoneNumber.replaceAll("\\(", "");
                phoneNumber = phoneNumber.replaceAll("\\)", "");
                if(!(phoneNumber.length() >0 && android.util.Patterns.PHONE.matcher(phoneNumber).matches()))
                {
					phoneNumber=getDefaultP2SCustomerCareNo();
                }
            }
			else
			{
				phoneNumber=getDefaultP2SCustomerCareNo();
			}
		}
		catch(Exception ex)
		{
			phoneNumber=getDefaultP2SCustomerCareNo();
		}

		if(phoneNumber.length() >0 && android.util.Patterns.PHONE.matcher(phoneNumber).matches())
		{
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+Uri.encode(phoneNumber.trim())));
			callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(callIntent);
			isSuccess=true;
		}

        if(!isSuccess)
        {
            Toast.makeText(context, "Phone Number \""+phoneNumber+"\" is invalid.", Toast.LENGTH_SHORT).show();
        }
	}
	
	////////////////////////////TEXT WATCHERS//////////////////////////
	public static void setQuantityWatcher(final EditText editText)
	{
		editText.addTextChangedListener(new TextWatcher() 
    	{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) 
			{}
			@Override
			public void afterTextChanged(Editable s) 
			{
				if(s.toString().trim().length()==0)
				{
					editText.setError(null);
					return;
				}
				
				if(matchPattern(s.toString(), "[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?[0-9]?(\\.[0-9][0-9]?)?"))
				{
					editText.setError(null);
				}
				else
				{
					editText.setError("Please enter valid quantity (########.##).");
				}
			}
		});
	}
	public static void setEmailWatcher(final EditText editText)
	{
		if(editText==null)
			return;
		
		editText.addTextChangedListener(new TextWatcher() 
    	{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) 
			{}
			@Override
			public void afterTextChanged(Editable s) 
			{
				if(s.toString().trim().length()==0)
				{
					editText.setError(null);
					return;
				}
				
				if(android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches())
				{
					editText.setError(null);
				}
				else
				{
					editText.setError(editText.getContext().getString(R.string.login_validationerror_emailid));
				}
			}
		});
	}
	private static boolean matchPattern(String str, String pattern)
	{
		if(str.matches(pattern))
		{
			return true;
		}
		return false;
	}
	public static void setMobileNumberWatcher(final EditText editText)
	{
		if(editText!=null)
			editText.setFilters(new InputFilter[]{ new InputFilterMinMax(10)});
	}
	public static void setCompanyNameWatcher(final EditText editText)
	{
		final Pattern COMPANY_NAME_PATTERN = Pattern.compile("([a-zA-Z0-9@\\(\\)\\.\\-_\\+876576]+)");


		if(editText==null)
			return;

		editText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{}
			@Override
			public void afterTextChanged(Editable s)
			{
				if(s.toString().trim().length()==0)
				{
					editText.setError(null);
					return;
				}

				if(COMPANY_NAME_PATTERN.matcher(s.toString()).matches())
				{
					editText.setError(null);
				}
				else
				{
					editText.setError(editText.getContext().getString(R.string.kam_search_validation_organization_invalidchars));
				}
			}
		});
	}
	public static void setSMEIdWatcher(final EditText editText)
	{
		final Pattern SME_ID_PATTERN = Pattern.compile("([a-zA-Z0-9]+)");


		if(editText==null)
			return;

		editText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{}
			@Override
			public void afterTextChanged(Editable s)
			{
				if(s.toString().trim().length()==0)
				{
					editText.setError(null);
					return;
				}

				if(SME_ID_PATTERN.matcher(s.toString()).matches())
				{
					editText.setError(null);
				}
				else
				{
					editText.setError(editText.getContext().getString(R.string.kam_search_validation_smeid_invalid));
				}
			}
		});

//		if(editText!=null)
//			editText.setFilters(new InputFilter[]{ new RegexInputFilter("([a-zA-Z0-9]+)")});
	}
	public static void setPhoneNumberWatcher(final EditText editText)
	{
		if(editText!=null)
			editText.setFilters(new InputFilter[]{ new InputFilterMinMax(13)});
	}
	public static void setPincodeWatcher(final EditText editText)
	{
		if(editText!=null)
			editText.setFilters(new InputFilter[]{ new InputFilterMinMax(6)});
	}
	
	public static <T extends Fragment> Fragment getFragmentObjectByClass(Context context, Class<T> fragementClass)
	{
		if(fragementClass.getName().equals(SMENewsDetailsFragment.class.getName()))
		{
			return new SMENewsDetailsFragment();
		}
		else if(fragementClass.getName().equals(ShipmentDetailsFragment.class.getName()))
		{
			return new ShipmentDetailsFragment();
		}
		else if(fragementClass.getName().equals(AboutUsFragment.class.getName()))
		{
			return new AboutUsFragment();
		}
		else if(fragementClass.getName().equals(SMEKhabarFragment.class.getName()))
		{
			return new SMEKhabarFragment();
		}
		else if(fragementClass.getName().equals(DealsFragment.class.getName()))
		{
			return new DealsFragment();
		}
		else if(fragementClass.getName().equals(BuyingRFQsFragment.class.getName()))
		{
			return new BuyingRFQsFragment();
		}
		else if(fragementClass.getName().equals(DeliveryAddressesListFragment.class.getName()))
		{
			return new DeliveryAddressesListFragment();
		}
		else if(fragementClass.getName().equals(ContactInfoEditFragment.class.getName()))
		{
			return new ContactInfoEditFragment();
		}
		else if(fragementClass.getName().equals(BuyingOrdersFragment.class.getName()))
		{
			return new BuyingOrdersFragment();
		}
		else if(fragementClass.getName().equals(AccountUpdatesFragment.class.getName()))
		{
			return new AccountUpdatesFragment();
		}
		else if(fragementClass.getName().equals(LoginSignupFormFragment.class.getName()))
		{
			return new LoginSignupFormFragment();
		}
		else if(fragementClass.getName().equals(LoginFragment.class.getName()))
		{
			return new LoginFragment();
		}
		else if(fragementClass.getName().equals(SignupFragment.class.getName()))
		{
			return new SignupFragment();
		}
		else if(fragementClass.getName().equals(NoInternetFragment.class.getName()))
		{
			return new NoInternetFragment();
		}
		else if(fragementClass.getName().equals(ServerErrorFragment.class.getName()))
		{
			return new ServerErrorFragment();
		}
		else if(fragementClass.getName().equals(HomeFragment.class.getName()))
		{
			return new HomeFragment();
		}
		else if(fragementClass.getName().equals(RateThisAppFragment.class.getName()))
		{
			return new RateThisAppFragment();
		}
		else if(fragementClass.getName().equals(TermsAndConditionsFragment.class.getName()))
		{
			return new TermsAndConditionsFragment();
		}
		else if(fragementClass.getName().equals(PrivacyPolicyFragment.class.getName()))
		{
			return new PrivacyPolicyFragment();
		}
		else if(fragementClass.getName().equals(AboutP2SMEFragment.class.getName()))
		{
			return new AboutP2SMEFragment();
		}
		else if(fragementClass.getName().equals(AddRFQFragment.class.getName()))
		{
			return new AddRFQFragment();
		}
		else if(fragementClass.getName().equals(DeliveryAddressesInsertFragment.class.getName()))
		{
			return new DeliveryAddressesInsertFragment();
		}
        else if(fragementClass.getName().equals(MarketPlaceHomeFragment.class.getName()))
        {
            return new MarketPlaceHomeFragment();
        }
        else if(fragementClass.getName().equals(KAMDetailsFragment.class.getName()))
        {
            return new KAMDetailsFragment();
        }
        else if(fragementClass.getName().equals(InterestedCustomerFragment.class.getName()))
        {
            return new InterestedCustomerFragment();
        }
		else if(fragementClass.getName().equals(WebBrowserFragment.class.getName()))
		{
			return new WebBrowserFragment();
		}
		else if(fragementClass.getName().equals(CustomerSelectionFragment.class.getName()))
		{
			return new CustomerSelectionFragment();
		}
		else if(fragementClass.getName().equals(CustomerSearchResultsFragment.class.getName()))
		{
			return new CustomerSearchResultsFragment();
		}
		return null;
	}
	
	public static String getCommaSeparatedNumber(String number)
	{
		if(number!=null && number.trim().length()>0)
		{
			try
			{
				Double doubleValOfNumber = Double.parseDouble(number.trim());	
				DecimalFormat decimalFormat = new DecimalFormat("##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,##,###.00");
//			    decimalFormat.setGroupingUsed(true);
//			    decimalFormat.setGroupingSize(2);
			    String formattedNumber = decimalFormat.format(doubleValOfNumber);
				
//				Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
//				System.out.println(format.format(new BigDecimal("100000000")));
				
				return formattedNumber;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return "";
	}

	public static void showChangePasswordDialog(final Context context,final String title, String prefilledEmail, final ForgotPasswordDialogCallback callback)
	{
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.change_password_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(promptsView);
		final EditText editTextOldPassword = (EditText) promptsView.findViewById(R.id.editTextOldPassword);
		final EditText editTextNewPassword = (EditText) promptsView.findViewById(R.id.editTextNewPassword);
		final EditText editTextConfirmPassword = (EditText) promptsView.findViewById(R.id.editTextConfirmPassword);
		TextView textView1 = (TextView) promptsView.findViewById(R.id.textView1);
		textView1.setText(title);
		alertDialogBuilder
				.setCancelable(false)
				.setNegativeButton(context.getString(R.string.label_cancel), null)
				.setPositiveButton(context.getString(R.string.label_continue), null);
		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				positiveButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (validateForgotPasswordFields(editTextOldPassword, editTextNewPassword, editTextConfirmPassword)) {
//                            Toast.makeText(ContainerActivity.this, "sadfasdf", Toast.LENGTH_SHORT).show();
							callback.onComplete(editTextOldPassword.getText().toString(), editTextNewPassword.getText().toString());
							alertDialog.cancel();
						}
					}
				});
				Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
				negativeButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						callback.onComplete(null, null);
						alertDialog.cancel();
					}
				});
			}

			private boolean validateForgotPasswordFields(EditText editTextOldPassword, EditText editTextNewPassword, EditText editTextConfirmPassword) {
				if (!(editTextOldPassword.getText().toString().length() > 0)) {
					editTextOldPassword.setError(context.getString(R.string.please_enter_old_password));
				} else if (!(editTextNewPassword.getText().toString().length() > 0)) {
					editTextNewPassword.setError(context.getString(R.string.please_enter_new_password));
				} else if (!(editTextConfirmPassword.getText().toString().length() > 0)) {
					editTextConfirmPassword.setError(context.getString(R.string.please_confirm_password));
				} else {
					if (!editTextNewPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
						editTextConfirmPassword.setError(context.getString(R.string.password_not_matched));
					} else if (editTextNewPassword.getText().toString().length() < 6) {
						editTextConfirmPassword.setError(context.getString(R.string.password_must_be_minimum_6_characters));
					}
//					else if(!editTextNewPassword.getText().toString().matches("[A-Za-z0-9]+"))
//					{
//						editTextNewPassword.setError(context.getString(R.string.password_must_be_alphanumeric_only));
//					}
					else {
						return true;
					}
				}
				return false;
			}
		});
		alertDialog.show();
	}

	public static void logoutApp(BaseAppCompatActivity baseActivity, boolean isOpenLoginScreen, final Bundle bundle)
	{
		baseActivity.isAppInBackground=false;
		MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());

		SharedPreferences prefs = myAccountApplication.getPrefs();
		SharedPreferences.Editor editor = prefs.edit();


		editor.remove(Constants.PREFERENCE_SELECTED_SKU);

		editor.remove(Constants.PREFERENCE_IS_KAM);

		editor.remove(Constants.PREFERENCE_FARMER_FULLNAME);
		editor.remove(Constants.PREFERENCE_FARMER_EMAIL);
		editor.remove(Constants.PREFERENCE_FARMER_MOBILE);

		editor.remove(Constants.PREFERENCE_KAM_FULLNAME);
		editor.remove(Constants.PREFERENCE_KAM_EMAIL_ID);
		editor.remove(Constants.PREFERENCE_KAM_MOBILE);

		editor.remove(Constants.PREFERENCE_CUSTOMER_COMPANYNAME);
		editor.remove(Constants.PREFERENCE_CUSTOMER_SMEID);
		editor.remove(Constants.PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP);
		editor.remove(Constants.PREFERENCE_CUSTOMER_EMAIL);
		editor.remove(Constants.PREFERENCE_CUSTOMER_FULLNAME);
		editor.remove(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER);

		editor.remove(Constants.PREFERENCE_AGENT_USERNAME);
		editor.remove(Constants.PREFERENCE_AGENT_FULLNAME);
		editor.remove(Constants.PREFERENCE_AGENT_EMAIL_ID);
		editor.remove(Constants.PREFERENCE_AGENT_MOBILE);
		editor.commit();
		NetworkUtils.deleteCookie(baseActivity);
		if(isOpenLoginScreen)
		{
			Intent intent=new Intent(baseActivity, LoginActivity.class);
			if(bundle!=null)
			{
				intent.putExtras(bundle);
			}
			intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_login);
			baseActivity.startActivity(intent);
		}

		Set<String> pnIdsSet = prefs.getStringSet("PUSH_IDS", null);
		if(pnIdsSet!=null)
		{
			NotificationManager nMgr = (NotificationManager) baseActivity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
			for(String pushId : pnIdsSet)
			{
				try
				{
					nMgr.cancel(Integer.parseInt(pushId));
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
	public static void saveActOnBehalfOfResponse(Context context, Organization_v3 organization)
	{
		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
		SharedPreferences.Editor editor = myAccountApplication.getPrefs().edit();
		if(organization!=null)
		{
			editor.putString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME,organization.getCompany_name());
			editor.putString(Constants.PREFERENCE_CUSTOMER_SMEID, organization.getSmeId());
			editor.putString(Constants.PREFERENCE_CUSTOMER_EMAIL,organization.getEmail());
			editor.putString(Constants.PREFERENCE_CUSTOMER_FULLNAME,organization.getContactPerson());
			editor.putString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER,organization.getPhone());
		}
		editor.commit();
	}
	public static void saveLoginResponseEmployeeInfo(Context context, Employee_v3 employee)
	{
		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
		SharedPreferences.Editor editor = myAccountApplication.getPrefs().edit();
		if(employee!=null)
		{
			editor.putBoolean(Constants.PREFERENCE_IS_KAM,true);

			editor.putString(Constants.PREFERENCE_AGENT_USERNAME, employee.getUserName());

			String agentFirstName = employee.getFirstName()!=null && employee.getFirstName().length()>0 ? employee.getFirstName() : "";
			String agentLastName = employee.getLastName()!= null && employee.getLastName().length()>0 ? employee.getLastName() : "";
			String agentFullName = agentFirstName + (agentLastName.length()>0 ? " "+agentLastName:"");

			editor.putString(Constants.PREFERENCE_AGENT_FULLNAME, agentFullName);
			editor.putString(Constants.PREFERENCE_AGENT_EMAIL_ID, employee.getEmail());
			editor.putString(Constants.PREFERENCE_AGENT_MOBILE, employee.getMobile());
		}
		else
		{
			editor.putBoolean(Constants.PREFERENCE_IS_KAM,false);
		}

		editor.commit();
	}
	public static void saveLoginResponseCustomerInfo(Context context, CustomerLogin_v3 customerLogin)
	{
		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
		SharedPreferences.Editor editor = myAccountApplication.getPrefs().edit();
		if(customerLogin!=null)
		{
			String farmerFirstName = customerLogin.getFarmer_first_name()!=null && customerLogin.getFarmer_first_name().length()>0 ? customerLogin.getFarmer_first_name() : "";
			String farmerLastName = customerLogin.getFarmer_last_name()!=null && customerLogin.getFarmer_last_name().length()>0 ? customerLogin.getFarmer_last_name() : "";
			String farmerFullName = farmerFirstName + (farmerLastName.length()>0 ? " "+farmerLastName:"");

			editor.putString(Constants.PREFERENCE_FARMER_FULLNAME, farmerFullName);
			editor.putString(Constants.PREFERENCE_FARMER_EMAIL,customerLogin.getFarmer_email());
			editor.putString(Constants.PREFERENCE_FARMER_MOBILE,customerLogin.getFarmer_mobile());

			String kamFirstName = customerLogin.getKam_first_name()!=null && customerLogin.getKam_first_name().length()>0 ? customerLogin.getKam_first_name() : "";
			String kamLastName = customerLogin.getKam_last_name()!=null && customerLogin.getKam_last_name().length()>0 ? customerLogin.getKam_last_name() : "";
			String kamFullName = kamFirstName + (kamLastName.length()>0 ? " "+kamLastName:"");

			editor.putString(Constants.PREFERENCE_KAM_FULLNAME,kamFullName);
			editor.putString(Constants.PREFERENCE_KAM_EMAIL_ID,customerLogin.getKam_email());
			editor.putString(Constants.PREFERENCE_KAM_MOBILE,customerLogin.getKam_mobile());

			editor.putString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME,customerLogin.getCompany_name());
			editor.putString(Constants.PREFERENCE_CUSTOMER_SMEID,customerLogin.getSmeId());
			editor.putBoolean(Constants.PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP, customerLogin.getIsCoRegInERP());
			editor.putString(Constants.PREFERENCE_CUSTOMER_EMAIL,customerLogin.getPrimary_email());

			String customerFirstName = customerLogin.getFirst_name()!=null && customerLogin.getFirst_name().length()>0 ? customerLogin.getFirst_name() : "";
			String customerLastName = customerLogin.getLast_name()!= null && customerLogin.getLast_name().length()>0 ? customerLogin.getLast_name() : "";
			String customerFullName = customerFirstName + (customerLastName.length()>0 ? " "+customerLastName:"");

			editor.putString(Constants.PREFERENCE_CUSTOMER_FULLNAME,customerFullName);
			editor.putString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER,customerLogin.getPrimary_phone());
		}
		editor.commit();
	}

//	public static void saveLoginResponse(Context context, CustomerLogin_v3 customerLogin, Employee_v3 employee)
//	{
//		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
//		SharedPreferences.Editor editor = myAccountApplication.getPrefs().edit();
//		if(customerLogin!=null)
//		{
//			String farmerFirstName = customerLogin.getFarmer_first_name()!=null && customerLogin.getFarmer_first_name().length()>0 ? customerLogin.getFarmer_first_name() : "";
//			String farmerLastName = customerLogin.getFarmer_last_name()!=null && customerLogin.getFarmer_last_name().length()>0 ? customerLogin.getFarmer_last_name() : "";
//			String farmerFullName = farmerFirstName + (farmerLastName.length()>0 ? " "+farmerLastName:"");
//
//			editor.putString(Constants.PREFERENCE_FARMER_FULLNAME, farmerFullName);
//			editor.putString(Constants.PREFERENCE_FARMER_EMAIL,customerLogin.getFarmer_email());
//			editor.putString(Constants.PREFERENCE_FARMER_MOBILE,customerLogin.getFarmer_mobile());
//
//			String kamFirstName = customerLogin.getKam_first_name()!=null && customerLogin.getKam_first_name().length()>0 ? customerLogin.getKam_first_name() : "";
//			String kamLastName = customerLogin.getKam_last_name()!=null && customerLogin.getKam_last_name().length()>0 ? customerLogin.getKam_last_name() : "";
//			String kamFullName = kamFirstName + (kamLastName.length()>0 ? " "+kamLastName:"");
//
//			editor.putString(Constants.PREFERENCE_KAM_FULLNAME,kamFullName);
//			editor.putString(Constants.PREFERENCE_KAM_EMAIL_ID,customerLogin.getKam_email());
//			editor.putString(Constants.PREFERENCE_KAM_MOBILE,customerLogin.getKam_mobile());
//
//			editor.putString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME,customerLogin.getCompany_name());
//			editor.putString(Constants.PREFERENCE_CUSTOMER_SMEID,customerLogin.getSmeId());
//			editor.putBoolean(Constants.PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP, customerLogin.getIsCoRegInERP());
//			editor.putString(Constants.PREFERENCE_CUSTOMER_EMAIL,customerLogin.getPrimary_email());
//
//			String customerFirstName = customerLogin.getFirst_name()!=null && customerLogin.getFirst_name().length()>0 ? customerLogin.getFirst_name() : "";
//			String customerLastName = customerLogin.getLast_name()!= null && customerLogin.getLast_name().length()>0 ? customerLogin.getLast_name() : "";
//			String customerFullName = customerFirstName + (customerLastName.length()>0 ? " "+customerLastName:"");
//
//			editor.putString(Constants.PREFERENCE_CUSTOMER_FULLNAME,customerFullName);
//			editor.putString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER,customerLogin.getPrimary_phone());
//		}
//
//		if(employee!=null)
//		{
//			editor.putBoolean(Constants.PREFERENCE_IS_KAM,true);
//
//			editor.putString(Constants.PREFERENCE_AGENT_USERNAME, employee.getUserName());
//
//			String agentFirstName = employee.getFirstName()!=null && employee.getFirstName().length()>0 ? employee.getFirstName() : "";
//			String agentLastName = employee.getLastName()!= null && employee.getLastName().length()>0 ? employee.getLastName() : "";
//			String agentFullName = agentFirstName + (agentLastName.length()>0 ? " "+agentLastName:"");
//
//			editor.putString(Constants.PREFERENCE_AGENT_FULLNAME, agentFullName);
//			editor.putString(Constants.PREFERENCE_AGENT_EMAIL_ID, employee.getEmail());
//			editor.putString(Constants.PREFERENCE_AGENT_MOBILE, employee.getMobile());
//		}
//		else
//		{
//			editor.putBoolean(Constants.PREFERENCE_IS_KAM,false);
//		}
//
//		editor.commit();
//	}

	public static void showEmailAlreadyExistDialog(final BaseAppCompatActivity baseActivity, final Bundle bundle)
	{
		new AlertDialog.Builder(baseActivity)
				.setMessage(baseActivity.getString(R.string.auth_dlg_title_email_already_exist))
				.setCancelable(false)
				.setPositiveButton(baseActivity.getString(R.string.label_login), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Utils.logoutApp(baseActivity, true, bundle);
//						containerActivity.doDrawerMenuSettings(null);
					}
				})
				.setNegativeButton(baseActivity.getString(R.string.label_cancel), null)
				.show();
	}

	public static void showSessionInvalidateDialog(final BaseAppCompatActivity baseActivity, final Bundle bundle)
	{
		new AlertDialog.Builder(baseActivity)
				.setMessage(baseActivity.getString(R.string.auth_dlg_title_session_expired_login_again))
				.setCancelable(false)
				.setPositiveButton(baseActivity.getString(R.string.label_login), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Utils.logoutApp(baseActivity, true, bundle);
//						baseActivity.doDrawerMenuSettings(null);
					}
				})
				.show();
	}

	public static boolean isTestDevice(Context context)
	{
		//[1] if power2sme account configures in device then it is test device
		AccountManager am = AccountManager.get(context);
		Account[] acc = am.getAccounts();
		for(Account account:acc)
		{
//			resultList.add(account.name);
			if(account.name.indexOf("power2sme.com")!=-1)
			{
				return true;
			}
		}
		return false;
	}
	public static int getCategoryDrawableResId(Context context, String category)
	{
		if(category.equalsIgnoreCase("Chemical"))
		{
			return R.drawable.chemicals;
		}
		else if(category.equalsIgnoreCase("Commodity Polymer"))
		{
			return R.drawable.polymers;
		}
		else if(category.equalsIgnoreCase("Steel"))
		{
			return R.drawable.steel;
		}
		else if(category.equalsIgnoreCase("Inks"))
		{
			return R.drawable.inks;
		}
		else if(category.equalsIgnoreCase("Paint"))
		{
			return R.drawable.paint;
		}
		else
		{
			return -1;
		}
	}

	public static TextDrawable getCategoryDrawable(Context context, String category)
	{
		String firstChar = String.valueOf(category.charAt(0));
		if(category.equals("Chemical"))
		{
			return TextDrawable.builder().buildRound(firstChar, context.getResources().getColor(R.color.deal_circular_color1));
		}
		else if(category.equals("Commodity Polymer"))
		{
			firstChar = "P";
			return TextDrawable.builder().buildRound(firstChar, context.getResources().getColor(R.color.deal_circular_color2));
		}
		else if(category.equals("Steel"))
		{
			return TextDrawable.builder().buildRound(firstChar, context.getResources().getColor(R.color.deal_circular_color3));
		}
		else
		{
			return TextDrawable.builder().buildRound(firstChar, context.getResources().getColor(R.color.deal_circular_color4));
		}
	}



//	public <T extends Fragment> void openFragment(
//
//			Class<T> fragmentClass,
//			Bundle bundle,
//			boolean isAddToBackStack,
//			boolean isCheckForNetwork,
//			boolean isClearBackStack
//
//	)
//	{
//		if(isCheckForNetwork && !NetworkUtils.isNetworkAvailable(this))
//		{
//			if(bundle==null)
//			{
//				bundle  = new Bundle();
//			}
//			bundle.putString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_NAME, fragmentClass.getName());
//			bundle.putBoolean(Constants.BUNDLE_KEY_IS_ADD_TO_BACKSTACK, isAddToBackStack);
//			bundle.putBoolean(Constants.BUNDLE_KEY_IS_CHECK_NETWORK, isCheckForNetwork);
//			bundle.putBoolean(Constants.BUNDLE_KEY_IS_CLEAR_BACKSTACK, isClearBackStack);
//			openFragment(NoInternetFragment.class, bundle, true, false, false);
//			return;
//		}
//
//		try
//		{
//			FragmentManager fragmentManager = getSupportFragmentManager();
//			Fragment fragment=null;
//
//			if(!(fragmentClass.getName().equals(BuyingOrdersFragment.class.getName())
//					|| fragmentClass.getName().equals(BuyingRFQsFragment.class.getName())))
//			{
//				fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
//			}
//
//			if(fragment==null)
//			{
//				fragment = Utils.getFragment(this, fragmentClass);
//			}
//			if(bundle!=null)
//			{
//				((SuperFragment)fragment).setFragmentDataBundle(bundle);
//			}
//			String str = fragment.isVisible()+"="+fragment.isAdded()+"="+fragment.isInLayout()+"="+fragment.isResumed();
////    		ACLogger.log(str);
//			if(fragment!=null && fragment.isVisible() && !((fragment instanceof BuyingRFQsFragment) || (fragment instanceof BuyingOrdersFragment)))
//			{
//				if( bundle != null )
//				{
//					if(fragment!=null && (fragment instanceof LoginSignupFormFragment))
//					{
//						LoginSignupFormFragment loginSignupFormFragment = (LoginSignupFormFragment)fragment;
//						Bundle b = loginSignupFormFragment.getFragmentDataBundle();
//						loginSignupFormFragment.setFragmentDataBundle(bundle);
//
//						LoginFragment.getInstance().setFragmentDataBundle(bundle);
//						SignupFragment.getInstance().setFragmentDataBundle(bundle);
//					}
//				}
//			}
//			else
//			{
//				if(isClearBackStack)
//				{
//					clearStack();
//				}
//
//				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//				fragmentTransaction.setCustomAnimations(R.drawable.slide_in_right, R.drawable.slide_out_left);
//				fragmentTransaction.replace(R.id.content_frame, fragment, fragmentClass.getName());
//				if(isAddToBackStack)
//					fragmentTransaction.addToBackStack(fragmentClass.getName());
//				fragmentTransaction.commitAllowingStateLoss();
//			}
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//	}


	public static void clearStack(AppCompatActivity appCompatActivity)
	{
		int count = appCompatActivity.getSupportFragmentManager().getBackStackEntryCount();
		while(count > 0)
		{
			appCompatActivity.getSupportFragmentManager().popBackStackImmediate();
			count--;
		}
	}

//	public <T extends Fragment> void setSubHeader(final AppCompatActivity appCompatActivity, Class<T> fragmentClass, View rootView)
//	{
//		boolean flag=false;
//		LinearLayout LinearLayout_subHeader = (LinearLayout) rootView.findViewById(R.id.LinearLayout_subHeader);
//		MyAccountApplication myAccountApplication = (MyAccountApplication)appCompatActivity.getApplicationContext();
//		if(myAccountApplication.getLoginStatus() != Constants.LoginStatus.NOT_LOGGED_IN)
//		{
//			if(myAccountApplication.isKAM())
//			{
//				View subHeaderView = LayoutInflater.from(appCompatActivity).inflate(R.layout.subheader_kam, null);
//
//				TextView TextView_CompanyName = (TextView) subHeaderView.findViewById(R.id.TextView_CompanyName);
//				TextView_CompanyName.setText(myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, ""));
//
//				TextView TextView_contactUs = (TextView) subHeaderView.findViewById(R.id.TextView_contactUs);
//				TextView_contactUs.setOnClickListener(new View.OnClickListener()
//				{
//					@Override
//					public void onClick(View v)
//					{
//						Utils.openFragment(appCompatActivity, CustomerSelectionFragment.class, null, true, true, false);
//					}
//				});
//				LinearLayout_subHeader.removeAllViews();
//				LinearLayout_subHeader.addView(subHeaderView);
//				flag=true;
//				LinearLayout_subHeader.setVisibility(LinearLayout.VISIBLE);
//			}
////			else
////			{
////				if(
////						fragmentClass.getName().equals(HomeFragment.class.getName())
////								|| fragmentClass.getName().equals(AccountUpdatesFragment.class.getName())
////								|| fragmentClass.getName().equals(DealsFragment.class.getName())
////								|| fragmentClass.getName().equals(BuyingRFQsFragment.class.getName())
////								|| fragmentClass.getName().equals(BuyingOrdersFragment.class.getName())
////						)
////
////				{
////					View subHeaderView = LayoutInflater.from(appCompatActivity).inflate(R.layout.subheader_contact_us, null);
////					Button Button_contactUs = (Button) subHeaderView.findViewById(R.id.Button_contactUs);
////					Button_contactUs.setText(appCompatActivity.getString(R.string.contact_us));
////					Button_contactUs.setOnClickListener(new View.OnClickListener() {
////						@Override
////						public void onClick(View v) {
////							Utils.openFragment(appCompatActivity, KAMDetailsFragment.class, null, true, true, false);
////						}
////					});
////					LinearLayout_subHeader.removeAllViews();
////					LinearLayout_subHeader.addView(subHeaderView);
////					flag=true;
////					LinearLayout_subHeader.setVisibility(LinearLayout.VISIBLE);
////				}
////				else if(fragmentClass.getName().equals(ContactInfoEditFragment.class.getName()))
////				{
////					View subHeaderView = LayoutInflater.from(appCompatActivity).inflate(R.layout.subheader_contact_us, null);
////					Button Button_contactUs = (Button) subHeaderView.findViewById(R.id.Button_contactUs);
////					Button_contactUs.setText(appCompatActivity.getString(R.string.change_password));
////					Button_contactUs.setOnClickListener(new View.OnClickListener() {
////						@Override
////						public void onClick(View v) {
////							Utils.showChangePasswordDialog(appCompatActivity, appCompatActivity.getString(R.string.chnage_password_label), "", new ForgotPasswordDialogCallback()
////							{
////								@Override
////								public void onComplete(String oldPassword, String newPassword)
////								{
////									if(oldPassword!=null && newPassword!=null)
////										iContainerActivityPresentor.changePassword(oldPassword, newPassword);
////								}
////							});
////						}
////					});
////					LinearLayout_subHeader.removeAllViews();
////					LinearLayout_subHeader.addView(subHeaderView);
////					flag=true;
////					LinearLayout_subHeader.setVisibility(LinearLayout.VISIBLE);
////				}
////				else
////				{
////					LinearLayout_subHeader.setVisibility(LinearLayout.GONE);
////				}
////			}
//		}
//		else
//		{
//			LinearLayout_subHeader.setVisibility(LinearLayout.GONE);
//		}
//
//		if(flag)
//		{
//			LinearLayout_subHeader.setVisibility(LinearLayout.VISIBLE);
//		}
//		else
//		{
//			LinearLayout_subHeader.setVisibility(LinearLayout.GONE);
//		}
//	}

	public static void openLoginScreen(AppCompatActivity appCompatActivity, Constants.LoginSources loginSources, Bundle bundle)
	{
		if(bundle==null)
		{
			bundle=new Bundle();
		}

		bundle.putString(Constants.BUNDLE_KEY_LOGIN_SOURCE, loginSources.toString());
		if(loginSources== Constants.LoginSources.DrawerMenu) {
//			Utils.openFragment(appCompatActivity, LoginSignupFormFragment.class, bundle, false, true, true);

			Intent intent=new Intent(appCompatActivity, LoginActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_login);
			intent.putExtras(bundle);
			appCompatActivity.startActivity(intent);

		} else {
//			Utils.openFragment(appCompatActivity, LoginSignupFormFragment.class, bundle, true, true, true);
			Intent intent=new Intent(appCompatActivity, LoginActivity.class);
			intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_login);
			intent.putExtras(bundle);
			appCompatActivity.startActivity(intent);
		}
	}
//	public static void openTargetScreen(BaseAppCompatActivity baseActivtiy, Bundle bundle)
//	{
//		MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivtiy.getApplicationContext());
//		if(bundle!=null)
//		{
//			String loginSource = bundle.getString(Constants.BUNDLE_KEY_LOGIN_SOURCE);
//			if(loginSource != null)
//			{
//				if(loginSource== Constants.LoginSources.DrawerMenu.toString())
//				{
//					Utils.openFragment(baseActivtiy, HomeFragment.class, null, false, true, true);
//				}
//				else if(loginSource== Constants.LoginSources.PREVIOUS_SCREEN.toString())
//				{
//					baseActivtiy.onBackPressed();
//				}
//				else if(loginSource== Constants.LoginSources.ADD_RFQ.toString())
//				{
//					Utils.openFragment(baseActivtiy, AddRFQFragment.class, bundle, true, true, true);
//				}
//				else if(loginSource== Constants.LoginSources.INTERESTED_CUSTOMER.toString())
//				{
//					Utils.openFragment(baseActivtiy, InterestedCustomerFragment.class, bundle, true, true, true);
//				}
//				else if(loginSource== Constants.LoginSources.MY_UPDATES.toString())
//				{
//					if(myAccountApplication.getLoginStatus()== Constants.LoginStatus.NOT_LOGGED_IN)
//					{
//						Utils.openLoginScreen(baseActivtiy, Constants.LoginSources.MY_UPDATES, new Bundle());
//					}
//					else
//					{
//						Utils.openFragment(baseActivtiy, AccountUpdatesFragment.class, null, true, true, true);
//					}
//				}
//				else if(loginSource== Constants.LoginSources.MY_ORDERS.toString())
//				{
//					if(myAccountApplication.getLoginStatus()== Constants.LoginStatus.NOT_LOGGED_IN)
//					{
//						Utils.openLoginScreen(baseActivtiy, Constants.LoginSources.MY_ORDERS, new Bundle());
//					}
//					else
//					{
//						Utils.openFragment(baseActivtiy, BuyingOrdersFragment.class, null, true, true, true);
//					}
//				}
//				else if(loginSource== Constants.LoginSources.CONTACT_INFO.toString())
//				{
//					if(myAccountApplication.getLoginStatus()!= Constants.LoginStatus.NOT_LOGGED_IN)
//					{
//						Utils.openFragment(baseActivtiy, ContactInfoEditFragment.class, null, true, true, true);
//					}
//					else
//					{
//						Utils.openLoginScreen(baseActivtiy, Constants.LoginSources.CONTACT_INFO, new Bundle());
//					}
//				}
//				else if(loginSource== Constants.LoginSources.DELIVERY_ADDRESS.toString())
//				{
//					if(myAccountApplication.getLoginStatus()== Constants.LoginStatus.NOT_LOGGED_IN)
//					{
//						Utils.openLoginScreen(baseActivtiy, Constants.LoginSources.DELIVERY_ADDRESS, new Bundle());
//					}
//					else
//					{
//						Utils.openFragment(baseActivtiy, DeliveryAddressesListFragment.class, null, true, true, true);
//					}
//				}
//				else if(loginSource== Constants.LoginSources.MY_RFQ.toString())
//				{
//					if(myAccountApplication.getLoginStatus()== Constants.LoginStatus.NOT_LOGGED_IN)
//					{
//						Utils.openLoginScreen(baseActivtiy, Constants.LoginSources.MY_RFQ, new Bundle());
//					}
//					else
//					{
//						Utils.openFragment(baseActivtiy, BuyingRFQsFragment.class, null, true, true, true);
//					}
//				}
//				else if(loginSource== Constants.LoginSources.TAKE_THIS_DEAL.toString())
//				{
//					Utils.openFragment(baseActivtiy, AddRFQFragment.class, bundle, true, true, false);
//				}
//				return;
//			}
//		}
//		Utils.openChildSectionFragment(baseActivtiy, HomeFragment.class, null, false, true, true, true);
//	}

	/**
	 * This method checck whether string is null
	 * if string is null it returns empty string instead of null else return string as it is
	 * created by Satish Shende
	 * @param str input string
	 * @return empty string or string itself
	 */
	public static String checkStringForNull(String str)	{
		return str==null?"":str.trim();
	}

	public static String encryptPassword(String plainPasswordText)
	{
		try
		{
			byte[] plainPasswordTextBytes = plainPasswordText.getBytes("UTF8");
			String encryptedPassword = Base64.encodeToString(plainPasswordTextBytes, Base64.DEFAULT);
			ACLogger.log("encryptedPassword : "+encryptedPassword);
			return encryptedPassword;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return "";
	}

	public static String decryptPassword(String encryptedPasswordText)
	{
		try
		{
			byte[] decryptedPasswordBytes = Base64.decode(encryptedPasswordText, Base64.DEFAULT);
			String decryptedPassword = new String(decryptedPasswordBytes, "UTF-8");
			ACLogger.log("decryptedPassword : "+decryptedPassword);
			return decryptedPassword;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	public static void openAppSettings(final Activity context)
	{
		if (context == null)
		{
			return;
		}
		final Intent i = new Intent();
		i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		i.setData(Uri.parse("package:" + context.getPackageName()));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		context.startActivity(i);
	}

	public static Snackbar getPermissionDisabledSnackBar(final Activity activity, String message)
	{
		final Snackbar permissionSnackbar=Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
		permissionSnackbar.setAction(activity.getString(R.string.permission_open_settings), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				permissionSnackbar.dismiss();
				Utils.openAppSettings(activity);
			}
		});
		View view= permissionSnackbar.getView();
		TextView snackbarText = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
		snackbarText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		permissionSnackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.colorAccent));
		return permissionSnackbar;
	}

	public static Snackbar getPermissionReTrySnackBar(final Activity activity, String message)
	{
		final Snackbar permissionSnackbar=Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
		View view= permissionSnackbar.getView();
		TextView snackbarText = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
		snackbarText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		return permissionSnackbar;
	}

	public static boolean isAppUpgraded(Activity activity)
	{
		try
		{
			SharedPreferences prefs = ((MyAccountApplication)activity.getApplication()).getPrefs();
			int oldAppVersion = prefs.getInt(Constants.PREFERENCE_APP_VERSION, 0);
			PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
			int versionCode = packageInfo.versionCode;
			if(oldAppVersion < versionCode)
			{
				prefs.edit().putInt(Constants.PREFERENCE_APP_VERSION, versionCode).commit();
				return true;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
}
