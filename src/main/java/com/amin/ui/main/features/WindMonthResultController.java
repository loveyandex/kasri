package com.amin.ui.main.features;

import com.amin.jsons.FormInfo;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 6/8/2018 at 08:15
 */
public class WindMonthResultController implements Initializable, Runnable {
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
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/features/day/day.fxml"));
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

    }
}
