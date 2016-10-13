package com.power2sme.android.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.ActOnBehalfOf;

/**
 * Created by tausif on 28/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseActOnBehalfOfDto extends ResponseBaseDTO
{
    @JsonProperty("Data")
    private ActOnBehalfOf data;

    public ActOnBehalfOf getData() {
        return data;
    }

    public void setData(ActOnBehalfOf data) {
        this.data = data;
    }
}
