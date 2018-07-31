package com.amin.ui.main.features.wholeyear;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.jsons.Features;
import com.amin.jsons.OtherFormInfo;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;
import org.controlsfx.control.RangeSlider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */

public class WholeYearAllStationsOfCountryController implements Initializable {

    public RangeSlider yearsSlider;
    public JFXSlider lowYearjfxslider;
    public JFXSlider highYearjfxslider;
    public JFXComboBox featuresCombo;
    public JFXComboBox unitsCombo;
    public JFXButton savebtn;
    public ProgressIndicator progressbar;
    private ArrayList<IOException> ioExceptions = new ArrayList<>();

    public GridPane rootNode;

    @FXML
    private JFXComboBox<Label> stationsCombo;
    @FXML
    private JFXComboBox<Label> countriesCombo;
    public JFXButton cancelBtn;
    public JFXButton Gobtn;

    public TextField height;
    public VBox vvv;
    public JFXComboBox monthCombo;
    public JFXComboBox dayofMonthCombo;
    public JFXCheckBox z00;
    public JFXTabPane jfxtab;

    @FXML
    CalendarPicker<PersianCalendar> persianCalendarCalendarPicker;

    public OtherFormInfo formInfo;
    private Map<String, String> stationNumTOCities;

    @FXML
    private RangeSlider hSlider;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        formInfo = new OtherFormInfo();

        savebtn.setOnMouseClicked(event -> {
            final DirectoryChooser directoryChooser =
                    new DirectoryChooser();
            final File kasriDate =
                    directoryChooser.showDialog(rootNode.getScene().getWindow());
            if (kasriDate != null) {
                formInfo.setDirTOSave(kasriDate.getAbsolutePath());
            }
        });







        String[] featursName = {"PRES", "HGHT", "TEMP", "DWPT", "RELH", "MIXR", "DRCT", Features.SKNT.getName(), "THTA", "THTE", "THTV"};
        for (int i = 0; i < featursName.length; i++) {
            featuresCombo.getItems().add(new Label(featursName[i]));
        }
        featuresCombo.valueProperty().setValue(featuresCombo.getItems().get(7));

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
                formInfo.setFeatureUnit(units.getCelsius().toString());

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


        GridPane.setMargin(monthCombo,new Insets(0,0,30,0));

        ArrayList<String> persianMonths=new ArrayList<String>( Arrays.asList("فروردین", "اردیبهشت", "خرداد","تیر","مرداد","شهریور","مهر","ابان","اذر","دی","بهمن","اسفند"));
        Map<String, Integer> persianMapMonth = new HashMap<>();

        for (int j = 0; j < persianMonths.size(); j++) {
            monthCombo.getItems().add(new Label((persianMonths.get(j))));
            persianMapMonth.put(persianMonths.get(j), j + 1);
        }



        monthCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            dayofMonthCombo.getItems().clear();
            int[] days={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
            for (int i = 1; i <= 31; i++) {
                    dayofMonthCombo.getItems().add(new Label(String.valueOf(i)));
            }


        });
        dayofMonthCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                persianCalendarCalendarPicker = CalendarPicker.persianWithSystemDefaults();
                String text = ((Label) monthCombo.getValue()).getText();
                Integer intmonth = persianMapMonth.get(text);

                try {
                    persianCalendarCalendarPicker.valueProperty().setValue(PersianCalendar.of(1372, intmonth, (Integer.parseInt(((Label) newValue).getText()))));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }catch (IllegalArgumentException ex){
                    Dialog.createExceptionDialog(ex);
                }
//                System.out.println(persianCalendarCalendarPicker.valueProperty().getValue().getMonth());
            PersianCalendar persianCalendar = persianCalendarCalendarPicker.valueProperty().getValue();
            PlainDate plainDate = persianCalendar.transform(PlainDate.class);
                System.out.println(String.format("%s-%s-%s", plainDate.getDayOfMonth(), plainDate.getMonth(), plainDate.getYear()));
            }

        });


        try {
            ArrayList<String> countriesName = Mapping.getFileLines(C.COUNTRIES_CONFIG_PATH);
            countriesName.forEach(countryName -> countriesCombo.getItems().add(new Label(countryName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        formInfo.setStationNamesList(new ArrayList<>());
        formInfo.setStationNumbersList(new ArrayList<>());
        countriesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            formInfo.getStationNamesList().clear();
            formInfo.getStationNumbersList().clear();
            try {
                formInfo.setCountry(newValue.getText());

                String dirpath = "config";
                String fileName = newValue.getText() + ".conf";

                File dir = new File(dirpath);
                dir.mkdirs();
                File fileTosave = new File(dir, fileName);
                if (!fileTosave.exists())
                    Mapping.createCSVFILEFORStations(dirpath, fileName);

                stationNumTOCities = Mapping.
                        MapStationNumTOCities("config/" + newValue.getText() + ".conf.csv");



                for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                    if (!station.getValue().equals("&")) {
                        formInfo.getStationNamesList().add(station.getKey());
                        formInfo.getStationNumbersList().add(station.getValue());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isReadyToFire(formInfo))
                Gobtn.setDisable(false);
            else
                Gobtn.setDisable(true);


        });

//        stationsCombo.setPromptText("select station");
        stationsCombo.setMinWidth(200);
        countriesCombo.setMinWidth(200);


        GridPane.setHalignment(stationsCombo, HPos.CENTER);

        cancelBtn.pressedProperty().addListener(observable -> {
        });

        cancelBtn.setOnAction(event -> {
            ((cancelBtn)).getParent().getScene().getWindow().hide();

        });
        cancelBtn.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
                cancelBtn.getOnAction().handle(null);
        });

        Gobtn.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
                Gobtn.getOnAction().handle(null);
        });


        Gobtn.setOnAction(event -> {
            if (formInfo.getHighYear().intValue() < formInfo.getLowerYear().intValue())
                Dialog.SnackBar.showSnack(rootNode, "high year is lower than low year!!");
            else {
                new Thread(() -> {
                    try {
                        Gobtn.setDisable(true);
                        progressbar.setVisible(true);
                        showChartAndAna();
                        Gobtn.setDisable(false);
                        progressbar.setVisible(false);

                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        Dialog.createExceptionDialog(e);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

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


    private void showChartAndAna() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
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


        for (int monthInt = 1; monthInt <= 12; monthInt++) {
//        for (int monthInt = 12; monthInt >= 1; monthInt--) {
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
                                            , stationNumber, intrapolateFeature, unit,String.valueOf(year), monthDisp, dayOfMonth));


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

                long time = System.nanoTime() - start;
                double t = time / 1e9d;
                System.out.printf("Each XXXXX took an average of %f s%n", t);

            }

        }

        writerw.close();
        long time = System.nanoTime() - start;
        double t = time / 1e9d;
        System.out.printf("took time %f s%n", t);

        ArrayList<ArrayList<String>> colsData = Mapping.LatLong.getColsData(
                pathDirToSave + File.separator + childFileName, ","
                , 0, 1, 2, 3, 4, 5);

        javafx.application.Platform.runLater(() -> {


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("success");
            alert.setHeaderText("valid stations data saved in your path");
            alert.show();
            alert.setOnHiding(event -> {
                Stage primaryStage = new StageOverride();
                Parent root = null;
                try {
                    root = FXMLLoader.load(WholeYearAllStationsOfCountryController.this.getClass().getResource("/com/amin/ui/main/features/allstationsstatistic.fxml"));
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
        });


    }

    private Features getfeatureIndex(String featureName) {
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


    private double intrapolateKnot(String height, ArrayList<ArrayList<String>> heightAndKnotAll) {
        double knotdesire = -1;
        double heightdesire = Double.parseDouble(height);
        final Vector<Double> heigthsVector = new Vector<>();
        final Vector<Double> knotsVector = new Vector<>();

        heightAndKnotAll.forEach(strings -> {
            if (!strings.get(0).equals("HGHT")
                    && !strings.get(1).equals("SKNT")
                    && !strings.get(0).equals("m")
                    && !strings.get(1).equals("knot")
                    && !strings.get(0).equals("NULL")
                    && !strings.get(1).equals("NULL")


                    ) {
                double h = Double.parseDouble(strings.get(0));
                double knot = Double.parseDouble(strings.get(1));
                heigthsVector.add(h);
                knotsVector.add(knot);
            }
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







    private boolean isReadyToFire(OtherFormInfo formInfo) {
        if (formInfo.getFeatureUnit() == null || formInfo.getFeaureName() == null || formInfo.getLowerYear() == null || formInfo.getHighYear() == null || formInfo.getStationNamesList().isEmpty() || formInfo.getCountry() == null || formInfo.getHeight() == null) {
            Gobtn.setDisable(true);
            return false;
        } else
            return true;
    }




}
