package com.amin.jsons;

import lombok.Getter;
import lombok.Setter;

/**
 * is created by aMIN on 6/6/2018 at 20:17
 */
@Getter
@Setter
public class FormInfo2 {
    private int month;
    private int day;
    private int year;
    private String feaureName;
    private String StationNumber;
    private String StationName;
    private String Country;
    private String height;
    private Number lowerYear;
    private Number highYear;
    private String featureUnit;

    public FormInfo2() {
    }

    public FormInfo2(int month, int day, int year, String feaureName, String stationNumber, String stationName, String country, String height, Number lowerYear, Number highYear, String featureUnit) {
        this.month=month;
        this.year=year;
        this.day = day;
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

