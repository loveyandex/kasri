package com.amin.neuralNetwork.regression;

import com.amin.analysis.Mapping;
import com.amin.config.MathTerminology;
import com.google.gson.Gson;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Anwar on 3/15/2016.
 * An example of regression neural network for performing addition
 */
public class TwoInput {
    //Random number generator seed, for reproducability
    public static final int seed = 123450;
    //Number of epochs (full passes of the data)
    public static final int nEpochs = 10000;
    //Number of data points
    public static final int nSamples = 100;
    //Batch size: i.e., each epoch has nSamples/batchSize parameter updates
    public static final int batchSize = 100;
    //Network learning rate
    public static final double learningRate = 0.01;


    public static final Random rng = new Random(seed);
    // The range of the sample data, data in range (0-1 is sensitive for NN, you can try other ranges and see how it effects the results
    // also try changing the range along with changing the activation function
    public static int MIN_RANGE = 0;
    public static int MAX_RANGE = 1;

    public static void main(String[] args) throws IOException {

        //Generate the training data
        DataSetIterator iterator = getTrainingData(batchSize, rng);

        //Create the network
        int numInput = 2;
        int numOutputs = 1;
        int nHidden = 24;
        final MultiLayerConfiguration build = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(learningRate, 0.9))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInput).nOut(nHidden)
                        .activation(Activation.TANH)
                        .build())
                .layer(1, new DenseLayer.Builder().nIn(nHidden).nOut(nHidden)
                        .activation(Activation.TANH)
                        .build())
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(nHidden).nOut(numOutputs).build())
                .pretrain(false).backprop(true).build();
        final MultiLayerConfiguration deepDenseLayerNetworkConfiguration = getDeepDenseLayerNetworkConfiguration(2, 1);
        MultiLayerNetwork net = new MultiLayerNetwork(build);


        net.init();
        net.setListeners(new ScoreIterationListener(11111111));


        //Train the network on the full data set, and evaluate in periodically
        for (int i = 0; i < nEpochs; i++) {
            iterator.reset();
            net.fit(iterator);
            // Test the addition of 2 numbers (Try different numbers here)
            final INDArray input = Nd4j.create(new double[]{023.444444, 33.3333333333333}, new int[]{1, 2});
            INDArray out = net.output(input, false);
            System.out.println(out);
        }

        net.save(new File("latlongtemp.net"));

    }


    /**
     * Returns the network configuration, 2 hidden DenseLayers of size 50.
     */
    private static MultiLayerConfiguration getDeepDenseLayerNetworkConfiguration(int ninp, int nout) {
        final int numHiddenNodes = 10;
        return new NeuralNetConfiguration.Builder()
                .seed(seed)
                .weightInit(WeightInit.XAVIER)
                .updater(new Nesterovs(learningRate, 0.9))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(ninp).nOut(numHiddenNodes)
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
                        .nIn(numHiddenNodes).nOut(nout).build())
                .pretrain(false).backprop(true).build();
    }



    public static DataSetIterator getTrainingData(int batchSize, Random rand) throws IOException {

        final ArrayList<ArrayList<String>> col1Col2Data = Mapping.LatLong.getColsData("assets/dd.csv",
                ",", 0, 1, 2);


        System.err.println(new Gson().toJson(col1Col2Data));

        final int nSamples = col1Col2Data.size();
        double[] sum = new double[nSamples];
        double[] input1 = new double[nSamples];
        double[] input2 = new double[nSamples];
        String forprint = "";
        for (int i = 0; i < col1Col2Data.size(); i++) {
            final String inp1 = col1Col2Data.get(i).get(0);
            final String inp2 = col1Col2Data.get(i).get(1);
            final String out = col1Col2Data.get(i).get(2);
            input1[i] = Double.parseDouble(inp1);
            input2[i] = Double.parseDouble(inp2);
            sum[i] = Double.parseDouble(out);
            forprint += "[" + out + "],";
        }
        final double maxinput = MathTerminology.max(input1);
        final double maxinput2 = MathTerminology.max(input2);
        final double maxSum = MathTerminology.max(sum);
        System.out.println(maxinput + " maxinput1");
        System.out.println(maxinput2 + " maxinput2");
        System.out.println(maxSum + "maxsum");
//
//        for (int i = 0; i < sum.length; i++) {
//            sum[i] /= maxSum;
//            input1[i] /= maxinput;
//            input2[i] /= maxinput2;
//        }

        INDArray inputNDArray1 = Nd4j.create(input1, new int[]{nSamples, 1});
        INDArray inputNDArray2 = Nd4j.create(input1, new int[]{nSamples, 1});
        INDArray inputNDArray = Nd4j.hstack(inputNDArray1, inputNDArray2);
        INDArray outPut = Nd4j.create(sum, new int[]{nSamples, 1});
        DataSet dataSet = new DataSet(inputNDArray, outPut);
        List<DataSet> listDs = dataSet.asList();
        Collections.shuffle(listDs, rng);
        return new ListDataSetIterator(listDs, batchSize);

    }

}