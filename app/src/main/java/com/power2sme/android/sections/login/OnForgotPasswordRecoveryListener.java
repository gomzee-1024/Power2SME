package com.power2sme.android.sections.login;

import com.power2sme.android.dtos.response.ResponseForgotPasswordDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnForgotPasswordRecoveryListener 
{
	void onForgotPasswordRecoveryStart();
	void onForgotPasswordRecoveryEnd();
	void onForgotPasswordRecoverySuccess(ResponseForgotPasswordDto responseForgotPasswordDto);
	void onForgotPasswordRecoveryError(ACError error);
}
