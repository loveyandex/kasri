package com.amin.ui;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;

/**
 * is created by aMIN on 8/21/2018 at 5:02 AM
 */
public class Alert  {

    public JFXButton exit;

    public JFXButton cancel;

    public void cancel(ActionEvent actionEvent) {
        cancel.getScene().getWindow().hide();

    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
