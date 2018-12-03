package com.amin.knn;

import com.amin.pojos.LatLon;
import com.amin.pojos.Station;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.amin.knn.KNN.*;

/**
 * is created by aMIN on 12/3/2018 at 4:16 AM
 */
public class ANN {

    public static void nearest(double maximum_distance_km, LatLon latLong) throws SQLException {
        final double lat1 = latLong.getLat();
        final double long1 = latLong.getLogn();

        Station station = null;
        ArrayList<Station> stations = new ArrayList<>();

        final ResultSet resultSet = exeing();
        double sum_distance = 0.0;
        double expection = 0.0;
        ArrayList<Double> tempsArray = new ArrayList<>();
        ArrayList<LatLon> tempLAtlongs = new ArrayList<>();
        while (resultSet.next()) {
            final String stationnumber = resultSet.getString(1);
            final String country = resultSet.getString(2);
            final String stacitinametion = resultSet.getString(3);
            final String lati = resultSet.getString(4);
            final String longi = resultSet.getString(5);
//            System.out.println(lat1);
//            System.out.print(long1);
            final double real_distance = real_distance(lat1, long1, Double.parseDouble(lati), Double.parseDouble(longi));
            if (real_distance < maximum_distance_km) {
//                station = new Station(stationnumber, country, stacitinametion,
//                        true, new LatLon(Double.parseDouble(lati), Double.parseDouble(longi))
//                        , real_distance);

                final double temp = temp(stationnumber, country);
                if (temp == -1000000)
                    continue;
                else {
                    tempsArray.add(temp);
                    tempLAtlongs.add(new LatLon(Double.parseDouble(lati), Double.parseDouble(longi)));
                    expection += real_distance * temp;
                    sum_distance += real_distance;
                    System.err.println(real_distance);
                    System.err.println(stacitinametion + "...." + lati + "  " + longi);
                }
            }
        }
        System.err.println(new Gson().toJson(tempLAtlongs));
        System.err.println(new Gson().toJson(tempsArray));
    }
}
