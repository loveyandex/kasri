package com.amin.neuralNetwork.regression;

import com.amin.analysis.Mapping;
import com.amin.config.MathTerminology;
import com.amin.neuralNetwork.regression.function.MathFunction;
import com.amin.neuralNetwork.regression.function.SinXDivXMathFunction;
import com.google.gson.Gson;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Example: Train a network to reproduce certain mathematical functions, and plot the results.
 * Plotting of the network output occurs every 'plotFrequency' epochs. Thus, the plot shows the accuracy of the network
 * predictions as training progresses.
 * A number of mathematical functions are implemented here.
 * Note the use of the identity function on the network output layer, for regression
 *
 * @author Alex Black
 */
public class Atm {

    //Random number generator seed, for reproducability
    public static final int seed = 123450;
    //Number of epochs (full passes of the data)
    public static final int nEpochs = 20000;
    //How frequently should we plot the network output?
    public static final int plotFrequency = 500;
    //Number of data points
    public static final int nSamples = 1000;
    //Batch size: i.e., each epoch has nSamples/batchSize parameter updates
    public static final int batchSize = 100;
    //Network learning rate
    public static final double learningRate = 0.05;
    public static final Random rng = new Random(seed);
    public static final int numInputs = 1;
    public static final int numOutputs = 1;


    public static void main(final String[] args) throws IOException {

        //Switch these two options to do different functions with different networks
        final MultiLayerConfiguration conf = getDeepDenseLayerNetworkConfiguration();

        //Generate the training data
//        final INDArray x = Nd4j.linspace(-10, 10, nSamples).reshape(nSamples, 1);
        final DataSetIterator iterator = getTrainingData( batchSize, rng);

        //Create the network
        final MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));


        //Train the network on the full data set, and evaluate in periodically
        final INDArray[] networkPredictions = new INDArray[nEpochs / plotFrequency];
        for (int i = 0; i < nEpochs; i++) {
//            iterator.reset();
            System.out.println(i);
            net.fit(iterator);
        }

        try {
            net.save(new File("assets/", "g5g.net"));
            System.err.println("saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Plot the target data and the network predictions


    }

    /**
     * Returns the network configuration, 2 hidden DenseLayers of size 50.
     */
    private static MultiLayerConfiguration getDeepDenseLayerNetworkConfiguration() {
        final int numHiddenNodes = 5;
        return new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(learningRate, 0.9))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(1, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(2, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(3, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(4, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(5, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(6, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(7, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(8, new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes)
                        .activation(Activation.ELU).build())
                .layer(9, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.ELU)
                        .nIn(numHiddenNodes).nOut(numOutputs).build())
                .pretrain(false).backprop(true).build();
    }

    /**
     * Create a DataSetIterator for training
     * @param batchSize Batch size (number of examples for every call of DataSetIterator.next())
     * @param rng       Random number generator (for repeatability)
     */
    private static DataSetIterator getTrainingData(final int batchSize, final Random rng) throws IOException {

        final ArrayList<ArrayList<String>> col1Col2Data = Mapping.LatLong.getCol1Col2Data("assets/00Z_02_Mar_2016.csv",
                1, 7, ";");
        final int nSamples = col1Col2Data.size();
        ;
        double[] sum = new double[nSamples];
        double[] input1 = new double[nSamples];
        String forprint = "";
        for (int i = 0; i < col1Col2Data.size(); i++) {
            final String inp = col1Col2Data.get(i).get(0);
            final String out = col1Col2Data.get(i).get(1);
            input1[i] = Double.parseDouble(inp);
            sum[i] = Double.parseDouble(out);
            ;
            forprint += "[" + out + "],";
        }
        final double maxinput = MathTerminology.max(input1);
        final double maxSum = MathTerminology.max(sum);
        System.out.println(maxinput + "maxinput");
        System.out.println(maxSum + "maxsum");

        for (int i = 0; i < sum.length; i++) {
            sum[i] /= maxSum;
            input1[i] /= maxinput;
        }

        System.err.println(new Gson().toJson(input1));
        System.err.println(new Gson().toJson(sum));
        INDArray x = Nd4j.create(input1, new int[]{nSamples, 1});
        INDArray y = Nd4j.create(sum, new int[]{nSamples, 1});
        final DataSet allData = new DataSet(x, y);

        final List<DataSet> list = allData.asList();
        Collections.shuffle(list, rng);
        return new ListDataSetIterator(list, batchSize);
    }


}
