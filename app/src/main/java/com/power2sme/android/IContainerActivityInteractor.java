package com.power2sme.android;

import com.power2sme.android.sections.OnPasswordChangeListener;

public interface IContainerActivityInteractor
{
	void logoutApp(String emailId, OnLogoutListener onLogoutListener);
	void changePassword(String oldPassword, String newPassword, OnPasswordChangeListener onPasswordChangeListener);
}
