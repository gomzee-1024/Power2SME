package com.power2sme.android.sections.signup;

import com.power2sme.android.dtos.response.ResponseSMESignupDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnUserRegisteration 
{
	void onUserRegisterationStart();
	void onUserRegisterationEnd();
	void onUserRegisterationSuccess(ResponseSMESignupDto responseSMESignupDto);
	void onUserRegisterationError(ACError error);
}
