package com.power2sme.android.sections;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.utilities.logging.UIMessage;

public interface IBaseView 
{
	void showProgress(ProgressTypes progressTypes, int flag);
	void hideProgress(ProgressTypes progressTypes, int flag);
	void showUIMessage(UIMessage uiMessage, int flag);
}
