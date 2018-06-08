package test.backtopbtn;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * is created by aMIN on 6/8/2018 at 04:43
 */
public class APP extends Application {
    private BorderPane layout;
    private Stage window;

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Menu Example");


        MenuBar file = new MenuBar();
        file.setId("file");

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                new MenuItem("New File..."),
                new MenuItem("Open file..."),
                new MenuItem("Save file"));
        fileMenu.setId("#fileMenu");



        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(
                new MenuItem("Undo"),
                new MenuItem("Redo"),
                new MenuItem("Cut"),
                new MenuItem("Copy"),
                new MenuItem("Paste")
        );

        Button closeButton = new Button("X");
        closeButton.setOnAction(event -> {
            Platform.exit();
        });
//        closeButton.setAlignment(Pos.TOP_RIGHT);

        file.getMenus().addAll(
                fileMenu,
                editMenu

        );

        layout = new BorderPane();
        layout.setTop(file);
        layout.setRight(closeButton);

        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
//        window.setMaximized(true);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();

        ResizeHelper.addResizeListener(primaryStage);

    }
}
