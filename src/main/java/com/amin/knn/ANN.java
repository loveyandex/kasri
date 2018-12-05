package com.amin.knn;

import com.amin.pojos.LatLon;
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
        final ResultSet resultSet = exeing();
        ArrayList<Double> tempsArray = new ArrayList<>();
        ArrayList<LatLon> tempLAtlongs = new ArrayList<>();
        while (resultSet.next()) {
            final String stationnumber = resultSet.getString(1);
            final String country = resultSet.getString(2);
            final String stacitinametion = resultSet.getString(3);
            final String lati = resultSet.getString(4);
            final String longi = resultSet.getString(5);

            final double real_distance = real_distance(lat1, long1, Double.parseDouble(lati), Double.parseDouble(longi));
            if (real_distance < maximum_distance_km) {
                final double temp = temp(stationnumber, country);
                if (temp == -1000000)
                    continue;
                else {
                    tempsArray.add(temp);
                    tempLAtlongs.add(new LatLon(Double.parseDouble(lati), Double.parseDouble(longi)));
                }
            }
        }
        System.err.println(new Gson().toJson(tempLAtlongs));
        System.err.println(new Gson().toJson(tempsArray));
    }
}
