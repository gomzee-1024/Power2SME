package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.ACError;

import java.util.ArrayList;

/**
 * Created by tausif on 19/6/15.
 */
public interface OnItemCategoryFetchListener
{
    void onItemCategoryFetchStart(BetterSpinner spinner, ProgressBar progressBar);
    void onItemCategoryFetchEnd(BetterSpinner spinner, ProgressBar progressBar);
    void onItemCategoryFetchSuccess(BetterSpinner spinner, ProgressBar progressBar, ArrayList<String> categoryList);
    void onItemCategoryFetchError(BetterSpinner spinner, ProgressBar progressBar, ACError error);
}
