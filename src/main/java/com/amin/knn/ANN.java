package com.amin.knn;

import com.amin.config.MathTerminology;
import com.amin.neuralNetwork.regression.load.AminLevenberg;
import com.amin.pojos.LatLon;
import com.google.gson.Gson;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;
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
                final double temp = calcFeatureValue(stationnumber, country);
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


    public static void IranAnn(Make make) throws SQLException {

        final ResultSet resultSet = exeing("iran");
        ArrayList<Double> tempsArray = new ArrayList<>();
        ArrayList<LatLon> tempLAtlongs = new ArrayList<>();
        while (resultSet.next()) {

            final String stationnumber = resultSet.getString(1);
            final String country = resultSet.getString(2);
            final String stacitinametion = resultSet.getString(3);
            final String lati = resultSet.getString(4);
            final String longi = resultSet.getString(5);

            final double temp = calcFeatureValue("onday %s 10 26 TEMP ℃ 9900 1973 2017 %s",stationnumber, country);
            if (temp == -1000000)
                continue;
            else {
                tempsArray.add(temp);
                tempLAtlongs.add(new LatLon(Double.parseDouble(lati), Double.parseDouble(longi)));
            }
        }
        make.done(tempsArray, tempLAtlongs);
        System.err.println(new Gson().toJson(tempsArray));
        System.err.println(new Gson().toJson(tempLAtlongs));
    }




    public static void IranAnn(Make make,String functionString) throws SQLException {

        final ResultSet resultSet = exeing("iran");
        ArrayList<Double> tempsArray = new ArrayList<>();
        ArrayList<LatLon> tempLAtlongs = new ArrayList<>();
        while (resultSet.next()) {

            final String stationnumber = resultSet.getString(1);
            final String country = resultSet.getString(2);
            final String stacitinametion = resultSet.getString(3);
            final String lati = resultSet.getString(4);
            final String longi = resultSet.getString(5);

            final double temp = calcFeatureValue(functionString,stationnumber, country);
            if (temp == -1000000)
                continue;
            else {
                tempsArray.add(temp);
                tempLAtlongs.add(new LatLon(Double.parseDouble(lati), Double.parseDouble(longi)));
            }
        }
        make.done(tempsArray, tempLAtlongs);
        System.err.println(new Gson().toJson(tempsArray));
        System.err.println(new Gson().toJson(tempLAtlongs));
    }




















    public static void main(String[] args) throws SQLException {
        IranAnn((temps, latLons) -> {
            double[] outi = new double[temps.size()];
            double[] inp1 = new double[temps.size()];
            double[] inp2 = new double[temps.size()];
            for (int i = 0; i < temps.size(); i++) {
                outi[i] = temps.get(i);
                inp1[i] = latLons.get(i).getLat();
                inp2[i] = latLons.get(i).getLogn();
            }
            final BasicMLDataSet dataset = dataset(inp1, inp2, outi);
            final BasicNetwork network = AminLevenberg.netAndTrain(dataset,train -> {});

            for (MLDataPair pair : dataset) {
                final MLData output = ( network).compute(pair.getInput());

                System.out.println(pair.getInput().getData(0) * MAX_LAT + "," + pair.getInput().getData(1) * MAX_LONG
                        + ", actual=" + output.getData(0) * MAX_FITTNESS + ",ideal=" + pair.getIdeal().getData(0) * MAX_FITTNESS);
            }
            EncogDirectoryPersistence.saveObject(new File("assets/", "lat.net"), network);


        });


    }

    public static double MAX_LAT;
    public static double MAX_LONG;
    public static double MAX_FITTNESS;

    public static BasicMLDataSet dataset(double[] inp1, double[] inp2, double[] outi) {

        final double maxinput = MathTerminology.max(inp1);
        final double maxinput2 = MathTerminology.max(inp2);
        double foriminig;

        foriminig = MathTerminology.HaveAMines(outi) ? MathTerminology.min(outi) : MathTerminology.max(outi);
        final double maxSum = Math.abs(foriminig);

        MAX_FITTNESS = maxSum;
        MAX_LAT=maxinput;
        MAX_LONG = maxinput2;



        System.out.println(maxinput + " maxinput1");
        System.out.println(maxinput2 + " maxinput2");
        System.out.println(maxSum + "maxsum");

        final double[][] inps = new double[inp1.length][2];
        final double[][] outs = new double[inp1.length][1];
        for (int i = 0; i < outi.length; i++) {
            outi[i] /= maxSum;
            inp1[i] /= maxinput;
            inp2[i] /= maxinput2;
            inps[i] = new double[]{inp1[i], inp2[i]};
            outs[i] = new double[]{(outi[i])};
        }
       return new BasicMLDataSet(inps, outs);

    }


}

