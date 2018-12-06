package com.amin.neuralNetwork.regression;

import com.amin.analysis.Mapping;
import com.amin.config.MathTerminology;
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
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * is created by aMIN on 12/1/2018 at 2:28 AM
 */
public class AtmosphereFunction {
    final static long seejd = 12345;
    final static Random rand = new Random(seejd);

    public static void main(String[] args) throws IOException {
        final double inp = 13710 / 32575d;
        final DataSetIterator trainingData = getTrainingData(100, rand);

        MultiLayerNetwork net = net(trainingData, 0.9, 2000);


        final INDArray input = Nd4j.create(new double[]{inp}, new int[]{1, 1});
        INDArray out = net.output(input, true);
        System.err.println(out);
        System.out.println("input: " + input);
        System.err.println(out.data().asDouble()[0] * 78);

        net.save(new File("/assets","god.net"));


    }


    public static MultiLayerNetwork net(DataSetIterator iterator, double learningRate2, int nepoche) {


        //Create the network
        int numInput = 1;
        int numOutputs = 1;
        int nHidden = 10;
        NeuralNetConfiguration.Builder seed;
        MultiLayerNetwork net = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                .seed(seejd)
                .weightInit(WeightInit.ZERO)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Nesterovs(learningRate2, 0.9))
                .list()
                .layer(0, new DenseLayer.Builder().nIn(numInput).nOut(nHidden)
                        .activation(Activation.TANH)
                        .build())
                .layer(1, new DenseLayer.Builder().nIn(nHidden).nOut(nHidden)
                        .activation(Activation.TANH)
                        .build())
                .layer(2, new DenseLayer.Builder().nIn(nHidden).nOut(nHidden)
                        .activation(Activation.TANH)
                        .build())
                .layer(3, new DenseLayer.Builder().nIn(nHidden).nOut(nHidden)
                        .activation(Activation.TANH)
                        .build())
                .layer(4, new DenseLayer.Builder().nIn(nHidden).nOut(nHidden)
                        .activation(Activation.TANH)
                        .build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.TANH)
                        .nIn(nHidden).nOut(numOutputs).build())
                .pretrain(true).backprop(true).build()
        );


        MultiLayerConfiguration conf =
                new NeuralNetConfiguration.Builder()
                        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                        .updater(new Sgd(learningRate2))
                        .list(
                                new DenseLayer.Builder().nIn(numInput).nOut(nHidden).activation(Activation.TANH).build(),
                                new DenseLayer.Builder().nIn(nHidden).nOut(nHidden).activation(Activation.TANH).build(),
                                new DenseLayer.Builder().nIn(nHidden).nOut(nHidden).activation(Activation.TANH).build(),
                                new DenseLayer.Builder().nIn(nHidden).nOut(nHidden).activation(Activation.TANH).build(),
                                new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                                        .activation(Activation.SOFTMAX).nIn(nHidden).nOut(numOutputs).build()
                        ).backprop(true).build();

        MultiLayerNetwork network = new MultiLayerNetwork(conf);
        network = net;

        network.init();
        network.setListeners(new ScoreIterationListener(1));


        //Train the network on the full data set, and evaluate in periodically
        for (int i = 0; i < nepoche; i++) {
            final double scorebef = network.score();
//            iterator.reset();
            network.fit(iterator);
//            if ((Math.abs(net.score() - scorebef)) < 0.000000000000008) {
//                return net;
//            }


        }
        return network;

    }


    public static DataSetIterator getTrainingData(int batchSize, Random rand) throws IOException {

        final ArrayList<ArrayList<String>> col1Col2Data = Mapping.LatLong.getCol1Col2Data("assets/00Z_02_Mar_2016.csv",
                1, 7, ";");
        final int nSamples = col1Col2Data.size();
        double[] sum = new double[nSamples];
        double[] input1 = new double[nSamples];
        String forprint = "";
        for (int i = 0; i < col1Col2Data.size(); i++) {
            final String inp = col1Col2Data.get(i).get(0);
            final String out = col1Col2Data.get(i).get(1);
            input1[i] = Double.parseDouble(inp);
            sum[i] = Double.parseDouble(out);
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
        INDArray inputNDArray1 = Nd4j.create(input1, new int[]{nSamples, 1});
//        INDArray inputNDArray2 = Nd4j.create(input2, new int[]{nSamples, 1});
//        INDArray inputNDArray = Nd4j.hstack(inputNDArray1);
        INDArray outPut = Nd4j.create(sum, new int[]{nSamples, 1});
        DataSet dataSet = new DataSet(inputNDArray1,outPut);
        List<DataSet> listDs = dataSet.asList();
        Collections.shuffle(listDs, rand);
        return new ListDataSetIterator(listDs, batchSize);
    }


}

