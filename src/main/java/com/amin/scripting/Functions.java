package com.amin.scripting;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.config.MathTerminology;
import com.amin.jsons.Features;
import com.amin.jsons.FormInfo;
import com.amin.jsons.OtherFormInfo;
import com.amin.jsons.SomeDays;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.main.features.day.FormDayController;
import com.amin.ui.main.main.Charting;
import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import static com.amin.ui.main.features.allheight.AntiHeightDayController.getFeatures;

/**
 * is created by aMIN on 8/6/2018 at 8:17 PM
 */
public class Functions {
    private static Functions ourInstance = new Functions();
    private ArrayList<IOException> ioExceptions = new ArrayList<>();
    private ArrayList<ArrayList<Object>> AllfeatureAndYear = new ArrayList<>();

    private Functions() {
    }

    public static Functions getInstance() {
        return ourInstance;
    }

    public void fopen(FormInfo formInfo) {
        try {
            onDayOneHeightOneStation(formInfo);
        } catch (NoSuchMethodException e) {
            Dialog.createExceptionDialog(new RuntimeException("choose the right unit"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void someDays(SomeDays someDays) {
        try {
            someDaysOneHeightOneStations(someDays);
        } catch (NoSuchMethodException e) {
            Dialog.createExceptionDialog(new RuntimeException("choose the right unit"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public double fopen2(FormInfo formInfo) {
        try {
            final double arrayLists = returnInterapulate(formInfo);
            return arrayLists;
        } catch (NoSuchMethodException e) {
            Dialog.createExceptionDialog(new RuntimeException("choose the right unit"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public void fAllstationsonDay(OtherFormInfo otherFormInfo) {
        try {
            allstationsofCountryOndayOneHeight(otherFormInfo);
        } catch (NoSuchMethodException e) {
            Dialog.createExceptionDialog(new RuntimeException("choose the right unit"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void allstationsofCountryOndayOneHeight(OtherFormInfo formInfo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        String childFileName = "";
        String pathDirToSave = System.getProperty("user.home") + "/Desktop/getdata";
        if (formInfo.getDirTOSave() != null)
            pathDirToSave = formInfo.getDirTOSave();

        if (!AllfeatureAndYear.isEmpty()) AllfeatureAndYear.clear();
        int fromYear = formInfo.getLowerYear().intValue();
        int toYear = formInfo.getHighYear().intValue();
        String featureName = formInfo.getFeaureName();
        int featureIndexCSV = getfeatureIndex(featureName).getLevelCode() - 1;
        String unit = formInfo.getFeatureUnit();


        int numDay = formInfo.Date.Day;
        String dayOfMonth = (numDay < 10 ? "0" : "") + numDay;
        int monthInt = formInfo.getDate().Month;
        Month month = Month.of(monthInt);
        String monthDisp = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        String country = formInfo.getCountry();
        ArrayList<String> stationNumberslist = formInfo.getStationNumbersList();
        ArrayList<String> stationNamesList = formInfo.getStationNamesList();
        String height = formInfo.getHeight();


        double lowrange = Double.parseDouble(getfeatureIndex(featureName).getLow_range());
        double highrange = Double.parseDouble(getfeatureIndex(featureName).getHigh_range());

        Charting charting123 = new Charting();
        int cti = charting123.convertTogether(featureName, unit);

        Method method;
        method = Charting.class.getMethod("conv" + cti, double.class);

        Double invokelowrange = ((Double) method.invoke(charting123, lowrange));
        Double invokehighrange = ((Double) method.invoke(charting123, highrange));
        double ytickUnit = ((Double) method.invoke(charting123, 10));

        Charting charting = new Charting(900, 33000, 1000,
                invokelowrange, invokehighrange, ytickUnit, "geopotHeight(m)", featureName + "(" + unit + ")", Charting.LINE_CHART);
        final XYChart<Number, Number> sc = charting.getSc();

        int counterforStations = -1;
        childFileName = formInfo.getFeaureName() + "_" + height + "_" + formInfo.getCountry() + "_" + dayOfMonth + "_" + monthDisp + "_.csv";

        File file = new File(pathDirToSave, childFileName);
        if (file.exists())
            file.delete();

        for (String stationNumber : stationNumberslist) {
            counterforStations++;
            ArrayList<Double> featurelist = new ArrayList<>();
            ArrayList<Integer> yearsofFeature = new ArrayList<>();

            String[] z = {"00Z", "12Z"};
            for (int id = 0; id < 2; id++) {
                int kkk = 0;

                String Z = z[id];
                for (int i = fromYear; i <= toYear; i++) {

                    ArrayList<Object> featureAndYear = new ArrayList<>();
                    String rootDir = C.THIRDY_PATH + File.separator + country + File.separator + "year_" + i + File.separator + "month_" + monthInt + File.separator + stationNumber;
                    String fileName = Z + "_" + dayOfMonth + "_" + monthDisp + "_" + i + ".csv";
                    ArrayList<ArrayList<Double>> heightAndFeature;
                    try {
//
//                    heightAndFeature = charting.addSeriesToChart(featureName
//                            , fileName.replaceAll(".csv", ""),
//                            rootDir + File.separator + fileName, 1, featureIndexCSV);


                        heightAndFeature = charting.addSeriesToChart(featureName
                                , fileName.replaceAll(".csv", ""),
                                rootDir + File.separator + fileName, 1, featureIndexCSV, featureName, unit);


                        Double intrapolateFeature = intrapolateFeature(height, heightAndFeature);
                        if (intrapolateFeature != null) {
                            featurelist.add(intrapolateFeature);
                            featureAndYear.add(((Double) intrapolateFeature));
                            featureAndYear.add(i);
                            yearsofFeature.add(i);
                            AllfeatureAndYear.add(featureAndYear);
                            Mapping.LatLong.writeStringInFile(pathDirToSave, childFileName
                                    , String.format("%d,%s,%s,%s,%f,%s\n", i, Z, stationNamesList.get(counterforStations)
                                            , stationNumber, intrapolateFeature, unit), true);
                        }


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

        final String absfilepathSaved = pathDirToSave + File.separator + childFileName;
        ArrayList<ArrayList<String>> colsData = Mapping.LatLong.getColsData(
                absfilepathSaved, ","
                , 0, 1, 2, 3, 4, 5);


        final String finalPathDirToSave = pathDirToSave;
        colsData.add(new ArrayList<String>() {{
            add(absfilepathSaved);
            add(finalPathDirToSave);
        }});

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("success");
        alert.setHeaderText("valid stations data saved in your path");
        alert.show();
        alert.setOnHiding(event -> {
            Stage primaryStage = new StageOverride();
            Parent root = null;
            try {
                root = FXMLLoader.load(this.getClass().getResource("/com/amin/ui/main/features/AllStationsOfCountryInOneDay/allstationsstatistic.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            root.setStyle("-fx-padding: 30 30 30 30 ");

            SceneJson sceneJson = new SceneJson<>(root);
            sceneJson.setJson(colsData);
            primaryStage.setScene(sceneJson);

            primaryStage.setResizable(false);
            primaryStage.show();
        });

    }


    public void onDayOneHeightOneStation(FormInfo formInfo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!AllfeatureAndYear.isEmpty()) AllfeatureAndYear.clear();
        int fromYear = formInfo.getLowerYear().intValue();
        int toYear = formInfo.getHighYear().intValue();
        String featureName = formInfo.getFeaureName();
        int featureIndexCSV = getfeatureIndex(featureName).getLevelCode() - 1;
        String unit = formInfo.getFeatureUnit();

        int numDay = formInfo.getDate().Day;
        String dayOfMonth = (numDay < 10 ? "0" : "") + numDay;
        int monthInt = formInfo.getDate().Month;
        Month month = Month.of(monthInt);
        String monthDisp = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        String country = formInfo.getCountry();
        String stationNumber = formInfo.getStationNumber();
        String height = formInfo.getHeight();


        double lowrange = Double.parseDouble(getfeatureIndex(featureName).getLow_range());
        double highrange = Double.parseDouble(getfeatureIndex(featureName).getHigh_range());

        Charting charting123 = new Charting();
        int cti = charting123.convertTogether(featureName, unit);

        Method method;
        method = Charting.class.getMethod("conv" + cti, double.class);

        double ytickUnit = ((Double) method.invoke(charting123, 10));


        ArrayList<Double> knotslist = new ArrayList<>();
        ArrayList<Integer> yearsofFeature = new ArrayList<>();

        String[] z = {"00Z", "12Z"};
        ArrayList<ArrayList<ArrayList<Double>>> all = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<String>();
        ArrayList<String> seriecsnAMES = new ArrayList<String>();
        ArrayList<Double> yearsdata = new ArrayList<>();

        for (int id = 0; id < 2; id++) {

            String Z = z[id];
            for (int i = fromYear; i <= toYear; i++) {
                ArrayList<Object> featureAndYear = new ArrayList<>();
                String rootDir = C.THIRDY_PATH + File.separator + country + File.separator + "year_" + i + File.separator + "month_" + monthInt + File.separator + stationNumber;
                String fileName = Z + "_" + dayOfMonth + "_" + monthDisp + "_" + i + ".csv";
                ArrayList<ArrayList<Double>> heightAndFeature;
                try {
//
//                    heightAndFeature = charting.addSeriesToChart(featureName
//                            , fileName.replaceAll(".csv", ""),
//                            rootDir + File.separator + fileName, 1, featureIndexCSV);

//
//                    heightAndFeature = charting.addSeriesToChart(featureName
//                            , Z+"_"+i,
//                            rootDir + File.separator + fileName, 1, featureIndexCSV, featureName, unit);
//
//
                    final String title = featureName;
                    final String seriesName = Z + "_" + i;
                    heightAndFeature = Charting.returnCOlCol2Data(title
                            , seriesName,
                            rootDir + File.separator + fileName, 1, featureIndexCSV, featureName, unit);

                    seriecsnAMES.add(seriesName);
                    titles.add(title);
                    yearsdata.add((double) i);
                    all.add(heightAndFeature);

                    Double intrapolatedKnot = intrapolateFeature(height, heightAndFeature);

                    if (intrapolatedKnot != null) {

                        knotslist.add(intrapolatedKnot);
                        featureAndYear.add(((Double) intrapolatedKnot));
                        featureAndYear.add(i);
                        featureAndYear.add(unit);

                        yearsofFeature.add(i);

                        AllfeatureAndYear.add(featureAndYear);
                    }


                } catch (IOException e) {
                    ioExceptions.add(e);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
//        if (!ioExceptions.isEmpty()) {
//            Dialog.createIOExceptionDialog(ioExceptions);
//            ioExceptions.clear();
//        }

        try {

            final double[] maxMin = maxMin(all);


            Charting charting = new Charting(1000, 33000, 1000,
                    maxMin[1] - 1, maxMin[0] + 1, ytickUnit, "geopotHeight(m)", featureName + "(" + unit + ")", Charting.LINE_CHART);
            final XYChart<Number, Number> sc = charting.getSc();
            for (int rr = 0; rr < all.size(); rr++) {
                sc.setTitle(titles.get(rr));
                XYChart.Series series1 = new XYChart.Series();
                series1.setName(seriecsnAMES.get(rr));
                final ArrayList<ArrayList<Double>> arrayLists = all.get(rr);
                for (int g = 0; g < arrayLists.size(); g++) {
                    final ArrayList<Double> vs = arrayLists.get(g);
                    series1.getData().add(new XYChart.Data(vs.get(0), vs.get(1)));
                }
                sc.getData().addAll(series1);


            }


            System.err.println(new Gson().toJson(yearsdata));
            Charting charting2 = new Charting(((int) MathTerminology.min(yearsdata)) - 1, toYear, 1,
                    maxMin[1] - 1, maxMin[0] + 1, ytickUnit, "years", featureName + "(" + unit + ")", Charting.LINE_CHART);
            charting2.interpolateChart("interpolate years for " + featureName + " in " + height + " m",
                    "interpolate", knotslist, yearsofFeature, "avg line val is on ", unit);


            final VBox vbox = new VBox();
            final HBox hbox = new HBox();
            vbox.getChildren().addAll(sc, charting2.getSc());
            hbox.setPadding(new Insets(10, 10, 03.10, 10));
            Parent root = FXMLLoader.load(FormDayController.class.getResource("/com/amin/ui/main/features/day/chart.fxml"));
            ((VBox) root).getChildren().add(vbox);
            StageOverride stage = new StageOverride();


            stage.setTitle("statistical analysis");
            root.getStylesheets().add("/chart.css");

            SceneJson sceneJson = new SceneJson<ArrayList>(root, 450, 450);


            sceneJson.setJson(AllfeatureAndYear);

            stage.setScene(sceneJson);
            stage.show();

        } catch (IOException e) {
            Dialog.createExceptionDialog(e);
        }


    }


    public void someDaysOneHeightOneStations(SomeDays someDays) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!AllfeatureAndYear.isEmpty()) AllfeatureAndYear.clear();
        int fromYear = someDays.getLowerYear().intValue();
        int toYear = someDays.getHighYear().intValue();
        String featureName = someDays.getFeaureName();
        int featureIndexCSV = getfeatureIndex(featureName).getLevelCode() - 1;
        String unit = someDays.getFeatureUnit();
        /**
         * java time is good for recognizing current date
         */


        int fromnumDay = someDays.getFromDate().Day;
        String fromdayOfMonth = (fromnumDay < 10 ? "0" : "") + fromnumDay;
        int fromMonthInt = someDays.getFromDate().Month;
        Month frommonth = Month.of(fromMonthInt);
        String frommonthDisp = frommonth.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        LocalDate myDate0 = LocalDate.of(fromYear, fromMonthInt, fromnumDay);

        String country = someDays.getCountry();
        String stationNumber = someDays.getStationNumber();
        String height = someDays.getHeight();

        Charting charting123 = new Charting();
        int cti = charting123.convertTogether(featureName, unit);
        Method method;
        method = Charting.class.getMethod("conv" + cti, double.class);

        double ytickUnit = ((Double) method.invoke(charting123, 10));

        ArrayList<Double> knotslist = new ArrayList<>();
        ArrayList<Integer> yearsofFeature = new ArrayList<>();

        String[] z = {"00Z", "12Z"};
        ArrayList<ArrayList<ArrayList<Double>>> all = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> seriecsnAMES = new ArrayList<String>();
        ArrayList<Double> yearsdata = new ArrayList<>();

        for (int yearIndexer = fromYear; yearIndexer <= toYear; yearIndexer++) {
            for (int id = 0; id < 2; id++) {
                String Z = z[id];
                ArrayList<Object> featureAndYear = new ArrayList<>();
                int dayIndexer = someDays.getMinusDay();
                while (true) {
                    if (dayIndexer > someDays.getPlusDay())
                        break;
                   LocalDate myDate = myDate0.plus(dayIndexer, ChronoUnit.DAYS);
                    final Month month = myDate.getMonth();
                    final String monthDisplayName = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                    final int monthValue = month.getValue();

                    final int dayOfMonth = myDate.getDayOfMonth();
                    fromdayOfMonth = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                    dayIndexer++;


                    String rootDir = C.THIRDY_PATH + File.separator +
                            country + File.separator + "year_" + yearIndexer + File.separator
                            + "month_" + monthValue + File.separator + stationNumber;

                    String fileName = Z + "_" + fromdayOfMonth + "_"
                            + monthDisplayName + "_" + yearIndexer + ".csv";


                    ArrayList<ArrayList<Double>> heightAndFeature;
                    try {
                        final String title = featureName;
                        final String seriesName = Z + "_" +monthDisplayName+fromdayOfMonth+"_"+ yearIndexer;
                        heightAndFeature = Charting.returnCOlCol2Data(title
                                , seriesName,
                                rootDir + File.separator + fileName, 1, featureIndexCSV, featureName, unit);

                        seriecsnAMES.add(seriesName);
                        titles.add(title);
                        yearsdata.add((double) yearIndexer);
                        all.add(heightAndFeature);

                        Double intrapolatedKnot = intrapolateFeature(height, heightAndFeature);

                        if (intrapolatedKnot != null) {

                            knotslist.add(intrapolatedKnot);
                            featureAndYear.add(((Double) intrapolatedKnot));
                            featureAndYear.add(yearIndexer);
                            featureAndYear.add(unit);

                            yearsofFeature.add(yearIndexer);

                            AllfeatureAndYear.add(featureAndYear);
                        }


                    } catch (IOException e) {
                        ioExceptions.add(e);
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
//        if (!ioExceptions.isEmpty()) {
//            Dialog.createIOExceptionDialog(ioExceptions);
//            ioExceptions.clear();
//        }

        try {

            final double[] maxMin = maxMin(all);


            Charting charting = new Charting(1000, 33000, 1000,
                    maxMin[1] , ((int) maxMin[0]) + 1, ((int) ytickUnit), "geopotHeight(m)", featureName + "(" + unit + ")", Charting.LINE_CHART);
            final XYChart<Number, Number> sc = charting.getSc();
            for (int rr = 0; rr < all.size(); rr++) {
                sc.setTitle(titles.get(rr));
                XYChart.Series series1 = new XYChart.Series();
                series1.setName(seriecsnAMES.get(rr));
                final ArrayList<ArrayList<Double>> arrayLists = all.get(rr);
                for (int g = 0; g < arrayLists.size(); g++) {
                    final ArrayList<Double> vs = arrayLists.get(g);
                    series1.getData().add(new XYChart.Data(vs.get(0), vs.get(1)));
                }
                sc.getData().addAll(series1);


            }


            System.err.println(new Gson().toJson(yearsdata));
            Charting charting2 = new Charting(((int) MathTerminology.min(yearsdata)) - 1, toYear+1, 1,
                    ((int) maxMin[1]), ((int) maxMin[0]) + 1, ((int) ytickUnit), "years", featureName + "(" + unit + ")", Charting.LINE_CHART);
            charting2.interpolateChart("interpolate years for " + featureName + " in " + height + " m",
                    "interpolate", knotslist, yearsofFeature, "avg line val is on ", unit);


            final VBox vbox = new VBox();
            final HBox hbox = new HBox();
            vbox.getChildren().addAll(sc, charting2.getSc());
            hbox.setPadding(new Insets(10, 10, 03.10, 10));
            Parent root = FXMLLoader.load(FormDayController.class.getResource("/com/amin/ui/main/features/day/chart.fxml"));
            ((VBox) root).getChildren().add(vbox);
            StageOverride stage = new StageOverride();


            stage.setTitle("statistical analysis");
            root.getStylesheets().add("/chart.css");

            SceneJson sceneJson = new SceneJson<ArrayList>(root, 450, 450);


            sceneJson.setJson(AllfeatureAndYear);

            stage.setScene(sceneJson);
            stage.show();

        } catch (IOException e) {
            Dialog.createExceptionDialog(e);
        }


    }


    private double[] maxMin(ArrayList<ArrayList<ArrayList<Double>>> allyearsData) {
        ArrayList<Double> minarrays = new ArrayList<>();
        ArrayList<Double> maxarrays = new ArrayList<>();

        for (ArrayList<ArrayList<Double>> oneArrayLists : allyearsData) {
            ArrayList<Double> arrayListdata = new ArrayList<>();

            for (ArrayList<Double> ddd : oneArrayLists) {
                for (int i = 0; i < ddd.size(); i++) {
                    final Double aDouble = ddd.get(1);
                    arrayListdata.add(aDouble);
                }
                maxarrays.add(MathTerminology.max(arrayListdata));
                minarrays.add(MathTerminology.min(arrayListdata));
            }
        }
        return new double[]{MathTerminology.max(maxarrays), MathTerminology.min(minarrays)};
    }


    private double returnInterapulate(FormInfo formInfo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        boolean haveever = false;
        int fromYear = formInfo.getLowerYear().intValue();
        int toYear = formInfo.getHighYear().intValue();
        String featureName = formInfo.getFeaureName();
        int featureIndexCSV = getfeatureIndex(featureName).getLevelCode() - 1;
        String unit = formInfo.getFeatureUnit();


        int numDay = formInfo.getDate().Day;
        String dayOfMonth = (numDay < 10 ? "0" : "") + numDay;
        int monthInt = formInfo.getDate().Month;
        Month month = Month.of(monthInt);
        String monthDisp = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

        String country = formInfo.getCountry();
        String stationNumber = formInfo.getStationNumber();
        String height = formInfo.getHeight();


        double lowrange = Double.parseDouble(getfeatureIndex(featureName).getLow_range());
        double highrange = Double.parseDouble(getfeatureIndex(featureName).getHigh_range());

        Charting charting123 = new Charting();
        int cti = charting123.convertTogether(featureName, unit);

        Method method;
        method = Charting.class.getMethod("conv" + cti, double.class);

        Double invokelowrange = ((Double) method.invoke(charting123, lowrange));
        Double invokehighrange = ((Double) method.invoke(charting123, highrange));
        double ytickUnit = ((Double) method.invoke(charting123, 10));

        Charting charting = new Charting();
        ArrayList<ArrayList<Object>> featureAndYears = new ArrayList<>();

        String[] z = {"00Z", "12Z"};

        String Z = z[0];
        double sum = 0.0;
        int intt = 0;
        for (int i = fromYear; i <= toYear; i++) {
            ArrayList<Object> featureAndYear = new ArrayList<>();
            String rootDir = C.THIRDY_PATH + File.separator + country + File.separator + "year_" + i + File.separator + "month_" + monthInt + File.separator + stationNumber;
            String fileName = Z + "_" + dayOfMonth + "_" + monthDisp + "_" + i + ".csv";
            ArrayList<ArrayList<Double>> heightAndFeature;
            try {
                heightAndFeature = charting.addSeriesToChartSimple(featureName
                        , fileName.replaceAll(".csv", ""),
                        rootDir + File.separator + fileName, 1, featureIndexCSV, featureName, unit);


                Double intrapolateFeature = intrapolateFeature(height, heightAndFeature);
                if (intrapolateFeature != null) {
                    haveever = true;
                    sum += intrapolateFeature;
                    intt++;
                    featureAndYear.add(intrapolateFeature);
                    featureAndYear.add(i);
                    featureAndYears.add(featureAndYear);
                }


            } catch (IOException e) {
//
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }
        if (haveever)
            return sum / intt;
        else
            return -1000000;


    }


    private Features getfeatureIndex(String featureName) {
        return getFeatures(featureName);

    }


    private Double intrapolateFeature(String height, ArrayList<ArrayList<Double>> heightAndFeature) {

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
