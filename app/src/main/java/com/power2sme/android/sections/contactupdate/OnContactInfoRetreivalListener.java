package com.power2sme.android.sections.contactupdate;

import com.power2sme.android.entities.ContactInfo;
import com.power2sme.android.utilities.logging.ACError;

public interface OnContactInfoRetreivalListener 
{
	void onContactInfoRetreivalStart();
	void onContactInfoRetreivalEnd();
	void onContactInfoRetreivalSuccess(ContactInfo contactInfo);
	void onContactInfoRetreivalError(ACError error);
}
