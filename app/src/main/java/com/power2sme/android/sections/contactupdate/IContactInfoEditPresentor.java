package com.power2sme.android.sections.contactupdate;

import com.power2sme.android.entities.ContactInfo;

public interface IContactInfoEditPresentor 
{
	void editContactInfo(ContactInfo contactInfo);
	void getContactInfo(String contactId);
	void getDesignations(boolean isSerelizable);
}
