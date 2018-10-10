package com.amin.ui.main.features.AllStationsOfCountryInOneDay;

import com.amin.config.MathTerminology;
import com.amin.ui.SceneJson;
import com.amin.ui.dialogs.Dialog;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jsat.linear.Vec;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * is created by aMIN on 7/18/2018 at 23:37
 */
public class allStationsStatisticalController implements Initializable, Runnable {
    public JFXComboBox feturesCombo;
    public Label mathematicLable;
    public ImageView openfileimage;
    public ImageView openfileimage2;
    @FXML
    private JFXDialogLayout content;
    @FXML
    private JFXButton acceptButton;
    ArrayList<ArrayList<String>> colsData;
    @FXML
    private Label valueLable;
    @FXML
    private StackPane rootstackpane;
    public Label sdvalue;
    public Label maxvalue;
    public Label minvalue;
    public VBox allvbox;
    public Label maxtext;
    public Label meanvalue;
    public Label variencevalue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(this);
    }

    public void copied(MouseEvent mouseEvent) {
        String myString = valueLable.getText();
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Dialog.SnackBar.showSnack(rootstackpane, "copied", 1300);

    }

    private String unit;
    @Override
    public void run() {
        allvbox.managedProperty().bind(allvbox.visibleProperty());
        colsData = (ArrayList) ((SceneJson) rootstackpane.getScene()).getJson();
        feturesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            mathematicLable.setText((String) newValue);
            calculatItem((String) newValue);
        });

        unit = colsData.get(0).get(5);

        calcAll();


    }

    private void calculatItem(String newValue) {
        ArrayList<Double> doubles = new ArrayList<>();
        colsData.stream().filter(strings -> strings.size() > 2).forEach(objects -> {
            doubles.add(Double.valueOf(objects.get(4)));
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
                valueLable.setText(String.format("%.4f %s", (calcMax(vec)),unit));
                ;
            } else if (newValue.equals(MathTerminology.MINVALUE)) {
                valueLable.setText(String.format("%.4f %s", (calcMin(vec)),unit));
            } else if (newValue.equals(MathTerminology.ALL)) {
                maxtext.visibleProperty().setValue(true);
                maxtext.setText("Max Value: ");
                maxvalue.setText(String.format("%.4f %s", (calcMax(vec)),unit));
                minvalue.setText(String.format("%.4f %s", (calcMin(vec)),unit));
                meanvalue.setText(String.format("%.4f %s", (calcAvg(vec)),unit));
                variencevalue.setText(String.format("%.4f", (calcVar(vec))));
                sdvalue.setText(String.format("%.4f %s", (calcSD(vec)),unit));
            } else if (newValue.equals(MathTerminology.AVARAGE)) {
                valueLable.setText(String.format("%.4f %s", (calcAvg(vec)),unit));
            } else if (newValue.equals(MathTerminology.VARIENCE)) {
                valueLable.setText(String.format("%.4f", (calcVar(vec))));
            } else if (newValue.equals(MathTerminology.STANDARDDEVIATION)) {
                valueLable.setText(String.format("%.4f %s", (calcSD(vec)),unit));
            }

        } catch (IndexOutOfBoundsException e) {
            Dialog.createExceptionDialog(e);
        }

    }

    private void calcAll() {
        ArrayList<Double> doubles = new ArrayList<>();
        colsData.stream().filter(strings -> strings.size() > 2).forEach(objects -> {
            doubles.add(Double.valueOf(objects.get(4)));
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
        maxvalue.setText(String.format("%.4f %s", (calcMax(vec)),unit));
        minvalue.setText(String.format("%.4f %s", (calcMin(vec)),unit));
        meanvalue.setText(String.format("%.4f %s", (calcAvg(vec)),unit));
        variencevalue.setText(String.format("%.4f", (calcVar(vec))));
        sdvalue.setText(String.format("%.4f %s", (calcSD(vec)),unit));

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


    public void showinexplorer(MouseEvent mouseEvent) {
        try {
            final String path = colsData.get(colsData.size() - 1).get(0).replaceAll("/", "\\\\");
            final String command = String.format("cmd /k   explorer /select ,%s", path);
            Gson d = new Gson();
            final String toJson = d.toJson(colsData);
            System.out.println(toJson);

            System.err.println(command);
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void opencsv(MouseEvent mouseEvent) {
        try {
            final String path = colsData.get(colsData.size() - 1).get(0).replaceAll("/", "\\\\");
            final String command = "cmd /k   start " + path;
            System.err.println(command);
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
