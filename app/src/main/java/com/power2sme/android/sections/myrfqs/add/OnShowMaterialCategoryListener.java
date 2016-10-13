package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;

import java.util.ArrayList;

public interface OnShowMaterialCategoryListener 
{
	void onShowMaterialCategoryStart(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable);
	void onShowMaterialCategoryEnd(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable);
	void onShowMaterialCategorySuccess(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable, ArrayList<String> categoryList);
    void onShowMaterialCategoryError(BetterSpinner spinner, ProgressBar progressBar, boolean isSerelizable, ACError error);
}
