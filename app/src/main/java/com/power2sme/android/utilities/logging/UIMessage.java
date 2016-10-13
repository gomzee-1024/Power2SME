package com.power2sme.android.utilities.logging;

/**
 * @author MhodPC
 *
 */
public class UIMessage 
{
	UIMessageType uiMessageType;
	String message;
	
	public UIMessage(UIMessageType uiMessageType,String message)
	{
		this.uiMessageType=uiMessageType;
		this.message=message;
	}

	public UIMessageType getUiMessageType() {
		return uiMessageType;
	}

	public String getMessage() {
		return message;
	}

	public void setUiMessageType(UIMessageType uiMessageType) {
		this.uiMessageType = uiMessageType;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
