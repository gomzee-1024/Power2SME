package com.power2sme.android.utilities.drawermenu;
import com.power2sme.android.R;
public enum DrawerMenuItemsEnum 
{
	TAB_HOME(R.string.screen_title_home),
	TAB_ADD_NEW_RFQ(R.string.screen_title_rfq_add),
	TAB_SELECT_CUSTOMER(R.string.screen_title_select_customer),
    TAB_MARKET_PLACE(R.string.screen_title_market_place_home),
	TAB_MY_UPDATES(R.string.screen_title_updates),
	TAB_CONTACT_INFO(R.string.screen_title_contact_edit),
	TAB_MY_ORDERS(R.string.screen_title_orders),
	TAB_DELIVERY_ADDRESS(R.string.screen_title_delivery_address_list),
	TAB_MY_RFQ(R.string.screen_title_rfq),
	TAB_LOGIN(R.string.screen_title_login),
	TAB_DEALS(R.string.screen_title_deals),
	TAB_ABOUT_POWER2SME(R.string.screen_title_about_power2sme),
	TAB_RATE_THIS_APP(R.string.screen_title_rate_this_app),
	TAB_SME_KHABAR(R.string.screen_title_sme_khabar),
	TAB_PRIVACY_POLICY(R.string.screen_title_privacy_policy),
	TAB_TERMS_AND_CONDITION(R.string.screen_title_terms_and_condition),
	
//	HEAD_FOR_BUYERS(R.string.menu_head_for_buyers),
//	HEAD_FOR_SUPPLIERS(R.string.menu_head_for_suppliers),
//	HEAD_SETTINGS(R.string.menu_head_settings),

	OTHER_LOGOUT(R.string.screen_title_logout);
	
	
	
	int value;
	private DrawerMenuItemsEnum(int value)
	{
		this.value=value;
	}
	public int getSectionTitleResourceId()
	{
		return value;
	}
}
