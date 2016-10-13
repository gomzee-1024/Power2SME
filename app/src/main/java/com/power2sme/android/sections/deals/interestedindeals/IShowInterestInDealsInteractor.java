package com.power2sme.android.sections.deals.interestedindeals;

import com.power2sme.android.entities.v3.NewRFQ_v3;
import com.power2sme.android.sections.myrfqs.add.OnAddRFQListener;

/**
 * Created by power2sme on 23/12/15.
 */
public interface IShowInterestInDealsInteractor
{
    void addNewRFQ(NewRFQ_v3 newRFQDTO, OnAddRFQListener onAddRFQListener);
}
