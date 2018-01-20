package com.jsoupway;

import com.telegram.bot.notify.Notify;
import com.telegram.bot.notify.NotifyBot;
import javafx.application.Application;
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

public class Frame extends Application {

    public static String ABSOLUTE_ROOT_PATH;

    @Override
    public void start(Stage myStage) {

        myStage.setTitle("weather");
//        myStage.initStyle(StageStyle.UNDECORATED);
        myStage.setOnCloseRequest(event -> {
            System.out.println("king amin ");
            System.exit(0);
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

        Button aButton = new Button("start getting Data");
        rootNode.add(aButton, 1, 2);
        GridPane.setHalignment(aButton, HPos.CENTER);

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        rootNode.add(pbar, 1, 4);

        aButton.setOnAction(e -> {
            ABSOLUTE_ROOT_PATH = firstValue.getText();
            pbar.setVisible(true);
            Notify.sendSelfMsg("downloading started :kissing_heart:");
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