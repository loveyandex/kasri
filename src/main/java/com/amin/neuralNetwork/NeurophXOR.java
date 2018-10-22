package com.amin.neuralNetwork;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.NeuralNetworkType;

public class NeurophXOR {

    public static NeuralNetwork assembleNeuralNetwork() {

        Layer inputLayer = new Layer();
        inputLayer.addNeuron(new Neuron());
        inputLayer.addNeuron(new Neuron());

        Layer hiddenLayerOne = new Layer();
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());

        Layer hiddenLayerTwo = new Layer();
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());

        Layer outputLayer = new Layer();
        outputLayer.addNeuron(new Neuron());

        NeuralNetwork ann = new NeuralNetwork();

        ann.addLayer(0, inputLayer);
        ann.addLayer(1, hiddenLayerOne);
        ConnectionFactory.fullConnect(ann.getLayerAt(0), ann.getLayerAt(1));
        ann.addLayer(2, hiddenLayerTwo);
        ConnectionFactory.fullConnect(ann.getLayerAt(1), ann.getLayerAt(2));
        ann.addLayer(3, outputLayer);
        ConnectionFactory.fullConnect(ann.getLayerAt(2), ann.getLayerAt(3));
        ConnectionFactory.fullConnect(ann.getLayerAt(0),
                ann.getLayerAt(ann.getLayersCount() - 1), false);

        ann.setInputNeurons(inputLayer.getNeurons());
        ann.setOutputNeurons(outputLayer.getNeurons());

        ann.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);
        return ann;
    }

    public static NeuralNetwork trainNeuralNetwork(NeuralNetwork ann) {
        int inputSize = 2;
        int outputSize = 1;
        DataSet ds = new DataSet(inputSize, outputSize);

        DataSetRow rOne = new DataSetRow(new double[]{0, 1}, new double[]{0.51});
        ds.addRow(rOne);
        DataSetRow rTwo = new DataSetRow(new double[]{0.5, 1}, new double[]{0.25});
        ds.addRow(rTwo);
        DataSetRow rThree = new DataSetRow(new double[]{0.25, 0.25}, new double[]{0.42});
        ds.addRow(rThree);
        DataSetRow rFour = new DataSetRow(new double[]{0.25, 0.1}, new double[]{0.91});
        ds.addRow(rFour);
        DataSetRow rFour2 = new DataSetRow(new double[]{0.85, 0.2}, new double[]{0.63});
        ds.addRow(rFour2);
        DataSetRow rFour22 = new DataSetRow(new double[]{0.59, 0.36}, new double[]{0.52});
        ds.addRow(rFour22);
        DataSetRow rFour222 = new DataSetRow(new double[]{1, 0}, new double[]{0.31});
        ds.addRow(rFour222);

        SigmoidDeltaRule sigmoidDeltaRule = new SigmoidDeltaRule();
        sigmoidDeltaRule.setMaxIterations(12000);

        ann.learn(ds, sigmoidDeltaRule);

        return ann;

    }


    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = NeurophXOR.assembleNeuralNetwork();
        neuralNetwork = NeurophXOR.trainNeuralNetwork(neuralNetwork);

        neuralNetwork.setInput(0.0000001, 0.0000001);
        neuralNetwork.calculate();
        final double[] output = neuralNetwork.getOutput();
        System.out.println(output[0]);


    }
}