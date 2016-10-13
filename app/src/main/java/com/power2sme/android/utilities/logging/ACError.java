package com.power2sme.android.utilities.logging;

public class ACError
{
	private ACErrorCodes acErrorCodes;
	private String message;
	public ACError(ACErrorCodes p2SErrorCodes, String message)
	{
		this.acErrorCodes=p2SErrorCodes;
		this.message=message;
	}
	public ACErrorCodes getErrorCodes() {
		return acErrorCodes;
	}
	public void setErrorCodes(ACErrorCodes errorCodes) {
		this.acErrorCodes = errorCodes;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
