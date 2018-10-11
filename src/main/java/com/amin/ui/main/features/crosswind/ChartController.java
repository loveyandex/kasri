package com.amin.ui.main.features.crosswind;

import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 10/11/2018 at 8:45 PM
 */
public class ChartController implements Initializable {

    private ArrayList<ArrayList<Object>> allfeatureandyear;
    @FXML
    private VBox rootme;


    public void statisticalModels(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new StageOverride();
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/day/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");

        SceneJson sceneJson = new SceneJson<>(root);
        sceneJson.setJson(allfeatureandyear);
        primaryStage.setScene(sceneJson);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void crossing(ActionEvent actionEvent) throws IOException {

        Stage primaryStage = new StageOverride();
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/crosswind/crosswindagainst.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");

        SceneJson sceneJson = new SceneJson<>(root);
        sceneJson.setJson(allfeatureandyear);
        primaryStage.setScene(sceneJson);

        primaryStage.setResizable(false);
        primaryStage.show();




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> allfeatureandyear = (ArrayList) ((SceneJson) rootme.getScene()).getJson());
    }
}
