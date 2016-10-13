package com.power2sme.android.utilities.media;

import com.power2sme.android.utilities.logging.ACError;

public interface IOnActivityResultCallback 
{
	void onSuccess(Object result, int requestCode);
	void onError(ACError error);
}
