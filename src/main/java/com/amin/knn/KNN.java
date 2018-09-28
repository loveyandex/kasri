package com.amin.knn;

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


    public static void main(String[] args) {
        final double lat1 = 31.883674;
        final double long1 = 54.368775 ;

        final double dle = Math.toDegrees(100 / (R * sin(Math.toRadians(90 - lat1))));
        System.out.println(dle);
        System.out.println(real_distance(lat1, long1, lat1, long1 + dle));
        System.out.println(real_distance(90, 0, -90, 0));


        final double delalf = 360 / 999d;
        double disradius = 1000;
        for (int i = 0; i < 1000; i++) {
            System.out.println("--------------------");
            double alfa = delalf * i;
            final double dellong = disradius * cos(Math.toRadians(alfa));
            final double dellat = disradius * sin(Math.toRadians(alfa));

            final double dellongang = Math.toDegrees(dellong / (R * sin(Math.toRadians(90 - lat1))));
            final double dellatang = Math.toDegrees(dellat / (R));

//            System.out.println(real_distance(lat1, long1, lat1 + dellatang, long1 + dellongang));

            System.out.println(lat1 + dellatang);
            System.out.println(long1 + dellongang);

//            System.err.println(alfa);
        }


    }


}
