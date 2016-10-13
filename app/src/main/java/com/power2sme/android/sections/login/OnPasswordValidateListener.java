package com.power2sme.android.sections.login;

import com.power2sme.android.utilities.logging.ACError;

public interface OnPasswordValidateListener 
{
	void onPasswordValidationStart();
	void onPasswordValidationEnd();
	void onPasswordValidationSuccess();
	void onPasswordValidationError(ACError error);
}
