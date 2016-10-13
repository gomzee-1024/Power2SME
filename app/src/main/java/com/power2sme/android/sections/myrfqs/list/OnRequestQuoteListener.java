package com.power2sme.android.sections.myrfqs.list;

import com.power2sme.android.entities.v3.Wishlist_v3;
import com.power2sme.android.utilities.logging.ACError;

public interface OnRequestQuoteListener 
{
	void onRequestQuoteStart();
	void onRequestQuoteEnd();
	void onRequestQuoteSuccess(Wishlist_v3 rfq);
	void onRequestQuoteError(Wishlist_v3 rfq, ACError error);
}
