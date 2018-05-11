package com.analysis;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * is created by aMIN on 5/9/2018 at 20:08
 */
public class App extends Application {

    static int width = 450;
    static int height = 350;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("weather");



        GridPane rootNode = new GridPane();
        rootNode.setPadding(new Insets(10, 10, 10, 10));
        rootNode.setVgap(5);
        rootNode.setHgap(5);
        rootNode.setAlignment(Pos.CENTER);


        javafx.scene.layout.Pane closePane = new Pane();

        Image image = new Image("File:assets\\1x\\baseline_close_white_36dp.png");

        ImageView imageView = new ImageView(image);
//        imageView.setX(width - 2* image.getWidth());


        ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(-1.0);

        imageView.setEffect(blackout);
        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);
        closePane.getChildren().add(imageView);
        imageView.setOnMouseClicked(event -> {
            System.exit(0);

        });
        imageView.setOnMouseEntered(event -> {
            System.out.println(event.getX() + " gpd");
            imageView.setEffect(null);
        });

        imageView.setOnMouseExited(event -> {
            System.out.println(event.getX() + " gpd");
            imageView.setEffect(blackout);

        });
        GridPane.setConstraints(closePane,0,0);
        rootNode.getChildren().add(closePane);


        Scene myScene = new Scene(rootNode, width, height, Color.rgb(75, 75, 69));
        primaryStage.setScene(myScene);

        primaryStage.setResizable(true);
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }

}
