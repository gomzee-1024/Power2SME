package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by tausif on 14/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wishlist_v3 implements Parcelable
{
    @JsonProperty("approvelevel")
    private String approvelevel;
    @JsonProperty("billingType")
    private String billingType;
    @JsonProperty("contactId")
    private String contactId;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("dispatchFromCode")
    private String dispatchFromCode;
    @JsonProperty("dtDate")
    private DispatchDate_v3 dtDate;
    @JsonProperty("farmerId")
    private String farmerId;
    @JsonProperty("firstReportingHead")
    private String firstReportingHead;
    @JsonProperty("formC")
    private String formC;
    @JsonProperty("fourthReportingHead")
    private String fourthReportingHead;
    @JsonProperty("hunterId")
    private String hunterId;
    @JsonProperty("paymentMethodCode")
    private String paymentMethodCode;
    @JsonProperty("paymentTermsCode")
    private String paymentTermsCode;
    @JsonProperty("remarks")
    private String remarks;
    @JsonProperty("requiredTimeFrame")
    private String requiredTimeFrame;
    @JsonProperty("rfqLineList")
    private ArrayList<RFQLineItem_v3> rfqLineList;
    @JsonProperty("rfqno")
    private String rfqno;
    @JsonProperty("scmTerritoryCode")
    private String scmTerritoryCode;
    @JsonProperty("secondReportingHead")
    private String secondReportingHead;
    @JsonProperty("shippingAddress")
    private String shippingAddress;
    @JsonProperty("shippingAddressCode")
    private String shippingAddressCode;
    @JsonProperty("smeId")
    private String smeId;
    @JsonProperty("source")
    private String source;
    @JsonProperty("sourceObjectId")
    private String sourceObjectId;
    @JsonProperty("taxationPref")
    private String taxationPref;
    @JsonProperty("thirdReportingHead")
    private String thirdReportingHead;
    @JsonProperty("urgency")
    private String urgency;
    @JsonProperty("vendorManager")
    private String vendorManager;
    @JsonProperty("vfirstReportingHead")
    private String vfirstReportingHead;
    @JsonProperty("vfourthReportingHead")
    private String vfourthReportingHead;
    @JsonProperty("vsecondReportingHead")
    private String vsecondReportingHead;
    @JsonProperty("vthirdReportingHead")
    private String vthirdReportingHead;

    @JsonProperty("totalPrice")
    private String grandTotal;
    @JsonProperty("createdDate")
    private String rfqDate;
    @JsonProperty("statusLabel")
    private String rfqStatus;
    @JsonProperty("statusCode")
    private String rfqCode;
    @JsonProperty("taxationPreference")
    private TaxationPreference_v3 taxationPreference;




    public Wishlist_v3(){}
    public Wishlist_v3(Parcel in)
    {
        rfqLineList = new ArrayList<RFQLineItem_v3>();

        approvelevel = in.readString();
        billingType = in.readString();
        contactId = in.readString();
        createdBy = in.readString();
        customerName = in.readString();
        dispatchFromCode = in.readString();
        dtDate = in.readParcelable(DispatchDate_v3.class.getClassLoader());
        farmerId = in.readString();
        firstReportingHead = in.readString();
        formC = in.readString();
        fourthReportingHead = in.readString();
        hunterId = in.readString();
        paymentMethodCode = in.readString();
        paymentTermsCode = in.readString();
        remarks = in.readString();
        requiredTimeFrame = in.readString();
        in.readTypedList(rfqLineList, RFQLineItem_v3.CREATOR);
        rfqno = in.readString();
        scmTerritoryCode = in.readString();
        secondReportingHead = in.readString();
        shippingAddress = in.readString();
        shippingAddressCode = in.readString();
        smeId = in.readString();
        String source = in.readString();
        sourceObjectId = in.readString();
        taxationPref = in.readString();
        thirdReportingHead = in.readString();
        urgency = in.readString();
        vendorManager = in.readString();
        vfirstReportingHead = in.readString();
        vfourthReportingHead = in.readString();
        vsecondReportingHead = in.readString();
        vthirdReportingHead = in.readString();
        grandTotal = in.readString();
        rfqDate = in.readString();
        rfqStatus = in.readString();
        rfqCode = in.readString();
        taxationPreference = in.readParcelable(TaxationPreference_v3.class.getClassLoader());
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(approvelevel);
        dest.writeString(billingType);
        dest.writeString(contactId);
        dest.writeString(createdBy);
        dest.writeString(customerName);
        dest.writeString(dispatchFromCode);
        dest.writeParcelable(dtDate, flags);
        dest.writeString(farmerId);
        dest.writeString(firstReportingHead);
        dest.writeString(formC);
        dest.writeString(fourthReportingHead);
        dest.writeString(hunterId);
        dest.writeString(paymentMethodCode);
        dest.writeString(paymentTermsCode);
        dest.writeString(remarks);
        dest.writeString(requiredTimeFrame);
        dest.writeTypedList(rfqLineList);
        dest.writeString(rfqno);
        dest.writeString(scmTerritoryCode);
        dest.writeString(secondReportingHead);
        dest.writeString(shippingAddress);
        dest.writeString(shippingAddressCode);
        dest.writeString(smeId);
        dest.writeString(source);
        dest.writeString(sourceObjectId);
        dest.writeString(taxationPref);
        dest.writeString(thirdReportingHead);
        dest.writeString(urgency);
        dest.writeString(vendorManager);
        dest.writeString(vfirstReportingHead);
        dest.writeString(vfourthReportingHead);
        dest.writeString(vsecondReportingHead);
        dest.writeString(vthirdReportingHead);
        dest.writeString(grandTotal);
        dest.writeString(rfqDate);
        dest.writeString(rfqStatus);
        dest.writeString(rfqCode);
        dest.writeParcelable(taxationPreference, flags);
    }
    public static final Creator<Wishlist_v3> CREATOR = new Creator<Wishlist_v3>()
    {
        public Wishlist_v3 createFromParcel(Parcel in)
        {
            return new Wishlist_v3(in);
        }

        public Wishlist_v3[] newArray (int size)
        {
            return new Wishlist_v3[size];
        }
    };

    public TaxationPreference_v3 getTaxationPreference() {
        return taxationPreference;
    }

    public void setTaxationPreference(TaxationPreference_v3 taxationPreference) {
        this.taxationPreference = taxationPreference;
    }

    public String getApprovelevel() {
        return approvelevel;
    }

    public void setApprovelevel(String approvelevel) {
        this.approvelevel = approvelevel;
    }

    public String getBillingType() {
        return billingType;
    }

    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDispatchFromCode() {
        return dispatchFromCode;
    }

    public void setDispatchFromCode(String dispatchFromCode) {
        this.dispatchFromCode = dispatchFromCode;
    }

    public DispatchDate_v3 getDtDate() {
        return dtDate;
    }

    public void setDtDate(DispatchDate_v3 dtDate) {
        this.dtDate = dtDate;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getFirstReportingHead() {
        return firstReportingHead;
    }

    public void setFirstReportingHead(String firstReportingHead) {
        this.firstReportingHead = firstReportingHead;
    }

    public String getFormC() {
        return formC;
    }

    public void setFormC(String formC) {
        this.formC = formC;
    }

    public String getFourthReportingHead() {
        return fourthReportingHead;
    }

    public void setFourthReportingHead(String fourthReportingHead) {
        this.fourthReportingHead = fourthReportingHead;
    }

    public String getHunterId() {
        return hunterId;
    }

    public void setHunterId(String hunterId) {
        this.hunterId = hunterId;
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

    public String getRequiredTimeFrame() {
        return requiredTimeFrame;
    }

    public void setRequiredTimeFrame(String requiredTimeFrame) {
        this.requiredTimeFrame = requiredTimeFrame;
    }

    public ArrayList<RFQLineItem_v3> getRfqLineList() {
        return rfqLineList;
    }

    public void setRfqLineList(ArrayList<RFQLineItem_v3> rfqLineList) {
        this.rfqLineList = rfqLineList;
    }

    public String getRfqno() {
        return rfqno;
    }

    public void setRfqno(String rfqno) {
        this.rfqno = rfqno;
    }

    public String getScmTerritoryCode() {
        return scmTerritoryCode;
    }

    public void setScmTerritoryCode(String scmTerritoryCode) {
        this.scmTerritoryCode = scmTerritoryCode;
    }

    public String getSecondReportingHead() {
        return secondReportingHead;
    }

    public void setSecondReportingHead(String secondReportingHead) {
        this.secondReportingHead = secondReportingHead;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddressCode() {
        return shippingAddressCode;
    }

    public void setShippingAddressCode(String shippingAddressCode) {
        this.shippingAddressCode = shippingAddressCode;
    }

    public String getSmeId() {
        return smeId;
    }

    public void setSmeId(String smeId) {
        this.smeId = smeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceObjectId() {
        return sourceObjectId;
    }

    public void setSourceObjectId(String sourceObjectId) {
        this.sourceObjectId = sourceObjectId;
    }

    public String getTaxationPref() {
        return taxationPref;
    }

    public void setTaxationPref(String taxationPref) {
        this.taxationPref = taxationPref;
    }

    public String getThirdReportingHead() {
        return thirdReportingHead;
    }

    public void setThirdReportingHead(String thirdReportingHead) {
        this.thirdReportingHead = thirdReportingHead;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getVendorManager() {
        return vendorManager;
    }

    public void setVendorManager(String vendorManager) {
        this.vendorManager = vendorManager;
    }

    public String getVfirstReportingHead() {
        return vfirstReportingHead;
    }

    public void setVfirstReportingHead(String vfirstReportingHead) {
        this.vfirstReportingHead = vfirstReportingHead;
    }

    public String getVfourthReportingHead() {
        return vfourthReportingHead;
    }

    public void setVfourthReportingHead(String vfourthReportingHead) {
        this.vfourthReportingHead = vfourthReportingHead;
    }

    public String getVsecondReportingHead() {
        return vsecondReportingHead;
    }

    public void setVsecondReportingHead(String vsecondReportingHead) {
        this.vsecondReportingHead = vsecondReportingHead;
    }

    public String getVthirdReportingHead() {
        return vthirdReportingHead;
    }

    public void setVthirdReportingHead(String vthirdReportingHead) {
        this.vthirdReportingHead = vthirdReportingHead;
    }

    public String getGrandTotal() {
        return grandTotal!=null?grandTotal:"0.0";
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getRfqDate() {
        return rfqDate;
    }

    public void setRfqDate(String rfqDate) {
        this.rfqDate = rfqDate;
    }

    public String getRfqStatus() {
        return rfqStatus;
    }

    public void setRfqStatus(String rfqStatus) {
        this.rfqStatus = rfqStatus;
    }

    public int getRfqCode()
    {
        try
        {
            if(rfqCode!=null && rfqCode.length()>0)
            {
                return Integer.parseInt(rfqCode);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }

    public void setRfqCode(String rfqCode) {
        this.rfqCode = rfqCode;
    }
}
