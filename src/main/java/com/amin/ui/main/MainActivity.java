package com.amin.ui.main;

import com.amin.config.C;
import com.amin.ui.MainController;
import com.amin.ui.dialogs.Dialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * is created by aMIN on 5/29/2018 at 05:39
 */
public class MainActivity extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main_activity.fxml"));
        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.toBack();
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResource("/fav.jpg").toURI().toString()));
        String image = MainController.class.getResource("/fav.jpg").toURI().toString();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: repeat ;");

        if (C.DATA_PATH.isEmpty())
            Dialog.createDataDirChooser("data_path");
        else if (C.SOCANDARY_DATA_PATH.isEmpty())
            Dialog.createDataDirChooser("secondary_data_path");

    }


}
