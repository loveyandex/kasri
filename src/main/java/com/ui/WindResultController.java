package com.ui;

import com.jfoenix.controls.JFXButton;
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
import java.util.ResourceBundle;

/**
 * is created by aMIN on 6/8/2018 at 08:15
 */
public class WindResultController implements Initializable {
    public AnchorPane rootNode;
    public JFXButton back;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
}
