package com.power2sme.android.dtos.response;

import java.util.HashMap;
import java.util.List;

public class ResponseGeoPointsDto extends ResponseBaseDTO
{
    private List<List<HashMap<String,String>>> geoPoints;

    public List<List<HashMap<String, String>>> getGeoPoints() {
        return geoPoints;
    }

    public void setGeoPoints(List<List<HashMap<String, String>>> geoPoints) {
        this.geoPoints = geoPoints;
    }
}