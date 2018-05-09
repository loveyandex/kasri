package com.analysis;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * is created by aMIN on 5/9/2018 at 20:08
 */
public class App extends Application {

   static int width = 450;
   static int height = 350;
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("weather");
        primaryStage.initStyle(StageStyle.DECORATED);


        GridPane rootNode = new GridPane();

        rootNode.setAlignment(Pos.CENTER);



        javafx.scene.layout.Pane closePane = new Pane();

        Image image = new Image("File:assets\\1x\\baseline_close_white_36dp.png");

        ImageView imageView = new ImageView(image);
        imageView.setX(width-4*image.getWidth());


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


        rootNode.add(closePane, 2, 0);


        Label label = new Label("absolute root path for saving :");
        label.setAlignment(Pos.CENTER);
//        rootNode.add(label, 0, 0, 2, 1);

        TextField firstValue = new TextField("G:\\Program Files\\AMinAbvall\\kasridata\\iran\\year_2017\\month_1\\40754.data");
        rootNode.add(firstValue, 2, 2,2,2);
        firstValue.setAlignment(Pos.CENTER);




        Button aButton = new Button("start getting Data");
        rootNode.add(aButton, 1, 4);
        GridPane.setHalignment(aButton, HPos.CENTER);


        aButton.setOnMouseClicked(event -> {
            try {
                new RawMining(firstValue.getText()).readFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });




        ProgressIndicator pbar = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
        pbar.setVisible(true);
        rootNode.add(pbar, 1, 5);

        final Group root = new Group(rootNode);

        Scene myScene = new Scene(root, width, height,Color.rgb(54,60,65));
        primaryStage.setScene(myScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
