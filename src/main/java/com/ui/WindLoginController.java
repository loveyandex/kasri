package com.ui;

import com.amin.jsons.Date;
import com.amin.jsons.WindInfo;
import com.analysis.Mapping;
import com.config.C;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;
import org.controlsfx.control.RangeSlider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
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
    //    public JFXTextField height;
    public TextField height;
    public VBox vvv;
    @FXML
    CalendarPicker<PersianCalendar> persianCalendarCalendarPicker;
    private DatePicker datePicker;

    public WindInfo windInfo;
    private Map<String, String> stationNumTOCities;
    private RangeSlider hSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windInfo = new WindInfo();
        rootNode.setAlignment(Pos.CENTER);
        rootNode.setStyle("          -fx-padding: 10;\n" +
                "            -fx-border-style: solid inside;\n" +
                "            -fx-border-width: 6;\n" +
                "            -fx-border-insets: 5;\n" +
                "            -fx-border-radius: 5;\n" +
                "            -fx-border-color: gray;");

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
            datePicker.setDisable(true);

            if (isReadyToFire(windInfo))
                Gobtn.setDisable(false);

        });

        datePicker = new DatePicker();
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
                stationNumTOCities = Mapping.SecondStepMapStationNumTOCities("config/" + newValue.getText() + ".conf.csv");
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

//        stationsCombo.setPromptText("select station");
        stationsCombo.setMinWidth(200);
        countriesCombo.setMinWidth(200);
        datePicker.setMinWidth(200);
        persianCalendarCalendarPicker.setMinWidth(200);
        height.setMinWidth(200);
        height.setMinHeight(32);


//        GridPane.setMargin(stationsCombo,new Insets(12,0,12,12));
        stationsCombo.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                for (Map.Entry<String, String> station : stationNumTOCities.entrySet()) {
                    if (station.getKey().equals(newValue.getText()))
                        windInfo.setStationNumber(station.getValue());

                }
                windInfo.setStationName(newValue.getText());

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
            try {
                getResult(((Stage) rootNode.getScene().getWindow()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        persianCalendarCalendarPicker.setFocusTraversable(true);

        height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(""))
                windInfo.setHeight(null);
            else
                windInfo.setHeight(newValue);
            if (isReadyToFire(windInfo))
                Gobtn.setDisable(false);
        });


        hSlider = new RangeSlider(1100, 20000, 1100, 20000);

        hSlider.setShowTickMarks(true);
        hSlider.setShowTickLabels(true);
        hSlider.setBlockIncrement(1);

        hSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            height.textProperty().setValue(String.valueOf(newValue));
        });

        height.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isReadyToFire(windInfo))
                Gobtn.setDisable(false);
        });


        rootNode.add(hSlider, 1, 11);

    }

    private void getResult(Stage prStage) throws IOException, URISyntaxException {

        URL resource = getClass().getResource("/wind_result.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = ((Parent) fxmlLoader.load(resource));
        SceneJsonWindInfo scene = new SceneJsonWindInfo(root, 450, 350);
        (scene).setWindInfo(windInfo);

        String image = MainController.class.getResource("/fav.jpg").toURI().toString();
        root.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
        prStage.hide();
        prStage.setScene(scene);
        prStage.show();
//        minnig();
        prStage.setOnShowing(event -> {
            System.out.println("showing");
        });
    }
    private void minnig() {
        int numDay = windInfo.Date.Day;
        String dayOfMonth = (numDay < 10 ? "0" : "") + numDay;
        Month day = Month.of(windInfo.getDate().Month);
//        System.out.println(day.getDisplayName(TextStyle.SHORT,Locale.ENGLISH));
//        System.out.println(dayOfMonth);






    }









































    public static final LocalDate LOCAL_DATE(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    private boolean isReadyToFire(WindInfo windInfo) {
        if (windInfo.getDate() == null || windInfo.getStationNumber() == null || windInfo.getCountry() == null || windInfo.getHeight() == null) {
            Gobtn.setDisable(true);
            return false;
        } else
            return true;
    }




}
