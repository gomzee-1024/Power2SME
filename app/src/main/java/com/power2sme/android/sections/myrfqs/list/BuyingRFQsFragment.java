package com.power2sme.android.sections.myrfqs.list;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.callbacks.DialogsSingleChoiceCallback;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.dtos.response.ResponseRFQsDto;
import com.power2sme.android.entities.v3.Wishlist_v3;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.myrfqs.add.AddRFQFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.ACError;
import com.power2sme.android.utilities.logging.ACLogger;
import com.power2sme.android.utilities.logging.UIMessage;
import com.power2sme.android.utilities.logging.UIMessageType;
import com.power2sme.android.utilities.logging.UIMessageUtility;
import com.power2sme.android.utilities.media.IOnActivityResultCallback;
import com.power2sme.android.utilities.media.MediaHelper;
import com.power2sme.android.utilities.styels.DividerItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuyingRFQsFragment extends SuperFragment implements IBuyingRFQsView, SwipeRefreshLayout.OnRefreshListener, OnMoreListener, RFQRecyclerAdapter.OnItemClickListener {
    private static final int RESULTS_PER_PAGE = 10;
    private static final int DATA_NOT_FOUND = 1004;
    private int rfqTotalRecordsCount;

    private static final String TAG = "BuyingRFQsFragment";
    View rootView;
    SuperRecyclerView ListView_rfqList;
    RFQRecyclerAdapter adapter;
    IBuyingRFQsPresentor iBuyingRFQsPresentor;
    RFQTypes rfqTypes;
    private String selectedRfqNo;
    ImageButton ImageButton_addRFQ;
    View mNoInternetConnection;

    private int pageCounter = 0;
    MyAccountApplication myAccountApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAccountApplication) baseActivity.getApplication()).getGAUtility().trackScreenView(baseActivity.getString(R.string.ga_screenname_BuyingRFQsFragment));
    }

    @Override
    public int getScreenTitleResId() {
        return R.string.screen_title_rfq;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_ID) != null) {
            setScreenTitle(R.string.screen_title_rfq_details);
        } else {
            setScreenTitle(R.string.screen_title_rfq);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        myAccountApplication = ((MyAccountApplication) baseActivity.getApplicationContext());

//        if (myAccountApplication.getLoginStatus() == Constants.LoginStatus.NOT_LOGGED_IN) {
//            rootView = LayoutInflater.from(getActivity()).inflate(R.layout.message_fragment, container, false);
//            ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.please_contact_our_sales_if_you_can_not_see_your_rfq_here));
//
//            ((Button) rootView.findViewById(R.id.Button_getAQuote)).setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(baseActivity, ChildActivity.class);
//                    intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
//                    startActivity(intent);
//                }
//            });
//            return rootView;
//        }
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.buying_rfq_fragment, container, false);
        iBuyingRFQsPresentor = new BuyingRFQsPresentorImpl(baseActivity, this);

        SharedPreferences prefs = myAccountApplication.getPrefs();
        initUIComponents();
        setupAdaptersAndListeners();
        setHasOptionsMenu(true);
        final LinearLayout LinearLayout_viewAllMyRFQsParent = (LinearLayout) rootView.findViewById(R.id.LinearLayout_viewAllMyRFQsParent);
        if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_ID) != null) {
            setScreenTitle(R.string.screen_title_rfq_details);
            String rfqId = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_ID);
            iBuyingRFQsPresentor.getRFQDetails(rfqId);
            //ImageButton_addRFQ.setVisibility(View.GONE);
            LinearLayout_viewAllMyRFQsParent.setVisibility(LinearLayout.VISIBLE);
            TextView TextView_viewAllMyRFQs = (TextView) rootView.findViewById(R.id.TextView_viewAllMyRFQs);
            TextView_viewAllMyRFQs.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentDataBundle().remove(Constants.BUNDLE_KEY_RFQ_ID);
                    LinearLayout_viewAllMyRFQsParent.setVisibility(LinearLayout.GONE);
                    applyActionBarNavigationListSettings();
                    ImageButton_addRFQ.setVisibility(View.VISIBLE);
                    setScreenTitle(R.string.screen_title_rfq);
                }
            });
        } else {
            LinearLayout_viewAllMyRFQsParent.setVisibility(LinearLayout.GONE);
            applyActionBarNavigationListSettings();
            //ImageButton_addRFQ.setVisibility(View.VISIBLE);
        }


        ((TextView) rootView.findViewById(R.id.TextView_message)).setText(baseActivity.getString(R.string.order_undelivered_list_empty_message));
        ((Button) rootView.findViewById(R.id.Button_getAQuote)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity, ChildActivity.class);
                intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_NO)!=null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_NO).length()>0)
        {
            String rfqNo = getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_NO);
            uploadPO( rfqNo );
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.default_blank_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void applyActionBarNavigationListSettings() {
        onRefresh();
        rfqTypes = RFQTypes.ALL_RFQ_ONLY;//default filter
        baseActivity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        final RFQTypes[] rfqTypesArr = RFQTypes.values();
        SpinnerAdapter orderFiltersAdapter = new ArrayAdapter<RFQTypes>(baseActivity, R.layout.filter_layout, rfqTypesArr);
        baseActivity.getSupportActionBar().setListNavigationCallbacks(orderFiltersAdapter, new OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long arg1) {
                rfqTypes = rfqTypesArr[itemPosition];
                if (!(getFragmentDataBundle() != null && getFragmentDataBundle().getParcelableArrayList("RFQ_ITEM_LIST") != null)) {
                    onRefresh();
                }
                return false;
            }
        });
    }

    private void setupAdaptersAndListeners() {
        ImageButton_addRFQ.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.openChildActivityFragment(AddRFQFragment.class, null, true, true, false);
            }
        });
    }

    private void initUIComponents() {
        mNoInternetConnection = rootView.findViewById(R.id.no_connection);
        mNoInternetConnection.setVisibility(View.GONE);
        ListView_rfqList = (SuperRecyclerView) rootView.findViewById(R.id.ListView_rfqList);

        ListView_rfqList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        ImageButton_addRFQ = (ImageButton) rootView.findViewById(R.id.ImageButton_addRFQ);
        ListView_rfqList.setLayoutManager(new LinearLayoutManager(baseActivity));
        adapter = new RFQRecyclerAdapter(new ArrayList<Wishlist_v3>());
        adapter.setOnItemClickListener(this);
        ListView_rfqList.setAdapter(adapter);
        ListView_rfqList.setRefreshListener(this);
        ListView_rfqList.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        ListView_rfqList.setupMoreListener(this, 1);
        ListView_rfqList.getSwipeToRefresh().setEnabled(false);
        Button retry = (Button) rootView.findViewById(R.id.Button_retryForInternet);
        retry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext())) {
                    changeVisibility(false);
                    onRefresh();
                } else {
                    UIMessageUtility.displayUIMessage(baseActivity, new UIMessage(UIMessageType.ERROR, "Please check your Network connection."));
                }
            }
        });
    }

    @Override
    public void showProgress(ProgressTypes progressTypes, int flag) {
        ListView_rfqList.getSwipeToRefresh().setEnabled(false);
        baseActivity.showProgressDialog(progressTypes);
    }

    @Override
    public void hideProgress(ProgressTypes progressTypes, int flag) {
        ListView_rfqList.getSwipeToRefresh().setEnabled(true);
        baseActivity.hideProgressDialog(progressTypes);
    }

    @Override
    public void showUIMessage(UIMessage uiMessage, int flag) {
        if (!isAdded()) {
            return;
        }
        if ((uiMessage.getUiMessageType() == UIMessageType.NETWORK_NOT_AVAILABLE)) {
            changeVisibility(true);
            return;
        }
        ListView_rfqList.getSwipeToRefresh().setEnabled(true);
        adapter.notifyDataSetChanged();
    }

    private void changeVisibility(Boolean isEmpty) {
        if (isEmpty) {
            ListView_rfqList.setVisibility(View.GONE);
            ImageButton_addRFQ.setVisibility(View.GONE);
            mNoInternetConnection.setVisibility(View.VISIBLE);
        } else {
            ListView_rfqList.setVisibility(View.VISIBLE);
            ImageButton_addRFQ.setVisibility(View.VISIBLE);
            mNoInternetConnection.setVisibility(View.GONE);
        }
    }

    private void showEmptyViewMessage(boolean isVisible)
    {
        LinearLayout LinearLayout_emptyViewParent=(LinearLayout)rootView.findViewById(R.id.LinearLayout_emptyViewParent);
        if(isVisible)
        {
            LinearLayout_emptyViewParent.setVisibility(LinearLayout.VISIBLE);
            TextView TextView_message=(TextView)rootView.findViewById(R.id.TextView_message);
            TextView_message.setText(baseActivity.getString(R.string.please_contact_our_sales_if_you_can_not_see_your_rfq_here));
            Button Button_getAQuote=(Button)rootView.findViewById(R.id.Button_getAQuote);
            ImageButton_addRFQ.setVisibility(View.GONE);
            Button_getAQuote.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(baseActivity, ChildActivity.class);
                    intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, R.id.drawer_menu_item_add_new_rfq);
                    startActivity(intent);
                }
            });
        }
        else
        {
            LinearLayout_emptyViewParent.setVisibility(LinearLayout.GONE);
            ImageButton_addRFQ.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRFQs(RFQTypes rfqTypes, ResponseRFQsDto responseRFQsDto, boolean isLoadmore)
    {
        if (responseRFQsDto!=null && responseRFQsDto.getErrorCode() == DATA_NOT_FOUND && responseRFQsDto.getTotalRecord()==0)
        {
            showEmptyViewMessage(true);
        }
        else
        {
            showEmptyViewMessage(false);
            baseActivity.getSupportActionBar().setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_LIST);
        }

        if (responseRFQsDto!=null && responseRFQsDto.getData() != null)
        {
            List<Wishlist_v3> rfqList = responseRFQsDto.getData();
            rfqTotalRecordsCount = responseRFQsDto.getTotalRecord();
            if (!isLoadmore) {
                adapter.clear();
            }
            adapter.addAll(rfqList);
            adapter.notifyDataSetChanged();
            if (getFragmentDataBundle() != null && getFragmentDataBundle().getString(Constants.BUNDLE_KEY_RFQ_ID) != null) {
                ListView_rfqList.getSwipeToRefresh().setEnabled(false);
            } else {
                ListView_rfqList.getSwipeToRefresh().setEnabled(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

//    private String rfqNoForPOUpload = null;
    private void uploadPO(String rfqNo)
    {
//        this.rfqNoForPOUpload=rfqNo;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)
                {
                    selectedRfqNo = rfqNo;
                    String[] options = new String[]{Constants.DLG_OPTIONS_FILE_FROM_CAMERA, Constants.DLG_OPTIONS_FILE_FROM_PICKER};
                    Utils.showSingleChoiceDialogWithCancelBtn("Upload PO:", baseActivity, options, new DialogsSingleChoiceCallback() {
                        @Override
                        public void onClose(int selectedPosition, String selectedTitle) {
                            if (selectedTitle.equals(Constants.DLG_OPTIONS_FILE_FROM_CAMERA)) {
                                MediaHelper.takePicByCamera(BuyingRFQsFragment.this);
                            } else if (selectedTitle.equals(Constants.DLG_OPTIONS_FILE_FROM_PICKER)) {
                                MediaHelper.takeFileByPicker(BuyingRFQsFragment.this, baseActivity.getString(R.string.file_picker_regex_pattern));
                            }
                            }
                        });
                }
                else
                {
                    checkRecordAudioPermission(true);
                }
            }
            else
            {
                checkStoragePermission(true);
            }
        }
        else
        {
            checkCameraPermission(true);
        }

//        if(PermissionManager.getInstance().isAllowToUploadPO(getActivity(), getString(R.string.permission_msg_storage), null))
//        {
//            selectedRfqNo = rfqNo;
//            String[] options = new String[]{Constants.DLG_OPTIONS_FILE_FROM_CAMERA, Constants.DLG_OPTIONS_FILE_FROM_PICKER};
//            Utils.showSingleChoiceDialogWithCancelBtn("Upload PO:", baseActivity, options, new DialogsSingleChoiceCallback() {
//                @Override
//                public void onClose(int selectedPosition, String selectedTitle) {
//                    if (selectedTitle.equals(Constants.DLG_OPTIONS_FILE_FROM_CAMERA)) {
//                        MediaHelper.takePicByCamera(BuyingRFQsFragment.this);
//                    } else if (selectedTitle.equals(Constants.DLG_OPTIONS_FILE_FROM_PICKER)) {
//                        MediaHelper.takeFileByPicker(BuyingRFQsFragment.this, baseActivity.getString(R.string.file_picker_regex_pattern));
//                    }
//                }
//            });
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MediaHelper.REQUEST_CODE_IMAGE_CAPTURE: {
                MediaHelper.onActivityResult(baseActivity, requestCode, resultCode, data, new IOnActivityResultCallback() {
                    @Override
                    public void onSuccess(Object result, int requestCode) {
//                        Bitmap capturedBitmap = (Bitmap) result;
//                        File file = Utils.getFile(baseActivity, capturedBitmap, baseActivity.getString(R.string.image_capture_tmp_file_name));
                        File rcvFile = (File) result;
                        if (rcvFile != null && rcvFile.exists()) {
                            iBuyingRFQsPresentor.uploadPO(selectedRfqNo, rcvFile, "");
                        }
                    }

                    @Override
                    public void onError(ACError error) {
                        ACLogger.log(baseActivity.getString(
                                R.string.error_occured_in_capturing_image_from_camera_));
                    }
                });
                break;
            }
            case MediaHelper.REQUEST_CODE_CHOOSE_FILE: {
                MediaHelper.onActivityResult(baseActivity, requestCode, resultCode, data, new IOnActivityResultCallback() {
                    @Override
                    public void onSuccess(Object result, int requestCode) {
                        File choosenFile = (File) result;
                        iBuyingRFQsPresentor.uploadPO(selectedRfqNo, choosenFile, "");
                    }

                    @Override
                    public void onError(ACError error) {
                        ACLogger.log(baseActivity.getString(R.string.error_occured_in_picking_file_from_file_chooser_));
                    }
                });
                break;
            }
        }
        ;
    }

    @Override
    public void refreshList()
    {
        if(this.selectedRfq!=null)
        {
            this.selectedRfq.setRfqStatus("PO received. Wait for availability confirmation.");
            this.selectedRfq.setRfqCode("6");
        }
        else
        {
            if(adapter.getItemCount()>0)
            {
                this.selectedRfq = adapter.getItem(0);
                this.selectedRfq.setRfqStatus("PO received. Wait for availability confirmation.");
                this.selectedRfq.setRfqCode("6");
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAcceptQuoteClick(Wishlist_v3 rfq) {
        iBuyingRFQsPresentor.acceptQuote(rfq, rfq.getRfqno(), "");
    }
    Wishlist_v3 selectedRfq;
    @Override
    public void onUploadPOClick(Wishlist_v3 rfq)
    {
        this.selectedRfq=rfq;
        uploadPO(rfq.getRfqno());
    }

    @Override
    public void onStartCallWithKAM()
    {
        checkCallingPermission(true);

//        if(PermissionManager.getInstance().isAllowToCallKAM(getActivity(), getString(R.string.permission_msg_kamcall), null))
//        {
//            String mobileNumber = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_FARMER_MOBILE, null);
//            Utils.startCall(getActivity(), mobileNumber);
//        }
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        if (overallItemsCount < rfqTotalRecordsCount) {
            pageCounter = pageCounter + 1;
            iBuyingRFQsPresentor.getRFQs(pageCounter, RESULTS_PER_PAGE, "", rfqTypes, true);
        }
    }

    @Override
    public void onRefresh() {
        pageCounter = 0;
        iBuyingRFQsPresentor.getRFQs(pageCounter, RESULTS_PER_PAGE, "", rfqTypes, false);
    }


    @Override
    public void onStop() {
        if (!(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)) {
            saveBundle();
        }
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!(myAccountApplication.getLoginStatus() == Constants.LoginStatus.SMEID_WITHOUT_ERPREGISTERATION)) {
            getSavedBundle();
        }
    }

    private void saveBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (getFragmentDataBundle() == null) {
            bundle = new Bundle();
        }
        if (adapter != null && adapter.data != null)
            bundle.putParcelableArrayList("RFQ_ITEM_LIST", adapter.data);
        setFragmentDataBundle(bundle);
    }

    private void getSavedBundle() {
        Bundle bundle = getFragmentDataBundle();
        if (bundle != null) {
            ArrayList<Wishlist_v3> rfqItems = bundle.getParcelableArrayList("RFQ_ITEM_LIST");
            if (rfqItems != null && rfqItems.size() > 0) {
                ResponseRFQsDto responseRFQsDto = new ResponseRFQsDto();
                responseRFQsDto.setData(rfqItems);
                if (adapter.getItemCount() == 0)
                    showRFQs(rfqTypes, responseRFQsDto, true);
            }
        }
    }

    //////////////////////////////////////////////////////////////
    ///////////////////// DYNAMIC PERMISSION /////////////////////
    //////////////////////////////////////////////////////////////
    Snackbar permissionSnackbar;
    private void checkCameraPermission(boolean isCheckForPermission)
    {
        if(!isCheckForPermission)
        {
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
        {
            checkCameraPermission(false);
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA))
            {
                permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.rfqlist_poupload_camera_permission_retry_msg));
                permissionSnackbar.show();
            }
            requestPermissions(new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_REQUEST_READ_CAMERA);
        }
    }

    private void checkStoragePermission(boolean isCheckForPermission)
    {
        if(!isCheckForPermission)
        {
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            checkStoragePermission(false);
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.rfqlist_poupload_storage_permission_retry_msg));
                permissionSnackbar.show();
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_READ_STORAGE);
        }
    }

    private void checkRecordAudioPermission(boolean isCheckForPermission)
    {
        if(!isCheckForPermission)
        {
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)
        {
            checkRecordAudioPermission(false);
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECORD_AUDIO))
            {
                permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.rfqlist_poupload_recordaudio_permission_retry_msg));
                permissionSnackbar.show();
            }
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, Constants.PERMISSION_REQUEST_RECOD_AUDIO_STORAGE);
        }
    }

    private void checkCallingPermission(boolean isCheckForPermission)
    {
        if(!isCheckForPermission)
        {
            String mobileNumber = myAccountApplication.getPrefs().getString(Constants.PREFERENCE_FARMER_MOBILE, null);
            Utils.startCall(getActivity(), mobileNumber);
            return;
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
        {
            checkCallingPermission(false);
        }
        else
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE))
            {
                permissionSnackbar= Utils.getPermissionReTrySnackBar(getActivity(), getActivity().getString(R.string.rfqlist_kamcall_call_permission_retry_msg));
                permissionSnackbar.show();
            }
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, Constants.PERMISSION_REQUEST_PHONE_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(permissionSnackbar!=null)
        {
            permissionSnackbar.dismiss();
            permissionSnackbar=null;
        }

        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case Constants.PERMISSION_REQUEST_READ_CAMERA:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkCameraPermission(false);
                    checkStoragePermission(true);
                }
                else
                {
                    // user denied permission
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA))
                    {
                        //user checked dont ask checkbox and denied
                        permissionSnackbar= Utils.getPermissionDisabledSnackBar(getActivity(), getActivity().getString(R.string.rfqlist_poupload_camera_permission_disabled_msg));
                        permissionSnackbar.show();
                    }
                }
                return;
            }
            case Constants.PERMISSION_REQUEST_READ_STORAGE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkStoragePermission(false);
                    checkRecordAudioPermission(true);
                }
                else
                {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                        //user checked dont ask checkbox and denied
                        permissionSnackbar= Utils.getPermissionDisabledSnackBar(getActivity(), getActivity().getString(R.string.rfqlist_poupload_storage_permission_disabled_msg));
                        permissionSnackbar.show();
                    }
                }
                return;
            }
            case Constants.PERMISSION_REQUEST_RECOD_AUDIO_STORAGE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkRecordAudioPermission(false);
                }
                else
                {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECORD_AUDIO))
                    {
                        //user checked dont ask checkbox and denied
                        permissionSnackbar= Utils.getPermissionDisabledSnackBar(getActivity(), getActivity().getString(R.string.rfqlist_poupload_recordaudio_permission_disabled_msg));
                        permissionSnackbar.show();
                    }
                }
                return;
            }
            case Constants.PERMISSION_REQUEST_PHONE_CALL:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    checkCallingPermission(false);
                }
                else
                {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE))
                    {
                        //user checked dont ask checkbox and denied
                        permissionSnackbar= Utils.getPermissionDisabledSnackBar(getActivity(), getActivity().getString(R.string.rfqlist_kamcall_call_permission_disable_msg));
                        permissionSnackbar.show();
                    }
                }
            }
        }
    }
}
