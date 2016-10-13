package com.power2sme.android.sections.splash;

public interface ISplashInteractor
{
	void getAPIUrlPrefix(String prefixApiUrl, OnServerPrefixLoadingListener onServerProcessCompletedListener);
	void isRegisteredInERP(String smeId, OnIsRegisteredInERPCheckingListener onIsRegisteredInERPCheckingListener);
}
