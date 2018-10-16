package test.alert1;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 10/10/2018 at 4:13 AM
 */
public class AlertAPp extends Application implements Initializable {
    @Override
    public void start(Stage primaryStage) throws Exception {

        JFXButton jfxButton = new JFXButton("add");
        jfxButton.setOnAction(event -> AlertMaker.showTrayMessage(String.format("Hello %s!", System.getProperty("user.name")), "God is great"));
        primaryStage.setScene(new Scene(new VBox(jfxButton)));
        primaryStage.getScene().getStylesheets().add("dark-theme.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initial");
    }
}
