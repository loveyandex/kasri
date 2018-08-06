package com.amin.ui.backtopbtn;

import com.amin.jsons.Date;
import com.amin.jsons.FormInfo;
import com.amin.scripting.Funsctions;
import com.amin.ui.dialogs.Dialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
    private BorderPane layout;
    private Stage window;
    private double xOffset = 0;
    private double yOffset = 0;


    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Menu Example");


        MenuBar file = new MenuBar();
        file.setId("file");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                new MenuItem("New File..."),
                new MenuItem("Open file..."),
                new MenuItem("Save file"));
        fileMenu.setId("#fileMenu");


        Menu editMenu = new Menu("samples");
        final MenuItem wind_speed = new MenuItem("wind speed");
        editMenu.getItems().addAll(
                wind_speed,
                new MenuItem("temp"),
                new MenuItem("Cut"),
                new MenuItem("Copy"),
                new MenuItem("Paste")
        );
        TextArea console = new TextArea(">>onday 40800 10 26 PRES hPa 8999 1999 2017 iran__islamic_rep\n>>");

        wind_speed.setOnAction(event -> {
            console.appendText("onday 40800 10 26 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");
        });


        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-radius: 0;-fx-background-color: #ff667d;-fx-text-fill: white");

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


        HBox hBox = new HBox(oth, closeButton);
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

        Scene scene = new Scene(layout, 900, 500, Color.rgb(75, 75, 69));
        oth.setMinWidth(scene.getWidth() - 30);

        window.setScene(scene);
//        window.setMaximized(true);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            oth.setMinWidth(((double) newValue) - 30);

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


    private void scripting(String cmd) {
        final String[] args = cmd.replaceAll("  ", " ").split(" ");
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
