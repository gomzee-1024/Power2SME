package com.power2sme.android.utilities.gcm;

public interface IGCMInteractor 
{
	void registerDeviceRegisterationId(String regId, OnDeviceIdRegisterListener onDeviceIdRegisterListener,String firstLaunchFlag);
}
