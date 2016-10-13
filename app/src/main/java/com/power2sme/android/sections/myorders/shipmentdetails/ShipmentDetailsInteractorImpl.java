package com.power2sme.android.sections.myorders.shipmentdetails;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseSalesOrdersDto;
import com.power2sme.android.dtos.response.ResponseShipmentDetailsDto;
import com.power2sme.android.sections.myorders.list.OrderTypes;

public class ShipmentDetailsInteractorImpl implements IShipmentDetailsInteractor
{
	Context context;
	public ShipmentDetailsInteractorImpl(Context context)
	{
		this.context = context;
	}
	@Override
	public void getOrderDetails(final String orderID,final OnOrderDetailsListener onOrderDetailsListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseSalesOrdersDto > >(context, new INetworkAsyncTaskCallbacks<ResponseSalesOrdersDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onOrderDetailsListener.onOrderDetailsStart();
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

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getSalesOrders(0, 5, "", orderID, OrderTypes.ALL);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseSalesOrdersDto> networkAsyncTaskResponse) 
			{
				onOrderDetailsListener.onOrderDetailsEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onOrderDetailsListener.onOrderDetailsError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseSalesOrdersDto responseSalesOrdersDto=(ResponseSalesOrdersDto) networkAsyncTaskResponse.getResultObject();
					onOrderDetailsListener.onOrderDetailsSuccess(responseSalesOrdersDto);
				}
			}
		}).execute();
	}
	
	@Override
	public void getShipmentDetails(final String orderId, final OnShipmentDetailsListener onShipmentDetailsListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseShipmentDetailsDto > >(context, new INetworkAsyncTaskCallbacks<ResponseShipmentDetailsDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onShipmentDetailsListener.onShipmentDetailsStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseShipmentDetailsDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseShipmentDetailsDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseShipmentDetailsDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getShipmentDetails(orderId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseShipmentDetailsDto> networkAsyncTaskResponse) 
			{
				onShipmentDetailsListener.onShipmentDetailsEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onShipmentDetailsListener.onShipmentDetailsError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseShipmentDetailsDto responseShipmentDetailsDto=(ResponseShipmentDetailsDto) networkAsyncTaskResponse.getResultObject();
					onShipmentDetailsListener.onShipmentDetailsSuccess(responseShipmentDetailsDto);
				}
			}
		}).execute();
	}
}
