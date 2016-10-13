package com.power2sme.android.sections.myrfqs.add;

import com.power2sme.android.dtos.response.ResponseSKUsFetchDto;
import com.power2sme.android.utilities.logging.ACError;

/**
 * Created by tausif on 19/6/15.
 */
public interface OnSKUsListener
{
    void onSKUsFetchFetchStart();
    void onSKUsFetchFetchEnd();
    void onSKUsFetchFetchSuccess(ResponseSKUsFetchDto responseSKUsFetchFetchDto);
    void onSKUsFetchFetchError(ACError error);
}
