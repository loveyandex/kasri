package com.amin.ui.main.wind;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.jsons.Date;
import com.amin.jsons.WindInfo;
import com.amin.ui.MainController;
import com.amin.ui.SceneJsonWindInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;
import org.controlsfx.control.RangeSlider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */
public class WindMonthController implements Initializable {


    public GridPane rootNode;

    @FXML
    public JFXComboBox<Label> stationsCombo;
    @FXML
    public JFXComboBox<Label> countriesCombo;
    public JFXButton cancelBtn;
    public JFXButton Gobtn;

    public TextField height;
    public VBox vvv;
    public JFXComboBox monthCombo;
    public JFXComboBox dayofMonthCombo;
    @FXML
    CalendarPicker<PersianCalendar> persianCalendarCalendarPicker;

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
                "            -fx-border-color: white;");


        ArrayList<String> persianMonths=new ArrayList<String>( Arrays.asList("فروردین", "اردیبهشت", "خرداد","تیر","مرداد","شهریور","مهر","ابان","اذر","دی","بهمن","اسفند"));
        Map<String, Integer> persianMapMonth = new HashMap<>();
        for (int j = 0; j < persianMonths.size(); j++) {
            persianMapMonth.put(persianMonths.get(j), j + 1);
        }


        persianMapMonth.forEach((s, integer) -> monthCombo.getItems().add(new Label((s))));


        monthCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            dayofMonthCombo.getItems().clear();
            int[] days={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
            for (int i = 1; i <= 31; i++) {
                    dayofMonthCombo.getItems().add(new Label(String.valueOf(i)));
            }

        });
        dayofMonthCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                windInfo.setDate(null);
                persianCalendarCalendarPicker = CalendarPicker.persianWithSystemDefaults();
                String text = ((Label) monthCombo.getValue()).getText();
                Integer intmonth = persianMapMonth.get(text);

                persianCalendarCalendarPicker.valueProperty().setValue(PersianCalendar.of(1372, intmonth, (Integer.parseInt(((Label) newValue).getText()))));
                System.out.println(persianCalendarCalendarPicker.valueProperty().getValue().getMonth());
            PersianCalendar persianCalendar = persianCalendarCalendarPicker.valueProperty().getValue();
            PlainDate plainDate = persianCalendar.transform(PlainDate.class);
                System.out.println(String.format("%s-%s-%s", plainDate.getDayOfMonth(), plainDate.getMonth(), plainDate.getYear()));
            windInfo.setDate(new Date(plainDate.getMonth(), plainDate.getDayOfMonth(), plainDate.getYear()));
            }

            if (isReadyToFire(windInfo))
                Gobtn.setDisable(false);

        });





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
                Mapping.createCSVFILEFORStations("config", newValue.getText() + ".conf");
                stationNumTOCities = Mapping.MapStationNumTOCities("config/" + newValue.getText() + ".conf.csv");
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
        height.setMinWidth(200);
        height.setMinHeight(32);


        GridPane.setMargin(stationsCombo, new Insets(12, 0, 12, 0));
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
        prStage.setOnShowing(event -> {
            System.out.println("showing");
        });
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
