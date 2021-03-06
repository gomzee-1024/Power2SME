package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by tausif on 13/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxationPreference_v3 implements Parcelable,Serializable
{
    @JsonProperty("Key")
    private String Key;
    @JsonProperty("Value")
    private String Value;

    public TaxationPreference_v3(){}
    public TaxationPreference_v3(Parcel in)
    {
        Key = in.readString();
        Value = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(Key);
        dest.writeString(Value);
    }
    public static final Parcelable.Creator<TaxationPreference_v3> CREATOR = new Parcelable.Creator<TaxationPreference_v3>()
    {
        public TaxationPreference_v3 createFromParcel(Parcel in)
        {
            return new TaxationPreference_v3(in);
        }

        public TaxationPreference_v3[] newArray (int size)
        {
            return new TaxationPreference_v3[size];
        }
    };

    @Override
    public String toString()
    {
        return getValue();
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof TaxationPreference_v3 && ((TaxationPreference_v3)o).toString().equals(this.toString()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /////////////////////////////////////////////////////////////////
    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
