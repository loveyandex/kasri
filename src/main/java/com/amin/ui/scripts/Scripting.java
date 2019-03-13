package com.amin.ui.scripts;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;
import com.amin.jsons.OtherFormInfo;
import com.amin.jsons.SomeDays;
import com.amin.scripting.Functions;
import com.amin.scripting.wind.Wind;
import com.amin.ui.SceneJson;
import com.amin.ui.dialogs.Dialog;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.amin.scripting.RestFuns.toFormInfo;
import static com.amin.scripting.RestFuns.toSomeDaysObject;

/**
 * is created by aMIN on 6/8/2018 at 04:43
 */
public class Scripting extends Application {
    private static String s;
    static private Map<String, String> stationNumTOCities;
    private BorderPane layout;

    private static void rem() {
        s = s.replaceAll("  ", " ");
        if (s.contains("  "))
            rem();
        else return;
    }



    public static void scripting(String cmd) {
        s = cmd;
        rem();
        cmd = s;
        System.out.println("f:\n" + cmd);
        final String[] args = cmd.split(" ");

        final String func = args[0];
        if (func.equals("onday"))
            runFopen(args, Functions.getInstance()::fopen);
        else if (func.equals("crosswind"))
            runFopen(args, Wind.getInstance()::crossWindOnDayOnStation);

        else if (func.equals("ondaystations"))
            runAllDay(args);
        else if (func.equals("somedays"))
            Functions.getInstance().someDays(toSomeDaysObject(args));

    }


    public static double scripting2(String cmd) {
        s = cmd;
        rem();
        cmd = s;
        System.out.println("f:\n" + cmd);
        final String[] args = cmd.split(" ");

        final String func = args[0];
        if (func.equals("onday")) {
            final double arrayLists = runFopen2(args);
            return arrayLists;
        } else if (func.equals("ondaystations"))
            runAllDay(args);


        return 0.0;
    }

    private static void runAllDay(String[] args) {

        if (args.length == 1)
            Dialog.createExceptionDialog(new RuntimeException("not arrgumet assigned"));
        else {

            final int month = Integer.parseInt(args[1]);
            final int day = Integer.parseInt(args[2]);
            final String featurename = args[3];
            final String unit = args[4];
            final String height = args[5];
            int loweryear = Integer.parseInt(args[6]);
            int highyear = Integer.parseInt(args[7]);
            final String country = args[8];
            OtherFormInfo otherFormInfo = new OtherFormInfo();
            otherFormInfo.setFeatureUnit(unit)
                    .setHeight(height).setDate(new Date(month, day, 1994))
                    .setCountry(country)
                    .setLowerYear(loweryear)
                    .setHighYear(highyear)
                    .setFeaureName(featurename)
                    .setStationNamesList(new ArrayList<>())
                    .setStationNumbersList(new ArrayList<>());

            try {

                String dirpath = C.STATES_PATH;
                String fileName = country + ".conf";

                File dir = new File(dirpath);
                dir.mkdirs();
                File fileTosave = new File(dir, fileName);
                if (!fileTosave.exists())
                    Mapping.createCSVFILEFORStations(dirpath, fileName);

                stationNumTOCities = Mapping.
                        MapStationNumTOCities(C.STATES_PATH + "/" + country + ".conf.csv");


                for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                    if (!station.getValue().equals("&")) {
                        otherFormInfo.getStationNamesList().add(station.getKey());
                        otherFormInfo.getStationNumbersList().add(station.getValue());
                    }
                }

                Functions.getInstance().fAllstationsonDay(otherFormInfo);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void runFopen(String[] args, Run run) {
        if (args.length == 1)
            Dialog.createExceptionDialog(new RuntimeException("not arrgumet assigned"));
        else {
            try {
                run.run(toFormInfo(args));

            } catch (Exception X) {
                Dialog.createExceptionDialog(new RuntimeException(X.toString()));
            }
        }
    }

    private static double runFopen2(String[] args) {
        if (args.length == 1)
            Dialog.createExceptionDialog(new RuntimeException("not arrgumet assigned"));
        else {
            final String stationNumber = args[1];
            final int month = Integer.parseInt(args[2]);
            final int day = Integer.parseInt(args[3]);

            final String featurename = args[4];
            final String unit = args[5];
            final String height = args[6];
            int loweryear = Integer.parseInt(args[7]);
            int highyear = Integer.parseInt(args[8]);
            final String country = args[9];
//            final FormInfo formInfo = new FormInfo(new Date(1,1,2017), Features.SKNT.getName(),
//                    "40800","",
//                    "iran__islamic_rep","9899",2017,2017
//                    , UnitConvertor.SPEED.units.getMetersPerSecond().toString());
            final FormInfo formInfo = new FormInfo(new Date(month, day, 1999), featurename,
                    stationNumber, "",
                    country, height, loweryear, highyear, unit);
            final double fopen2 = Functions.getInstance().fopen2(formInfo);
            return fopen2;

        }
        return 0.0;
    }

    public void start(Stage primaryStage) throws Exception {


        primaryStage.getIcons().add(new Image(getClass().getResource("/logo.png").toURI().toString()));
        MenuBar file = new MenuBar();
        file.setId("file");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                new MenuItem("..."),
                new MenuItem("..."));
        fileMenu.setId("#fileMenu");


        Menu editMenu = new Menu("samples");
        Menu onday = new Menu(" onday");
        Menu someDays = new Menu(" somedays");
        Menu crosswindmenu = new Menu("cross wind onday");
        Menu ondaystations = new Menu(" onday over the state");

        final MenuItem wind_speed = new MenuItem("wind speed");
        final MenuItem temp = new MenuItem("temp");
        final MenuItem DWPT = new MenuItem("DWPT");
        final MenuItem press = new MenuItem("press");
        final MenuItem relh = new MenuItem("RELH");
        final MenuItem mixr = new MenuItem("MIXR");
        final MenuItem drct = new MenuItem("DRCT");
        onday.getItems().addAll(
                wind_speed,
                temp,
                DWPT,
                press,
                relh,
                mixr,
                drct
        );
        editMenu.getItems().add(onday);


        final MenuItem wind_speedSome = new MenuItem("wind speed");
        final MenuItem tempSome = new MenuItem("temp");
        final MenuItem DWPTSome = new MenuItem("DWPT");
        final MenuItem pressSome = new MenuItem("press");
        final MenuItem relhSome = new MenuItem("RELH");
        final MenuItem mixrSome = new MenuItem("MIXR");
        final MenuItem drctSome = new MenuItem("DRCT");
        someDays.getItems().addAll(
                wind_speedSome,
                tempSome,
                DWPTSome,
                pressSome,
                relhSome,
                mixrSome,
                drctSome
        );
        editMenu.getItems().add(someDays);


        final MenuItem wind_speed1 = new MenuItem("wind speed");
        final MenuItem temp1 = new MenuItem("temp");
        final MenuItem DWPT1 = new MenuItem("DWPT");
        final MenuItem press1 = new MenuItem("press");
        final MenuItem relh1 = new MenuItem("RELH");
        final MenuItem mixr1 = new MenuItem("MIXR");
        final MenuItem drct1 = new MenuItem("DRCT");
        ondaystations.getItems().addAll(
                wind_speed1,
                temp1,
                DWPT1,
                press1,
                relh1,
                mixr1,
                drct1
        );

        editMenu.getItems().add(ondaystations);


        final MenuItem qwind_speed1 = new MenuItem("wind speed");
        final MenuItem qtemp1 = new MenuItem("temp");
        final MenuItem qDWPT1 = new MenuItem("DWPT");
        final MenuItem qpress1 = new MenuItem("press");
        final MenuItem qrelh1 = new MenuItem("RELH");
        final MenuItem qmixr1 = new MenuItem("MIXR");
        final MenuItem qdrct1 = new MenuItem("DRCT");

        crosswindmenu.getItems().addAll(
                qwind_speed1
        );

        editMenu.getItems().add(crosswindmenu);


        JFXTextArea console = new JFXTextArea(">>onday 40800 10 26 PRES hPa 8999 1999 2017 iran__islamic_rep\n>>");
        console.setMinSize(900, 220);



        wind_speed.setOnAction(event -> {
            console.appendText("onday 40800 10 26 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");
        });

        temp.setOnAction(event -> {
            console.appendText("onday 40800 10 26 TEMP ℃ 20000 1973 2017 iran__islamic_rep");
        });
        DWPT.setOnAction(event -> {
            console.appendText("onday 40800 10 26 DWPT ℃ 20000 1973 2017 iran__islamic_rep");
        });

        press.setOnAction(event -> {
            console.appendText("onday 40800 10 26 PRES atm 20000 1973 2017 iran__islamic_rep");
        });

        relh.setOnAction(event -> {
            console.appendText("onday 40800 10 26 RELH % 20000 1973 2017 iran__islamic_rep");
        });

        mixr.setOnAction(event -> {
            console.appendText("onday 40800 10 26 MIXR g/kg 20000 1973 2017 iran__islamic_rep");
        });

        drct.setOnAction(event -> {
            console.appendText("onday 40800 10 26 DRCT ° 20000 1973 2017           iran__islamic_rep ");
        });


        wind_speedSome.setOnAction(event -> {
            console.appendText("somedays 40800 10 26 -2 2 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");
        });

        tempSome.setOnAction(event -> {
            console.appendText("somedays 40800 10 26 -2 2 TEMP ℃ 20000 1973 2017 iran__islamic_rep");
        });
        DWPTSome.setOnAction(event -> {
            console.appendText("somedays 40800 10 26 -2 2 DWPT ℃ 20000 1973 2017 iran__islamic_rep");
        });

        pressSome.setOnAction(event -> {
            console.appendText("somedays 40800 10 26 -2 2 PRES atm 20000 1973 2017 iran__islamic_rep");
        });

        relhSome.setOnAction(event -> {
            console.appendText("somedays 40800 10 26 -2 2 RELH % 20000 1973 2017 iran__islamic_rep");
        });

        mixrSome.setOnAction(event -> {
            console.appendText("somedays 40800 10 26 -2 2 MIXR g/kg 20000 1973 2017 iran__islamic_rep");
        });

        drctSome.setOnAction(event -> {
            console.appendText("somedays 40800 10 26 -2 2 DRCT ° 20000 1973 2017 iran__islamic_rep ");
        });


        /************************///******///////////////////////////////////////////////////////////////////////*/

        qwind_speed1.setOnAction(event -> {
            console.appendText("crosswind 40800 10 26 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");
        });

        wind_speed1.setOnAction(event -> {
            console.appendText("ondaystations  10 26 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");
        });

        temp1.setOnAction(event -> {
            console.appendText("ondaystations  10 26 TEMP ℃ 20000 1973 2017 iran__islamic_rep");
        });
        DWPT1.setOnAction(event -> {
            console.appendText("ondaystations  10 26 DWPT ℃ 20000 1973 2017 iran__islamic_rep");
        });

        press1.setOnAction(event -> {
            console.appendText("ondaystations  10 26 PRES atm 20000 1973 2017 iran__islamic_rep");
        });

        relh1.setOnAction(event -> {
            console.appendText("ondaystations  10 26 RELH % 20000 1973 2017 iran__islamic_rep");
        });

        mixr1.setOnAction(event -> {
            console.appendText("ondaystations  10 26 MIXR g/kg 20000 1973 2017 iran__islamic_rep");
        });

        drct1.setOnAction(event -> {
            console.appendText("ondaystations  10 26 DRCT ° 20000 1973 2017           iran__islamic_rep ");
        });


        Button minimumbtn = new Button("__");
        minimumbtn.setStyle("-fx-border-radius: 0;-fx-background-radius: 0;" +
                "-fx-background-color: #353533;" +
                "-fx-text-fill: #ffffff");

        minimumbtn.getStyleClass().add(".btn:hover{\n" +
                "    -fx-background-color: #eb5f48; -fx-text-fill: white;}");

        minimumbtn.setOnAction(event -> {
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
        });

        console.setOnKeyPressed(event -> {
            final KeyCode code = event.getCode();
            if (code == KeyCode.ENTER) {
                event.consume();
                console.appendText("\n>>");
                final String text = console.getText();
                final String[] split = text.split(">>");
                if (split.length == 10) {
                    console.setText(">>");
                    console.positionCaret(console.getText().length());
                }
                final String x = split[split.length - 1].replaceAll("\n", "");
                scripting(x);
                System.out.println(x);
            }
        });


        VBox vBox = new VBox(file, console);

        file.getMenus().addAll(
                fileMenu,
                editMenu
        );

        layout = new BorderPane();
        layout.setTop(vBox);

        Scene scene = new SceneJson<>(layout, 900, 450);

        primaryStage.setScene(scene);
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> console.setPrefHeight(newValue.doubleValue()));
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> console.prefWidthProperty().setValue(newValue));



        layout.getStylesheets().add("/dark-theme.css");
        console.getStyleClass().add("cons");
        file.getStyleClass().add("cons");

        primaryStage.show();
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("g");
    }


    interface Run {
        void run(FormInfo formInfo);
    }

    interface RunSome {
        void run(SomeDays someDays);
    }
}
