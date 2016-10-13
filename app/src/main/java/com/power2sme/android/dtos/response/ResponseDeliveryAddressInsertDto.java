package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.DeliveryAddress;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDeliveryAddressInsertDto extends ResponseBaseDTO
{
    @JsonProperty("Data")
    DeliveryAddress Data;

    public DeliveryAddress getData() {
        return Data;
    }

    public void setData(DeliveryAddress data) {
        Data = data;
    }
}
