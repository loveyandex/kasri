package test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;

/**
 * is created by aMIN on 5/28/2018 at 14:39
 */
public class Pane extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a WebView
        WebView webView = new WebView();
        webView.getEngine().load("http://google.com");

        // Wrap it inside a NotificationPane
        NotificationPane notificationPane = new NotificationPane(webView);

        // and put the NotificationPane inside a Tab
        Tab tab1 = new Tab("Tab 1");
        tab1.setContent(notificationPane);
 // and put the NotificationPane inside a Tab
        Tab tab11 = new Tab("Tab 1");
        tab1.setContent(notificationPane);

        // and the Tab inside a TabPane. We just have one tab here, but of course
        // you can have more!
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(tab1,tab11);
        notificationPane.setText("Do you want to save your password?");
        notificationPane.show();


        Group group=new Group();
        group.getChildren().addAll(tabPane);


        primaryStage.setScene(new Scene(group));
        primaryStage.show();


    }
}
