package com.power2sme.android.sections.contactupdate;

import com.power2sme.android.entities.ContactInfo;
import com.power2sme.android.sections.IBaseView;

public interface IContactInfoEditView extends IBaseView
{
	void showContactInfo(ContactInfo contactInfo);
}
