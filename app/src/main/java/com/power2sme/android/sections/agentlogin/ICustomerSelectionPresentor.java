package com.power2sme.android.sections.agentlogin;

import com.power2sme.android.entities.v3.Customer;

/**
 * Created by tausif on 15/7/15.
 */
public interface ICustomerSelectionPresentor
{
    void getCustomersList(CustomerSearchFilter customerSearchFilter, String searchQuery, long currentPage, long pageSize, boolean isLoadmore);
    void actOnBehalfOf(Customer customer);
}
