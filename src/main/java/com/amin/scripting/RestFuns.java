package com.amin.scripting;

import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;

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

        final FormInfo formInfo = new FormInfo(new Date(month, day, 1999), featurename,
                stationNumber, "",
                country, height, loweryear, highyear, unit);
        return formInfo;
    }

    public static FormInfo toFormInfo(String stationNumber, int month, int day,
                                      String featurename, String unit, String height,
                                      int loweryear, int highyear, String country) {
        return new FormInfo(new Date(month, day, 1999), featurename,
                stationNumber, "",
                country, height, loweryear, highyear, unit);
    }
}
