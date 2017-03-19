package com.ims.tasol.networkingexample.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasol on 18/3/17.
 */


public class GeonameResponse {

    @SerializedName("geonames")
    @Expose
    private List<GeonameModel> geonames = new ArrayList<GeonameModel>();
    public List<GeonameModel> getGeonames() {
        return geonames;
    }
    public void setGeonames(List<GeonameModel> geonames) {
        this.geonames = geonames;
    }
}