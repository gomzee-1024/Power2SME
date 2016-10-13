package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

/**
 * Created by tausif on 19/6/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SKUOtherProperties
{
    @JsonProperty("ItemName")
    private String Thickness;

    public String getThickness() {
        return Utils.checkStringForNull(Thickness);
    }
}
