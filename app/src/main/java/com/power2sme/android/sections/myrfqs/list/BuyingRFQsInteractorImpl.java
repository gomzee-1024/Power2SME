package com.power2sme.android.sections.myrfqs.list;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseAcceptRFQDto;
import com.power2sme.android.dtos.response.ResponseRFQsDto;
import com.power2sme.android.dtos.response.ResponseRequestForRequoteRFQDto;
import com.power2sme.android.dtos.response.ResponseUploadPODto;
import com.power2sme.android.entities.v3.Wishlist_v3;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACErrorCodes;

import java.io.File;

public class BuyingRFQsInteractorImpl implements IBuyingRFQsInteractor 
{
	Context context;
	public BuyingRFQsInteractorImpl(Context context)
	{
		this.context=context;
	}
	
	@Override
	public void acceptQuote(final Wishlist_v3 rfq, final String orderNo, final String smeId, final OnAcceptQuoteListener onAcceptQuoteListener)
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseAcceptRFQDto>>(context, new INetworkAsyncTaskCallbacks<ResponseAcceptRFQDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onAcceptQuoteListener.onAcceptQuoteStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseAcceptRFQDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseAcceptRFQDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseAcceptRFQDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().acceptRFQ(orderNo, smeId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseAcceptRFQDto> networkAsyncTaskResponse) 
			{
				onAcceptQuoteListener.onAcceptQuoteEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onAcceptQuoteListener.onAcceptQuoteError(rfq, networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseAcceptRFQDto responseAcceptRFQDto=(ResponseAcceptRFQDto) networkAsyncTaskResponse.getResultObject();
					onAcceptQuoteListener.onAcceptQuoteSuccess(rfq);
				}
			}
		}).execute();
	}
	@Override
	public void requestRequote(final Wishlist_v3 rfq, final String orderNo, final String smeId,final OnRequestQuoteListener onRequestQuoteListener)
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto>>(context, new INetworkAsyncTaskCallbacks<ResponseRequestForRequoteRFQDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onRequestQuoteListener.onRequestQuoteStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().requestForRequoteRFQ(orderNo, smeId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseRequestForRequoteRFQDto> networkAsyncTaskResponse) 
			{
				onRequestQuoteListener.onRequestQuoteEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onRequestQuoteListener.onRequestQuoteError(rfq, networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseRequestForRequoteRFQDto responseRequestForRequoteRFQDto=(ResponseRequestForRequoteRFQDto) networkAsyncTaskResponse.getResultObject();
					onRequestQuoteListener.onRequestQuoteSuccess(rfq);
				}
			}
		}).execute();
	}
	@Override
	public void getRFQs(final long currentPage,final long pageSize, final String smeId, final RFQTypes rfqTypes,final OnRFQsRetrievalListener onRFQsRetrievalListener, final boolean isLoadmore) 
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseRFQsDto>>(context, new INetworkAsyncTaskCallbacks<ResponseRFQsDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onRFQsRetrievalListener.onRFQsRetrievalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseRFQsDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseRFQsDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseRFQsDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getRFQs(currentPage,pageSize,smeId, rfqTypes);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseRFQsDto> networkAsyncTaskResponse) 
			{
				onRFQsRetrievalListener.onRFQsRetrievalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onRFQsRetrievalListener.onRFQsRetrievalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseRFQsDto responseRFQsDto=(ResponseRFQsDto) networkAsyncTaskResponse.getResultObject();
					onRFQsRetrievalListener.onRFQsRetrievalSuccess(rfqTypes, responseRFQsDto, isLoadmore);
				}
			}
		}).execute();
	}

	@Override
	public void uploadPO(final String rfqNo, final File file,final String smeId,final OnPOUploadListener onPOUploadListener)
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseUploadPODto>>(context, new INetworkAsyncTaskCallbacks<ResponseUploadPODto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onPOUploadListener.onPOUploadStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseUploadPODto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseUploadPODto> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseUploadPODto>();
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				if(rfqNo!=null && file.exists())
				{
					networkAsyncTaskResponse = factoryResponse.getiDataProvider().uploadPO(rfqNo, file, smeId);
				}
				else
				{
					networkAsyncTaskResponse.setError(new ACError(ACErrorCodes.SERVER_ERROR, " "));
				}
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseUploadPODto> networkAsyncTaskResponse) 
			{
				onPOUploadListener.onPOUploadEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onPOUploadListener.onPOUploadError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseUploadPODto responseUploadPODto=(ResponseUploadPODto) networkAsyncTaskResponse.getResultObject();
					onPOUploadListener.onPOUploadSuccess(responseUploadPODto);
				}
			}
		}).execute();
	}

	@Override
	public void getRFQDetails(final String rfqId, final OnRFQDetailsListener onRFQDetailsListener) 
	{
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ResponseRFQsDto>>(context, new INetworkAsyncTaskCallbacks<ResponseRFQsDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onRFQDetailsListener.onRFQDetailsStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseRFQsDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseRFQsDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseRFQsDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getRFQDetails(rfqId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseRFQsDto> networkAsyncTaskResponse) 
			{
				onRFQDetailsListener.onRFQDetailsEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onRFQDetailsListener.onRFQDetailsError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseRFQsDto responseRFQsDto=(ResponseRFQsDto) networkAsyncTaskResponse.getResultObject();
					onRFQDetailsListener.onRFQDetailsSuccess(responseRFQsDto);
				}
			}
		}).execute();
	}
}
