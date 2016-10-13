package com.power2sme.android.sections.login;

import com.power2sme.android.utilities.logging.ACError;

public interface OnUserNameValidateListener 
{
	void onUserNameValidationStart();
	void onUserNameValidationEnd();
	void onUserNameValidationSuccess();
	void onUserNameValidationError(ACError error);
}
