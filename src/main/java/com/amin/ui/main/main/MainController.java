package com.amin.ui.main.main;

import com.amin.getdata.Starter;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.map.LatLongMainApp;
import com.amin.ui.scripts.ScriptAPP;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 5/30/2018 at 21:54
 */
public class MainController implements Initializable {
    @FXML
    private VBox rootme;

    public Label outputLbl;

    public MenuBar menuBar;
    public JFXButton alertButton;
    public JFXButton bottombtn;
    public StackPane stackpane;
    public JFXButton acceptButton;

    @FXML
    private JFXDialog dialog;
    @FXML
    private TextArea textArea;

    @FXML
    private void onactionHandeler() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/chart.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void exit() {
        System.exit(0);
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM
            );
            dialog.show(stackpane);
            dialog.close();

        });
        rootme.setOnKeyPressed(event -> {
             if (event.getCode()== KeyCode.D &&  event.isControlDown()) {
                 onday(null);
             } else if (event.getCode() == KeyCode.M && event.isControlDown()) {
                 onMap(null);
             } else if (event.getCode() == KeyCode.S && event.isControlDown()) {
                 openscript(null);
             }
        });





        acceptButton.setOnAction(action -> dialog.close());

    }

    public void windyear(ActionEvent actionEvent) throws IOException, URISyntaxException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResource("/fav.jpg").toURI().toString()));

        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/wind_year.fxml"));
        Scene scene = new Scene(root, 550, 550);
        String image = MainController.class.getResource("/loginWind.jpg").toURI().toString();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
        root.setStyle("-fx-background-color: #e6fcff");

        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }



    public void dayFeature(ActionEvent actionEvent) throws IOException, URISyntaxException {
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/day/day.fxml"));
        Scene scene = new SceneJson<>(root, 750, 600);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    public void getDataFromInternet(ActionEvent actionEvent) {
        Starter starter=new Starter();
        Stage stage=new Stage();
        starter.start(stage);
    }


    public void onday(ActionEvent actionEvent) {
        try {
            dayFeature(actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void loadAddBook(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");
        SceneJson scene = new SceneJson<>(root);
        ArrayList<ArrayList> json = new ArrayList<>();
        ArrayList<Object> kaArrayList=new ArrayList();
        for (int i = 0; i < 5; i++) {
            ArrayList e = new ArrayList();
            e.add(23.32+i);
            e.add(2016+i);
            json.add(e);
        }
        scene.setJson(json);
        Stage primaryStage = new StageOverride();
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void allstationsinone(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setTitle("all stations of a country");
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/AllStationsOfCountryInOneDay/allstationsofcountry.fxml"));
        Scene scene = new SceneJson<>(root, 750, 600);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void wholestationsallyearmthread(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setTitle("whole of the year in all stations of a country");
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/wholeyear/concurent/wholeyearallstationsofcountry.fxml"));
        Scene scene = new SceneJson<>(root, 750, 600);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void wholestationsallyear(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setTitle("whole of the year in all stations of a country");
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/wholeyear/parallel/wholeyearallstationsofcountry.fxml"));
        Scene scene = new SceneJson<>(root, 750, 600);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void openscript(ActionEvent actionEvent) {
        try {
            new ScriptAPP().start(new StageOverride());
        } catch (Exception e) {
            Dialog.createExceptionDialog(e);
        }
    }

    public void onMap(ActionEvent actionEvent) {
        try {
            new LatLongMainApp().start(new StageOverride());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Dialog.SnackBar.showSnack(rootme,"comming sooon...");
    }


    public void ff(ActionEvent actionEvent) {
        Platform.runLater(()->{
            JFXTooltip jfxTooltip=new JFXTooltip("god is great kijng ",rootme);
            jfxTooltip.show(rootme.getScene().getWindow());
        });

    }

    public void latlong(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/latlong.fxml"));
        Scene scene = new SceneJson<>(root, 750, 600);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();


    }

    public void ondayAllHeight(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/allheight/antiheight.fxml"));
        Scene scene = new SceneJson<>(root, 750, 600);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void about(ActionEvent actionEvent) {
        dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM
        );
        dialog.show(stackpane);

    }

    public void onMapless(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/mappless/mapless.fxml"));
        Scene scene = new SceneJson<>(root);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }
}
