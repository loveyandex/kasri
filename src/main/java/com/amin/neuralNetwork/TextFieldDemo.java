package com.amin.neuralNetwork;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.neuroph.core.NeuralNetwork;

public class TextFieldDemo extends Application {

    private static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";
    private static final String EM1 = "1em";
    private static final String ERROR = "error";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

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
        validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.WARNING)
                .size(EM1)
                .styleClass(ERROR)
                .build());
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
        validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
                .glyph(FontAwesomeIcon.WARNING)
                .size(EM1)
                .styleClass(ERROR)
                .build());
        passwordField.getValidators().add(validator);
        passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordField.validate();
            }
        });
//        pane.getChildren().add(passwordField);

        final NeuralNetwork[] net = new NeuralNetwork[1];

        JFXButton jfxButton = new JFXButton("learn");
        Label results = new Label("no resut");

        JFXButton gett = new JFXButton("get");

        jfxButton.setOnMouseClicked(event -> {
            gett.setDisable(true);
            net[0] = TEs.net(Integer.parseInt(disabledField.getText()));
            results.setText("");
            gett.setDisable(false);
        });


        gett.setOnMouseClicked(event -> {


            final double aDouble = Double.parseDouble(validationField.getText().split(",")[0]);
            final double bDouble = Double.parseDouble(validationField.getText().split(",")[1]);
            net[0].setInput(aDouble, bDouble);
            net[0].calculate();
            double[] networkOutputOne = net[0].getOutput();

            final double x = networkOutputOne[0];
            results.setText(results.getText() + ";" + x);
            System.out.println(x);
        });


        pane.getChildren().add(jfxButton);
        pane.getChildren().add(gett);
        pane.getChildren().add(results);


        final Scene scene = new Scene(pane, 600, 400, Color.WHITE);
        scene.getStylesheets()
                .add(TextFieldDemo.class.getResource("/css/jfoenix-components.css").toExternalForm());
        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }


}