package com.power2sme.android.entities.tracking;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipmentTime implements Parcelable
{
    @JsonProperty("date")
    private String date;
    @JsonProperty("timezone_type")
    private String timezone_type;
    @JsonProperty("timezone")
    private String timezone;

    public ShipmentTime(){}
    public ShipmentTime(Parcel in)
    {
        date = in.readString();
        timezone_type = in.readString();
        timezone = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(date);
        dest.writeString(timezone_type);
        dest.writeString(timezone);
    }

    public static final Creator<ShipmentTime> CREATOR = new Creator<ShipmentTime>()
    {
        public ShipmentTime createFromParcel(Parcel in)
        {
            return new ShipmentTime(in);
        }

        public ShipmentTime[] newArray (int size)
        {
            return new ShipmentTime[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getTimezone_type() {
        return timezone_type;
    }

    public String getTimezone() {
        return timezone;
    }
}
