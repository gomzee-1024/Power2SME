package com.power2sme.android.sections.deals.list;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseGetDealsDto;

public class DealsInteractorImpl implements IDealsInteractor
{
	Context context;
	public DealsInteractorImpl(Context context)
	{
		this.context=context;
	}
	@Override
	public void getDeals(final long pageIndex, final long pageSize, final String filterValue, final OnRetreivalDealsListener onDealsRetreivalListener, final boolean isLoadmore)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseGetDealsDto > >(context, new INetworkAsyncTaskCallbacks() 
    	{
			@Override
			public void onPreExecute() 
			{
				onDealsRetreivalListener.onRetreivalDealsStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseGetDealsDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseGetDealsDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetDealsDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getDeals(pageIndex, pageSize, filterValue);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse networkAsyncTaskResponse) 
			{
				onDealsRetreivalListener.onRetreivalDealsEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDealsRetreivalListener.onRetreivalDealsError(filterValue, networkAsyncTaskResponse.getError(),isLoadmore);
				}
				else
				{
					ResponseGetDealsDto responseGetDealsDto=(ResponseGetDealsDto) networkAsyncTaskResponse.getResultObject();
					onDealsRetreivalListener.onRetreivalDealsSuccess(filterValue, responseGetDealsDto, isLoadmore);
				}
			}
		}).execute();
	}
}
