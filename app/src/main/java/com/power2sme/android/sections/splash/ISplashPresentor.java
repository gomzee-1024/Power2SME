package com.power2sme.android.sections.splash;

public interface ISplashPresentor
{
    /**
     * retrive server prefix, this prefix will be append at start of api name.
     */
	void getAPIUrlPrefix(String prefixApiUrl);

    /**
     * check is sme is registered in ERP
     * @param smeId
     */
	void isRegisteredInERP(String smeId);
}
