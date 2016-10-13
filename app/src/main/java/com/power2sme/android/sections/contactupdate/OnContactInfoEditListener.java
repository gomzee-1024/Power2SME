package com.power2sme.android.sections.contactupdate;

import com.power2sme.android.dtos.response.ResponseContactInfoEditDto;
import com.power2sme.android.entities.ContactInfo;
import com.power2sme.android.utilities.logging.ACError;

public interface OnContactInfoEditListener 
{
	void onContactInfoEditStart();
	void onContactInfoEditEnd();
	void onContactInfoEditSuccess(ContactInfo contactInfo, ResponseContactInfoEditDto responseContactInfoEditDto);
	void onContactInfoEditError(ACError error);
}
