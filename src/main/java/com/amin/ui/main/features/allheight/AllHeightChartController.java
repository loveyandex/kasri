package com.amin.ui.main.features.allheight;

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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 7/18/2018 at 21:57
 */

public class AllHeightChartController implements Initializable {

    private ArrayList<ArrayList<ArrayList<Double>>> allfeatureandyear;
    @FXML
    private VBox rootme;
    Runnable runnable = () -> {
        allfeatureandyear = (ArrayList) ((SceneJson) rootme.getScene()).getJson();
        rootme.heightProperty().addListener((observable, oldValue, newValue) -> {
            ((VBox) rootme.getChildren().get(1)).getChildren().get(0).minHeight(newValue.doubleValue());

        });

    };

    @PostConstruct
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(runnable);

    }

    public void statisticalModels(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new StageOverride();
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/allheight/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");

        SceneJson sceneJson = new SceneJson<>(root);
        sceneJson.setJson(allfeatureandyear);
        primaryStage.setScene(sceneJson);

        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
