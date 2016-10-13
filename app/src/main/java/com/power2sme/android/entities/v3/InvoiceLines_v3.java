package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 14/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceLines_v3 implements Parcelable
{
    @JsonProperty("qty")
    private String qty;
    @JsonProperty("unitPrice")
    private String unitPrice;
    @JsonProperty("amtIncVat")
    private String amtIncVat;
    @JsonProperty("amtIncExcise")
    private String amtIncExcise;
    @JsonProperty("amtIncTax")
    private String amtIncTax;
    @JsonProperty("amtToCust")
    private String amtToCust;

    public InvoiceLines_v3(){}
    public InvoiceLines_v3(Parcel in)
    {
        qty = in.readString();
        unitPrice = in.readString();
        amtIncVat = in.readString();
        amtIncExcise = in.readString();
        amtIncTax = in.readString();
        amtToCust = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(qty);
        dest.writeString(unitPrice);
        dest.writeString(amtIncVat);
        dest.writeString(amtIncExcise);
        dest.writeString(amtIncTax);
        dest.writeString(amtToCust);
    }
    public static final Creator<InvoiceLines_v3> CREATOR = new Creator<InvoiceLines_v3>()
    {
        public InvoiceLines_v3 createFromParcel(Parcel in)
        {
            return new InvoiceLines_v3(in);
        }

        public InvoiceLines_v3[] newArray (int size)
        {
            return new InvoiceLines_v3[size];
        }
    };
}
