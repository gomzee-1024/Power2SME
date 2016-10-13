package com.power2sme.android.sections.splash;

import com.power2sme.android.sections.IBaseView;
import com.power2sme.android.utilities.logging.UIMessageType;

public interface ISplashView  extends IBaseView
{
	void navigateToHome(UIMessageType uiMessageType);
	void appStartUpError();
}
