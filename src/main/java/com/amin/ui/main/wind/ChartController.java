package com.amin.ui.main.wind;

import com.amin.jsons.FormInfo;
import com.amin.ui.SceneJson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 7/18/2018 at 21:57
 */

public class ChartController implements Initializable, Runnable {

    private FormInfo formInfo;
    @FXML
    private VBox rootme;

    @PostConstruct
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this);

    }

    @Override
    public void run() {
        formInfo = (FormInfo) ((SceneJson) rootme.getScene()).getJson();
        System.out.println(formInfo.Country + " king");
    }


    public void statisticalModels(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/wind/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");
        Scene scene = new SceneJson<>(root);
        Stage primaryStage=new Stage();
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
