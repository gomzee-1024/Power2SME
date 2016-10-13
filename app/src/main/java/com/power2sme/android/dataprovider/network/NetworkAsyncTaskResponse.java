package com.power2sme.android.dataprovider.network;

import com.power2sme.android.utilities.logging.ACError;

public class NetworkAsyncTaskResponse<T> 
{
	T resultObject;
	ACError error;
	
	public T getResultObject() {
		return resultObject;
	}
	public void setResultObject(T resultObject) {
		this.resultObject = resultObject;
	}
	public ACError getError() {
		return error;
	}
	public void setError(ACError error) {
		this.error = error;
	}
}
