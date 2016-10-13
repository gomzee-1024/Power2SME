package com.power2sme.android.sections.login;

public enum SocialNetworkTypes 
{
	FACEBOOK(1), GOOGLE(2), LINKEDIN(3), KAM(4);
	
	private int socialNetworkType;
	private SocialNetworkTypes(int socialNetworkType)
	{
		this.socialNetworkType=socialNetworkType;
	}
	@Override
	public String toString() 
	{
		return ""+socialNetworkType;
	}
}
