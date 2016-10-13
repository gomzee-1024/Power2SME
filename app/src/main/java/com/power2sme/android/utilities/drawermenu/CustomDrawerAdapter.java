package com.power2sme.android.utilities.drawermenu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.power2sme.android.MyAccountApplication;
import com.power2sme.android.R;
import com.power2sme.android.conf.Constants;
import com.power2sme.android.utilities.styels.StyleTypes;
import com.power2sme.android.utilities.styels.StylesManager;

import java.util.List;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem>
{
	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResID;
	private String selectedItem;

	private OnClickSwitchCustomerListener onClickSwitchCustomerListener;
	public interface OnClickSwitchCustomerListener
	{
		void onCustomerSwitched();
	}
	public void setOnClickSwitchCustomerListener(OnClickSwitchCustomerListener onClickSwitchCustomerListener)
	{
		this.onClickSwitchCustomerListener=onClickSwitchCustomerListener;
	}
	public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) 
	{
		super(context, layoutResourceID, listItems);
		this.context = context;
		this.drawerItemList = listItems;
		this.layoutResID = layoutResourceID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		DrawerItemHolder drawerHolder;
		View view = convertView;
		if (view == null) 
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();
			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
			drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
//			drawerHolder.spinner = (Spinner) view.findViewById(R.id.drawerSpinner);
//			drawerHolder.spinner.setPadding(5,0,0,0);
			drawerHolder.title = (TextView) view.findViewById(R.id.drawerTitle);
			drawerHolder.headerLayout = (LinearLayout) view.findViewById(R.id.headerLayout);
			drawerHolder.itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
			drawerHolder.spinnerLayout = (LinearLayout) view.findViewById(R.id.spinnerLayout);
			drawerHolder.spinnerSuperParent = (LinearLayout) view.findViewById(R.id.spinnerSuperParent);
			
			StylesManager.getInstance(context).setTextViewStyle(drawerHolder.ItemName, StyleTypes.TextView_body1);
			StylesManager.getInstance(context).setTextViewStyle(drawerHolder.title, StyleTypes.TextView_Subhead1_accent1);
			view.setTag(drawerHolder);
		} 
		else 
		{
			drawerHolder = (DrawerItemHolder) view.getTag();
		}
		
		DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
		
		if(dItem.getTitle() == null && !dItem.isSpinner())
		{
//			if(dItem.getItemName().equals(getSelectedItem()))
//			{
//				view.setBackgroundColor(context.getResources().getColor(R.color.menu_selected_bg_color));
//			}
//			else
//			{
//				view.setBackgroundColor(context.getResources().getColor(R.color.menu_nonselected_bg_color));
//			}
		}
		
		if (dItem.isSpinner()) 
		{
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.spinnerSuperParent.setVisibility(LinearLayout.VISIBLE);
			
			View topHeadTitle = LayoutInflater.from(context).inflate(R.layout.drawermenu_headtitle_item, null);
			drawerHolder.spinnerLayout.removeAllViews();
			
			ImageView userImage=(ImageView)topHeadTitle.findViewById(R.id.left_pic);
			TextView name=(TextView)topHeadTitle.findViewById(R.id.text_main_name);
			TextView email=(TextView)topHeadTitle.findViewById(R.id.TextView_emailId);
			
			MyAccountApplication myAccountApplication = ((MyAccountApplication) context.getApplicationContext());
			SharedPreferences prefs = myAccountApplication.getPrefs();

	        if(myAccountApplication.getLoginStatus() != Constants.LoginStatus.NOT_LOGGED_IN)
	        {
				LinearLayout LinearLayout_switchParent=(LinearLayout)topHeadTitle.findViewById(R.id.LinearLayout_switchParent);
				if(myAccountApplication.isKAM())
				{
					String companyName = prefs.getString(Constants.PREFERENCE_AGENT_FULLNAME, "");
					name.setText("Acting on Behalf of");
					email.setText(companyName);
					userImage.setVisibility(ImageView.VISIBLE);

					LinearLayout_switchParent.setVisibility(LinearLayout.VISIBLE);
					ImageButton imageButton_switchSME=(ImageButton)topHeadTitle.findViewById(R.id.imageButton_switchSME);
					imageButton_switchSME.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							if(onClickSwitchCustomerListener!=null)
								onClickSwitchCustomerListener.onCustomerSwitched();
						}
					});
				}
				else
				{
					LinearLayout_switchParent.setVisibility(LinearLayout.GONE);
					String fullName = prefs.getString(Constants.PREFERENCE_CUSTOMER_FULLNAME, "");
					name.setText(fullName);
					String emailStr = prefs.getString(Constants.PREFERENCE_CUSTOMER_EMAIL, "");
					email.setText(emailStr);
					userImage.setVisibility(ImageView.GONE);
				}
				email.setVisibility(TextView.VISIBLE);
	        }
	        else
	        {
	        	name.setText("Welcome Guest");
	        	email.setVisibility(TextView.GONE);
	        }
			drawerHolder.spinnerLayout.addView(topHeadTitle);
		}
		else if (dItem.getTitle() != null) 
		{
			drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerSuperParent.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.title.setText(dItem.getTitle());
		} 
		else 
		{
			drawerHolder.headerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerLayout.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.spinnerSuperParent.setVisibility(LinearLayout.INVISIBLE);
			drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);
			drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));
			drawerHolder.ItemName.setText(dItem.getItemName());
		}
		return view;
	}

	private static class DrawerItemHolder {
		TextView ItemName, title;
		ImageView icon;
		LinearLayout headerLayout, itemLayout, spinnerLayout,spinnerSuperParent;
//		Spinner spinner;
	}
	/**
	 * @return the selectedItem
	 */
	public String getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}
}