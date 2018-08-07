package com.amin.ui.scripts;

import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;
import com.amin.scripting.Funsctions;
import com.amin.ui.dialogs.Dialog;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * is created by aMIN on 6/8/2018 at 04:43
 */
public class ScriptAPP extends Application {
    public Button titlebtn;
    private BorderPane layout;
    private Stage window;
    private double xOffset = 0;
    private double yOffset = 0;


    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        MenuBar file = new MenuBar();
        file.setId("file");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                new MenuItem("..."),
                new MenuItem("..."));
        fileMenu.setId("#fileMenu");


        Menu editMenu = new Menu("samples");
        final MenuItem wind_speed = new MenuItem("wind speed");
        final MenuItem temp = new MenuItem("temp");
        final MenuItem DWPT = new MenuItem("DWPT");
        final MenuItem press = new MenuItem("press");
        final MenuItem relh = new MenuItem("RELH");
        final MenuItem mixr = new MenuItem("MIXR");
        final MenuItem drct = new MenuItem("DRCT");
        editMenu.getItems().addAll(
                wind_speed,
                temp,
                DWPT,
                press,
                relh,
                mixr,
                drct
        );
        TextArea console = new TextArea(">>onday 40800 10 26 PRES hPa 8999 1999 2017 iran__islamic_rep\n>>");
        console.setMinSize(900, 220);
        console.setStyle("-fx-base: #fff3f8;\n" +
                " -fx-control-inner-background: #effff2");


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



        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-radius: 0;-fx-background-color: #ff667d;-fx-text-fill: white");


        Button minimumbtn = new Button("__");
        minimumbtn.setStyle("-fx-border-radius: 0;-fx-background-radius: 0;" +
                "-fx-background-color: #fefff6;" +
                "-fx-text-fill: #ababab");

        minimumbtn.getStyleClass().add(".btn:hover{\n" +
                "    -fx-background-color: #eb5f48; -fx-text-fill: white;\n" +
                "\n" +
                "}");


        minimumbtn.setOnAction(event -> {
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
        });


        closeButton.setMinWidth(30);
        Button oth = new Button("scripting");
        oth.setStyle("-fx-background-radius: 0;-fx-border-radius: 0");



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

        Scene scene = new Scene(layout, 900, 450, Color.rgb(75, 75, 69));
        oth.setMinWidth(scene.getWidth() - 30 - 25);

        window.setScene(scene);
//        window.setMaximized(true);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            oth.setMinWidth(((double) newValue) - 30 - 25);

        });

        oth.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        oth.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        oth.setFocusTraversable(false);
//        closeButton.setFocusTraversable(false);
        oth.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(observable.getClass().getName());
        });

        ResizeHelper.addResizeListener(primaryStage);

    }

    private String s;

    private void rem() {
        s = s.replaceAll("  ", " ");
        if (s.contains("  "))
            rem();
        else return;
    }
    private void scripting(String cmd) {
        s=cmd;
        rem();
        cmd=s;
        System.out.println("f:\n"+cmd);
        final String[] args = cmd.split(" ");

        final String func = args[0];
        if (func.equals("onday"))
            runFopen(args);


    }

    private void runFopen(String[] args) {
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
                Funsctions.getInstance().fopen(formInfo);

            } catch (Exception X) {
                Dialog.createExceptionDialog(new RuntimeException(X.toString()));
            }
        }
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("g");
    }
}
