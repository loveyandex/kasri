package com.amin.ui.main.main;

import com.amin.analysis.oldmapping.OldMapping;
import com.amin.jsons.Features;
import com.amin.jsons.UnitConvertor;
import com.amin.ui.dialogs.Dialog;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import javax.measure.Measure;
import javax.measure.quantity.Pressure;
import javax.measure.unit.AlternateUnit;
import javax.measure.unit.Unit;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * is created by aMIN on 7/5/2018 at 16:09
 */
public class Charting {
    public static String LINE_CHART = "line";
    public static String SCATTER_CHART = "ScatterChart";

    public Charting() {
    }

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
        ArrayList<ArrayList<String>> windSpeedCol = OldMapping.getCol1Col2(dayfilePath, col1, col2);
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


    public ArrayList<ArrayList<Double>> addSeriesToChart(String title, String seriesName, String dayfilePath, int col1, int col2
            , String featurename, String unitname) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException

    {
        ArrayList<ArrayList<Double>> arrayListArrayList = new ArrayList<>();
        int cti = convertTogether(featurename, unitname);
        Method method;
        method = Charting.class.getMethod("conv" + cti, double.class);
        sc.setTitle(title);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesName);
        ArrayList<ArrayList<String>> col1Col2 = OldMapping.getCol1Col2(dayfilePath, col1, col2);
        for (int j = 2; j < col1Col2.size() - 1; j++) {
            if (!col1Col2.get(j).get(0).equals("NULL") && !col1Col2.get(j).get(1).equals("NULL")) {
                ArrayList<Double> doubleArrayList = new ArrayList<>(2);
                double v0 = Double.parseDouble(col1Col2.get(j).get(0));
                double v1 = Double.parseDouble(col1Col2.get(j).get(1));
                Double invokeDouble = ((Double) method.invoke(this, v1));
                doubleArrayList.add(v0);
                doubleArrayList.add(invokeDouble);

                series1.getData().add(new XYChart.Data(v0, ((double) invokeDouble)));
                arrayListArrayList.add(doubleArrayList);

            }
        }
        sc.getData().addAll(series1);
        return arrayListArrayList;
    }


    public void addSeriesToChart(ArrayList<ArrayList<Double>> doublescol1col2, String title, String seriesName,
                                 String featurename, String unitname)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int cti = convertTogether(featurename, unitname);
        Method method;
        method = Charting.class.getMethod("conv" + cti, double.class);
        sc.setTitle(title);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(seriesName);
        for (int j = 0; j < doublescol1col2.size() ; j++) {
                double v0 = (doublescol1col2.get(j).get(0));
                double v1 = (doublescol1col2.get(j).get(1));
                Double invokeDouble = ((Double) method.invoke(this, v1));
                series1.getData().add(new XYChart.Data(v0, invokeDouble));
        }
        sc.getData().addAll(series1);
    }


    public Charting(String featurename, String unitname) {
        arrayListArrayList = new ArrayList<>();
        windSpeedCol = new ArrayList<>();
        int cti = convertTogether(featurename, unitname);

        try {
            method = Charting.class.getMethod("conv" + cti, double.class);
        } catch (NoSuchMethodException e) {
            Dialog.createExceptionDialog(e);
        }

    }

    private Method method;
    private ArrayList<ArrayList<Double>> arrayListArrayList;

    private ArrayList<ArrayList<String>> windSpeedCol;

    public ArrayList<ArrayList<Double>> getcol1col2daydata(String dayfilePath, int col1, int col2) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        arrayListArrayList.clear();
        windSpeedCol.clear();
        windSpeedCol = OldMapping.getCol1Col2(dayfilePath, col1, col2);

        for (int j = 2; j < windSpeedCol.size() - 1; j++) {
            if (!windSpeedCol.get(j).get(0).equals("NULL") && !windSpeedCol.get(j).get(1).equals("NULL")) {
                try {
                    ArrayList<Double> doubleArrayList=new ArrayList<>(2);
                    double v0 = Double.parseDouble(windSpeedCol.get(j).get(0));
                    double v1 = Double.parseDouble(windSpeedCol.get(j).get(1));
                    Double invokeDouble = ((Double) method.invoke(this, v1));
                    doubleArrayList.add(v0);
                    doubleArrayList.add(invokeDouble);
                    arrayListArrayList.add(doubleArrayList);
                }catch (NumberFormatException e){
                    System.out.println(dayfilePath);
                }

            }
        }
        return arrayListArrayList;
    }











    public int convertTogether(String featurename, String unitname) {
        if (featurename.equals(Features.PRES.getName())) {

            if (unitname.equals("hPa")) {
                return 2;

            } else if (unitname.equals(UnitConvertor.PRES.units.getPascal().getSymbol())) {
                return 3;

            } else if (unitname.equals(UnitConvertor.PRES.units.getAtmosphere().toString())) {
                return 4;
            } else if (unitname.equals(UnitConvertor.PRES.units.getBar().toString())) {
                return 5;
            } else if (unitname.equals(UnitConvertor.PRES.units.getMillimeterOfMercury().toString())) {
                return 6;
            }


        } else if (featurename.equals(Features.SKNT.getName())) {

            if (unitname.equals(UnitConvertor.SPEED.units.getMetersPerSecond().toString())) {
                return 7;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getKnot().toString())) {
                return 8;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getKilometresPerHour().toString())) {
                return 9;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getMach().toString())) {
                return 10;
            } else if (unitname.equals(UnitConvertor.SPEED.units.getMilesPerHour().toString())) {
                return 11;
            }

        } else if (featurename.equals(Features.DRCT.getName())) {
            if (unitname.equals(UnitConvertor.DRCT.units.getRadian().getSymbol())) {
                return 12;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getDegreeAngle().toString())) {
                return 13;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getGrade().toString())) {
                return 14;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getMinuteAngle().toString())) {
                return 15;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getSecondAngle().toString())) {
                return 16;
            } else if (unitname.equals(UnitConvertor.DRCT.units.getRevolution().toString())) {
                return 17;
            }

        } else if (featurename.equals(Features.TEMP.getName()) || featurename.equals(Features.DWPT.getName())) {
            if (unitname.equals(UnitConvertor.TEMP.units.getCelsius().toString())) {
                return 18;

            } else if (unitname.equals(UnitConvertor.TEMP.units.getKelvin().toString())) {
                return 19;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getFahrenheit().toString())) {
                return 20;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getRankine().toString())) {
                return 21;
            }
        } else if (featurename.equals(Features.HGHT.getName())) {
            if (unitname.equals(UnitConvertor.HGHT.units.getMeter().toString())) {
                return 22;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getFoot().toString())) {
                return 23;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getMile().toString())) {
                return 24;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getYard().toString())) {
                return 25;
            } else if (unitname.equals(UnitConvertor.HGHT.units.getInch().toString())) {
                return 26;
            }

        } else if (featurename.equals(Features.MIXR.getName())) {
            return 1;

        } else if (featurename.equals(Features.RELH.getName())) {
            return 1;
        } else if (featurename.equals(Features.THTA.getName()) || featurename.equals(Features.THTE.getName()) || featurename.equals(Features.THTV.getName())) {
            if (unitname.equals(UnitConvertor.TEMP.units.getCelsius().toString())) {
                return 27;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getKelvin().toString())) {
                return 28;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getFahrenheit().toString())) {
                return 29;
            } else if (unitname.equals(UnitConvertor.TEMP.units.getRankine().toString())) {
                return 30;
            }
        }

        return 0;
    }


    public double conv1(double v1) {
        return v1;
    }

    public double conv2(double v1) {
        return (v1);
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
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getCelsius())
                .doubleValue(UnitConvertor.TEMP.units.getCelsius());
    }

    public double conv19(double v1) {
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getCelsius())
                .doubleValue(UnitConvertor.TEMP.units.getKelvin());
    }

    public double conv20(double v1) {
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getCelsius())
                .doubleValue(UnitConvertor.TEMP.units.getFahrenheit());
    }

    public double conv21(double v1) {
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getCelsius())
                .doubleValue(UnitConvertor.TEMP.units.getRankine());
    }

    public double conv22(double v1) {
        return Measure.valueOf(v1, UnitConvertor.HGHT.units.getMeter())
                .doubleValue(UnitConvertor.HGHT.units.getMeter());

    }

    public double conv23(double v1) {
        return Measure.valueOf(v1, UnitConvertor.HGHT.units.getMeter())
                .doubleValue(UnitConvertor.HGHT.units.getFoot());

    }

    public double conv24(double v1) {
        return Measure.valueOf(v1, UnitConvertor.HGHT.units.getMeter())
                .doubleValue(UnitConvertor.HGHT.units.getMile());
    }

    public double conv25(double v1) {
        return Measure.valueOf(v1, UnitConvertor.HGHT.units.getMeter())
                .doubleValue(UnitConvertor.HGHT.units.getYard());
    }

    public double conv26(double v1) {
        return Measure.valueOf(v1, UnitConvertor.HGHT.units.getMeter())
                .doubleValue(UnitConvertor.HGHT.units.getInch());
    }

    public double conv27(double v1) {
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getKelvin())
                .doubleValue(UnitConvertor.TEMP.units.getCelsius());
    }

    public double conv28(double v1) {
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getKelvin())
                .doubleValue(UnitConvertor.TEMP.units.getKelvin());
    }

    public double conv29(double v1) {
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getKelvin())
                .doubleValue(UnitConvertor.TEMP.units.getFahrenheit());
    }

    public double conv30(double v1) {
        return Measure.valueOf(v1, UnitConvertor.TEMP.units.getKelvin())
                .doubleValue(UnitConvertor.TEMP.units.getRankine());
    }


    public void interpolateChart(String title, String seriesName, ArrayList<Double> knots, ArrayList<Integer> years, String avgseriname, String unit) {
        sc.setTitle(title);
        XYChart.Series series = new XYChart.Series();
        XYChart.Series avgseries = new XYChart.Series();
        series.setName(seriesName);
        double sum = 0.0;
        for (int i = 0; i < knots.size(); i++) {
            sum += knots.get(i);
        }
        int size = knots.size();
        System.out.println("size of "+size);
        double avgknots = sum / size;
        avgseries.setName(avgseriname + String.format("%.4f ", avgknots) + unit);
        for (int i = 0; i < knots.size(); i++) {

            series.getData().add(new XYChart.Data(years.get(i), knots.get(i)));
            avgseries.getData().add(new XYChart.Data(years.get(i), avgknots));
        }


        sc.getData().addAll(series, avgseries);
    }

}
