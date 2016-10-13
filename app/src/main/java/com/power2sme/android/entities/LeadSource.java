package com.power2sme.android.entities;

import com.power2sme.android.utilities.Utils;

/**
 * Created by tausif on 21/4/15.
 */
public enum LeadSource
{
    Android_Registration("Android_Registration"),
    Android_RFQ("Android_Rfq"),
    Android_Deal("Android_Deal"),
    Android_Reorder("Android_Reorder");

    String value;
    private LeadSource(String value)
    {
        this.value=value;
    }

    @Override
    public String toString()
    {
        return Utils.checkStringForNull(value);
    }
}
