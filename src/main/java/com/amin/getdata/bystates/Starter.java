package com.amin.getdata.bystates;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class Starter extends javafx.application.Application implements EventHandler<KeyEvent> {

    private TextArea regionteTextArea;
    public static String ABSOLUTE_ROOT_PATH;
    public static String[] COUNTRIES;
    public static boolean mustStop=false;

    @Override
    public void start(Stage myStage) {

        myStage.setTitle("weather");

        myStage.setOnCloseRequest(event -> {
            mustStop=true;
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

        Scene myScene = new Scene(rootNode, 400, 500);
        Label label = new Label("absolute root path for saving :");
        label.setAlignment(Pos.CENTER);
        rootNode.add(label, 0, 0, 2, 1);

        TextField firstValue = new TextField("E:\\neuronman\\world");
        firstValue.setAlignment(Pos.CENTER);
        rootNode.add(firstValue, 1, 1, 2, 1);


        Label regionl = new Label("regions:");
        regionl.setAlignment(Pos.CENTER);
        rootNode.add(regionl, 0, 3, 2, 1);
        regionteTextArea = new TextArea("");
        ComboBox<String> comboBoxrigion = new ComboBox<>();
        rootNode.add(regionteTextArea, 1, 4, 2, 1);


        Button aButton = new Button("start getting Data");
        HBox hBox = new HBox(aButton);
        hBox.setAlignment(Pos.CENTER);

        rootNode.add(hBox, 1, 7);
        GridPane.setHalignment(hBox, HPos.CENTER);

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        rootNode.add(pbar, 1, 8);
        setstationsforpersent();
        aButton.setOnAction(e -> {
            ABSOLUTE_ROOT_PATH = firstValue.getText();

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


    private void setstationsforpersent() {
        File rootfile = new File("config/states");
        final File[] listFiles = rootfile.listFiles();
        for (File file : listFiles)
            if (file.isFile()) {
                final String s = file.getName().replaceAll(".conf", "");
                regionteTextArea.appendText(s + "\n");
            }

        COUNTRIES = regionteTextArea.textProperty().getValue().split("\\n");


        regionteTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            COUNTRIES = newValue.split("\\n");
            for (int i = 0; i < COUNTRIES.length; i++) {
                System.out.println(COUNTRIES[i]);
            }
            System.out.println(COUNTRIES.length);
        });

    }
}