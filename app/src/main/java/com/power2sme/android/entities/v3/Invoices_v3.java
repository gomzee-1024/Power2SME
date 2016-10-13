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
public class Invoices_v3 implements Parcelable
{
    @JsonProperty("no")
    private String no;
    @JsonProperty("sellToCustNo")
    private String sellToCustNo;
    @JsonProperty("sellToAddress")
    private String sellToAddress;
    @JsonProperty("sellToPostCode")
    private String sellToPostCode;
    @JsonProperty("billToCustNo")
    private String billToCustNo;
    @JsonProperty("billToAddress")
    private String billToAddress;
    @JsonProperty("billToPostCode")
    private String billToPostCode;
    @JsonProperty("billingType")
    private String billingType;
    @JsonProperty("farmerId")
    private String farmerId;
    @JsonProperty("shipToName")
    private String shipToName;
    @JsonProperty("shipToAddress")
    private String shipToAddress;
    @JsonProperty("shipToContact")
    private String shipToContact;
    @JsonProperty("orderDate")
    private String orderDate;
    @JsonProperty("postingDate")
    private String postingDate;
    @JsonProperty("postingDesc")
    private String postingDesc;
    @JsonProperty("paymentMethodCode")
    private String paymentMethodCode;
    @JsonProperty("paymentTermsCode")
    private String paymentTermsCode;
    @JsonProperty("noSeries")
    private String noSeries;
    @JsonProperty("preAssignedNo")
    private String preAssignedNo;
    @JsonProperty("orderNo")
    private String orderNo;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("shipmentDone")
    private String shipmentDone;
    @JsonProperty("shipmentDate")
    private String shipmentDate;
    @JsonProperty("taxPreference")
    private String taxPreference;
    @JsonProperty("taxAmount")
    private String taxAmount;
    @JsonProperty("dueDate")
    private String dueDate;
    @JsonProperty("customerPOProductName")
    private String customerPOProductName;
    @JsonProperty("creditPeriod")
    private String creditPeriod;
    @JsonProperty("invoiceLines")
    private ArrayList<InvoiceLines_v3> invoiceLines;

    public Invoices_v3(){}
    public Invoices_v3(Parcel in)
    {
        invoiceLines =new ArrayList<InvoiceLines_v3>();

        no = in.readString();
        sellToCustNo = in.readString();
        sellToAddress = in.readString();
        sellToPostCode = in.readString();
        billToCustNo = in.readString();
        billToAddress = in.readString();
        billToPostCode = in.readString();
        billingType = in.readString();
        farmerId = in.readString();
        shipToName = in.readString();
        shipToAddress = in.readString();
        shipToContact = in.readString();
        orderDate = in.readString();
        postingDate = in.readString();
        postingDesc = in.readString();
        paymentMethodCode = in.readString();
        paymentTermsCode = in.readString();
        noSeries = in.readString();
        preAssignedNo = in.readString();
        orderNo = in.readString();
        deviceId = in.readString();
        shipmentDone = in.readString();
        shipmentDate = in.readString();
        taxPreference = in.readString();
        taxAmount = in.readString();
        dueDate = in.readString();
        customerPOProductName = in.readString();
        creditPeriod = in.readString();
        in.readTypedList(invoiceLines, InvoiceLines_v3.CREATOR);
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(no);
        dest.writeString(sellToCustNo);
        dest.writeString(sellToAddress);
        dest.writeString(sellToPostCode);
        dest.writeString(billToCustNo);
        dest.writeString(billToAddress);
        dest.writeString(billToPostCode);
        dest.writeString(billingType);
        dest.writeString(farmerId);
        dest.writeString(shipToName);
        dest.writeString(shipToAddress);
        dest.writeString(shipToContact);
        dest.writeString(orderDate);
        dest.writeString(postingDate);
        dest.writeString(postingDesc);
        dest.writeString(paymentMethodCode);
        dest.writeString(paymentTermsCode);
        dest.writeString(noSeries);
        dest.writeString(preAssignedNo);
        dest.writeString(orderNo);
        dest.writeString(deviceId);
        dest.writeString(shipmentDone);
        dest.writeString(shipmentDate);
        dest.writeString(taxPreference);
        dest.writeString(taxAmount);
        dest.writeString(dueDate);
        dest.writeString(customerPOProductName);
        dest.writeString(creditPeriod);
        dest.writeTypedList(invoiceLines);
    }
    public static final Creator<Invoices_v3> CREATOR = new Creator<Invoices_v3>()
    {
        public Invoices_v3 createFromParcel(Parcel in)
        {
            return new Invoices_v3(in);
        }

        public Invoices_v3[] newArray (int size)
        {
            return new Invoices_v3[size];
        }
    };
}
