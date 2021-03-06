package com.amin.knnann;

import com.amin.database.database.DatabaseHandler;
import com.amin.pojos.LatLon;
import com.amin.pojos.Station;
import com.amin.ui.scripts.ScriptAPP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * is created by aMIN on 9/27/2018 at 12:27 AM
 */
public class KNN {
    private static final double R = 6371;

    public static void predict() {
    }


    public static double vector_distance(double tetha1, double phi1, double tetha2, double phi2) {
        final double cosphi1 = cos(toRadians(phi1));
        final double sphi1 = sin(toRadians(phi1));
        final double sphi2 = sin(toRadians(phi2));
        final double cosphi2 = cos(toRadians(phi2));


        double a1 = R * cosphi1 * cos(toRadians(tetha1));
        double a2 = R * cosphi1 * sin(toRadians(tetha1));
        double a3 = R * sphi1;

        double b1 = R * cosphi2 * cos(toRadians(tetha2));
        double b2 = R * cosphi2 * sin(toRadians(tetha2));
        double b3 = R * sphi2;

        return sqrt(((a1 - b1) * (a1 - b1) + (a2 - b2) * (a2 - b2) + (a3 - b3) * (a3 - b3)));

    }


    public static double real_distance(double lat1, double long1, double lat2, double long2) {
        final double cosphi1 = cos(toRadians(lat1));
        final double sphi1 = sin(toRadians(lat1));
        final double sphi2 = sin(toRadians(lat2));
        final double cosphi2 = cos(toRadians(lat2));


        double a1 = cosphi1 * cos(toRadians(long1));
        double a2 = cosphi1 * sin(toRadians(long1));
        double a3 = sphi1;

        double b1 = cosphi2 * cos(toRadians(long2));
        double b2 = cosphi2 * sin(toRadians(long2));
        double b3 = sphi2;

//        double gama = Math.acos((a1 * b1 + a2 * b2 + a3 * b3) / (R * R));
        double gama = Math.acos((a1 * b1 + a2 * b2 + a3 * b3));

        return R * gama;
    }


    public static void main(String[] args) throws SQLException {
        long start = System.nanoTime();
        ;
        final double lat1 = 31.883674;
        final double long1 = 54.368775;

        final double dle = Math.toDegrees(100 / (R * sin(Math.toRadians(90 - lat1))));
        System.out.println(dle);
        System.out.println(real_distance(lat1, long1, lat1, long1 + dle));
        System.out.println(real_distance(90, 0, -90, 0));
        final double delalf = 360 / 999d;
        double disradius = 100;
        final ResultSet resultSet = exeing();
        while (resultSet.next()) {
            final String station = resultSet.getString(1);
            final String country = resultSet.getString(2);
            final String stacitinametion = resultSet.getString(3);
            final String lati = resultSet.getString(4);
            final String longi = resultSet.getString(5);
//            System.out.println(lat1);
//            System.out.print(long1);
            final double real_distance = real_distance(lat1, long1, Double.parseDouble(lati), Double.parseDouble(longi));
            if (real_distance < 10) {
                System.err.println(real_distance);
                System.out.println(stacitinametion + "...." + lati + "  " + longi);
            }
        }
        long time = System.nanoTime() - start;
        System.out.printf("Each XXXXX took an average of %f ms%n", time / 1e6d);
    }


    public static ResultSet exeing() throws SQLException {
        final Statement statement = DatabaseHandler.getInstance().getConnection().createStatement();

        final ResultSet executeQuery = statement.executeQuery("select *\n" +
                "from stations\n" +
                "where station!='NULL'");
        return executeQuery;
    }

    public static ResultSet exeing(String country) throws SQLException {
        final Statement statement = DatabaseHandler.getInstance().getConnection().createStatement();

        final ResultSet executeQuery = statement.executeQuery("select *\n" +
                "from stations\n" +
//                "where station!='NULL'");
                "where station!='NULL' and country like '" + country + "%'");
        return executeQuery;
    }


    public static double nearst(double maximum_distance_km, LatLon latLong) throws SQLException {
        final double lat1 = latLong.getLat();
        final double long1 = latLong.getLogn();

        Station station = null;
        ArrayList<Station> stations = new ArrayList<>();

        final ResultSet resultSet = exeing();
        double sum_distance = 0.0;
        double expection = 0.0;
        while (resultSet.next()) {
            final String stationnumber = resultSet.getString(1);
            final String country = resultSet.getString(2);
            final String stacitinametion = resultSet.getString(3);
            final String lati = resultSet.getString(4);
            final String longi = resultSet.getString(5);
//            System.out.println(lat1);
//            System.out.print(long1);
            final double real_distance1 = real_distance(lat1, long1, Double.parseDouble(lati), Double.parseDouble(longi));
            if (real_distance1 < maximum_distance_km) {
//                station = new Station(stationnumber, country, stacitinametion,
//                        true, new LatLon(Double.parseDouble(lati), Double.parseDouble(longi))
//                        , real_distance1);

                final double temp = calcFeatureValue(stationnumber, country);
                if (temp == -1000000)
                    continue;
                else {
                    expection += real_distance1 * temp;
                    sum_distance += real_distance1;

                    System.err.println(real_distance1);
                    System.err.println(stacitinametion + "...." + lati + "  " + longi);
                }
            }
        }

        if (sum_distance != 0.0)
            return expection / sum_distance;
        else
            return -3000000000000000.0;

    }



    public static double nearst(double maximum_distance_km, LatLon latLong,String dayOfmonth,String monthofyear) throws SQLException {
        final double lat1 = latLong.getLat();
        final double long1 = latLong.getLogn();

        Station station = null;
        ArrayList<Station> stations = new ArrayList<>();

        final ResultSet resultSet = exeing();
        double sum_distance = 0.0;
        double expection = 0.0;
        while (resultSet.next()) {
            final String stationnumber = resultSet.getString(1);
            final String country = resultSet.getString(2);
            final String stacitinametion = resultSet.getString(3);
            final String lati = resultSet.getString(4);
            final String longi = resultSet.getString(5);
//            System.out.println(lat1);
//            System.out.print(long1);
            final double real_distance1 = real_distance(lat1, long1, Double.parseDouble(lati), Double.parseDouble(longi));
            if (real_distance1 < maximum_distance_km) {
//                station = new Station(stationnumber, country, stacitinametion,
//                        true, new LatLon(Double.parseDouble(lati), Double.parseDouble(longi))
//                        , real_distance1);

                final double temp = calcFeatureValue(stationnumber, country, dayOfmonth, monthofyear);
                if (temp == -1000000)
                    continue;
                else {
                    expection += real_distance1 * temp;
                    sum_distance += real_distance1;

                    System.err.println(real_distance1);
                    System.err.println(stacitinametion + "...." + lati + "  " + longi);
                }
            }
        }



        if (sum_distance != 0.0)
            return expection / sum_distance;
        else
            return -3000000000000000.0;

    }







    public static double nearst(double maximum_distance_km, LatLon latLong, Do aDo) throws SQLException {

        final double lat1 = latLong.getLat();
        final double long1 = latLong.getLogn();

        Station station = null;
        ArrayList<Station> stations = new ArrayList<>();

        final ResultSet resultSet = exeing();
        double sum_distance = 0.0;
        double expection = 0.0;
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
                station = new Station(stationnumber, country, stacitinametion,
                        true, new LatLon(Double.parseDouble(lati), Double.parseDouble(longi))
                        , real_distance);
                final double temp = aDo.run(stationnumber, country);
                if (temp == -1000000)
                    continue;
                else {
                    expection += real_distance * temp;
                    sum_distance += real_distance;
                    stations.add(station);
                    System.err.println(real_distance);
                    System.err.println(stacitinametion + "...." + lati + "  " + longi);
                }


            }

        }

        if (sum_distance != 0.0)
            return expection / sum_distance;
        else
            return -3000000000000000.0;

    }

    public static double calcFeatureValue(String stationnumber, String country) {
        final String function = String.format("onday %s 10 26 WIND_SPEED m/s 12000 1973 2017 %s", stationnumber, country);
        System.out.println(function);
        final double v = ScriptAPP.scripting2(function);
        return v;
    }
    public static double calcFeatureValue(String stationnumber, String country,String dayofmonth,String monthofyear) {
        final String function = String.format("onday %s %s %s WIND_SPEED m/s 12000 1973 2017 %s",
                stationnumber,monthofyear,dayofmonth, country);
        System.out.println(function);
        final double v = ScriptAPP.scripting2(function);
        return v;
    }

    public static double calcFeatureValue(String functionformat, String stationnumber, String country) {

        String function = String.format(functionformat, stationnumber, country);
        if (function.contains("PERCENT"))
            function = function.replaceAll("PERCENT", "%");

        final double v = ScriptAPP.scripting2(function);
        return v;
    }


}