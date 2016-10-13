package com.power2sme.android.sections.splash;

import com.power2sme.android.dtos.response.ResponseIsRegisterdInERPDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnIsRegisteredInERPCheckingListener 
{
	void onIsRegisteredInERPCheckingStart();
	void onIsRegisteredInERPCheckingEnd();
	void onIsRegisteredInERPCheckingSuccess(ResponseIsRegisterdInERPDto responseIsRegisterdInERPDto);
	void onIsRegisteredInERPCheckingError(ACError error);
}
