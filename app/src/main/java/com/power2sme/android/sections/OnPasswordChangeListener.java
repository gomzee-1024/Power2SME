package com.power2sme.android.sections;

import com.power2sme.android.utilities.logging.ACError;

public interface OnPasswordChangeListener
{
	void onPasswordChangeStart();
	void onPasswordChangeEnd();
	void onPasswordChangeSuccess();
	void onPasswordChangeError(ACError error);
}
