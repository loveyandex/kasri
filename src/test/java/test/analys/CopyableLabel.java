package test.analys;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CopyableLabel extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField copyable = new TextField("Copy this");
        copyable.setEditable(false);
        copyable.getStyleClass().add("copyable-label");

        TextField tf2 = new TextField();
        VBox root = new VBox();
        root.getChildren().addAll(copyable, tf2);
        Scene scene = new Scene(root, 250, 150);
        scene.getStylesheets().add(getClass().getResource("copy.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}