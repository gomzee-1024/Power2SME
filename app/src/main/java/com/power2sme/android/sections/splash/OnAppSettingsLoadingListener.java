package com.power2sme.android.sections.splash;

import com.power2sme.android.dtos.response.ResponseGetServerPrefixDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnAppSettingsLoadingListener
{
	void onAppSettingsLoadingStart();
	void onAppSettingsLoadingEnd();
	void onAppSettingsLoadingSuccess(ResponseGetServerPrefixDto responseGetServerPrefixDto);
	void onAppSettingsLoadingError(ACError error);
}
