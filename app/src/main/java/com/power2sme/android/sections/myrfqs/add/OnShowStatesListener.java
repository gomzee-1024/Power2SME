package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.dtos.response.ResponseGetStatesDto;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;

public interface OnShowStatesListener 
{
	void onShowStatesStart(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable);
	void onShowStatesEnd(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable);
	void onShowStatesSuccess(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable, ResponseGetStatesDto responseGetStatesDto);
	void onShowStatesError(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable, ACError error);
}
