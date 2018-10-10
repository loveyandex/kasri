package org.amin.jsons;

import lombok.Getter;
import lombok.Setter;

/**
 * is created by aMIN on 6/6/2018 at 20:17
 */
@Getter
@Setter
public class FormInfo {
    private Date Date;
    private String feaureName;
    private String StationNumber;
    private String StationName;
    private String Country;
    private String height;
    private Number lowerYear;
    private Number highYear;
    private String featureUnit;

    public FormInfo() {
    }

    public FormInfo(Date date, String feaureName, String stationNumber, String stationName, String country, String height,  Number lowerYear, Number highYear, String featureUnit) {
        Date = date;
        this.feaureName = feaureName;
        StationNumber = stationNumber;
        StationName = stationName;
        Country = country;
        this.height = height;
        this.lowerYear = lowerYear;
        this.highYear = highYear;
        this.featureUnit = featureUnit;
    }
}

