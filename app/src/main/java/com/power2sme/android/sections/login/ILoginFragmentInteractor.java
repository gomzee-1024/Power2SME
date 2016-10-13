package com.power2sme.android.sections.login;

public interface ILoginFragmentInteractor
{
	boolean validateCredentials(String userName, String password, OnLoginCredentialsValidateListener onLoginCredentialsValidateListener);
	void loginByP2SMEAccount(String userName, String password, OnLoginCompletedListener onLoginCompletedListener);
	void loginBySocialNetwork(SocialNetworkTypes socialNetworkTypes, String email, String firstName, String lastName, OnLoginCompletedListener onLoginCompletedListener);
	void forgotPassword(String userName, OnForgotPasswordRecoveryListener onForgotPasswordCompletedListener);
	boolean validateUserName(String userName, OnUserNameValidateListener onUserNameValidateListener);
	boolean validatePassword(String password, OnPasswordValidateListener OnPasswordValidateListener);
}
