package com.power2sme.android.sections.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.power2sme.android.ProgressDialogFragment;
import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.dataprovider.network.NetworkUtils;
import com.power2sme.android.sections.IBaseView;
import com.power2sme.android.sections.SuperFragment;
import com.power2sme.android.sections.accountupdates.AccountUpdatesFragment;
import com.power2sme.android.sections.contactupdate.farmerinfo.KAMDetailsFragment;
import com.power2sme.android.sections.deals.list.DealsFragment;
import com.power2sme.android.sections.deliveryaddress.list.DeliveryAddressesListFragment;
import com.power2sme.android.sections.msgscreens.NoInternetFragment;
import com.power2sme.android.sections.myorders.list.BuyingOrdersFragment;
import com.power2sme.android.sections.myrfqs.list.BuyingRFQsFragment;
import com.power2sme.android.sections.smekhabar.SMEKhabarFragment;
import com.power2sme.android.utilities.Utils;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by power2sme on 11/08/15.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements IBaseView {
    public static final int ERROR_ACTIVITY_REQUEST_CODE = 103;


//    public static boolean isAppInBackground = false;

    public ProgressBar SmoothProgressBar_actionProgressBar;
    public Toolbar toolbar;
    public boolean isAppInBackground=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        setupToolbar();
    }
    public abstract int getActivityLayoutResourceId();
    public abstract ProgressBar getProgressBar();
    public abstract Toolbar getToolbar();

    private void initUI() {
        setContentView(getActivityLayoutResourceId());
        SmoothProgressBar_actionProgressBar = getProgressBar();
        toolbar = getToolbar();
    }

    private void setupToolbar() {
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void showProgressDialog(ProgressTypes progressTypes)
    {
        if (progressTypes == ProgressTypes.INTERACTION_ALLOWED)
        {
            SmoothProgressBar_actionProgressBar.setVisibility(SmoothProgressBar.VISIBLE);
        }
        else
        {
            if(!(isFinishing()))
            {

            }
            DialogFragment newFragment = ProgressDialogFragment.newInstance(R.string.please_wait);
            newFragment.setCancelable(false);
            newFragment.show(getSupportFragmentManager(), "dialog");
        }
    }

    public void hideProgressDialog(ProgressTypes progressTypes) {
        if (progressTypes == ProgressTypes.INTERACTION_ALLOWED) {
            SmoothProgressBar_actionProgressBar.setVisibility(SmoothProgressBar.GONE);
        } else if (progressTypes == ProgressTypes.INTERACTION_NOT_ALLOWED) {
            Fragment dialogFragment = getSupportFragmentManager().findFragmentByTag("dialog");
            if (dialogFragment != null) {
                DialogFragment df = (DialogFragment) dialogFragment;
                df.dismissAllowingStateLoss();
            }
        }
    }

    public <T extends Fragment> void openChildActivityFragment(
            Class<T> fragmentClass,
            Bundle bundle,
            boolean isAddToBackStack,
            boolean isCheckForNetwork,
            boolean isClearBackStack

    )
    {
        if (isCheckForNetwork && !NetworkUtils.isNetworkAvailable(this) && !(fragmentClass.getName().equals(BuyingRFQsFragment.class.getName())) &&
                !(fragmentClass.getName().equals(BuyingOrdersFragment.class.getName())) && !(fragmentClass.getName().equals(SMEKhabarFragment.class.getName()))
                && !(fragmentClass.getName().equals(AccountUpdatesFragment.class.getName())) && !(fragmentClass.getName().equals(DealsFragment.class.getName())
                && (fragmentClass.getName().equals(KAMDetailsFragment.class.getName())))
                && !(fragmentClass.getName().equals(DeliveryAddressesListFragment.class.getName()))) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_NAME, fragmentClass.getName());
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_ADD_TO_BACKSTACK, isAddToBackStack);
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_CHECK_NETWORK, isCheckForNetwork);
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_CLEAR_BACKSTACK, isClearBackStack);
            openChildActivityFragment(NoInternetFragment.class, bundle, false, false, false);
            return;
        }

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = null;

            if (!(fragmentClass.getName().equals(BuyingOrdersFragment.class.getName())
                    || fragmentClass.getName().equals(BuyingRFQsFragment.class.getName()))) {
                fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
            }

            if (fragment == null) {
                fragment = Utils.getFragmentObjectByClass(this, fragmentClass);
            }
            if (bundle != null) {
                ((SuperFragment) fragment).setFragmentDataBundle(bundle);
            }
            String str = fragment.isVisible() + "=" + fragment.isAdded() + "=" + fragment.isInLayout() + "=" + fragment.isResumed();
            if (isClearBackStack) {
                clearStack();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment, fragmentClass.getName());
            if (isAddToBackStack)
                fragmentTransaction.addToBackStack(fragmentClass.getName());
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public <T extends Fragment> void openContainerActivityFragment(
            Class<T> fragmentClass,
            Bundle bundle,
            boolean isAddToBackStack,
            boolean isCheckForNetwork,
            boolean isClearBackStack

    ) {
        if (isCheckForNetwork && !NetworkUtils.isNetworkAvailable(this)) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_NAME, fragmentClass.getName());
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_ADD_TO_BACKSTACK, isAddToBackStack);
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_CHECK_NETWORK, isCheckForNetwork);
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_CLEAR_BACKSTACK, isClearBackStack);
            openContainerActivityFragment(NoInternetFragment.class, bundle, false, false, false);
            return;
        }

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = null;

            if (!(fragmentClass.getName().equals(BuyingOrdersFragment.class.getName())
                    || fragmentClass.getName().equals(BuyingRFQsFragment.class.getName()))) {
                fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
            }

            if (fragment == null) {
                fragment = Utils.getFragmentObjectByClass(this, fragmentClass);
            }
            if (bundle != null) {
                ((SuperFragment) fragment).setFragmentDataBundle(bundle);
            }
                if (isClearBackStack)
                {
                    Utils.clearStack(this);
                }

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.home_content_frame, fragment, fragmentClass.getName());
                if (isAddToBackStack)
                    fragmentTransaction.addToBackStack(fragmentClass.getName());
                fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearStack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while (count > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            count--;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
