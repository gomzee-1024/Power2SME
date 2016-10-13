package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 14/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerLogin_v3 implements Parcelable
{
    @JsonProperty("id")
    private String id;
    @JsonProperty("smeId")
    private String smeId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("password")
    private String password;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("company_name")
    private String company_name;
    @JsonProperty("first_name")
    private String first_name;
    @JsonProperty("last_name")
    private String last_name;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("state")
    private String state;
    @JsonProperty("city")
    private String city;
    @JsonProperty("primary_email")
    private String primary_email;
    @JsonProperty("secondary_email")
    private String secondary_email;
    @JsonProperty("secondary_email2")
    private String secondary_email2;
    @JsonProperty("finance_emai")
    private String finance_email;
    @JsonProperty("primary_phone")
    private String primary_phone;
    @JsonProperty("secondary_phone")
    private String secondary_phone;
    @JsonProperty("finance_landline")
    private String finance_landline;
    @JsonProperty("finance_mobile")
    private String finance_mobile;
    @JsonProperty("secondary_phone2")
    private String secondary_phone2;
    @JsonProperty("secondary_phone3")
    private String secondary_phone3;
    @JsonProperty("farmer_first_name")
    private String farmer_first_name;
    @JsonProperty("farmer_last_name")
    private String farmer_last_name;
    @JsonProperty("farmer_id")
    private String farmer_id;
    @JsonProperty("farmer_email")
    private String farmer_email;
    @JsonProperty("farmer_mobile")
    private String farmer_mobile;
    @JsonProperty("kam_first_name")
    private String kam_first_name;
    @JsonProperty("kam_last_name")
    private String kam_last_name;
    @JsonProperty("kam_id")
    private String kam_id;
    @JsonProperty("kam_email")
    private String kam_email;
    @JsonProperty("kam_mobile")
    private String kam_mobile;
    @JsonProperty("isCoRegInERP")
    private String isCoRegInERP;


    public CustomerLogin_v3(){}
    public CustomerLogin_v3(Parcel in)
    {
        id = in.readString();
        smeId = in.readString();
        userId = in.readString();
        password = in.readString();
        sessionId = in.readString();
        company_name = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        postcode = in.readString();
        state = in.readString();
        city = in.readString();
        primary_email = in.readString();
        secondary_email = in.readString();
        secondary_email2 = in.readString();
        finance_email = in.readString();
        primary_phone = in.readString();
        secondary_phone = in.readString();
        finance_landline = in.readString();
        finance_mobile = in.readString();
        secondary_phone2 = in.readString();
        secondary_phone3 = in.readString();
        farmer_first_name = in.readString();
        farmer_last_name = in.readString();
        farmer_id = in.readString();
        farmer_email = in.readString();
        farmer_mobile = in.readString();
        kam_first_name = in.readString();
        kam_last_name = in.readString();
        kam_id = in.readString();
        kam_email = in.readString();
        kam_mobile = in.readString();
        isCoRegInERP = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(smeId);
        dest.writeString(userId);
        dest.writeString(password);
        dest.writeString(sessionId);
        dest.writeString(company_name);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(postcode);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(primary_email);
        dest.writeString(secondary_email);
        dest.writeString(secondary_email2);
        dest.writeString(finance_email);
        dest.writeString(primary_phone);
        dest.writeString(secondary_phone);
        dest.writeString(finance_landline);
        dest.writeString(finance_mobile);
        dest.writeString(secondary_phone2);
        dest.writeString(secondary_phone3);
        dest.writeString(farmer_first_name);
        dest.writeString(farmer_last_name);
        dest.writeString(farmer_id);
        dest.writeString(farmer_email);
        dest.writeString(farmer_mobile);
        dest.writeString(kam_first_name);
        dest.writeString(kam_last_name);
        dest.writeString(kam_id);
        dest.writeString(kam_email);
        dest.writeString(kam_mobile);
        dest.writeString(isCoRegInERP);
    }
    public static final Creator<CustomerLogin_v3> CREATOR = new Creator<CustomerLogin_v3>()
    {
        public CustomerLogin_v3 createFromParcel(Parcel in)
        {
            return new CustomerLogin_v3(in);
        }

        public CustomerLogin_v3[] newArray (int size)
        {
            return new CustomerLogin_v3[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmeId() {
        return smeId;
    }

    public void setSmeId(String smeId) {
        this.smeId = smeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrimary_email() {
        return primary_email;
    }

    public void setPrimary_email(String primary_email) {
        this.primary_email = primary_email;
    }

    public String getSecondary_email() {
        return secondary_email;
    }

    public void setSecondary_email(String secondary_email) {
        this.secondary_email = secondary_email;
    }

    public String getSecondary_email2() {
        return secondary_email2;
    }

    public void setSecondary_email2(String secondary_email2) {
        this.secondary_email2 = secondary_email2;
    }

    public String getFinance_email() {
        return finance_email;
    }

    public void setFinance_email(String finance_email) {
        this.finance_email = finance_email;
    }

    public String getPrimary_phone() {
        return primary_phone;
    }

    public void setPrimary_phone(String primary_phone) {
        this.primary_phone = primary_phone;
    }

    public String getSecondary_phone() {
        return secondary_phone;
    }

    public void setSecondary_phone(String secondary_phone) {
        this.secondary_phone = secondary_phone;
    }

    public String getFinance_landline() {
        return finance_landline;
    }

    public void setFinance_landline(String finance_landline) {
        this.finance_landline = finance_landline;
    }

    public String getFinance_mobile() {
        return finance_mobile;
    }

    public void setFinance_mobile(String finance_mobile) {
        this.finance_mobile = finance_mobile;
    }

    public String getSecondary_phone2() {
        return secondary_phone2;
    }

    public void setSecondary_phone2(String secondary_phone2) {
        this.secondary_phone2 = secondary_phone2;
    }

    public String getSecondary_phone3() {
        return secondary_phone3;
    }

    public void setSecondary_phone3(String secondary_phone3) {
        this.secondary_phone3 = secondary_phone3;
    }

    public String getFarmer_first_name() {
        return farmer_first_name;
    }

    public void setFarmer_first_name(String farmer_first_name) {
        this.farmer_first_name = farmer_first_name;
    }

    public String getFarmer_last_name() {
        return farmer_last_name;
    }

    public void setFarmer_last_name(String farmer_last_name) {
        this.farmer_last_name = farmer_last_name;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getFarmer_email() {
        return farmer_email;
    }

    public void setFarmer_email(String farmer_email) {
        this.farmer_email = farmer_email;
    }

    public String getFarmer_mobile() {
        return farmer_mobile;
    }

    public void setFarmer_mobile(String farmer_mobile) {
        this.farmer_mobile = farmer_mobile;
    }

    public String getKam_first_name() {
        return kam_first_name;
    }

    public void setKam_first_name(String kam_first_name) {
        this.kam_first_name = kam_first_name;
    }

    public String getKam_last_name() {
        return kam_last_name;
    }

    public void setKam_last_name(String kam_last_name) {
        this.kam_last_name = kam_last_name;
    }

    public String getKam_id() {
        return kam_id;
    }

    public void setKam_id(String kam_id) {
        this.kam_id = kam_id;
    }

    public String getKam_email() {
        return kam_email;
    }

    public void setKam_email(String kam_email) {
        this.kam_email = kam_email;
    }

    public String getKam_mobile() {
        return kam_mobile;
    }

    public void setKam_mobile(String kam_mobile) {
        this.kam_mobile = kam_mobile;
    }

    public boolean getIsCoRegInERP() {
        return isCoRegInERP!=null && isCoRegInERP.length()>0 && (isCoRegInERP.equals("true") || isCoRegInERP.equals("1"))? true:false;
    }

    public void setIsCoRegInERP(String isCoRegInERP) {
        this.isCoRegInERP = isCoRegInERP;
    }
}
