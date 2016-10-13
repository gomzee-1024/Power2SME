package com.power2sme.android.sections.signup;

import com.power2sme.android.dtos.request.RequestRegisterOrgDto;

public interface ISignupFragmentInteractor 
{
	void registerP2SMEAccount(RequestRegisterOrgDto registerOrgDto, OnUserRegisteration onUserRegisteration);
}
