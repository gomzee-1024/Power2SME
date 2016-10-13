package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.entities.v3.Urgency_v3;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;

import java.util.ArrayList;

public interface OnUrgencyListLoadingListener 
{
	void onUrgencyListLoadingStart(BetterSpinner spinner, ProgressBar progressBar);
	void onUrgencyListLoadingEnd(BetterSpinner spinner, ProgressBar progressBar);
	void onUrgencyListLoadingSuccess(BetterSpinner spinner, ProgressBar progressBar, ArrayList<Urgency_v3> urgencyList);
	void onUrgencyListLoadingError(BetterSpinner spinner, ProgressBar progressBar, ACError error);
}
