package com.power2sme.android.sections.login;

import com.power2sme.android.dtos.response.ResponseSMELoginDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnLoginCompletedListener 
{
	void onLoginStart();
	void onLoginEnd();
	void onLoginSuccess(ResponseSMELoginDto responseSMELoginDto, LoginType loginType);
	void onLoginError(ACError error, LoginType loginType);
}
