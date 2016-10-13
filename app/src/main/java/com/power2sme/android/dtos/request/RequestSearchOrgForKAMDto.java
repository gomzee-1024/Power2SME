package com.power2sme.android.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.entities.v3.RequestSearchOrgForKAMDtoOrganization;

/**
 * Created by tausif on 22/5/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestSearchOrgForKAMDto
{
    @JsonProperty("organisation")
    private RequestSearchOrgForKAMDtoOrganization organisation;

    public RequestSearchOrgForKAMDtoOrganization getOrganisation() {
        return organisation;
    }

    public void setOrganisation(RequestSearchOrgForKAMDtoOrganization organisation) {
        this.organisation = organisation;
    }
}
