package com.ui;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.time4j.calendar.PersianCalendar;
import net.time4j.ui.javafx.CalendarPicker;

import java.util.Locale;

/**
 * is created by aMIN on 6/1/2018 at 05:50
 */
public class WindLoginController {


    public VBox root23;

    public void per(ActionEvent actionEvent) {
        CalendarPicker<PersianCalendar> picker = CalendarPicker.persianWithSystemDefaults();
        root23.getChildren().add(picker);

        picker.setLengthOfAnimations(Duration.seconds(0));
        picker.setShowInfoLabel(true);
        picker.setLocale(new Locale("fa", "IR"));
        picker.setShowWeeks(true);

        picker.setCellCustomizer(
                (cell, column, row, model, date) -> {
//                    if (CellCustomizer.isWeekend(column, model)) {
//                        cell.setStyle("-fx-background-color: #FFE0E0;");
//                        cell.setDisable(true);
//                    }
                }
        );
    }
}
