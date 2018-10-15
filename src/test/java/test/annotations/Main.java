package test.annotations;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Password Window");

        Scene scene = new Scene(button);
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(event -> System.out.println(openPasswordWindow()));
    }

    private String openPasswordWindow(){
        PasswordField passwordField = new PasswordField();
        Stage stage = new Stage(StageStyle.UTILITY);
        stage.setScene(new Scene(passwordField));
        stage.show();

        //You would need from here 
        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if(!stage.isFocused())
                Platform.runLater(() -> stage.close());
        });
        //Down to here

        return passwordField.getText();
    }
}