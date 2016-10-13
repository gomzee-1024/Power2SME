package com.power2sme.android.sections.agentlogin;

import com.power2sme.android.dtos.response.ResponseCustomersDto;
import com.power2sme.android.sections.IBaseView;

/**
 * Created by tausif on 15/7/15.
 */
public interface ICustomerSelectionView extends IBaseView
{
    void showCustomers(ResponseCustomersDto responseCustomersDto, boolean isLoadmore);

    void navigateToHome();
}
