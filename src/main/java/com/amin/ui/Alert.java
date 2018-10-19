package com.amin.ui;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 8/21/2018 at 5:02 AM
 */
public class Alert implements Initializable {

    public JFXButton exit;
    public JFXButton cancel;
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    public void cancel(ActionEvent actionEvent) {
        cancel.getScene().getWindow().hide();

    }


    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                exit(null);
            else if (event.getCode() == KeyCode.SPACE)
                event.consume();
        });

        cancel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                cancel(null);
            else if (event.getCode() == KeyCode.SPACE)
                event.consume();
        });

    }
}
