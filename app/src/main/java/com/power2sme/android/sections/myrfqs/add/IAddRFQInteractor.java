package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.sections.myrfqs.list.OnPOUploadListener;
import com.power2sme.android.utilities.customviews.BetterSpinner;

import java.io.File;

public interface IAddRFQInteractor 
{
	void addNewRFQ(NewRFQ_v3 newRFQDTO, OnAddRFQListener onAddRFQListener);
	void getUnitsList(String category, BetterSpinner spinner, ProgressBar progressBar, OnShowUnitsListener listener);
	void getUrgencyList(BetterSpinner spinner, ProgressBar progressBar, OnUrgencyListLoadingListener listener);
	void getTaxationPrefsList(BetterSpinner spinner, ProgressBar progressBar, OnTaxPreferenceLoadingListener listener);
    void uploadPO(String rfqNo, File file, String smeId, OnPOUploadListener listener);
	void getItemCategories(BetterSpinner spinner, ProgressBar progressBar, OnItemCategoryFetchListener listener);
	void getItemSubCategories(BetterSpinner spinner, ProgressBar progressBar, String category, OnItemSubCategoryFetchListener listener);
}
