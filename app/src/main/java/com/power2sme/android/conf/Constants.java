package com.power2sme.android.conf;

public class Constants
{
	public static final long CACHE_EXPIRY_TIME = 1000*60*60*24;//24 hrs
	public static final int SERVER_ERROR_FRAGMENT_CODE = 1001;
	public static final int NETWORK_ERROR_FRAGMENT_CODE = 1002;
	public static final int CUSTOMER_SEARCH_RESULT_FRAGMENT_CODE = 1003;
	public static final String PREFERENCE_APP_VERSION = "PREFERENCE_APP_VERSION";
	public static final String PREFERENCE_IS_REMEMBERED_CREDENTIAL = "PREFERENCE_IS_REMEMBERED_CREDENTIAL";
	public static final String PREFERENCE_REMEMBERED_USERNAME = "PREFERENCE_REMEMBERED_USERNAME";
	public static final String PREFERENCE_REMEMBERED_PASSWORD = "PREFERENCE_REMEMBERED_PASSWORD";
	public static final String PREFERENCE_LAST_UPDATE_CHECK_TIME = "PREFERENCE_LAST_UPDATE_CHECK_TIME";
	public static final String PREFERENCE_SERVER_PREFIX = "PREFERENCE_SERVER_PREFIX";
	public static final String PREFERENCE_SERVER_APP_MAX_VERSION = "PREFERENCE_SERVER_APP_MAX_VERSION";
	public static final String PREFERENCE_SERVER_APP_MIN_VERSION = "PREFERENCE_SERVER_APP_MIN_VERSION";
	public static final String PREFERENCE_BUILD_TYPE = "PREFERENCE_BUILD_TYPE";
	public static final String PREFERENCE_IS_KAM = "PREFERENCE_IS_KAM";
	public static final String PREFERENCE_FARMER_FULLNAME = "PREFERENCE_FARMER_FULLNAME";
    public static final String PREFERENCE_FARMER_EMAIL = "PREFERENCE_FARMER_EMAIL";
	public static final String PREFERENCE_FARMER_MOBILE = "PREFERENCE_FARMER_MOBILE";
	public static final String PREFERENCE_KAM_FULLNAME = "PREFERENCE_KAM_FULLNAME";
	public static final String PREFERENCE_KAM_EMAIL_ID = "PREFERENCE_KAM_EMAIL_ID";
	public static final String PREFERENCE_KAM_MOBILE = "PREFERENCE_KAM_MOBILE";
	public static final String PREFERENCE_CUSTOMER_COMPANYNAME = "PREFERENCE_CUSTOMER_COMPANYNAME";
	public static final String PREFERENCE_CUSTOMER_SMEID = "PREFERENCE_CUSTOMER_SMEID";
	public static final String PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP = "PREFERENCE_CUSTOMER_ISCOMPANYREGISTEREDINERP";
	public static final String PREFERENCE_CUSTOMER_EMAIL = "PREFERENCE_CUSTOMER_EMAIL";
	public static final String PREFERENCE_CUSTOMER_FULLNAME = "PREFERENCE_CUSTOMER_FULLNAME";
	public static final String PREFERENCE_CUSTOMER_MOBILENUMBER = "PREFERENCE_CUSTOMER_MOBILENUMBER";
	public static final String PREFERENCE_AGENT_USERNAME = "PREFERENCE_AGENT_USERNAME";
	public static final String PREFERENCE_AGENT_FULLNAME = "PREFERENCE_AGENT_FULLNAME";
	public static final String PREFERENCE_AGENT_EMAIL_ID = "PREFERENCE_AGENT_EMAIL_ID";
	public static final String PREFERENCE_AGENT_MOBILE = "PREFERENCE_AGENT_MOBILE";
	public static final String PREFERENCE_SELECTED_SKU = "PREFERENCE_SELECTED_SKU";
	public static final String DLG_OPTIONS_FILE_FROM_CAMERA="Take Picture From Camera";
	public static final String DLG_OPTIONS_FILE_FROM_PICKER="Pick Document From SDCard";
	public static final String DLG_OPTIONS_CANCEL="Upload PO Later";
	public static final String BUNDLE_KEY_SELECTED_DEAL="KEY_SELECTED_DEAL";
	public static final String BUNDLE_KEY_SELECTED_DELIVERY_ADDRESS="BUNDLE_KEY_SELECTED_DELIVERY_ADDRESS";
	public static final String BUNDLE_KEY_DEAL_ID="BUNDLE_KEY_DEAL_ID";
	public static final String BUNDLE_KEY_EDIT_DELIVERYADDRESS="BUNDLE_KEY_EDIT_DELIVERYADDRESS";
	public static final String BUNDLE_KEY_PROFILE_GENERAL_INFORMATION="BUNDLE_KEY_PROFILE_GENERAL_INFORMATION";
	public static final String BUNDLE_KEY_EDITED_CONTACT="BUNDLE_KEY_EDITED_CONTACT";
	public static final String BUNDLE_KEY_SELECTED_CONTACT="BUNDLE_KEY_EDITED_CONTACT";
	public static final String BUNDLE_KEY_REORDER_ITEM="BUNDLE_KEY_REORDER_ITEM";
	public static final String BUNDLE_KEY_WEB_CONTENT="BUNDLE_KEY_WEB_CONTENT";
	public static final String BUNDLE_KEY_ORDER_ID = "BUNDLE_KEY_ORDER_ID";
	public static final String BUNDLE_KEY_RFQ_ID = "BUNDLE_KEY_RFQ_ID";
	public static final String BUNDLE_KEY_RFQ_NO = "BUNDLE_KEY_RFQ_NO";
	public static final String BUNDLE_KEY_SALESORDER = "BUNDLE_KEY_SALESORDER";
	public static final String BUNDLE_KEY_LOGIN_SOURCE = "BUNDLE_KEY_LOGIN_SOURCE";
	public static final String BUNDLE_KEY_INTERNET_STATUS = "BUNDLE_KEY_INTERNET_STATUS";
	public static final String BUNDLE_KEY_TARGET_FRAGMENT_NAME = "BUNDLE_KEY_TARGET_FRAGMENT_NAME";
	public static final String BUNDLE_KEY_TARGET_FRAGMENT_MESSAGE = "BUNDLE_KEY_TARGET_FRAGMENT_MESSAGE";
	public static final String BUNDLE_KEY_IS_ADD_TO_BACKSTACK = "BUNDLE_KEY_IS_ADD_TO_BACKSTACK";
	public static final String BUNDLE_KEY_IS_CHECK_NETWORK = "BUNDLE_KEY_IS_CHECK_NETWORK";
	public static final String BUNDLE_KEY_IS_CHECK_UPDATE = "BUNDLE_KEY_IS_CHECK_UPDATE";
	public static final String BUNDLE_KEY_IS_CLEAR_BACKSTACK = "BUNDLE_KEY_IS_CLEAR_BACKSTACK";
	public static final String BUNDLE_KEY_RFQ_FROM_MENU = "BUNDLE_KEY_RFQ_FROM_MENU";
	public static final String BUNDLE_KEY_REPEATE_ACTION_PARCEL = "BUNDLE_KEY_REPEATE_ACTION_PARCEL";
	public static final String BUNDLE_KEY_REPEATE_ACTION_FRAGMENT = "BUNDLE_KEY_REPEATE_ACTION_FRAGMENT";
	public static final String BUNDLE_KEY_LOGIN_REPONSE_OBJECT = "BUNDLE_KEY_LOGIN_REPONSE_OBJECT";
    public static final String BUNDLE_KEY_TRACKING_ORDER = "BUNDLE_KEY_TRACKING_ORDER";
	public static final String BUNDLE_KEY_TRACKING_DEVICE_ID = "BUNDLE_KEY_TRACKING_DEVICE_ID";
	public static final String BUNDLE_KEY_MENU_ID = "BUNDLE_KEY_MENU_ID";
	public static final String BUNDLE_KEY_REFRESH_DRAWER_MENU = "BUNDLE_KEY_REFRESH_DRAWER_MENU";
	public static final String BUNDLE_KEY_EMAIL = "BUNDLE_KEY_EMAIL";
	public static final String BUNDLE_KEY_SALESORDER_DATE = "BUNDLE_KEY_SALESORDER_DATE";
	public static final String BUNDLE_KEY_SEARCHED_CUSTOMERS = "BUNDLE_KEY_SEARCHED_CUSTOMERS";
	public static final String BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE = "BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE";
	public static final String BUNDLE_KEY_KAM_FILTER = "BUNDLE_KEY_KAM_FILTER";
	public static final String BUNDLE_KEY_KAM_CUSTOMER_QUIRY = "BUNDLE_KEY_KAM_CUSTOMER_QUIRY";
	public static final int FLAG_NEWS = 1001;
	public static final int FLAG_DEALS = 1002;
	public static final int FLAG_ACOUNT_UPDATES = 1003;
	public static final int FLAG_INSERT_DELIVERY_ADDRESS = 1004;
	public static final int FLAG_ADD_RFQ = 1005;
	public static final int FLAG_DESIGNATION_LOADING = 1006;
	public static final int FLAG_STATES_LOADING = 1007;
	public static final int FLAG_CITIES_LOADING = 1008;
	public static final int FLAG_EDIT_CONTACT_INFO_LOADING = 1009;
	public static final int FLAG_EDIT_CONTACT_INFO_EDITING = 1010;
	public static final int FLAG_UNITS_LOADING = 1011;
	public static final int FLAG_MATERIAL_CATEGORY_LOADING = 1012;
	public static final int FLAG_DEALS_POST_ORDER = 1013;
	public static final int FLAG_NO_INTERNET = 1014;
	public static final int FLAG_APIURL_PREFIX = 1015;
	public static final int FLAG_ISREGISTERD_IN_ERP = 1016;
	public static final int FLAG_URGENCY_LIST_LOADING = 1017;
	public static final int FLAG_TAXATION_PREFERENCE_LOADING = 1018;
	public static final int FLAG_RE_ORDER = 1019;
	public static final int FLAG_ORDER_DETAIL = 1020;
	public static final int FLAG_SHIPMENT_DETAIL = 1021;
	public static final int FLAG_RFQ_DETAIL = 1022;
	public static final int FLAG_PO_UPLOAD = 1023;
	public static final int FLAG_ACCEPT_QUOTE = 1024;
	public static final int FLAG_REQUEST_RE_QUOTE = 1025;
	public static final int FLAG_RFQ_LOADING = 1026;
	public static final int FLAG_LOGOUT = 1027;
	public static final int FLAG_LOGIN = 1028;
	public static final int FLAG_REGISTER_USER = 1029;
	public static final int FLAG_OUTSTANDING = 1030;
	public static final int FLAG_DEALS_BI_ID = 1031;
	public static final int FLAG_REQUEST_STATEMENT = 1032;
	public static final int FLAG_CHANGE_PASSWORD = 1033;
	public static final int FLAG_DELETE_DELIVERY_ADDRESS = 1034;
	public static final int FLAG_UPDATE_DELIVERY_ADDRESS = 1035;
	public static final int FLAG_SKU_CATEGORY = 1036;
	public static final int FLAG_SKU_SUB_CATEGORY = 1037;
	public static final int FLAG_SKU = 1038;
	public static final int FLAG_Customers_FLAG = 1039;
	public static final String LOOKUP_MASTER_TABLE_KEY_DATA_EXPIRY = "LOOKUP_MASTER_TABLE_KEY_DATA_EXPIRY";
	public static final String LOOKUP_MASTER_TABLE_KEY_LAST_UPDATE_TIME = "LOOKUP_MASTER_TABLE_KEY_LAST_UPDATE_TIME";
	public static final String LOOKUP_MASTER_TABLE_KEY_STATE_LIST = "LOOKUP_MASTER_TABLE_KEY_STATE_LIST";
	public static final String LOOKUP_MASTER_TABLE_KEY_CITY_LIST_FOR_STATE = "LOOKUP_MASTER_TABLE_KEY_CITY_LIST_FOR_STATE";
	public static final String LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST = "LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST";
	public static final String LOOKUP_MASTER_TABLE_KEY_ITEM_SUB_CATEGORY_LIST = "LOOKUP_MASTER_TABLE_KEY_ITEM_SUB_CATEGORY_LIST";
	public static final String LOOKUP_MASTER_TABLE_KEY_UNITS_LIST = "LOOKUP_MASTER_TABLE_KEY_UIM_LIST";
	public static final String LOOKUP_MASTER_TABLE_KEY_BILLING_TYPE_LIST = "LOOKUP_MASTER_TABLE_KEY_BILLING_TYPE_LIST";
	public static final String LOOKUP_MASTER_TABLE_KEY_TAX_PREFERENCE_LIST = "LOOKUP_MASTER_TABLE_KEY_TAX_PREFERENCE_LIST";
	public static final String LOOKUP_MASTER_TABLE_KEY_DEGISNATIONS = "LOOKUP_MASTER_TABLE_KEY_DEGISNATIONS";
	public static final String LOOKUP_MASTER_TABLE_KEY_URGENCY_LIST = "LOOKUP_MASTER_TABLE_KEY_URGENCY_LIST";
	public static final int BUILD_TYPE_LIVE = 1001;
	public static final int BUILD_TYPE_UAT = 1002;
	public static final int BUILD_TYPE_CUSTOM = 1003;
	public static boolean isInternalBuild = true;
	public static boolean isDevMode = true;
	public static int serverType = 0;
	public static int sCONTAINERACTIVITY = 1500;

	public static enum LoginStatus
	{
		SMEID_WITH_ERPREGISTERATION(1),
		SMEID_WITHOUT_ERPREGISTERATION(2),
		LOGGED_IN_AS_KAM_WITH_CUSTOMER_SELECTED(3),
		LOGGED_IN_AS_KAM_WITH_CUSTOMER_NOT_SELECTED_YET(4),
		NOT_LOGGED_IN(5);
		
		int value;
		private LoginStatus(int value)
		{
			this.value = value;
		}
		public int getValue()
		{
			return value;
		}
	}

	public static enum LoginSources
	{
		DrawerMenu("DrawerMenu"),
		MY_UPDATES("MY_UPDATES"),
		MY_ORDERS("MY_ORDERS"),
		CONTACT_INFO("CONTACT_INFO"),
		TAKE_THIS_DEAL("TAKE_THIS_DEAL"),
		DELIVERY_ADDRESS("DELIVERY_ADDRESS"),
		MY_RFQ("MY_RFQ"),
		ADD_RFQ("ADD_RFQ"),
        INTERESTED_CUSTOMER("INTERESTED_CUSTOMER"),
		PREVIOUS_SCREEN("PREVIOUS_SCREEN");
		
		String value;
		private LoginSources(String value)
		{
			this.value=value;
		}

		@Override
		public String toString() 
		{
			return value;
		}
	}

	public static final int PERMISSION_REQUEST_READ_ACCOUNTS = 1;
	public static final int PERMISSION_REQUEST_READ_CAMERA = 2;
	public static final int PERMISSION_REQUEST_READ_LOCATION = 3;
	public static final int PERMISSION_REQUEST_READ_STORAGE = 4;
	public static final int PERMISSION_REQUEST_PHONE_CALL = 5;
	public static final int PERMISSION_REQUEST_ALL = 6;
	public static final int PERMISSION_REQUEST_RECOD_AUDIO_STORAGE = 7;
}
