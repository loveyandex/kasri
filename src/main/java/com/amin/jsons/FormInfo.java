package com.amin.jsons;

import lombok.Getter;
import lombok.Setter;

/**
 * is created by aMIN on 6/6/2018 at 20:17
 */
@Getter
@Setter
public class FormInfo {
    public Date Date;
    private String feaureName;
    public String StationNumber;
    public String StationName;
    public String Country;
    private String height;
    private String lowHeightRange;
    private String highHeightRange;
    private Number lowerYear;
    private Number highYear;
    private String featureUnit;

    public String getFeatureUnit() {
        return featureUnit;
    }

    public void setFeatureUnit(String featureUnit) {
        this.featureUnit = featureUnit;
    }


    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    private Features features;

    public void setLowerYear(Number lowerYear) {
        this.lowerYear = lowerYear;
    }

    public Number getLowerYear() {
        return lowerYear;
    }

    public void setHighYear(Number highYear) {
        this.highYear = highYear;
    }

    public Number getHighYear() {
        return highYear;
    }


    public String getFeaureName() {
        return feaureName;
    }

    public void setFeaureName(String feaureName) {
        this.feaureName = feaureName;
    }
}

