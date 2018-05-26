package test;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;

/**
 * is created by aMIN on 5/26/2018 at 06:25
 */
public class Noti extends Application {
    public static void main(String[] args) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        showNotificationPane(primaryStage);
    }

    public void showNotificationPane(Stage stage) {
        Scene scene = stage.getScene();
        Parent pane = scene.getRoot();
        if (!(pane instanceof NotificationPane)) {
            NotificationPane notificationPane = new NotificationPane(pane);
            scene = new Scene(notificationPane, scene.getWidth(), scene.getHeight());
            stage.setScene(scene);
            notificationPane.show();
        } else {
            ((NotificationPane) pane).show();
        }
    }

}
