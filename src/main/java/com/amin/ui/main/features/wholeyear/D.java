package com.amin.ui.main.features.wholeyear;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * is created by aMIN on 7/30/2018 at 10:37 PM
 */
public class D extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/wholeyear/progress.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setOnCloseRequest(event -> {
            scene.getWindow().hide();
            System.exit(0);

        });


        primaryStage.setTitle("Mining");
        primaryStage.setScene(scene);
        primaryStage.toBack();
        primaryStage.show();
    }
}
