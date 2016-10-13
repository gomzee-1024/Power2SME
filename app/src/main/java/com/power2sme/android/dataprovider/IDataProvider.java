package com.power2sme.android.dataprovider;

import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.request.RequestAddCampaignDto;
import com.power2sme.android.dtos.request.RequestRegisterOrgDto;
import com.power2sme.android.dtos.response.ResponseAcceptRFQDto;
import com.power2sme.android.dtos.response.ResponseAccountSummeryDto;
import com.power2sme.android.dtos.response.ResponseActOnBehalfOfDto;
import com.power2sme.android.dtos.response.ResponseAddCampaignDto;
import com.power2sme.android.dtos.response.ResponseAddNewRFQDto;
import com.power2sme.android.dtos.response.ResponseChangePasswordDto;
import com.power2sme.android.dtos.response.ResponseContactInfoEditDto;
import com.power2sme.android.dtos.response.ResponseContactInfoListDto;
import com.power2sme.android.dtos.response.ResponseCustomersDto;
import com.power2sme.android.dtos.response.ResponseDealsByLocationDto;
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
import com.power2sme.android.entities.ReOrder;
import com.power2sme.android.entities.State;
import com.power2sme.android.entities.v3.Customer;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.sections.agentlogin.CustomerSearchFilter;
import com.power2sme.android.sections.myorders.list.OrderTypes;
import com.power2sme.android.sections.myrfqs.list.RFQTypes;

import java.io.File;
import java.io.InputStream;

public interface IDataProvider 
{
	NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> getServerPrefix(String prefixApiUrl);
	NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto>  isRegisterdInERP(String smeID); 
	NetworkAsyncTaskResponse<ResponseSMELoginDto> smeLogin(String emailId, String password);
	NetworkAsyncTaskResponse<ResponseGetStatesDto> getStates();
	NetworkAsyncTaskResponse<ResponseGetCitiesDto> getCities(State state);
	NetworkAsyncTaskResponse<ResponseGetDealsDto> getDeals(long pageIndex, long pageSize, String filterValue);
	NetworkAsyncTaskResponse<ResponseAddCampaignDto> addCampaign(RequestAddCampaignDto campaign);
	NetworkAsyncTaskResponse<ResponseForgotPasswordDto> forgotPassword(String emailId);
	NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto> getAccountUpdates(long currentPage, long PageSize, String strsmeid);
	
	NetworkAsyncTaskResponse<ResponseSalesOrdersDto> getSalesOrders(long currentPageIndex, long ordersPerPage, String smeId, String orderNo, OrderTypes orderTypes);
	NetworkAsyncTaskResponse<ResponseSalesOrdersDto> getSearchSalesOrders(long currentPageIndex, long ordersPerPage, String smeId, String orderNo, OrderTypes orderTypes);
	
	NetworkAsyncTaskResponse<ResponseRFQsDto> getRFQs(long currentPage, long pageSize, String smeId, RFQTypes rfqTypes);
	NetworkAsyncTaskResponse<ResponseRFQsDto> getRFQDetails(String rfqId);
	NetworkAsyncTaskResponse<ResponseAcceptRFQDto> acceptRFQ(String orderNo, String smeId);
	NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto> requestForRequoteRFQ(String orderNo, String smeId);
	
	NetworkAsyncTaskResponse<ResponseContactInfoListDto> getContactInfoList(String smeId);
	NetworkAsyncTaskResponse<ResponseContactInfoEditDto> editContactInfo(ContactInfo contactInfo);
	
	NetworkAsyncTaskResponse<ResponseDeliveryAddressDto> getDeliveryAdresses(String smeId);
	NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto> insertDeliveryAdresses(DeliveryAddress deliveryAddress);
	NetworkAsyncTaskResponse<ResponseDeliveryAddressEditDto> editDeliveryAdresses(DeliveryAddress deliveryAddress);
	NetworkAsyncTaskResponse<ResponseSMEProfileDto> getSMEProfile(String smeId);
	NetworkAsyncTaskResponse<ResponseSMEProfileUpdateDto> editSMEProfile(GeneralInformation generalInformation);
	
	NetworkAsyncTaskResponse<ResponseUploadPODto> uploadPO(String rfqNo, File file, String smeId);
	NetworkAsyncTaskResponse<ResponsePostReOrderDto> postReOrder(ReOrder reOrder);
	NetworkAsyncTaskResponse<ResponseShipmentDetailsDto> getShipmentDetails(String orderId);
	NetworkAsyncTaskResponse<ResponseAddNewRFQDto> addNewRFQ(NewRFQ_v3 newRFQ);
	NetworkAsyncTaskResponse<ResponseUnitOfMeasureDto> getUnitOfMeasure(String category);
	NetworkAsyncTaskResponse<ResponseMaterialCategoryListDto> getMaterialCategoryList();
	NetworkAsyncTaskResponse<ResponseSMESignupDto> smeSignup(RequestRegisterOrgDto registerOrgDto);
	NetworkAsyncTaskResponse<ResponseGetDesignationsDto> getDesignations();
	NetworkAsyncTaskResponse<ResponseGetTaxPreferenceListDto> getTaxPreferenceList();
	NetworkAsyncTaskResponse<ResponseGetUrgencyListDto> getUrgencyList();
	NetworkAsyncTaskResponse<ResponseLogoutDto> logoutApp(String emailId);
	NetworkAsyncTaskResponse<ResponseContactInfoListDto> getContactInfo(String contactId);
	NetworkAsyncTaskResponse<ResponseOutstandingDto> getOutstandingAmout(String strsmeid);
	NetworkAsyncTaskResponse loginBySocialNetwork(String socialNetworkType, String email, String firstName, String lastName);
	NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> registerDeviceRegisterationId(String regId,String firstLaunchFlag);
	NetworkAsyncTaskResponse<ResponseAccountSummeryDto> getAccountSummery();
	NetworkAsyncTaskResponse<ResponseGetDealsByIDDto> getDealById(String dealId);
	InputStream getNewsFeeds(int pageNumber);
	NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto> getTruckLocations(String orderId);
	NetworkAsyncTaskResponse<ResponseChangePasswordDto> changePassword(String oldPassword, String newPassword);
	NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto> deleteDeliveryAddress(String deliveryAddressCode);

	NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto> updateDeliveryAdresses(DeliveryAddress deliveryAddress);

	NetworkAsyncTaskResponse<ResponseSKUCategoryFetchDto> getSKUCategories();

	NetworkAsyncTaskResponse<ResponseSKUSubCategoryFetchDto> getSKUSubCategories(String category);

	NetworkAsyncTaskResponse<ResponseSKUsFetchDto> getSKUs(String skuId,
														   String category,
														   String subCategory,
														   String brand,
														   String longdesc,
														   int pageSize,
														   int pageIndex);

	NetworkAsyncTaskResponse<ResponseCustomersDto> getCustomersList(CustomerSearchFilter customerSearchFilter, String searchQuery, long currentPage, long pageSize, boolean isLoadmore);

	NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto> actOnBehalfOf(Customer customer);


	ResponseGeoPointsDto drawRouteOnMap(double startLattitude, double startLongitude, double endLattitude, double endLongitude);

	NetworkAsyncTaskResponse<ResponseTrackingStatusDto> getTrackingStatus(String smeId);

//	NetworkAsyncTaskResponse<ResponseDealsByLocationDto> getDealsByLocation(String pincode, String smeId, String skuCode);
}
