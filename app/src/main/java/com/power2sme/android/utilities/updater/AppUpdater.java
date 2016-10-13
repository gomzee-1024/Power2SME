package com.power2sme.android.utilities.updater;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.APIs;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;
import com.power2sme.android.sections.settings.RateThisAppFragment;

/**
 * Created by tausif on 1/7/15.
 */
public class AppUpdater extends DialogFragment
{
    private static final int UPDATE_CHECK_INTERVAL = 7*24*60*60*1000;//3600000;
    private static AppUpdateStatus updateStatus;
    private static BaseAppCompatActivity containerActivity;

    private static SharedPreferences prefs;
    private static int maxVerCode;
    private static int minVerCode;
    private static int currentVerCode;


    private static DialogFragment newInstance()
    {
        AppUpdater dialog = new AppUpdater();

        return dialog;
    }

    public static enum AppUpdateStatus
    {
        FORCE_UPDATE_APP, SUGGEST_UPDATE_APP, NO_UPDATE_AVAILABLE;
    }

    public static boolean isRightTimeForUpdate()
    {
        long lastUpdateCheckTime = prefs.getLong(Constants.PREFERENCE_LAST_UPDATE_CHECK_TIME, 0l);
        long currentUpdateCheckTime = System.currentTimeMillis();
        if(currentUpdateCheckTime-lastUpdateCheckTime >= UPDATE_CHECK_INTERVAL)
        {
            prefs.edit().putLong(Constants.PREFERENCE_LAST_UPDATE_CHECK_TIME, currentUpdateCheckTime).commit();
            return true;
        }
        return false;
    }

    public static void checkForAppUpdate(BaseAppCompatActivity mActivity)
    {
        containerActivity = mActivity;
        MyAccountApplication myAccountApplication = ((MyAccountApplication) mActivity.getApplicationContext());
        prefs = myAccountApplication.getPrefs();
        updateStatus = AppUpdater.isUpdateAvailable(containerActivity);
        if(updateStatus == AppUpdater.AppUpdateStatus.NO_UPDATE_AVAILABLE)
        {
            return;
        }
        else if(updateStatus == AppUpdater.AppUpdateStatus.SUGGEST_UPDATE_APP || updateStatus == AppUpdater.AppUpdateStatus.FORCE_UPDATE_APP)
        {
            if(updateStatus == AppUpdater.AppUpdateStatus.SUGGEST_UPDATE_APP && !AppUpdater.isRightTimeForUpdate())
            {
                return;
            }

            FragmentTransaction ft = containerActivity.getSupportFragmentManager().beginTransaction();
            Fragment prev = containerActivity.getSupportFragmentManager().findFragmentByTag("updatedialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            // Create and show the dialog.
            DialogFragment newFragment = AppUpdater.newInstance();

            newFragment.show(ft, "updatedialog");
        }
    }

    public static AppUpdateStatus isUpdateAvailable(Context context)
    {
        try
        {
            MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
            prefs = myAccountApplication.getPrefs();
            String maxVer = prefs.getString(Constants.PREFERENCE_SERVER_APP_MAX_VERSION, null);
            String minVer = prefs.getString(Constants.PREFERENCE_SERVER_APP_MIN_VERSION, null);
            if(maxVer==null || minVer==null)
                return AppUpdateStatus.NO_UPDATE_AVAILABLE;
            maxVerCode = Integer.parseInt(maxVer);
            minVerCode = Integer.parseInt(minVer);

            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            currentVerCode = pInfo.versionCode;

            if(currentVerCode>=minVerCode)
            {
                if(currentVerCode < maxVerCode)
                {
                    //update avalable
                    return AppUpdateStatus.SUGGEST_UPDATE_APP;
                }
            }
            else
            {
                //force update
                return AppUpdateStatus.FORCE_UPDATE_APP;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return AppUpdateStatus.NO_UPDATE_AVAILABLE;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity())
        .setIcon(R.drawable.app_logo)
        .setTitle(getString(R.string.updator_dlg_title));



        builder.setPositiveButton(getString(R.string.updator_dlg_pos_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try
                {
                    if(Constants.isInternalBuild)
                    {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(APIs.URL_PRODUCTION_APK));
                        startActivity(browserIntent);
                    }
                    else
                    {
                        String packageName = getActivity().getPackageName();
                        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    }

                }
                catch (ActivityNotFoundException anfe)
                {
                    containerActivity.openContainerActivityFragment(RateThisAppFragment.class, null, true, true, false);
                }
            }
        });

        if(updateStatus == AppUpdateStatus.SUGGEST_UPDATE_APP)
        {
            builder.setNegativeButton(getString(R.string.updator_dlg_suggest_neg_btn), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            })
                    .setMessage(getString(R.string.updator_dlg_suggest_message));
        }
        else if(updateStatus == AppUpdateStatus.FORCE_UPDATE_APP)
        {
            builder.setNegativeButton(getString(R.string.updator_dlg_force_neg_btn), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    killApp();
                }
            }).setMessage(getString(R.string.updator_dlg_force_message));
        }
        AlertDialog dlg = builder.create();
        dlg.setCanceledOnTouchOutside(false);
        return dlg;
    }

    private void killApp()
    {
        Intent intent=new Intent();
        intent.putExtra("EXIT_ME", true);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        if(updateStatus == AppUpdateStatus.FORCE_UPDATE_APP)
        {
            if(currentVerCode<minVerCode && getActivity()!=null)
            {
                killApp();
            }
        }
        super.onDismiss(dialog);
    }
}
