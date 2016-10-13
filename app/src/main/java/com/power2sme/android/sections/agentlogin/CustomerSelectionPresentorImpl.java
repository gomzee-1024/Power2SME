package com.power2sme.android.sections.agentlogin;

import android.content.Context;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseActOnBehalfOfDto;
import com.power2sme.android.dtos.response.ResponseCustomersDto;
import com.power2sme.android.entities.v3.Customer;
import com.power2sme.android.sections.home.HomeFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;

/**
 * Created by tausif on 15/7/15.
 */
public class CustomerSelectionPresentorImpl implements ICustomerSelectionPresentor, OnCustomersRetrievalListener, OnActOnBehalfOfListener
{
    Context context;
    ICustomerSelectionView iCustomerSelectionView;
    ICustomerSelectionInteractor iCustomerSelectionInteractor;
    public CustomerSelectionPresentorImpl(Context context, ICustomerSelectionView iCustomerSelectionView)
    {
        this.context=context;
        this.iCustomerSelectionView=iCustomerSelectionView;
        iCustomerSelectionInteractor= new CustomerSelectionInteractorImpl(context);
    }

    @Override
    public void getCustomersList(CustomerSearchFilter customerSearchFilter, String searchQuery, long currentPage, long pageSize, boolean isLoadmore) {
        iCustomerSelectionInteractor.getCustomersList(customerSearchFilter, searchQuery, currentPage, pageSize, isLoadmore, this);
    }

    @Override
    public void onCustomersRetrievalStart() {
        ACLogger.log("#################### EVENT : onCustomersRetrievalStart ####################");
        iCustomerSelectionView.showProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_Customers_FLAG);
    }

    @Override
    public void onCustomersRetrievalEnd() {
        iCustomerSelectionView.hideProgress(ProgressTypes.INTERACTION_NOT_ALLOWED, Constants.FLAG_Customers_FLAG);
        ACLogger.log("#################### EVENT : onCustomersRetrievalEnd ####################");
    }

    @Override
    public void onCustomersRetrievalSuccess(ResponseCustomersDto responseCustomersDto, boolean isLoadmore)
    {
        iCustomerSelectionView.showCustomers(responseCustomersDto, isLoadmore);
    }

    @Override
    public void onCustomersRetrievalError(ACError error) {
        iCustomerSelectionView.showCustomers(null, false);
        UIMessage uiMessage = Utils.getUIErrorMessage(
                context,
                error,
                context.getString(R.string.agent_customer_selection_error),
                null
        );
        iCustomerSelectionView.showUIMessage(uiMessage, Constants.FLAG_Customers_FLAG);
    }
//////////////////////////////////////////////////////////////////////////////

    @Override
    public void actOnBehalfOf(Customer customer)
    {
        iCustomerSelectionInteractor.actOnBehalfOf(customer, this);
    }

    @Override
    public void onActOnBehalfOfStart() {
        ACLogger.log("#################### EVENT : onActOnBehalfOfStart ####################");
        iCustomerSelectionView.showProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_Customers_FLAG);
    }

    @Override
    public void onActOnBehalfOfEnd() {
        iCustomerSelectionView.hideProgress(ProgressTypes.INTERACTION_ALLOWED, Constants.FLAG_Customers_FLAG);
        ACLogger.log("#################### EVENT : onActOnBehalfOfEnd ####################");
    }

    @Override
    public void onActOnBehalfOfSuccess(ResponseActOnBehalfOfDto responseActOnBehalfOfDto)
    {
        MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
        if(responseActOnBehalfOfDto!=null && responseActOnBehalfOfDto.getData()!=null)
        {
            HomeFragment.outstanding=null;
            HomeFragment.updatesList=null;
            HomeFragment.newsList=null;
            HomeFragment.dealsList=null;
            Utils.saveActOnBehalfOfResponse(context, responseActOnBehalfOfDto.getData().getOrganisation());
            iCustomerSelectionView.navigateToHome();
        }
    }

    @Override
    public void onActOnBehalfOfError(ACError error) {
        UIMessage uiMessage = Utils.getUIErrorMessage(
                context,
                error,
                context.getString(R.string.agent_actbehalfof_customer_error),
                null
        );

        iCustomerSelectionView.showUIMessage(uiMessage, Constants.FLAG_Customers_FLAG);
    }
}
