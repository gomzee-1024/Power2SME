package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by tausif on 15/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Parcelable
{
    @JsonProperty("oldNew")
    private String oldNew;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("ship_pincode")
    private String ship_pincode;
    @JsonProperty("smeId")
    private String smeId;
    @JsonProperty("leadSource")
    private String leadSource;
    @JsonProperty("ship_country")
    private String ship_country;
    @JsonProperty("bill_street")
    private String bill_street;
    @JsonProperty("taxationPreference")
    private TaxationPreference_v3 taxationPreference;
    @JsonProperty("bill_city")
    private String bill_city;
    @JsonProperty("verticals")
    private ArrayList<String> verticals;
    @JsonProperty("farmerid")
    private String farmerid;
    @JsonProperty("setype")
    private String setype;
    @JsonProperty("smcreatorid")
    private String smcreatorid;
    @JsonProperty("ship_street")
    private String ship_street;
    @JsonProperty("company_name")
    private String company_name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("bill_state")
    private String bill_state;
    @JsonProperty("bill_pincode")
    private String bill_pincode;
    @JsonProperty("bill_country")
    private String bill_country;
    @JsonProperty("crmid")
    private String crmid;
    @JsonProperty("invoices")
    private ArrayList<String> invoices;
    @JsonProperty("contactPerson")
    private String contactPerson;
    @JsonProperty("ship_city")
    private String ship_city;
    @JsonProperty("ship_state")
    private String ship_state;


//    private String companyName;
//    private String smeId;
//    private String date;
//    private String email;
//    private String phone;

    public Customer(){}
    public Customer(Parcel in)
    {
        invoices=new ArrayList<String>();
        verticals=new ArrayList<String>();

        oldNew=in.readString();
        phone=in.readString();
        ship_pincode=in.readString();
        smeId=in.readString();
        leadSource=in.readString();
        ship_country=in.readString();
        bill_street=in.readString();
        taxationPreference = in.readParcelable(TaxationPreference_v3.class.getClassLoader());
        bill_city=in.readString();
        in.readStringList(verticals);
        farmerid=in.readString();
        setype=in.readString();
        smcreatorid=in.readString();
        ship_street=in.readString();
        company_name=in.readString();
        email=in.readString();
        bill_state=in.readString();
        bill_pincode=in.readString();
        bill_country=in.readString();
        crmid=in.readString();
        in.readStringList(invoices);
        contactPerson=in.readString();
        ship_city=in.readString();
        ship_state=in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(oldNew);
        dest.writeString(phone);
        dest.writeString(ship_pincode);
        dest.writeString(smeId);
        dest.writeString(leadSource);
        dest.writeString(ship_country);
        dest.writeString(bill_street);
        dest.writeParcelable(taxationPreference, flags);
        dest.writeString(bill_city);
        dest.writeStringList(verticals);
        dest.writeString(farmerid);
        dest.writeString(setype);
        dest.writeString(smcreatorid);
        dest.writeString(ship_street);
        dest.writeString(company_name);
        dest.writeString(email);
        dest.writeString(bill_state);
        dest.writeString(bill_pincode);
        dest.writeString(bill_country);
        dest.writeString(crmid);
        dest.writeStringList(invoices);
        dest.writeString(contactPerson);
        dest.writeString(ship_city);
        dest.writeString(ship_state);
    }
    public static final Creator<Customer> CREATOR = new Creator<Customer>()
    {
        public Customer createFromParcel(Parcel in)
        {
            return new Customer(in);
        }

        public Customer[] newArray (int size)
        {
            return new Customer[size];
        }
    };

    public String getOldNew() {
        return oldNew;
    }

    public void setOldNew(String oldNew) {
        this.oldNew = oldNew;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShip_pincode() {
        return ship_pincode;
    }

    public void setShip_pincode(String ship_pincode) {
        this.ship_pincode = ship_pincode;
    }

    public String getSmeId() {
        return smeId;
    }

    public void setSmeId(String smeId) {
        this.smeId = smeId;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getShip_country() {
        return ship_country;
    }

    public void setShip_country(String ship_country) {
        this.ship_country = ship_country;
    }

    public String getBill_street() {
        return bill_street;
    }

    public void setBill_street(String bill_street) {
        this.bill_street = bill_street;
    }

    public TaxationPreference_v3 getTaxationPreference() {
        return taxationPreference;
    }

    public void setTaxationPreference(TaxationPreference_v3 taxationPreference) {
        this.taxationPreference = taxationPreference;
    }

    public String getBill_city() {
        return bill_city;
    }

    public void setBill_city(String bill_city) {
        this.bill_city = bill_city;
    }

    public ArrayList<String> getVerticals() {
        return verticals;
    }

    public void setVerticals(ArrayList<String> verticals) {
        this.verticals = verticals;
    }

    public String getFarmerid() {
        return farmerid;
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getSetype() {
        return setype;
    }

    public void setSetype(String setype) {
        this.setype = setype;
    }

    public String getSmcreatorid() {
        return smcreatorid;
    }

    public void setSmcreatorid(String smcreatorid) {
        this.smcreatorid = smcreatorid;
    }

    public String getShip_street() {
        return ship_street;
    }

    public void setShip_street(String ship_street) {
        this.ship_street = ship_street;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBill_state() {
        return bill_state;
    }

    public void setBill_state(String bill_state) {
        this.bill_state = bill_state;
    }

    public String getBill_pincode() {
        return bill_pincode;
    }

    public void setBill_pincode(String bill_pincode) {
        this.bill_pincode = bill_pincode;
    }

    public String getBill_country() {
        return bill_country;
    }

    public void setBill_country(String bill_country) {
        this.bill_country = bill_country;
    }

    public String getCrmid() {
        return crmid;
    }

    public void setCrmid(String crmid) {
        this.crmid = crmid;
    }

    public ArrayList<String> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<String> invoices) {
        this.invoices = invoices;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getShip_city() {
        return ship_city;
    }

    public void setShip_city(String ship_city) {
        this.ship_city = ship_city;
    }

    public String getShip_state() {
        return ship_state;
    }

    public void setShip_state(String ship_state) {
        this.ship_state = ship_state;
    }
}
