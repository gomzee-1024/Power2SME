package com.power2sme.android.sections.myrfqs.add;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power2sme.android.ContainerActivity;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.conf.Constants.LoginSources;
import com.power2sme.android.conf.Constants.LoginStatus;
import com.power2sme.android.dtos.request.RequestAddCampaignDto;
import com.power2sme.android.entities.DealItemPrice;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.entities.LeadSource;
import com.power2sme.android.entities.SalesOrder;
import com.power2sme.android.entities.SalesOrderItem;
import com.power2sme.android.entities.UnitOfMeasure;
import com.power2sme.android.entities.v3.Deal_v3;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.entities.v3.OpportunityLine_v3;
import com.power2sme.android.entities.v3.Opportunity_v3;
import com.power2sme.android.entities.v3.Organization_v3;
import com.power2sme.android.entities.v3.RFQLineItem_v3;
import com.power2sme.android.entities.v3.ReOrder_v3;
import com.power2sme.android.entities.v3.SKU_v3;
import com.power2sme.android.entities.v3.TaxationPreference_v3;
import com.power2sme.android.entities.v3.Urgency_v3;
import com.power2sme.android.entities.v3.Wishlist_v3;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.deals.postorder.DealsPostOrderFragmentPresentorImpl;
import com.power2sme.android.sections.deals.postorder.IDealsPostOrderFragmentPresentor;
import com.power2sme.android.sections.deals.postorder.IDealsPostOrderView;
import com.power2sme.android.sections.deliveryaddress.insert.DeliveryAddressesInsertFragment;
import com.power2sme.android.sections.deliveryaddress.list.DeliveryAddressListAdapter;
import com.power2sme.android.sections.deliveryaddress.list.DeliveryAddressesListPresentorImpl;
import com.power2sme.android.sections.deliveryaddress.list.IDeliveryAddressesListPresentor;
import com.power2sme.android.sections.deliveryaddress.list.IDeliveryAddressesListView;
import com.power2sme.android.sections.home.HomeFragment;
import com.power2sme.android.sections.login.LoginType;
import com.power2sme.android.sections.myrfqs.add.typeahead.SuggestionAdapter;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.customviews.BetterSpinner.OnBetterSpinnerItemClickedListener;
import com.power2sme.android.utilities.customviews.EmailAutoCompleteAdapter;
import com.power2sme.android.utilities.customviews.InputFilterMinMax;
import com.power2sme.android.utilities.customviews.SpinnerHintAdapter;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.media.MediaHelper;
import com.power2sme.android.utilities.styels.StyleTypes;
import com.power2sme.android.utilities.styels.StylesManager;

import java.util.ArrayList;
import java.util.List;


public class AddRFQFragment extends SuperFragment implements IAddRFQView, IDealsPostOrderView, IDeliveryAddressesListView
{
	View rootView;
	IAddRFQPresentor iAddRFQPresentor;
	IDeliveryAddressesListPresentor iDeliveryAddressesListPresentor;
	IDealsPostOrderFragmentPresentor iDealsPostOrderFragmentPresentor;
	Deal_v3 selectedDeal;
	SalesOrder selectedSalesOrder;
	NewRFQ_v3 newRFQ;
	TextView TextView_contactInfoTabLabel;
	TextView TextView_itemDetailsTabLabel;
	TextView TextView_deliveryDetailsTabLabel;
	TextView TextView_contactTabCircle;
	TextView TextView_itemsTabCircle;
	TextView TextView_deliveryTabCircle;
	LinearLayout LinearLayout_state1spaceHolder;
	LinearLayout LinearLayout_state1pointerParent;
	ImageView ImageView_state1pointer;
	LinearLayout LinearLayout_state2spaceHolder;
	LinearLayout LinearLayout_state2pointerParent;
	ImageView ImageView_state2pointer;
	LinearLayout LinearLayout_state3spaceHolder;
	LinearLayout LinearLayout_state3pointerParent;
	ImageView ImageView_state3pointer;
	TableLayout TableLayout_state1Parent;
	TableLayout TableLayout_state2Parent;
	TableLayout TableLayout_state3Parent;
	private int currentTabPosition = 0;
	ViewFlipper view_flipper;
	Button Button_previous;
	Button Button_next;


	//Contact
	TextInputLayout TextInputLayout_companyName;
	TextInputLayout TextInputLayout_fullName;
	TextInputLayout TextInputLayout_email;
	TextInputLayout TextInputLayout_mobileNumber;

	SharedPreferences prefs;
	EditText EditText_companyName;
	EditText EditText_fullName;
	EditText EditText_mobileNumber;
	AutoCompleteTextView EditText_email;

	//add items
	LinearLayout LinearLayout_rfqItemsParent;
	ImageButton ImageButton_addMoreRFQItems;
	ScrollView rfqItemsParentScrollView;

	//delivery address
	LinearLayout LL1;
	LinearLayout LL2;
//	ListView ListView_deliveryAddress;
	Button Button_addDeliveryAddress;
	List<DeliveryAddress> deliveryAddressList;
	private DeliveryAddress selectedDeliveryAddress=null;
//	DeliveryAddressListAdapter deliveryAddressListAdapter;
	Button Button_submit;
//	TextView TextView_selectDeliveryAddressMessage;
	Button Button_selectDeliveryAddress;
	RadioGroup paymentTermRadioGroup;
	TextInputLayout TextInputLayout_paymentTermDays;
	EditText EditText_paymentTermDays;



	TextInputLayout TextInputLayout_taxation;
	TextInputLayout TextInputLayout_urgency;
	TextInputLayout TextInputLayout_deliveryAddressLine1;
	TextInputLayout TextInputLayout_deliveryAddressLine2;
	TextInputLayout TextInputLayout_deliveryPostCode;

	EditText EditText_deliveryAdress;
	EditText EditText_deliveryAdress2;
	EditText EditText_deliveryPostCode;
	BetterSpinner Spinner_taxation;
	BetterSpinner Spinner_urgency;
	ProgressBar ProgressBar_urgencyLoader;
	ProgressBar ProgressBar_taxationLoader;
	CheckBox CheckBox_isArrangeFreightByYou;
	CheckBox CheckBox_isFormC;

	private boolean isFirstItemAlreadyAdded = false;
	MyAccountApplication myAccountApplication;
	NewRFQTypes newRFQTypes=NewRFQTypes.iRFQ;//default is iRFQ
    String selectedDealRfqNo;

	Constants.LoginStatus userLoginStatus;
	private LayoutType layoutType;

	private boolean isCheckSubCatForEmpty=false;
	private boolean isInitCategoryPerformed=false;
	public static boolean isSKUSelectionPerformed=false;

	//private String selectedCategory;
//	private String selectedSubCategory;



	private enum LayoutType
	{
		LOGGEDIN_LAYOUT,NONLOGGEDIN_LAYOUT;
	}

    public interface OnUploadPOClickListener
    {
        void onUploadPOClick(String rfqNo);
    }

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_AddRFQFragment));
	}

	@Override
	public int getScreenTitleResId() {
		return R.string.screen_title_rfq_add;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//    	baseActivity=(ContainerActivity)getActivity();
        iAddRFQPresentor=new AddRFQPresentorImpl(baseActivity, this);
        iDeliveryAddressesListPresentor=new DeliveryAddressesListPresentorImpl(baseActivity, this);
        iDealsPostOrderFragmentPresentor=new DealsPostOrderFragmentPresentorImpl(baseActivity, this);

    	if(getFragmentDataBundle()!=null && getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL)!=null)
    	{
			newRFQTypes = NewRFQTypes.DEALS;
			setScreenTitle(R.string.screen_title_deals_post_order);

    	}
    	else if(getFragmentDataBundle()!=null && getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_REORDER_ITEM)!=null)
    	{
    		newRFQTypes = NewRFQTypes.REORDER;
			setScreenTitle(R.string.screen_title_rfq_add);
    	}
    	else if(getFragmentDataBundle()!=null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_DEAL_ID)!=null)
    	{
    		newRFQTypes = NewRFQTypes.DEALS;
			setScreenTitle(R.string.screen_title_deals_post_order);
    		String dealId = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_DEAL_ID);
    		iDealsPostOrderFragmentPresentor.getDealById(dealId);
    	}
    	else
    	{
    		newRFQTypes = NewRFQTypes.iRFQ;
			setScreenTitle(R.string.screen_title_rfq_add);
    	}

    	myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
		userLoginStatus = myAccountApplication.getLoginStatus();

    	if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
    	{
			layoutType=LayoutType.LOGGEDIN_LAYOUT;
    		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.add_rfq_loggedin_state_fragment, container, false);
    	}
    	else
    	{
			layoutType=LayoutType.NONLOGGEDIN_LAYOUT;
    		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.add_rfq_not_loggedin_state_fragment, container, false);
    	}

        initUIComponents();
		setupAdaptersAndListeners();
		setHasOptionsMenu(true);
		applyActionBarNavigationListSettings();

		prefs = myAccountApplication.getPrefs();

		initContactScreen();
		initItemsScreen();

		if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
		{
			initDeliveryAddressScreen(true, false);
		}
		else
		{
			initDeliveryAddressScreen(false, false);
		}

		if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
		{
			EditText_companyName.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, ""));
			EditText_fullName.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_FULLNAME, ""));
			EditText_mobileNumber.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER, ""));
			EditText_email.setText(prefs.getString(Constants.PREFERENCE_CUSTOMER_EMAIL, ""));
		}

		openRFQSection(RFQSection.SECTION_FIRST_ON_LAUNCH, 0, false);

		return rootView;
    }

	enum RFQSection
	{
		SECTION_FIRST_ON_LAUNCH,
		SECTION_CONTACT,
		SECTION_ITEMS,
		SECTION_DELIVERY,
		SECTION_BASEDON_POSITION
	}

	private void openRFQSection(RFQSection rfqSection, int position, boolean isReporting)
	{
		if(layoutType==LayoutType.LOGGEDIN_LAYOUT)
		{
			switch(rfqSection)
			{
				case SECTION_FIRST_ON_LAUNCH:
				{
					//validate contact if not valid contact then show contact
					// else open item section
					openRFQSection(RFQSection.SECTION_ITEMS, position, isReporting);
					break;
				}
				case SECTION_BASEDON_POSITION:
				{
					switch(position)
					{
						case 0:
						{
							openRFQSection(RFQSection.SECTION_CONTACT, position, isReporting);
							break;
						}
						case 1:
						{
							openRFQSection(RFQSection.SECTION_ITEMS, position, isReporting);
							break;
						}
						case 2:
						{
							openRFQSection(RFQSection.SECTION_DELIVERY, position, isReporting);
							break;
						}
					};
					break;
				}
				default:
				{
					switch(rfqSection)
					{
						case SECTION_CONTACT:
						{
							//show contact
							boolean isContactValid = validateContactScreen(isReporting);
							openContactsTab(isContactValid,true,true);
							break;
						}
						case SECTION_ITEMS:
						{
							//validate contact if not valid contact then show contact
							//open items
							boolean isContactValid = validateContactScreen(true);
							if(isContactValid)
							{
								//open items
								boolean isItemsValid = validateItemsScreen(isReporting);
								openItemsTab(true,isItemsValid,true);

							}
							else
							{
								//open contact
								openContactsTab(isContactValid,true,true);
							}
							break;
						}
						case SECTION_DELIVERY:
						{
							//validate contact if not valid contact then show contact
							//validate items if not valid then open items
							//show delivery
							boolean isContactValid = validateContactScreen(true);
							if(isContactValid)
							{
								boolean isItemsValid = validateItemsScreen(true);
								if(isItemsValid)
								{
									//show delivery
									boolean isDeliveryValid = validateDeliveryScreen(isReporting);
									openDeliveryTab(isContactValid,isItemsValid,isDeliveryValid);
								}
								else
								{
									//show items
									openItemsTab(true,isItemsValid,true);
								}
							}
							else
							{
								//open contact
								openContactsTab(isContactValid,true,true);
							}
							break;
						}
					};
				}
			};
		}
		else
		{
			switch(rfqSection)
			{
				case SECTION_FIRST_ON_LAUNCH:
				{
					//open item section
					openRFQSection(RFQSection.SECTION_ITEMS, position, isReporting);
					break;
				}
				case SECTION_BASEDON_POSITION:
				{
					switch(position)
					{
						case 0:
						{
							openRFQSection(RFQSection.SECTION_ITEMS, position, isReporting);
							break;
						}
						case 1:
						{
							openRFQSection(RFQSection.SECTION_DELIVERY, position, isReporting);
							break;
						}
						case 2:
						{
							openRFQSection(RFQSection.SECTION_CONTACT, position, isReporting);
							break;
						}
					};
					break;
				}
				default:
				{
					switch(rfqSection)
					{
						case SECTION_CONTACT:
						{
							//validate items if not valid then open items
							//validate delivery if not valid then open delivery
							//show contact
							boolean isItemsValid = validateItemsScreen(true);
							if(isItemsValid)
							{
								boolean isDeliveryValid = validateDeliveryScreen(true);
								if(isDeliveryValid)
								{
									//show contact
									boolean isContactValid = validateContactScreen(isReporting);
									openContactsTab(isContactValid,true ,true);
								}
								else
								{
									//show delivery
									openDeliveryTab(true ,true ,isDeliveryValid);
								}
							}
							else
							{
								//show items
								openItemsTab(true,isItemsValid,true);
							}

							break;
						}
						case SECTION_ITEMS:
						{
							//show items
							boolean isItemsValid = validateItemsScreen(isReporting);
							openItemsTab(true,isItemsValid,true);
							break;
						}
						case SECTION_DELIVERY:
						{
							//validate items if not valid then open items
							//show delivery
							boolean isItemsValid = validateItemsScreen(true);
							if(isItemsValid)
							{
								//show delivery
								boolean isDeliveryValid = validateDeliveryScreen(isReporting);
								openDeliveryTab(true,true,isDeliveryValid);
							}
							else
							{
								//show items
								openItemsTab(true,isItemsValid,true);
							}
							break;
						}
					};
				}
			};
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();
		initUIDataFromSavedViewState();
		setFragmentDataBundle(null);
	}

	@Override
	public void onStop()
	{
		if(getFragmentDataBundle()!=null && getFragmentDataBundle().getBoolean("SaveDataFlag", false))
		{
			onSaveViewState();
			getFragmentDataBundle().putBoolean("SaveDataFlag", false);
		}
		super.onStop();
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
		TableLayout_state1Parent.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentTabPosition = 0;
				openRFQSection(RFQSection.SECTION_BASEDON_POSITION, currentTabPosition, false);
			}
		});

		TableLayout_state2Parent.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				currentTabPosition = 1;
				openRFQSection(RFQSection.SECTION_BASEDON_POSITION, currentTabPosition, false);
			}
		});

		TableLayout_state3Parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentTabPosition = 2;
				openRFQSection(RFQSection.SECTION_BASEDON_POSITION, currentTabPosition, false);
			}
		});

		Button_previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentTabPosition >= 1) {
					Utils.hideKeyboard(getActivity());
					currentTabPosition = currentTabPosition - 1;
					openRFQSection(RFQSection.SECTION_BASEDON_POSITION, currentTabPosition, false);
				}
			}
		});

		Button_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (currentTabPosition <= 1) {
					Utils.hideKeyboard(getActivity());
					currentTabPosition = currentTabPosition + 1;
					openRFQSection(RFQSection.SECTION_BASEDON_POSITION, currentTabPosition, false);
				}
			}
		});
	}

	private void initUIComponents()
	{
		rfqItemsParentScrollView = (ScrollView)rootView.findViewById(R.id.rfqItemsParentScrollView);
		LinearLayout_state1spaceHolder=(LinearLayout)rootView.findViewById(R.id.LinearLayout_state1spaceHolder);
		LinearLayout_state1pointerParent=(LinearLayout)rootView.findViewById(R.id.LinearLayout_state1pointerParent);
		ImageView_state1pointer=(ImageView)rootView.findViewById(R.id.ImageView_state1pointer);
		LinearLayout_state2spaceHolder=(LinearLayout)rootView.findViewById(R.id.LinearLayout_state2spaceHolder);
		LinearLayout_state2pointerParent=(LinearLayout)rootView.findViewById(R.id.LinearLayout_state2pointerParent);
		ImageView_state2pointer=(ImageView)rootView.findViewById(R.id.ImageView_state2pointer);
		LinearLayout_state3spaceHolder=(LinearLayout)rootView.findViewById(R.id.LinearLayout_state3spaceHolder);
		LinearLayout_state3pointerParent=(LinearLayout)rootView.findViewById(R.id.LinearLayout_state3pointerParent);
		ImageView_state3pointer=(ImageView)rootView.findViewById(R.id.ImageView_state3pointer);

		TextView_contactTabCircle=(TextView)rootView.findViewById(R.id.TextView_contactTabCircle);
		TextView_itemsTabCircle=(TextView)rootView.findViewById(R.id.TextView_itemsTabCircle);
		TextView_deliveryTabCircle=(TextView)rootView.findViewById(R.id.TextView_deliveryTabCircle);

		TableLayout_state1Parent=(TableLayout)rootView.findViewById(R.id.TableLayout_state1Parent);
		TableLayout_state2Parent=(TableLayout)rootView.findViewById(R.id.TableLayout_state2Parent);
		TableLayout_state3Parent=(TableLayout)rootView.findViewById(R.id.TableLayout_state3Parent);

		view_flipper =(ViewFlipper)rootView.findViewById(R.id.view_flipper);

		Button_previous=(Button)rootView.findViewById(R.id.Button_previous);
		Button_next = (Button)rootView.findViewById(R.id.Button_next);
		Button_submit = (Button)rootView.findViewById(R.id.Button_submit);

		TextView_contactInfoTabLabel = (TextView)rootView.findViewById(R.id.TextView_contactInfoTabLabel);
		TextView_itemDetailsTabLabel = (TextView)rootView.findViewById(R.id.TextView_itemDetailsTabLabel);
		TextView_deliveryDetailsTabLabel = (TextView)rootView.findViewById(R.id.TextView_deliveryDetailsTabLabel);
		TextView_contactTabCircle = (TextView)rootView.findViewById(R.id.TextView_contactTabCircle);
		TextView_itemsTabCircle = (TextView)rootView.findViewById(R.id.TextView_itemsTabCircle);
		TextView_deliveryTabCircle = (TextView)rootView.findViewById(R.id.TextView_deliveryTabCircle);
	}

	private void initContactScreen()
	{
		TextInputLayout_companyName=(TextInputLayout)rootView.findViewById(R.id.TextInputLayout_companyName);
		TextInputLayout_fullName=(TextInputLayout)rootView.findViewById(R.id.TextInputLayout_fullName);
		TextInputLayout_email=(TextInputLayout)rootView.findViewById(R.id.TextInputLayout_email);
		TextInputLayout_mobileNumber=(TextInputLayout)rootView.findViewById(R.id.TextInputLayout_mobileNumber);

		//contact details
		EditText_companyName = (EditText)rootView.findViewById(R.id.EditText_companyName);
		EditText_fullName = (EditText)rootView.findViewById(R.id.EditText_fullName);
		EditText_mobileNumber = (EditText)rootView.findViewById(R.id.EditText_mobileNumber);
		Utils.setMobileNumberWatcher(EditText_mobileNumber);
		EditText_mobileNumber.setFilters(new InputFilter[]{ new InputFilterMinMax(10)});
		EditText_companyName.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
				{
					validateContact_MobileNumber(true, false);
				}
			}
		});
		EditText_email = (AutoCompleteTextView)rootView.findViewById(R.id.EditText_email);
//		Utils.setEmailWatcher(EditText_email);

		if(userLoginStatus == LoginStatus.SMEID_WITH_ERPREGISTERATION)
		{
			EditText_companyName.setEnabled(false);
			EditText_companyName.setTextColor(getResources().getColor(R.color.textfield_grayedout_color));
			EditText_companyName.setClickable(false);
		}
		else
		{
			EditText_companyName.setEnabled(true);
			EditText_companyName.setClickable(true);
			EditText_companyName.setTextColor(getResources().getColor(R.color.black));
			EditText_companyName.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus)
					{
						validateContact_CompanyName(true, false);
					}
				}
			});
		}

		if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
		{
			EditText_email.setEnabled(false);
			EditText_email.setFocusable(false);
			EditText_email.setTextColor(getResources().getColor(R.color.textfield_grayedout_color));
		}
		else
		{
			EditText_email.setEnabled(true);
			EditText_email.setFocusable(true);
			EditText_email.setTextColor(getResources().getColor(R.color.black));
			addEmailSuggesionAdapter(true);

			EditText_email.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus)
					{
						validateContact_EmailId(true, false);
					}
				}
			});
		}
	}

	private boolean validateContact_CompanyName(boolean isReporting, boolean isRequestFocus)
	{
		if(EditText_companyName.getText().toString().length()>0)
		{
			TextInputLayout_companyName.setError(null);
			TextInputLayout_companyName.setErrorEnabled(false);
			return true;
		}
		else
		{
			if(isReporting)
			{
				TextInputLayout_companyName.setError(baseActivity.getString(R.string.addrfq_validationerror_companyname));
				TextInputLayout_companyName.setErrorEnabled(true);

				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							EditText_companyName.requestFocus();
						}
					});
				}

			}
			return false;
		}
	}

	private boolean validateContact_EmailId(boolean isReporting, boolean isRequestFocus)
	{
		if(Utils.isValidEmailId(EditText_email))
		{
			TextInputLayout_email.setError(null);
			TextInputLayout_email.setErrorEnabled(false);
			EditText_email.setError(null);
			return true;
		}
		else
		{
			if(isReporting)
			{
				TextInputLayout_email.setError(baseActivity.getString(R.string.addrfq_validationerror_emailid));
				TextInputLayout_email.setErrorEnabled(true);
				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							EditText_email.requestFocus();
						}
					});
				}

			}
			return false;
		}
	}

	private boolean validateContact_MobileNumber(boolean isReporting, boolean isRequestFocus)
	{
		if(Utils.isValidMobileNumber(EditText_mobileNumber))
		{
			TextInputLayout_mobileNumber.setError(null);
			TextInputLayout_mobileNumber.setErrorEnabled(false);
			return true;
		}
		else
		{
			if(isReporting)
			{
				TextInputLayout_mobileNumber.setError(baseActivity.getString(R.string.addrfq_validationerror_mobilenumber));
				TextInputLayout_mobileNumber.setErrorEnabled(true);
				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							EditText_mobileNumber.requestFocus();
						}
					});
				}
			}
			return false;
		}
	}

	private boolean validateContactScreen(boolean isReporting)
	{
		boolean flag=true;
		boolean successflag=true;

		flag = validateContact_CompanyName(isReporting, (isReporting && flag));
		successflag=successflag==true?flag:false;

		flag = validateContact_EmailId(isReporting, (isReporting && flag));
		successflag=successflag==true?flag:false;

		flag = validateContact_MobileNumber(isReporting, (isReporting && flag));
		successflag=successflag==true?flag:false;

		return successflag;
	}

	private void initItemsScreen()
	{
		//item details
		LinearLayout_rfqItemsParent=(LinearLayout)rootView.findViewById(R.id.LinearLayout_rfqItemsParent);
		ImageButton_addMoreRFQItems=(ImageButton)rootView.findViewById(R.id.ImageButton_addMoreRFQItems);

		if(newRFQTypes == NewRFQTypes.DEALS)
		{
			selectedDeal = getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL);
			showDeal(selectedDeal);
		}
		else if(newRFQTypes == NewRFQTypes.REORDER)
		{
			selectedSalesOrder = getFragmentDataBundle().getParcelable(Constants.BUNDLE_KEY_REORDER_ITEM);
			if(selectedSalesOrder!=null)
			{
				List<SalesOrderItem> salesOrderLineItemList= selectedSalesOrder.getSalesLine();
				for(int i=0;i<salesOrderLineItemList.size();i++)
				{
					SalesOrderItem salesOrderItem = salesOrderLineItemList.get(i);
					String subCategory="";
					String category = salesOrderItem.getItemCatName();
					if(salesOrderItem.getSku()!=null && salesOrderItem.getSku().getSubcategory()!=null)
					{
						subCategory = salesOrderItem.getSku().getSubcategory();
					}

					UnitOfMeasure unitSelector=new UnitOfMeasure();
					unitSelector.setUnitOfMeasureName(salesOrderItem.getUom());
					unitSelector.setUnitOfMeasureID(salesOrderItem.getUom());

					String spec = salesOrderItem.getDescription();
					String quantity=salesOrderItem.getQty();
					addRFQItemInItemsList(true, category, subCategory, unitSelector, spec,salesOrderItem.getSku(), quantity);
				}
			}
		}
		else
		{
//			addRFQItemInItemsList();
			SKU_v3 sku = getPreviousSelectedSKUFromHistory();
			if(sku!=null)
			{
				addRFQItemInItemsList(true, sku.getCategory(), null, null, null,sku, null);
			}
			else {
				addRFQItemInItemsList();
			}
		}

		ImageButton_addMoreRFQItems.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Utils.hideKeyboard(getActivity());
				if (LinearLayout_rfqItemsParent == null || LinearLayout_rfqItemsParent.getChildCount() == 0)
				{
					addRFQItemInItemsList();
				}
				else if (validateItemsScreen(true))
				{
					addRFQItemInItemsList();
					rfqItemsParentScrollView.post(new Runnable() {
						@Override
						public void run() {
							rfqItemsParentScrollView.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});
				}
			}
		});
	}

	private boolean validateItems_Category(boolean isReporting, boolean isRequestFocus, TextInputLayout categoryInputLayout, final BetterSpinner categorySpinner)
	{
		if(categorySpinner.getText()!=null && categorySpinner.getText().toString().length()>0)
		{
			categoryInputLayout.setError(null);
			categoryInputLayout.setErrorEnabled(false);
			return true;
		}
		else
		{
			if(isReporting)
			{
				categoryInputLayout.setError(baseActivity.getString(R.string.addrfq_validationerror_material_category));
				categoryInputLayout.setErrorEnabled(true);

				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							categorySpinner.requestFocus();
						}
					});
				}
			}
			return false;
		}
	}

	private boolean validateItems_itemDetails(boolean isReporting, boolean isRequestFocus, TextInputLayout itemDetailsInputLayout, final AutoCompleteTextView itemDetailsAutoCompleteTextView)
	{
		if(itemDetailsAutoCompleteTextView.getText().toString().trim().length()==0)
		{
			if(isReporting)
			{
				itemDetailsInputLayout.setError(baseActivity.getString(R.string.addrfq_validationerror_material_specification));
				itemDetailsInputLayout.setErrorEnabled(true);

				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable(){

						@Override
						public void run() {
							itemDetailsAutoCompleteTextView.requestFocus();
						}
					});
				}
			}
			return false;
		}
		else
		{
			itemDetailsInputLayout.setError(null);
			itemDetailsInputLayout.setErrorEnabled(false);
			return true;
		}
	}

	private boolean validateItems_Quantity(boolean isReporting, boolean isRequestFocus, TextInputLayout quantityInputLayout, final EditText quantityEditText)
	{
		if(Utils.validateQuantity(quantityEditText, isReporting))
		{
			if(newRFQTypes == NewRFQTypes.DEALS)
			{
				if(isDealsQuantityUnderRange(selectedDeal.getPriceSpace(), quantityEditText, isReporting))
				{
					quantityInputLayout.setError(null);
					quantityInputLayout.setErrorEnabled(false);
					return true;
				}
				else
				{
					return false;
				}
			}
			quantityInputLayout.setError(null);
			quantityInputLayout.setErrorEnabled(false);
			return true;
		}
		else
		{
			if(isReporting)
			{
				quantityInputLayout.setError("Please enter valid quantity (########.##).");
				quantityInputLayout.setErrorEnabled(true);
				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							quantityEditText.requestFocus();
						}
					});
				}
			}
			return false;
		}
	}

	private boolean validateItems_Units(boolean isReporting, boolean isRequestFocus, TextInputLayout unitsInputLayout, final BetterSpinner unitsSpinner)
	{
		if(!(unitsSpinner.getText()!=null && unitsSpinner.getText().toString().length()>0))
		{
			if(isReporting)
			{
				unitsInputLayout.setError(baseActivity.getString(R.string.addrfq_validationerror_material_units));
				unitsInputLayout.setErrorEnabled(true);

				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							unitsSpinner.requestFocus();
						}
					});
					isRequestFocus=false;
				}
			}
			return false;
		}
		else
		{
			unitsInputLayout.setError(null);
			unitsInputLayout.setErrorEnabled(false);
			return true;
		}
	}

	private boolean validateItemsScreen(boolean isReporting)
	{
		boolean isRequestFocus=true;
		if(LinearLayout_rfqItemsParent==null || LinearLayout_rfqItemsParent.getChildCount()==0)
		{
			if(isReporting)
			{
				UIMessageUtility.displayUIMessage(baseActivity, new UIMessage(UIMessageType.ERROR, baseActivity.getString(R.string.addrfq_validationerror_no_items)));
			}
			return false;
		}
		else
		{
			boolean successflag=true;
			for(int i=0;i<LinearLayout_rfqItemsParent.getChildCount();i++)
			{
				boolean flag=true;
				View rfqItem = LinearLayout_rfqItemsParent.getChildAt(i);

				TextInputLayout TextInputLayout_category = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_category);
				TextInputLayout TextInputLayout_subCategory = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_subCategory);
				TextInputLayout TextInputLayout_itemDetails = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_itemDetails);
				TextInputLayout TextInputLayout_quantity = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_quantity);
				TextInputLayout TextInputLayout_unit = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_unit);

//%%%%%%%%%%%%%%%

				final BetterSpinner Spinner_material = (BetterSpinner)rfqItem.findViewById(R.id.Spinner_material);
				flag = validateItems_Category(isReporting, (flag && isReporting),TextInputLayout_category , Spinner_material);
				successflag=successflag==true?flag:false;

//%%%%%%%%%%%%%%%

				final AutoCompleteTextView AutoCompleteTextView_materialSpecification = (AutoCompleteTextView)rfqItem.findViewById(R.id.AutoCompleteTextView_materialSpecification);
				SuggestionAdapter adtr = (SuggestionAdapter)AutoCompleteTextView_materialSpecification.getAdapter();
				AutoCompleteTextView_materialSpecification.setAdapter(null);
				flag = validateItems_itemDetails(isReporting, (flag && isReporting), TextInputLayout_itemDetails, AutoCompleteTextView_materialSpecification);
				successflag=successflag==true?flag:false;
				AutoCompleteTextView_materialSpecification.setAdapter(adtr);

//%%%%%%%%%%%%%%%

				final EditText EditText_quantity = (EditText)rfqItem.findViewById(R.id.EditText_quantity);
				flag = validateItems_Quantity(isReporting, (flag && isReporting), TextInputLayout_quantity, EditText_quantity);
				successflag=successflag==true?flag:false;
//%%%%%%%%%%%%%%%

				final BetterSpinner Spinner_unitOfMeasure = (BetterSpinner)rfqItem.findViewById(R.id.Spinner_unitOfMeasure);
				flag = validateItems_Units(isReporting, (flag && isReporting), TextInputLayout_unit, Spinner_unitOfMeasure);
				successflag=successflag==true?flag:false;
//%%%%%%%%%%%%%%%

//				if(!flag)
//				{
//					successflag = flag;
//				}
			}
			return successflag;
		}
	}

	private void requestFocus(View view)
	{
		if (view.requestFocus()) {
//			yourcoordinatorlayout.clearFocus();
			view.requestFocus();
			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		}
	}

    private boolean isDealsQuantityUnderRange(List<DealItemPrice> priceSpace , final EditText EditText_quantity, boolean isReporting)
    {
        try
        {
            String enterQuantity = EditText_quantity.getText().toString();
            if(enterQuantity!=null && enterQuantity.length()>0 && priceSpace!=null && priceSpace.size()>0)
            {
                Double enterQuantityVal = Double.parseDouble(enterQuantity);
                if(enterQuantityVal < priceSpace.get(priceSpace.size()-1).getMinqty())
                {
                    if(!isReporting)
                    {
                        return false;
                    }
                    //quantity is less than MOQ so it will be places as iRFQ
                    //check for user response and return true on continue else false

                    new AlertDialog.Builder(baseActivity)
                    .setMessage(baseActivity.getString(R.string.addrfq_label_qty_below_MOQ_dlg_title))
                    .setCancelable(false)
                    .setPositiveButton(baseActivity.getString(R.string.label_continue), new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            newRFQTypes = NewRFQTypes.iRFQ;
                            if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
                            {
                                currentTabPosition=2;
                            }
                            else
                            {
                                currentTabPosition=1;
                            }
							openRFQSection(RFQSection.SECTION_BASEDON_POSITION, currentTabPosition, true);
                        }
                    })
                    .setNegativeButton(baseActivity.getString(R.string.label_cancel), new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            //cancel submission and focus Quantity field
                            EditText_quantity.requestFocus();
                        }
                    })
                    .show();
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        catch(Exception ex){}
        return false;
    }

	private void initDeliveryAddressScreen(boolean isLoggedIn, boolean isAddressListRefreshOnly)
	{
		paymentTermRadioGroup = (RadioGroup) rootView.findViewById(R.id.paymentTermRadioGroup);
		TextInputLayout_paymentTermDays = (TextInputLayout) rootView.findViewById(R.id.TextInputLayout_paymentTermDays);
		EditText_paymentTermDays = (EditText) rootView.findViewById(R.id.EditText_paymentTermDays);

		paymentTermRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				if(checkedId == R.id.paymentTermCash)
				{
					TextInputLayout_paymentTermDays.setVisibility(TextInputLayout.GONE);
				}
				else if(checkedId == R.id.paymentTermCredit)
				{
					TextInputLayout_paymentTermDays.setVisibility(TextInputLayout.VISIBLE);
				}
			}

		});

		if(!isAddressListRefreshOnly)
		{
			TextInputLayout_taxation = (TextInputLayout) rootView.findViewById(R.id.TextInputLayout_taxation);
			TextInputLayout_urgency = (TextInputLayout) rootView.findViewById(R.id.TextInputLayout_urgency);

			Spinner_taxation = (BetterSpinner) rootView.findViewById(R.id.Spinner_taxation);
			Spinner_urgency = (BetterSpinner) rootView.findViewById(R.id.Spinner_urgency);

			Spinner_taxation.setOnBetterSpinnerItemClickedListener(new OnBetterSpinnerItemClickedListener()
			{
				@Override
				public void onItemClicked(Object selectedObject, int position)
				{
					validateDelivery_taxation(true, true, TextInputLayout_taxation, Spinner_taxation);

					TaxationPreference_v3 tp = (TaxationPreference_v3)selectedObject;
					if(tp.getValue().indexOf("VAT")!=-1)
					{
						CheckBox_isFormC.setChecked(false);
						CheckBox_isFormC.setEnabled(false);
					}
					else
					{
						CheckBox_isFormC.setEnabled(true);
					}

					Spinner_taxation.setTag(selectedObject);
				}
			});

			Spinner_urgency.setOnBetterSpinnerItemClickedListener(new OnBetterSpinnerItemClickedListener()
			{
				@Override
				public void onItemClicked(Object selectedObject, int position)
				{
					validateDelivery_urgency(true, true, TextInputLayout_urgency, Spinner_urgency);
					Spinner_urgency.setTag(selectedObject);
				}
			});

			ProgressBar_urgencyLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_urgencyLoader);
			ProgressBar_taxationLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_taxationLoader);

			CheckBox_isArrangeFreightByYou = (CheckBox) rootView.findViewById(R.id.CheckBox_isArrangeFreightByYou);
			CheckBox_isFormC = (CheckBox) rootView.findViewById(R.id.CheckBox_isFormC);

			iAddRFQPresentor.getTaxationPrefsList(Spinner_taxation, ProgressBar_taxationLoader);
			iAddRFQPresentor.getUrgencyList(Spinner_urgency, ProgressBar_urgencyLoader);

			if(newRFQTypes == NewRFQTypes.REORDER)
			{
				if(selectedSalesOrder!=null)
				{
					CheckBox_isArrangeFreightByYou.setChecked(false);
					CheckBox_isFormC.setChecked(false);
				}
			}
		}

		LL1 = (LinearLayout)rootView.findViewById(R.id.LL1);
		LL2 = (LinearLayout)rootView.findViewById(R.id.LL2);
		Button_addDeliveryAddress = (Button)rootView.findViewById(R.id.Button_addDeliveryAddress);
		Button_selectDeliveryAddress = (Button)rootView.findViewById(R.id.Button_selectDeliveryAddress);
		Button_selectDeliveryAddress.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(deliveryAddressList!=null && deliveryAddressList.size()>0)
				{
					if(DeliveryAddressesInsertFragment.isDeliveryAddressAdded)
					{
						DeliveryAddressesInsertFragment.isDeliveryAddressAdded=false;
						iDeliveryAddressesListPresentor.getDeliveryAddressList("");
						return;
					}
					showSelectDeliveryAddressDialog(baseActivity, "Select delivery address:", deliveryAddressList);
				}
				else
				{
					iDeliveryAddressesListPresentor.getDeliveryAddressList("");
				}
			}
		});

		StylesManager.getInstance(baseActivity).setTextViewStyle(Button_selectDeliveryAddress, StyleTypes.TextView_body1);

		if(deliveryAddressList==null)
		{
			deliveryAddressList=new ArrayList<DeliveryAddress>();
		}

		Button_addDeliveryAddress.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(getFragmentDataBundle()==null)
				{
					Bundle bundle=new Bundle();
					bundle.putBoolean("SaveDataFlag", true);
					setFragmentDataBundle(bundle);
				}
				else
				{
					getFragmentDataBundle().putBoolean("SaveDataFlag", true);
				}
				baseActivity.openChildActivityFragment(DeliveryAddressesInsertFragment.class, null, true, true, false);
				DeliveryAddressesInsertFragment.isDeliveryAddressAdded=true;
			}
		});

		EditText_deliveryAdress = (EditText) rootView.findViewById(R.id.EditText_deliveryAdress);
		EditText_deliveryAdress2 = (EditText) rootView.findViewById(R.id.EditText_deliveryAdress2);
		EditText_deliveryPostCode = (EditText)rootView.findViewById(R.id.EditText_deliveryPostCode);
		EditText_deliveryPostCode.setFilters(new InputFilter[]{ new InputFilterMinMax(6)});

		TextInputLayout_deliveryAddressLine1 = (TextInputLayout) rootView.findViewById(R.id.TextInputLayout_deliveryAddressLine1);
		TextInputLayout_deliveryAddressLine2 = (TextInputLayout) rootView.findViewById(R.id.TextInputLayout_deliveryAddressLine2);
		TextInputLayout_deliveryPostCode = (TextInputLayout) rootView.findViewById(R.id.TextInputLayout_deliveryPostCode);

		EditText_deliveryAdress.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if(!hasFocus)
				{
					validateDelivery_addressLine1(true, false);
				}
			}
		});

		EditText_deliveryPostCode.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if(!hasFocus)
				{
					validateDelivery_postCode(true, false);
				}
			}
		});

		if(isLoggedIn)
		{
			LL2.setVisibility(LinearLayout.VISIBLE);
			LL1.setVisibility(LinearLayout.GONE);

		}
		else
		{
			LL1.setVisibility(LinearLayout.VISIBLE);
			LL2.setVisibility(LinearLayout.GONE);


		}

		Button_submit.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
			if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
			{
				if(!validateDeliveryScreen(true))
				{
					return;
				}
			}
			else
			{
				if(!validateContactScreen(true))
				{
					return;
				}
			}

			saveSelectedCategoryInHistory();

			Utils.hideKeyboard(getActivity());
			Button_submit.setEnabled(false);
			Button_submit.setClickable(false);

			newRFQ = new NewRFQ_v3();

			//organization
			Organization_v3 organization = getOrganizationPayloadEntity();
			if(newRFQTypes == NewRFQTypes.DEALS)
			{
				organization.setLeadSource(LeadSource.Android_Deal.toString());
				newRFQ.setObject_type_id("2");
				newRFQ.setDeal(getDealPayloadEntity());
				Opportunity_v3 opportunity = getOpportunityPayloadEntity();
				opportunity.setLeadSource(LeadSource.Android_Deal.toString());
				newRFQ.setOpportunity(opportunity);
			}
			else if(newRFQTypes == NewRFQTypes.REORDER)
			{
				//reorder
				organization.setLeadSource(LeadSource.Android_Reorder.toString());
//				newRFQ.setObject_type_id("3");//time being as requested by himanshu in Bug #2675
				ReOrder_v3 reorder = getReOrderPayloadEntity();
				if(isReorderItemModified(reorder.getRfqLineList(), selectedSalesOrder))
				{
					//opportunity
					newRFQTypes = NewRFQTypes.iRFQ;
					organization.setLeadSource(LeadSource.Android_RFQ.toString());
					newRFQ.setObject_type_id("1");
					Opportunity_v3 opportunity = getOpportunityPayloadEntity();
					opportunity.setLeadSource(LeadSource.Android_RFQ.toString());
					newRFQ.setOpportunity(opportunity);
					newRFQ.setReorder(null);
				}
				else
				{
					//reorder
					newRFQTypes = NewRFQTypes.REORDER;
					organization.setLeadSource(LeadSource.Android_Reorder.toString());
					newRFQ.setObject_type_id("3");
					ReOrder_v3 reOrder = getReOrderPayloadEntity();
					newRFQ.setReorder(reOrder);
					Opportunity_v3 opportunity = getOpportunityPayloadEntity();
					opportunity.setLeadSource(LeadSource.Android_Reorder.toString());
					newRFQ.setOpportunity(opportunity);
				}
				newRFQ.setObject_type_id("1");//time being as requested by himanshu in Bug #2675
			}
			else
			{
				//opportunity
				organization.setLeadSource(LeadSource.Android_RFQ.toString());
				newRFQ.setObject_type_id("1");
				Opportunity_v3 opportunity = getOpportunityPayloadEntity();
				opportunity.setLeadSource(LeadSource.Android_RFQ.toString());
				newRFQ.setOpportunity(opportunity);
			}
			newRFQ.setOrganisation(organization);
			iAddRFQPresentor.addNewRFQ(newRFQ);
			Utils.hideKeyboard(baseActivity);
			}
		});
	}

	private void saveSelectedCategoryInHistory()
	{

	}

	private Organization_v3 getOrganizationPayloadEntity()
	{
		Organization_v3 organization = new Organization_v3();
		organization.setCompany_name(EditText_companyName.getText().toString());
		organization.setContactPerson(EditText_fullName.getText().toString());
		organization.setEmail(EditText_email.getText().toString());
		organization.setPhone(EditText_mobileNumber.getText().toString());
		if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
		{
			organization.setShip_street(selectedDeliveryAddress.getSmeAddress()
					+ ", " + selectedDeliveryAddress.getSmeAddress2()
					+ ", " + selectedDeliveryAddress.getSmeCity()
					+ ", " + selectedDeliveryAddress.getSmeState()
					+ ", " + selectedDeliveryAddress.getSmePostCode());
			organization.setShip_pincode(selectedDeliveryAddress.getSmePostCode());
			organization.setShip_state(selectedDeliveryAddress.getSmeState());
			organization.setShip_city(selectedDeliveryAddress.getSmeCity());
			organization.setShippingAddressCode(selectedDeliveryAddress.getCode());
		}
		else
		{
			organization.setShip_street(
					EditText_deliveryAdress.getText().toString()
							+", "+EditText_deliveryAdress2.getText().toString()
							);
			organization.setShip_pincode(EditText_deliveryPostCode.getText().toString());
			organization.setShip_state("");
			organization.setShip_city("");
		}
		return organization;
	}
	private Opportunity_v3 getOpportunityPayloadEntity()
	{
		Opportunity_v3 opportunity = new Opportunity_v3();

		if(R.id.paymentTermCredit == paymentTermRadioGroup.getCheckedRadioButtonId())
		{
			opportunity.setPaymentTermDays(EditText_paymentTermDays.getText().toString());
		}
		else
		{
			opportunity.setPaymentTermDays("0");
		}


		opportunity.setTaxationPref((TaxationPreference_v3) Spinner_taxation.getTag());
		opportunity.setUrgency((Urgency_v3) Spinner_urgency.getTag());
		opportunity.setFreightArrangement(CheckBox_isArrangeFreightByYou.isChecked() ? "true" : "false");
		opportunity.setFormC(CheckBox_isFormC.isChecked() ? "true" : "false");
		ArrayList<OpportunityLine_v3> rfqLineItemList = new ArrayList<OpportunityLine_v3>();
		for(int i=0;i<LinearLayout_rfqItemsParent.getChildCount();i++)
		{
			View childView = LinearLayout_rfqItemsParent.getChildAt(i);
			OpportunityLine_v3 newRFQItem=new OpportunityLine_v3();

			BetterSpinner Spinner_material=(BetterSpinner)childView.findViewById(R.id.Spinner_material);
			BetterSpinner Spinner_materialSubCategory = (BetterSpinner)childView.findViewById(R.id.Spinner_materialSubCategory);
			AutoCompleteTextView AutoCompleteTextView_materialSpecification=(AutoCompleteTextView)childView.findViewById(R.id.AutoCompleteTextView_materialSpecification);
			EditText EditText_quantity=(EditText)childView.findViewById(R.id.EditText_quantity);
			BetterSpinner Spinner_unitOfMeasure=(BetterSpinner)childView.findViewById(R.id.Spinner_unitOfMeasure);

			SKU_v3 sku = new SKU_v3();
			sku.setCategory((String) Spinner_material.getText().toString());
			sku.setSubcategory((String) Spinner_materialSubCategory.getText().toString());
			Object skuObj = AutoCompleteTextView_materialSpecification.getTag();
			if(skuObj!=null && skuObj instanceof SKU_v3)// if user has selected SKU from type ahead
			{
				SKU_v3 selectedSKU = (SKU_v3)skuObj;
				sku.setSkucode(selectedSKU.getSkucode());
			}

			saveSelectedSKUInHistory(sku);

			newRFQItem.setSku(sku);

			newRFQItem.setRemarks(AutoCompleteTextView_materialSpecification.getText().toString());
			newRFQItem.setQuantity(EditText_quantity.getText().toString());
			newRFQItem.setUom(((UnitOfMeasure) Spinner_unitOfMeasure.getTag()).getUnitOfMeasureID());

			rfqLineItemList.add(newRFQItem);
		}
		opportunity.setOpportunityLine(rfqLineItemList);
		return opportunity;
	}

	private void saveSelectedSKUInHistory(SKU_v3 sku)
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
					.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
					.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
					.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
			String skuJsonStr = objectMapper.writeValueAsString(sku);

			MyAccountApplication app = (MyAccountApplication) getActivity().getApplicationContext();
			SharedPreferences sharedPreferences = app.getPrefs();
			Editor editor = sharedPreferences.edit();
			editor.putString(Constants.PREFERENCE_SELECTED_SKU, skuJsonStr);
			editor.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private SKU_v3 getPreviousSelectedSKUFromHistory()
	{
		try
		{
			MyAccountApplication app = (MyAccountApplication) getActivity().getApplicationContext();
			SharedPreferences sharedPreferences = app.getPrefs();
			String previousSelectedSKUJsonStr = sharedPreferences.getString(Constants.PREFERENCE_SELECTED_SKU, null);
			if(previousSelectedSKUJsonStr!=null)
			{
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
						.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
						.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
						.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
						.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
				JsonFactory jsonFactory = new JsonFactory();

				JsonParser jp = jsonFactory.createJsonParser(previousSelectedSKUJsonStr);
				SKU_v3 sku = objectMapper.readValue(jp, SKU_v3.class);
				return sku;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	private Wishlist_v3 getWishlistPayloadEntity()
	{
		return null;
	}
	private ReOrder_v3 getReOrderPayloadEntity()
	{
		ReOrder_v3 reorder = new ReOrder_v3();
		reorder.setTaxationPref(((TaxationPreference_v3) Spinner_taxation.getTag()).getKey());
		reorder.setUrgency(((Urgency_v3) Spinner_urgency.getTag()).getKey());
		reorder.setFreightArrangement(CheckBox_isArrangeFreightByYou.isChecked() ? "true" : "false");
		reorder.setFormC(CheckBox_isFormC.isChecked() ? "true" : "false");
		ArrayList<RFQLineItem_v3> rfqLineItemList = new ArrayList<RFQLineItem_v3>();
		for(int i=0;i<LinearLayout_rfqItemsParent.getChildCount();i++)
		{
			View childView = LinearLayout_rfqItemsParent.getChildAt(i);
			RFQLineItem_v3 newRFQItem=new RFQLineItem_v3();

			BetterSpinner Spinner_material=(BetterSpinner)childView.findViewById(R.id.Spinner_material);
			BetterSpinner Spinner_materialSubCategory = (BetterSpinner)childView.findViewById(R.id.Spinner_materialSubCategory);
			AutoCompleteTextView AutoCompleteTextView_materialSpecification=(AutoCompleteTextView)childView.findViewById(R.id.AutoCompleteTextView_materialSpecification);
			EditText EditText_quantity=(EditText)childView.findViewById(R.id.EditText_quantity);
			BetterSpinner Spinner_unitOfMeasure=(BetterSpinner)childView.findViewById(R.id.Spinner_unitOfMeasure);

			newRFQItem.setItemCategory((String) Spinner_material.getText().toString());
			newRFQItem.setItemSubCategory1((String) Spinner_materialSubCategory.getText().toString());
			newRFQItem.setDescription(AutoCompleteTextView_materialSpecification.getText().toString());
			newRFQItem.setQuantity(EditText_quantity.getText().toString());
			newRFQItem.setUom(((UnitOfMeasure) Spinner_unitOfMeasure.getTag()).getUnitOfMeasureID());

			rfqLineItemList.add(newRFQItem);
		}
		reorder.setRfqLineList(rfqLineItemList);
		return reorder;
	}
	private Deal_v3 getDealPayloadEntity()
	{
		Deal_v3 deal = selectedDeal;
		deal.setTaxationPref((TaxationPreference_v3)Spinner_taxation.getTag());
		deal.setUrgency((Urgency_v3) Spinner_urgency.getTag());
		deal.setFormC(CheckBox_isFormC.isChecked() ? "true" : "false");
		deal.setFreight(CheckBox_isArrangeFreightByYou.isChecked() ? "true" : "false");

		for(int i=0;i<LinearLayout_rfqItemsParent.getChildCount();i++)
		{
			View childView = LinearLayout_rfqItemsParent.getChildAt(i);
			EditText EditText_quantity=(EditText)childView.findViewById(R.id.EditText_quantity);
			deal.setQty(EditText_quantity.getText().toString());
		}

		return deal;
	}

	private boolean isReorderItemModified(ArrayList<RFQLineItem_v3> rfqLineItemList, SalesOrder salesOrder)
	{
		if(salesOrder!=null && rfqLineItemList.size()>0
				&& salesOrder.getSalesLine()!=null
				&& salesOrder.getSalesLine().size()>0
				&& rfqLineItemList.size()==salesOrder.getSalesLine().size())
		{
			for(SalesOrderItem salesOrderItem:salesOrder.getSalesLine())
			{
				for(RFQLineItem_v3 newRFQItem:rfqLineItemList)
				{
					if(!newRFQItem.getItemCategory().equals(salesOrderItem.getItemCatName()))
					{
						return true;
					}
					if(!newRFQItem.getDescription().equals(salesOrderItem.getDescription()))
					{
						return true;
					}
					if(!newRFQItem.getQuantity().equals(salesOrderItem.getQty()))
					{
						return true;
					}
					if(!newRFQItem.getUom().equals(salesOrderItem.getUom()))
					{
						return true;
					}
					newRFQItem.setItemSubCategory1(salesOrderItem.getSubcat1());
					newRFQItem.setItemSubCategory2(salesOrderItem.getSubcat2());
					newRFQItem.setItemSubCategory3(salesOrderItem.getSubcat3());
				}
			}
			return false;
		}
		return true;
	}

	private boolean validateDelivery_taxation(boolean isReporting, boolean isRequestFocus, TextInputLayout TextInputLayout_taxation, final BetterSpinner Spinner_taxation)
	{
		//if(!(Spinner_taxation!=null && Spinner_taxation.getTag()!=null && Spinner_taxation.getTag().toString().indexOf("elect")==-1))
		if(!(Spinner_taxation.getText()!=null && Spinner_taxation.getText().toString().length()>0))
		{
			if(isReporting)
			{
				TextInputLayout_taxation.setError(baseActivity.getString(R.string.addrfq_validationerror_taxpreferences));
				TextInputLayout_taxation.setErrorEnabled(true);
				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							Spinner_taxation.requestFocus();
						}
					});
				}
			}
			return false;
		}
		else
		{
			TextInputLayout_taxation.setError(null);
			TextInputLayout_taxation.setErrorEnabled(false);
			return true;
		}
	}

	private boolean validateDelivery_urgency(boolean isReporting, boolean isRequestFocus, TextInputLayout TextInputLayout_urgency, final BetterSpinner Spinner_urgency)
	{
		//if(!(Spinner_urgency!=null && Spinner_urgency.getTag()!=null && Spinner_urgency.getTag().toString().indexOf("elect")==-1))
		if(!(Spinner_urgency.getText()!=null && Spinner_urgency.getText().toString().length()>0))
		{
			if(isReporting)
			{
				TextInputLayout_urgency.setError(baseActivity.getString(R.string.addrfq_validationerror_ungency));
				TextInputLayout_urgency.setErrorEnabled(true);
				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							Spinner_urgency.requestFocus();
						}
					});
				}
			}
			return false;
		}
		else
		{
			TextInputLayout_urgency.setError(null);
			TextInputLayout_urgency.setErrorEnabled(false);
			return true;
		}
	}

	private boolean validateDelivery_addressLine1(boolean isReporting, boolean isRequestFocus)
	{
		if(EditText_deliveryAdress.getText().toString().trim().length()>0)
		{
			TextInputLayout_deliveryAddressLine1.setError(null);
			TextInputLayout_deliveryAddressLine1.setErrorEnabled(false);
			return true;
		}
		else
		{
			if(isReporting)
			{
				TextInputLayout_deliveryAddressLine1.setError(baseActivity.getString(R.string.addrfq_validationerror_address_line1));
				TextInputLayout_deliveryAddressLine1.setErrorEnabled(true);
				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							EditText_deliveryAdress.requestFocus();
						}
					});
				}
			}
			return false;
		}
	}

	private boolean validateDelivery_postCode(boolean isReporting, boolean isRequestFocus)
	{
		if(Utils.isValidPinCode(EditText_deliveryPostCode))
		{
			TextInputLayout_deliveryPostCode.setError(null);
			TextInputLayout_deliveryPostCode.setErrorEnabled(false);
			return true;
		}
		else
		{
			if(isReporting)
			{
				TextInputLayout_deliveryPostCode.setError(baseActivity.getString(R.string.addrfq_validationerror_pincode));
				TextInputLayout_deliveryPostCode.setErrorEnabled(true);
				if(isRequestFocus)
				{
					new android.os.Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							EditText_deliveryPostCode.requestFocus();
						}
					});
				}

			}
			return false;
		}
	}

	private boolean validateDelivery_paymentTermDays(boolean isReporting, boolean isRequestFocus)
	{
		if(paymentTermRadioGroup.getCheckedRadioButtonId()==R.id.paymentTermCredit)
		{
			try
			{
				String paymentTermDaysStr = EditText_paymentTermDays.getText().toString();
				float val = Float.parseFloat(paymentTermDaysStr);
				if(val>=1f && val<=90f)
				{
					TextInputLayout_paymentTermDays.setError(null);
					TextInputLayout_paymentTermDays.setErrorEnabled(false);
					return true;
				}
			}
			catch(Exception ex){}
		}
		else
		{
			TextInputLayout_paymentTermDays.setError(null);
			TextInputLayout_paymentTermDays.setErrorEnabled(false);
			return true;
		}

		if(isReporting)
		{
			TextInputLayout_paymentTermDays.setError("Please enter valid payment term in days ( 1 to 90 )." );
			TextInputLayout_paymentTermDays.setErrorEnabled(true);
			if(isRequestFocus)
			{
				new android.os.Handler().post(new Runnable()
				{
					@Override
					public void run()
					{
						EditText_paymentTermDays.requestFocus();
					}
				});
			}
		}

		return false;
	}

	private boolean validateDeliveryScreen(boolean isReporting)
	{
		boolean flag=true;
		boolean isRequestFocus=true;
		boolean successflag=true;

		flag = validateDelivery_taxation(isReporting, (isReporting && flag), TextInputLayout_taxation, Spinner_taxation);
		successflag=successflag==true?flag:false;

		flag = validateDelivery_urgency(isReporting, (isReporting && flag), TextInputLayout_urgency, Spinner_urgency);
		successflag=successflag==true?flag:false;


		flag = validateDelivery_paymentTermDays(isReporting, (isReporting && flag));
		successflag=successflag==true?flag:false;



		if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
		{
			if(selectedDeliveryAddress==null)
			{
				if(isReporting)
				{
					UIMessageUtility.displayUIMessage(baseActivity, new UIMessage(UIMessageType.ERROR, baseActivity.getString(R.string.addrfq_validationerror_deliveryaddress)));
				}
				flag=false;
				successflag=successflag==true?flag:false;
			}
		}
		else
		{
			flag=validateNonLoggedInDeliveryAddressScreen(isReporting, flag, isRequestFocus);
			successflag=successflag==true?flag:false;
		}
		return successflag;
	}

	private boolean validateNonLoggedInDeliveryAddressScreen(boolean isReporting, boolean isSuccess, boolean isRequestFocus)
	{
		boolean successflag=isSuccess;
		isSuccess = validateDelivery_addressLine1(isReporting, (isReporting && isSuccess));
		successflag=successflag==true?isSuccess:false;
		isSuccess = validateDelivery_postCode(isReporting, (isReporting && isSuccess));
		successflag=successflag==true?isSuccess:false;
		return successflag;
	}

	private void addRFQItemInItemsList()
	{
		addRFQItemInItemsList(false, null, null, null,null,null,  "");
	}
	private void addRFQItemInItemsList(boolean initFlag, String itemCategory, String itemSubCategory, UnitOfMeasure uom, String itemDetails,SKU_v3 sku, String itemQuantity)
	{
		final View rfqItem = LayoutInflater.from(baseActivity).inflate(R.layout.add_rfq_items_list_item, null);

		final TextInputLayout TextInputLayout_category = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_category);
		final TextInputLayout TextInputLayout_subCategory = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_subCategory);
		final TextInputLayout TextInputLayout_itemDetails = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_itemDetails);
		final TextInputLayout TextInputLayout_quantity = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_quantity);
		final TextInputLayout TextInputLayout_unit = (TextInputLayout) rfqItem.findViewById(R.id.TextInputLayout_unit);

		final ImageButton ImageButton_removeRFQItem = (ImageButton)rfqItem.findViewById(R.id.ImageButton_removeRFQItem);
		final BetterSpinner BetterSpinner_itemCategory = (BetterSpinner)rfqItem.findViewById(R.id.Spinner_material);

		final RelativeLayout materialSubCategoryParentRL = (RelativeLayout)rfqItem.findViewById(R.id.materialSubCategoryParentRL);


		final BetterSpinner BetterSpinner_itemSubCategory = (BetterSpinner)rfqItem.findViewById(R.id.Spinner_materialSubCategory);
		final ProgressBar ProgressBar_itemDetails = (ProgressBar)rfqItem.findViewById(R.id.ProgressBar_materialSpecificationLoader);
		final AutoCompleteTextView AutoCompleteTextView_itemDetails = (AutoCompleteTextView)rfqItem.findViewById(R.id.AutoCompleteTextView_materialSpecification);

		AutoCompleteTextView_itemDetails.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if(!hasFocus)
				{
					validateItems_itemDetails(true, false, TextInputLayout_itemDetails,  AutoCompleteTextView_itemDetails);
				}
			}
		});

		final EditText EditText_quantity = (EditText)rfqItem.findViewById(R.id.EditText_quantity);

		EditText_quantity.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if(!hasFocus)
				{
					validateItems_Quantity(true, false, TextInputLayout_quantity,  EditText_quantity);
				}
			}
		});

		final BetterSpinner Spinner_unitOfMeasure = (BetterSpinner) rfqItem.findViewById(R.id.Spinner_unitOfMeasure);

		final ProgressBar ProgressBar_unitOfMeasureLoader = (ProgressBar)rfqItem.findViewById(R.id.ProgressBar_unitOfMeasureLoader);
		final ProgressBar ProgressBar_materialLoader = (ProgressBar)rfqItem.findViewById(R.id.ProgressBar_materialLoader);
		final ProgressBar ProgressBar_materialSubCategoryLoader = (ProgressBar)rfqItem.findViewById(R.id.ProgressBar_materialSubCategoryLoader);

		final SuggestionAdapter suggestionAdapter= new SuggestionAdapter(baseActivity,
				ProgressBar_itemDetails,
				AutoCompleteTextView_itemDetails.getText().toString(),
				BetterSpinner_itemCategory,
				BetterSpinner_itemSubCategory
		);

		AutoCompleteTextView_itemDetails.setAdapter(suggestionAdapter);

		AutoCompleteTextView_itemDetails.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Utils.hideKeyboard(getActivity());
				isSKUSelectionPerformed = true;
				AutoCompleteTextView_itemDetails.setError(null);
				AutoCompleteTextView_itemDetails.setTag(parent.getItemAtPosition(position));
			}
		});

		if(newRFQTypes == NewRFQTypes.DEALS)
		{
			Spinner_unitOfMeasure.setEnabled(false);
			Spinner_unitOfMeasure.setClickable(false);
			BetterSpinner_itemCategory.setEnabled(false);
			BetterSpinner_itemCategory.setClickable(false);
			BetterSpinner_itemSubCategory.setEnabled(false);
			BetterSpinner_itemSubCategory.setClickable(false);
			AutoCompleteTextView_itemDetails.setEnabled(false);
			AutoCompleteTextView_itemDetails.setClickable(false);

			ImageButton_removeRFQItem.setVisibility(ImageButton.GONE);
			ImageButton_addMoreRFQItems.setVisibility(ImageButton.GONE);
		}
		else
		{
			Spinner_unitOfMeasure.setEnabled(true);
			Spinner_unitOfMeasure.setClickable(true);
			BetterSpinner_itemCategory.setEnabled(true);
			BetterSpinner_itemCategory.setClickable(true);
			BetterSpinner_itemSubCategory.setEnabled(true);
			BetterSpinner_itemSubCategory.setClickable(true);
			AutoCompleteTextView_itemDetails.setEnabled(true);
			AutoCompleteTextView_itemDetails.setClickable(true);

			ImageButton_removeRFQItem.setVisibility(ImageButton.VISIBLE);
			ImageButton_addMoreRFQItems.setVisibility(ImageButton.VISIBLE);

			ImageButton_removeRFQItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					LinearLayout_rfqItemsParent.removeView(rfqItem);
				}
			});

			//Item Categories
			iAddRFQPresentor.getItemCategories(BetterSpinner_itemCategory, ProgressBar_materialLoader);
			iAddRFQPresentor.getUnitsList("", Spinner_unitOfMeasure, ProgressBar_unitOfMeasureLoader);
			BetterSpinner_itemCategory.setOnBetterSpinnerItemClickedListener(new OnBetterSpinnerItemClickedListener() {
				@Override
				public void onItemClicked(Object selectedObject, int position) {

					validateItems_Category(true, true, TextInputLayout_category,  BetterSpinner_itemCategory);

					String selectedCategory = (String) selectedObject;
					String prevousSelectedCategory = (String)BetterSpinner_itemCategory.getTag();
					if (prevousSelectedCategory != null && prevousSelectedCategory.length() > 0 && selectedCategory.equals(prevousSelectedCategory))
					{
						return;
					}
					BetterSpinner_itemCategory.setTag(selectedCategory);
					materialSubCategoryParentRL.setVisibility(RelativeLayout.VISIBLE);
					iAddRFQPresentor.getItemSubCategories(BetterSpinner_itemSubCategory, ProgressBar_materialSubCategoryLoader, selectedCategory);
					iAddRFQPresentor.getUnitsList(selectedCategory, Spinner_unitOfMeasure, ProgressBar_unitOfMeasureLoader);
					EditText_quantity.setText("");
					BetterSpinner_itemSubCategory.setText("");
					Spinner_unitOfMeasure.setText("");
					Object skuObj = AutoCompleteTextView_itemDetails.getTag();
					if (skuObj != null && skuObj instanceof SKU_v3)// if user has selected SKU from type ahead
					{
						AutoCompleteTextView_itemDetails.setAdapter(null);
						AutoCompleteTextView_itemDetails.setText("");
						AutoCompleteTextView_itemDetails.setAdapter(suggestionAdapter);
					}
				}
			});

			BetterSpinner_itemSubCategory.setOnBetterSpinnerItemClickedListener(new OnBetterSpinnerItemClickedListener() {
				@Override
				public void onItemClicked(Object selectedObject, int position) {
					BetterSpinner_itemSubCategory.setError(null);
					String selectedSubCategory = (String) selectedObject;
					String previousSelectedSubCategory= (String) BetterSpinner_itemSubCategory.getTag();
					if (previousSelectedSubCategory != null && previousSelectedSubCategory.length() > 0 && previousSelectedSubCategory.equals(selectedSubCategory)) {
						return;
					}
					BetterSpinner_itemSubCategory.setTag(selectedSubCategory);

					EditText_quantity.setText("");
					Spinner_unitOfMeasure.setText("");
					Object skuObj = AutoCompleteTextView_itemDetails.getTag();
					if (skuObj != null && skuObj instanceof SKU_v3)// if user has selected SKU from type ahead
					{
						AutoCompleteTextView_itemDetails.setAdapter(null);
						AutoCompleteTextView_itemDetails.setText("");
						AutoCompleteTextView_itemDetails.setAdapter(suggestionAdapter);
					}
				}
			});

			Spinner_unitOfMeasure.setOnBetterSpinnerItemClickedListener(new OnBetterSpinnerItemClickedListener()
			{
				@Override
				public void onItemClicked(Object selectedObject, int position)
				{
					validateItems_Units(true, true, TextInputLayout_unit,  Spinner_unitOfMeasure);
					Spinner_unitOfMeasure.setTag(selectedObject);
				}
			});

		}

		if((newRFQTypes == NewRFQTypes.iRFQ || newRFQTypes == NewRFQTypes.DEALS) && LinearLayout_rfqItemsParent.getChildCount() == 0)
		{
			ImageButton_removeRFQItem.setVisibility(ImageButton.GONE);
		}
		else
		{
			ImageButton_removeRFQItem.setVisibility(ImageButton.VISIBLE);
		}

		if(initFlag)
		{
			if(itemCategory!=null && itemCategory.length()>0)
			{
				BetterSpinner_itemCategory.setText(itemCategory);
				if(newRFQTypes != NewRFQTypes.DEALS)
				{
					materialSubCategoryParentRL.setVisibility(RelativeLayout.VISIBLE);
					iAddRFQPresentor.getItemSubCategories(BetterSpinner_itemSubCategory, ProgressBar_materialSubCategoryLoader, itemCategory);
					iAddRFQPresentor.getUnitsList(itemCategory, Spinner_unitOfMeasure, ProgressBar_unitOfMeasureLoader);
				}
			}

			if(itemSubCategory!=null && itemSubCategory.length()>0)
			{
				BetterSpinner_itemSubCategory.setText(itemSubCategory);
				materialSubCategoryParentRL.setVisibility(RelativeLayout.VISIBLE);
			}

			if(itemDetails!=null && itemDetails.length()>0)
			{
				AutoCompleteTextView_itemDetails.setAdapter(null);
				AutoCompleteTextView_itemDetails.setText(itemDetails);
				AutoCompleteTextView_itemDetails.setAdapter(suggestionAdapter);
			}

			if(sku!=null)
			{
				AutoCompleteTextView_itemDetails.setTag(sku);
			}

			if(itemQuantity!=null && itemQuantity.length()>0)
			{
				EditText_quantity.setText(itemQuantity);
			}

			if(uom!=null)
			{
				Spinner_unitOfMeasure.setText(uom.getUnitOfMeasureName());
				Spinner_unitOfMeasure.setTag(uom);
			}
		}
		LinearLayout_rfqItemsParent.addView(rfqItem);
	}



	long mLastClickTime;
	private boolean isClickAllowed()
	{
		if (SystemClock.elapsedRealtime() - mLastClickTime < 1500)
		{
			return false;
		}
		mLastClickTime = SystemClock.elapsedRealtime();
		return true;
	}

	private void resetNextPreviousButtonStates()
	{
		if(currentTabPosition == 0)
		{
			Button_previous.setClickable(false);
			Button_previous.setTextColor(getResources().getColor(R.color.black));
			Button_previous.setCompoundDrawablesWithIntrinsicBounds( R.drawable.previous_arrow_black, 0, 0, 0);
		}
		else
		{
			Button_previous.setClickable(true);
			Button_previous.setTextColor(getResources().getColor(R.color.accent1));
			Button_previous.setCompoundDrawablesWithIntrinsicBounds( R.drawable.previous_arrow, 0, 0, 0);
		}

		if(currentTabPosition == 2)
		{
			Button_next.setVisibility(Button.GONE);
			Button_submit.setVisibility(Button.VISIBLE);
			Button_submit.setTextColor(getResources().getColor(R.color.accent1));
		}
		else
		{
			Button_next.setVisibility(Button.VISIBLE);
			Button_submit.setVisibility(Button.GONE);
			Button_next.setTextColor(getResources().getColor(R.color.accent1));
		}
	}

	private void openContactsTab(boolean isContactsValid, boolean isItemsValid, boolean isDeliveryValid)
	{
		TextView_contactTabCircle.setText("1");
		TextView_itemsTabCircle.setText("2");
		TextView_deliveryTabCircle.setText("3");

		if(layoutType==LayoutType.LOGGEDIN_LAYOUT)
		{
			currentTabPosition = 0;
			LinearLayout_state1spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent2));
			LinearLayout_state1pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state1pointer.setImageResource(R.drawable.addrfq_yellow_arrow);
			LinearLayout_state2spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state2pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state2pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state3spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state3pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state3pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_blue_circle);
			TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_deliveryTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);

			if(isContactsValid)
			{
				TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_contactTabCircle.setText("");
			}

			if(isItemsValid)
			{
				TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_itemsTabCircle.setText("");
			}
		}
		else
		{
			currentTabPosition = 2;
			LinearLayout_state1spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state1pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state1pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state2spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state2pointerParent.setBackgroundColor(getResources().getColor(R.color.accent2));
			ImageView_state2pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state3spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent2));
			LinearLayout_state3pointerParent.setBackgroundColor(getResources().getColor(R.color.accent2));
			ImageView_state3pointer.setImageResource(R.drawable.addrfq_yellow_arrow);
			TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_deliveryTabCircle.setBackgroundResource(R.drawable.addrfq_blue_circle);

			if(isItemsValid)
			{
				TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_contactTabCircle.setText("");
			}
			if(isDeliveryValid)
			{
				TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_itemsTabCircle.setText("");
			}
		}
		view_flipper.setDisplayedChild(currentTabPosition);
		resetNextPreviousButtonStates();
	}
	private void openItemsTab(boolean isContactsValid, boolean isItemsValid, boolean isDeliveryValid)
	{
		TextView_contactTabCircle.setText("1");
		TextView_itemsTabCircle.setText("2");
		TextView_deliveryTabCircle.setText("3");

		if(layoutType==LayoutType.LOGGEDIN_LAYOUT)
		{
			currentTabPosition = 1;
			LinearLayout_state1spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state1pointerParent.setBackgroundColor(getResources().getColor(R.color.accent2));
			ImageView_state1pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state2spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent2));
			LinearLayout_state2pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state2pointer.setImageResource(R.drawable.addrfq_yellow_arrow);
			LinearLayout_state3spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state3pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state3pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_blue_circle);
			TextView_deliveryTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);

			if(isContactsValid)
			{
				TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_contactTabCircle.setText("");
			}
		}
		else
		{
			currentTabPosition = 0;
			LinearLayout_state1spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent2));
			LinearLayout_state1pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state1pointer.setImageResource(R.drawable.addrfq_yellow_arrow);
			LinearLayout_state2spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state2pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state2pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state3spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state3pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state3pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_blue_circle);
			TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_deliveryTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);

			if(isItemsValid)
			{
				TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_contactTabCircle.setText("");
			}
		}
		view_flipper.setDisplayedChild(currentTabPosition);
		resetNextPreviousButtonStates();
	}

	private void openDeliveryTab(boolean isContactsValid, boolean isItemsValid, boolean isDeliveryValid)
	{
		TextView_contactTabCircle.setText("1");
		TextView_itemsTabCircle.setText("2");
		TextView_deliveryTabCircle.setText("3");

		if(layoutType==LayoutType.LOGGEDIN_LAYOUT)
		{
			currentTabPosition = 2;
			LinearLayout_state1spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state1pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state1pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state2spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state2pointerParent.setBackgroundColor(getResources().getColor(R.color.accent2));
			ImageView_state2pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state3spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent2));
			LinearLayout_state3pointerParent.setBackgroundColor(getResources().getColor(R.color.accent2));
			ImageView_state3pointer.setImageResource(R.drawable.addrfq_yellow_arrow);
			TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_deliveryTabCircle.setBackgroundResource(R.drawable.addrfq_blue_circle);

			if(isContactsValid)
			{
				TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_contactTabCircle.setText("");
			}

			if(isItemsValid)
			{
				TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_itemsTabCircle.setText("");
			}
		}
		else
		{
			currentTabPosition = 1;
			LinearLayout_state1spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state1pointerParent.setBackgroundColor(getResources().getColor(R.color.accent2));
			ImageView_state1pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			LinearLayout_state2spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent2));
			LinearLayout_state2pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state2pointer.setImageResource(R.drawable.addrfq_yellow_arrow);
			LinearLayout_state3spaceHolder.setBackgroundColor(getResources().getColor(R.color.accent1));
			LinearLayout_state3pointerParent.setBackgroundColor(getResources().getColor(R.color.accent1));
			ImageView_state3pointer.setImageResource(R.drawable.addrfq_blue_arrow);
			TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);
			TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_blue_circle);
			TextView_deliveryTabCircle.setBackgroundResource(R.drawable.addrfq_yellow_circle);

			if(isItemsValid)
			{
				TextView_contactTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_contactTabCircle.setText("");
			}
			if(isDeliveryValid)
			{
				TextView_itemsTabCircle.setBackgroundResource(R.drawable.addrfq_green_circle);
				TextView_itemsTabCircle.setText("");
			}
		}
		view_flipper.setDisplayedChild(currentTabPosition);
		resetNextPreviousButtonStates();
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

        onSaveViewState();
		Bundle bundle = getFragmentDataBundle();
        if(bundle==null)
        {
            bundle=new Bundle();
            setFragmentDataBundle(bundle);
        }

		Button_submit.setEnabled(true);
		Button_submit.setClickable(true);

		if(uiMessage.getUiMessageType()==UIMessageType.UNAUTHORIZE)
		{
			bundle.putBoolean("SaveDataFlag", true);
			bundle.putBoolean("RepeatLastActionFlag", true);
			bundle.putString(Constants.BUNDLE_KEY_LOGIN_SOURCE, LoginSources.ADD_RFQ.toString());
			Utils.showSessionInvalidateDialog(baseActivity, bundle);
            return;
		}

        if(uiMessage.getUiMessageType()==UIMessageType.EMAIL_ALREADY_EXIST)
        {
			bundle.putString("Populate_Email", EditText_email.getText().toString());
            bundle.putBoolean("SaveDataFlag", true);
            bundle.putBoolean("RepeatLastActionFlag", true);
            bundle.putString(Constants.BUNDLE_KEY_LOGIN_SOURCE, LoginSources.ADD_RFQ.toString());
			Utils.showEmailAlreadyExistDialog(baseActivity, bundle);
            return;
        }

		if(!showErrorMessageFragment(uiMessage.getUiMessageType(), AddRFQFragment.class, bundle))
		{
			if(
					(uiMessage.getUiMessageType()==UIMessageType.SUCCESS
					|| uiMessage.getUiMessageType()==UIMessageType.DIALOG_OK_BACK
					|| uiMessage.getUiMessageType()==UIMessageType.DIALOG_OK)
					)
			{
				if(userLoginStatus == LoginStatus.NOT_LOGGED_IN)
				{
					((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackActionEvent("Login", "Success", LoginType.RFQ_CREATION + "", 1);
				}

                if(flag==Constants.FLAG_ADD_RFQ)
                {
					navigateToMyRFQs(null, uiMessage.getMessage());
                }
                else if(flag==Constants.FLAG_PO_UPLOAD)
                {
                    UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
//                    baseActivity.onBackPressed();
                }
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
						Button_submit.setEnabled(true);
						Button_submit.setClickable(true);
						break;
					}
                    case Constants.FLAG_PO_UPLOAD:
                    {
						if(userLoginStatus == LoginStatus.NOT_LOGGED_IN)
						{
							((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackActionEvent("Login", "Success", LoginType.RFQ_CREATION+"", 1);
							Editor editor = prefs.edit();
							editor.putString(Constants.PREFERENCE_CUSTOMER_COMPANYNAME, EditText_companyName.getText().toString().trim());
							editor.putString(Constants.PREFERENCE_CUSTOMER_FULLNAME, EditText_fullName.getText().toString().trim());
							editor.putString(Constants.PREFERENCE_CUSTOMER_MOBILENUMBER, EditText_mobileNumber.getText().toString().trim());
							editor.putString(Constants.PREFERENCE_CUSTOMER_EMAIL, EditText_email.getText().toString().trim());
							editor.commit();
							baseActivity.setIntent(new Intent(baseActivity, ContainerActivity.class));
//							baseActivity.doDrawerMenuSettings(null);
							baseActivity.openChildActivityFragment(HomeFragment.class, null, false, true, true);
						}
                        baseActivity.onBackPressed();
                    }
				}
                UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
			}

		}
	}

	@Override
	public void showDeliveryAddressList(List<DeliveryAddress> deliveryAddressList)
	{
		Button_selectDeliveryAddress = (Button)rootView.findViewById(R.id.Button_selectDeliveryAddress);
		this.deliveryAddressList=deliveryAddressList;
        if(deliveryAddressList!=null && deliveryAddressList.size()>0)
		    showSelectDeliveryAddressDialog(baseActivity, "Select delivery address:", deliveryAddressList);
	}



	@Override
	public void showProgress(BetterSpinner spinner, ProgressBar progressBar,ProgressTypes progressTypes, int flag)
	{
		progressBar.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	public void hideProgress(BetterSpinner spinner, ProgressBar progressBar,ProgressTypes progressTypes, int flag)
	{
		progressBar.setVisibility(ProgressBar.GONE);
	}

	@Override
	public void showUIMessage(BetterSpinner spinner, ProgressBar progressBar, UIMessage uiMessage, int flag)
	{
		if(!isAdded())
		{
			return;
		}
		if(uiMessage.getUiMessageType()==UIMessageType.UNAUTHORIZE)
		{
			Utils.showSessionInvalidateDialog(baseActivity, null);
		}
	}

	public void onSaveViewState()
	{
		Bundle outState = getFragmentDataBundle();
		if(outState==null)
		{
			outState=new Bundle();
			setFragmentDataBundle(outState);
		}


        outState.putInt("newRFQTypes", newRFQTypes.getValue());
		outState.putBoolean("DataSaved", true);
		outState.putParcelable("newRFQ", newRFQ);

		//contact section saved
		outState.putString("EditText_companyName", EditText_companyName.getText().toString());
		outState.putString("EditText_fullName", EditText_fullName.getText().toString());
		outState.putString("EditText_mobileNumber", EditText_mobileNumber.getText().toString());
		outState.putString("EditText_email", EditText_email.getText().toString());
		//items section saved
		outState.putInt("ItemsCount", LinearLayout_rfqItemsParent.getChildCount());
		for(int i=0;i<LinearLayout_rfqItemsParent.getChildCount();i++)
		{
			View rfqItem = LinearLayout_rfqItemsParent.getChildAt(i);
			BetterSpinner Spinner_material = (BetterSpinner)rfqItem.findViewById(R.id.Spinner_material);
			BetterSpinner Spinner_materialSubCategory = (BetterSpinner)rfqItem.findViewById(R.id.Spinner_materialSubCategory);
			AutoCompleteTextView AutoCompleteTextView_materialSpecification = (AutoCompleteTextView)rfqItem.findViewById(R.id.AutoCompleteTextView_materialSpecification);
			EditText EditText_quantity = (EditText)rfqItem.findViewById(R.id.EditText_quantity);
			BetterSpinner Spinner_unitOfMeasure = (BetterSpinner)rfqItem.findViewById(R.id.Spinner_unitOfMeasure);

			outState.putString("Spinner_material" + i, ((String) Spinner_material.getText().toString()));
			outState.putString("Spinner_materialSubCategory" + i, ((String) Spinner_materialSubCategory.getText().toString()));
			outState.putString("EditText_materialSpecification"+i , AutoCompleteTextView_materialSpecification.getText().toString());

			Object skuObj = AutoCompleteTextView_materialSpecification.getTag();
			if(skuObj!=null && skuObj instanceof SKU_v3)// if user has selected SKU from type ahead
			{
				SKU_v3 selectedSKU = (SKU_v3)skuObj;
				outState.putString("EditText_materialSpecification_skuCode"+i , selectedSKU.getSkucode());
			}
			else
			{
				outState.putString("EditText_materialSpecification_skuCode"+i , "");
			}


			outState.putString("EditText_quantity"+i , EditText_quantity.getText().toString());
			outState.putParcelable("Spinner_unitOfMeasure"+i , ((UnitOfMeasure)Spinner_unitOfMeasure.getTag()));
		}
		//delivery address section saved
		if(Spinner_taxation!=null)
			outState.putParcelable("Spinner_taxation" , ((TaxationPreference_v3)Spinner_taxation.getTag()));
		if(Spinner_urgency!=null)
			outState.putParcelable("Spinner_urgency" , ((Urgency_v3)Spinner_urgency.getTag()));

        if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
        {
            TextView TextView_firstAddress = (TextView) rootView.findViewById(R.id.TextView_firstAddress);
            TextView TextView_secondAddress = (TextView) rootView.findViewById(R.id.TextView_secondAddress);
            TextView TextView_cityPostCode = (TextView) rootView.findViewById(R.id.TextView_cityPostCode);

            outState.putString("TextView_firstAddress" , TextView_firstAddress.getText().toString());
            outState.putString("TextView_secondAddress" , TextView_secondAddress.getText().toString());
            outState.putString("TextView_cityPostCode" , TextView_cityPostCode.getText().toString());

            outState.putString("Button_selectDeliveryAddress" , Button_selectDeliveryAddress.getText().toString());
            TextView TextView_selectDeliveryAddressLabel = (TextView) rootView.findViewById(R.id.TextView_selectDeliveryAddressLabel);
            outState.putString("TextView_selectDeliveryAddressLabel" , TextView_selectDeliveryAddressLabel.getText().toString());

            LinearLayout selectedDeliveryAddressLL = (LinearLayout) rootView.findViewById(R.id.addressTitleParent);
            outState.putInt("selectedDeliveryAddressLL" , selectedDeliveryAddressLL.getVisibility());
        }
        else
        {
            outState.putString("EditText_deliveryAdress" , EditText_deliveryAdress.getText().toString());
            outState.putString("EditText_deliveryAdress2" , EditText_deliveryAdress2.getText().toString());
            outState.putString("EditText_deliveryPostCode" , EditText_deliveryPostCode.getText().toString());
        }
        outState.putInt("LastSelectedTabPosition", currentTabPosition);

        if(newRFQTypes == NewRFQTypes.DEALS)
        {
            outState.putParcelable("selectedDeal", selectedDeal);
        }
	}
	private void initUIDataFromSavedViewState()
	{
		userLoginStatus = myAccountApplication.getLoginStatus();
		Bundle bundle = getFragmentDataBundle();
		if(bundle==null || !bundle.getBoolean("DataSaved", false))
		{
			return;
		}
//
//		Utils.openFragment(baseActivity, AddRFQFragment.class, bundle, false, true, true);

        switch(bundle.getInt("newRFQTypes", 0))
        {
            case 1:
                newRFQTypes = NewRFQTypes.iRFQ;
                break;
            case 2:
                newRFQTypes = NewRFQTypes.DEALS;
                break;
            case 3:
                newRFQTypes = NewRFQTypes.REORDER;
                break;
            default:
                newRFQTypes = NewRFQTypes.iRFQ;
        }



//		iDeliveryAddressesListPresentor.getDeliveryAddressList(smeId);

		newRFQ = bundle.getParcelable("newRFQ");

		String EditText_companyNameVal = bundle.getString("EditText_companyName");
		EditText_companyName.setText(EditText_companyNameVal!=null?EditText_companyNameVal:"");

		String EditText_firstNameVal = bundle.getString("EditText_fullName");
		EditText_fullName.setText(EditText_firstNameVal!=null?EditText_firstNameVal:"");

		String EditText_mobileNumberVal = bundle.getString("EditText_mobileNumber");
		EditText_mobileNumber.setText(EditText_mobileNumberVal!=null?EditText_mobileNumberVal:"");

		String EditText_emailVal = bundle.getString("EditText_email");
		EditText_email.setText(EditText_emailVal!=null?EditText_emailVal:"");

		//items section saved
		int itemCount = bundle.getInt("ItemsCount");
//		LinearLayout_rfqItemsParent
		if(itemCount!=0)
		{
			LinearLayout_rfqItemsParent.removeAllViews();
		}
		for(int i=0;i<itemCount;i++)
		{
			String materialCategory = bundle.getString("Spinner_material" + i);
			String materialSubCategory = bundle.getString("Spinner_materialSubCategory" + i);
			String EditText_materialSpecificationVal = bundle.getString("EditText_materialSpecification"+i);

			String EditText_materialSpecification_skuCode = bundle.getString("EditText_materialSpecification_skuCode"+i);
			SKU_v3 sku_v3=new SKU_v3();
			sku_v3.setSkucode(EditText_materialSpecification_skuCode);
			sku_v3.setCategory(materialCategory);
			sku_v3.setSubcategory(materialSubCategory);


			String EditText_quantityVal = bundle.getString("EditText_quantity"+i);
			UnitOfMeasure nnitOfMeasure = bundle.getParcelable("Spinner_unitOfMeasure"+i);

			addRFQItemInItemsList(true, materialCategory, materialSubCategory, nnitOfMeasure, EditText_materialSpecificationVal,sku_v3, EditText_quantityVal);
		}
		//delivery address section saved
		Spinner_taxation = (BetterSpinner) rootView.findViewById(R.id.Spinner_taxation);

		Spinner_urgency = (BetterSpinner) rootView.findViewById(R.id.Spinner_urgency);

		ProgressBar_urgencyLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_urgencyLoader);
		ProgressBar_taxationLoader = (ProgressBar) rootView.findViewById(R.id.ProgressBar_taxationLoader);

		TaxationPreference_v3 taxationPref = bundle.getParcelable("Spinner_taxation");
		if(taxationPref!=null)
		{
			Spinner_taxation.setText(taxationPref.getValue());
			Spinner_taxation.setTag(taxationPref);
		}

		Urgency_v3 urgency = bundle.getParcelable("Spinner_urgency");
		if(urgency!=null)
		{
			Spinner_urgency.setText(urgency.getValue());
			Spinner_urgency.setTag(urgency);
		}


        if(userLoginStatus != LoginStatus.NOT_LOGGED_IN)
        {
			if(DeliveryAddressesInsertFragment.isDeliveryAddressAdded && DeliveryAddressesInsertFragment.srcDeliveryAddress!=null)
			{
				initSelectedDeliveryAddressUI(DeliveryAddressesInsertFragment.srcDeliveryAddress);
				DeliveryAddressesInsertFragment.isDeliveryAddressAdded=false;
				deliveryAddressList=null;
			}
			else
			{
				LL2.setVisibility(LinearLayout.VISIBLE);
				LL1.setVisibility(LinearLayout.GONE);

				TextView TextView_firstAddress = (TextView) rootView.findViewById(R.id.TextView_firstAddress);
				TextView TextView_secondAddress = (TextView) rootView.findViewById(R.id.TextView_secondAddress);
				TextView TextView_cityPostCode = (TextView) rootView.findViewById(R.id.TextView_cityPostCode);


				if(bundle.getString("TextView_firstAddress")!=null)
				{
					TextView_firstAddress.setText(bundle.getString("TextView_firstAddress"));
				}
				if(bundle.getString("TextView_secondAddress")!=null)
				{
					TextView_secondAddress.setText(bundle.getString("TextView_secondAddress"));
				}
				if(bundle.getString("TextView_cityPostCode")!=null)
				{
					TextView_cityPostCode.setText(bundle.getString("TextView_cityPostCode"));
				}


				Button_selectDeliveryAddress = (Button)rootView.findViewById(R.id.Button_selectDeliveryAddress);
				Button_selectDeliveryAddress.setText(bundle.getString("Button_selectDeliveryAddress")==null?getString(R.string.addrfq_button_select_address):bundle.getString("Button_selectDeliveryAddress") );

				TextView TextView_selectDeliveryAddressLabel = (TextView) rootView.findViewById(R.id.TextView_selectDeliveryAddressLabel);
				TextView_selectDeliveryAddressLabel.setText(bundle.getString("TextView_selectDeliveryAddressLabel"));

				LinearLayout selectedDeliveryAddressLL = (LinearLayout) rootView.findViewById(R.id.addressTitleParent);
				int selectedDeliveryAddressLLVisibility = bundle.getInt("selectedDeliveryAddressLL", LinearLayout.GONE);
				selectedDeliveryAddressLL.setVisibility(selectedDeliveryAddressLLVisibility==LinearLayout.GONE?LinearLayout.GONE:LinearLayout.VISIBLE);
			}
		}
        else
        {
			LL2.setVisibility(LinearLayout.GONE);
			LL1.setVisibility(LinearLayout.VISIBLE);

            EditText_deliveryAdress.setText(bundle.getString("EditText_deliveryAdress"));
            EditText_deliveryAdress2.setText(bundle.getString("EditText_deliveryAdress2"));
            EditText_deliveryPostCode.setText(bundle.getString("EditText_deliveryPostCode"));
        }

		bundle.putBoolean("DataSaved", false);

		currentTabPosition = bundle.getInt("LastSelectedTabPosition", 0);

        if(newRFQTypes == NewRFQTypes.DEALS)
        {
            selectedDeal = bundle.getParcelable("selectedDeal");
        }
		openRFQSection(RFQSection.SECTION_BASEDON_POSITION, currentTabPosition, false);
	}
	@Override
	public void postOrder(RequestAddCampaignDto campaign) {}
	@Override
	public void showDeal(Deal_v3 deal)
	{
		if(deal!=null)
		{
			selectedDeal = deal;
			getFragmentDataBundle().putParcelable(Constants.BUNDLE_KEY_SELECTED_DEAL, deal);
			String category="";
			String subCategory="";

			SKU_v3 sku = deal.getSku();
			if(sku!=null)
			{
				category = sku.getCategory();
				subCategory = sku.getSubcategory();
			}
			else
			{
				category = deal.getCategoryName();
				subCategory = "";
			}

			UnitOfMeasure unitSelector=new UnitOfMeasure();
			unitSelector.setUnitOfMeasureName(deal.getUom());
			unitSelector.setUnitOfMeasureID(deal.getUom());


			String spec=deal.getProduct() +" ("+baseActivity.getString(R.string.inr)+deal.getPriceSpace().get(0).getRatevisibletocustomer()+"/"+deal.getUom()+")";
			String quantity="";
			addRFQItemInItemsList(true,category, subCategory, unitSelector, spec, sku, quantity);

			addDealsDetailsPanel(selectedDeal);
		}
	}

	public void showSelectDeliveryAddressDialog(Context context,final String title, final List<DeliveryAddress> deliveryAddressList)
	{
        final Dialog dialog;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.delivery_address_selection_dialog);
        dialog.setTitle("Select delivery address:");
        final ListView lst = (ListView) dialog.findViewById(R.id.ListView_deliveryAddresses);

        final DeliveryAddressListAdapter adapter=new DeliveryAddressListAdapter(context, deliveryAddressList, true);

        if(selectedDeliveryAddress!=null && selectedDeliveryAddress.getSelectedPosition()!=-1)
        {
        	adapter.setSelection(selectedDeliveryAddress.getSelectedPosition(), true);
        }

        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> arg0, View arg1, int selectedPosition, long arg3)
        	{
        		selectedDeliveryAddress = deliveryAddressList.get(selectedPosition);
        		selectedDeliveryAddress.setSelectedPosition(selectedPosition);
        		initSelectedDeliveryAddressUI(selectedDeliveryAddress);
        		adapter.setSelection(selectedPosition, true);
        		adapter.notifyDataSetChanged();
        		dialog.dismiss();
        	}
        });
		dialog.show();
	}

	private void initSelectedDeliveryAddressUI(DeliveryAddress selDeliveryAddress)
	{
		selectedDeliveryAddress = selDeliveryAddress;
		LinearLayout selectedDeliveryAddressLL = (LinearLayout) rootView.findViewById(R.id.addressTitleParent);
		selectedDeliveryAddressLL.setVisibility(LinearLayout.VISIBLE);

		TextView TextView_firstAddress = (TextView) rootView.findViewById(R.id.TextView_firstAddress);
		TextView TextView_secondAddress = (TextView) rootView.findViewById(R.id.TextView_secondAddress);
		TextView TextView_cityPostCode = (TextView) rootView.findViewById(R.id.TextView_cityPostCode);

		TextView_firstAddress.setText(selDeliveryAddress.getSmeAddress());
		TextView_secondAddress.setText(selDeliveryAddress.getSmeAddress2());
		TextView_cityPostCode.setText(selDeliveryAddress.getSmeCity()+", "+selDeliveryAddress.getSmePostCode());

        Button_selectDeliveryAddress.setText(getString(R.string.addrfq_button_change_address));

        TextView TextView_selectDeliveryAddressLabel = (TextView) rootView.findViewById(R.id.TextView_selectDeliveryAddressLabel);
        TextView_selectDeliveryAddressLabel.setText(getString(R.string.addrfq_label_selected_delivery_address));
	}

    private void addDealsDetailsPanel(Deal_v3 deal)
    {
        View view = LayoutInflater.from(baseActivity).inflate(R.layout.deals_list_item, null);
        ((LinearLayout)view.findViewById(R.id.LinearLayout_dealsActionPanel)).setVisibility(LinearLayout.GONE);
        TextView TextView_DealIdLabel=(TextView)view.findViewById(R.id.TextView_DealIdLabel);
        LinearLayout LinearLayout_ProductParent=(LinearLayout)view.findViewById(R.id.LinearLayout_ProductParent);
        LinearLayout LinearLayout_ratesContainer=(LinearLayout)view.findViewById(R.id.LinearLayout_ratesContainer);
        TextView TextView_rateLabel=(TextView)view.findViewById(R.id.TextView_rateLabel);

        TextView_DealIdLabel.setText(baseActivity.getString(R.string.addrfq_label_deals_description_title));
        TextView_DealIdLabel.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_info_details, 0, 0, 0);

        LinearLayout_ProductParent.setVisibility(TextView.GONE);

        if(LinearLayout_ratesContainer!=null)
        {
            LinearLayout_ratesContainer.removeAllViews();
        }

        if(deal.getPriceSpace()!=null && deal.getPriceSpace().size()>0)
        {
            TextView_rateLabel.setText(baseActivity.getString(R.string.deals_label_rate)+"/"+deal.getUom()+": ");
            List<DealItemPrice> dealItemPriceList = deal.getPriceSpace();
            for(int i=0; i<dealItemPriceList.size();i++)
            {
                DealItemPrice dealItemPrice = dealItemPriceList.get(i);

                View dealsRatesItem = LayoutInflater.from(baseActivity).inflate(R.layout.deals_rates_item, null);
                TextView TextView_rate=(TextView)dealsRatesItem.findViewById(R.id.TextView_rate);
                TextView TextView_minQtyMsg=(TextView)dealsRatesItem.findViewById(R.id.TextView_minQtyMsg);

                TextView_rate.setText(baseActivity.getString(R.string.inr)+ Utils.getCommaSeparatedNumber(dealItemPrice.getRatevisibletocustomer()));
                TextView_minQtyMsg.setText(" for "+dealItemPrice.getMinqty()+" "+deal.getUom()+" to "+dealItemPrice.getMaxqty()+" "+deal.getUom());
                LinearLayout_ratesContainer.addView(dealsRatesItem);
            }
        }

        View locationView = LayoutInflater.from(baseActivity).inflate(R.layout.deals_rates_item, null);
        ((TextView)locationView.findViewById(R.id.TextView_minQtyMsg)).setText("Ex- "+deal.getLocationValue());
        LinearLayout_ratesContainer.addView(locationView);

        View remarksView = LayoutInflater.from(baseActivity).inflate(R.layout.deals_rates_item, null);
        TextView remarksTextView = ((TextView)remarksView.findViewById(R.id.TextView_minQtyMsg));
        remarksTextView.setText(deal.getRemarks());
        LinearLayout_ratesContainer.addView(remarksView);

        LinearLayout LinearLayout_dealDetailsParent = (LinearLayout)  rootView.findViewById(R.id.LinearLayout_dealDetailsParent);
        LinearLayout_dealDetailsParent.setVisibility(LinearLayout.VISIBLE);
        LinearLayout_dealDetailsParent.addView(view);
    }

	@Override
	public void navigateToMyRFQs(final String rfqNo, final String message)
	{
		new AlertDialog.Builder(getActivity())
				.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.dismiss();
						Intent intent=new Intent(getActivity(), ChildActivity.class);
						intent.putExtra(Constants.BUNDLE_KEY_RFQ_NO, rfqNo);
						intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_myrfq);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivityForResult(intent, ContainerActivity.CHILD_ACTIVITY_REQUEST_CODE);
					}
				})
				.show();
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if(resultCode==Activity.RESULT_CANCELED)
		{
			if(requestCode==MediaHelper.REQUEST_CODE_IMAGE_CAPTURE || requestCode==MediaHelper.REQUEST_CODE_CHOOSE_FILE)
			{
				baseActivity.onBackPressed();
			}
		}
    }

	////////////////////////////////////////////////////

	@Override
	public void showItemCategoryList(final BetterSpinner spinner, ProgressBar progressBar, List<String> categories)
	{
		SpinnerHintAdapter<String> adapter = new SpinnerHintAdapter<String>(baseActivity, categories);
		spinner.setAdapter(adapter);
	}

	@Override
	public void showItemSubCategoryList(final BetterSpinner spinner, ProgressBar progressBar, String category, List<String> subCategories)
	{
		if(subCategories==null || subCategories.size()==0)
		{
			spinner.setVisibility(BetterSpinner.GONE);
			progressBar.setVisibility(ProgressBar.GONE);
			return;
		}
		else
		{
			spinner.setVisibility(BetterSpinner.VISIBLE);
		}

		SpinnerHintAdapter<String> adapter = new SpinnerHintAdapter<String>(baseActivity, subCategories);
		spinner.setAdapter(adapter);
	}

	@Override
	public void showUrgencyList(final BetterSpinner spinner, ProgressBar progressBar, List<Urgency_v3> urgencyList)
	{
		SpinnerHintAdapter<Urgency_v3> adapter = new SpinnerHintAdapter<Urgency_v3>(baseActivity, urgencyList);
		spinner.setAdapter(adapter);
	}

	public void showTaxationPrefsList(final BetterSpinner spinner, ProgressBar progressBar, List<TaxationPreference_v3> taxationPrefsList)
	{
		SpinnerHintAdapter<TaxationPreference_v3> adapter = new SpinnerHintAdapter<TaxationPreference_v3>(baseActivity, taxationPrefsList);
		spinner.setAdapter(adapter);
	}

	@Override
	public void showUnitsList(String category, final BetterSpinner spinner, ProgressBar progressBar, List<UnitOfMeasure> unitsList)
	{
		SpinnerHintAdapter<UnitOfMeasure> adapter = new SpinnerHintAdapter<UnitOfMeasure>(baseActivity, unitsList);
		spinner.setAdapter(adapter);
	}

	//////////////////////////////////////////////////////////////
	///////////////////// DYNAMIC PERMISSION /////////////////////
	//////////////////////////////////////////////////////////////
	Snackbar permissionSnackbar;

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
		}
		else
		{
			if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.GET_ACCOUNTS))
			{
				permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.addrfq_emailsuggession_account_permission_retry_msg));
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
		switch (requestCode)
		{
			case Constants.PERMISSION_REQUEST_READ_ACCOUNTS:
			{
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					addEmailSuggesionAdapter(false);
				}
				return;
			}
		}
	}
}
