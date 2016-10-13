package com.power2sme.android.sections.deals.postorder;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.request.RequestAddCampaignDto;
import com.power2sme.android.dtos.response.ResponseAddCampaignDto;
import com.power2sme.android.dtos.response.ResponseGetDealsByIDDto;

public class DealsPostOrderFragmentInteractorImpl implements IDealsPostOrderFragmentInteractor
{
	Context context;
	public DealsPostOrderFragmentInteractorImpl(Context context)
	{
		this.context=context;
	}
	@Override
	public void postOrder(final RequestAddCampaignDto campaign, final OnPostDealsOrderListener onDealsOrderPostListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseAddCampaignDto > >(context, new INetworkAsyncTaskCallbacks<ResponseAddCampaignDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onDealsOrderPostListener.onPostDealsOrderStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseAddCampaignDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseAddCampaignDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAddCampaignDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().addCampaign(campaign);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseAddCampaignDto> networkAsyncTaskResponse) 
			{
				onDealsOrderPostListener.onPostDealsOrderEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDealsOrderPostListener.onPostDealsOrderError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseAddCampaignDto responseAddCampaignDto=(ResponseAddCampaignDto) networkAsyncTaskResponse.getResultObject();
					onDealsOrderPostListener.onPostDealsOrderSuccess(responseAddCampaignDto);
				}
			}
		}).execute();
	}
	@Override
	public void getDealById(final String dealId, final OnGetDealsByIDListener onGetDealsByIDListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseGetDealsByIDDto > >(context, new INetworkAsyncTaskCallbacks<ResponseGetDealsByIDDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onGetDealsByIDListener.onGetDealsByIDStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseGetDealsByIDDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseGetDealsByIDDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetDealsByIDDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getDealById(dealId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseGetDealsByIDDto> networkAsyncTaskResponse) 
			{
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onGetDealsByIDListener.onGetDealsByIDError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseGetDealsByIDDto responseGetDealsByIDDto=(ResponseGetDealsByIDDto) networkAsyncTaskResponse.getResultObject();
					onGetDealsByIDListener.onGetDealsByIDSuccess(responseGetDealsByIDDto);
				}
				onGetDealsByIDListener.onGetDealsByIDEnd();
			}
		}).execute();
	}
}
