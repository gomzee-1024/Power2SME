package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.entities.v3.Wishlist_v3;
import com.power2sme.android.utilities.logging.ACError;

public interface OnAcceptQuoteListener 
{
	void onAcceptQuoteStart();
	void onAcceptQuoteEnd();
	void onAcceptQuoteSuccess(Wishlist_v3 rfq);
	void onAcceptQuoteError(Wishlist_v3 rfq, ACError error);
}
