package com.ui;

import com.analysis.WindMining;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */
public class WindLoginController implements Initializable {


    public VBox root23;

    public void per(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CalendarPicker<PersianCalendar> picker = CalendarPicker.persianWithSystemDefaults();
        picker.setStyle("");


        root23.getChildren().add(picker);

        picker.setLengthOfAnimations(Duration.seconds(0));
        picker.setShowInfoLabel(true);
        picker.setLocale(new Locale("fa", "IR"));
        picker.setShowWeeks(true);
        picker.setCellCustomizer((cell, column, row, model, date) -> {
//                    if (CellCustomizer.isWeekend(column, model)) {
//                        cell.setStyle("-fx-background-color: #FFE0E0;");
//                        cell.setDisable(true);
//                    }
                });

        picker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("godIsGreat");
            int dayOfMonth = picker.valueProperty().getValue().getDayOfMonth();
            System.out.println(dayOfMonth);
            int year = picker.valueProperty().getValue().getYear();
            System.out.println(year);
            int momth = picker.valueProperty().getValue().getMonth().getValue();
            System.out.println(momth);



            System.out.println("------------------------godisgreat-------");
            PersianCalendar persianCalendar = picker.valueProperty().getValue();
            PlainDate plainDate = persianCalendar.transform(PlainDate.class);
            System.out.println(plainDate.getYear());
            System.out.println(plainDate.getMonth());
            System.out.println(plainDate.getDayOfMonth());


        });

        DatePicker datePicker=new DatePicker();
        datePicker.getEditor().setOnAction(event -> {
            System.out.println("god is for me");
        });
        root23.getChildren().add(datePicker);



    }




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

}
