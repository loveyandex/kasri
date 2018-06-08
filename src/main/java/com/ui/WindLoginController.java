package com.ui;

import com.amin.jsons.Date;
import com.amin.jsons.WindInfo;
import com.analysis.Mapping;
import com.config.C;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private DatePicker datePicker;

    public WindInfo windInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windInfo = new WindInfo();
        rootNode.setAlignment(Pos.CENTER);

        persianCalendarCalendarPicker = CalendarPicker.persianWithSystemDefaults();
        persianCalendarCalendarPicker.setStyle("  -fx-primary : gray;\n" +
                "    -fx-secondary : #007bff;\n" +
                "    -fx-primarytext : white;\n" +
                "    -fx-blue: #007bff;\n" +
                "    -fx-red: #dc3545;\n" +
                "    -fx-green:#2E7D32;");
        persianCalendarCalendarPicker.setFocusTraversable(true);
        persianCalendarCalendarPicker.requestFocus();

//        rootNode.getChildren().add(persianCalendarCalendarPicker);
        rootNode.add(persianCalendarCalendarPicker, 1, 1);

        persianCalendarCalendarPicker.setLengthOfAnimations(Duration.seconds(0));
        persianCalendarCalendarPicker.setShowInfoLabel(true);
        persianCalendarCalendarPicker.setLocale(new Locale("fa", "IR"));
        persianCalendarCalendarPicker.setShowWeeks(true);
        persianCalendarCalendarPicker.setCellCustomizer((cell, column, row, model, date) -> {});

        persianCalendarCalendarPicker.valueProperty().addListener((observable, oldValue, newValue) -> {

            PersianCalendar persianCalendar = persianCalendarCalendarPicker.valueProperty().getValue();
            PlainDate plainDate = persianCalendar.transform(PlainDate.class);

            windInfo.setDate(new Date(plainDate.getMonth(), plainDate.getDayOfMonth(), plainDate.getYear()));

            datePicker.valueProperty().
                    setValue(LOCAL_DATE(
                            String.format("%s-%s-%s", plainDate.getDayOfMonth(), plainDate.getMonth(), plainDate.getYear()), "d-M-yyyy"));

            if (isReadyToFire(windInfo))
                Gobtn.setDisable(false);

        });

        datePicker = new DatePicker();
        datePicker.setStyle("-fx-padding: 0 0 10 0");
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
                windInfo.setStationNumber(null);
                windInfo.setCountry(newValue.getText());
                Mapping.creteCSVFILEFORStations("config", newValue.getText() + ".conf");
                Map<String, String> stationNumTOCities = Mapping.SecondStepMapStationNumTOCities("config/" + newValue.getText() + ".conf.csv");
                for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                    if (!station.getValue().equals("&"))
                        stationsCombo.getItems().add(new Label(station.getKey()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isReadyToFire(windInfo))
                Gobtn.setDisable(false);


        });

        stationsCombo.setPromptText("select station");
        stationsCombo.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                windInfo.setStationNumber(newValue.getText());
                if (isReadyToFire(windInfo))
                    Gobtn.setDisable(false);
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

        Gobtn.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER)
                Gobtn.getOnAction().handle(null);
        });


        Gobtn.setOnAction(event -> {
            getResult();
            ((Stage) ((Stage) (rootNode.getScene().getWindow())).getOwner()).getIcons().clear();
        });



        persianCalendarCalendarPicker.setFocusTraversable(true);
    }

    private void getResult() {
    }


    public static final LocalDate LOCAL_DATE(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    private boolean isReadyToFire(WindInfo windInfo) {
        if (windInfo.getDate() == null || windInfo.getStationNumber() == null || windInfo.getCountry() == null) {
            Gobtn.setDisable(true);
            return false;
        } else
            return true;
    }




}