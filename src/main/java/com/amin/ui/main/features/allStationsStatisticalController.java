package com.amin.ui.main.features;

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
import javafx.scene.layout.StackPane;
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
    @FXML
    private JFXDialogLayout content;
    @FXML
    private JFXButton acceptButton;
    ArrayList<ArrayList<String>> colsData;
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
        Dialog.SnackBar.showSnack(rootstackpane, "copied", 1300);

    }

    @Override
    public void run() {
        colsData = (ArrayList) ((SceneJson) rootstackpane.getScene()).getJson();
        feturesCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            mathematicLable.setText((String) newValue);
            calculatItem((String) newValue);
        });


    }

    private void calculatItem(String newValue) {
        ArrayList<Double> doubles = new ArrayList<>();
        colsData.forEach(objects -> {
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
                calcMax(vec);
            } else if (newValue.equals(MathTerminology.MINVALUE)) {
                calcMin(vec);
            } else if (newValue.equals(MathTerminology.AVARAGE)) {
                calcAvg(vec);
            } else if (newValue.equals(MathTerminology.VARIENCE)) {
                calcVar(vec);
            } else if (newValue.equals(MathTerminology.STANDARDDEVIATION)) {
                calcSD(vec);
            }

        } catch (IndexOutOfBoundsException e) {
            Dialog.createExceptionDialog(e);
        }

    }

    private void calcSD(Vec Vec) {
        double standardDeviation = Vec.standardDeviation();
        valueLable.setText(String.valueOf(standardDeviation));
        System.out.println(standardDeviation);
    }

    private void calcVar(Vec Vec) {
        double vari = Vec.variance();
        valueLable.setText(String.valueOf(vari));
        System.out.println(vari);
    }

    private void calcAvg(Vec Vec) {
        double mean = Vec.mean();
        valueLable.setText(String.valueOf(mean));
        System.out.println(mean);

    }

    private void calcMin(Vec Vec) {
        double min = Vec.min();
        valueLable.setText(String.valueOf(min));
        System.out.println(min);
    }

    private void calcMax(Vec Vec) {
        double max = Vec.max();
        valueLable.setText(String.valueOf(max));
        System.out.println(max);
    }

    public void showinexplorer(MouseEvent mouseEvent) {
        try {
            Runtime.getRuntime().exec("explorer "+colsData.get(colsData.size()-1).get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
