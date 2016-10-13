package com.power2sme.android.sections.myorders.list;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.dtos.response.ResponseTrackingStatusDto;
import com.power2sme.android.entities.SalesOrder;

public class BuyingOrdersInteractorImpl implements IBuyingOrdersInteractor
{
	Context context;
	public BuyingOrdersInteractorImpl(Context context)
	{
		this.context=context;
	}
	@Override
	public void uploadOrder(SalesOrder salesOrder, OnPostReOrderListener onUploadOrderListener) 
	{
		// TODO web service pending for upload order
	}
	@Override
	public void getDeliveryAddress(OnDeliveryAddressRetrievalListener onDeliveryAddressRetrievalListener) 
	{
		// TODO web service pending for get delivery address
	}
	@Override
	public void searchOrder(final long currentPageIndex,final long ordersPerPage,final  String smeId,final  String orderNo, final OrderTypes orderTypes, final OnSearchOrderListener onSearchOrderListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseSalesOrdersDto > >(context, new INetworkAsyncTaskCallbacks<ResponseSalesOrdersDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onSearchOrderListener.onSearchOrderStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseSalesOrdersDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseSalesOrdersDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSalesOrdersDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getSearchSalesOrders(currentPageIndex, ordersPerPage, smeId, orderNo, orderTypes);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseSalesOrdersDto> networkAsyncTaskResponse) 
			{
				onSearchOrderListener.onSearchOrderEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onSearchOrderListener.onSearchOrderError(networkAsyncTaskResponse.getError(), orderNo);
				}
				else
				{
					ResponseSalesOrdersDto responseSalesOrdersDto=(ResponseSalesOrdersDto) networkAsyncTaskResponse.getResultObject();
					onSearchOrderListener.onSearchOrderSuccess(orderTypes, responseSalesOrdersDto);
				}
			}
		}).execute();
	}
	@Override
	public void getOrders(final long currentPageIndex,final long ordersPerPage,final  String smeId,final  String orderNo, final OrderTypes orderTypes, final OnOrderRetrievalListener onOrderRetrievalListener,final boolean isLoadmore) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseSalesOrdersDto > >(context, new INetworkAsyncTaskCallbacks<ResponseSalesOrdersDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onOrderRetrievalListener.onOrderRetrievalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseSalesOrdersDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseSalesOrdersDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseSalesOrdersDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getSalesOrders(currentPageIndex, ordersPerPage, smeId, orderNo, orderTypes);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseSalesOrdersDto> networkAsyncTaskResponse) 
			{
				onOrderRetrievalListener.onOrderRetrievalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onOrderRetrievalListener.onOrderRetrievalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseSalesOrdersDto responseSalesOrdersDto=(ResponseSalesOrdersDto) networkAsyncTaskResponse.getResultObject();
					onOrderRetrievalListener.onOrderRetrievalSuccess(orderTypes, responseSalesOrdersDto, isLoadmore);
				}
			}
		}).execute();
	}

	///////////////////////////////////////////////////////////////

	@Override
	public void getTrackingStatus(final String smeId, final OnTrackingStatusListener onTrackingStatusListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse <ResponseTrackingStatusDto> >(context, new INetworkAsyncTaskCallbacks<ResponseTrackingStatusDto>()
		{
			@Override
			public void onPreExecute()
			{
				onTrackingStatusListener.onTrackingStatusStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseTrackingStatusDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseTrackingStatusDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseTrackingStatusDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getTrackingStatus(smeId);
				return networkAsyncTaskResponse;
			}

			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseTrackingStatusDto> networkAsyncTaskResponse)
			{
				onTrackingStatusListener.onTrackingStatusEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onTrackingStatusListener.onTrackingStatusError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseTrackingStatusDto responseTrackingStatusDto=(ResponseTrackingStatusDto) networkAsyncTaskResponse.getResultObject();
					onTrackingStatusListener.onTrackingStatusSuccess(responseTrackingStatusDto);
				}
			}
		}).execute();
	}

//	@Override
//	public void uploadPO(final File file,final String fileName,final OnUploadPOListener onUploadPOListener) 
//	{
//		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseUploadPODto > >(context, new INetworkAsyncTaskCallbacks<ResponseUploadPODto>() 
//    	{
//			@Override
//			public void onPreExecute() 
//			{
//				onUploadPOListener.onUploadPOStart();
//			}
//			@Override
//			public NetworkAsyncTaskResponse<ResponseUploadPODto> doInBackground() 
//			{
//				NetworkAsyncTaskResponse<ResponseUploadPODto> networkAsyncTaskResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK).uploadPO(file, fileName);
//				return networkAsyncTaskResponse;
//			}
//			@Override
//			public void onPostExecute(NetworkAsyncTaskResponse<ResponseUploadPODto> networkAsyncTaskResponse) 
//			{
//				if(networkAsyncTaskResponse.getError()!=null)
//				{
//					onUploadPOListener.onUploadPOError(networkAsyncTaskResponse.getError());
//				}
//				else
//				{
//					ResponseUploadPODto ResponseUploadPODto=(ResponseUploadPODto) networkAsyncTaskResponse.getResultObject();
//					onUploadPOListener.onUploadPOSuccess();
//				}
//				onUploadPOListener.onUploadPOEnd();
//			}
//		}).execute();
//	}
}
