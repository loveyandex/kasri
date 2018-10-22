package com.amin.neuralNetwork.regression;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class UI extends Application {

    private static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";
    private static final String EM1 = "1em";
    private static final String ERROR = "error";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image(getClass().getResource("/drawable/wind_white.png").toURI().toString()));

        final VBox pane = new VBox();
        pane.setSpacing(30);
        pane.setStyle("-fx-background-color:WHITE;-fx-padding:40;");

//        pane.getChildren().add(new TextField());

        JFXTextField field = new JFXTextField();
        field.setLabelFloat(true);
        field.setPromptText("Type Something");
//        pane.getChildren().add(field);


        JFXTextField disabledField = new JFXTextField("555");
        disabledField.setStyle(FX_LABEL_FLOAT_TRUE);
        disabledField.setPromptText("Itr..");
//        disabledField.setDisable(true);
        pane.getChildren().add(disabledField);

        JFXTextField validationField = new JFXTextField("0.1,0.99");

        validationField.setPromptText("0.1,0.99");
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");

        validationField.getValidators().add(validator);
        validationField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                validationField.validate();
            }
        });
        pane.getChildren().add(validationField);


        JFXPasswordField passwordField = new JFXPasswordField();
        passwordField.setStyle(FX_LABEL_FLOAT_TRUE);
        passwordField.setPromptText("Password");
        validator = new RequiredFieldValidator();
        validator.setMessage("Password Can't be empty");


        JFXButton jfxButton = new JFXButton("learn");
        Label results = new Label("no resut");

        JFXButton gett = new JFXButton("get");
        final MultiLayerNetwork[] net = new MultiLayerNetwork[1];
        jfxButton.setOnMouseClicked(event -> {
            gett.setDisable(true);


            net[0] = RegressionSum.net(RegressionSum.getTrainingData(RegressionSum.batchSize, RegressionSum.rng));
            results.setText("no result");
            gett.setDisable(false);
        });

        passwordField.getValidators().add(validator);
        passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordField.validate();
            }
        });
//        pane.getChildren().add(passwordField);

        gett.setOnMouseClicked(event -> {

            final double aDouble = Double.parseDouble(validationField.getText().split(",")[0]);
            final double bDouble = Double.parseDouble(validationField.getText().split(",")[1]);

            // Test the addition of 2 numbers (Try different numbers here)
            final INDArray input = Nd4j.create(new double[]{aDouble, bDouble}, new int[]{1, 2});
            INDArray out = net[0].output(input, false);
            System.err.println(out);
            results.setText(results.getText() + ";" + out.toString());
        });


        pane.getChildren().add(jfxButton);
        pane.getChildren().add(gett);
        pane.getChildren().add(results);


        final Scene scene = new Scene(pane, 600, 400, Color.WHITE);
        scene.getStylesheets()
                .add(UI.class.getResource("/css/jfoenix-components.css").toExternalForm());
        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }


}