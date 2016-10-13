package com.power2sme.android.utilities.logging;

public enum ACErrorCodes
{
	UNSUPPORTED_ENCODING_EXCEPTION(0),
	CLIENT_PROTOCOL_EXCEPTION(1),
	SOCKET_TIMEOUT_EXCEPTION(2),
	CONNECTION_TIMEOUT_EXCEPTION(3),
	IO_EXCEPTION(4),
	PAGE_NOT_FOUND_EXCEPTION(5),
	RESPONSE_NOT_FOUND_EXCEPTION(6),
	JSON_PARSE_EXCEPTION(9),
	NETWORK_NOTAVAILABLE_ERROR(8),
	
	APP_FUNCTIONING_ERROR(7),
	
	
	EXCEPTION(10),
	SERVER_ERROR(11),
	INPUT_ERROR(16),
	
	INVALID_USERNAME(12),
	INVALID_PASSWORD(13),
	INVALID_LOGIN_CREDENTIALS(14),
	EMAIL_ALREADY_REGISTERED(15),
	
	API_ERRORCODE_DB(1001),
	API_ERRORCODE_INTERNALSERVER(1002),
	API_ERRORCODE_DATEFORMATERROR(1003),
	API_ERRORCODE_NORECORDSFOUND(1004),
	API_ERRORCODE_INPUTERROR(1005),
	API_ERRORCODE_FORBIDDEN(1006),
	API_ERRORCODE_EMAIL_ALREADY_EXIST_NEED_RELOGIN(1007);
		    
	
	int errorCode;
	private ACErrorCodes(int errorCode)
	{
		this.errorCode=errorCode;
	}
	
	public int getErrorCode()
	{
		return errorCode;
	}
}
