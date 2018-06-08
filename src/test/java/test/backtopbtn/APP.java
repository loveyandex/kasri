package test.backtopbtn;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * is created by aMIN on 6/8/2018 at 04:43
 */
public class APP extends Application {
    private BorderPane layout;
    private Stage window;
    private double xOffset = 0;
    private double yOffset = 0;


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
        closeButton.setMinWidth(30);
        Button oth = new Button();
//        oth.setDisable(true);
        oth.setMinWidth(270);

        HBox hBox= new HBox(oth,closeButton);
        VBox vBox=new VBox(hBox,file);
        closeButton.setOnAction(event -> {
            Platform.exit();
        });
//        closeButton.setAlignment(Pos.TOP_RIGHT);

        file.getMenus().addAll(
                fileMenu,
                editMenu

        );

        layout = new BorderPane();
        layout.setTop(vBox);;

        Scene scene = new Scene(layout, 300, 200);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
//        window.setMaximized(true);
        window.initStyle(StageStyle.UNDECORATED);
        window.show();

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            oth.setMinWidth(((double) newValue)-30);

        });

        oth.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        oth.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        oth.setFocusTraversable(false);
//        closeButton.setFocusTraversable(false);
        oth.setStyle("-fx-border-radius: 0 0 0 0");
        oth.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(observable.getClass().getName());
        });

        ResizeHelper.addResizeListener(primaryStage);

    }
}
