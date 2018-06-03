package com.ui;

import com.analysis.JalaliCalendar;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.time4j.PlainDate;
import net.time4j.calendar.PersianCalendar;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;
import net.time4j.ui.javafx.CalendarPicker;
import net.time4j.ui.javafx.CellCustomizer;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;

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
        picker.promptTextProperty().addListener((observable, oldValue, newValue) -> {
            System.exit(0);
        });
        picker.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("god is great");
            int dayOfMonth = picker.valueProperty().getValue().getDayOfMonth();
            System.out.println(dayOfMonth);
            int year = picker.valueProperty().getValue().getYear();
            System.out.println(year);
            int momth = picker.valueProperty().getValue().getMonth().getValue();
            System.out.println(momth);


//
//            JalaliCalendar.YearMonthDate yearMonthDate = JalaliCalendar.jalaliToGregorian(new JalaliCalendar.YearMonthDate(year, momth, dayOfMonth));
//
//            System.out.println(yearMonthDate.getYear());
//            System.out.println(yearMonthDate.getMonth());
//            System.out.println(yearMonthDate.getDate());

            System.out.println("------------------------godisgreat-------");
            PersianCalendar persianCalendar = picker.valueProperty().getValue();
            PlainDate plainDate = persianCalendar.transform(PlainDate.class);
            System.out.println(plainDate.getYear());
            System.out.println(plainDate.getMonth());
            System.out.println(plainDate.getDayOfMonth());


        });


        CalendarPicker<PlainDate> plainDateCalendarPicker = CalendarPicker.gregorianWithSystemDefaults();
        plainDateCalendarPicker.setCellCustomizer(
                (cell, column, row, model, date) -> {
                    if (CellCustomizer.isWeekend(column, model)) {
                        cell.setStyle("-fx-background-color: #FFE0E0;");
                    }
                }
        );

        plainDateCalendarPicker.setPromptText("TT.MM.JJJJ");

        ChronoFormatter<PlainDate> dateFormat =
                ChronoFormatter.ofDatePattern("dd.MM.yyyy", PatternType.CLDR, Locale.GERMAN);
        plainDateCalendarPicker.setDateFormat(dateFormat);

        plainDateCalendarPicker.setMinDate(PlainDate.of(-12, 1));
        plainDateCalendarPicker.setMaxDate(PlainDate.of(9999, 365));

        plainDateCalendarPicker.setValue(PlainDate.of(9999, 1, 20));
        plainDateCalendarPicker.setLocale(new Locale("en"));

        root23.getChildren().add(plainDateCalendarPicker);


    }
}
