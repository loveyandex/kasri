package com.amin.ui.main.features.someday.day;

import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 7/18/2018 at 21:57
 */

public class ChartController implements Initializable, Runnable {

    private ArrayList<ArrayList<Object>> allfeatureandyear;
    @FXML
    private VBox rootme;

    @PostConstruct
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this);

    }

    @Override
    public void run() {

        allfeatureandyear = (ArrayList) ((SceneJson) rootme.getScene()).getJson();
    }


    public void statisticalModels(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new StageOverride();
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/day/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");
//        root.getStylesheets().add("/dark-theme.css");

        SceneJson sceneJson = new SceneJson<>(root);
        sceneJson.setJson(allfeatureandyear);
        
        primaryStage.setScene(sceneJson);
        primaryStage.titleProperty().setValue(new Gson().toJson(allfeatureandyear));

        primaryStage.show();
    }
    public void styling(ActionEvent actionEvent) {
        final Parent root = rootme.getScene().getRoot();
        root.getStylesheets().add("/dark-theme.css");
    }

    public void removestyling(ActionEvent actionEvent) {
        final Parent root = rootme.getScene().getRoot();
        final ObservableList<String> stylesheets = root.getStylesheets();

        System.out.println(stylesheets.size());

    }

    public void whiteStyle(ActionEvent actionEvent) {
        final Parent root = rootme.getScene().getRoot();
        root.getStylesheets().add("/white-chart.css");
    }
}
