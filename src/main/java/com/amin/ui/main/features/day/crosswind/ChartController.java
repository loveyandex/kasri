package com.amin.ui.main.features.day.crosswind;

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
import java.util.stream.IntStream;

/**
 * is created by aMIN on 10/11/2018 at 8:45 PM
 */
public class ChartController implements Initializable {

    private ArrayList<ArrayList<Object>> allfeatureandyear = new ArrayList<>();
    private ArrayList<Object> godObj;
    @FXML
    private VBox rootme;


    public void statisticalModels(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new StageOverride();
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/day/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");

        IntStream.range(0, godObj.size() - 1).filter(value -> (value % 2 == 1 && godObj.get(value) != null))
                .forEach(value -> {
                    allfeatureandyear.add((ArrayList<Object>) godObj.get(value));
                });

        SceneJson sceneJson = new SceneJson<>(root);
        sceneJson.setJson(allfeatureandyear);
        primaryStage.setScene(sceneJson);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void crossing(ActionEvent actionEvent) throws IOException {

        Stage primaryStage = new StageOverride();
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/day/crosswind/crosswindagainst.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");


        SceneJson sceneJson = new SceneJson<>(root);
        sceneJson.setJson(godObj);
        primaryStage.setScene(sceneJson);

        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> godObj = (ArrayList) ((SceneJson) rootme.getScene()).getJson());
    }


}
