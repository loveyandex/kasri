package com.ui;

import com.amin.jsons.WindInfo;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 6/8/2018 at 08:15
 */
public class WindResultController implements Initializable, Runnable {
    public AnchorPane rootNode;
    public JFXButton back;
    public JFXButton f32;
    private WindInfo windInfo;

    public WindInfo getWindInfo() {
        return windInfo;
    }

    public void setWindInfo(WindInfo windInfo) {
        this.windInfo = windInfo;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this);
    }



    public void back(ActionEvent actionEvent) {
        try {
            getBack(((Stage) rootNode.getScene().getWindow()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    private void getBack(Stage prStage) throws IOException, URISyntaxException {
        Parent root = FXMLLoader.load(getClass().getResource("/wind_login.fxml"));
        System.out.println(((SceneJsonWindInfo) prStage.getScene()).getWindInfo().getStationNumber());


        Scene scene = new Scene(root, 450, 350);
        prStage.setScene(scene);
    }

    @Override
    public void run() {
        WindInfo windInfo = ((SceneJsonWindInfo) rootNode.getScene()).getWindInfo();
        int numDay = windInfo.Date.Day;
        String dayOfMonth = (numDay < 10 ? "0" : "") + numDay;
        Month day = Month.of(windInfo.getDate().Month);

        System.out.println(day.getDisplayName(TextStyle.SHORT,Locale.ENGLISH));
        System.out.println(dayOfMonth);

//        String fileName=;
//        String dayDir;
//        WindMining.getWindSpeedCol(dayDir,fileName)


    }
}
