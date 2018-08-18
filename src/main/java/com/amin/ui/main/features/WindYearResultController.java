package com.amin.ui.main.features;

import com.amin.analysis.wind.WindMining;
import com.amin.config.C;
import com.amin.jsons.FormInfo;
import com.amin.ui.SceneJson;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.main.main.MainController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
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
public class WindYearResultController implements Initializable, Runnable {
    public VBox rootNode;
    public JFXButton back;
    public TextArea resultArea;
    private FormInfo formInfo;

    public FormInfo getFormInfo() {
        return formInfo;
    }

    public void setFormInfo(FormInfo formInfo) {
        this.formInfo = formInfo;
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
        stage.getIcons().add(new Image(getClass().getResource("/fav.jpg").toURI().toString()));
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/wind_year.fxml"));
        Scene scene = new Scene(root, 550, 400);
        String image = MainController.class.getResource("/loginWind.jpg").toURI().toString();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
        root.setStyle("-fx-background-color: #e6fcff");

        stage.setScene(scene);
    }

    @Override
    public void run() {
        FormInfo formInfo = (FormInfo) ((SceneJson) rootNode.getScene()).getJson();
        int numDay = formInfo.getDate().Day;
        String dayOfMonth = (numDay < 10 ? "0" : "") + numDay;
        int monthInt = formInfo.getDate().Month;
        Month month = Month.of(monthInt);
        String monthDisp = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        int year = formInfo.getDate().Year;

        String country = formInfo.getCountry();
        String stationNumber = formInfo.getStationNumber();
        String height = formInfo.getHeight();


        for (int i = 1973; i < 1974; i++) {


            String rootDir= C.SOCANDARY_DATA_PATH+File.separator+country+File.separator+"year_"+i+File.separator+"month_"+monthInt+File.separator+stationNumber;


            System.out.println(monthDisp);
            System.out.println(dayOfMonth);

            String fileName="00Z_"+dayOfMonth+"_"+monthDisp+"_"+ i +".csv";
            String dayDir="assets/data";


            try {
                ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol(rootDir, fileName);
                resultArea.appendText("------------------------------------------");
                windSpeedCol.forEach(strings -> {
                    resultArea.appendText(strings.get(0)+"--->>>"+strings.get(1)+"\r\n");
                });
            } catch (IOException e) {
                Dialog.createExceptionDialog(e);
            }


        }



    }
}
