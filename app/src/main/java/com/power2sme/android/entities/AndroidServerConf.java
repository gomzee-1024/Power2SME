package com.power2sme.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.power2sme.android.utilities.Utils;

/**
 * Created by tausif on 2/7/15.
 *
 * modified by satish shende on 9/9/15
 * use of implementation of Utils.checkfornull
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AndroidServerConf
{
    @JsonProperty("ServerPrefix")
    private String ServerPrefix;
    @JsonProperty("minversion")
    private String minversion;
    @JsonProperty("maxversion")
    private String maxversion;

    public String getMinversion() {
        return Utils.checkStringForNull(minversion);
    }

    public void setMinversion(String minversion) {
        this.minversion = minversion;
    }

    public String getMaxversion() {
        return Utils.checkStringForNull(maxversion);
    }

    public void setMaxversion(String maxversion) {
        this.maxversion = maxversion;
    }

    public String getServerPrefix() {
        return Utils.checkStringForNull(ServerPrefix);
    }

    public void setServerPrefix(String serverPrefix) {
        ServerPrefix = serverPrefix;
    }
}
