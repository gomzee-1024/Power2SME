package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.NewRFQ_v3;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAddNewRFQDto extends ResponseBaseDTO
{
    @JsonProperty("Data")
    private NewRFQ_v3 data;

    public NewRFQ_v3 getData() {
        return data;
    }

    public void setData(NewRFQ_v3 data) {
        this.data = data;
    }
}
