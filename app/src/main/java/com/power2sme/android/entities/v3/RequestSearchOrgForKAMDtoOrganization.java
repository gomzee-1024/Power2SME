package com.power2sme.android.entities.v3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 28/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestSearchOrgForKAMDtoOrganization
{
    @JsonProperty("smeId")
    private String smeId;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("company_name")
    private String company_name;
    @JsonProperty("lastTransacted")
    private String lastTransacted;


    public String getSmeId() {
        return smeId;
    }

    public void setSmeId(String smeId) {
        this.smeId = smeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getLastTransacted() {
        return lastTransacted;
    }

    public void setLastTransacted(String lastTransacted) {
        this.lastTransacted = lastTransacted;
    }
}
