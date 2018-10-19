package com.amin.ui.main.main;

import com.amin.config.C;
import com.amin.io.MyWriter;
import com.amin.jsons.Features;
import com.amin.jsons.OtherFormInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import static com.amin.ui.main.features.allheight.AntiHeightDayController.getFeatures;

/**
 * is created by aMIN on 10/5/2018 at 12:53 AM
 */
public class Run {
    public static void main(String[] args) {

// Serialization
//        OtherFormInfo obj = new OtherFormInfo();
//        obj.setFeaureName(Features.SKNT.getName());
        Gson gson = new Gson();
//        String json = gson.toJson(obj);
        System.out.println(args[0]);
        // Deserialization
        OtherFormInfo mOtherFormInfo = gson.fromJson(args[0], OtherFormInfo.class);
        final int initmonth = Integer.parseInt(args[1]);
        final int lastmonth = Integer.parseInt(args[2]);
        try {
            showChartAndAna(mOtherFormInfo, initmonth, lastmonth);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(mOtherFormInfo.getStationNamesList());

    }


    public static void showChartAndAna(OtherFormInfo formInfo, int initmonth, int lastmonth) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        String childFileName = "";
        String pathDirToSave = System.getProperty("user.home") + "/Desktop/data";
        if (formInfo.getDirTOSave() != null)
            pathDirToSave = formInfo.getDirTOSave();

        int fromYear = formInfo.getLowerYear().intValue();
        int toYear = formInfo.getHighYear().intValue();
        String featureName = formInfo.getFeaureName();
        int featureIndexCSV = getfeatureIndex(featureName).getLevelCode() - 1;
        String unit = formInfo.getFeatureUnit();

        String country = formInfo.getCountry();
        ArrayList<String> stationNumberslist = formInfo.getStationNumbersList();
        ArrayList<String> stationNamesList = formInfo.getStationNamesList();
        String height = formInfo.getHeight();


        childFileName = formInfo.getFeaureName() + "_" + height + "_" + formInfo.getCountry() + ".csv";
        File file = new File(pathDirToSave, childFileName);
        if (file.exists())
            file.delete();
        long start = 0;
        start = System.nanoTime();
        String[] z = {"00Z", "12Z"};
        String Z;
        String dayOfMonth;
        Month month;
        String monthDisp;
        String rootDir;
        String fileName;
        int counterforStations;
        ArrayList<ArrayList<Double>> heightAndFeature;
        Charting charting = new Charting(featureName, unit);
        MyWriter writerw = new MyWriter(pathDirToSave, childFileName, true);


        for (int monthInt = initmonth; monthInt <= lastmonth; monthInt++) {
            month = Month.of(monthInt);
            monthDisp = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            for (int day = 1; day <= 31; day++) {
                dayOfMonth = (day < 10 ? "0" : "") + day;


                counterforStations = -1;
                for (String stationNumber : stationNumberslist) {
                    counterforStations++;

                    for (int id = 0; id < 2; id++) {
                        Z = z[id];
                        for (int year = fromYear; year <= toYear; year++) {

                            rootDir = C.THIRDY_PATH + File.separator + country + File.separator + "year_" + year + File.separator + "month_" + monthInt + File.separator + stationNumber;
                            fileName = Z + "_" + dayOfMonth + "_" + monthDisp + "_" + year + ".csv";

                            try {
                                heightAndFeature = charting.getcol1col2daydata(rootDir + File.separator + fileName
                                        , 1, featureIndexCSV);

                                Double intrapolateFeature = intrapolateFeature(height, heightAndFeature);
                                if (intrapolateFeature != null)
                                    writerw.appendStringInFile(String.format(
                                            "%d,%s,%s,%s,%f,%s,%s,%s,%s\n", year, Z, stationNamesList.get(counterforStations)
                                            , stationNumber, intrapolateFeature, unit, String.valueOf(year), monthDisp, dayOfMonth));


                            } catch (IOException e) {
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }

                long time = System.nanoTime() - start;
                double t = time / 1e9d;
                System.out.printf("Each XXXXX took an average of %f s%n", t);

            }

        }

        writerw.close();
        long time = System.nanoTime() - start;
        double t = time / 1e9d;
        System.out.printf("took time %f s%n", t);


    }

    public static Features getfeatureIndex(String featureName) {
        return getFeatures(featureName);

    }


    public static Double intrapolateFeature(String height, ArrayList<ArrayList<Double>> heightAndFeature) {

        Double knotdesire = null;
        double heightdesire = Double.parseDouble(height);
        final Vector<Double> heigthsVector = new Vector<>();
        final Vector<Double> knotsVector = new Vector<>();

        heightAndFeature.forEach(doubles -> {

            double h = (doubles.get(0));
            double knot = (doubles.get(1));
            heigthsVector.add(h);
            knotsVector.add(knot);

        });

        for (int i = 0; i < heigthsVector.size() - 1; i++) {
            double hi = heigthsVector.get(i);
            double hiplus = heigthsVector.get(i + 1);
            double knoti = knotsVector.get(i);
            double knotiplus = knotsVector.get(i + 1);
            if ((heightdesire - hi) * (heightdesire - hiplus) <= 0) {
                knotdesire = (knotiplus - knoti) * (heightdesire - hi) / (hiplus - hi) + (knoti);
                break;
            }

        }
        return knotdesire;
    }

}
