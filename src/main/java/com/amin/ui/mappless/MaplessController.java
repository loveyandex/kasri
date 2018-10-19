package com.amin.ui.mappless;

import com.amin.knn.KNN;
import com.amin.pojos.LatLon;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.map.LatLongFXMLController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MaplessController implements Initializable {

    public AnchorPane root;
    public JFXButton cancelbtn;
    public JFXTextField distanceradius;
    public Hyperlink hyperlink;

    @FXML
    private JFXTextField longitude;
    @FXML
    private JFXTextField latitude;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws SQLException {
        try {
            LatLon latLon = new LatLon(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
            final double nearst = KNN.nearst(300, new LatLon(latLon.getLat(), latLon.getLogn()));
            System.out.println(nearst);
            LatLongFXMLController.SnackBar.showSnack(root, String.valueOf(nearst), 2333);

            Stage stage = new StageOverride();
            stage.setResizable(true);
            Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/mappless/otherfield.fxml"));
            Scene scene = new SceneJson<>(root, 750, 600);
            ((SceneJson) scene).setJson(latLon);

            stage.setScene(scene);
//            stage.initOwner(root.getScene().getRoot().getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        }catch (NumberFormatException v){
            Dialog.SnackBar.showSnack(root,v.getLocalizedMessage(),3000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        root.getScene().getWindow().hide();

    }

    public void hypring(ActionEvent actionEvent) throws Exception {

        new ListViewExperiments((latLon) -> {
            final double nearst = KNN.nearst(300, latLon);
            System.out.println(nearst);
            LatLongFXMLController.SnackBar.showSnack(root, String.valueOf(nearst), 2333);

        }).start(new Stage());

    }

}
