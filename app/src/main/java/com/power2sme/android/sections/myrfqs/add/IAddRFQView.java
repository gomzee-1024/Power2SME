package com.power2sme.android.sections.myrfqs.add;

import android.widget.ProgressBar;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.entities.UnitOfMeasure;
import com.power2sme.android.entities.v3.TaxationPreference_v3;
import com.power2sme.android.entities.v3.Urgency_v3;
import com.power2sme.android.sections.IBaseView;
import com.power2sme.android.utilities.customviews.BetterSpinner;
import com.power2sme.android.utilities.logging.UIMessage;

import java.util.List;

public interface IAddRFQView extends IBaseView
{
	void showProgress(BetterSpinner spinner, ProgressBar progressBar, ProgressTypes progressTypes, int flag);
	void hideProgress(BetterSpinner spinner, ProgressBar progressBar, ProgressTypes progressTypes, int flag);
	void showUIMessage(BetterSpinner spinner, ProgressBar progressBar,UIMessage uiMessage, int flag);
	
	void showUnitsList(String category, BetterSpinner spinner, ProgressBar progressBar, List<UnitOfMeasure> unitsList);
	void showUrgencyList(BetterSpinner spinner, ProgressBar progressBar,  List<Urgency_v3> urgencyList);
	void showTaxationPrefsList(BetterSpinner spinner, ProgressBar progressBar, List<TaxationPreference_v3> taxationPrefList);
//    boolean uploadPO(String rfqNo);
	void navigateToMyRFQs(String rfqNo, String message);

	void showItemCategoryList(BetterSpinner spinner, ProgressBar progressBar, List<String> categories);
	void showItemSubCategoryList(BetterSpinner spinner, ProgressBar progressBar, String category, List<String> subCategories);
}
