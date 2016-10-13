package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

/**
 * Created by tausif on 3/5/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RfqNo
{
    @JsonProperty("rfqNo")
    private String rfqNo;

    public String getRfqNo() {
        return Utils.checkStringForNull(rfqNo);
    }

    public void setRfqNo(String rfqNo) {
        this.rfqNo = rfqNo;
    }
}
