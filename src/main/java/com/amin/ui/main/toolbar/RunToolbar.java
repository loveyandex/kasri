package com.amin.ui.main.toolbar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * is created by aMIN on 7/7/2018 at 14:13
 */
public class RunToolbar extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/toolbar/toolbar.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
