package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

import java.util.ArrayList;

/**
 * Created by tausif on 13/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization_v3 implements Parcelable
{
    @JsonProperty("crmid")
    private String crmid;
    @JsonProperty("smcreatorid")
    private String smcreatorid;
    @JsonProperty("farmerid")
    private String farmerid;
    @JsonProperty("setype")
    private String setype;
    @JsonProperty("company_name")
    private String company_name;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("contactPerson")
    private String contactPerson;
    @JsonProperty("leadSource")
    private String leadSource;
    @JsonProperty("taxationPreference")
    private TaxationPreference_v3 taxationPreference;
    @JsonProperty("ship_city")
    private String ship_city;
    @JsonProperty("ship_pincode")
    private String ship_pincode;
    @JsonProperty("ship_country")
    private String ship_country;
    @JsonProperty("ship_state")
    private String ship_state;
    @JsonProperty("ship_street")
    private String ship_street;
    @JsonProperty("bill_city")
    private String bill_city;
    @JsonProperty("bill_pincode")
    private String bill_pincode;
    @JsonProperty("bill_country")
    private String bill_country;
    @JsonProperty("bill_state")
    private String bill_state;
    @JsonProperty("bill_street")
    private String bill_street;
    @JsonProperty("verticals")
    private ArrayList<String> verticals;
    @JsonProperty("oldNew")
    private String oldNew;
    @JsonProperty("smeId")
    private String smeId;
    @JsonProperty("invoices")
    private ArrayList<Invoices_v3> invoices;
    @JsonProperty("shippingAddressCode")
    private String shippingAddressCode;

    public Organization_v3(){}
    public Organization_v3(Parcel in)
    {
        verticals = new ArrayList<String>();
        invoices=new ArrayList<Invoices_v3>();

        crmid = in.readString();
        smcreatorid = in.readString();
        farmerid = in.readString();
        setype = in.readString();
        company_name = in.readString();
        phone = in.readString();
        email = in.readString();
        contactPerson = in.readString();
        leadSource = in.readString();
        taxationPreference = in.readParcelable(TaxationPreference_v3.class.getClassLoader());
        ship_city = in.readString();
        ship_pincode = in.readString();
        ship_country = in.readString();
        ship_state = in.readString();
        ship_street = in.readString();
        bill_city = in.readString();
        bill_pincode = in.readString();
        bill_country = in.readString();
        bill_state = in.readString();
        bill_street = in.readString();
        in.readStringList(verticals);
        oldNew = in.readString();
        smeId = in.readString();
        in.readTypedList(invoices, Invoices_v3.CREATOR);
        shippingAddressCode = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(crmid);
        dest.writeString(smcreatorid);
        dest.writeString(farmerid);
        dest.writeString(setype);
        dest.writeString(company_name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(contactPerson);
        dest.writeString(leadSource);
        dest.writeParcelable(taxationPreference, flags);
        dest.writeString(ship_city);
        dest.writeString(ship_pincode);
        dest.writeString(ship_country);
        dest.writeString(ship_state);
        dest.writeString(ship_street);
        dest.writeString(bill_city);
        dest.writeString(bill_pincode);
        dest.writeString(bill_country);
        dest.writeString(bill_state);
        dest.writeString(bill_street);
        dest.writeStringList(verticals);
        dest.writeString(oldNew);
        dest.writeString(smeId);
        dest.writeTypedList(invoices);
        dest.writeString(shippingAddressCode);
    }
    public static final Creator<Organization_v3> CREATOR = new Creator<Organization_v3>()
    {
        public Organization_v3 createFromParcel(Parcel in)
        {
            return new Organization_v3(in);
        }

        public Organization_v3[] newArray (int size)
        {
            return new Organization_v3[size];
        }
    };

    public String getCrmid() {
        return Utils.checkStringForNull(crmid);
    }

    public void setCrmid(String crmid) {
        this.crmid = crmid;
    }

    public String getSmcreatorid() {
        return Utils.checkStringForNull(smcreatorid);
    }

    public void setSmcreatorid(String smcreatorid) {
        this.smcreatorid = smcreatorid;
    }

    public String getFarmerid() {
        return Utils.checkStringForNull(farmerid);
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getSetype() {
        return Utils.checkStringForNull(setype);
    }

    public void setSetype(String setype) {
        this.setype = setype;
    }

    public String getCompany_name() {
        return Utils.checkStringForNull(company_name);
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPhone() {
        return Utils.checkStringForNull(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return Utils.checkStringForNull(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return Utils.checkStringForNull(contactPerson);
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLeadSource() {
        return Utils.checkStringForNull(leadSource);
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public TaxationPreference_v3 getTaxationPreference() {
        return taxationPreference;
    }

    public void setTaxationPreference(TaxationPreference_v3 taxationPreference) {
        this.taxationPreference = taxationPreference;
    }

    public String getShip_city() {
        return Utils.checkStringForNull(ship_city);
    }

    public void setShip_city(String ship_city) {
        this.ship_city = ship_city;
    }

    public String getShip_pincode() {
        return Utils.checkStringForNull(ship_pincode);
    }

    public void setShip_pincode(String ship_pincode) {
        this.ship_pincode = ship_pincode;
    }

    public String getShip_country() {
        return Utils.checkStringForNull(ship_country);
    }

    public void setShip_country(String ship_country) {
        this.ship_country = ship_country;
    }

    public String getShip_state() {
        return Utils.checkStringForNull(ship_state);
    }

    public void setShip_state(String ship_state) {
        this.ship_state = ship_state;
    }

    public String getShip_street() {
        return Utils.checkStringForNull(ship_street);
    }

    public void setShip_street(String ship_street) {
        this.ship_street = ship_street;
    }

    public String getBill_city() {
        return Utils.checkStringForNull(bill_city);
    }

    public void setBill_city(String bill_city) {
        this.bill_city = bill_city;
    }

    public String getBill_pincode() {
        return Utils.checkStringForNull(bill_pincode);
    }

    public void setBill_pincode(String bill_pincode) {
        this.bill_pincode = bill_pincode;
    }

    public String getBill_country() {
        return Utils.checkStringForNull(bill_country);
    }

    public void setBill_country(String bill_country) {
        this.bill_country = bill_country;
    }

    public String getBill_state() {
        return Utils.checkStringForNull( bill_state);
    }

    public void setBill_state(String bill_state) {
        this.bill_state = bill_state;
    }

    public String getBill_street() {
        return Utils.checkStringForNull(bill_street);
    }

    public void setBill_street(String bill_street) {
        this.bill_street = bill_street;
    }

    public ArrayList<String> getVerticals() {
        return verticals;
    }

    public void setVerticals(ArrayList<String> verticals) {
        this.verticals = verticals;
    }

    public String getOldNew() {
        return Utils.checkStringForNull(oldNew);
    }

    public void setOldNew(String oldNew) {
        this.oldNew = oldNew;
    }

    public String getSmeId() {
        return Utils.checkStringForNull(smeId);
    }

    public void setSmeId(String smeId) {
        this.smeId = smeId;
    }

    public ArrayList<Invoices_v3> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoices_v3> invoices) {
        this.invoices = invoices;
    }

    public String getShippingAddressCode() {
        return shippingAddressCode;
    }

    public void setShippingAddressCode(String shippingAddressCode) {
        this.shippingAddressCode = shippingAddressCode;
    }
}
