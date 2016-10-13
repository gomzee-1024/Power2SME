package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.utilities.customviews.BetterSpinner;

import java.io.File;

public interface IAddRFQPresentor 
{
	void addNewRFQ(NewRFQ_v3 newRFQ);
	void getUnitsList(String category, BetterSpinner spinner, ProgressBar progressBar);
	void getUrgencyList(BetterSpinner spinner, ProgressBar progressBar);
	void getTaxationPrefsList(BetterSpinner spinner, ProgressBar progressBar);
    void uploadPO(String rfqNo, File file, String smeId);
	void getItemCategories(BetterSpinner spinner, ProgressBar progressBar);
	void getItemSubCategories(BetterSpinner spinner, ProgressBar progressBar, String category);
}
