package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.entities.UnitOfMeasure;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;

import java.util.ArrayList;

public interface OnShowUnitsListener 
{
	void onShowUnitsStart(BetterSpinner spinner, ProgressBar progressBar);
	void onShowUnitsEnd(BetterSpinner spinner, ProgressBar progressBar);
	void onShowUnitsSuccess(String category, BetterSpinner spinner, ProgressBar progressBar, ArrayList<UnitOfMeasure> units);
	void onShowUnitsError(BetterSpinner spinner, ProgressBar progressBar, ACError error);
}
