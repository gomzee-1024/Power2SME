package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.dtos.response.ResponseUploadPODto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnPOUploadListener 
{
	void onPOUploadStart();
	void onPOUploadEnd();
	void onPOUploadSuccess(ResponseUploadPODto responseUploadPODto);
	void onPOUploadError(ACError error);
}
