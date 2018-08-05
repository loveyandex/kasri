package com.amin.getdata;

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

public class Starter extends javafx.application.Application implements EventHandler<KeyEvent> {

    public static String ABSOLUTE_ROOT_PATH;
    public static String[] COUNTRIES;
    public static boolean terminateThread=false;

    @Override
    public void start(Stage myStage) {

        myStage.setTitle("weather");

        myStage.setOnCloseRequest(event -> {
            terminateThread=true;

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

        Scene myScene = new Scene(rootNode, 400, 300);
        Label label = new Label("absolute root path for saving :");
        label.setAlignment(Pos.CENTER);
        rootNode.add(label, 0, 0, 2, 1);

        TextField firstValue = new TextField("G:/Program Files/AMinAbvall/haji");
        firstValue.setAlignment(Pos.CENTER);
        rootNode.add(firstValue, 1, 1, 2, 1);


        Label country = new Label("country:");
        country.setAlignment(Pos.CENTER);
        rootNode.add(country, 0, 2, 2, 1);

//        TextField countryvalue = new TextField( "israel;turkey;u_arab_emirates;saudi_arabia;qatar;oman;yemen;pakistan;bahrain;azerbaijan;afghanistan;armenia");
        TextField countryvalue = new TextField( "israel;turkey");
        countryvalue.setAlignment(Pos.CENTER);
        rootNode.add(countryvalue, 1, 3, 2, 1);

        Label regionl = new Label("region:");
        regionl.setAlignment(Pos.CENTER);
        rootNode.add(regionl, 0, 4, 2, 1);

        TextField region = new TextField( "");
        ComboBox<String> comboBoxrigion=new ComboBox<>();
        region.setAlignment(Pos.CENTER);
        rootNode.add(region, 1, 5, 2, 1);




        Button aButton = new Button("start getting Data");
        rootNode.add(aButton, 1, 6);
        GridPane.setHalignment(aButton, HPos.CENTER);

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        rootNode.add(pbar, 1, 7);

        aButton.setOnAction(e -> {
            ABSOLUTE_ROOT_PATH = firstValue.getText();
            COUNTRIES =countryvalue.getText().toLowerCase().split(";");
            System.out.println(COUNTRIES.length+">>>>>>>>>");
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
        myStage.show();
    }
    Thread startProcess = new Thread(new Process());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(KeyEvent event) {
//        System.exit(0);
    }









    private void setstationsforpersent(){

    }
}