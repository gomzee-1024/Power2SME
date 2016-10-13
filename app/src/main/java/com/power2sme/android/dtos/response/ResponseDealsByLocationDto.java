package com.power2sme.android.dtos.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.tracking.Order;
import com.power2sme.android.entities.v3.Deal_v3;

import java.util.List;

/**
 * Created by shubhamkansal on 9/19/16.
 */

public class ResponseDealsByLocationDto  extends ResponseBaseDTO
{
    @JsonProperty("Data")
    private List<Deal_v3> Data;

    public List<Deal_v3> getData() {
        return Data;
    }

    public void setData(List<Deal_v3> data) {
        Data = data;
    }
}
