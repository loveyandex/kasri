package test.xx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LayoutExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(5);
        root.setPadding(new Insets(5));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(new Menu("File"));

        TextArea textArea = new TextArea();
        VBox.setVgrow(textArea, Priority.ALWAYS);

        AnchorPane bottomRow = new AnchorPane();
        Label table1 = new Label("Table 1");
        table1.setStyle("-fx-background-color: gray");
        table1.setMinSize(200, 200);
        Label table2 = new Label("Table 2");
        table2.setStyle("-fx-background-color: gray");
        table2.setMinSize(200, 200);

        AnchorPane.setLeftAnchor(table1, 0.0);
        AnchorPane.setRightAnchor(table2, 0.0);
        bottomRow.getChildren().addAll(table1, table2);

        root.getChildren().addAll(menuBar, textArea, bottomRow);

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}