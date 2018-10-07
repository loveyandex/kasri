package com.amin.ui.main.features.day;

import com.amin.config.MathTerminology;
import com.amin.ui.SceneJson;
import com.amin.ui.dialogs.Dialog;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jsat.linear.Vec;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 7/18/2018 at 23:37
 */
public class StatisticalController implements Initializable, Runnable {
    public JFXComboBox feturesCombo;
    public Label mathematicLable;
    public Label sdvalue;
    public Label maxvalue;
    public Label minvalue;
    public VBox allvbox;
    public Label maxtext;
    public Label meanvalue;
    public Label variencevalue;

    @FXML
    private JFXDialogLayout content;
    @FXML
    private JFXButton acceptButton;
    private ArrayList<ArrayList<Object>> allfeatureandyear;

    @FXML
    private Label valueLable;
    @FXML
    private StackPane rootstackpane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this);


    }

    public void copied(MouseEvent mouseEvent) {
        String myString = valueLable.getText();
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Dialog.SnackBar.showSnack(rootstackpane, "copied", 1000);

    }

    @Override
    public void run() {
        allfeatureandyear = (ArrayList) ((SceneJson) rootstackpane.getScene()).getJson();
        allfeatureandyear.forEach(objects -> {
            objects.forEach(o -> {
                System.out.println(objects.size());
            });
        });
        allvbox.managedProperty().bind(allvbox.visibleProperty());
        feturesCombo.valueProperty().setValue(MathTerminology.ALL);
        calcAll();

        feturesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            mathematicLable.setText((String) newValue);
            calculatItem((String) newValue);
        });


    }

    private void calculatItem(String newValue) {
        ArrayList<Double> doubles = new ArrayList<>();
        allfeatureandyear.forEach(objects -> {
            doubles.add((Double) objects.get(0));
        });

        Vec vec = new Vec() {
            @Override
            public int length() {
                return doubles.size();

            }

            @Override
            public double get(int i) {
                return doubles.get(i);
            }

            @Override
            public void set(int i, double v) {
                doubles.remove(i);
                doubles.add(i, v);
            }

            @Override
            public boolean isSparse() {
                return false;
            }

            @Override
            public Vec clone() {
                return null;
            }
        };
        try {

            if (newValue.equals(MathTerminology.MAXVALUE)) {
                valueLable.setText(String.format("%.4f", (calcMax(vec))));
                ;
            } else if (newValue.equals(MathTerminology.MINVALUE)) {
                valueLable.setText(String.format("%.4f", (calcMin(vec))));
            } else if (newValue.equals(MathTerminology.ALL)) {
                maxtext.visibleProperty().setValue(true);
                maxtext.setText("Max Value: ");
                maxvalue.setText(String.format("%.4f", (calcMax(vec))));
                minvalue.setText(String.format("%.4f", (calcMin(vec))));
                meanvalue.setText(String.format("%.4f", (calcAvg(vec))));
                variencevalue.setText(String.format("%.4f", (calcVar(vec))));
                sdvalue.setText(String.format("%.4f", (calcSD(vec))));
            } else if (newValue.equals(MathTerminology.AVARAGE)) {
                valueLable.setText(String.format("%.4f", (calcAvg(vec))));
            } else if (newValue.equals(MathTerminology.VARIENCE)) {
                valueLable.setText(String.format("%.4f", (calcVar(vec))));
            } else if (newValue.equals(MathTerminology.STANDARDDEVIATION)) {
                valueLable.setText(String.format("%.4f", (calcSD(vec))));
            }

        } catch (IndexOutOfBoundsException e) {
            Dialog.createExceptionDialog(e);
        }

    }

    private void calcAll() {
        ArrayList<Double> doubles = new ArrayList<>();
        allfeatureandyear.forEach(objects -> {
            doubles.add((Double) objects.get(0));
        });

        Vec vec = new Vec() {
            @Override
            public int length() {
                return doubles.size();

            }

            @Override
            public double get(int i) {
                return doubles.get(i);
            }

            @Override
            public void set(int i, double v) {
                doubles.remove(i);
                doubles.add(i, v);
            }

            @Override
            public boolean isSparse() {
                return false;
            }

            @Override
            public Vec clone() {
                return null;
            }
        };
        valueLable.setText("");
        allvbox.setVisible(true);
        maxtext.visibleProperty().setValue(true);
        maxtext.setText("Max Value: ");
        maxvalue.setText(String.format("%.4f", (calcMax(vec))));
        minvalue.setText(String.format("%.4f", (calcMin(vec))));
        meanvalue.setText(String.format("%.4f", (calcAvg(vec))));
        variencevalue.setText(String.format("%.4f", (calcVar(vec))));
        sdvalue.setText(String.format("%.4f", (calcSD(vec))));

    }

    private double calcSD(Vec Vec) {
        return Vec.standardDeviation();
    }

    private double calcVar(Vec Vec) {
        return Vec.variance();
    }

    private double calcAvg(Vec Vec) {
        return Vec.mean();

    }

    private double calcMin(Vec Vec) {
        return Vec.min();
    }

    private double calcMax(Vec Vec) {
        return Vec.max();
    }
}
