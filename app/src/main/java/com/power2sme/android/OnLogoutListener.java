package com.power2sme.android;

import com.power2sme.android.dtos.response.ResponseLogoutDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnLogoutListener 
{
	void onLogoutStart();
	void onLogoutEnd();
	void onLogoutSuccess(ResponseLogoutDto responseLogoutDto);
	void onLogoutError(ACError error);
}
