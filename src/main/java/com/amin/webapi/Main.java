package com.amin.webapi;

import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/*  ww w .jav  a 2  s.  co  m*/
public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) {

        WebView webView = new WebView();


        WebEngine webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == State.SUCCEEDED) {
                        System.out.println("finished loading");
                    }
                }); // addListener()

        // begin loading...
        webEngine.load("http://localhost:8080/greeting");


        Group root = new Group();
        root.getChildren().add(webView);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}