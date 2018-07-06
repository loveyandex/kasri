package com.amin.ui.main.main;

import com.amin.analysis.wind.WindMining;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.util.ArrayList;

/**
 * is created by aMIN on 7/5/2018 at 16:09
 */
public class Charting {
    public static String LINE_CHART = "line";
    public static String SCATTER_CHART = "ScatterChart";

    public XYChart<Number, Number> getSc() {
        return sc;
    }

    public void setSc(XYChart<Number, Number> sc) {
        this.sc = sc;
    }

    XYChart<Number, Number> sc;

    public Charting(double XlowerBound, double XupperBound, double XtickUnit,
                    double YlowerBound, double YupperBound, double YtickUnit,
                    String Xlabel, String Ylabel, String chartType) {
        NumberAxis xAxis = new NumberAxis(XlowerBound, XupperBound, XtickUnit);
        NumberAxis yAxis = new NumberAxis(YlowerBound, YupperBound, YtickUnit);
        xAxis.setLabel(Xlabel);
        yAxis.setLabel(Ylabel);
        if (chartType.equals(SCATTER_CHART)) {
            sc = new ScatterChart<Number, Number>(xAxis, yAxis);

        } else {
            sc = new LineChart<>(xAxis, yAxis);

        }

    }


    public XYChart<Number, Number> addSeriesToChart(String title, String seriesName,
                                                    String dayfilePath) throws IOException {
        sc.setTitle(title);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesName);

        ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol(dayfilePath, 1, 7);
        for (int j = 2; j < windSpeedCol.size() - 1; j++) {
            if (!windSpeedCol.get(j).get(0).equals("NULL") && !windSpeedCol.get(j).get(1).equals("NULL")) {
                double v0 = Double.parseDouble(windSpeedCol.get(j).get(0));
                double v1 = Double.parseDouble(windSpeedCol.get(j).get(1));
                series1.getData().add(new XYChart.Data(v0, v1));
            }
        }


//        sc.setPrefSize(500, 400);
        sc.getData().addAll(series1);


//        final DropShadow shadow = new DropShadow();
//        shadow.setOffsetX(2);
//        shadow.setColor(Color.GREY);
//        sc.setEffect(shadow);
//        sc.setStyle("-fx-background-color: #fff;");

        return sc;

    }


    public static void main(String[] args) {


    }
}
