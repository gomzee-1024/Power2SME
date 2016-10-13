package com.power2sme.android.sections.agentlogin;

/**
 * Created by power2sme on 07/12/15.
 */
public enum CustomerSearchFilter {
    LAST_TEN("Last 10 Transacted Customers"),
    ORG_NAME("Organization Name"),
    EMAIL("Email"),
    PHONE("Phone No."),
    SME_ID("SME ID");

    private String filter;
    private CustomerSearchFilter(String filter)
    {
        this.filter=filter;
    }

    public static CustomerSearchFilter getFilter(String filter)
    {
        if(filter.equals(LAST_TEN.toString()))
        {
            return LAST_TEN;
        }
        else if(filter.equals(ORG_NAME.toString()))
        {
            return ORG_NAME;
        }
        else if(filter.equals(EMAIL.toString()))
        {
            return EMAIL;
        }
        else if(filter.equals(PHONE.toString()))
        {
            return PHONE;
        }
        else if(filter.equals(SME_ID.toString()))
        {
            return SME_ID;
        }
        return null;
    }

    @Override
    public String toString()
    {
        return filter;
    }
}
