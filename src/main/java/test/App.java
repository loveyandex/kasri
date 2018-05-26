package test;

/**
 * is created by aMIN on 5/26/2018 at 05:00
 */

import com.analysis.WindMining;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

//from   ww w .  ja va 2  s  .c o  m
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Group group = new Group();
        group.getChildren().add(root);
        Scene scene = new Scene(group, new Dimension().getWidth()/2, new Dimension().getHeight()/2, Color.WHITE);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);


        // File menu - new, save, exit
        javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");

        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(false);
        pbar.setTranslateX(232);

        Button button1 = new Button("select All");
        Button button2 = new Button("Button Number 2");



        HBox hbox = new HBox(button1, button2);
        TextArea textArea = new TextArea();

        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            System.exit(0);
        });
        try {
            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol("assets/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");

            for (int j = 0; j < windSpeedCol.size(); j++)
                textArea.appendText(windSpeedCol.get(j).get(0)+";"+windSpeedCol.get(j).get(1)+"\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        button1.setOnAction( event -> {
            textArea.selectAll();
            textArea.copy();
        });



        VBox vbox = new VBox(textArea);


        hbox.setLayoutX(232);
        hbox.setLayoutY(333);
        vbox.setLayoutX(50);
        vbox.setLayoutY(100);
        group.getChildren().addAll(pbar,vbox,hbox);

        newMenuItem.setOnAction(event -> {
            pbar.setVisible(true);
        });



        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        javafx.scene.control.Menu webMenu = new javafx.scene.control.Menu("Web");
        CheckMenuItem htmlMenuItem = new CheckMenuItem("HTML");
        htmlMenuItem.setSelected(true);
        webMenu.getItems().add(htmlMenuItem);

        CheckMenuItem cssMenuItem = new CheckMenuItem("CSS");
        cssMenuItem.setSelected(true);
        webMenu.getItems().add(cssMenuItem);

        javafx.scene.control.Menu sqlMenu = new javafx.scene.control.Menu("SQL");
        ToggleGroup tGroup = new ToggleGroup();
        RadioMenuItem mysqlItem = new RadioMenuItem("MySQL");
        mysqlItem.setToggleGroup(tGroup);

        RadioMenuItem oracleItem = new RadioMenuItem("Oracle");
        oracleItem.setToggleGroup(tGroup);
        oracleItem.setSelected(true);

        sqlMenu.getItems().addAll(mysqlItem, oracleItem,
                new SeparatorMenuItem());

        javafx.scene.control.Menu tutorialManeu = new javafx.scene.control.Menu("Tutorial");
        tutorialManeu.getItems().addAll(
                new CheckMenuItem("Java"),
                new CheckMenuItem("JavaFX"),
                new CheckMenuItem("Swing"));

        sqlMenu.getItems().add(tutorialManeu);

        menuBar.getMenus().addAll(fileMenu, webMenu, sqlMenu);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}