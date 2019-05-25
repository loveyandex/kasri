package com.amin.getdata;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.IntStream;

public class Starter extends javafx.application.Application implements EventHandler<KeyEvent> {

    public static String ABSOLUTE_ROOT_PATH;
    public static Label updating = new Label("");
    ;
    public static String[] COUNTRIES;
    public static boolean terminateThread = false;
    Thread startProcess = new Thread(new Process());
    public static Stack<String> years = new Stack<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) {

        myStage.setTitle("weather");

        myStage.setOnCloseRequest(event -> {
            terminateThread = true;

        });


        GridPane rootNode = new GridPane();
        rootNode.setStyle("          -fx-padding: 10;\n" +
                "            -fx-border-style: solid inside;\n" +
                "            -fx-border-width: 6;\n" +
                "            -fx-border-insets: 5;\n" +
                "            -fx-border-radius: 5;\n" +
                "            -fx-border-color: gray;");
        rootNode.setPadding(new Insets(15));
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setAlignment(Pos.CENTER);

        Scene myScene = new Scene(rootNode, 500, 400);
        Label label = new Label("absolute root path for saving :");
        label.setAlignment(Pos.CENTER);
        rootNode.add(label, 0, 0, 2, 1);

        JFXTextField firstValue = new JFXTextField(C.DATA_PATH);
        firstValue.setAlignment(Pos.CENTER);
        rootNode.add(firstValue, 1, 1, 2, 1);


        Label country = new Label("country:");
        country.setAlignment(Pos.CENTER);
        rootNode.add(country, 0, 4, 3, 1);

        JFXTextField fromyear = new JFXTextField("1973");
        JFXTextField toyear = new JFXTextField("2018");

        IntStream.range(Integer.parseInt(fromyear.getText()), Integer.parseInt(toyear.getText())).forEach(value -> years.push(String.valueOf(value)));

        class A<T> {
            /**
             * This method needs to be provided by an implementation of
             * {@code ChangeListener}. It is called if the value of an
             * {@link ObservableValue} changes.
             * <p>
             * In general is is considered bad practice to modify the observed value in
             * this method.
             *
             * @param observable
             *            The {@code ObservableValue} which value changed
             * @param oldValue
             *            The old value
             * @param newValue
             *            The new value
             */
            void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                years.clear();
                IntStream.range(Integer.parseInt(fromyear.getText()), Integer.parseInt(toyear.getText())).forEach(value -> years.push(String.valueOf(value)));
            }

        }
        fromyear.textProperty().addListener(new A<>()::changed);
        toyear.textProperty().addListener(new A<>()::changed);


        JFXComboBox<Label> countriesCombo = new JFXComboBox<>();

        try {
            ArrayList<String> countriesName = Mapping.getFileLines(C.COUNTRIES_CONFIG_PATH);
            countriesName.forEach(countryName -> countriesCombo.getItems().add(new Label(countryName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JFXTextField countryvalue = new JFXTextField("israel;turkey");
        countryvalue.setVisible(false);


        rootNode.add(countriesCombo, 1, 5, 2, 1);


        rootNode.add(fromyear, 1, 7);
        rootNode.add(toyear, 1, 9);


        Button aButton = new Button("Start Getting Data");
        aButton.setDisable(true);
        rootNode.add(aButton, 1, 11);
        GridPane.setHalignment(aButton, HPos.CENTER);


        countriesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            countryvalue.setText(newValue.getText());
            aButton.setDisable(false);
            System.out.println(newValue.getText());

        });

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        rootNode.add(pbar, 1, 13);


        rootNode.add(updating, 1, 15);

        aButton.setOnAction(e -> {
            ABSOLUTE_ROOT_PATH = firstValue.getText();
//            COUNTRIES = countryvalue.getText().toLowerCase().split(";");
            COUNTRIES = new String[]{countryvalue.getText()};

            System.out.println(COUNTRIES.length + ">>>>>>>>>");
            pbar.setVisible(true);
            System.out.println(ABSOLUTE_ROOT_PATH);
            aButton.setDisable(true);
            startProcess.start();
        });
        myStage.setScene(myScene);
        myScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                myStage.close();
        });


        myScene.getStylesheets().add("/dark-theme.css");
        myStage.show();
    }

    @Override
    public void handle(KeyEvent event) {
//        System.exit(0);
    }


    private void setstationsforpersent() {

    }
}