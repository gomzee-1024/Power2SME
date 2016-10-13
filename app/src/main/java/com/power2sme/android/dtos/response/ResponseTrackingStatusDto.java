package com.power2sme.android.dtos.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.tracking.Order;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTrackingStatusDto implements Parcelable
{
    @JsonProperty("error")
    private String error;
    @JsonProperty("error_message")
    private String error_message;
    @JsonProperty("sme_id")
    private String sme_id;
    @JsonProperty("orders")
    private List<Order> orders;

    public ResponseTrackingStatusDto(){}
    public ResponseTrackingStatusDto(Parcel in)
    {
        error = in.readString();
        error_message = in.readString();
        sme_id = in.readString();
        in.readTypedList(orders, Order.CREATOR);
    }
    @Override
    public int describeContents()
    {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(error);
        dest.writeString(error_message);
        dest.writeString(sme_id);
        dest.writeTypedList(orders);
    }
    public static final Creator<ResponseTrackingStatusDto> CREATOR = new Creator<ResponseTrackingStatusDto>()
    {
        public ResponseTrackingStatusDto createFromParcel(Parcel in)
        {
            return new ResponseTrackingStatusDto(in);
        }

        public ResponseTrackingStatusDto[] newArray (int size)
        {
            return new ResponseTrackingStatusDto[size];
        }
    };

    public String getError() {
        return error;
    }

    public String getError_message() {
        return error_message;
    }

    public String getSme_id() {
        return sme_id;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
