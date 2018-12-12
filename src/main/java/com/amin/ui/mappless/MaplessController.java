package com.amin.ui.mappless;

import com.amin.knnann.KNN;
import com.amin.pojos.LatLon;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.map.LatLongFXMLController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.amin.ui.StageOverride.shiftToEnterEvent;

public class MaplessController implements Initializable {

    public AnchorPane root;
    public JFXButton cancelbtn;
    public JFXTextField distanceradius;
    public Hyperlink hyperlink;
    public JFXButton KNNbtn;
    public JFXButton ANNbtn;
    public Hyperlink radiusLink;
    public Hyperlink hyperlinkCountryName;

    @FXML
    private JFXTextField longitude;
    @FXML
    private JFXTextField latitude;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shiftToEnterEvent(hyperlink, cancelbtn, KNNbtn, ANNbtn, radiusLink, hyperlinkCountryName);

    }


    @FXML
    private void annSolve(ActionEvent actionEvent) throws IOException {
        LatLon latLon = new LatLon(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
        final double nearst;
        try {
            nearst = KNN.nearst(radius, new LatLon(latLon.getLat(), latLon.getLogn()));

            System.out.println(nearst);
            if (nearst == -3.0E15d) {
                LatLongFXMLController.SnackBar.showSnack(root, "Data not found for this pos", 2333);

            } else {

                Stage stage = new StageOverride("/drawable/neo.png");
                stage.setResizable(true);
                Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/mappless/ann.fxml"));
                Scene scene = new SceneJson<>(root, 650, 480);
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(latLon);
                objects.add(radius);
                ((SceneJson) scene).setJson(objects);
                stage.setTitle("ANN Computing...");
                stage.setScene(scene);
//                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void knnSolve(ActionEvent event) throws SQLException {
        try {
            LatLon latLon = new LatLon(Double.parseDouble(latitude.getText()), Double.parseDouble(longitude.getText()));
            final double nearst = KNN.nearst(radius, new LatLon(latLon.getLat(), latLon.getLogn()));
            System.out.println(nearst);
            if (nearst == -3.0E15d) {
                LatLongFXMLController.SnackBar.showSnack(root, "Data not found for this pos", 2333);

            } else {

                Stage stage = new StageOverride();
                stage.setResizable(true);
                Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/mappless/otherfield.fxml"));
                Scene scene = new SceneJson<>(root, 650, 480);
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(latLon);
                objects.add(radius);
                ((SceneJson) scene).setJson(objects);
                stage.setTitle("KNN ...");


                stage.setScene(scene);
//                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();

            }
        } catch (NumberFormatException v) {
            Dialog.SnackBar.showSnack(root, v.getLocalizedMessage(), 3000);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        root.getScene().getWindow().hide();


    }

    public void hypringCityname(ActionEvent actionEvent) throws Exception {

        new SearchingView((latLon) -> {
            final double nearst = KNN.nearst(300, latLon);
            System.out.println(nearst);
            LatLongFXMLController.SnackBar.showSnack(root, String.valueOf(nearst), 2333);
            longitude.setText(String.valueOf(latLon.getLogn()));
            latitude.setText(String.valueOf(latLon.getLat()));
        }).start(new Stage());

    }

    public void hypringCuntryname(ActionEvent actionEvent) throws Exception {

        new SearchingView((latLon) -> {
            final double nearst = KNN.nearst(300, latLon);
            System.out.println(nearst);
            LatLongFXMLController.SnackBar.showSnack(root, String.valueOf(nearst), 2333);
            longitude.setText(String.valueOf(latLon.getLogn()));
            latitude.setText(String.valueOf(latLon.getLat()));
        }).start(new Stage());

    }

    double radius = 100;

    public void radiusAssigning(ActionEvent actionEvent) {
        try {
            new Application() {
                @Override
                public void start(Stage primaryStage) throws Exception {
                    JFXButton ok = new JFXButton("OK");

                    JFXTextField radiusJfxTextField = new JFXTextField(String.valueOf(radius));
                    radiusJfxTextField.setLabelFloat(true);
                    radiusJfxTextField.setPromptText("radius(km)");
                    shiftToEnterEvent(ok);
                    ok.setOnAction(event -> {
                        radius = Double.parseDouble(radiusJfxTextField.getText());
                        primaryStage.hide();
                    });
                    radiusJfxTextField.setOnAction(ok.getOnAction());
                    VBox vBox = new VBox(radiusJfxTextField, ok);
                    VBox.setMargin(radiusJfxTextField, new Insets(10, 20, 10, 20));
                    vBox.setAlignment(Pos.CENTER);
                    Scene sd = new SceneJson<>(vBox, 150, 80);
                    primaryStage.setScene(sd);
                    sd.getStylesheets().add("/dark-theme.css");
                    primaryStage.show();
                }
            }.start(new StageOverride());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
