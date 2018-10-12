package com.amin.ui.web;


import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;

/*  ww w .jav  a 2  s.  co  m*/
public class Server extends Application {
    public static void main(String[] args) {
        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        final WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == State.SUCCEEDED) {
                        System.out.println("finished loading");
                    }
                }); // addListener()
        webEngine.load("http://127.0.0.1:8080");
//        webEngine.load("https://www.w3schools.com/");

        browser.setContextMenuEnabled(false);
        createContextMenu(browser);
        Group root = new Group();
        root.getChildren().add(browser);
        Scene scene = new Scene(root, 300, 250);

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            browser.setMinHeight(newValue.doubleValue());
        });

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            browser.setMinWidth(newValue.doubleValue());
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createContextMenu(WebView webView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem reload = new MenuItem("Reload");
        reload.setOnAction(e -> webView.getEngine().reload());
        MenuItem savePage = new MenuItem("Save Page");
        savePage.setOnAction(e -> System.out.println("Save page..."));
        MenuItem hideImages = new MenuItem("Hide Images");
        hideImages.setOnAction(e -> System.out.println("Hide Images..."));
        contextMenu.getItems().addAll(reload, savePage, hideImages);

        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }

}