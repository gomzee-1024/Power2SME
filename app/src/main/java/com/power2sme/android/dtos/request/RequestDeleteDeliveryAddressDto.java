package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 27/5/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestDeleteDeliveryAddressDto
{
    @JsonProperty("code")
    private String code;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
