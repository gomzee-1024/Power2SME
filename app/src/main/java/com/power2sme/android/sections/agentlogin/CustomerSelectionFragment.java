package com.power2sme.android.sections.agentlogin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.power2sme.android.ContainerActivity;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dtos.response.ResponseCustomersDto;
import com.power2sme.android.entities.v3.Customer;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.styels.DividerItemDecoration;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by tausif on 15/7/15.
 */
public class CustomerSelectionFragment extends SuperFragment implements ICustomerSelectionView
{
    private static final String TAG="CustomerSelectionFragment";
    private static final int RESULTS_PER_PAGE = 10;
    private int customersTotalRecordsCount;
    private int pageCounter = 0;

    String smeId;
    FloatingActionMenu menu3;
    View rootView;
    SuperRecyclerView ListView_customersList;
    CustomersRecyclerAdapter adapter;
    ICustomerSelectionPresentor iCustomerSelectionPresentor;
    MyAccountApplication myAccountApplication;
    TextView TextView_transactedCustomersLabel;
    TextView TextView_displayedResults;
    CustomerSearchFilter sustomerSearchFilter;
    String searchQuery;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication)baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_customers_selection));
    }

    @Override
    public int getScreenTitleResId() {
        return R.string.ga_screenname_customers_selection;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.customers_fragment, container, false);
        initUIComponents();
        setHasOptionsMenu(true);
        iCustomerSelectionPresentor=new CustomerSelectionPresentorImpl(baseActivity, this);
        pageCounter=0;
        sustomerSearchFilter=CustomerSearchFilter.LAST_TEN;
        searchQuery="";
        iCustomerSelectionPresentor.getCustomersList(sustomerSearchFilter, searchQuery, pageCounter, RESULTS_PER_PAGE, false);
        ListView_customersList.getSwipeToRefresh().setEnabled(false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.default_blank_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void openSearchDialog(final CustomerSearchFilter searchType)
    {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.kam_search_dialog, null);
        builder.setView(dialogView);

        final EditText EditText_searchQuery = (EditText)dialogView.findViewById(R.id.EditText_searchQuery);
        if(searchType==CustomerSearchFilter.EMAIL)
        {
            builder.setTitle("Find SME by Email ID");
            EditText_searchQuery.setHint(R.string.kam_search_hint_email_id);
            EditText_searchQuery.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        else if(searchType==CustomerSearchFilter.ORG_NAME)
        {
            builder.setTitle("Find SME by Company Name");
            EditText_searchQuery.setHint(R.string.kam_search_hint_company_name);
            EditText_searchQuery.setInputType(EditorInfo.TYPE_CLASS_TEXT);
            Utils.setCompanyNameWatcher(EditText_searchQuery);
        }
        else if(searchType==CustomerSearchFilter.PHONE)
        {
            builder.setTitle("Find SME by Mobile Number");
            EditText_searchQuery.setHint(R.string.kam_search_hint_mobile_number);
            EditText_searchQuery.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            Utils.setMobileNumberWatcher(EditText_searchQuery);
        }
        else if(searchType==CustomerSearchFilter.SME_ID)
        {
            builder.setTitle("Find SME by SME ID");
            EditText_searchQuery.setHint(R.string.kam_search_hint_sme_id);
            EditText_searchQuery.setInputType(EditorInfo.TYPE_CLASS_TEXT);
            Utils.setSMEIdWatcher(EditText_searchQuery);
        }

        builder.setPositiveButton("Submit", null);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.hideKeyboard(baseActivity);
                dialog.dismiss();
            }
        });
        final android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isValidCustomerInfo(EditText_searchQuery, searchType)) {
                    String queryText = EditText_searchQuery.getText().toString();
                    pageCounter = 0;
                    sustomerSearchFilter = searchType;
                    searchQuery = queryText;
                    iCustomerSelectionPresentor.getCustomersList(searchType, queryText, pageCounter, RESULTS_PER_PAGE, false);
                    ListView_customersList.getSwipeToRefresh().setEnabled(false);
                    menu3.close(true);
                    dialog.dismiss();
                }
                Utils.hideKeyboard(baseActivity);
            }
        });
    }

    private boolean isValidCustomerInfo(EditText EditText_searchQuery, CustomerSearchFilter customerSearchFilter)
    {
        String searchQuery = EditText_searchQuery.getText().toString();
        switch(customerSearchFilter)
        {
            case EMAIL:
            {
                if(searchQuery==null || searchQuery.trim().length()==0)
                {
                    EditText_searchQuery.setError(getString(R.string.kam_search_validation_email_empty));
                    return false;
                }
                else if(!Utils.isValidEmailId(EditText_searchQuery))
                {
                    EditText_searchQuery.setError(getString(R.string.kam_search_validation_email_invalid));
                    return false;
                }
                break;
            }
            case ORG_NAME:
            {
                if(searchQuery==null || searchQuery.trim().length()==0)
                {
                    EditText_searchQuery.setError(getString(R.string.kam_search_validation_organization_empty));
                    return false;
                }
                else if(!Pattern.compile("([a-zA-Z0-9@\\(\\)\\.\\-_]+)").matcher(searchQuery.toString()).matches())
                {
                    return false;
                }
                break;
            }
            case PHONE:
            {
                if(searchQuery==null || searchQuery.trim().length()==0)
                {
                    EditText_searchQuery.setError(getString(R.string.kam_search_validation_mobile_empty));
                    return false;
                }
                else if(!Utils.isValidMobileNumber(EditText_searchQuery))
                {
                    EditText_searchQuery.setError(getString(R.string.kam_search_validation_mobile_invalid));
                    return false;
                }
                break;
            }
            case SME_ID:
            {
                if(searchQuery==null || searchQuery.trim().length()==0)
                {
                    EditText_searchQuery.setError(getString(R.string.kam_search_validation_smeid_empty));
                    return false;
                }
                else if(!Pattern.compile("([a-zA-Z0-9]+)").matcher(searchQuery.toString()).matches())
                {
                    return false;
                }
                break;
            }
        }

        EditText_searchQuery.setError(null);
        return true;
    }
    private void initUIComponents()
    {
        menu3 = (FloatingActionMenu) rootView.findViewById(R.id.menu3);
        final FloatingActionButton fab_email = (FloatingActionButton) rootView.findViewById(R.id.fab_email);
        final FloatingActionButton fab_companyName = (FloatingActionButton) rootView.findViewById(R.id.fab_companyName);
        final FloatingActionButton fab_mobileNumber = (FloatingActionButton) rootView.findViewById(R.id.fab_mobileNumber);
        final FloatingActionButton fab_smeId = (FloatingActionButton) rootView.findViewById(R.id.fab_smeId);

//        menu3.hideMenuButton(false);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleY", 1.0f, 0.2f);
        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu3.getMenuIconView(), "scaleY", 0.2f, 1.0f);
        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);
        scaleInX.setDuration(150);
        scaleInY.setDuration(150);
        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu3.getMenuIconView().setImageResource(menu3.isOpened() ? R.drawable.fab_xicon : R.drawable.fab_search);
                if (menu3.isOpened()) {
                    fab_email.setVisibility(FloatingActionButton.VISIBLE);
                    fab_companyName.setVisibility(FloatingActionButton.VISIBLE);
                    fab_mobileNumber.setVisibility(FloatingActionButton.VISIBLE);
                    fab_smeId.setVisibility(FloatingActionButton.VISIBLE);
                } else {
                    fab_email.setVisibility(FloatingActionButton.GONE);
                    fab_companyName.setVisibility(FloatingActionButton.GONE);
                    fab_mobileNumber.setVisibility(FloatingActionButton.GONE);
                    fab_smeId.setVisibility(FloatingActionButton.GONE);
                }
            }
        });
        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));
        menu3.setIconToggleAnimatorSet(set);



        fab_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSearchDialog(CustomerSearchFilter.EMAIL);
            }
        });
        fab_companyName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openSearchDialog(CustomerSearchFilter.ORG_NAME);
            }
        });
        fab_mobileNumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openSearchDialog(CustomerSearchFilter.PHONE);
            }
        });
        fab_smeId.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openSearchDialog(CustomerSearchFilter.SME_ID);
            }
        });

        TextView_transactedCustomersLabel=(TextView)rootView.findViewById(R.id.TextView_transactedCustomersLabel);
        TextView_transactedCustomersLabel.setText(getString(R.string.kam_search_label_last_10_transacted_customers));
        TextView_displayedResults=(TextView)rootView.findViewById(R.id.TextView_displayedResults);

        CustomerSearchFilter[] filters = CustomerSearchFilter.values();
        ArrayAdapter<CustomerSearchFilter> customerSearchAdapter = new ArrayAdapter<CustomerSearchFilter>(baseActivity, android.R.layout.simple_dropdown_item_1line, filters);
        ListView_customersList=(SuperRecyclerView)rootView.findViewById(R.id.ListView_customersList);
        ListView_customersList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ListView_customersList.setLayoutManager(new LinearLayoutManager(baseActivity));
        adapter = new CustomersRecyclerAdapter(new ArrayList<Customer>());
        adapter.setOnItemClickListener(new CustomersRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onActOnBehalf(final Customer customer) {
                if(menu3.isOpened())
                {
                    return;
                }
                new AlertDialog.Builder(baseActivity)
                        .setTitle(getString(R.string.kam_search_dlg_title, customer.getCompany_name()))
                        .setMessage(getString(R.string.kam_search_dlg_message, customer.getCompany_name()))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton(getString(R.string.label_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                iCustomerSelectionPresentor.actOnBehalfOf(customer);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        ListView_customersList.setAdapter(adapter);

        ListView_customersList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                pageCounter = 0;
                iCustomerSelectionPresentor.getCustomersList(sustomerSearchFilter, searchQuery, pageCounter, RESULTS_PER_PAGE, false);
            }
        });

        ListView_customersList.setOnMoreListener(new OnMoreListener()
        {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition)
            {
                if (overallItemsCount < customersTotalRecordsCount) {
                    pageCounter = pageCounter + 1;
                    iCustomerSelectionPresentor.getCustomersList(sustomerSearchFilter, searchQuery, pageCounter, RESULTS_PER_PAGE, true);
                }
            }
        });
    }


    @Override
    public void showProgress(ProgressTypes progressTypes, int flag) {
        baseActivity.showProgressDialog(progressTypes);
    }

    @Override
    public void hideProgress(ProgressTypes progressTypes, int flag) {
        baseActivity.hideProgressDialog(progressTypes);
    }

    @Override
    public void showUIMessage(UIMessage uiMessage, int flag)
    {
        if (!isAdded()) {
            return;
        }
        if(!showErrorMessageFragment(uiMessage.getUiMessageType(), CustomerSelectionFragment.class, null))
        {
//            if(flag == Constants.FLAG_Customers_FLAG)
//            {
//                if(uiMessage.getMessage().indexOf("Records not found")!=-1)
//                {
//                    uiMessage.setMessage(getString(R.string.customers_not_found));
//                    TextView_displayedResults.setText("" + adapter.getItemCount()+" results");
//                }
//            }
//            UIMessageUtility.displayUIMessage(baseActivity, uiMessage);
        }
    }

    @Override
    public void showCustomers(ResponseCustomersDto responseCustomersDto, boolean isLoadmore)
    {
        if(!isAdded())
        {
            return;
        }
        ListView_customersList.getSwipeToRefresh().setEnabled(true);
        TextView_transactedCustomersLabel.setText(getString(R.string.kam_search_label_last_10_transacted_customers));
        TextView_displayedResults.setVisibility(TextView.GONE);

        if(sustomerSearchFilter != CustomerSearchFilter.LAST_TEN)
        {
            Intent intent=new Intent(baseActivity, ChildActivity.class);
            intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, Constants.CUSTOMER_SEARCH_RESULT_FRAGMENT_CODE);
            intent.putExtra(Constants.BUNDLE_KEY_KAM_FILTER, sustomerSearchFilter.toString());
            intent.putExtra(Constants.BUNDLE_KEY_KAM_CUSTOMER_QUIRY, searchQuery);

            if(responseCustomersDto!=null && responseCustomersDto.getCustomers()!=null && responseCustomersDto.getCustomers().size()>0)
            {
                customersTotalRecordsCount = responseCustomersDto.getTotalRecord();
                intent.putParcelableArrayListExtra(Constants.BUNDLE_KEY_SEARCHED_CUSTOMERS, responseCustomersDto.getCustomers());
            }
            else
            {
                customersTotalRecordsCount = 0;
                intent.putParcelableArrayListExtra(Constants.BUNDLE_KEY_SEARCHED_CUSTOMERS, new ArrayList<Customer>());
            }
            startActivity(intent);
        }
        else
        {
            if(responseCustomersDto!=null)
            {
                ArrayList<Customer> customerList = responseCustomersDto.getCustomers();
                if(customerList!=null)
                {
                    customersTotalRecordsCount = responseCustomersDto.getTotalRecord();
                    if(!isLoadmore)
                    {
                        adapter.clear();
                    }
                    adapter.addAll(customerList);
                    adapter.notifyDataSetChanged();
                }
            }
            else
            {
                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void navigateToHome()
    {
//        ContainerActivity.isKAMStatusChanged=true;
//        baseActivity.getIntent().removeExtra("SUCCESS_FLAG");
//        if(getActivity() instanceof ContainerActivity)
//        {
//            ((ContainerActivity)getActivity()).doDrawerMenuSettings(null);
//        }
//        baseActivity.openContainerActivityFragment(HomeFragment.class, null, false, true, true);

        Intent intent =new Intent(getActivity(), ContainerActivity.class);
        intent.putExtra(Constants.BUNDLE_KEY_IS_LOGIN_STATUS_CHANGE, true);
        startActivity(intent);
    }
}
