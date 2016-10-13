package com.power2sme.android.utilities.gcm;

import android.content.Context;

import com.power2sme.android.dtos.response.ResponseDeviceIdRegisterDto;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;

public class GCMPresentorImpl implements IGCMPresentor, OnDeviceIdRegisterListener
{
	IGCMView iGCMView;
	Context context;
	IGCMInteractor iGCMInteractor;
	public GCMPresentorImpl(Context context, IGCMView iGCMView)
	{
		this.context=context;
		this.iGCMView=iGCMView;
		this.iGCMInteractor=new GCMInteractorImpl(context);
	}
	
	@Override
	public void registerDeviceRegisterationId(String regId, String firstLaunchFlag)
	{
		iGCMInteractor.registerDeviceRegisterationId(regId, this, firstLaunchFlag);
	}

	@Override
	public void onDeviceIdRegisterStart() 
	{
		ACLogger.log("Device Registeration start.");
	}

	@Override
	public void onDeviceIdRegisterEnd() 
	{
		ACLogger.log("Device Registeration end.");
	}

	@Override
	public void onDeviceIdRegisterSuccess(ResponseDeviceIdRegisterDto responseDeviceIdRegisterDto) 
	{
		ACLogger.log("Device Registeration success."+responseDeviceIdRegisterDto);
		iGCMView.onGCMRegisterSuccess();
	}

	@Override
	public void onDeviceIdRegisterError(ACError error) 
	{
		ACLogger.log("Device Registeration error."+error);
	}
}
