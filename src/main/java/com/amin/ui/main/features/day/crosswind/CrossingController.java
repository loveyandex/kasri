package com.amin.ui.main.features.day.crosswind;

import com.amin.config.MathTerminology;
import com.amin.ui.SceneJson;
import com.amin.ui.main.features.StaticFunctions;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static com.amin.neuralNetwork.regression.RegressionSum.rng;

/**
 * is created by aMIN on 10/12/2018 at 3:22 AM
 */
public class CrossingController extends StaticFunctions implements Initializable {
    private ArrayList<ArrayList<ArrayList<Double>>> allyears = new ArrayList<>();
    private ArrayList<ArrayList<Object>> allfeatureandyear = new ArrayList<>();
    private ArrayList<Object> godObj;

    @FXML
    private StackPane rootstackpane;

    public static DataSetIterator getTrainingData(ArrayList<Double> input, ArrayList<Double> output, int nSamples, int batchSize) {

        final double maxfeaturesinput = MathTerminology.max(input);
        final double maxheightsoutput = MathTerminology.max(output);
        System.out.println(maxfeaturesinput);
        System.out.println(maxheightsoutput);
        double[] sum = new double[nSamples];
        double[] input1 = new double[nSamples];
        for (int i = 0; i < nSamples; i++) {
            input1[i] = input.get(i) / maxfeaturesinput;
            sum[i] = output.get(i) / maxheightsoutput;
        }
        INDArray inputNDArray1 = Nd4j.create(input1, new int[]{nSamples, 1});
        INDArray inputNDArray = Nd4j.hstack(inputNDArray1);
        INDArray outPut = Nd4j.create(sum, new int[]{nSamples, 1});
        DataSet dataSet = new DataSet(inputNDArray, outPut);
        List<DataSet> listDs = dataSet.asList();
        Collections.shuffle(listDs, rng);
        return new ListDataSetIterator(listDs, batchSize);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            godObj = (ArrayList<Object>) ((SceneJson) rootstackpane.getScene()).getJson();
            System.out.println(new Gson().toJson(godObj));

            IntStream.range(0, godObj.size() - 1)
                    .filter(value -> (value % 2 == 1 && godObj.get(value) != null))
                    .forEach(value -> allfeatureandyear.add((ArrayList<Object>) godObj.get(value)));

            IntStream.range(0, godObj.size() - 1)
                    .filter(value -> (value % 2 == 0 && godObj.get(value) != null))
                    .forEach(value -> allyears.add((ArrayList<ArrayList<Double>>) godObj.get(value)));

            final ArrayList<ArrayList<Double>> firstyear = allyears.get(0);
            ArrayList<Double> heights = new ArrayList<>();
            ArrayList<Double> features = new ArrayList<>();
            firstyear.stream().forEach(doubles -> heights.add(doubles.get(0)));
            firstyear.stream().forEach(doubles -> features.add(doubles.get(1)));

            final Double random = features.get(23);
            System.out.println(random + "random");


            ArrayList<Double> fitness = new ArrayList<>();
            for (int i = 0; i < features.size(); i++) {
                final double x = random - features.get(i);
                fitness.add(x);
                System.out.println(x + " >> " + i);
            }

//            ArrayList<Double> difs = new ArrayList<>();
//
//            for (int i = 0; i < fitness.size()-1; i++) {
//                final double del = fitness.get(i + 1) - fitness.get(i);
//                difs.add(del);
//            }

            for (int i = 0; i < fitness.size() - 1; i++) {
                if ((fitness.get(i) * fitness.get(i + 1)) < 0) {
                    System.out.println(i);
                    final int limit = 20;
                    for (int j = 0; j < limit + 1; j++) {

                        final double height = (heights.get(i) * (limit - j) / limit + (j) * heights.get(i + 1) / limit);
                        final Double aDouble = random - intrapolateinAllFeature(height, firstyear);
                        System.out.println("       " + height + "intrabulate " + aDouble);
                    }

                }
            }

        });

    }


}
