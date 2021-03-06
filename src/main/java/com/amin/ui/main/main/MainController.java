package com.amin.ui.main.main;

import com.amin.config.C;
import com.amin.database.database.DatabaseHandler;
import com.amin.getdata.Starter;
import com.amin.ui.SceneJson;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import com.amin.ui.map.LatLongMainApp;
import com.amin.ui.scripts.Scripting;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.amin.config.C.writePropertie;

/**
 * is created by aMIN on 5/30/2018 at 21:54
 */
public class MainController implements Initializable {
    public JFXButton ondayoneheight;
    public JFXButton ondayallheights;
    public JFXButton allstninoneday;
    public JFXButton wholeconcurrent;
    public JFXButton wholeparallel;
    public JFXButton scriptbtn;
    public JFXButton mapbtn;
    public JFXButton maplessbtn;
    public MenuBar menuBar;
    public StackPane stackpane;
    public JFXButton acceptButton;
    public VBox isloadingvbox;
    public JFXButton earth;
    public VBox otherroot;
    @FXML
    private VBox rootme;
    @FXML
    private JFXDialog dialog;


    @FXML
    private void exit() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ondayoneheight.setOnAction(this::abdout);

        new Thread(() -> {
            DatabaseHandler.getInstance();
            isloadingvbox.setVisible(false);
//            Platform.runLater(() -> primaryStage.show());
        }).start();

        StageOverride.shiftToEnterEvent(ondayoneheight, ondayallheights, allstninoneday, wholeconcurrent, scriptbtn, mapbtn, maplessbtn);

        Platform.runLater(() -> {
            otherroot.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                System.out.println(event.getCode());
            });
            dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
            dialog.show(stackpane);
            dialog.close();


        });

        Tooltip tooltip1 = new Tooltip(ondayoneheight.getText());
        tooltip1.setStyle("-fx-show-duration: 0.01s ;");

        ondayoneheight.setTooltip(tooltip1);


        rootme.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D && event.isControlDown()) {
                onday(null);
            } else if (event.getCode() == KeyCode.M && event.isControlDown()) {
                onMap(null);
            } else if (event.getCode() == KeyCode.S && event.isControlDown()) {
                openscript(null);
            } else if (event.getCode() == KeyCode.L && event.isControlDown()) {
                try {
                    onMapless(null);
                } catch (IOException e) {
                    Dialog.createExceptionDialog(e);
                }
            } else if (event.getCode() == KeyCode.H && event.isControlDown()) {
                halpanddoc(null);
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
        Starter starter = new Starter();
        Stage stage = new Stage();
        starter.start(stage);
    }


    public void onday(ActionEvent actionEvent) {
        //            abdout(actionEvent);
        isloadingvbox.setVisible(true);

    }


    public void loadAddBook(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/statistic.fxml"));
        root.setStyle("-fx-padding: 30 30 30 30 ");
        SceneJson scene = new SceneJson<>(root);
        ArrayList<ArrayList> json = new ArrayList<>();
        ArrayList<Object> kaArrayList = new ArrayList();
        for (int i = 0; i < 5; i++) {
            ArrayList e = new ArrayList();
            e.add(23.32 + i);
            e.add(2016 + i);
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
        System.out.println("in whole");
        stage.show();


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
            new Scripting().start(new StageOverride());
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
        Platform.runLater(() -> {
            JFXTooltip jfxTooltip = new JFXTooltip("god is great kijng ", rootme);
            jfxTooltip.show(rootme.getScene().getWindow());
        });

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

    public void abdout(ActionEvent actionEvent) {
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/someday/day/somedays.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new SceneJson<>(root);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onMapless(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/mappless/mapless.fxml"));
        Scene scene = new SceneJson<>(root);
        stage.setScene(scene);
        stage.initOwner(rootme.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void earth(ActionEvent actionEvent) {
        new Thread(() -> {
            try {
                Runtime.getRuntime().exec("cmd.exe /k start earth-server.bat");
                Runtime.getRuntime().exec("cmd.exe /k cd positron && amin.exe");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }

    public void clear(ActionEvent actionEvent) throws IOException, URISyntaxException {
        writePropertie(C.THIRDY_PATH_NAME, "");
        writePropertie(C.DATA_PATH_NAME, "");
        writePropertie(C.SOCANDARY_DATA_PATH_NAME, "");

        C.THIRDY_PATH = "";
        C.SOCANDARY_DATA_PATH = "";
        C.DATA_PATH = "";
    }

    public void halpanddoc(ActionEvent actionEvent) {
        new Thread(() -> {
            try {
                String cd = "cmd.exe /k taskkill /f /im node.exe";
//                Runtime.getRuntime().exec(cd);

                cd = "cmd.exe /k start cd docs-deploy & ..\\http-server\\bin\\http-server.cmd  -o";
                Runtime.getRuntime().exec("cmd.exe /k start doc-server.bat");


//                Runtime.getRuntime().exec(cd);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }




    public void dataFormatiing(ActionEvent actionEvent) throws IOException {
        Stage stage = new StageOverride();
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource(C.RAW_FMXL_PATH));
        System.out.println("amin");
        System.out.println(C.DATA_PATH + " DATA_PATH");
        System.out.println(C.SOCANDARY_DATA_PATH + " Secondary");
        System.out.println(C.THIRDY_PATH + " Third");

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("/dark-theme.css");

        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
        System.out.println("myth " + Thread.activeCount());

    }
}
