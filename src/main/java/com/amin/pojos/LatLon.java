package com.amin.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * is created by aMIN on 10/1/2018 at 3:03 AM
 */
@Getter
@Setter
@Accessors(chain = true)
public class LatLon {
    private double lat;
    private double logn;

    public LatLon() {
    }

    public LatLon(double lat, double logn) {
        this.lat = lat;
        this.logn = logn;
    }
}
