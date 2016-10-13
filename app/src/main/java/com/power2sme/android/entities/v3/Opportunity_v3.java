package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by tausif on 13/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Opportunity_v3 implements Parcelable
{
    @JsonProperty("oppid")
    private String oppid;
    @JsonProperty("orgid")
    private String orgid;
    @JsonProperty("smcreatorid")
    private String smcreatorid;
    @JsonProperty("farmerId")
    private String farmerId;
    @JsonProperty("setype")
    private String setype;
    @JsonProperty("opportunity_name")
    private String opportunity_name;
    @JsonProperty("leadSource")
    private String leadSource;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sales_stage")
    private String sales_stage;
    @JsonProperty("rfqNo")
    private String rfqNo;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("taxationPref")
    private TaxationPreference_v3 taxationPref;
    @JsonProperty("urgency")
    private Urgency_v3 urgency;
    @JsonProperty("freightArrangement")
    private String freightArrangement;
    @JsonProperty("shippingAddress")
    private String shippingAddress;
    @JsonProperty("deliveryAddress")
    private String deliveryAddress;
    @JsonProperty("paymentMethodCode")
    private String paymentMethodCode;
    @JsonProperty("paymentTermsCode")
    private String paymentTermsCode;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("requiredTimeFrame")
    private RequiredTimeFrame_v3 requiredTimeFrame;
    @JsonProperty("scmTerritoryCode")
    private String scmTerritoryCode;
    @JsonProperty("opportunityLine")
    private ArrayList<OpportunityLine_v3> opportunityLine;
    @JsonProperty("formC")
    private String formC;
    @JsonProperty("paymentTermDays")
    private String paymentTermDays;


    public Opportunity_v3(){}
    public Opportunity_v3(Parcel in)
    {
        opportunityLine =new ArrayList<OpportunityLine_v3>();

        oppid = in.readString();
        orgid = in.readString();
        smcreatorid = in.readString();
        farmerId = in.readString();
        setype = in.readString();
        opportunity_name = in.readString();
        leadSource = in.readString();
        description = in.readString();
        sales_stage = in.readString();
        rfqNo = in.readString();
        categoryName = in.readString();
        taxationPref = in.readParcelable(TaxationPreference_v3.class.getClassLoader());
        urgency = in.readParcelable(Urgency_v3.class.getClassLoader());
        freightArrangement = in.readString();
        shippingAddress = in.readString();
        deliveryAddress = in.readString();
        paymentMethodCode = in.readString();
        paymentTermsCode = in.readString();
        remarks = in.readString();
        requiredTimeFrame = in.readParcelable(RequiredTimeFrame_v3.class.getClassLoader());
        scmTerritoryCode = in.readString();
        in.readTypedList(opportunityLine, OpportunityLine_v3.CREATOR);
        formC = in.readString();
        leadSource = in.readString();
        paymentTermDays = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(oppid);
        dest.writeString(orgid);
        dest.writeString(smcreatorid);
        dest.writeString(farmerId);
        dest.writeString(setype);
        dest.writeString(opportunity_name);
        dest.writeString(leadSource);
        dest.writeString(description);
        dest.writeString(sales_stage);
        dest.writeString(rfqNo);
        dest.writeString(categoryName);
        dest.writeParcelable(taxationPref, flags);
        dest.writeParcelable(urgency, flags);
        dest.writeString(freightArrangement);
        dest.writeString(shippingAddress);
        dest.writeString(deliveryAddress);
        dest.writeString(paymentMethodCode);
        dest.writeString(paymentTermsCode);
        dest.writeString(remarks);
        dest.writeParcelable(requiredTimeFrame, flags);
        dest.writeString(scmTerritoryCode);
        dest.writeTypedList(opportunityLine);
        dest.writeString(formC);
        dest.writeString(leadSource);
        dest.writeString(paymentTermDays);
    }
    public static final Parcelable.Creator<Opportunity_v3> CREATOR = new Parcelable.Creator<Opportunity_v3>()
    {
        public Opportunity_v3 createFromParcel(Parcel in)
        {
            return new Opportunity_v3(in);
        }

        public Opportunity_v3[] newArray (int size)
        {
            return new Opportunity_v3[size];
        }
    };

    public String getPaymentTermDays() {
        return paymentTermDays;
    }

    public void setPaymentTermDays(String paymentTermDays) {
        this.paymentTermDays = paymentTermDays;
    }

    public String getOppid() {
        return oppid;
    }

    public void setOppid(String oppid) {
        this.oppid = oppid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getSmcreatorid() {
        return smcreatorid;
    }

    public void setSmcreatorid(String smcreatorid) {
        this.smcreatorid = smcreatorid;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getSetype() {
        return setype;
    }

    public void setSetype(String setype) {
        this.setype = setype;
    }

    public String getOpportunity_name() {
        return opportunity_name;
    }

    public void setOpportunity_name(String opportunity_name) {
        this.opportunity_name = opportunity_name;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSales_stage() {
        return sales_stage;
    }

    public void setSales_stage(String sales_stage) {
        this.sales_stage = sales_stage;
    }

    public String getRfqNo() {
        return rfqNo;
    }

    public void setRfqNo(String rfqNo) {
        this.rfqNo = rfqNo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public TaxationPreference_v3 getTaxationPref() {
        return taxationPref;
    }

    public void setTaxationPref(TaxationPreference_v3 taxationPref) {
        this.taxationPref = taxationPref;
    }

    public Urgency_v3 getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency_v3 urgency) {
        this.urgency = urgency;
    }

    public String getFreightArrangement() {
        return freightArrangement;
    }

    public void setFreightArrangement(String freightArrangement) {
        this.freightArrangement = freightArrangement;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getPaymentTermsCode() {
        return paymentTermsCode;
    }

    public void setPaymentTermsCode(String paymentTermsCode) {
        this.paymentTermsCode = paymentTermsCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public RequiredTimeFrame_v3 getRequiredTimeFrame() {
        return requiredTimeFrame;
    }

    public void setRequiredTimeFrame(RequiredTimeFrame_v3 requiredTimeFrame) {
        this.requiredTimeFrame = requiredTimeFrame;
    }

    public String getScmTerritoryCode() {
        return scmTerritoryCode;
    }

    public void setScmTerritoryCode(String scmTerritoryCode) {
        this.scmTerritoryCode = scmTerritoryCode;
    }

    public ArrayList<OpportunityLine_v3> getOpportunityLine() {
        return opportunityLine;
    }

    public void setOpportunityLine(ArrayList<OpportunityLine_v3> opportunityLine) {
        this.opportunityLine = opportunityLine;
    }

    public String getFormC() {
        return formC;
    }

    public void setFormC(String formC) {
        this.formC = formC;
    }
}
