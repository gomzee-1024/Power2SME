package com.power2sme.android.sections.login;

public enum LoginType 
{
	POWER2SME_LOGIN("Login By Power2SME Account"),
	POWER2SME_SIGNUP("Login By Power2SME SignUp"),
	FACEBOOK("Login By Facebook"),
	GOOGLE("Login By Google"),
	RFQ_CREATION("Login By RFQ Creation");
	
	private String loginType="";
	
	private LoginType(String loginType)
	{
		this.loginType=loginType;
	}
	@Override
	public String toString() 
	{
		return loginType;
	}
}
