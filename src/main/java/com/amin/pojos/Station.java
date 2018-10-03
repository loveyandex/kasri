package com.amin.pojos;

import lombok.Getter;
import lombok.Setter;

/**
 * is created by aMIN on 10/1/2018 at 3:08 AM
 */
@Setter
@Getter
public class Station {
    private String stationNumber;
    private String country;
    private String cityName;
    private boolean haveEverData;
    private LatLon latLong;
    private double distance;

    public Station(String stationNumber, String country, String cityName, boolean haveEverData, LatLon latLong,double distance) {
        this.stationNumber = stationNumber;
        this.country = country;
        this.cityName = cityName;
        this.haveEverData = haveEverData;
        this.latLong = latLong;
        this.distance = distance;
    }
}
