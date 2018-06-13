package com.ui;

import com.amin.jsons.WindInfo;
import com.analysis.wind.WindMining;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 6/8/2018 at 08:15
 */
public class WindResultController implements Initializable, Runnable {
    public VBox rootNode;
    public JFXButton back;
    public TextArea resultArea;
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


    private void getBack(Stage stage) throws IOException, URISyntaxException {
        Parent root = FXMLLoader.load(getClass().getResource("/wind_login.fxml"));
        System.out.println(((SceneJsonWindInfo) stage.getScene()).getWindInfo().getStationNumber());


        Scene scene = new Scene(root, 450, 350);
        stage.setScene(scene);
        stage.setScene(scene);
        stage.initOwner(scene.getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
    }

    @Override
    public void run() {
        WindInfo windInfo = ((SceneJsonWindInfo) rootNode.getScene()).getWindInfo();
        int numDay = windInfo.Date.Day;
        String dayOfMonth = (numDay < 10 ? "0" : "") + numDay;
        Month day = Month.of(windInfo.getDate().Month);

        String monthDisp = day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        System.out.println(monthDisp);
        System.out.println(dayOfMonth);

        String fileName="00Z_"+dayOfMonth+"_"+monthDisp+"_"+windInfo.getDate().Year+".csv";
        String dayDir="assets/data";
        try {
            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol(dayDir, fileName);
            windSpeedCol.forEach(strings -> {
                resultArea.appendText(strings.get(0)+"--->>>"+strings.get(1)+"\r\n");
            });





        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
