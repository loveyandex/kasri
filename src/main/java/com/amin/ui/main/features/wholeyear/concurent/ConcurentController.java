package com.amin.ui.main.features.wholeyear.concurent;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.io.MyWriter;
import com.amin.io.UTF8Reader;
import com.amin.jsons.Features;
import com.amin.jsons.OtherFormInfo;
import com.amin.jsons.UnitConvertor;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.main.features.StaticFunctions;
import com.amin.ui.main.main.Charting;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;
import org.controlsfx.control.RangeSlider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Stream;

import static com.amin.ui.main.features.allheight.AntiHeightDayController.getFeatures;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */

public class ConcurentController extends StaticFunctions implements Initializable {

    public RangeSlider yearsSlider;
    public JFXSlider lowYearjfxslider;
    public JFXSlider highYearjfxslider;
    public JFXComboBox featuresCombo;
    public JFXComboBox unitsCombo;
    public JFXButton savebtn;
    public ProgressIndicator progressbar;
    public GridPane rootNode;
    public JFXButton cancelBtn;
    public JFXButton Gobtn;
    public TextField height;
    public VBox vvv;
    public JFXComboBox monthCombo;
    public JFXComboBox dayofMonthCombo;
    public JFXCheckBox z00;
    public JFXTabPane jfxtab;
    public OtherFormInfo formInfo;
    boolean[] concurentFinished = new boolean[]{false, false, false, false};
    @FXML
    CalendarPicker<PersianCalendar> persianCalendarCalendarPicker;
    private ArrayList<IOException> ioExceptions = new ArrayList<>();
    @FXML
    private JFXComboBox<Label> stationsCombo;
    @FXML
    private JFXComboBox<Label> countriesCombo;
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


        GridPane.setMargin(monthCombo, new Insets(0, 0, 30, 0));

        ArrayList<String> persianMonths = new ArrayList<String>(Arrays.asList("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "ابان", "اذر", "دی", "بهمن", "اسفند"));
        Map<String, Integer> persianMapMonth = new HashMap<>();

        for (int j = 0; j < persianMonths.size(); j++) {
            monthCombo.getItems().add(new Label((persianMonths.get(j))));
            persianMapMonth.put(persianMonths.get(j), j + 1);
        }


        monthCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            dayofMonthCombo.getItems().clear();
            int[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
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
                } catch (IllegalArgumentException ex) {
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

                String dirpath = C.STATES_PATH;
                String fileName = newValue.getText() + ".conf";

                File dir = new File(dirpath);
                dir.mkdirs();
                File fileTosave = new File(dir, fileName);
                if (!fileTosave.exists())
                    Mapping.createCSVFILEFORStations(dirpath, fileName);

                stationNumTOCities = Mapping.
                        MapStationNumTOCities(C.STATES_PATH +"/" + newValue.getText() + ".conf.csv");


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
            if (event.getCode() == KeyCode.ENTER)
                cancelBtn.getOnAction().handle(null);
        });

        Gobtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                Gobtn.getOnAction().handle(null);
        });


        Gobtn.setOnAction(event -> {
            if (formInfo.getHighYear().intValue() < formInfo.getLowerYear().intValue())
                Dialog.SnackBar.showSnack(rootNode, "high year is lower than low year!!");
            else {
                System.out.println("starting");

                new Thread(() -> {
                    try {
                        Gobtn.setDisable(true);
                        progressbar.setVisible(true);
                        showChartAndAna(1, 3, "_1", 1);
                        Gobtn.setDisable(false);
                        progressbar.setVisible(false);
                        if (concurentFinished[0] && concurentFinished[1]
                                && concurentFinished[2] && concurentFinished[3]) {
                            synchronized (this) {
                                notify();
                            }
                        }
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
                new Thread(() -> {
                    try {
                        Gobtn.setDisable(true);
                        progressbar.setVisible(true);
                        showChartAndAna(4, 6, "_2", 2);
                        Gobtn.setDisable(false);
                        progressbar.setVisible(false);
                        if (concurentFinished[0] && concurentFinished[1]
                                && concurentFinished[2] && concurentFinished[3]) {
                            synchronized (this) {
                                notify();
                            }
                        }
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
                new Thread(() -> {
                    try {
                        Gobtn.setDisable(true);
                        progressbar.setVisible(true);
                        showChartAndAna(7, 9, "_3", 3);
                        Gobtn.setDisable(false);
                        progressbar.setVisible(false);
                        if (concurentFinished[0] && concurentFinished[1]
                                && concurentFinished[2] && concurentFinished[3]) {
                            synchronized (this) {
                                notify();
                            }
                        }

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
                new Thread(() -> {
                    try {
                        Gobtn.setDisable(true);
                        progressbar.setVisible(true);
                        showChartAndAna(10, 12, "_4", 4);
                        Gobtn.setDisable(false);
                        progressbar.setVisible(false);
                        if (concurentFinished[0] && concurentFinished[1]
                                && concurentFinished[2] && concurentFinished[3]) {
                            synchronized (this) {
                                notify();
                            }
                        }
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

                // last thread for reducing map
                new Thread(() -> {
                    System.out.println("king abolfazl thread manager and horn that");
                    synchronized (this) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            System.out.println("king abolfazl thread manager and horn that 2");

                            String childFileName = formInfo.getFeaureName() + "_" + formInfo.getHeight() + "_" + formInfo.getCountry() + ".csv";
                            formInfo.setChildFileName(childFileName);
                            MyWriter mainfile = new MyWriter(formInfo.getDirTOSave() + File.separator, formInfo.getChildFileName(), true);


                            UTF8Reader firstfile = new UTF8Reader(formInfo.getDirTOSave() + File.separator + formInfo.getChildFileName().replaceAll(".csv", "_1.csv"));
                            BufferedReader bf = firstfile.getIn();
                            Stream<String> lines = bf.lines();
                            lines.forEach(s -> {
                                try {
                                    mainfile.appendStringInNewLine(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                            UTF8Reader firstfile2 = new UTF8Reader(formInfo.getDirTOSave() + File.separator + formInfo.getChildFileName().replaceAll(".csv", "_2.csv"));
                            bf = firstfile2.getIn();
                            lines = bf.lines();
                            lines.forEach(s -> {
                                try {
                                    mainfile.appendStringInNewLine(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                            UTF8Reader firstfile3 = new UTF8Reader(formInfo.getDirTOSave() + File.separator + formInfo.getChildFileName().replaceAll(".csv", "_3.csv"));
                            bf = firstfile3.getIn();
                            lines = bf.lines();
                            lines.forEach(s -> {
                                try {
                                    mainfile.appendStringInNewLine(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                            UTF8Reader firstfile4 = new UTF8Reader(formInfo.getDirTOSave() + File.separator + formInfo.getChildFileName().replaceAll(".csv", "_4.csv"));
                            bf = firstfile4.getIn();
                            lines = bf.lines();
                            lines.forEach(s -> {
                                try {
                                    mainfile.appendStringInNewLine(s);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                            mainfile.close();
                            System.err.println("mainfile closed:))");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                    }else{
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }

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


        System.err.println("in bottom of init");
    }


    private void showChartAndAna(int initmonth, int endmonth, String endofFileName, int threadNumber) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        String childFileName = "";
        String pathDirToSave = System.getProperty("user.home") + "/Desktop/data";
        if (formInfo.getDirTOSave() != null)
            pathDirToSave = formInfo.getDirTOSave();
        else
            formInfo.setDirTOSave(pathDirToSave);

        int fromYear = formInfo.getLowerYear().intValue();
        int toYear = formInfo.getHighYear().intValue();
        String featureName = formInfo.getFeaureName();
        int featureIndexCSV = getfeatureIndex(featureName).getLevelCode() - 1;
        String unit = formInfo.getFeatureUnit();

        String country = formInfo.getCountry();
        ArrayList<String> stationNumberslist = formInfo.getStationNumbersList();
        ArrayList<String> stationNamesList = formInfo.getStationNamesList();
        String height = formInfo.getHeight();


        childFileName = formInfo.getFeaureName() + "_" + height + "_" + formInfo.getCountry() + endofFileName + ".csv";
        formInfo.setChildFileName(childFileName);
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


        for (int monthInt = initmonth; monthInt <= endmonth; monthInt++) {
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
                            if (!new File(rootDir, fileName).exists())
                                continue;

                            try {
                                heightAndFeature = charting.getcol1col2daydata(rootDir + File.separator + fileName
                                        , 1, featureIndexCSV);

                                Double intrapolateFeature = intrapolateinAllFeature(Double.parseDouble(height), heightAndFeature);
                                if (intrapolateFeature != null)
                                    writerw.appendStringInFile(String.format(
                                            "%d,%s,%s,%s,%f,%s,%s,%s,%s\n", year, Z, stationNamesList.get(counterforStations)
                                            , stationNumber, intrapolateFeature, unit, String.valueOf(year), monthDisp, dayOfMonth));


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
                String format = String.format("Each process took an average of %f s  in thread of %s %n",
                        t, endofFileName);
                System.out.printf(format);
            }
        }

        writerw.close();
        concurentFinished[threadNumber - 1] = true;
        long time = System.nanoTime() - start;
        double t = time / 1e9d;
        String format = String.format("took time %f s in thread of %s %n", t, endofFileName);
        System.err.printf(format);

        Platform.runLater(() -> {
            Dialog.SnackBar.showSnack(rootNode, format, 5000);
        });
    }


    private Features getfeatureIndex(String featureName) {
        return getFeatures(featureName);

    }


    private boolean isReadyToFire(OtherFormInfo formInfo) {
        if (formInfo.getFeatureUnit() == null || formInfo.getFeaureName() == null || formInfo.getLowerYear() == null || formInfo.getHighYear() == null || formInfo.getStationNamesList().isEmpty() || formInfo.getCountry() == null || formInfo.getHeight() == null) {
            Gobtn.setDisable(true);
            return false;
        } else
            return true;
    }


}
