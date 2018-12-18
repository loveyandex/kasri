package com.amin.jsons;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * is created by aMIN on 12/17/2018 at 11:21 PM
 */

@Getter
@Setter
@Accessors(chain = true)
public class SomeDays {
    private Date fromDate;
    private String feaureName;
    private String StationNumber;
    private String StationName;
    private String Country;
    private String height;
    private Number lowerYear;
    private Number highYear;
    private String featureUnit;
    private int minusDay;
    private int plusDay;

    public SomeDays() {
    }

    public SomeDays(Date fromDate, String feaureName, String stationNumber, String stationName, String country, String height, Number lowerYear, Number highYear, String featureUnit, int minusDay, int plusDay) {
        this.fromDate = fromDate;
        this.feaureName = feaureName;
        StationNumber = stationNumber;
        StationName = stationName;
        Country = country;
        this.height = height;
        this.lowerYear = lowerYear;
        this.highYear = highYear;
        this.featureUnit = featureUnit;
        this.minusDay = minusDay;
        this.plusDay = plusDay;
    }
}
