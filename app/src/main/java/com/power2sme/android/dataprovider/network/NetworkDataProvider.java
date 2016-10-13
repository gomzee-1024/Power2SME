package com.power2sme.android.dataprovider.network;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power2sme.android.GCMUtils;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.conf.Constants.LoginStatus;
import com.power2sme.android.dataprovider.IDataProvider;
import com.power2sme.android.dtos.request.RequestAddCampaignDto;
import com.power2sme.android.dtos.request.RequestChangePasswordDto;
import com.power2sme.android.dtos.request.RequestDeleteDeliveryAddressDto;
import com.power2sme.android.dtos.request.RequestDeviceIdRegisterDto;
import com.power2sme.android.dtos.request.RequestForgotPasswordDto;
import com.power2sme.android.dtos.request.RequestGetCitiesDto;
import com.power2sme.android.dtos.request.RequestLoginBySocialNetworkDto;
import com.power2sme.android.dtos.request.RequestRegisterOrgDto;
import com.power2sme.android.dtos.request.RequestSMELoginDto;
import com.power2sme.android.dtos.request.RequestSearchOrgForKAMDto;
import com.power2sme.android.dtos.response.ResponseAcceptRFQDto;
import com.power2sme.android.dtos.response.ResponseAccountSummeryDto;
import com.power2sme.android.dtos.response.ResponseActOnBehalfOfDto;
import com.power2sme.android.dtos.response.ResponseAddCampaignDto;
import com.power2sme.android.dtos.response.ResponseAddNewRFQDto;
import com.power2sme.android.dtos.response.ResponseBaseDTO;
import com.power2sme.android.dtos.response.ResponseChangePasswordDto;
import com.power2sme.android.dtos.response.ResponseContactInfoEditDto;
import com.power2sme.android.dtos.response.ResponseContactInfoListDto;
import com.power2sme.android.dtos.response.ResponseCustomersDto;
import com.power2sme.android.dtos.response.ResponseDeleteDeliveryAddressDto;
import com.power2sme.android.dtos.response.ResponseDeliveryAddressDto;
import com.power2sme.android.dtos.response.ResponseDeliveryAddressEditDto;
import com.power2sme.android.dtos.response.ResponseDeliveryAddressInsertDto;
import com.power2sme.android.dtos.response.ResponseDeviceIdRegisterDto;
import com.power2sme.android.dtos.response.ResponseForgotPasswordDto;
import com.power2sme.android.dtos.response.ResponseGeoPointsDto;
import com.power2sme.android.dtos.response.ResponseGetAccountUpdatesDto;
import com.power2sme.android.dtos.response.ResponseGetCitiesDto;
import com.power2sme.android.dtos.response.ResponseGetDealsByIDDto;
import com.power2sme.android.dtos.response.ResponseGetDealsDto;
import com.power2sme.android.dtos.response.ResponseGetDesignationsDto;
import com.power2sme.android.dtos.response.ResponseGetServerPrefixDto;
import com.power2sme.android.dtos.response.ResponseGetStatesDto;
import com.power2sme.android.dtos.response.ResponseGetTaxPreferenceListDto;
import com.power2sme.android.dtos.response.ResponseGetUrgencyListDto;
import com.power2sme.android.dtos.response.ResponseIsRegisterdInERPDto;
import com.power2sme.android.dtos.response.ResponseLogoutDto;
import com.power2sme.android.dtos.response.ResponseMaterialCategoryListDto;
import com.power2sme.android.dtos.response.ResponseOutstandingDto;
import com.power2sme.android.dtos.response.ResponsePostReOrderDto;
import com.power2sme.android.dtos.response.ResponseRFQsDto;
import com.power2sme.android.dtos.response.ResponseRequestForRequoteRFQDto;
import com.power2sme.android.dtos.response.ResponseSKUCategoryFetchDto;
import com.power2sme.android.dtos.response.ResponseSKUSubCategoryFetchDto;
import com.power2sme.android.dtos.response.ResponseSKUsFetchDto;
import com.power2sme.android.dtos.response.ResponseSMELoginDto;
import com.power2sme.android.dtos.response.ResponseSMEProfileDto;
import com.power2sme.android.dtos.response.ResponseSMEProfileUpdateDto;
import com.power2sme.android.dtos.response.ResponseSMESignupDto;
import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.dtos.response.ResponseShipmentDetailsDto;
import com.power2sme.android.dtos.response.ResponseTrackingStatusDto;
import com.power2sme.android.dtos.response.ResponseTruckLocationsTrackingDto;
import com.power2sme.android.dtos.response.ResponseUnitOfMeasureDto;
import com.power2sme.android.dtos.response.ResponseUpdateDeliveryAddressDto;
import com.power2sme.android.dtos.response.ResponseUploadPODto;
import com.power2sme.android.entities.ContactInfo;
import com.power2sme.android.entities.DeliveryAddress;
import com.power2sme.android.entities.GeneralInformation;
import com.power2sme.android.entities.LeadSource;
import com.power2sme.android.entities.ReOrder;
import com.power2sme.android.entities.State;
import com.power2sme.android.entities.v3.Customer;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.entities.v3.RequestSearchOrgForKAMDtoOrganization;
import com.power2sme.android.sections.agentlogin.CustomerSearchFilter;
import com.power2sme.android.sections.myorders.list.OrderTypes;
import com.power2sme.android.sections.myorders.tracking.GoogleGeoPointsParser;
import com.power2sme.android.sections.myrfqs.list.RFQTypes;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkDataProvider implements IDataProvider
{
	private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    
    
	private static NetworkDataProvider networkDataProvider;
	Context context;
	MyAccountApplication myAccountApplication;
	private NetworkDataProvider(Context context)
	{
		this.context=context;
		objectMapper = new ObjectMapper();
		objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		
		jsonFactory = new JsonFactory();
		myAccountApplication = (MyAccountApplication) context.getApplicationContext();
	}
	
	public static NetworkDataProvider getInstance(Context context)
	{
		if(networkDataProvider==null)
		{
			networkDataProvider=new NetworkDataProvider(context);
		}
		return networkDataProvider;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> getServerPrefix(String prefixApiUrl)
	{
		ResponseGetServerPrefixDto responseGetServerPrefixDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, prefixApiUrl, null);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetServerPrefixDto = objectMapper.readValue(jp, ResponseGetServerPrefixDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetServerPrefixDto>();
		ACError error = parseServerResponse(response, responseGetServerPrefixDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetServerPrefixDto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSMELoginDto> smeLogin(String emailId, String password) 
	{
		ResponseSMELoginDto responseSMELoginDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> regGCMIdResponse=null;
		try
		{
			RequestSMELoginDto requestSMELoginDto=new RequestSMELoginDto();
			requestSMELoginDto.setEmailId(emailId);
			requestSMELoginDto.setPassword(password);
			String requestSMELoginDtoStrVal = objectMapper.writeValueAsString(requestSMELoginDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_LOGIN), requestSMELoginDtoStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSMELoginDto = objectMapper.readValue(jp, ResponseSMELoginDto.class);
				/*SharedPreferences sharedPref = context.getSharedPreferences(
						"clientinfo", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putString("smeid",responseSMELoginDto.getData().getCustomerLogin().getSmeId());
				editor.putString("phone",responseSMELoginDto.getData().getCustomerLogin().getPrimary_phone());
				editor.putString("email",responseSMELoginDto.getData().getCustomerLogin().getPrimary_email());
				editor.commit();*/
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSMELoginDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMELoginDto>();
		ACError error = parseServerResponse(response, responseSMELoginDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseSMELoginDto);
			
			final SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
			String regId = prefs.getString("registration_id", null);
			if(regId!=null && regId.length()>0)
			{
				myAccountApplication.getPrefs().edit().putString(Constants.PREFERENCE_CUSTOMER_EMAIL, emailId).commit();
				regGCMIdResponse = registerDeviceRegisterationId(regId, "1");
			}
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetStatesDto> getStates()
	{
		ResponseGetStatesDto responseGetStatesDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_STATES), null);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetStatesDto = objectMapper.readValue(jp, ResponseGetStatesDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseGetStatesDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetStatesDto>();
		ACError error = parseServerResponse(response, responseGetStatesDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetStatesDto);
		}
		return networkAsyncTaskResponse;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseGetCitiesDto> getCities(State state)
	{
		ResponseGetCitiesDto responseGetCitiesDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			RequestGetCitiesDto requestGetCitiesDto=new RequestGetCitiesDto();
			requestGetCitiesDto.setStateid(String.valueOf(state.getStateId()));
			String requestGetCitiesDtoStrVal = objectMapper.writeValueAsString(requestGetCitiesDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_GET_CITIES), requestGetCitiesDtoStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetCitiesDto = objectMapper.readValue(jp, ResponseGetCitiesDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseGetCitiesDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetCitiesDto>();
		ACError error = parseServerResponse(response, responseGetCitiesDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetCitiesDto);
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseAddCampaignDto> addCampaign(RequestAddCampaignDto requestAddCampaignDto)
	{
		ResponseAddCampaignDto responseAddCampaignDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			String requestAddCampaignDtoStrVal = objectMapper.writeValueAsString(requestAddCampaignDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_ADD_CAMPAIGN), requestAddCampaignDtoStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseAddCampaignDto = objectMapper.readValue(jp, ResponseAddCampaignDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseAddCampaignDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAddCampaignDto>();
		ACError error = parseServerResponse(response, responseAddCampaignDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseAddCampaignDto);
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseForgotPasswordDto> forgotPassword(String emailId) 
	{
		ResponseForgotPasswordDto responseForgotPasswordDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			RequestForgotPasswordDto requestForgotPasswordDto=new RequestForgotPasswordDto();
			requestForgotPasswordDto.setStremailid(emailId);
			String requestForgotPasswordDtoStrVal = objectMapper.writeValueAsString(requestForgotPasswordDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_FORGOT_PASSWORD), requestForgotPasswordDtoStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseForgotPasswordDto = objectMapper.readValue(jp, ResponseForgotPasswordDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseForgotPasswordDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseForgotPasswordDto>();
		ACError error = parseServerResponse(response, responseForgotPasswordDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseForgotPasswordDto);			
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseGetDealsDto> getDeals(long pageIndex, long pageSize, String filterValue)
	{
		ResponseGetDealsDto responseGetDealsDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("pageIndex", ""+pageIndex);
            params.put("pageSize", ""+pageSize);
            params.put("filterValue", filterValue);

			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_SMART_DEALS_DATA), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetDealsDto = objectMapper.readValue(jp, ResponseGetDealsDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseGetDealsDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetDealsDto>();
		ACError error = parseServerResponse(response, responseGetDealsDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetDealsDto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto> getAccountUpdates(long currentPage, long PageSize, String strsmeid) 
	{
		ResponseGetAccountUpdatesDto responseGetAccountUpdatesDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map<String, String> params=new HashMap<String, String>();
			params.put("currentPage", ""+currentPage);
			params.put("PageSize", ""+PageSize);
			params.put("strsmeid", ""+strsmeid);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_ACCOUNT_UPDATES), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetAccountUpdatesDto = objectMapper.readValue(jp, ResponseGetAccountUpdatesDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto>();
		ACError error = parseServerResponse(response, responseGetAccountUpdatesDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetAccountUpdatesDto);			
		}
		return networkAsyncTaskResponse;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseSalesOrdersDto> getSalesOrders(long currentPageIndex,long ordersPerPage, String smeId, String orderNo, OrderTypes orderTypes) 
	{
		ResponseSalesOrdersDto responseSalesOrdersDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("currentPage", ""+currentPageIndex);
			params.put("PageSize", ""+ordersPerPage);
			params.put("strsmeid", smeId);
			params.put("OrderNo", orderNo);
			if(orderTypes==OrderTypes.PREVIOUS_ORDERS_ONLY)
			{
				params.put("strType", "PreviousOrder");
			}
			else if(orderTypes==OrderTypes.UNDELIVERED_ORDERS_ONLY)
			{
				params.put("strType", "UndeliveredOrder");
			}
			else if(orderTypes==OrderTypes.ALL)
			{
				params.put("strType", "All");
			}
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_SALES_ORDER), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSalesOrdersDto = objectMapper.readValue(jp, ResponseSalesOrdersDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSalesOrdersDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSalesOrdersDto>();
		ACError error = parseServerResponse(response, responseSalesOrdersDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseSalesOrdersDto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSalesOrdersDto> getSearchSalesOrders(long currentPageIndex,long ordersPerPage, String smeId, String orderNo, OrderTypes orderTypes) 
	{
		ResponseSalesOrdersDto responseSalesOrdersDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("currentPage", ""+currentPageIndex);
			params.put("PageSize", ""+ordersPerPage);
			params.put("strsmeid", smeId);
			params.put("OrderNo", orderNo);
			if(orderTypes==OrderTypes.PREVIOUS_ORDERS_ONLY)
			{
				params.put("strType", "PreviousOrder");
			}
			else if(orderTypes==OrderTypes.UNDELIVERED_ORDERS_ONLY)
			{
				params.put("strType", "UndeliveredOrder");
			}
			else if(orderTypes==OrderTypes.ALL)
			{
				params.put("strType", "All");
			}
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_SALES_ORDER), params);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSalesOrdersDto = objectMapper.readValue(jp, ResponseSalesOrdersDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSalesOrdersDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSalesOrdersDto>();
		ACError error = parseServerResponse(response, responseSalesOrdersDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseSalesOrdersDto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseRFQsDto> getRFQs(long currentPage, long pageSize, String smeId, RFQTypes rfqTypes) 
	{
		ResponseRFQsDto responseRFQsDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("currentPage", "" + currentPage);
			params.put("PageSize", "" + pageSize);
			params.put("strsmeid", smeId);
			if(rfqTypes==RFQTypes.OPEN_RFQ_ONLY)
			{
				params.put("strType", "PendingQuotes");
			}
			else if(rfqTypes==RFQTypes.PRICE_QUOTES_ONLY)
			{
				params.put("strType", "Quotes");
			}
			else
			{
				params.put("strType", "All");
			}
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_OPEN_RFQ), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseRFQsDto = objectMapper.readValue(jp, ResponseRFQsDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseRFQsDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseRFQsDto>();
		ACError error = parseServerResponse(response, responseRFQsDto);
		if(error!=null)
		{
			if(error.getErrorCodes()==ACErrorCodes.API_ERRORCODE_NORECORDSFOUND && responseRFQsDto.getTotalRecord()==0)
			{
				networkAsyncTaskResponse.setResultObject(responseRFQsDto);
			}
			else
			{
				networkAsyncTaskResponse.setError(error);
			}
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseRFQsDto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseDeliveryAddressDto> getDeliveryAdresses(String smeId) 
	{
		ResponseDeliveryAddressDto responseDeliveryAddressDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("strsmeid", smeId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_DELIVERY_ADDRESS_LIST), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseDeliveryAddressDto = objectMapper.readValue(jp, ResponseDeliveryAddressDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseDeliveryAddressDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeliveryAddressDto>();
		ACError error = parseServerResponse(response, responseDeliveryAddressDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseDeliveryAddressDto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto> insertDeliveryAdresses(DeliveryAddress deliveryAddress)
	{
		ResponseDeliveryAddressInsertDto responseDeliveryAddressInsertDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			String deliveryAddressJsonStrVal = objectMapper.writeValueAsString(deliveryAddress);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_DELIVERY_ADDRESS_INSERT), deliveryAddressJsonStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseDeliveryAddressInsertDto = objectMapper.readValue(jp, ResponseDeliveryAddressInsertDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto>();
		ACError error = parseServerResponse(response, responseDeliveryAddressInsertDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseDeliveryAddressInsertDto);
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseDeliveryAddressEditDto> editDeliveryAdresses(DeliveryAddress deliveryAddress) 
	{
//		ResponseDeliveryAddressEditDto responseDeliveryAddressEditDto=null;
//		NetworkAsyncTaskResponse<String> response=null;
//		try
//		{
//			String deliveryAddressJsonStrVal = objectMapper.writeValueAsString(deliveryAddress);
//			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_DELIVERY_ADDRESS_EDIT), deliveryAddressJsonStrVal);
//			if(response.getResultObject()!=null)
//			{
//				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
//				responseDeliveryAddressEditDto = objectMapper.readValue(jp, ResponseDeliveryAddressEditDto.class);
//			}
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		NetworkAsyncTaskResponse<ResponseDeliveryAddressEditDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeliveryAddressEditDto>();
//		ACError error = parseServerResponse(response, responseDeliveryAddressEditDto);
//		if(error!=null)
//		{
//			networkAsyncTaskResponse.setError(error);
//		}
//		else
//		{
//			networkAsyncTaskResponse.setResultObject(responseDeliveryAddressEditDto);			
//		}
//		return networkAsyncTaskResponse;
		return null;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseSMEProfileDto> getSMEProfile(String smeId) 
	{
//		ResponseSMEProfileDto responseSMEProfileDto=null;
//		NetworkAsyncTaskResponse<String> response=null;
//		try
//		{
//			Map params=new HashMap<String, String>();
//			params.put("strSMEId", smeId);
//			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_SMEPROFILE_GET), params);
//			if(response.getResultObject()!=null)
//			{
//				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
//				responseSMEProfileDto = objectMapper.readValue(jp, ResponseSMEProfileDto.class);
//			}
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		NetworkAsyncTaskResponse<ResponseSMEProfileDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMEProfileDto>();
//		ACError error = parseServerResponse(response, responseSMEProfileDto);
//		if(error!=null)
//		{
//			networkAsyncTaskResponse.setError(error);
//		}
//		else
//		{
//			networkAsyncTaskResponse.setResultObject(responseSMEProfileDto);			
//		}
//		return networkAsyncTaskResponse;
		return null;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseSMEProfileUpdateDto> editSMEProfile(GeneralInformation generalInformation)
	{
		ResponseSMEProfileUpdateDto responseSMEProfileUpdateDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			generalInformation.setState("1");
			generalInformation.setCity("1");
			String deliveryAddressJsonStrVal = objectMapper.writeValueAsString(generalInformation);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_SMEPROFILE_EDIT), deliveryAddressJsonStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSMEProfileUpdateDto = objectMapper.readValue(jp, ResponseSMEProfileUpdateDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSMEProfileUpdateDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMEProfileUpdateDto>();
		ACError error = parseServerResponse(response, responseSMEProfileUpdateDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseSMEProfileUpdateDto);
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseAcceptRFQDto> acceptRFQ(String orderNo, String smeId) 
	{
		ResponseAcceptRFQDto responseAcceptRFQDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("strWishListNo", orderNo);
			params.put("strSmeId", smeId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_ACCEPT_QUOTE), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseAcceptRFQDto = objectMapper.readValue(jp, ResponseAcceptRFQDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseAcceptRFQDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAcceptRFQDto>();
		ACError error = parseServerResponse(response, responseAcceptRFQDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseAcceptRFQDto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto> requestForRequoteRFQ(String orderNo, String smeId)
	{
		ResponseRequestForRequoteRFQDto responseRequestForRequoteRFQDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("WishListNo", orderNo);
			params.put("strSMEId", smeId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_REQUEST_REQUOTE), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseRequestForRequoteRFQDto = objectMapper.readValue(jp, ResponseRequestForRequoteRFQDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto>();
		ACError error = parseServerResponse(response, responseRequestForRequoteRFQDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseRequestForRequoteRFQDto);
		}
		return networkAsyncTaskResponse;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseContactInfoListDto> getContactInfoList(String smeId) 
	{
		ResponseContactInfoListDto responseContactInfoListDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("strsmeid", smeId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_CONTACTS_INFO), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseContactInfoListDto = objectMapper.readValue(jp, ResponseContactInfoListDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseContactInfoListDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseContactInfoListDto>();
		ACError error = parseServerResponse(response, responseContactInfoListDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseContactInfoListDto);			
		}
		return networkAsyncTaskResponse;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseContactInfoEditDto> editContactInfo(ContactInfo contactInfo) 
	{
		ResponseContactInfoEditDto responseContactInfoEditDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			String contactInfoJsonStrVal = objectMapper.writeValueAsString(contactInfo);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_EDIT_CONTACTS_INFO), contactInfoJsonStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseContactInfoEditDto = objectMapper.readValue(jp, ResponseContactInfoEditDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseContactInfoEditDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseContactInfoEditDto>();
		ACError error = parseServerResponse(response, responseContactInfoEditDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseContactInfoEditDto);			
		}
		return networkAsyncTaskResponse;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseUploadPODto> uploadPO(String rfqNo, File file, String smeId)
	{
		ResponseUploadPODto responseUploadPODto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map<String, String> strParams=new HashMap<String, String>();
			strParams.put("rfqno", rfqNo);
			Map<String, File> fileParams=new HashMap<String, File>();
			fileParams.put("file", file);
			response = NetworkUtils.getMultipartResponse(context, APIs.getURL(context, APIs.URL_UPLOAD_PO), strParams, fileParams);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseUploadPODto = objectMapper.readValue(jp, ResponseUploadPODto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseUploadPODto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseUploadPODto>();
		ACError error = parseServerResponse(response, responseUploadPODto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseUploadPODto);			
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponsePostReOrderDto> postReOrder(ReOrder reOrder)
	{
		ResponsePostReOrderDto responsePostReOrderDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			String reOrderJsonStrVal = objectMapper.writeValueAsString(reOrder);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_POST_RE_ORDER), reOrderJsonStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responsePostReOrderDto = objectMapper.readValue(jp, ResponsePostReOrderDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponsePostReOrderDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponsePostReOrderDto>();
		ACError error = parseServerResponse(response, responsePostReOrderDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responsePostReOrderDto);
		}
		return networkAsyncTaskResponse;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseRFQsDto> getRFQDetails(String rfqId) 
	{
		ResponseRFQsDto responseRFQsDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("rfqno", ""+rfqId);
			
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_RFQ_DETAILS), params);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseRFQsDto = objectMapper.readValue(jp, ResponseRFQsDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseRFQsDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseRFQsDto>();
		ACError error = parseServerResponse(response, responseRFQsDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseRFQsDto);			
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseShipmentDetailsDto> getShipmentDetails(String orderId) 
	{
		ResponseShipmentDetailsDto responseShipmentDetailsDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("OrderNumber", ""+orderId);
			
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_SHIPMENT_DETAILS), params);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseShipmentDetailsDto = objectMapper.readValue(jp, ResponseShipmentDetailsDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseShipmentDetailsDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseShipmentDetailsDto>();
		ACError error = parseServerResponse(response, responseShipmentDetailsDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseShipmentDetailsDto);			
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseAddNewRFQDto> addNewRFQ(NewRFQ_v3 newRFQ)
	{
		ResponseAddNewRFQDto responseAddNewRFQDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
            String addNewRfqPayloadStrVal="";

//            if(newRFQTypes==NewRFQTypes.DEALS)
//            {
//                newRFQ.setLeadSource(LeadSource.Android_Deal.toString());
//                String dealJsonStr = newRFQ.getRFQLine().get(0).getGrade();
//                newRFQ.getRFQLine().get(0).setGrade("");
//                String newRfqStr = objectMapper.writeValueAsString(newRFQ);
//                addNewRfqPayloadStrVal = "{\"rfq\":"+newRfqStr+",\"deal\":"+dealJsonStr+"}";
//            }
//            else if(newRFQTypes==NewRFQTypes.REORDER)
//            {
//                newRFQ.setLeadSource(LeadSource.Android_Reorder.toString());
//                addNewRfqPayloadStrVal = objectMapper.writeValueAsString(newRFQ);
//            }
//            else
//            {
//                newRFQ.setLeadSource(LeadSource.Android_RFQ.toString());
//                addNewRfqPayloadStrVal = objectMapper.writeValueAsString(newRFQ);
//            }

			addNewRfqPayloadStrVal = objectMapper.writeValueAsString(newRFQ);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_ADD_NEW_RFQ), addNewRfqPayloadStrVal);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseAddNewRFQDto = objectMapper.readValue(jp, ResponseAddNewRFQDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseAddNewRFQDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAddNewRFQDto>();
		ACError error = parseServerResponse(response, responseAddNewRFQDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			if(myAccountApplication.getLoginStatus() == LoginStatus.NOT_LOGGED_IN &&
					responseAddNewRFQDto!=null &&
					responseAddNewRFQDto.getData()!=null &&
					responseAddNewRFQDto.getData().getCustomerLogin()!=null)
			{
				final SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
				String regId = prefs.getString("registration_id", null);
				if(regId!=null && regId.length()>0)
				{
					NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> regGCMIdResponse = registerDeviceRegisterationId(regId, "1");
				}
				Utils.saveLoginResponseCustomerInfo(context, responseAddNewRFQDto.getData().getCustomerLogin());
			}
			networkAsyncTaskResponse.setResultObject(responseAddNewRFQDto);
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseUnitOfMeasureDto> getUnitOfMeasure(String category)
	{
		ResponseUnitOfMeasureDto responseUnitOfMeasureDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map<String, String> params=new HashMap<String, String>();
			params.put("category", category);
			response = NetworkUtils.getResponse(context,MethodTypes.GET, APIs.getURL(context, APIs.URL_UNIT_OF_MEASURE_LIST), params);

			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseUnitOfMeasureDto = objectMapper.readValue(jp, ResponseUnitOfMeasureDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseUnitOfMeasureDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseUnitOfMeasureDto>();
		ACError error = parseServerResponse(response, responseUnitOfMeasureDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseUnitOfMeasureDto);			
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseMaterialCategoryListDto> getMaterialCategoryList() 
	{
		ResponseMaterialCategoryListDto responseMaterialCategoryListDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_MATERIAL_CATEGORY_LIST), null);
			if(response.getResultObject()!=null)
			{
	//			String materialCategoryJsonResponse = context.getString(R.string.material_category_json_response);
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
	//			JsonParser jp = jsonFactory.createJsonParser(materialCategoryJsonResponse);
				responseMaterialCategoryListDto = objectMapper.readValue(jp, ResponseMaterialCategoryListDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseMaterialCategoryListDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseMaterialCategoryListDto>();
		ACError error = parseServerResponse(response, responseMaterialCategoryListDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseMaterialCategoryListDto);			
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSMESignupDto> smeSignup(RequestRegisterOrgDto registerOrgDto)
	{
		ResponseSMESignupDto responseSMESignupDto=null;
		NetworkAsyncTaskResponse<String> response = null;
		try {
			registerOrgDto.setObject_type_id("1");
			registerOrgDto.getOrganisation().setLeadSource(LeadSource.Android_Registration.toString());
			String reOrderJsonStrVal = objectMapper.writeValueAsString(registerOrgDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_SIGNUP), reOrderJsonStrVal);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSMESignupDto = objectMapper.readValue(jp, ResponseSMESignupDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseSMESignupDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMESignupDto>();
		ACError error = parseServerResponse(response, responseSMESignupDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			final SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
			String regId = prefs.getString("registration_id", null);
			if(regId!=null && regId.length()>0)
			{
				myAccountApplication.getPrefs().edit().putString(Constants.PREFERENCE_CUSTOMER_EMAIL, registerOrgDto.getOrganisation().getEmail()).commit();
				NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> regGCMIdResponse = registerDeviceRegisterationId(regId, "1");
			}
			networkAsyncTaskResponse.setResultObject(responseSMESignupDto);			
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetDesignationsDto> getDesignations()
	{
		ResponseGetDesignationsDto responseGetDesignationsDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_DESIGNATIONS), null);

			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetDesignationsDto = objectMapper.readValue(jp, ResponseGetDesignationsDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		NetworkAsyncTaskResponse<ResponseGetDesignationsDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetDesignationsDto>();
		ACError error = parseServerResponse(response, responseGetDesignationsDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetDesignationsDto);
		}
		return networkAsyncTaskResponse;

	}
	@Override
	public NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto> isRegisterdInERP(String smeID) 
	{
		ResponseIsRegisterdInERPDto responseIsRegisterdInERPDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map<String, String> params = new HashMap<String, String>();
			params.put("smeId", smeID);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_IS_REGISTERD_IN_ERP), params);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseIsRegisterdInERPDto = objectMapper.readValue(jp, ResponseIsRegisterdInERPDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto>();
		ACError error = parseServerResponse(response, responseIsRegisterdInERPDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseIsRegisterdInERPDto);			
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetTaxPreferenceListDto> getTaxPreferenceList() 
	{
		ResponseGetTaxPreferenceListDto responseGetTaxPreferenceListDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_TAX_PREFERENCE_LIST), null);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetTaxPreferenceListDto = objectMapper.readValue(jp, ResponseGetTaxPreferenceListDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseGetTaxPreferenceListDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetTaxPreferenceListDto>();
		ACError error = parseServerResponse(response, responseGetTaxPreferenceListDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetTaxPreferenceListDto);			
		}
		return networkAsyncTaskResponse;
		
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetUrgencyListDto> getUrgencyList() 
	{
		ResponseGetUrgencyListDto responseGetUrgencyListDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_URGENCY_LIST), null);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetUrgencyListDto = objectMapper.readValue(jp, ResponseGetUrgencyListDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseGetUrgencyListDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetUrgencyListDto>();
		ACError error = parseServerResponse(response, responseGetUrgencyListDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetUrgencyListDto);			
		}
		return networkAsyncTaskResponse;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public NetworkAsyncTaskResponse<ResponseGetUrgencyListDto> getUrgencyList(File file, String smeId)
	{
		ResponseGetUrgencyListDto responseGetUrgencyListDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_URGENCY_LIST), null);
			
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetUrgencyListDto = objectMapper.readValue(jp, ResponseGetUrgencyListDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseGetUrgencyListDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetUrgencyListDto>();
		ACError error = parseServerResponse(response, responseGetUrgencyListDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetUrgencyListDto);			
		}
		return networkAsyncTaskResponse;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseLogoutDto> logoutApp(String emailId)
	{
		ResponseLogoutDto responseLogoutDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
//			String regId = GCMUtils.getRegistrationId(context);
//			Map<String, String> params=new HashMap<String, String>();
//			params.put("reg_id", regId);
//
////			String emailId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_EMAIL, "");
//			params.put("email_id", emailId);
//			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_LOGOUT), params);

//			RequestLogoutDto requestLogoutDto=new RequestLogoutDto();
//			String regId = GCMUtils.getRegistrationId(context);
//			requestLogoutDto.setReg_id(regId);
//			requestLogoutDto.setEmail_id(emailId);
//			String logoutJsonStrVal = objectMapper.writeValueAsString(requestLogoutDto);
//			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_LOGOUT), logoutJsonStrVal);

			String regId = GCMUtils.getRegistrationId(context);
			Map<String, String> params=new HashMap<String, String>();
			params.put("reg_id", regId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_LOGOUT), params);

			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseLogoutDto = objectMapper.readValue(jp, ResponseLogoutDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		NetworkAsyncTaskResponse<ResponseLogoutDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseLogoutDto>();
		ACError error = parseServerResponse(response, responseLogoutDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseLogoutDto);
		}
		return networkAsyncTaskResponse;
	}
	
	public ACError parseServerResponse(NetworkAsyncTaskResponse<String> response, ResponseBaseDTO responseBaseDTO) 
	{
		if(!NetworkUtils.isNetworkAvailable(context))
		{
			return new ACError(ACErrorCodes.NETWORK_NOTAVAILABLE_ERROR, context.getString(R.string.network_not_available));
		}
		if(response==null)
		{
			return new ACError(ACErrorCodes.SERVER_ERROR, context.getString(R.string.server_error));
		}
		if(response.getError()!=null && response.getError().getErrorCodes()== ACErrorCodes.API_ERRORCODE_FORBIDDEN)
		{
			return new ACError(ACErrorCodes.API_ERRORCODE_FORBIDDEN, context.getString(R.string.server_error_access_forbidden));
		}
		if(response.getError()!=null)
		{
			return new ACError(ACErrorCodes.SERVER_ERROR, context.getString(R.string.server_error));
		}
		try 
		{
			if(responseBaseDTO!=null)
			{
				if(responseBaseDTO.getErrorCode()==0)
				{
					return null;
				}
				else if(responseBaseDTO.getErrorCode()== ACErrorCodes.API_ERRORCODE_DB.getErrorCode())
				{
					return new ACError(ACErrorCodes.API_ERRORCODE_DB, context.getString(R.string.server_error));
				}
				else if(responseBaseDTO.getErrorCode()== ACErrorCodes.API_ERRORCODE_INTERNALSERVER.getErrorCode())
				{
					return new ACError(ACErrorCodes.API_ERRORCODE_INTERNALSERVER, context.getString(R.string.server_error));
				}
				else if(responseBaseDTO.getErrorCode()== ACErrorCodes.API_ERRORCODE_DATEFORMATERROR.getErrorCode())
				{
					return new ACError(ACErrorCodes.API_ERRORCODE_DATEFORMATERROR, context.getString(R.string.server_error));
				}
				else if(responseBaseDTO.getErrorCode()== ACErrorCodes.API_ERRORCODE_NORECORDSFOUND.getErrorCode())
				{
					return new ACError(ACErrorCodes.API_ERRORCODE_NORECORDSFOUND, responseBaseDTO.getMessage());
				}
				else if(responseBaseDTO.getErrorCode()== ACErrorCodes.API_ERRORCODE_INPUTERROR.getErrorCode())
				{
					return new ACError(ACErrorCodes.API_ERRORCODE_INPUTERROR, responseBaseDTO.getMessage());
				}
				else if(responseBaseDTO.getErrorCode()== ACErrorCodes.API_ERRORCODE_EMAIL_ALREADY_EXIST_NEED_RELOGIN.getErrorCode())
				{
					return new ACError(ACErrorCodes.API_ERRORCODE_EMAIL_ALREADY_EXIST_NEED_RELOGIN, responseBaseDTO.getMessage());
				}
				else
				{
					return new ACError(ACErrorCodes.SERVER_ERROR, context.getString(R.string.server_error));
				}
			}
			else
			{
				return new ACError(ACErrorCodes.SERVER_ERROR, context.getString(R.string.network_not_available));
			}
		} 
		catch (Exception e) 
		{
		    e.printStackTrace();
		    return new ACError(ACErrorCodes.EXCEPTION, context.getString(R.string.server_error));
		}
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseContactInfoListDto> getContactInfo(String contactId) 
	{
		ResponseContactInfoListDto responseContactInfoListDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
//			Map<String, String> params=new HashMap<String, String>();
//			params.put("Contactid", contactId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_CONTACT_DETAIL), null);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseContactInfoListDto = objectMapper.readValue(jp, ResponseContactInfoListDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseContactInfoListDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseContactInfoListDto>();
		ACError error = parseServerResponse(response, responseContactInfoListDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseContactInfoListDto);			
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseOutstandingDto> getOutstandingAmout(String strsmeid) 
	{
		ResponseOutstandingDto responseOutstandingDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("smeId", strsmeid);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_OUTSTANDING_AMOUNT), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseOutstandingDto = objectMapper.readValue(jp, ResponseOutstandingDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseOutstandingDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseOutstandingDto>();
		ACError error = parseServerResponse(response, responseOutstandingDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseOutstandingDto);			
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse loginBySocialNetwork(String socialNetworkType,String email, String firstName, String lastName) 
	{
		ResponseSMELoginDto responseSMELoginDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			RequestLoginBySocialNetworkDto requestLoginBySocialNetworkDto=new RequestLoginBySocialNetworkDto();
			requestLoginBySocialNetworkDto.setEmailId(email);
			requestLoginBySocialNetworkDto.setFirstName(firstName);
			requestLoginBySocialNetworkDto.setLastName(lastName);
			requestLoginBySocialNetworkDto.setSignUpMode(""+socialNetworkType);

			if(socialNetworkType.equals("1"))
			{
				requestLoginBySocialNetworkDto.setLeadSource("Android_Facebook");
			}
			else if(socialNetworkType.equals("2"))
			{
				requestLoginBySocialNetworkDto.setLeadSource("Android_Google");
			}
			else if(socialNetworkType.equals("3"))
			{
				requestLoginBySocialNetworkDto.setLeadSource("Android_Linkedin");
			}

			String requestLoginBySocialNetworkDtoStrVal = objectMapper.writeValueAsString(requestLoginBySocialNetworkDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_LOGIN_BY_SOCIAL_NET), requestLoginBySocialNetworkDtoStrVal);



			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSMELoginDto = objectMapper.readValue(jp, ResponseSMELoginDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSMELoginDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSMELoginDto>();
		ACError error = parseServerResponse(response, responseSMELoginDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			final SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
			String regId = prefs.getString("registration_id", null);
			if(regId!=null && regId.length()>0)
			{
				myAccountApplication.getPrefs().edit().putString(Constants.PREFERENCE_CUSTOMER_EMAIL, email).commit();
				NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> regGCMIdResponse = registerDeviceRegisterationId(regId, "1");
			}
			networkAsyncTaskResponse.setResultObject(responseSMELoginDto);			
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> registerDeviceRegisterationId(String regId, String firstLaunchFlag)
	{
		ResponseDeviceIdRegisterDto responseDeviceIdRegisterDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			RequestDeviceIdRegisterDto requestDeviceIdRegisterDto=new RequestDeviceIdRegisterDto();
			requestDeviceIdRegisterDto.setDevice_id(Utils.getDeviceId(context));
			requestDeviceIdRegisterDto.setInstallType(firstLaunchFlag);
			requestDeviceIdRegisterDto.setDevice_name(Build.MODEL);//this is for future
			requestDeviceIdRegisterDto.setDevice_model(Build.MODEL);//model number
			requestDeviceIdRegisterDto.setReg_id(regId);//gcm id
			requestDeviceIdRegisterDto.setPlatform("Android v"+ Build.VERSION.RELEASE);//Android
			
			String smeId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_SMEID, "");
			String emailId = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_CUSTOMER_EMAIL, "");
			requestDeviceIdRegisterDto.setUser_id(emailId);//email
			requestDeviceIdRegisterDto.setSme_id(smeId);//sme id

			if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
			{
				try
				{
					LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
					List<String> providers = lm.getProviders(false);
					Location l = null;
					for (int i=providers.size()-1; i>=0; i--)
					{
						l = lm.getLastKnownLocation(providers.get(i));
						if (l != null) break;
					}
					if (l != null)
					{
						requestDeviceIdRegisterDto.setLati(""+l.getLatitude());
						requestDeviceIdRegisterDto.setLongi(""+l.getLongitude());
					}
					else
					{
						requestDeviceIdRegisterDto.setLati("0");
						requestDeviceIdRegisterDto.setLongi("0");
					}
				}
				catch(Exception ex)
				{
					requestDeviceIdRegisterDto.setLati("0");
					requestDeviceIdRegisterDto.setLongi("0");
				}
			}



//            LocationManager locationManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
//            Criteria criteria = new Criteria();
//            String bestProvider = locationManager.getBestProvider(criteria, false);
//            Location location = locationManager.getLastKnownLocation(bestProvider);
//            try
//            {
//                requestDeviceIdRegisterDto.setLati(""+location.getLatitude());
//                requestDeviceIdRegisterDto.setLongi(""+location.getLongitude());
//            }
//            catch (NullPointerException e)
//            {
//                requestDeviceIdRegisterDto.setLati("0");
//                requestDeviceIdRegisterDto.setLongi("0");
//            }




//            MyAccountApplication myAccountApplication = (MyAccountApplication)context.getApplicationContext();
//            requestDeviceIdRegisterDto.setLati(""+myAccountApplication.getUserCurrentLatitude());
//            requestDeviceIdRegisterDto.setLongi(""+myAccountApplication.getUserCurrentLongitude());

//            GPSTracker gps = new GPSTracker(context);
//            if(gps.canGetLocation())
//            {
//                double latitude = gps.getLatitude();
//                double longitude = gps.getLongitude();
//                //Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//                requestDeviceIdRegisterDto.setLati(""+latitude);//set lattitude of user current location
//                requestDeviceIdRegisterDto.setLongi(""+longitude);
//            }
//            else
//            {
//                gps.showSettingsAlert();
//            }

			String requestDeviceIdRegisterDtoStrVal = objectMapper.writeValueAsString(requestDeviceIdRegisterDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_DEVICE_REGISTER), requestDeviceIdRegisterDtoStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseDeviceIdRegisterDto = objectMapper.readValue(jp, ResponseDeviceIdRegisterDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto>();
		ACError error = parseServerResponse(response, responseDeviceIdRegisterDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseDeviceIdRegisterDto);			
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseAccountSummeryDto> getAccountSummery() 
	{
		ResponseAccountSummeryDto responseAccountSummeryDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_ACCOUNT_SUMMERY_REQUEST), null);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseAccountSummeryDto = objectMapper.readValue(jp, ResponseAccountSummeryDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseAccountSummeryDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAccountSummeryDto>();
		ACError error = parseServerResponse(response, responseAccountSummeryDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseAccountSummeryDto);			
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseGetDealsByIDDto> getDealById(String dealId) 
	{
		ResponseGetDealsByIDDto responseGetDealsByIDDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("dealId", dealId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_DEALS_BY_ID), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseGetDealsByIDDto = objectMapper.readValue(jp, ResponseGetDealsByIDDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseGetDealsByIDDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetDealsByIDDto>();
		ACError error = parseServerResponse(response, responseGetDealsByIDDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseGetDealsByIDDto);			
		}
		return networkAsyncTaskResponse;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto> getTruckLocations(String deviceId)
	{
		ResponseTruckLocationsTrackingDto responseTruckLocationsTrackingDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("invoice_number", deviceId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.URL_GET_TRUCK_LOCATION_BY_ORDER_ID, params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseTruckLocationsTrackingDto = objectMapper.readValue(jp, ResponseTruckLocationsTrackingDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto>();
		if(responseTruckLocationsTrackingDto.getError()!=null && responseTruckLocationsTrackingDto.getError().equals("1"))
		{
			networkAsyncTaskResponse.setError(new ACError(ACErrorCodes.SERVER_ERROR, responseTruckLocationsTrackingDto.getError_message()));
		}
		else {
			networkAsyncTaskResponse.setResultObject(responseTruckLocationsTrackingDto);
		}

		return networkAsyncTaskResponse;
	}

	@Override
	public InputStream getNewsFeeds(int pageNumber) 
	{
		try
		{
			Map params=new HashMap<String, String>();
//			params.put("paged", ""+pageNumber);
			params.put("page", ""+pageNumber);
			NetworkAsyncTaskResponse<String> response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.URL_SME_KHABAR, params);
			if(response.getResultObject()!=null)
			{
				return new ByteArrayInputStream(response.getResultObject().getBytes());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseChangePasswordDto> changePassword(String oldPassword, String newPassword)
	{
		ResponseChangePasswordDto responseChangePasswordDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			RequestChangePasswordDto requestChangePasswordDto=new RequestChangePasswordDto();
			requestChangePasswordDto.setNewPassword(newPassword);
			requestChangePasswordDto.setOldPassword(oldPassword);

			String requestChangePasswordDtoStrVal = objectMapper.writeValueAsString(requestChangePasswordDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_CHANGE_PASSWORD), requestChangePasswordDtoStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseChangePasswordDto = objectMapper.readValue(jp, ResponseChangePasswordDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseChangePasswordDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseChangePasswordDto>();
		ACError error = parseServerResponse(response, responseChangePasswordDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseChangePasswordDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto> deleteDeliveryAddress(String deliveryAddressCode)
	{
		ResponseDeleteDeliveryAddressDto responseDeleteDeliveryAddressDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			RequestDeleteDeliveryAddressDto requestDeleteDeliveryAddressDto=new RequestDeleteDeliveryAddressDto();
			requestDeleteDeliveryAddressDto.setCode(deliveryAddressCode);

			String requestChangePasswordDtoStrVal = objectMapper.writeValueAsString(requestDeleteDeliveryAddressDto);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_DELETE_DELIVERY_ADDRESS), requestChangePasswordDtoStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseDeleteDeliveryAddressDto = objectMapper.readValue(jp, ResponseDeleteDeliveryAddressDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto>();
		ACError error = parseServerResponse(response, responseDeleteDeliveryAddressDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseDeleteDeliveryAddressDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto> updateDeliveryAdresses(DeliveryAddress deliveryAddress)
	{
		ResponseUpdateDeliveryAddressDto responseUpdateDeliveryAddressDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			String requestUpdateDeliveryAddressStrVal = objectMapper.writeValueAsString(deliveryAddress);
			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_UPDATE_DELIVERY_ADDRESS), requestUpdateDeliveryAddressStrVal);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseUpdateDeliveryAddressDto = objectMapper.readValue(jp, ResponseUpdateDeliveryAddressDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto>();
		ACError error = parseServerResponse(response, responseUpdateDeliveryAddressDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseUpdateDeliveryAddressDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseSKUCategoryFetchDto> getSKUCategories()
	{
		ResponseSKUCategoryFetchDto responseSKUCategoryFetchDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
//			Map params=new HashMap<String, String>();
//			params.put("dealId", dealId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_SKU_CATEGORY_LIST), null);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSKUCategoryFetchDto = objectMapper.readValue(jp, ResponseSKUCategoryFetchDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSKUCategoryFetchDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSKUCategoryFetchDto>();
		ACError error = parseServerResponse(response, responseSKUCategoryFetchDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseSKUCategoryFetchDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseSKUSubCategoryFetchDto> getSKUSubCategories(String category) {
		ResponseSKUSubCategoryFetchDto responseSKUSubCategoryFetchDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("category", category);
			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_SKU_SUB_CATEGORY_LIST), params);
			if(response.getResultObject()!=null)
			{
				String res = response.getResultObject();
				res = res.replaceAll(",\" \"", "");
				res = res.replaceAll("\" \"", "");

				JsonParser jp = jsonFactory.createJsonParser(res);
				responseSKUSubCategoryFetchDto = objectMapper.readValue(jp, ResponseSKUSubCategoryFetchDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSKUSubCategoryFetchDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSKUSubCategoryFetchDto>();
		ACError error = parseServerResponse(response, responseSKUSubCategoryFetchDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseSKUSubCategoryFetchDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseSKUsFetchDto> getSKUs(String skuId,
																  String category,
																  String subCategory,
																  String brand,
																  String longdesc,
																  int pageSize,
																  int pageIndex)
	{
		ResponseSKUsFetchDto responseSKUsFetchDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("skuid", skuId);
			params.put("category", category);
			params.put("subcategory", subCategory);
			params.put("brand", brand);
			params.put("longdesc", longdesc);
			params.put("pageSize", pageSize);
			params.put("pageIndex", pageIndex);

			response = NetworkUtils.getResponse(context, MethodTypes.GET, APIs.getURL(context, APIs.URL_GET_SKU_LIST), params);
			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseSKUsFetchDto = objectMapper.readValue(jp, ResponseSKUsFetchDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseSKUsFetchDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSKUsFetchDto>();
		ACError error = parseServerResponse(response, responseSKUsFetchDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseSKUsFetchDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseCustomersDto> getCustomersList(CustomerSearchFilter customerSearchFilter, String searchQuery, long currentPage, long pageSize, boolean isLoadmore) {
		ResponseCustomersDto responseCustomersDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			RequestSearchOrgForKAMDto requestDto = new RequestSearchOrgForKAMDto();
			RequestSearchOrgForKAMDtoOrganization requestDtoOrganization = new RequestSearchOrgForKAMDtoOrganization();

			if(customerSearchFilter== CustomerSearchFilter.EMAIL)
			{
				requestDtoOrganization.setEmail(searchQuery);
			}
			else if(customerSearchFilter== CustomerSearchFilter.ORG_NAME)
			{
				requestDtoOrganization.setCompany_name(searchQuery);
			}
			else if(customerSearchFilter== CustomerSearchFilter.SME_ID)
			{
				requestDtoOrganization.setSmeId(searchQuery);
			}
			else if(customerSearchFilter== CustomerSearchFilter.PHONE)
			{
				requestDtoOrganization.setPhone(searchQuery);
			}
			else if(customerSearchFilter== CustomerSearchFilter.LAST_TEN)
			{
				requestDtoOrganization.setLastTransacted(null);
			}
			//add filter
			requestDto.setOrganisation(requestDtoOrganization);
			String requestSearchOrgForKAMDtoStrVal = objectMapper.writeValueAsString(requestDto);
			String url = APIs.getURL(context, APIs.URL_GET_CUSTOMER_LIST);
			url = url + "?pageIndex="+currentPage+"&pageSize="+pageSize;
			response = NetworkUtils.getResponse(context, url , requestSearchOrgForKAMDtoStrVal);

			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseCustomersDto = objectMapper.readValue(jp, ResponseCustomersDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseCustomersDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseCustomersDto>();
		ACError error = parseServerResponse(response, responseCustomersDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseCustomersDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto> actOnBehalfOf(Customer customer) {
		ResponseActOnBehalfOfDto responseActOnBehalfOfDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			String customerStrVal = "{\"organisation\":"+objectMapper.writeValueAsString(customer)+"}";

			response = NetworkUtils.getResponse(context, APIs.getURL(context, APIs.URL_ACT_ON_BEHALF_OF) , customerStrVal);

			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseActOnBehalfOfDto = objectMapper.readValue(jp, ResponseActOnBehalfOfDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto>();
		ACError error = parseServerResponse(response, responseActOnBehalfOfDto);
		if(error!=null)
		{
			networkAsyncTaskResponse.setError(error);
		}
		else
		{
			networkAsyncTaskResponse.setResultObject(responseActOnBehalfOfDto);
		}
		return networkAsyncTaskResponse;
	}

	@Override
	public ResponseGeoPointsDto drawRouteOnMap(double startLattitude, double startLongitude, double endLattitude, double endLongitude)
	{
		ResponseGeoPointsDto responseGeoPointsDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			String output="json";
			String parameters="origin="+startLattitude+","+startLongitude+"&"+"destination="+endLattitude+","+endLongitude;
			String sensor="sensor=false";
			String url="https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&"+sensor;

			response = NetworkUtils.getResponse(context, MethodTypes.GET , url, null);

			if(response.getResultObject()!=null)
			{
				List<List<HashMap<String,String>>> routes=null;
				JSONObject jObejct;
				try{
					jObejct=new JSONObject(response.getResultObject());
					GoogleGeoPointsParser jParser=new GoogleGeoPointsParser();
					routes=jParser.parse(jObejct);
					responseGeoPointsDto = new ResponseGeoPointsDto();
					responseGeoPointsDto.setGeoPoints(routes);
				}
				catch(Exception e)
				{
					Log.d("JSON PARSING ERROR", e.getMessage());
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return responseGeoPointsDto;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseTrackingStatusDto> getTrackingStatus(String smeId) {
		ResponseTrackingStatusDto responseTrackingStatusDto=null;
		NetworkAsyncTaskResponse<String> response=null;
		try
		{
			Map params=new HashMap<String, String>();
			params.put("sme_id", smeId);
			response = NetworkUtils.getResponse(context, MethodTypes.GET , APIs.URL_TRACKING_STATUS, params);

			if(response.getResultObject()!=null)
			{
				JsonParser jp = jsonFactory.createJsonParser(response.getResultObject());
				responseTrackingStatusDto = objectMapper.readValue(jp, ResponseTrackingStatusDto.class);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		NetworkAsyncTaskResponse<ResponseTrackingStatusDto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseTrackingStatusDto>();
		networkAsyncTaskResponse.setResultObject(responseTrackingStatusDto);
		return networkAsyncTaskResponse;
	}
}
