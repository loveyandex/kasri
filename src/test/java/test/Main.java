package test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title");

        final Circle circ = new Circle(40, 40, 30);
        final Group root = new Group(circ);

//        final Scene scene = new Scene(root, 800, 400, Color.rgb(61,64,72));
        final Scene scene = new Scene(root, 800, 400, Color.rgb(54,60,65));


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
