package com.amin.ui.main.main;

import com.amin.config.C;
import com.amin.ui.SceneJson;
import com.amin.ui.SceneMainActivity;
import com.amin.ui.StageOverride;
import com.amin.ui.dialogs.Dialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * is created by aMIN on 5/29/2018 at 05:39
 */
public class MainActivity extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    private void closeAlertDialog(Stage primaryStage) throws IOException {
        Stage stage = new StageOverride();
        stage.initStyle(StageStyle.UTILITY);
        System.err.println("undecorated");
        stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/com/amin/ui/alert.fxml"));
        Scene scene = new SceneJson<>(root);
        stage.setScene(scene);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(MainActivity.class.getResource("/com/amin/ui/main/main/main_activity.fxml"));
        Scene scene = new SceneMainActivity<>(root, 1100, 600);

        scene.getStylesheets().add(C.THEME);


        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            try {
                closeAlertDialog(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        primaryStage.setOnShowing(System.out::println);


        primaryStage.setTitle("WA");
        primaryStage.setScene(scene);
        primaryStage.toBack();
        primaryStage.getIcons().add(new Image(getClass().getResource("/drawable/uav.png").toURI().toString()));
        primaryStage.show();
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

        System.out.println("now path is");
        System.out.println(C.DATA_PATH);
        System.out.println(C.SOCANDARY_DATA_PATH);
        System.out.println(C.THIRDY_PATH);



        System.out.println("after_out_of_thread");
    }

    public interface AfterLoaded {
        void doThis();
    }

}

