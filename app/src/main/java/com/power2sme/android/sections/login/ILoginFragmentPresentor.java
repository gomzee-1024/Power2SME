package com.power2sme.android.sections.login;

public interface ILoginFragmentPresentor
{
	void loginByP2SMEAccount(String userName, String password);
	void loginBySocialNetwork(SocialNetworkTypes socialNetworkType, String email, String firstName, String lastName);
	void forgotPassword(String emailId);
	void skipToDeals();
}
