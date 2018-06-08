package test.backtopbtn;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * is created by aMIN on 6/8/2018 at 05:08
 */
public class K extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 0, 20, 20));

        Button btnAdd = new Button("Add");
        Button btnDelete = new Button("Delete");
        Button btnMoveUp = new Button("Move Up");
        Button btnMoveDown = new Button("Move Down");

        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnDelete.setMaxWidth(Double.MAX_VALUE);
        btnMoveUp.setMaxWidth(Double.MAX_VALUE);
        btnMoveDown.setMaxWidth(Double.MAX_VALUE);

        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20));
        vbButtons.getChildren().addAll(btnAdd, btnDelete, btnMoveUp, btnMoveDown);

        primaryStage.setScene(new Scene(border));
        primaryStage.show();

    }
}
