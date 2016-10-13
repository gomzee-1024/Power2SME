package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.entities.v3.TaxationPreference_v3;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;

import java.util.ArrayList;

public interface OnTaxPreferenceLoadingListener 
{
	void onTaxPreferenceLoadingStart(BetterSpinner spinner, ProgressBar progressBar);
	void onTaxPreferenceLoadingEnd(BetterSpinner spinner, ProgressBar progressBar);
	void onTaxPreferenceLoadingSuccess(BetterSpinner spinner, ProgressBar progressBar, ArrayList<TaxationPreference_v3> preferenceList);
	void onTaxPreferenceLoadingError(BetterSpinner spinner, ProgressBar progressBar, ACError error);
}
