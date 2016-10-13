package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by tausif on 13/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherProperties_v3 implements Parcelable
{
    public OtherProperties_v3(){}
    public OtherProperties_v3(Parcel in)
    {
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
    }
    public static final Parcelable.Creator<OtherProperties_v3> CREATOR = new Parcelable.Creator<OtherProperties_v3>()
    {
        public OtherProperties_v3 createFromParcel(Parcel in)
        {
            return new OtherProperties_v3(in);
        }

        public OtherProperties_v3[] newArray (int size)
        {
            return new OtherProperties_v3[size];
        }
    };


}
