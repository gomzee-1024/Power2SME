package com.power2sme.android.sections.agentlogin;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseActOnBehalfOfDto;
import com.power2sme.android.dtos.response.ResponseCustomersDto;
import com.power2sme.android.entities.v3.Customer;

/**
 * Created by tausif on 15/7/15.
 */
public class CustomerSelectionInteractorImpl implements ICustomerSelectionInteractor
{
    Context context;
    public CustomerSelectionInteractorImpl(Context context)
    {
        this.context=context;
    }

    @Override
    public void getCustomersList(final CustomerSearchFilter customerSearchFilter, final String searchQuery, final long currentPage, final long pageSize, final boolean isLoadmore, final OnCustomersRetrievalListener listener)
    {
        new NetworkAsyncTask<NetworkAsyncTaskResponse<ResponseCustomersDto>>(context, new INetworkAsyncTaskCallbacks<ResponseCustomersDto>()
        {
            @Override
            public void onPreExecute()
            {
                listener.onCustomersRetrievalStart();
            }
            @Override
            public NetworkAsyncTaskResponse<ResponseCustomersDto> doInBackground()
            {
                NetworkAsyncTaskResponse<ResponseCustomersDto> networkAsyncTaskResponse=null;
                FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
                if(factoryResponse.getError()!=null)
                {
                    networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseCustomersDto>();
                    networkAsyncTaskResponse.setError(factoryResponse.getError());
                    return networkAsyncTaskResponse;
                }

                networkAsyncTaskResponse = factoryResponse.getiDataProvider().getCustomersList(customerSearchFilter, searchQuery, currentPage, pageSize, isLoadmore);
                return networkAsyncTaskResponse;
            }
            @Override
            public void onPostExecute(NetworkAsyncTaskResponse<ResponseCustomersDto> networkAsyncTaskResponse)
            {
                listener.onCustomersRetrievalEnd();
                if(networkAsyncTaskResponse.getError()!=null)
                {
                    listener.onCustomersRetrievalError(networkAsyncTaskResponse.getError());
                }
                else
                {
                    ResponseCustomersDto responseCustomersDto=(ResponseCustomersDto) networkAsyncTaskResponse.getResultObject();
                    listener.onCustomersRetrievalSuccess(responseCustomersDto, isLoadmore);
                }
            }
        }).execute();
    }

    @Override
    public void actOnBehalfOf(final Customer customer,final OnActOnBehalfOfListener listener) {
        new NetworkAsyncTask<NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto>>(context, new INetworkAsyncTaskCallbacks<ResponseActOnBehalfOfDto>()
        {
            @Override
            public void onPreExecute()
            {
                listener.onActOnBehalfOfStart();
            }
            @Override
            public NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto> doInBackground()
            {
                NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto> networkAsyncTaskResponse=null;
                FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
                if(factoryResponse.getError()!=null)
                {
                    networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto>();
                    networkAsyncTaskResponse.setError(factoryResponse.getError());
                    return networkAsyncTaskResponse;
                }

                networkAsyncTaskResponse = factoryResponse.getiDataProvider().actOnBehalfOf(customer);
                return networkAsyncTaskResponse;
            }
            @Override
            public void onPostExecute(NetworkAsyncTaskResponse<ResponseActOnBehalfOfDto> networkAsyncTaskResponse)
            {
                listener.onActOnBehalfOfEnd();
                if(networkAsyncTaskResponse.getError()!=null)
                {
                    listener.onActOnBehalfOfError(networkAsyncTaskResponse.getError());
                }
                else
                {
                    ResponseActOnBehalfOfDto responseActOnBehalfOfDto=(ResponseActOnBehalfOfDto) networkAsyncTaskResponse.getResultObject();
                    listener.onActOnBehalfOfSuccess(responseActOnBehalfOfDto);
                }
            }
        }).execute();
    }
}
