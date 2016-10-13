package com.power2sme.android.sections.contactupdate;

import com.power2sme.android.entities.ContactInfo;

public interface IContactInfoEditInteractor 
{
	void editContactInfo(ContactInfo contactInfo, OnContactInfoEditListener onContactInfoEditListener);
	void getContactInfo(String contactId, OnContactInfoRetreivalListener onContactInfoRetreivalListener);
	void getDesignations(OnDesignationRetrievalListener onDesignationRetrievalListener, boolean isSerelizable);
}
