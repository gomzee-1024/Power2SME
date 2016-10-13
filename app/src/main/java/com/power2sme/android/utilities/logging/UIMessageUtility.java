package com.power2sme.android.utilities.logging;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.SuperToast;
import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.sections.activities.BaseAppCompatActivity;

public class UIMessageUtility 
{
//	public static void displayUIMessage(Fragment fragment, UIMessage uiMessage)
//	{
//		if(!fragment.isVisible())
//		{
//			return;
//		}
//		if(fragment.getActivity() instanceof ContainerActivity)
//		{
//			ContainerActivity context = (ContainerActivity)fragment.getActivity();
//			displayUIMessage(context, uiMessage);
//		}
//	}
	public static void displayUIMessage(BaseAppCompatActivity context, UIMessage uiMessage)
	{
		if(context.isAppInBackground)
		{
			return;
		}
		
		switch(uiMessage.getUiMessageType())
		{
			case SUCCESS:
			case ERROR:
			case INFO:
			case DEBUG:
			case WARNING:
			case SERVER_ERROR:
			case NETWORK_NOT_AVAILABLE:
			{
				showToast(context, uiMessage.getMessage());
				break;
			}
			case DIALOG_OK:
			{
				showDialog(context, uiMessage.getMessage(), false);
				break;
			}
			case DIALOG_OK_BACK:
			{
				showDialog(context, uiMessage.getMessage(), true);
				break;
			}
		};
	}
	public static void showDialog(final Activity context, String message, final boolean isBackOnOKPressed)
	{
		new AlertDialog.Builder(context)
	    .setMessage(message)
	    .setCancelable(false)
	    .setPositiveButton("OK", new DialogInterface.OnClickListener() 
	    {
	        public void onClick(DialogInterface dialog, int id) 
	        {
	        	dialog.dismiss();
	        	if(isBackOnOKPressed)
	        	{
	        		MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
	        		//if(myAccountApplication.getLoginStatus()!=LoginStatus.ONLY_LEAD_ID)
	        		//{
	        			context.onBackPressed();	
	        		//}
	        	}
	        }
	    })
	    .show();
	}
	public static void showToast(Context context, String message)
	{
		SuperToast.cancelAllSuperToasts();
		SuperToast superToast = SuperToast.create(context, message, SuperToast.Duration.MEDIUM, SuperToast.Animations.FLYIN);
        superToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		superToast.setBackground(R.color.blue_gred_start_color);
		superToast.setTextColor(context.getResources().getColor(R.color.white));
		superToast.setTextSize(20);
		superToast.show();
	}
}
