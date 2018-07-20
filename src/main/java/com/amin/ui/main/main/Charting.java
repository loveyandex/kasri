package com.amin.ui.main.main;

import com.amin.analysis.wind.WindMining;
import com.amin.jsons.Features;
import com.amin.jsons.UnitConvertor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import javax.measure.Measure;
import javax.measure.quantity.Pressure;
import javax.measure.unit.AlternateUnit;
import javax.measure.unit.Unit;
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

    private XYChart<Number, Number> sc;

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


    public ArrayList<ArrayList<String>> addSeriesToChart(String title, String seriesName, String dayfilePath, int col1, int col2) throws IOException {
        sc.setTitle(title);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesName);
        ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol(dayfilePath, col1, col2);
        for (int j = 2; j < windSpeedCol.size() - 1; j++) {
            if (!windSpeedCol.get(j).get(0).equals("NULL") && !windSpeedCol.get(j).get(1).equals("NULL")) {
                double v0 = Double.parseDouble(windSpeedCol.get(j).get(0));
                double v1 = Double.parseDouble(windSpeedCol.get(j).get(1));
                series1.getData().add(new XYChart.Data(v0, v1));
            }
        }
        sc.getData().addAll(series1);
        return windSpeedCol;
    }


    public ArrayList<ArrayList<String>> addSeriesToChart(String title, String seriesName, String dayfilePath, int col1, int col2
            , String featurename, String unitname) throws IOException {

        sc.setTitle(title);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesName);
        ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol(dayfilePath, col1, col2);
        for (int j = 2; j < windSpeedCol.size() - 1; j++) {
            if (!windSpeedCol.get(j).get(0).equals("NULL") && !windSpeedCol.get(j).get(1).equals("NULL")) {
                double v0 = Double.parseDouble(windSpeedCol.get(j).get(0));
                double v1 = Double.parseDouble(windSpeedCol.get(j).get(1));

                series1.getData().add(new XYChart.Data(v0, v1));
            }
        }
        sc.getData().addAll(series1);
        return windSpeedCol;
    }


    private int convertTogether(String featurename, String unitname) {
        if (featurename.equals(Features.PRES.getName())) {

            if (unitname.equals("hPa")) {
                return 2;

            } else if (unitname.equals(UnitConvertor.PRES.units.getPascal())) {
                return 3;

            } else if (unitname.equals(UnitConvertor.PRES.units.getAtmosphere())) {
                return 4;
            } else if (unitname.equals(UnitConvertor.PRES.units.getBar())) {
                return 5;
            } else if (unitname.equals(UnitConvertor.PRES.units.getMillimeterOfMercury())) {
                return 6;
            }


        } else if (featurename.equals(Features.SKNT.getName())) {

            if (unitname.equals(UnitConvertor.SPEED.units.getMetersPerSecond())) {
                return 7;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getKnot())) {
                return 8;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getKilometresPerHour())) {
                return 9;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getMach())) {
                return 10;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getMilesPerHour())) {
                return 11;
            }

        } else if (featurename.equals(Features.DRCT.getName())) {
            if (unitname.equals(UnitConvertor.DRCT.units.getRadian())) {
                return 12;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getDegreeAngle())) {
                return 13;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getGrade())) {
                return 14;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getMinuteAngle())) {
                return 15;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getSecondAngle())) {
                return 16;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getRevolution())) {
                return 17;
            }

        } else if (featurename.equals(Features.TEMP.getName()) || featurename.equals(Features.DWPT.getName())) {
            if (unitname.equals(UnitConvertor.TEMP.units.getCelsius())) {
                return 18;

            } else if (unitname.equals(UnitConvertor.TEMP.units.getKelvin())) {
                return 19;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getFahrenheit())) {
                return 20;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getRankine())) {
                return 21;
            }
        } else if (featurename.equals(Features.HGHT.getName())) {
            if (unitname.equals(UnitConvertor.HGHT.units.getMeter())) {
                return 22;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getFoot())) {
                return 23;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getMile())) {
                return 24;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getYard())) {
                return 25;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getInch())) {
                return 26;
            }

        } else if (featurename.equals(Features.MIXR.getName())) {
            return 1;

        } else if (featurename.equals(Features.RELH.getName())) {
            return 1;
        } else if (featurename.equals(Features.THTA.getName()) || featurename.equals(Features.THTE.getName()) || featurename.equals(Features.THTV.getName())) {
            if (unitname.equals(UnitConvertor.TEMP.units.getCelsius())) {
                return 27;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getKelvin())) {
                return 28;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getFahrenheit())) {
                return 29;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getRankine())) {
                return 30;
            }
        }

        return 0;
    }


    public double conv1(double v1) {
        return v1;
    }

    public double conv2(double v1) {
        return v1;
    }

    public double conv3(double v1) {
        return 100.0 * v1;
    }

    public double conv4(double v1) {
        v1 = 100.0 * v1;
        Unit<Pressure> atmosphere = UnitConvertor.PRES.units.getAtmosphere();
        AlternateUnit<Pressure> pascal = UnitConvertor.PRES.units.getPascal();

        return Measure.valueOf(v1, pascal).doubleValue(atmosphere);

    }

    public double conv5(double v1) {
        v1 = 100.0 * v1;
        Unit<Pressure> bar = UnitConvertor.PRES.units.getBar();
        AlternateUnit<Pressure> pascal = UnitConvertor.PRES.units.getPascal();

        return Measure.valueOf(v1, pascal).doubleValue(bar);
    }

    public double conv6(double v1) {
        v1 = 100.0 * v1;
        Unit<Pressure> millimeterOfMercury = UnitConvertor.PRES.units.getMillimeterOfMercury();
        AlternateUnit<Pressure> pascal = UnitConvertor.PRES.units.getPascal();

        return Measure.valueOf(v1, pascal).doubleValue(millimeterOfMercury);
    }

    public double conv7(double v1) {
        return Measure.valueOf(v1, UnitConvertor.SPEED.units.getKnot())
                .doubleValue(UnitConvertor.SPEED.units.getMetersPerSecond());
    }

    public double conv8(double v1) {
        return Measure.valueOf(v1, UnitConvertor.SPEED.units.getKnot())
                .doubleValue(UnitConvertor.SPEED.units.getKnot());
    }

    public double conv9(double v1) {
        return Measure.valueOf(v1, UnitConvertor.SPEED.units.getKnot())
                .doubleValue(UnitConvertor.SPEED.units.getKilometresPerHour());
    }

    public double conv10(double v1) {
        return Measure.valueOf(v1, UnitConvertor.SPEED.units.getKnot())
                .doubleValue(UnitConvertor.SPEED.units.getMach());
    }

    public double conv11(double v1) {
        return Measure.valueOf(v1, UnitConvertor.SPEED.units.getKnot())
                .doubleValue(UnitConvertor.SPEED.units.getMilesPerHour());
    }

    public double conv12(double v1) {
        return Measure.valueOf(v1, UnitConvertor.DRCT.units.getDegreeAngle())
                .doubleValue(UnitConvertor.DRCT.units.getRadian());
    }

    public double conv13(double v1) {
        return Measure.valueOf(v1, UnitConvertor.DRCT.units.getDegreeAngle())
                .doubleValue(UnitConvertor.DRCT.units.getDegreeAngle());
    }

    public double conv14(double v1) {
        return Measure.valueOf(v1, UnitConvertor.DRCT.units.getDegreeAngle())
                .doubleValue(UnitConvertor.DRCT.units.getGrade());
    }

    public double conv15(double v1) {
        return Measure.valueOf(v1, UnitConvertor.DRCT.units.getDegreeAngle())
                .doubleValue(UnitConvertor.DRCT.units.getMinuteAngle());
    }

    public double conv16(double v1) {
        return Measure.valueOf(v1, UnitConvertor.DRCT.units.getDegreeAngle())
                .doubleValue(UnitConvertor.DRCT.units.getSecondAngle());
    }

    public double conv17(double v1) {
        return Measure.valueOf(v1, UnitConvertor.DRCT.units.getDegreeAngle())
                .doubleValue(UnitConvertor.DRCT.units.getRevolution());
    }

    public double conv18(double v1) {
    }

    public double conv19(double v1) {
    }

    public double conv20(double v1) {
    }

    public double conv21(double v1) {
    }

    public double conv22(double v1) {
    }

    public double conv23(double v1) {
    }

    public double conv24(double v1) {
    }

    public double conv25(double v1) {
    }

    public double conv26(double v1) {
    }

    public double conv27(double v1) {
    }

    public double conv28(double v1) {
    }

    public double conv29(double v1) {
    }

    public double conv30(double v1) {
    }








    public void interpolateChart(String title, String seriesName, ArrayList<Double> knots, int[] years, String avgseriname, String unit) {
        sc.setTitle(title);
        XYChart.Series series = new XYChart.Series();
        XYChart.Series avgseries = new XYChart.Series();
        series.setName(seriesName);
        double sum = 0.0;
        for (int i = 0; i < knots.size(); i++) {
            sum += knots.get(i);
        }
        double avgknots = sum / knots.size();
        avgseries.setName(avgseriname + String.format("%.4f ", avgknots) + unit);
        for (int i = 0; i < knots.size(); i++) {

            series.getData().add(new XYChart.Data(years[i], knots.get(i)));
            avgseries.getData().add(new XYChart.Data(years[i], avgknots));
        }


        sc.getData().addAll(series, avgseries);
    }


    public static void main(String[] args) {
        MainActivity.A.a();


    }


}
