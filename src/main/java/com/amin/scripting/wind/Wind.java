package com.amin.scripting.wind;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.config.MathTerminology;
import com.amin.jsons.FormInfo;
import com.amin.scripting.Do;
import com.amin.scripting.Function;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import static com.amin.ui.main.main.Run.getfeatureIndex;
import static com.amin.ui.main.main.Run.intrapolateFeature;

/**
 * is created by aMIN on 10/11/2018 at 1:59 AM
 */
public class Wind implements Function {
    private static Wind ourInstance = new Wind();

    public static Wind getInstance() {
        return ourInstance;
    }

    private Wind() {
    }

    @Override
    public String function(Object... o) {
        return null;
    }

    public void crossWindOnDayOnStation(FormInfo formInfo) {
        try {
            showChartAndAna(formInfo, new Do() {
                @Override
                public void filterAndDo() {
                    return;
                }

                @Override
                public ArrayList<ArrayList<Double>> filterAndDo(String dataFilePath, int... cols) throws IOException {
                    ArrayList<ArrayList<Double>> arrayLists = new ArrayList<>();
                        final ArrayList<ArrayList<String>> col1Col2 = Mapping.LatLong.getColsData(dataFilePath, ";", 1, 6, 7);
                        for (int j = 2; j < col1Col2.size() - 1; j++) {
                            if (!col1Col2.get(j).get(0).equals("NULL") && !col1Col2.get(j).get(1).equals("NULL")) {
                                ArrayList<Double> doubleArrayList = new ArrayList<>(2);
                                double v0 = Double.parseDouble(col1Col2.get(j).get(0));
                                double degAngle = Double.parseDouble(col1Col2.get(j).get(1));
                                double SKN = Double.parseDouble(col1Col2.get(j).get(2));
                                double newsci = SKN * Math.cos(Math.toRadians(degAngle));
                                doubleArrayList.add(v0);
                                doubleArrayList.add(newsci);
                                arrayLists.add(doubleArrayList);
                            }
                        }
                    return arrayLists;
                }

                @Override
                public double maxValue(ArrayList<Double> doubles) {
                    return   MathTerminology.max(doubles);
                }

                @Override
                public double minValue(ArrayList<Double> doubles) {
                    return MathTerminology.min(doubles);
                }
            });


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    private ArrayList<ArrayList<Object>> AllfeatureAndYear = new ArrayList<>();


    private void showChartAndAna(FormInfo formInfo, Do aDo) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
        String heightDesire = formInfo.getHeight();

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
                -50, 50, ytickUnit, "geopotHeight(m)", featureName + "(" + unit + ")", Charting.LINE_CHART);
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
                final String dayfilePath = rootDir + File.separator + fileName;
                try {


                    ArrayList<ArrayList<Double>> heightAndFeature = aDo.filterAndDo(dayfilePath, 0, 0/*not important*/);

                    final String seriesName = fileName.replaceAll(".csv", "");
                    charting.addSeriesToChart(heightAndFeature, featureName
                            , seriesName,
                            featureName, unit);

                    ;
                    Double intrapolatedKnot = intrapolateFeature(heightDesire, heightAndFeature);
                    if (intrapolatedKnot != null) {

                        knotslist.add(intrapolatedKnot);
                        featureAndYear.add(((Double) intrapolatedKnot));
                        featureAndYear.add(i);
                        yearsofFeature.add(i);
                        AllfeatureAndYear.add(featureAndYear);
                    }


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IOException e) {
//                    e.printStackTrace();
                }

            }
        }

        try {
            System.out.println(new Gson().toJson(knotslist));

            Charting charting2 = new Charting(yearsofFeature.get(0)-1, yearsofFeature.get(yearsofFeature.size()-1)+1, 1,
                    Math.floor(aDo.minValue(knotslist)), Math.ceil(aDo.maxValue(knotslist)), ytickUnit, "years", featureName + "(" + unit + ")", Charting.LINE_CHART);
            charting2.interpolateChart("interpolate years for " + featureName + " in " + heightDesire + " m",
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


}
