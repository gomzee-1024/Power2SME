package com.power2sme.android.sections;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.power2sme.android.ProgressTypes;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.activities.ChildActivity;
import com.power2sme.android.sections.smekhabar.SMEKhabarFragment;
import com.power2sme.android.utilities.Utils;
import com.power2sme.android.utilities.logging.UIMessageType;

public abstract class SuperFragment extends Fragment {
    Bundle fragmentDataBundle;
    public BaseAppCompatActivity baseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseAppCompatActivity) getActivity();
    }

    @Override
    public void onResume()
    {
        if(getScreenTitleResId()!=-1)
            setScreenTitle(getScreenTitleResId());
        super.onResume();
//		if(!baseActivity.getSupportActionBar().isShowing()) {
//			baseActivity.getSupportActionBar().show();
//		}
//		else {
//			baseActivity.getSupportActionBar().hide();
//			baseActivity.getSupportActionBar().show();
//		}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    ///////////////////
//	protected void setToolbar(View view)
//	{
////		if(isToolbarActive())
////		{
////			getDrawerActivity().showActionBar();
////		}
////		else
////		{
////			getDrawerActivity().hideActionBar();
////		}
//
////		if(!hasCustomToolbar()) return;
//		Toolbar toolbar = (Toolbar) view.findViewById(getToolbarId());
//		getDrawerActivity().toolbar.setTitle(getTitle());
//		getDrawerActivity().toolbar.setNavigationIcon(R.drawable.ic_menu);
//		getDrawerActivity().toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
////                getDrawerActivity().openDrawer();
//			}
//		});
//	}
//
//	protected int getToolbarId()
//	{
//		return R.id.toolbar;
//	}
//
//	public abstract boolean hasCustomToolbar();
    ///////////////////
    public void setScreenTitle(int screenTitleResId) {
        baseActivity.getSupportActionBar().setTitle(screenTitleResId);
    }

    public abstract int getScreenTitleResId();

    public Bundle getFragmentDataBundle() {
        if (fragmentDataBundle == null) {
            fragmentDataBundle = new Bundle();
        }
        return fragmentDataBundle;
    }

    public void setFragmentDataBundle(Bundle fragmentDataBundle) {
        this.fragmentDataBundle = fragmentDataBundle;
    }

    @Override
    public void onStop() {
        baseActivity.hideProgress(ProgressTypes.INTERACTION_ALLOWED, 0);
        super.onStop();
    }

//    public void showEmailAlreadyExistDialog(final Bundle bundle)
//    {
//        new AlertDialog.Builder(containerActivity)
//                .setMessage(containerActivity.getString(R.string.auth_dlg_title_email_already_exist))
//                .setCancelable(false)
//                .setPositiveButton(containerActivity.getString(R.string.label_login), new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog, int id)
//                    {
//                        containerActivity.doDrawerMenuSettings(null, true);
//						Utils.logoutApp(containerActivity, true, bundle);
//                    }
//                })
//				.setNegativeButton(containerActivity.getString(R.string.label_cancel), null)
//                .show();
//    }
//
//    public void showSessionInvalidateDialog(final Bundle bundle)
//    {
//        new AlertDialog.Builder(containerActivity)
//            .setMessage(containerActivity.getString(R.string.auth_dlg_title_session_expired_login_again))
//            .setCancelable(false)
//            .setPositiveButton(containerActivity.getString(R.string.label_login), new DialogInterface.OnClickListener()
//            {
//                public void onClick(DialogInterface dialog, int id)
//                {
//                    containerActivity.doDrawerMenuSettings(null, true);
//					Utils.logoutApp(containerActivity, true, bundle);
//                }
//            })
//            .show();
//    }

    public <T extends Fragment> boolean showErrorMessageFragment(UIMessageType uiMessageType, Class<T> targetFragmentClass, Bundle bundle) {
        if (!isAdded()) {
            return false;
        }

        if (bundle == null) {
            bundle = new Bundle();
        }
        if (targetFragmentClass.getName().equals(SMEKhabarFragment.class.getName())){
            return false;
        }
        if (uiMessageType == UIMessageType.UNAUTHORIZE) {
            Utils.showSessionInvalidateDialog(baseActivity, bundle);
            return true;
        } else if (uiMessageType == UIMessageType.SERVER_ERROR) {
            Intent intent = new Intent(baseActivity, ChildActivity.class);
            intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, Constants.SERVER_ERROR_FRAGMENT_CODE);
            startActivityForResult(intent, baseActivity.ERROR_ACTIVITY_REQUEST_CODE);
            return true;
        } else if ((uiMessageType == UIMessageType.NETWORK_NOT_AVAILABLE)) {
            bundle.putString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_NAME, targetFragmentClass.getName());
            bundle.putString(Constants.BUNDLE_KEY_TARGET_FRAGMENT_MESSAGE, getString(R.string.network_not_available));
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_ADD_TO_BACKSTACK, false);
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_CHECK_NETWORK, true);
            bundle.putBoolean(Constants.BUNDLE_KEY_IS_CLEAR_BACKSTACK, true);
            Intent intent = new Intent(baseActivity, ChildActivity.class);
            intent.putExtras(bundle);
            intent.putExtra(Constants.BUNDLE_KEY_MENU_ID, Constants.NETWORK_ERROR_FRAGMENT_CODE);
            startActivityForResult(intent, baseActivity.ERROR_ACTIVITY_REQUEST_CODE);

            return true;
        }
        return false;
    }
}
