package test;

import com.amin.io.MyWriter;
import com.amin.io.Waching;
import com.amin.ui.scripts.ScriptAPP;
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

import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.List;

/**
 * is created by aMIN on 5/9/2018 at 20:08
 */
public class App extends Application {

    static int width = 450;
    static int height = 350;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("weather");
        new Thread(() -> {
            Waching.changeFileLisnter(System.getProperty("user.dir"), "config", pathInChanging -> {
                if (pathInChanging.endsWith("parallel.txt")) {
                    System.out.println(pathInChanging.toFile().getAbsolutePath());
                    System.exit(0);
                }

            }, StandardWatchEventKinds.ENTRY_MODIFY);
        }).start();


        Thread.sleep(2323);

        new MyWriter("config/", "parallel.txt", true)
                .appendStringInNewLine("ll");



        ScriptAPP.scripting("onday 40800 10 26 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");



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
//            System.exit(0);
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
