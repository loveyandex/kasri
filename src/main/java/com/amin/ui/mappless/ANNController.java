package com.amin.ui.mappless;

import com.amin.jsons.Date;
import com.amin.jsons.Features;
import com.amin.jsons.FormInfo;
import com.amin.jsons.UnitConvertor;
import com.amin.knn.ANN;
import com.amin.neuralNetwork.regression.load.AminLevenberg;
import com.amin.pojos.LatLon;
import com.amin.ui.SceneJson;
import com.amin.ui.dialogs.Dialog;
import com.google.gson.Gson;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;
import org.controlsfx.control.RangeSlider;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.Format;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static com.amin.knn.ANN.*;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */
public class ANNController implements Initializable {

    private LatLon latLon;

    public RangeSlider yearsSlider;
    public JFXSlider lowYearjfxslider;
    public JFXSlider highYearjfxslider;
    public HBox topOfgobtn;
    public JFXComboBox featuresCombo;
    public JFXComboBox unitsCombo;

    public GridPane rootNode;


    public JFXButton cancelBtn;
    public JFXButton Gobtn;

    public TextField height;
    public VBox vvv;
    public JFXComboBox monthCombo;
    public JFXComboBox dayofMonthCombo;
    public JFXCheckBox z00;
    public JFXTabPane jfxtab;
    public FormInfo formInfo;
    @FXML
    CalendarPicker<PersianCalendar> persianCalendarCalendarPicker;
    @FXML
    private RangeSlider hSlider;
    private double max_radius_kml = 100;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            ArrayList jsons = (ArrayList) ((SceneJson) vvv.getScene()).getJson();
            latLon = ((LatLon) jsons.get(0));
            max_radius_kml = ((double) jsons.get(1));
            System.err.println(new Gson().toJson(jsons));
        });


        formInfo = new FormInfo();


        String[] featursName = {"PRES", "HGHT", "TEMP", "DWPT", "RELH", "MIXR", "DRCT", Features.SKNT.getName(), "THTA", "THTE", "THTV"};
        Arrays.sort(featursName);

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
                ;
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


        cancelBtn.pressedProperty().addListener(observable -> {
        });

        cancelBtn.setOnAction(event -> {
            ((cancelBtn)).getParent().getScene().getWindow().hide();

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
                String function = String.format("onday $  %d  %d  %s  %s  %s %d  %d $",
                        formInfo.getDate().Month,
                        formInfo.getDate().Day,
                        formInfo.getFeaureName(),
                        formInfo.getFeatureUnit(),
                        formInfo.getHeight(),
                        formInfo.getLowerYear().intValue()
                        , formInfo.getHighYear().intValue());

                if (function.contains("%"))
                    function = function.replaceAll("%", "PERCENT");


                function = function.replaceAll("\\$", "%s");
                annSolve(function);
            }


        });


        height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(""))
                formInfo.setHeight(null);
            else
                formInfo.setHeight(newValue);
            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
        });


        hSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            height.textProperty().setValue(String.valueOf(Math.round(newValue.doubleValue())));
        });

        height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
        });


    }


    public void annSolve(String fncScript) {
        try {
            ANN.IranAnn(max_radius_kml, latLon, (temps, latLons) -> {
                double[] outi = new double[temps.size()];
                double[] inp1 = new double[temps.size()];
                double[] inp2 = new double[temps.size()];
                for (int i = 0; i < temps.size(); i++) {
                    outi[i] = temps.get(i);
                    inp1[i] = latLons.get(i).getLat();
                    inp2[i] = latLons.get(i).getLogn();
                }
                final Stage primaryStage = new Stage();
                final BasicMLDataSet dataset = ANN.dataset(inp1, inp2, outi);
                final BasicNetwork network = AminLevenberg.netAndTrain(dataset, train -> {
                    try {
                        new WhenTrainingView(console -> {
                            System.out.println("Beginning training...");
                            double error = 1e-6d;
                            int epoch = 1;
                            do {
                                train.iteration();
                                console.appendText("Iteration #" + Format.formatInteger(epoch)
                                        + " Error:" + Format.formatPercent(train.getError())
                                        + " Target Error: " + Format.formatPercent(error) + "\n");
                                epoch++;
                            } while ((train.getError() > error) && !train.isTrainingDone());
                            train.finishTraining();

                        }).start(primaryStage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                primaryStage.setOnHidden(event -> {

                    double[] inps = new double[]{latLon.getLat() / MAX_LAT, latLon.getLogn() / MAX_LONG};
                    double[] ops = new double[1];

                    network.compute(inps, ops);
                    final double v = ops[0] * MAX_FITTNESS;
                    final String format = String.format("%.4f %s", v, formInfo.getFeatureUnit());
                    System.err.println(format);
                    Dialog.SnackBar.showSnack(rootNode, format, 4001);
                });


            }, fncScript);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private boolean isReadyToFire(FormInfo formInfo) {
        if (formInfo.getFeatureUnit() == null || formInfo.getFeaureName() == null || formInfo.getLowerYear() == null || formInfo.getHighYear() == null || formInfo.getDate() == null || formInfo.getHeight() == null) {
            Gobtn.setDisable(true);
            return false;
        } else
            return true;
    }


}
