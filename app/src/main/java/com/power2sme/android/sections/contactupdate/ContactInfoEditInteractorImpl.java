package com.power2sme.android.sections.contactupdate;

import android.content.Context;

import com.power2sme.android.dataprovider.DataProviderFactory;
import com.power2sme.android.dataprovider.DataProviderTypes;
import com.power2sme.android.dataprovider.FactoryResponse;
import com.power2sme.android.dataprovider.network.INetworkAsyncTaskCallbacks;
import com.power2sme.android.dataprovider.network.NetworkAsyncTask;
import com.power2sme.android.dataprovider.network.NetworkAsyncTaskResponse;
import com.power2sme.android.dtos.response.ResponseContactInfoEditDto;
import com.power2sme.android.dtos.response.ResponseContactInfoListDto;
import com.power2sme.android.dtos.response.ResponseGetDesignationsDto;
import com.power2sme.android.entities.ContactInfo;

public class ContactInfoEditInteractorImpl implements IContactInfoEditInteractor 
{
	Context context;
	public ContactInfoEditInteractorImpl(Context context) 
	{
		this.context=context;
	}
	
	@Override
	public void editContactInfo(final ContactInfo contactInfo, final OnContactInfoEditListener onContactInfoEditListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseContactInfoEditDto > >(context, new INetworkAsyncTaskCallbacks<ResponseContactInfoEditDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onContactInfoEditListener.onContactInfoEditStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseContactInfoEditDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseContactInfoEditDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseContactInfoEditDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().editContactInfo(contactInfo);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseContactInfoEditDto> networkAsyncTaskResponse) 
			{
				onContactInfoEditListener.onContactInfoEditEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onContactInfoEditListener.onContactInfoEditError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseContactInfoEditDto responseContactInfoEditDto=(ResponseContactInfoEditDto) networkAsyncTaskResponse.getResultObject();
					onContactInfoEditListener.onContactInfoEditSuccess(contactInfo, responseContactInfoEditDto);
				}
			}
		}).execute();
	}
	@Override
	public void getContactInfo(final String contactId, final OnContactInfoRetreivalListener onContactInfoRetreivalListener) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseContactInfoListDto > >(context, new INetworkAsyncTaskCallbacks<ResponseContactInfoListDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onContactInfoRetreivalListener.onContactInfoRetreivalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseContactInfoListDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseContactInfoListDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseContactInfoListDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getContactInfo(contactId);
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseContactInfoListDto> networkAsyncTaskResponse) 
			{
				onContactInfoRetreivalListener.onContactInfoRetreivalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onContactInfoRetreivalListener.onContactInfoRetreivalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseContactInfoListDto responseContactInfoListDto=(ResponseContactInfoListDto) networkAsyncTaskResponse.getResultObject();
					if(responseContactInfoListDto.getData()!=null)
						onContactInfoRetreivalListener.onContactInfoRetreivalSuccess(responseContactInfoListDto.getData());
					else
						onContactInfoRetreivalListener.onContactInfoRetreivalError(networkAsyncTaskResponse.getError());
				}
			}
		}).execute();

//		try
//		{
//			onContactInfoRetreivalListener.onContactInfoRetreivalStart();
//			MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
//			SharedPreferences prefs = myAccountApplication.getPrefs();
//			ContactInfo contactInfo=new ContactInfo();
//			
//			contactInfo.setCompanyName(prefs.getString(Constants.PREFERENCE_CONTACTINFO_CompanyName, ""));
//			contactInfo.setFirstName(prefs.getString(Constants.PREFERENCE_CONTACTINFO_FirstName, ""));
//			contactInfo.setMiddleName(prefs.getString(Constants.PREFERENCE_CONTACTINFO_MiddleName, ""));
//			contactInfo.setDepartment(prefs.getString(Constants.PREFERENCE_CONTACTINFO_Department, ""));
//			contactInfo.setSurname(prefs.getString(Constants.PREFERENCE_CONTACTINFO_SurName, ""));
//			contactInfo.setFirstAddress(prefs.getString(Constants.PREFERENCE_CONTACTINFO_FirstAddress, ""));
//			contactInfo.setSecondAddress(prefs.getString(Constants.PREFERENCE_CONTACTINFO_SecondAddress, ""));
//			contactInfo.setCity(prefs.getString(Constants.PREFERENCE_CONTACTINFO_City, ""));
//			contactInfo.setPostCode(prefs.getString(Constants.PREFERENCE_CONTACTINFO_PostCode, ""));
//			contactInfo.setState(prefs.getString(Constants.PREFERENCE_CONTACTINFO_State, ""));
//			contactInfo.setMobilePhoneNo(prefs.getString(Constants.PREFERENCE_CONTACTINFO_MobilePhoneNo, ""));
//			contactInfo.setPhoneNumber(prefs.getString(Constants.PREFERENCE_CONTACTINFO_PhoneNumber, ""));
//			contactInfo.setEmail(prefs.getString(Constants.PREFERENCE_CONTACTINFO_Email, ""));
//			contactInfo.setTurnOver(prefs.getString(Constants.PREFERENCE_CONTACTINFO_TurnOver, ""));
//			contactInfo.setJobTitle(prefs.getString(Constants.PREFERENCE_CONTACTINFO_JobTitle, ""));
//			
//			onContactInfoRetreivalListener.onContactInfoRetreivalSuccess(contactInfo);
//			onContactInfoRetreivalListener.onContactInfoRetreivalEnd();
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			ACError error=new ACError(ACErrorCodes.APP_FUNCTIONING_ERROR, "");
//			onContactInfoRetreivalListener.onContactInfoRetreivalError(error);
//		}
	}

	@Override
	public void getDesignations(final OnDesignationRetrievalListener onDesignationRetrievalListener, final boolean isSerelizable) 
	{
		new NetworkAsyncTask < NetworkAsyncTaskResponse < ResponseGetDesignationsDto > >(context, new INetworkAsyncTaskCallbacks<ResponseGetDesignationsDto>() 
    	{
			@Override
			public void onPreExecute() 
			{
				onDesignationRetrievalListener.onDesignationRetrievalStart();
			}
			@Override
			public NetworkAsyncTaskResponse<ResponseGetDesignationsDto> doInBackground() 
			{
				NetworkAsyncTaskResponse<ResponseGetDesignationsDto> networkAsyncTaskResponse=null;
				FactoryResponse factoryResponse = DataProviderFactory.getDataProvider(context, DataProviderTypes.NETWORK);
				if(factoryResponse.getError()!=null)
				{
					networkAsyncTaskResponse = new NetworkAsyncTaskResponse<ResponseGetDesignationsDto>();
					networkAsyncTaskResponse.setError(factoryResponse.getError());
					return networkAsyncTaskResponse;
				}

				networkAsyncTaskResponse = factoryResponse.getiDataProvider().getDesignations();
				return networkAsyncTaskResponse;
			}
			@Override
			public void onPostExecute(NetworkAsyncTaskResponse<ResponseGetDesignationsDto> networkAsyncTaskResponse) 
			{
				onDesignationRetrievalListener.onDesignationRetrievalEnd();
				if(networkAsyncTaskResponse.getError()!=null)
				{
					onDesignationRetrievalListener.onDesignationRetrievalError(networkAsyncTaskResponse.getError());
				}
				else
				{
					ResponseGetDesignationsDto responseGetDesignationsDto=(ResponseGetDesignationsDto) networkAsyncTaskResponse.getResultObject();
					onDesignationRetrievalListener.onDesignationRetrievalSuccess(responseGetDesignationsDto, isSerelizable);
				}
			}
		}).execute();
	}
}
