package com.power2sme.android.sections.contactupdate;

import com.power2sme.android.dtos.response.ResponseGetDesignationsDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnDesignationRetrievalListener 
{
	void onDesignationRetrievalStart();
	void onDesignationRetrievalEnd();
	void onDesignationRetrievalSuccess(ResponseGetDesignationsDto responseGetDesignationsDto, boolean isSerelizable);
	void onDesignationRetrievalError(ACError error);
}
