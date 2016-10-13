package com.power2sme.android.dataprovider.database;

import android.content.Context;

import com.power2sme.android.dataprovider.IDataProvider;
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

public class SQLiteDataProvider implements IDataProvider
{
	private static SQLiteDataProvider sqliteDataProvider;
	Context context;
	private SQLiteDataProvider(Context context)
	{
		this.context=context;
	}
	public static SQLiteDataProvider getInstance(Context context)
	{
		if(sqliteDataProvider==null)
		{
			sqliteDataProvider=new SQLiteDataProvider(context);
		}
		return sqliteDataProvider;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetServerPrefixDto> getServerPrefix(String prefixApiUrl) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSMELoginDto> smeLogin(
			String emailId, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetStatesDto> getStates() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetCitiesDto> getCities(State state) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public NetworkAsyncTaskResponse<ResponseGetDealsDto> getDeals(long pageIndex, long pageSize, String filterValue) {
        return null;
    }

	@Override
	public NetworkAsyncTaskResponse<ResponseAddCampaignDto> addCampaign(
			RequestAddCampaignDto campaign) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseForgotPasswordDto> forgotPassword(
			String emailId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetAccountUpdatesDto> getAccountUpdates(
			long currentPage, long PageSize, String strsmeid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public NetworkAsyncTaskResponse<ResponseDeliveryAddressDto> getDeliveryAdresses(
			String smeId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseDeliveryAddressInsertDto> insertDeliveryAdresses(
			DeliveryAddress deliveryAddress) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseDeliveryAddressEditDto> editDeliveryAdresses(
			DeliveryAddress deliveryAddress) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSMEProfileDto> getSMEProfile(
			String smeId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSMEProfileUpdateDto> editSMEProfile(
			GeneralInformation generalInformation) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseRFQsDto> getRFQs(long currentPage,
			long pageSize, String smeId, RFQTypes rfqTypes) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseAcceptRFQDto> acceptRFQ(
			String orderNo, String smeId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto> requestForRequoteRFQ(
			String orderNo, String smeId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSalesOrdersDto> getSalesOrders(
			long currentPageIndex, long ordersPerPage, String smeId,
			String orderNo, OrderTypes orderTypes) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseContactInfoListDto> getContactInfoList(String smeId) 
	{
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseContactInfoEditDto> editContactInfo(ContactInfo contactInfo) 
	{
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseUploadPODto> uploadPO(String rfqNo, File file,String fileName)
	{
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponsePostReOrderDto> postReOrder(
			ReOrder reOrder) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseRFQsDto> getRFQDetails(String rfqId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseShipmentDetailsDto> getShipmentDetails(
			String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseMaterialCategoryListDto> getMaterialCategoryList() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSMESignupDto> smeSignup(
			RequestRegisterOrgDto registerOrgDto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseSalesOrdersDto> getSearchSalesOrders(
			long currentPageIndex, long ordersPerPage, String smeId,
			String orderNo, OrderTypes orderTypes) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetDesignationsDto> getDesignations() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseIsRegisterdInERPDto> isRegisterdInERP(
			String smeID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetTaxPreferenceListDto> getTaxPreferenceList() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetUrgencyListDto> getUrgencyList() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseLogoutDto> logoutApp(String emailId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseContactInfoListDto> getContactInfo(
			String contactId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseOutstandingDto> getOutstandingAmout(
			String strsmeid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse loginBySocialNetwork(String socialNetworkType,
			String email, String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseDeviceIdRegisterDto> registerDeviceRegisterationId(String regId, String firstLaunchFlag) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseAccountSummeryDto> getAccountSummery() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseGetDealsByIDDto> getDealById(
			String dealId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public InputStream getNewsFeeds(int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public NetworkAsyncTaskResponse<ResponseAddNewRFQDto> addNewRFQ(
			NewRFQ_v3 newRFQ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseUnitOfMeasureDto> getUnitOfMeasure(String category) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseTruckLocationsTrackingDto> getTruckLocations(
			String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseChangePasswordDto> changePassword(String oldPassword, String newPassword) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseDeleteDeliveryAddressDto> deleteDeliveryAddress(String deliveryAddressCode) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseUpdateDeliveryAddressDto> updateDeliveryAdresses(DeliveryAddress deliveryAddress) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseSKUCategoryFetchDto> getSKUCategories() {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseSKUSubCategoryFetchDto> getSKUSubCategories(String category) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseSKUsFetchDto> getSKUs(String skuId,
																  String category,
																  String subCategory,
																  String brand,
																  String longdesc,
																  int pageSize,
																  int pageIndex) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseCustomersDto> getCustomersList(CustomerSearchFilter customerSearchFilter, String searchQuery, long currentPage, long pageSize, boolean isLoadmore) {
		return null;
	}

	@Override
	public NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto> actOnBehalfOf(Customer customer) {
		return null;
	}

	@Override
	public ResponseGeoPointsDto drawRouteOnMap(double startLattitude, double startLongitude, double endLattitude, double endLongitude) {
		return null;
	}


	@Override
	public NetworkAsyncTaskResponse<ResponseTrackingStatusDto> getTrackingStatus(String smeId) {
		return null;
	}


}
