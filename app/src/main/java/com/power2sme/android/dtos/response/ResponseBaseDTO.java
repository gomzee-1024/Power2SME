package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract public class ResponseBaseDTO
{
    @JsonProperty("Status")
	private String Status;
    @JsonProperty("Message")
    private String Message;
	@JsonProperty("TotalRecord")
	private String TotalRecord;
    @JsonProperty("ErrorCode")
    private String ErrorCode;
	/**
	 * @return the errorCode
	 */
	public int getErrorCode()
    {
        try
        {
            if(ErrorCode!=null)
                return Integer.parseInt(ErrorCode);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.ErrorCode = errorCode;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.Message = message;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.Status = status;
	}
	/**
	 * @return the totalRecord
	 */
	public int getTotalRecord()
    {
        try
        {
            if(TotalRecord!=null)
                return Integer.parseInt(TotalRecord);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
	}
	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(String totalRecord) {
		this.TotalRecord = totalRecord;
	}
	
	
	
	
}
