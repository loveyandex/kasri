package com.amin.ui.scripts;

import com.amin.analysis.Mapping;
import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;
import com.amin.jsons.OtherFormInfo;
import com.amin.scripting.Functions;
import com.amin.scripting.wind.Wind;
import com.amin.ui.SceneJson;
import com.amin.ui.dialogs.Dialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.amin.scripting.RestFuns.toFormInfo;

/**
 * is created by aMIN on 6/8/2018 at 04:43
 */
public class ScriptAPP extends Application {
    private static String s;
    static private Map<String, String> stationNumTOCities;
    public Button titlebtn;
    private BorderPane layout;
    private Stage window;
    private double xOffset = 0;
    private double yOffset = 0;

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

                String dirpath = "config/old-stations";
                String fileName = country + ".conf";

                File dir = new File(dirpath);
                dir.mkdirs();
                File fileTosave = new File(dir, fileName);
                if (!fileTosave.exists())
                    Mapping.createCSVFILEFORStations(dirpath, fileName);

                stationNumTOCities = Mapping.
                        MapStationNumTOCities("config/old-stations/" + country + ".conf.csv");


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
            try {


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

            } catch (Exception X) {
                Dialog.createExceptionDialog(new RuntimeException(X.toString()));
            }
        }
        return 0.0;
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        primaryStage.getIcons().add(new Image(getClass().getResource("/logo.png").toURI().toString()));
        MenuBar file = new MenuBar();
        file.setId("file");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                new MenuItem("..."),
                new MenuItem("..."));
        fileMenu.setId("#fileMenu");


        Menu editMenu = new Menu("samples");
        Menu onday = new Menu("samples onday");
        Menu crosswindmenu = new Menu("cross wind onday");
        Menu ondaystations = new Menu("samples onday over the state");

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
                qwind_speed1/*,
                qtemp1,
                qDWPT1,
                qpress1,
                qrelh1,
                qmixr1,
                qdrct1*/
        );

        editMenu.getItems().add(crosswindmenu);


        TextArea console = new TextArea(">>onday 40800 10 26 PRES hPa 8999 1999 2017 iran__islamic_rep\n>>");
        console.setMinSize(900, 220);
        console.setStyle("-fx-base: #fff3f8;\n" +
                " -fx-control-inner-background: #f8fbee");


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


        qwind_speed1.setOnAction(event -> {
            console.appendText("crosswind 40800 10 26 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");
        });
//
//        qtemp1.setOnAction(event -> {
//            console.appendText("crosswind 40800 10 26 TEMP ℃ 20000 1973 2017 iran__islamic_rep");
//        });
//        qDWPT1.setOnAction(event -> {
//            console.appendText("crosswind 40800 10 26 DWPT ℃ 20000 1973 2017 iran__islamic_rep");
//        });
//
//        qpress1.setOnAction(event -> {
//            console.appendText("crosswind 40800 10 26 PRES atm 20000 1973 2017 iran__islamic_rep");
//        });
//
//        qrelh1.setOnAction(event -> {
//            console.appendText("crosswind 40800 10 26 RELH % 20000 1973 2017 iran__islamic_rep");
//        });
//
//        qmixr1.setOnAction(event -> {
//            console.appendText("crosswind 40800 10 26 MIXR g/kg 20000 1973 2017 iran__islamic_rep");
//        });
//
//        qdrct1.setOnAction(event -> {
//            console.appendText("crosswind 40800 10 26 DRCT ° 20000 1973 2017           iran__islamic_rep ");
//        });


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


        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-radius: 0;-fx-background-color: #ff667d;-fx-text-fill: white");


        Button minimumbtn = new Button("__");
        minimumbtn.setStyle("-fx-border-radius: 0;-fx-background-radius: 0;" +
                "-fx-background-color: #353533;" +
                "-fx-text-fill: #ffffff");

        minimumbtn.getStyleClass().add(".btn:hover{\n" +
                "    -fx-background-color: #eb5f48; -fx-text-fill: white;}");


        minimumbtn.setOnAction(event -> {
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
        });


        closeButton.setMinWidth(30);
        Button oth = new Button("scripting");
        oth.setStyle("-fx-background-radius: 0;-fx-border-radius: 0;" +
                "-fx-background-color: #f8fbee;" +
                "-fx-text-fill: #B2B2B2;");


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


        HBox hBox = new HBox(oth, minimumbtn, closeButton);
        VBox vBox = new VBox(hBox, file, console);
        closeButton.setOnAction(event -> {
            window.hide();
        });
//        closeButton.setAlignment(Pos.TOP_RIGHT);

        file.getMenus().addAll(
                fileMenu,
                editMenu

        );

        layout = new BorderPane();
        layout.setTop(vBox);
        ;

        Scene scene = new SceneJson<>(layout, 900, 450, Color.rgb(75, 75, 69));
        layout.getStylesheets().add("/scripting.css");


        oth.setMinWidth(scene.getWidth() - 30 - 25);

        window.setScene(scene);
//        window.setMaximized(true);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            oth.setMinWidth(((double) newValue) - 30 - 25);

        });

        oth.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        oth.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        oth.setFocusTraversable(false);
//        closeButton.setFocusTraversable(false);
        oth.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(observable.getClass().getName());
        });

        ResizeHelper.addResizeListener(primaryStage);

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("g");
    }


    interface Run {
        void run(FormInfo formInfo);
    }


}
