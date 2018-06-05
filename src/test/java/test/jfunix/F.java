package test.jfunix;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * is created by aMIN on 6/5/2018 at 00:38
 */
public class F extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        JFXTabPane tabPane = new JFXTabPane();
        tabPane.setPrefSize(300, 200);
        Tab tab = new Tab();
        tab.setText("Tab 1");
        tab.setContent(new Label("Content"));
        tabPane.getTabs().add(tab);
        tab.setStyle("-fx-background-color: #D40C49;");
        Tab tab5 = new Tab();
        tab5.setText("Tab 1");
        tab5.setContent(new Label("Content"));
        tabPane.getTabs().add(tab5);
        tab5.setStyle("-fx-background-color: #D40C49;");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("bal.png")));


        primaryStage.setScene(new Scene(tabPane));
        primaryStage.show();
    }
}
