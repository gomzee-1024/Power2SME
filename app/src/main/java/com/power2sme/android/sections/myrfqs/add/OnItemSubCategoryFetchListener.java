package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;

import java.util.ArrayList;

/**
 * Created by tausif on 19/6/15.
 */
public interface OnItemSubCategoryFetchListener
{
    void onItemSubCategoryFetchStart(BetterSpinner spinner, ProgressBar progressBar);
    void onItemSubCategoryFetchEnd(BetterSpinner spinner, ProgressBar progressBar);
    void onItemSubCategoryFetchSuccess(BetterSpinner spinner, ProgressBar progressBar, String category, ArrayList<String> subCatList);
    void onItemSubCategoryFetchError(BetterSpinner spinner, ProgressBar progressBar, ACError error);
}
