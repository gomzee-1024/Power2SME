package com.power2sme.android.utilities.gcm;

import com.power2sme.android.dtos.response.ResponseDeviceIdRegisterDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnDeviceIdRegisterListener 
{
	void onDeviceIdRegisterStart();
	void onDeviceIdRegisterEnd();
	void onDeviceIdRegisterSuccess(ResponseDeviceIdRegisterDto responseDeviceIdRegisterDto);
	void onDeviceIdRegisterError(ACError error);
}
