package com.amin.getdata;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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

public class Starter extends javafx.application.Application implements EventHandler<KeyEvent> {

    public static String ABSOLUTE_ROOT_PATH;
    public static String[] COUNTRIES;
    public static boolean terminateThread = false;
    Thread startProcess = new Thread(new Process());

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

//        TextField countryvalue = new TextField( "israel;turkey;u_arab_emirates;saudi_arabia;qatar;oman;yemen;pakistan;bahrain;azerbaijan;afghanistan;armenia");


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
//
//        rootNode.add(regionl, 0, 4, 2, 1);

        ComboBox<String> comboBoxrigion = new ComboBox<>();


        Button aButton = new Button("Start Getting Data");
        aButton.setDisable(true);
        rootNode.add(aButton, 1, 9);
        GridPane.setHalignment(aButton, HPos.CENTER);


        countriesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            countryvalue.setText(newValue.getText());
            aButton.setDisable(false);
            System.out.println(newValue.getText());

        });

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        rootNode.add(pbar, 1, 11);

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