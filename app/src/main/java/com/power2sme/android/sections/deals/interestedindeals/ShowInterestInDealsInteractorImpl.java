package com.power2sme.android.sections.deals.interestedindeals;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseAddNewRFQDto;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.sections.myrfqs.add.OnAddRFQListener;

public class ShowInterestInDealsInteractorImpl implements IShowInterestInDealsInteractor
{
	Context context;
	public ShowInterestInDealsInteractorImpl(Context context)
	{
		this.context=context;
	}
	
	@Override
	public void addNewRFQ(final NewRFQ_v3 newRFQ, final OnAddRFQListener onAddRFQListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseAddNewRFQDto > >(context, new INetworkAsyncTaskCallbacks<ResponseAddNewRFQDto>()
		{
			@Override
			public void onPreExecute()
			{
				onAddRFQListener.onAddRFQStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseAddNewRFQDto> doInBackground()
			{
				NetworkAsyncTaskResponse<ResponseAddNewRFQDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAddNewRFQDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().addNewRFQ(newRFQ);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseAddNewRFQDto> networkAsyncTaskResponse)
			{
				onAddRFQListener.onAddRFQEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onAddRFQListener.onAddRFQError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseAddNewRFQDto responseAddNewRFQDto=(ResponseAddNewRFQDto)networkAsyncTaskResponse.getResultObject();
					onAddRFQListener.onAddRFQSuccess(responseAddNewRFQDto);
				}
			}
		}).execute();
	}
}
