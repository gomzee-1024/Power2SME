package com.power2sme.android.conf;

import android.content.Context;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dataprovider.network.NetworkDataProvider;
import com.power2sme.android.dtos.response.ResponseGetServerPrefixDto;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class APIs 
{
	public static final String BASE_DOMAIN = "http://uat.power2sme.com/p2sapi"; //UAT
//	public static final String BASE_DOMAIN = "http://www.power2sme.com/p2sapi"; //Live
//	public static final String BASE_DOMAIN = "http://192.168.1.161:9090/p2sapi"; //Himanshu Sys IP

	private static final String BASE_URL = BASE_DOMAIN + "/ws/";
	private static final String API_VERSION="v3/";

	public static final String BANNER_IMG_URL = "http://www.power2sme.com/images/2016/android/";

	//	STATIC URLs START
	public static String URL_SERVER_PREFIX_LIVE			= "http://power2sme.com/api/v3/getAppStartupSettings/";
	public static String URL_SERVER_PREFIX_UAT			= "http://uat.power2sme.com/api/v3/getAppStartupSettings/";
//	public static String URL_SME_KHABAR 			= "http://blog.power2sme.com/feed/";
    public static String URL_SME_KHABAR 			= "http://power2sme.com/exportnews/feed";
	public static String URL_ABOUT_US				= "http://www.power2sme.com/mobile/about-us.html";
	public static String URL_PRIVACY_POLICY			= "http://www.power2sme.com/mobile/privacy-policy.html";
	public static String URL_RATE_THIS_APP			= "https://play.google.com/store/apps/details?id=com.power2sme.android";
	public static String URL_TERMS_AND_CONDITION	= "http://www.power2sme.com/mobile/tnc.html";
    public static String URL_MARKET_PLACE			= "http://uat-marketplace.power2sme.com/product-type-en/";
	public static String URL_PRODUCTION_APK			= "http://uat.power2sme.com/mobile/android/latest/app-release.apk";

//	STATIC URLs END

	public static final String URL_LOGIN 						= "getSmeLoginCrm";
	public static final String URL_LOGIN_BY_SOCIAL_NET			= "getSmeLoginBySocialNetCrm";
	public static final String URL_SIGNUP						= "registerorg";
	public static final String URL_GET_STATES 					= "getStateList";
	public static final String URL_GET_CITIES 					= "getCityList";
//	public static final String URL_GET_DEALS	 				= "dealsData";
	public static final String URL_ADD_CAMPAIGN 				= "addPolymerCampaign";
	public static final String URL_FORGOT_PASSWORD 				= "forgotPassword";
	public static final String URL_GET_ACCOUNT_UPDATES			= "accountUpdates";
	public static final String URL_GET_SALES_ORDER 				= "getSalesOrder";
	public static final String URL_GET_OPEN_RFQ 				= "rfqList";
	public static final String URL_ACCEPT_QUOTE 				= "acceptQuote";
	public static final String URL_REQUEST_REQUOTE 				= "reQuoteRequestMail";
	public static final String URL_GET_CONTACTS_INFO 			= "smeContact";
	public static final String URL_GET_CONTACT_DETAIL			= "vieworganisation";
	public static final String URL_EDIT_CONTACTS_INFO 			= "updateorganisation";
	public static final String URL_DELIVERY_ADDRESS_LIST		= "shipToAddress";
	public static final String URL_DELIVERY_ADDRESS_INSERT		= "insertShippingAddress";
	public static final String URL_SMEPROFILE_EDIT				= "UpdateProfileService.svc/UpdateSMEProfile";//not in use
	public static final String URL_UPLOAD_PO					= "uploadPO";
	public static final String URL_POST_RE_ORDER				= "createNewWishlist";
	public static final String URL_RFQ_DETAILS 					= "rfqDetailsByNumber";
	public static final String URL_SHIPMENT_DETAILS 			= "shipmentDetails";	
	public static final String URL_ADD_NEW_RFQ					= "createopportunity";
	public static final String URL_UNIT_OF_MEASURE_LIST			= "uomList";
	public static final String URL_MATERIAL_CATEGORY_LIST		= "getItemCategorylist";
	public static final String URL_GET_DESIGNATIONS				= "designations";
	public static final String URL_GET_IS_REGISTERD_IN_ERP		= "isCompanyRegistered";
	public static final String URL_GET_TAX_PREFERENCE_LIST		= "taxationPrefList";
	public static final String URL_GET_URGENCY_LIST				= "urgencyList";
//	public static final String URL_LOGOUT						= "getSmeLogout";
	public static final String URL_GET_OUTSTANDING_AMOUNT		= "outstandingAmount";
	public static final String URL_DEVICE_REGISTER				= "regdevicefornotification";
	public static final String URL_ACCOUNT_SUMMERY_REQUEST		= "accountSummary";
	public static final String URL_GET_DEALS_BY_ID				= "dealsDataById";

    public static final String URL_GET_SMART_DEALS_DATA			= "smartDealsData";
	public static final String URL_CHANGE_PASSWORD				= "changePassword";
	public static final String URL_DELETE_DELIVERY_ADDRESS 		= "deleteShippingAddress";
	public static final String URL_UPDATE_DELIVERY_ADDRESS 		= "updateShippingAddress";
	public static final String URL_GET_SKU_CATEGORY_LIST 		= "skuCategoryList";
	public static final String URL_GET_SKU_SUB_CATEGORY_LIST 	= "skuSubCategoryList";
	public static final String URL_GET_SKU_LIST 				= "skuList";
	public static final String URL_GET_CUSTOMER_LIST 			= "searchorgforkam";
	public static final String URL_ACT_ON_BEHALF_OF 			= "actonbehalfof";
	public static final String URL_LOGOUT 						= "logout";

	public static final String URL_GET_DEALS_LOCATION			= "dealsDataByLocation";

	// PHP tracking APIs
//	public static final String URL_GET_TRUCK_LOCATION_BY_ORDER_ID= "http://uat-track.power2sme.com/api/v1/getOrderLocation";
//	public static final String URL_TRACKING_STATUS 				= "http://uat-track.power2sme.com/api/v1/getTrackingStatus";

	public static String URL_GET_TRUCK_LOCATION_BY_ORDER_ID= "http://track.power2sme.com/api/v1/getOrderLocation";
	public static String URL_TRACKING_STATUS 				= "http://track.power2sme.com/api/v1/getTrackingStatus";

/**
 *  Navision APIs 
 */
	
	public static String URL_GET_STATE_FROM_NAV		= "getCityStateFromNavision";//"NavStateService.svc/GetState";//use this api where we interacting with Navision
	public static String URL_GET_CITY_FROM_NAV		= "getCityStateFromNavision";//"NavCityService.svc/GetCity";//use this api where we interacting with Navision
	
	public static String getURL(Context context, String urlSuffix)
	{
		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
		String serverPrefix = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_SERVER_PREFIX, null);

		if(serverPrefix==null)
		{
			NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> response = null;
			if(Constants.isDevMode)
			{
				response = NetworkDataProvider.getInstance(context).getServerPrefix(APIs.URL_SERVER_PREFIX_UAT);
			}
			else
			{
				response = NetworkDataProvider.getInstance(context).getServerPrefix(APIs.URL_SERVER_PREFIX_LIVE);
			}

			if(response!=null && response.getResultObject()!=null && response.getResultObject().getData()!=null)
			{
				serverPrefix = response.getResultObject().getData().getAndroidServerConf().getServerPrefix();
				myAccountApplication.getPrefs().edit().putString(Constants.PREFERENCE_SERVER_PREFIX, serverPrefix).commit();
			}
		}

		String serverUrl = serverPrefix + "/ws/" + API_VERSION + urlSuffix;
		Authenticator.setDefault(new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication("admin",new String("admin").toCharArray());
			}
		});
		
		serverUrl = BASE_URL + API_VERSION + urlSuffix;
		return serverUrl;
	}
}

