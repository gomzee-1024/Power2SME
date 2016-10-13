package com.power2sme.android.sections.deliveryaddress.insert;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.entities.State;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.deliveryaddress.list.DeliveryAddressesListFragment;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.InputFilterMinMax;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;

import java.util.List;

public class DeliveryAddressesInsertFragment extends SuperFragment implements IDeliveryAddressesInsertView, TextView.OnEditorActionListener {
	private static final String TAG="DeliveryAddressesEditFragment";
	View rootView;
	IDeliveryAddressesInsertPresentor iDeliveryAddressesInsertPresentor;

	EditText EditText_adress;
	EditText EditText_adress2;
	
	EditText EditText_postCode;
	Button Button_submit;
	
	List<State> stateList;
	State selectedState;

	DeliveryAddress deliveryAddress;

	public static DeliveryAddress srcDeliveryAddress = null;
	
	public static boolean isDeliveryAddressAdded=false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_DeliveryAddressesInsertFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_delivery_address_add;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
		DeliveryAddressesListFragment.mRefreshShippingAddress=true;
		AddRFQFragment.isSKUSelectionPerformed=true;
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.delivery_address_edit_fragment, container, false);
        initUIComponents();
		setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();
		iDeliveryAddressesInsertPresentor=new DeliveryAddressesInsertPresentorImpl(baseActivity, this);
		deliveryAddress=null;
		srcDeliveryAddress=null;
		if(getFragmentDataBundle()!=null && getFragmentDataBundle().containsKey(Constants.BUNDLE_KEY_SELECTED_DELIVERY_ADDRESS))
		{
			DeliveryAddress dlyAddress = getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_SELECTED_DELIVERY_ADDRESS);
			try
			{
				deliveryAddress = (DeliveryAddress)dlyAddress.getClone();
			}
			catch(Exception ex){}

			initUIData();
//			baseActivity.setDrawerMenuEnabled(false, R.drawable.p2s_logo, baseActivity.getString(R.string.screen_title_delivery_address_update));
		}

        return rootView;
    }

	private void initUIData()
	{
		if(deliveryAddress==null)
			return;

		EditText_adress.setText(deliveryAddress.getSmeAddress());
		EditText_adress2.setText(deliveryAddress.getSmeAddress2());
		EditText_postCode.setText(deliveryAddress.getSmePostCode());
		Button_submit.setText("UPDATE");
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) 
	{
        inflater.inflate(R.menu.delivery_address_edit_section_actionbar_top_menu, menu);
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
			public void onClick(View v) {
				submitData();
			}
		});
	}
	private void submitData(){
		if (isFeildsValidated()) {
			Button_submit.setEnabled(false);
			Button_submit.setClickable(false);

			if (deliveryAddress != null)
			{
				if (isUpdateAllowed())
				{
					DeliveryAddress requestAddressDto=new DeliveryAddress(deliveryAddress);
					requestAddressDto.setSmeAddress(EditText_adress.getText().toString());
					requestAddressDto.setSmeAddress2(EditText_adress2.getText().toString());
					requestAddressDto.setSmePostCode(EditText_postCode.getText().toString());
					iDeliveryAddressesInsertPresentor.updateDeliveryAddress(requestAddressDto);
				}
				else
				{
					UIMessageUtility.displayUIMessage(baseActivity, new UIMessage(UIMessageType.SUCCESS, getString(R.string.update_delivery_address_success)));
					baseActivity.onBackPressed();
				}
			}
			else
			{
				srcDeliveryAddress = new DeliveryAddress();
				srcDeliveryAddress.setCode(" ");
				MyAccountApplication myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
				SharedPreferences prefs = myAccountApplication.getPrefs();
				String smeId = prefs.getString(Constants.PREFERENCE_CUSTOMER_SMEID, "");
				srcDeliveryAddress.setCustomerNumber(smeId);
				srcDeliveryAddress.setSmeAddress(EditText_adress.getText().toString());
				srcDeliveryAddress.setSmeAddress2(EditText_adress2.getText().toString());
				srcDeliveryAddress.setSmePostCode(EditText_postCode.getText().toString());
				iDeliveryAddressesInsertPresentor.insertDeliveryAddress(srcDeliveryAddress);
			}
		}
	}

	private boolean isUpdateAllowed()
	{
		if(deliveryAddress.getSmeAddress().equals(EditText_adress.getText().toString())
				&& deliveryAddress.getSmeAddress2().equals(EditText_adress2.getText().toString())
				&& deliveryAddress.getSmePostCode().equals(EditText_postCode.getText().toString()))
		{
			return false;
		}
		return true;
	}

	private void initUIComponents()
	{
		EditText_adress = (EditText)rootView.findViewById(R.id.EditText_adress);
		EditText_adress2 = (EditText)rootView.findViewById(R.id.EditText_adress2);
		EditText_postCode = (EditText)rootView.findViewById(R.id.EditText_postCode);
		Utils.setPincodeWatcher(EditText_postCode);
		EditText_postCode.setFilters(new InputFilter[]{ new InputFilterMinMax(6)});
		EditText_postCode.setOnEditorActionListener(this);
		Button_submit = (Button)rootView.findViewById(R.id.Button_submit);
		Button_submit.setText(R.string.addshippingaddress_button_save);
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
		if(!showErrorMessageFragment(uiMessage.getUiMessageType(), DeliveryAddressesInsertFragment.class, null))
		{
			if(uiMessage.getUiMessageType() == UIMessageType.SUCCESS && (flag == Constants.FLAG_INSERT_DELIVERY_ADDRESS || flag == Constants.FLAG_UPDATE_DELIVERY_ADDRESS))
			{
//				if(DeliveryAddressesInsertFragment.isDeliveryAddressAdded)
//				{
//					DeliveryAddressesInsertFragment.selectedDeliveryAddress=
//				}
				baseActivity.onBackPressed();
			}
			EditText_postCode.requestFocus();
			UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
		}
		Button_submit.setEnabled(true);
		Button_submit.setClickable(true);
	}
	
	private boolean isFeildsValidated()
	{
		boolean isSuccess=true;

		if(EditText_adress.getText().toString().length()>0)
		{
			EditText_adress.setError(null);
		}
		else
		{
			EditText_adress.setError(baseActivity.getString(R.string.addshippingaddress_validationerror_addressline1));
			isSuccess=false;
		}

		if(Utils.isValidPinCode(EditText_postCode))
		{
			EditText_postCode.setError(null);
		}
		else
		{
			EditText_postCode.setError(baseActivity.getString(R.string.addshippingaddress_validationerror_pincode));
			isSuccess=false;
		}
		return isSuccess;
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
