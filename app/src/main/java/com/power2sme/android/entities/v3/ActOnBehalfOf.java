package com.power2sme.android.entities.v3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tausif on 28/7/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActOnBehalfOf {
    @JsonProperty("object_type_id")
    private String object_type_id;
    @JsonProperty("organisation")
    private Organization_v3 organisation;

    public String getObject_type_id() {
        return object_type_id;
    }

    public void setObject_type_id(String object_type_id) {
        this.object_type_id = object_type_id;
    }

    public Organization_v3 getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organization_v3 organisation) {
        this.organisation = organisation;
    }
}
