package com.amin.getdata;

import com.amin.ui.SceneJson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DoanloadContrller extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/getdata/doanload.fxml"));
        Scene scene = new SceneJson<>(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
