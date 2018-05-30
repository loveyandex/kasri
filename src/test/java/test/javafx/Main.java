package test.javafx;

/**
 * is created by aMIN on 5/30/2018 at 21:28
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] arguments) {
        Application.launch(Main.class, arguments);
    }


    @Override
    public void start(final Stage stage) throws Exception {
        final Parent fxmlRoot = FXMLLoader.load(getClass().getResource("/Javafx.fxml"));
        stage.setScene(new Scene(fxmlRoot));
        stage.show();
    }
}
