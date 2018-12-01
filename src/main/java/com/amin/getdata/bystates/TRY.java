package com.amin.getdata.bystates;

import com.amin.ui.SceneMainActivity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TRY extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/getdata/bystates/starter.fxml"));
        Scene scene = new SceneMainActivity<>(root, 1100, 600);

        primaryStage.setTitle("Abolfazl");
        primaryStage.setScene(scene);
        primaryStage.toBack();
        primaryStage.show();
    }
}
