package test.analys;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.RadioMenuItemBuilder;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Submenu extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menus");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250, Color.WHITE);
        
        MenuBar menuBar = new MenuBar();
        
        Menu menu = new Menu("Item");

        Menu contingencyPlans = new Menu("Submenu");
        contingencyPlans.getItems().add(new CheckMenuItem("Item 1"));
        contingencyPlans.getItems().add(new CheckMenuItem("Item 2"));
        contingencyPlans.getItems().add(new CheckMenuItem("Item 3"));
        
        menu.getItems().add(contingencyPlans);
        
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        menuBar.getMenus().add(menu);
        
        root.getChildren().add(menuBar); 
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}