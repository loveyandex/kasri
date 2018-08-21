package com.amin.ui.main.main;

import com.amin.config.C;
import com.amin.ui.SceneJson;
import com.amin.ui.SceneMainActivity;
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
public class MainActivity extends Application  {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/main/main/main_activity.fxml"));
        Scene scene = new SceneMainActivity<>(root, 1000, 500);
        primaryStage.setOnCloseRequest(event -> {
            scene.getWindow().hide();
            System.exit(0);

        });


        primaryStage.setTitle("Mining");
        primaryStage.setScene(scene);
        primaryStage.toBack();
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResource("/logo.png").toURI().toString()));
//        String image = MainController.class.getResource("/logo.png").toURI().toString();
//        root.setStyle("-fx-background-image: url('" + image + "'); " +
//                "-fx-background-position: center center; " +
//                "-fx-background-repeat: stretch ;");

        if (C.DATA_PATH.isEmpty())
            Dialog.createDataDirChooser("data_path");
        if (C.SOCANDARY_DATA_PATH.isEmpty())
            Dialog.createDataDirChooser("secondary_data_path");
        if (C.THIRDY_PATH.isEmpty())
            Dialog.createDataDirChooser("thirdy_data_path");

        System.out.println(C.DATA_PATH);
        System.out.println(C.SOCANDARY_DATA_PATH);
        System.out.println(C.THIRDY_PATH);
    }













}


