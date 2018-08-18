package test.analys;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import test.sounds.Tes;


public class MultipleStageStyles extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setOpacity(0);
        stage.setHeight(0);
        stage.setWidth(0);
        stage.show();

        new Thread(() -> {
            try {
                new Tes().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }}).start();

    }
}