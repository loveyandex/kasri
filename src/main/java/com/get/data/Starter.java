package com.get.data;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Starter extends javafx.application.Application {

    public static String ABSOLUTE_ROOT_PATH;
    public static String[] COUNTRIES;

    @Override
    public void start(Stage myStage) {

        myStage.setTitle("weather");

        myStage.setOnCloseRequest(event -> {
            System.out.println("king amin ");
        });


        GridPane rootNode = new GridPane();
        rootNode.setPadding(new Insets(15));
        rootNode.setHgap(5);
        rootNode.setVgap(5);
        rootNode.setAlignment(Pos.CENTER);

        Scene myScene = new Scene(rootNode, 300, 200);
        Label label = new Label("absolute root path for saving :");
        label.setAlignment(Pos.CENTER);
        rootNode.add(label, 0, 0, 2, 1);

        TextField firstValue = new TextField("G:/Program Files/AMinAbvall/kasridata");
        firstValue.setAlignment(Pos.CENTER);
        rootNode.add(firstValue, 1, 1, 2, 1);


        Label country = new Label("country:");
        country.setAlignment(Pos.CENTER);
        rootNode.add(country, 0, 2, 2, 1);

//        TextField countryvalue = new TextField( "israel;turkey;u_arab_emirates;saudi_arabia;qatar;oman;yemen;pakistan;bahrain;azerbaijan;afghanistan;armenia");
        TextField countryvalue = new TextField( "israel;turkey");
        countryvalue.setAlignment(Pos.CENTER);
        rootNode.add(countryvalue, 1, 3, 2, 1);




        Button aButton = new Button("start getting Data");
        rootNode.add(aButton, 1, 4);
        GridPane.setHalignment(aButton, HPos.CENTER);

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        rootNode.add(pbar, 1, 5);

        aButton.setOnAction(e -> {
            ABSOLUTE_ROOT_PATH = firstValue.getText();
            COUNTRIES =countryvalue.getText().toLowerCase().split(";");
            System.out.println(COUNTRIES.length+">>>>>>>>>");
            pbar.setVisible(true);
            System.out.println("downloading started :kissing_heart:");
            System.out.println(ABSOLUTE_ROOT_PATH);
            aButton.setDisable(true);
            startProcess.start();
        });
        myStage.setScene(myScene);
        myStage.show();
    }
    Thread startProcess = new Thread(new Process());

    public static void main(String[] args) {
        launch(args);
    }

}