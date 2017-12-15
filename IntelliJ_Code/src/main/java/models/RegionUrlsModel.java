package com.farm.hub.models;

import java.util.List;

/**
 * Model class to hold the region code, its weather param and its param URLs. This will be initiated by Parser and
 * used by Writer to write data to CSV file.
 */
public class RegionUrlsModel {
    String mRegionCode;
    List<String> mWeatherParamUrl;

    public RegionUrlsModel() {
    }

    public RegionUrlsModel(String regionCode, List<String> weatherParamUrl) {
        this.mRegionCode = regionCode;
        this.mWeatherParamUrl = weatherParamUrl;
    }

    public String getRegionCode() {
        return mRegionCode;
    }

    public void setRegionCode(String regionCode) {
        this.mRegionCode = regionCode;
    }

    public List<String> getWeatherParamUrl() {
        return mWeatherParamUrl;
    }

    public void setWeatherParamUrl(List<String> weatherParamUrl) {
        this.mWeatherParamUrl = weatherParamUrl;
    }
}
