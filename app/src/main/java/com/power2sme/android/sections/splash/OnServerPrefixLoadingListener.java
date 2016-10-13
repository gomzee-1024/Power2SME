package com.power2sme.android.sections.splash;

import com.power2sme.android.dtos.response.ResponseGetServerPrefixDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnServerPrefixLoadingListener 
{
	void onServerPrefixLoadingStart();
	void onServerPrefixLoadingEnd();
	void onServerPrefixLoadingSuccess(ResponseGetServerPrefixDto responseGetServerPrefixDto);
	void onServerPrefixLoadingError(ACError error);
}
