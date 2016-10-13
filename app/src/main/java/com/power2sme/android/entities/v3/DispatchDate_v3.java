package com.power2sme.android.entities.v3;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 14/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatchDate_v3 implements Parcelable
{
    @JsonProperty("year")
    private String year;
    @JsonProperty("month")
    private String month;
    @JsonProperty("day")
    private String day;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("hour")
    private String hour;
    @JsonProperty("minute")
    private String minute;
    @JsonProperty("second")
    private String second;
    @JsonProperty("fractionalSecond")
    private String fractionalSecond;

    public DispatchDate_v3(){}
    public DispatchDate_v3(Parcel in)
    {
        year = in.readString();
        month = in.readString();
        day = in.readString();
        timezone = in.readString();
        hour = in.readString();
        minute = in.readString();
        second = in.readString();
        fractionalSecond = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(year);
        dest.writeString(month);
        dest.writeString(day);
        dest.writeString(timezone);
        dest.writeString(hour);
        dest.writeString(minute);
        dest.writeString(second);
        dest.writeString(fractionalSecond);
    }
    public static final Parcelable.Creator<DispatchDate_v3> CREATOR = new Parcelable.Creator<DispatchDate_v3>()
    {
        public DispatchDate_v3 createFromParcel(Parcel in)
        {
            return new DispatchDate_v3(in);
        }

        public DispatchDate_v3[] newArray (int size)
        {
            return new DispatchDate_v3[size];
        }
    };
}
