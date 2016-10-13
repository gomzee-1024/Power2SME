package com.power2sme.android.sections.agentlogin;

import com.power2sme.android.dtos.response.ResponseCustomersDto;
import com.power2sme.android.utilities.logging.ACError;

/**
 * Created by tausif on 15/7/15.
 */
public interface OnCustomersRetrievalListener
{
    void onCustomersRetrievalStart();
    void onCustomersRetrievalEnd();
    void onCustomersRetrievalSuccess(ResponseCustomersDto responseCustomersDto, boolean isLoadmore);
    void onCustomersRetrievalError(ACError error);
}
