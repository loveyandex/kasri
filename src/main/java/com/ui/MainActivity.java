package com.ui;

import com.config.C;
import com.ui.dialogs.Dialog;
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
            Dialog.createDataDirChooser();
        else
            System.out.println(C.DATA_PATH);
//
//        Notifications notificationsBuilder=Notifications.create()
//                .graphic(null)
//                .hideAfter(Duration.millis(10000))
//                .text("god is great")
//                .onAction(event -> {
//                    primaryStage.toFront();
//                })
//                .title("launching.....")
//                .position(Pos.BOTTOM_RIGHT)
//                ;
//        notificationsBuilder.show();

    }


}
