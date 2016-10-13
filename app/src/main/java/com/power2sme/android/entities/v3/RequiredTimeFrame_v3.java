package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 13/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequiredTimeFrame_v3 implements Parcelable
{
    @JsonProperty("Key")
    private String Key;
    @JsonProperty("Value")
    private String Value;

    public RequiredTimeFrame_v3(){}
    public RequiredTimeFrame_v3(Parcel in)
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
    public static final Parcelable.Creator<RequiredTimeFrame_v3> CREATOR = new Parcelable.Creator<RequiredTimeFrame_v3>()
    {
        public RequiredTimeFrame_v3 createFromParcel(Parcel in)
        {
            return new RequiredTimeFrame_v3(in);
        }

        public RequiredTimeFrame_v3[] newArray (int size)
        {
            return new RequiredTimeFrame_v3[size];
        }
    };

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
