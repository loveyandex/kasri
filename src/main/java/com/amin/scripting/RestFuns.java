package com.amin.scripting;

import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;
import com.amin.jsons.SomeDays;

/**
 * is created by aMIN on 10/14/2018 at 11:13 PM
 */
public class RestFuns {

    public static FormInfo toFormInfo(String[] args) {

        final String stationNumber = args[1];
        final int month = Integer.parseInt(args[2]);
        final int day = Integer.parseInt(args[3]);
        final String featurename = args[4];
        final String unit = args[5];
        final String height = args[6];
        int loweryear = Integer.parseInt(args[7]);
        int highyear = Integer.parseInt(args[8]);
        final String country = args[9];

        final FormInfo formInfo = new FormInfo(new Date(month, day, loweryear), featurename,
                stationNumber, "",
                country, height, loweryear, highyear, unit);
        return formInfo;
    }

    public static SomeDays toSomeDaysObject(String[] args) {

        final String stationNumber = args[1];
        final int month = Integer.parseInt(args[2]);
        final int day = Integer.parseInt(args[3]);
        int minusDay = Integer.parseInt(args[4]);
        int plusDay = Integer.parseInt(args[5]);
        final String featurename = args[6];
        final String unit = args[7];
        final String height = args[8];
        int loweryear = Integer.parseInt(args[9]);
        int highyear = Integer.parseInt(args[10]);
        final String country = args[11];


        final SomeDays someDays = new SomeDays(new Date(month, day, loweryear), featurename,
                stationNumber, "",
                country, height, loweryear, highyear, unit, minusDay, plusDay);
        return someDays;
    }

    public static FormInfo toFormInfo(String stationNumber, int month, int day,
                                      String featurename, String unit, String height,
                                      int loweryear, int highyear, String country) {
        return new FormInfo(new Date(month, day, 1999), featurename,
                stationNumber, "",
                country, height, loweryear, highyear, unit);
    }
}
