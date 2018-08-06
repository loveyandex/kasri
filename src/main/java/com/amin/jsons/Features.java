package com.amin.jsons;

/**
 * is created by aMIN on 7/17/2018 at 01:28
 */
public enum Features {
//   PRES(1),HGHT(2), TEMP(3), DWPT(4), RELH(5), MIXR(6), DRCT(7), SKNT(8), THTA(9), THTE(10), THTV(11);
// hPa;m;C;C;%;g/kg;deg;knot;K;K;K

    PRES(1, "PRES","hPa","0","1000"), HGHT(2, "HGHT","m","0","35000"), TEMP(3, "TEMP","C","-100","65"), DWPT(4, "DWPT","C","-110","65")
    , RELH(5, "RELH","%","0","100"), MIXR(6, "MIXR","g/kg","0","8.5"), DRCT(7, "DRCT","deg","0","360")
    , SKNT(8, "WIND_SPEED","knot","0","300"), THTA(9, "THTA","K","100","1000"), THTE(10, "THTE","K","100","1000"), THTV(11, "THTV","K","100","1000");


    private final int levelCode;
    private final String name;
    private final String unit;
    private final String low_range;
    private final String high_range;

    Features(int levelCode, String name,String unit,String LOW_RANGE,String HIGH_RANGE) {
        this.levelCode = levelCode;
        this.name = name;
        this.unit=unit;
        low_range = LOW_RANGE;
        high_range = HIGH_RANGE;
    }


    public final int getLevelCode() {
        return this.levelCode;
    }

    public final String getName() {
        return name;
    }

    public String getLow_range() {
        return low_range;
    }

    public String getHigh_range() {
        return high_range;
    }

    public String getUnit() {
        return unit;
    }
}
