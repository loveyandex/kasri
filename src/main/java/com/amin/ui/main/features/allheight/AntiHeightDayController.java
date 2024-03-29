package com.amin.ui.main.features.allheight;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.jsons.Date;
import com.amin.jsons.Features;
import com.amin.jsons.FormInfo;
import com.amin.jsons.UnitConvertor;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.main.main.Charting;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */
public class AntiHeightDayController implements Initializable {

    public JFXSlider lowYearjfxslider;
    public JFXSlider highYearjfxslider;
    public HBox topOfgobtn;
    public JFXComboBox featuresCombo;
    public JFXComboBox unitsCombo;
    public GridPane rootNode;
    public JFXButton cancelBtn;
    public JFXButton Gobtn;
    public VBox vvv;
    public JFXComboBox monthCombo;
    public JFXComboBox dayofMonthCombo;
    public JFXCheckBox z00;
    public JFXTabPane jfxtab;
    public FormInfo formInfo;
    @FXML
    CalendarPicker<PersianCalendar> persianCalendarCalendarPicker;
    private ArrayList<IOException> ioExceptions = new ArrayList<>();
    @FXML
    private JFXComboBox<Label> stationsCombo;
    @FXML
    private JFXComboBox<Label> countriesCombo;
    private Map<String, String> stationNumTOCities;


    private ArrayList<ArrayList<ArrayList<Double>>> AllfeatureAndYear = new ArrayList<ArrayList<ArrayList<Double>>>();

    public static Features getFeatures(String featureName) {
        if (featureName.equals(Features.PRES.getName()))
            return Features.PRES;
        else if (featureName.equals(Features.HGHT.getName()))
            return Features.HGHT;
        else if (featureName.equals(Features.TEMP.getName()))
            return Features.TEMP;
        else if (featureName.equals(Features.DWPT.getName()))
            return Features.DWPT;
        else if (featureName.equals(Features.RELH.getName()))
            return Features.RELH;
        else if (featureName.equals(Features.MIXR.getName()))
            return Features.MIXR;
        else if (featureName.equals(Features.DRCT.getName()))
            return Features.DRCT;
        else if (featureName.equals(Features.SKNT.getName()))
            return Features.SKNT;
        else if (featureName.equals(Features.THTA.getName()))
            return Features.THTA;
        else if (featureName.equals(Features.THTE.getName()))
            return Features.THTE;
        else if (featureName.equals(Features.THTV.getName()))
            return Features.THTV;
        else
            return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        formInfo = new FormInfo();

        String[] featursName = {"PRES", "HGHT", "TEMP", "DWPT", "RELH", "MIXR", "DRCT", Features.SKNT.getName(), "THTA", "THTE", "THTV"};
        for (int i = 0; i < featursName.length; i++) {
            featuresCombo.getItems().add(new Label(featursName[i]));
        }

        featuresCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            String feaureName = ((Label) newValue).getText();
            formInfo.setFeaureName(feaureName);
            unitsCombo.getItems().clear();
            formInfo.setFeatureUnit(null);
            if (feaureName.equals(Features.PRES.getName())) {
                UnitConvertor.PRES units = UnitConvertor.PRES.units;
                formInfo.setFeatureUnit("hPa");
                unitsCombo.getItems().add(new Label("hPa"));
                unitsCombo.getItems().add(new Label(units.getPascal().getSymbol()));
                unitsCombo.getItems().add(new Label(units.getAtmosphere().toString()));
                unitsCombo.getItems().add(new Label(units.getBar().toString()));
                unitsCombo.getItems().add(new Label(units.getMillimeterOfMercury().toString()));
                unitsCombo.valueProperty().setValue(unitsCombo.getItems().get(0));
            } else if (feaureName.equals(Features.HGHT.getName())) {
                UnitConvertor.HGHT units = UnitConvertor.HGHT.units;
                formInfo.setFeatureUnit(units.getMeter().toString());

                unitsCombo.getItems().add(new Label(units.getMeter().toString()));
                unitsCombo.getItems().add(new Label(units.getFoot().toString()));
                unitsCombo.getItems().add(new Label(units.getMile().toString()));
                unitsCombo.getItems().add(new Label(units.getYard().toString()));
                unitsCombo.getItems().add(new Label(units.getInch().toString()));
                unitsCombo.valueProperty().setValue(unitsCombo.getItems().get(0));

            } else if (feaureName.equals(Features.TEMP.getName())
                    || feaureName.equals(Features.DWPT.getName())
                    || feaureName.equals(Features.THTA.getName())
                    || feaureName.equals(Features.THTE.getName())
                    || feaureName.equals(Features.THTV.getName())) {
                UnitConvertor.TEMP units = UnitConvertor.TEMP.units;
                final String featureUnit = units.getCelsius().toString();
                System.out.println(featureUnit);
                formInfo.setFeatureUnit(featureUnit);

                unitsCombo.getItems().add(new Label(units.getCelsius().toString()));
                unitsCombo.getItems().add(new Label(units.getKelvin().getSymbol()));
                unitsCombo.getItems().add(new Label(units.getFahrenheit().toString()));
                unitsCombo.getItems().add(new Label(units.getRankine().toString()));
                unitsCombo.valueProperty().setValue(unitsCombo.getItems().get(0));

            } else if (feaureName.equals(Features.SKNT.getName())) {
                UnitConvertor.SPEED units = UnitConvertor.SPEED.units;
                formInfo.setFeatureUnit(units.getMetersPerSecond().toString());

                unitsCombo.getItems().add(new Label(units.getMetersPerSecond().toString()));
                unitsCombo.getItems().add(new Label(units.getKnot().toString()));
                unitsCombo.getItems().add(new Label(units.getKilometresPerHour().toString()));
                unitsCombo.getItems().add(new Label(units.getMilesPerHour().toString()));
                unitsCombo.getItems().add(new Label(units.getMach().toString()));
                unitsCombo.valueProperty().setValue(unitsCombo.getItems().get(0));

            } else if (feaureName.equals(Features.DRCT.getName())) {
                UnitConvertor.DRCT units = UnitConvertor.DRCT.units;
                formInfo.setFeatureUnit(units.getDegreeAngle().toString());
                System.out.println(units.getDegreeAngle().toString());

                unitsCombo.getItems().add(new Label(units.getDegreeAngle().toString()));
                unitsCombo.getItems().add(new Label(units.getRadian().getSymbol()));
                unitsCombo.getItems().add(new Label(units.getGrade().toString()));
                unitsCombo.getItems().add(new Label(units.getMinuteAngle().toString()));
                unitsCombo.getItems().add(new Label(units.getSecondAngle().toString()));
                unitsCombo.getItems().add(new Label(units.getRevolution().toString()));
                unitsCombo.valueProperty().setValue(unitsCombo.getItems().get(0));

            } else if (feaureName.equals(Features.RELH.getName())) {
                formInfo.setFeatureUnit("%");
                unitsCombo.getItems().add(new Label("%"));
                unitsCombo.valueProperty().setValue(unitsCombo.getItems().get(0));

            } else if (feaureName.equals(Features.MIXR.getName())) {
                formInfo.setFeatureUnit("g/kg");
                unitsCombo.getItems().add(new Label("g/kg"));
                unitsCombo.valueProperty().setValue(unitsCombo.getItems().get(0));

            }


            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
            else
                Gobtn.setDisable(true);
        });

        unitsCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null)
                formInfo.setFeatureUnit(((Label) newValue).getText());
            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
            else
                Gobtn.setDisable(true);
        });


        lowYearjfxslider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int a = (int) Math.round((Double) newValue);
            formInfo.setLowerYear(a);
            highYearjfxslider.valueProperty().setValue(newValue);
            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
        });

        highYearjfxslider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int a = (int) Math.round((Double) newValue);
            formInfo.setHighYear(a);

            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);

        });


        GridPane.setMargin(monthCombo, new Insets(0, 0, 30, 0));

        ArrayList<String> persianMonths = new ArrayList<String>(Arrays.asList("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "ابان", "اذر", "دی", "بهمن", "اسفند"));
        Map<String, Integer> persianMapMonth = new HashMap<>();

        for (int j = 0; j < persianMonths.size(); j++) {
            monthCombo.getItems().add(new Label((persianMonths.get(j))));
            persianMapMonth.put(persianMonths.get(j), j + 1);
        }


        monthCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            formInfo.setDate(null);
            dayofMonthCombo.getItems().clear();
            int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
            for (int i = 1; i <= 31; i++) {
                dayofMonthCombo.getItems().add(new Label(String.valueOf(i)));
            }
            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
            else
                Gobtn.setDisable(true);

        });
        dayofMonthCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                formInfo.setDate(null);
                persianCalendarCalendarPicker = CalendarPicker.persianWithSystemDefaults();
                String text = ((Label) monthCombo.getValue()).getText();
                Integer intmonth = persianMapMonth.get(text);

                try {
                    persianCalendarCalendarPicker.valueProperty().setValue(PersianCalendar.of(1372, intmonth, (Integer.parseInt(((Label) newValue).getText()))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException ex) {
                    Dialog.createExceptionDialog(ex);
                }
//                System.out.println(persianCalendarCalendarPicker.valueProperty().getValue().getMonth());
                PersianCalendar persianCalendar = persianCalendarCalendarPicker.valueProperty().getValue();
                PlainDate plainDate = persianCalendar.transform(PlainDate.class);
                System.out.println(String.format("%s-%s-%s", plainDate.getDayOfMonth(), plainDate.getMonth(), plainDate.getYear()));
                formInfo.setDate(new Date(plainDate.getMonth(), plainDate.getDayOfMonth(), plainDate.getYear()));
            }

            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);

        });


        try {
            ArrayList<String> countriesName = Mapping.getFileLines(C.COUNTRIES_CONFIG_PATH);
            countriesName.forEach(countryName -> countriesCombo.getItems().add(new Label(countryName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        countriesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            stationsCombo.getItems().clear();
            try {
                formInfo.setStationNumber(null);
                formInfo.setCountry(newValue.getText());

                String dirpath = C.STATES_PATH;;
                String fileName = newValue.getText() + ".conf";

                File dir = new File(dirpath);
                dir.mkdirs();
                File fileTosave = new File(dir, fileName);
                if (!fileTosave.exists())
                    Mapping.createCSVFILEFORStations(dirpath, fileName);

                stationNumTOCities = Mapping.
                        MapStationNumTOCities(dirpath+File.separator+ newValue.getText() + ".conf.csv");


                for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                    if (!station.getValue().equals("&"))
                        stationsCombo.getItems().add(new Label(station.getKey()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
            else
                Gobtn.setDisable(true);


        });

        stationsCombo.setMinWidth(200);
        countriesCombo.setMinWidth(200);

        stationsCombo.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                    if (station.getKey().equals(newValue.getText()))
                        formInfo.setStationNumber(station.getValue());

                }
                formInfo.setStationName(newValue.getText());

                if (isReadyToFire(formInfo))
                    Gobtn.setDisable(false);
            }
        });

        GridPane.setHalignment(stationsCombo, HPos.CENTER);

        cancelBtn.pressedProperty().addListener(observable -> {
        });

        cancelBtn.setOnAction(event -> {
            cancelBtn.getParent().getScene().getWindow().hide();

        });
        cancelBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                cancelBtn.getOnAction().handle(null);
        });

        Gobtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                Gobtn.getOnAction().handle(null);
        });


        Gobtn.setOnAction(event -> {
            if (formInfo.getHighYear().intValue() < formInfo.getLowerYear().intValue())
                Dialog.SnackBar.showSnack(rootNode, "high year is lower than low year");
            else {
                try {
                    showChartAndAna();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        });


    }

    private void showChartAndAna() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
                ArrayList<ArrayList<Double>> yearsUseful = new ArrayList<ArrayList<Double>>();
                try {

                    heightAndFeature = charting.addSeriesToChart(featureName
                            , fileName.replaceAll(".csv", ""),
                            rootDir + File.separator + fileName, 1, featureIndexCSV, featureName, unit);

                    int finalI = i;
                    yearsUseful.add(new ArrayList<Double>() {{
                        add((double) finalI);
                    }});

                    AllfeatureAndYear.add(yearsUseful);
                    AllfeatureAndYear.add(heightAndFeature);


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
        if (!ioExceptions.isEmpty()) {
            Dialog.createIOExceptionDialog(ioExceptions);
            ioExceptions.clear();
        }

        try {


            final VBox vbox = new VBox();
            vbox.setLayoutY(300);
            vbox.setLayoutX(400);
            vbox.setStyle("-fx-background-color: #fff");
            vbox.getChildren().addAll(sc);

            Parent root = FXMLLoader.load(AntiHeightDayController.class.getResource("/com/amin/ui/main/features/allheight/chart.fxml"));
            ((VBox) root).getChildren().add(vbox);
            StageOverride stage = new StageOverride();
            stage.setTitle("statistical analysis");

            SceneJson sceneJson = new SceneJson<ArrayList>(root, 400, 300);


            sceneJson.setJson(AllfeatureAndYear);

            stage.setScene(sceneJson);
            stage.show();

        } catch (IOException e) {
            Dialog.createExceptionDialog(e);
        }


    }

    private Features getfeatureIndex(String featureName) {
        return getFeatures(featureName);

    }

    private boolean isReadyToFire(FormInfo formInfo) {
        if (formInfo.getFeatureUnit() == null || formInfo.getFeaureName() == null || formInfo.getLowerYear() == null || formInfo.getHighYear() == null || formInfo.getDate() == null || formInfo.getStationNumber() == null || formInfo.getCountry() == null) {
            Gobtn.setDisable(true);
            return false;
        } else
            return true;
    }


}
