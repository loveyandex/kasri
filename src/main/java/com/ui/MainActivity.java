package com.ui;

import com.analysis.WindMining;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * is created by aMIN on 5/29/2018 at 05:39
 */
public class MainActivity extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/main_activity.fxml"));



        Scene scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
        startME();

    }

    public void startME() {
        final NumberAxis xAxis = new NumberAxis(1000, 30000, 1000);
        final NumberAxis yAxis = new NumberAxis(0, 360, 10);
        final ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);
        xAxis.setLabel("Age (years)");
        yAxis.setLabel("Returns to date");
        sc.setTitle("Investment Overview");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Option 1");

        try {
            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol("assets/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");

            for (int j = 2; j < windSpeedCol.size() - 1; j++)
                series1.getData().add(new XYChart.Data((Double.parseDouble(windSpeedCol.get(j).get(0))), Double.parseDouble(windSpeedCol.get(j).get(1))));

        } catch (IOException e) {
            e.printStackTrace();
        }


//        sc.setPrefSize(500, 400);
        sc.getData().addAll(series1);
        final VBox vbox = new VBox();
        final HBox hbox = new HBox();
        vbox.setLayoutY(300);
        vbox.setLayoutX(400);

        final Button add = new Button("Add Series");
        final Button remove = new Button("Remove Series");
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!sc.getData().isEmpty())
                    sc.getData().remove((int) (Math.random() * (sc.getData().size() - 1)));
            }
        });

        final DropShadow shadow = new DropShadow();
        shadow.setOffsetX(2);
        shadow.setColor(Color.GREY);
        sc.setEffect(shadow);

        hbox.setSpacing(10);
        hbox.getChildren().addAll(add, remove);

        vbox.getChildren().addAll(sc, hbox);
        hbox.setPadding(new Insets(10, 10, 10, 50));

        VBox vBoxxx = new VBox(vbox);
        Scene scene = new Scene(vBoxxx, 400, 300, Color.BLACK);
        new Stage().setScene(scene);
    }


}
