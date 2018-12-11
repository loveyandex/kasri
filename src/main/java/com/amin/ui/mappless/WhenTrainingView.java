package com.amin.ui.mappless;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

interface MAKE {
    void aDo(JFXTextArea console);

}

/**
 * is created by aMIN on 12/7/2018 at 4:51 AM
 */
public class WhenTrainingView extends Application {
    public WhenTrainingView(MAKE make) {
        this.make = make;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UTILITY);

        JFXTextArea console = new JFXTextArea("");
        console.setMinHeight(660);
        console.setStyle("-fx-text-fill: #e2e2e2;-fx-font-size: 14px;");

        console.scrollTopProperty().setValue(100.0);

        VBox vBox = new VBox(console);




        vBox.getStylesheets().add("/dark-theme.css");
//        //You would need from here
//        primaryStage.focusedProperty().addListener((ov, onHidden, onShown) -> {
//            primaryStage.hide();
//        });


        primaryStage.setOnShown(event -> {
            console.appendText("king aain is here");
            this.make.aDo(console);
        });
        Scene scene = new Scene(vBox, 550, 662);
        primaryStage.setScene(scene);
        primaryStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                primaryStage.hide();
        }); primaryStage.show();
//
    }
    private MAKE make;
}
