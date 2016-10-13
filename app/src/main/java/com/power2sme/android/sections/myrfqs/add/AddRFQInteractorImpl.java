package com.power2sme.android.sections.myrfqs.add;

import android.content.Context;
import android.widget.ProgressBar;

import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseAddNewRFQDto;
import com.power2sme.android.dtos.response.ResponseSKUSubCategoryFetchDto;
import com.power2sme.android.dtos.response.ResponseUploadPODto;
import com.power2sme.android.entities.UnitOfMeasure;
import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.entities.v3.TaxationPreference_v3;
import com.power2sme.android.entities.v3.Urgency_v3;
import com.power2sme.android.sections.myrfqs.list.OnPOUploadListener;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.customviews.BetterSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class AddRFQInteractorImpl implements IAddRFQInteractor 
{
	Context context;
	public AddRFQInteractorImpl(Context context) 
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
	////////////////////////////////////////////////
//	@Override
//	public void getMaterialCategoryList(final BetterSpinner spinner, final ProgressBar progressBar, final boolean isSerelizable, final OnShowMaterialCategoryListener onShowMaterialCategoryListener)
//	{
//		new NetworkAsyncTask < NetworkAsyncTaskResponse < ArrayList<String> > >(context, new INetworkAsyncTaskCallbacks<ArrayList<String>>()
//    	{
//			@Override
//			public void onPreExecute()
//			{
//				onShowMaterialCategoryListener.onShowMaterialCategoryStart(spinner, progressBar, isSerelizable);
//			}
//			@Override
//			public NetworkAsyncTaskResponse<ArrayList<String>> doInBackground()
//			{
//				NetworkAsyncTaskResponse<ArrayList<String>> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ArrayList<String>>();
//
//				Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(context);
//				if(lookupMasterTable!=null && lookupMasterTable.containsKey(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST))
//				{
//					ArrayList<String> catList = (ArrayList<String>)lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST);
//					networkAsyncTaskResponse.setResultObject(catList);
//				}
//				else
//				{
//					FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
//					if(factoryResponse.getError()!=null)
//					{
//						networkAsyncTaskResponse.setError(factoryResponse.getError());
//						return networkAsyncTaskResponse;
//					}
//					ArrayList<String> catList = factoryResponse.getiDataProvider().getMaterialCategoryList().getResultObject().getCategories();
//					if(catList!=null && catList.size()>0)
//					{
//						lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST, catList);
//						Utils.serializeLookupMasterTable(context, lookupMasterTable);
//						networkAsyncTaskResponse.setResultObject(catList);
//					}
//					else
//					{
//						networkAsyncTaskResponse.setError(factoryResponse.getError());
//						return networkAsyncTaskResponse;
//					}
//				}
//				return networkAsyncTaskResponse;
//			}
//			@Override
//			public void onPostExecute(NetworkAsyncTaskResponse<ArrayList<String>> networkAsyncTaskResponse)
//			{
//				onShowMaterialCategoryListener.onShowMaterialCategoryEnd(spinner, progressBar, isSerelizable);
//				if(networkAsyncTaskResponse.getError()!=null)
//				{
//					onShowMaterialCategoryListener.onShowMaterialCategoryError(spinner, progressBar, isSerelizable, networkAsyncTaskResponse.getError());
//				}
//				else
//				{
//					onShowMaterialCategoryListener.onShowMaterialCategorySuccess(spinner, progressBar, isSerelizable, networkAsyncTaskResponse.getResultObject());
//				}
//			}
//		}).execute();
//	}
	////////////////////////////////////////////////
	@Override
	public void getUnitsList(final String category, final BetterSpinner spinner, final ProgressBar progressBar, final OnShowUnitsListener onShowUnitsListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ArrayList<UnitOfMeasure> > >(context, new INetworkAsyncTaskCallbacks<ArrayList<UnitOfMeasure>>()
    	{
			@Override
			public void onPreExecute() 
			{
				onShowUnitsListener.onShowUnitsStart(spinner, progressBar);
			}
			@Override
			public NetworkAsyncTaskResponse<ArrayList<UnitOfMeasure>> doInBackground()
			{
				NetworkAsyncTaskResponse<ArrayList<UnitOfMeasure>> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ArrayList<UnitOfMeasure>>();
				Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(context);
				if (lookupMasterTable != null && lookupMasterTable.containsKey(Constants.LOOKUP_MASTER_TABLE_KEY_UNITS_LIST+category))
				{
					ArrayList<UnitOfMeasure> unitsList = (ArrayList<UnitOfMeasure>) lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_UNITS_LIST+category);
					networkAsyncTaskResponse.setResultObject(unitsList);
				}
				else
				{
					FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
					if(factoryResponse.getError()!=null)
					{
						networkAsyncTaskResponse.setError(factoryResponse.getError());
					}
					else
					{
						ArrayList<UnitOfMeasure> unitsList = factoryResponse.getiDataProvider().getUnitOfMeasure(category).getResultObject().getData();
						if(unitsList!=null && unitsList.size()>0)
						{
							networkAsyncTaskResponse.setResultObject(unitsList);
							lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_UNITS_LIST+category, unitsList);
							Utils.serializeLookupMasterTable(context, lookupMasterTable);
						}
						else
						{
							networkAsyncTaskResponse.setError(factoryResponse.getError());
						}
					}
				}
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ArrayList<UnitOfMeasure>> networkAsyncTaskResponse)
			{
				onShowUnitsListener.onShowUnitsEnd(spinner, progressBar);
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onShowUnitsListener.onShowUnitsError(spinner, progressBar, networkAsyncTaskResponse.getError());
				}
				else
				{
					onShowUnitsListener.onShowUnitsSuccess(category, spinner, progressBar, networkAsyncTaskResponse.getResultObject());
				}
			}
		}).execute();
	}

	////////////////////////////////////////////////
	@Override
	public void getUrgencyList(final BetterSpinner spinner, final ProgressBar progressBar, final OnUrgencyListLoadingListener onUrgencyListLoadingListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ArrayList<Urgency_v3> > >(context, new INetworkAsyncTaskCallbacks<ArrayList<Urgency_v3>>()
    	{
			@Override
			public void onPreExecute() 
			{
				onUrgencyListLoadingListener.onUrgencyListLoadingStart(spinner, progressBar);
			}
			@Override
			public NetworkAsyncTaskResponse<ArrayList<Urgency_v3>> doInBackground()
			{
				NetworkAsyncTaskResponse<ArrayList<Urgency_v3>> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ArrayList<Urgency_v3>>();

				Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(context);
				if (lookupMasterTable != null && lookupMasterTable.containsKey(Constants.LOOKUP_MASTER_TABLE_KEY_URGENCY_LIST))
				{
					ArrayList<Urgency_v3> urgencyList = (ArrayList<Urgency_v3>) lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_URGENCY_LIST);
					networkAsyncTaskResponse.setResultObject(urgencyList);
				}
				else
				{
					FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
					if(factoryResponse.getError()!=null)
					{
						networkAsyncTaskResponse.setError(factoryResponse.getError());
					}
					else
					{
						ArrayList<Urgency_v3> urgencyList = factoryResponse.getiDataProvider().getUrgencyList().getResultObject().getData();
						if(urgencyList!=null && urgencyList.size()>0)
						{
							networkAsyncTaskResponse.setResultObject(urgencyList);
							lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_URGENCY_LIST, urgencyList);
							Utils.serializeLookupMasterTable(context, lookupMasterTable);
						}
						else
						{
							networkAsyncTaskResponse.setError(factoryResponse.getError());
						}
					}
				}
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ArrayList<Urgency_v3>> networkAsyncTaskResponse)
			{
				onUrgencyListLoadingListener.onUrgencyListLoadingEnd(spinner, progressBar);
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onUrgencyListLoadingListener.onUrgencyListLoadingError(spinner, progressBar, networkAsyncTaskResponse.getError());
				}
				else
				{
					onUrgencyListLoadingListener.onUrgencyListLoadingSuccess(spinner, progressBar, networkAsyncTaskResponse.getResultObject());
				}
			}
		}).execute();
	}
	////////////////////////////////////////////////
	@Override
	public void getTaxationPrefsList(final BetterSpinner spinner, final ProgressBar progressBar, final OnTaxPreferenceLoadingListener onTaxPreferenceLoadingListener)
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ArrayList<TaxationPreference_v3> > >(context, new INetworkAsyncTaskCallbacks<ArrayList<TaxationPreference_v3>>()
    	{
			@Override
			public void onPreExecute() 
			{
				onTaxPreferenceLoadingListener.onTaxPreferenceLoadingStart(spinner, progressBar);
			}
			@Override
			public NetworkAsyncTaskResponse<ArrayList<TaxationPreference_v3>> doInBackground()
			{
				NetworkAsyncTaskResponse<ArrayList<TaxationPreference_v3>> networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ArrayList<TaxationPreference_v3>>();

				Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(context);
				if (lookupMasterTable != null && lookupMasterTable.containsKey(Constants.LOOKUP_MASTER_TABLE_KEY_TAX_PREFERENCE_LIST))
				{
					ArrayList<TaxationPreference_v3> taxationPrefList = (ArrayList<TaxationPreference_v3>) lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_TAX_PREFERENCE_LIST);
					networkAsyncTaskResponse.setResultObject(taxationPrefList);
				}
				else
				{
					FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
					if(factoryResponse.getError()!=null)
					{
						networkAsyncTaskResponse.setError(factoryResponse.getError());
					}
					else
					{
						ArrayList<TaxationPreference_v3> taxationPrefList = factoryResponse.getiDataProvider().getTaxPreferenceList().getResultObject().getData();
						if(taxationPrefList!=null && taxationPrefList.size()>0)
						{
							networkAsyncTaskResponse.setResultObject(taxationPrefList);
							lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_TAX_PREFERENCE_LIST, taxationPrefList);
							Utils.serializeLookupMasterTable(context, lookupMasterTable);
						}
						else
						{
							networkAsyncTaskResponse.setError(factoryResponse.getError());
						}
					}
				}
				return networkAsyncTaskResponse;

			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ArrayList<TaxationPreference_v3>> networkAsyncTaskResponse)
			{
				onTaxPreferenceLoadingListener.onTaxPreferenceLoadingEnd(spinner, progressBar);
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onTaxPreferenceLoadingListener.onTaxPreferenceLoadingError(spinner, progressBar, networkAsyncTaskResponse.getError());
				}
				else
				{
					onTaxPreferenceLoadingListener.onTaxPreferenceLoadingSuccess(spinner, progressBar, networkAsyncTaskResponse.getResultObject());
				}
			}
		}).execute();
	}

////////////////////////////////////////////////

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
				NetworkAsyncTaskResponse<ResponseUploadPODto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseUploadPODto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().uploadPO(rfqNo, file, smeId);
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

	////////////////////////////////////////////////

	@Override
	public void getItemCategories(final BetterSpinner spinner, final ProgressBar progressBar, final OnItemCategoryFetchListener onSKUCategoryListener) {
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ArrayList<String>>>(context, new INetworkAsyncTaskCallbacks<ArrayList<String>>()
		{
			@Override
			public void onPreExecute()
			{
				onSKUCategoryListener.onItemCategoryFetchStart(spinner, progressBar);
			}
			@Override
			public NetworkAsyncTaskResponse<ArrayList<String>> doInBackground()
			{

				NetworkAsyncTaskResponse<ArrayList<String>> networkAsyncTaskResponse=new NetworkAsyncTaskResponse<ArrayList<String>>();
				Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(context);
				if(lookupMasterTable!=null && lookupMasterTable.containsKey(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST))
				{
					ArrayList<String> catList = (ArrayList<String>)lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST);
					networkAsyncTaskResponse.setResultObject(catList);
				}
				else
				{
					FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
					if(factoryResponse.getError()!=null)
					{
						networkAsyncTaskResponse.setError(factoryResponse.getError());
					}
					else
					{
						if(factoryResponse!=null && factoryResponse.getiDataProvider()!=null && factoryResponse.getiDataProvider().getSKUCategories()!=null && factoryResponse.getiDataProvider().getSKUCategories().getResultObject()!=null)
						{
							ArrayList<String> catList = factoryResponse.getiDataProvider().getSKUCategories().getResultObject().getCategories();
							if(catList!=null && catList.size()>0 && lookupMasterTable!=null)
							{
								networkAsyncTaskResponse.setResultObject(catList);
								lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_CATEGORY_LIST, catList);
								Utils.serializeLookupMasterTable(context, lookupMasterTable);
							}
							else
							{
								networkAsyncTaskResponse.setError(factoryResponse.getError());
							}
						}
					}
				}
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ArrayList<String>> networkAsyncTaskResponse)
			{
				onSKUCategoryListener.onItemCategoryFetchEnd(spinner, progressBar);
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onSKUCategoryListener.onItemCategoryFetchError(spinner, progressBar, networkAsyncTaskResponse.getError());
				}
				else
				{
					onSKUCategoryListener.onItemCategoryFetchSuccess(spinner, progressBar, networkAsyncTaskResponse.getResultObject());
				}
			}
		}).execute();
	}

	////////////////////////////////////////////////

	@Override
	public void getItemSubCategories(final BetterSpinner spinner, final ProgressBar progressBar, final String category, final OnItemSubCategoryFetchListener onSKUSubCategoryFetchListener) {
		new NetworkAsyncTask <NetworkAsyncTaskResponse<ArrayList<String>>>(context, new INetworkAsyncTaskCallbacks<ArrayList<String>>()
		{
			@Override
			public void onPreExecute()
			{
				onSKUSubCategoryFetchListener.onItemSubCategoryFetchStart(spinner, progressBar);
			}
			@Override
			public NetworkAsyncTaskResponse<ArrayList<String>> doInBackground()
			{
				NetworkAsyncTaskResponse<ArrayList<String>> networkAsyncTaskResponse=new NetworkAsyncTaskResponse<ArrayList<String>>();
				Hashtable<String, Object> lookupMasterTable = Utils.deserializeLookupMasterTable(context);
				if(lookupMasterTable!=null && lookupMasterTable.containsKey(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_SUB_CATEGORY_LIST + category))
				{
					ArrayList<String> catList = (ArrayList<String>)lookupMasterTable.get(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_SUB_CATEGORY_LIST+category);
					networkAsyncTaskResponse.setResultObject(catList);
				}
				else
				{
					FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
					if(factoryResponse.getError()!=null)
					{
						networkAsyncTaskResponse.setError(factoryResponse.getError());
					}
					else
					{
						NetworkAsyncTaskResponse<ResponseSKUSubCategoryFetchDto> res = factoryResponse.getiDataProvider().getSKUSubCategories(category);
						if(res!=null && res.getResultObject()!=null)
						{
							ArrayList<String> subCatList = res.getResultObject().getSubCategories();
							if(subCatList!=null && subCatList.size()>0)
							{
								networkAsyncTaskResponse.setResultObject(subCatList);
								lookupMasterTable.put(Constants.LOOKUP_MASTER_TABLE_KEY_ITEM_SUB_CATEGORY_LIST + category, subCatList);
								Utils.serializeLookupMasterTable(context, lookupMasterTable);
							}
							else
							{
								networkAsyncTaskResponse.setError(factoryResponse.getError());
							}
						}
						else
						{
							networkAsyncTaskResponse.setError(factoryResponse.getError());
						}
					}
				}
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ArrayList<String>> networkAsyncTaskResponse)
			{
				onSKUSubCategoryFetchListener.onItemSubCategoryFetchEnd(spinner, progressBar);
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onSKUSubCategoryFetchListener.onItemSubCategoryFetchError(spinner, progressBar, networkAsyncTaskResponse.getError());
				}
				else
				{
					onSKUSubCategoryFetchListener.onItemSubCategoryFetchSuccess(spinner, progressBar, category, networkAsyncTaskResponse.getResultObject());
				}
			}
		}).execute();
	}
}
