package com.amin.jsons;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

/**
 * is created by aMIN on 6/6/2018 at 20:17
 */
@Getter
@Setter
public class WindInfo {
    public Date Date;
    public String StationNumber;
    public String StationName;
    public String Country;

    public WindInfo() {
    }

    public WindInfo(Date date, String stationNumber, String country) {
        Date = date;
        StationNumber = stationNumber;
        Country = country;
    }

    public static void main(String[] args) {
        WindInfo windInfo = new WindInfo(new Date(2,23,2033),"48644","iran");
        Gson gson = new Gson();
        String s = gson.toJson(windInfo);
        System.out.println(s);
        WindInfo windInfo1 = gson.fromJson(s, WindInfo.class);
        System.out.println(windInfo.Country);


    }



}

