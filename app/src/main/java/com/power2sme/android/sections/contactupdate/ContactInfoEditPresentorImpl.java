package com.power2sme.android.sections.contactupdate;

import android.content.Context;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseContactInfoEditDto;
import com.power2sme.android.dtos.response.ResponseGetDesignationsDto;
import com.power2sme.android.entities.ContactInfo;
import com.power2sme.android.entities.Designation;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;

import java.util.List;

public class ContactInfoEditPresentorImpl implements IContactInfoEditPresentor, OnContactInfoEditListener,OnContactInfoRetreivalListener, OnDesignationRetrievalListener
{
	Context context;
	IContactInfoEditView iContactInfoDisplayView;
	IContactInfoEditInteractor iContactInfoDisplayInteractor;
	
	public ContactInfoEditPresentorImpl(Context context, IContactInfoEditView iContactInfoDisplayView)
	{
		this.context=context;
		this.iContactInfoDisplayView=iContactInfoDisplayView;
		this.iContactInfoDisplayInteractor= new ContactInfoEditInteractorImpl(context);
	}
///////////////////////////////////////////////////////////////////
	@Override
	public void editContactInfo(ContactInfo contactInfo) 
	{
		iContactInfoDisplayInteractor.editContactInfo(contactInfo, this);
	}

	@Override
	public void onContactInfoEditStart() 
	{
        ACLogger.log("#################### EVENT : onContactInfoEditStart ####################");
		iContactInfoDisplayView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_EDIT_CONTACT_INFO_EDITING);		
	}

	@Override
	public void onContactInfoEditEnd() 
	{
		iContactInfoDisplayView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_EDIT_CONTACT_INFO_EDITING);
        ACLogger.log("#################### EVENT : onContactInfoEditEnd ####################");
	}

	@Override
	public void onContactInfoEditSuccess(ContactInfo contactInfo, ResponseContactInfoEditDto responseContactInfoEditDto) 
	{
		UIMessage uiMessage=new UIMessage(UIMessageType.SUCCESS, context.getString(R.string.contactupdate_edit_success));
		iContactInfoDisplayView.showUIMessage(uiMessage, Constants.FLAG_EDIT_CONTACT_INFO_EDITING);
		if(responseContactInfoEditDto!=null && responseContactInfoEditDto.getData()!=null)
		{
			if(responseContactInfoEditDto.getData().getOrganisation()!=null)
			{
				Utils.saveActOnBehalfOfResponse(context, responseContactInfoEditDto.getData().getOrganisation());
			}

			if(responseContactInfoEditDto.getData().getCustomerLogin()!=null)
			{
				Utils.saveLoginResponseCustomerInfo(context, responseContactInfoEditDto.getData().getCustomerLogin());
			}
		}
	}

	@Override
	public void onContactInfoEditError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.contactupdate_edit_error), 
				null
				);
		
		iContactInfoDisplayView.showUIMessage(uiMessage, Constants.FLAG_EDIT_CONTACT_INFO_EDITING);
	}
	//////////////////////////////////////////////////////////////
	@Override
	public void getContactInfo(String contactId) 
	{
		iContactInfoDisplayInteractor.getContactInfo(contactId, this);
	}

	@Override
	public void onContactInfoRetreivalStart() 
	{
        ACLogger.log("#################### EVENT : onContactInfoRetreivalStart ####################");
		iContactInfoDisplayView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_EDIT_CONTACT_INFO_LOADING);		
	}

	@Override
	public void onContactInfoRetreivalEnd() 
	{
		iContactInfoDisplayView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_EDIT_CONTACT_INFO_LOADING);
        ACLogger.log("#################### EVENT : onContactInfoRetreivalEnd ####################");
	}

	@Override
	public void onContactInfoRetreivalSuccess(ContactInfo contactInfo) 
	{
		iContactInfoDisplayView.showContactInfo(contactInfo);
	}

	@Override
	public void onContactInfoRetreivalError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.contactupdate_display_error), 
				null
				);
		
		iContactInfoDisplayView.showUIMessage(uiMessage, Constants.FLAG_EDIT_CONTACT_INFO_LOADING);	
	}
////////////////////////////////////////////////////////////////////
	@Override
	public void getDesignations(boolean isSerelizable) 
	{
		iContactInfoDisplayInteractor.getDesignations(this, isSerelizable);
	}

	@Override
	public void onDesignationRetrievalStart() 
	{
        ACLogger.log("#################### EVENT : onDesignationRetrievalStart ####################");
		iContactInfoDisplayView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_DESIGNATION_LOADING);	
	}

	@Override
	public void onDesignationRetrievalEnd() 
	{
		iContactInfoDisplayView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_DESIGNATION_LOADING);
        ACLogger.log("#################### EVENT : onDesignationRetrievalEnd ####################");
	}

	@Override
	public void onDesignationRetrievalSuccess(ResponseGetDesignationsDto responseGetDesignationsDto, boolean isSerelizable) 
	{
		List<Designation> designtionList = responseGetDesignationsDto.getData();
//		iContactInfoDisplayView.showDesignationList(designtionList, isSerelizable);
	}

	@Override
	public void onDesignationRetrievalError(ACError error) 
	{
		UIMessage uiMessage = Utils.getUIErrorMessage(
				context, 
				error, 
				context.getString(R.string.contactupdate_designation_loading_error), 
				null
				);
		
		iContactInfoDisplayView.showUIMessage(uiMessage, Constants.FLAG_DESIGNATION_LOADING);	
	}
}
