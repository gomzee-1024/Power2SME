package com.power2sme.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.CustomerLogin_v3;
import com.power2sme.android.entities.v3.Organization_v3;
import com.power2sme.android.utilities.Utils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfo implements Parcelable
{
	@JsonProperty("object_type_id")
	private String object_type_id;
	@JsonProperty("organisation")
	private Organization_v3 organisation;
	@JsonProperty("customerLogin")
	private CustomerLogin_v3 customerLogin;

	public ContactInfo(){}
	public ContactInfo(Parcel in)
	{
		object_type_id = in.readString();
		organisation = in.readParcelable(Organization_v3.class.getClassLoader());
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
		dest.writeParcelable(customerLogin, flags);
	}
	public static final Creator<ContactInfo> CREATOR = new Creator<ContactInfo>()
 	{
 		public ContactInfo createFromParcel(Parcel in)
 		{
 			return new ContactInfo(in);
 		}

 		public ContactInfo[] newArray (int size)
 		{
 			return new ContactInfo[size];
 		}
 	};

	public String getObject_type_id() {
		return Utils.checkStringForNull(object_type_id);
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

	public CustomerLogin_v3 getCustomerLogin() {
		return customerLogin;
	}

	public void setCustomerLogin(CustomerLogin_v3 customerLogin) {
		this.customerLogin = customerLogin;
	}
}
