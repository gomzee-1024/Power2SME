package com.power2sme.android.sections.myorders.list;

import com.power2sme.android.dtos.response.ResponsePostReOrderDto;
import com.power2sme.android.utilities.logging.ACError;

public interface OnPostReOrderListener 
{
	void onPostReOrderStart();
	void onPostReOrderEnd();
	void onPostReOrderSuccess(ResponsePostReOrderDto responsePostReOrderDto);
	void onPostReOrderError(ACError error);
}
