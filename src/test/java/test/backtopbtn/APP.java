package test.backtopbtn;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        closeButton.setStyle("-fx-background-radius: 0;-fx-background-color: #ff667d;-fx-text-fill: white");

        closeButton.setMinWidth(30);
        Button oth = new Button();
        oth.setStyle("-fx-background-radius: 0;-fx-border-radius: 0");

//        oth.setDisable(true);
        oth.setMinWidth(270);


        TextArea console = new TextArea(">>");
        console.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ;
            }
        });

        console.caretPositionProperty().addListener((obs, oldPosition, newPosition) -> {
            String text = console.getText().substring(0, newPosition.intValue());
            int index;
            for (index = text.length() - 1; index >= 0 && !Character.isWhitespace(text.charAt(index)); index--) ;
            String prefix = text.substring(index + 1, text.length());
            if (text.charAt(text.length())=='\n') {
                console.appendText(">>");

            }
        });
        console.caretPositionProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });








        HBox hBox= new HBox(oth,closeButton);
        VBox vBox = new VBox(hBox, file, console);
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

        Scene scene = new Scene(layout, 300, 200, Color.rgb(75, 75, 69));
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
        oth.focusedProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(observable.getClass().getName());
        });

        ResizeHelper.addResizeListener(primaryStage);

    }
}
