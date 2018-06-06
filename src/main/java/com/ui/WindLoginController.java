package com.ui;

import com.analysis.Mapping;
import com.config.C;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */
public class WindLoginController implements Initializable {


    public GridPane rootNode;

    @FXML
    public JFXComboBox<Label> stationsCombo;
    @FXML
    public JFXComboBox<Label> countriesCombo;
    public JFXButton cancelBtn;
    public JFXButton Gobtn;


    @FXML
    CalendarPicker<PersianCalendar> persianCalendarCalendarPicker;

    public void per(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rootNode.setAlignment(Pos.CENTER);

        persianCalendarCalendarPicker = CalendarPicker.persianWithSystemDefaults();
        persianCalendarCalendarPicker.setStyle("-fx-padding: 0 0 10 0");


//        rootNode.getChildren().add(persianCalendarCalendarPicker);
        rootNode.add(persianCalendarCalendarPicker, 1, 1);

        persianCalendarCalendarPicker.setLengthOfAnimations(Duration.seconds(0));
        persianCalendarCalendarPicker.setShowInfoLabel(true);
        persianCalendarCalendarPicker.setLocale(new Locale("fa", "IR"));
        persianCalendarCalendarPicker.setShowWeeks(true);
        persianCalendarCalendarPicker.setCellCustomizer((cell, column, row, model, date) -> {
//                    if (CellCustomizer.isWeekend(column, model)) {
//                        cell.setStyle("-fx-background-color: #FFE0E0;");
//                        cell.setDisable(true);
//                    }
                });

        persianCalendarCalendarPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("godIsGreat");
            int dayOfMonth = persianCalendarCalendarPicker.valueProperty().getValue().getDayOfMonth();
            System.out.println(dayOfMonth);
            int year = persianCalendarCalendarPicker.valueProperty().getValue().getYear();
            System.out.println(year);
            int momth = persianCalendarCalendarPicker.valueProperty().getValue().getMonth().getValue();
            System.out.println(momth);



            System.out.println("------------------------godisgreat-------");
            PersianCalendar persianCalendar = persianCalendarCalendarPicker.valueProperty().getValue();
            PlainDate plainDate = persianCalendar.transform(PlainDate.class);
            System.out.println(plainDate.getYear());
            System.out.println(plainDate.getMonth());
            System.out.println(plainDate.getDayOfMonth());


        });

        DatePicker datePicker=new DatePicker();
        datePicker.setStyle("-fx-padding: 0 0 10 0");

//        rootNode.getChildren().add(datePicker);

        rootNode.add(datePicker, 1, 3);


        countriesCombo = new JFXComboBox<>();

        try {
            ArrayList<String> countriesName = Mapping.getFileLines(C.COUNTRIES_CONFIG_PATH);
            countriesName.forEach(countryName -> countriesCombo.getItems().add(new Label(countryName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rootNode.add(countriesCombo, 1, 5);


        stationsCombo = new JFXComboBox<>();

        countriesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            stationsCombo.getItems().clear();
            try {
                Mapping.creteCSVFILEFORStations("config", newValue.getText() + ".conf");
                Map<String, String> stationNumTOCities = Mapping.SecondStepMapStationNumTOCities("config/" + newValue.getText() + ".conf.csv");
                for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                    if (!station.getValue().equals("&"))
                        stationsCombo.getItems().add(new Label(station.getKey()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        stationsCombo.setPromptText("select station");
        stationsCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                System.out.println(oldValue.getText());
                System.out.println(newValue.getText());

            } catch (NullPointerException e) {
                try {
                    System.out.println(newValue.getText());

                }catch (NullPointerException e1){
                    System.out.println("fucked");
                }
            }
        });
//        rootNode.getChildren().add(stationsCombo);

        rootNode.add(stationsCombo, 1, 7);
        GridPane.setHalignment(stationsCombo, HPos.CENTER);

        cancelBtn.pressedProperty().addListener(observable -> {
        });

        cancelBtn.setOnAction(event -> {
            ((cancelBtn)).getParent().getScene().getWindow().hide();

        });
        cancelBtn.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
                cancelBtn.getOnAction().handle(null);
        });

        persianCalendarCalendarPicker.setFocusTraversable(true);
    }





}
