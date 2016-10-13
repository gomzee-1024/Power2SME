package com.power2sme.android.entities.tracking;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by power2sme on 29/10/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimatedSchedule implements Parcelable
{
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("time")
    private String time;

    public EstimatedSchedule(){}
    public EstimatedSchedule(Parcel in)
    {
        distance = in.readString();
        time = in.readString();
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(distance);
        dest.writeString(time);
    }

    public static final Creator<EstimatedSchedule> CREATOR = new Creator<EstimatedSchedule>()
    {
        public EstimatedSchedule createFromParcel(Parcel in)
        {
            return new EstimatedSchedule(in);
        }

        public EstimatedSchedule[] newArray (int size)
        {
            return new EstimatedSchedule[size];
        }
    };

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }
}
