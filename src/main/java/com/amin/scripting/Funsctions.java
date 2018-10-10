package com.amin.scripting;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.jsons.Features;
import com.amin.jsons.FormInfo;
import com.amin.jsons.OtherFormInfo;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.main.features.day.FormDayController;
import com.amin.ui.main.main.Charting;
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
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import static com.amin.ui.main.features.allheight.AntiHeightDayController.getFeatures;

/**
 * is created by aMIN on 8/6/2018 at 8:17 PM
 */
public class Funsctions {
    private static Funsctions ourInstance = new Funsctions();

    public static Funsctions  getInstance() {
        return ourInstance;
    }

    private Funsctions() {
    }

    public void fopen(FormInfo formInfo) {
        try {
            showChartAndAna(formInfo);
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

    public void fAllstationsonDay(OtherFormInfo otherFormInfo){
        try {
            allstationsofCountry(otherFormInfo);
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

    private ArrayList<ArrayList<Object>> AllfeatureAndYear = new ArrayList<>();

    private void allstationsofCountry( OtherFormInfo formInfo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
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

        File file = new File(pathDirToSave,childFileName);
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






    private void showChartAndAna(FormInfo formInfo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

        Double invokelowrange = ((Double) method.invoke(charting123, lowrange));
        Double invokehighrange = ((Double) method.invoke(charting123, highrange));
        double ytickUnit = ((Double) method.invoke(charting123, 10));

        Charting charting = new Charting(900, 33000, 1000,
                invokelowrange, invokehighrange, ytickUnit, "geopotHeight(m)", featureName + "(" + unit + ")", Charting.LINE_CHART);
        final XYChart<Number, Number> sc = charting.getSc();


        ArrayList<Double> knotslist = new ArrayList<>();
        ArrayList<Integer> yearsofFeature = new ArrayList<>();

        String[] z = {"00Z", "12Z"};
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


                    heightAndFeature = charting.addSeriesToChart(featureName
                            , fileName.replaceAll(".csv", ""),
                            rootDir + File.separator + fileName, 1, featureIndexCSV, featureName, unit);


                    Double intrapolatedKnot = intrapolateFeature(height, heightAndFeature);
                    if (intrapolatedKnot != null) {

                        knotslist.add(intrapolatedKnot);
                        featureAndYear.add(((Double) intrapolatedKnot));
                        featureAndYear.add(i);
                        yearsofFeature.add(i);
                        AllfeatureAndYear.add(featureAndYear);
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

        try {

            Charting charting2 = new Charting(fromYear, toYear, 1,
                    invokelowrange, invokehighrange, ytickUnit, "years", featureName + "(" + unit + ")", Charting.LINE_CHART);
            charting2.interpolateChart("interpolate years for " + featureName + " in " + height + " m",
                    "interpolate", knotslist, yearsofFeature, "avg line val is on ", unit);


            final VBox vbox = new VBox();
            final HBox hbox = new HBox();
            vbox.setLayoutY(300);
            vbox.setLayoutX(400);
            vbox.setStyle("-fx-background-color: #fff");
            vbox.getChildren().addAll(sc, charting2.getSc());
            hbox.setPadding(new Insets(10, 10,
                    03.10, 10));
            Parent root = FXMLLoader.load(FormDayController.class.getResource("/com/amin/ui/main/features/day/chart.fxml"));
            ((VBox) root).getChildren().add(vbox);
            StageOverride stage = new StageOverride();
            stage.setTitle("statistical analysis");

            SceneJson sceneJson = new SceneJson<ArrayList>(root, 450, 450);


            sceneJson.setJson(AllfeatureAndYear);

            stage.setScene(sceneJson);
            stage.show();

        } catch (IOException e) {
            Dialog.createExceptionDialog(e);
        }


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

        Charting charting = new Charting(900, 33000, 1000,
                invokelowrange, invokehighrange, ytickUnit, "geopotHeight(m)", featureName + "(" + unit + ")", Charting.LINE_CHART);

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
                heightAndFeature = charting.addSeriesToChart(featureName
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
