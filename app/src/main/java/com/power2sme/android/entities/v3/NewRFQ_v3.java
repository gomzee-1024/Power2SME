package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewRFQ_v3 implements Parcelable
{
    @JsonProperty("object_type_id")
    private String object_type_id;
    @JsonProperty("organisation")
    private Organization_v3 organisation;
    @JsonProperty("opportunity")
    private Opportunity_v3 opportunity;
    @JsonProperty("wishlist")
    private Wishlist_v3 wishlist;
    @JsonProperty("reorder")
    private ReOrder_v3 reorder;
    @JsonProperty("deal")
    private Deal_v3 deal;
    @JsonProperty("customerLogin")
    private CustomerLogin_v3 customerLogin;

	public NewRFQ_v3(){}
	public NewRFQ_v3(Parcel in)
	{
        object_type_id = in.readString();
        organisation = in.readParcelable(Organization_v3.class.getClassLoader());
        opportunity = in.readParcelable(Opportunity_v3.class.getClassLoader());
        wishlist = in.readParcelable(Wishlist_v3.class.getClassLoader());
        reorder = in.readParcelable(ReOrder_v3.class.getClassLoader());
        deal = in.readParcelable(Deal_v3.class.getClassLoader());
        customerLogin = in.readParcelable(CustomerLogin_v3.class.getClassLoader());
	}
	@Override
	public int describeContents()
	{
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
        dest.writeString(object_type_id);
        dest.writeParcelable(organisation, flags);
        dest.writeParcelable(opportunity, flags);
        dest.writeParcelable(wishlist, flags);
        dest.writeParcelable(reorder, flags);
        dest.writeParcelable(deal, flags);
        dest.writeParcelable(customerLogin, flags);
	}
	public static final Creator<NewRFQ_v3> CREATOR = new Creator<NewRFQ_v3>()
	{
		public NewRFQ_v3 createFromParcel(Parcel in)
		{
			return new NewRFQ_v3(in);
		}

		public NewRFQ_v3[] newArray (int size)
		{
			return new NewRFQ_v3[size];
		}
	};

    public String getObject_type_id() {
        return object_type_id;
    }

    public void setObject_type_id(String object_type_id) {
        this.object_type_id = object_type_id;
    }

    public Organization_v3 getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organization_v3 organisation) {
        this.organisation = organisation;
    }

    public Opportunity_v3 getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity_v3 opportunity) {
        this.opportunity = opportunity;
    }

    public Wishlist_v3 getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist_v3 wishlist) {
        this.wishlist = wishlist;
    }

    public ReOrder_v3 getReorder() {
        return reorder;
    }

    public void setReorder(ReOrder_v3 reorder) {
        this.reorder = reorder;
    }

    public Deal_v3 getDeal() {
        return deal;
    }

    public void setDeal(Deal_v3 deal) {
        this.deal = deal;
    }

    public CustomerLogin_v3 getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(CustomerLogin_v3 customerLogin) {
        this.customerLogin = customerLogin;
    }
}
