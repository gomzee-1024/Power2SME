package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Outstanding 
{
	@JsonProperty("OutstandingAmount")
	private String OutstandingAmount;
	@JsonProperty("CreditLimit")
	private String CreditLimit;


	////////////////////////////////////////////////////
	/**
	 * @return the outstandingAmount
	 */
	public String getOutstandingAmount() 
	{
		try
		{
			String outstandingAmount = Utils.getCommaSeparatedNumber(OutstandingAmount);
            if(outstandingAmount.length()==0)
            {
                return "0.00";
            }
            else
            {
                return outstandingAmount;
            }
		}
		catch(Exception ex)
		{
			return "0.00";
		}
	}

	/**
	 * @param outstandingAmount the outstandingAmount to set
	 */
	public void setOutstandingAmount(String outstandingAmount) {
		OutstandingAmount = outstandingAmount;
	}

	public String getCreditLimit()
	{
		try
		{
			String creditLimit = Utils.getCommaSeparatedNumber(CreditLimit);
			if(creditLimit.length()==0)
			{
				return "0.00";
			}
			else
			{
				return creditLimit;
			}
		}
		catch(Exception ex)
		{
			return "0.00";
		}
	}

	public void setCreditLimit(String creditLimit) {
		CreditLimit = creditLimit;
	}
}
