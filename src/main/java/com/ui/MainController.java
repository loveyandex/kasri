package com.ui;

import com.analysis.WindMining;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 5/30/2018 at 21:54
 */
public class MainController implements Initializable {
    @FXML
    public VBox rootme;

    public Label outputLbl;

    public MenuBar menuBar;

    @FXML
    private TextArea textArea;

    @FXML
    private void onactionHandeler() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/chart.fxml"));
            Stage stage = new Stage();

            stage.setTitle("Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void fetch() {
        try {
            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol("assets/data/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");
            ;
            for (int j = 0; j < windSpeedCol.size(); j++)
                textArea.appendText(windSpeedCol.get(j).get(0) + ";" + windSpeedCol.get(j).get(1) + "\r\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void chart(ActionEvent actionEvent) {
        charting();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (resources!=null){
            Enumeration<String> keys = resources.getKeys();

        while (keys.hasMoreElements())
            System.out.println(keys.nextElement());
    } }


    public void charting() {
        final NumberAxis xAxis = new NumberAxis(1000, 30000, 1000);
        final NumberAxis yAxis = new NumberAxis(0, 360, 10);
        final ScatterChart<Number, Number> sc = new ScatterChart<Number, Number>(xAxis, yAxis);
        xAxis.setLabel("height");
        yAxis.setLabel("knot");
        sc.setTitle("wind-knot-m");

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series11 = new XYChart.Series();
        series1.setName("windspeed");

        try {
            ArrayList<ArrayList<String>> windSpeedCol = WindMining.getWindSpeedCol("assets/data/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");
            ;
            for (int j = 2; j < windSpeedCol.size() - 1; j++)
                series1.getData().add(new XYChart.Data((Double.parseDouble(windSpeedCol.get(j).get(0))), Double.parseDouble(windSpeedCol.get(j).get(1))));

        } catch (IOException e) {
            e.printStackTrace();
        }

//        sc.setPrefSize(500, 400);
        sc.getData().addAll(series1,series11);
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

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/chart.fxml"));
            ((VBox) root).getChildren().add(vbox);


            Stage stage = new Stage();
            stage.setTitle("Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

            add.setOnAction(event -> {
                stage.hide();
            });


            // Hide this current window (if this is what you want)


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void wind(ActionEvent actionEvent) throws IOException {
        Stage dialog = new Stage();
        dialog.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/wind_login.fxml"));
        Scene scene = new Scene(root, 450, 350);
        dialog.setScene(scene);
        dialog.initOwner(rootme.getScene().getWindow());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
