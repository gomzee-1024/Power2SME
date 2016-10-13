package com.power2sme.android.sections.login;

import com.power2sme.android.utilities.logging.ACError;

public interface OnLoginCredentialsValidateListener 
{
	void onLoginCredentialsValidationStart();
	void onLoginCredentialsValidationEnd();
	void onValidLoginCredentials();
	void onInvalidLoginCredentials(ACError error);
}
