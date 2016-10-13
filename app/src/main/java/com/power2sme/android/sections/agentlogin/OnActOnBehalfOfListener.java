package com.power2sme.android.sections.agentlogin;

import com.power2sme.android.dtos.response.ResponseActOnBehalfOfDto;
import com.power2sme.android.utilities.logging.ACError;

/**
 * Created by tausif on 28/7/15.
 */
public interface OnActOnBehalfOfListener
{
    void onActOnBehalfOfStart();
    void onActOnBehalfOfEnd();
    void onActOnBehalfOfSuccess(ResponseActOnBehalfOfDto responseActOnBehalfOfDto);
    void onActOnBehalfOfError(ACError error);
}
