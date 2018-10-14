package com.amin.ui.mappless;

import com.amin.knn.KNN;
import com.amin.pojos.LatLon;
import com.amin.ui.map.LatLongFXMLController;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MaplessController implements Initializable {

    private final static Logger LOGGER = LogManager.getLogger(MaplessController.class.getName());
    public AnchorPane root;

    @FXML
    private JFXTextField longitude;
    @FXML
    private JFXTextField latitude;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws SQLException {
        LatLon latLon = new LatLon(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
        final double nearst = KNN.nearst(300, new LatLon(latLon.getLat(), latLon.getLogn()));
        System.out.println(nearst);
        LatLongFXMLController.SnackBar.showSnack(root, String.valueOf(nearst), 2333);


    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage) latitude.getScene().getWindow()).close();
    }


}
