package com.amin.neuralNetwork;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;

/**
 * is created by aMIN on 8/18/2018 at 10:29 PM
 */
public class TEs {
    public static void main(String[] args) {}


    public static NeuralNetwork net(int itr){
        Layer inputLayer = new Layer();
        inputLayer.addNeuron(new Neuron());
        inputLayer.addNeuron(new Neuron());


        Layer hiddenLayerOne = new Layer();
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());


        Layer hiddenLayerTwo = new Layer();
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
        final int layersCount = ann.getLayersCount();
//        System.out.println(layersCount);
        ann.setInputNeurons(inputLayer.getNeurons());
        ann.setOutputNeurons(outputLayer.getNeurons());


        int inputSize = 2;
        int outputSize = 1;
        DataSet ds = new DataSet(inputSize, outputSize);

        DataSetRow rOne
                = new DataSetRow(new double[]{00.0, 1.0}, new double[]{1.0});
        ds.addRow(rOne);
        DataSetRow rTwo
                = new DataSetRow(new double[]{1, 1}, new double[]{0.0});
        ds.addRow(rTwo);
        DataSetRow rThree
                = new DataSetRow(new double[]{0, 0}, new double[]{0.0});
        ds.addRow(rThree);
        DataSetRow rFour
                = new DataSetRow(new double[]{1, 0}, new double[]{1.0});
        ds.addRow(rFour);


        BackPropagation backPropagation = new BackPropagation();
        backPropagation.setMaxIterations(itr);
        ann.learn(ds, backPropagation);

        return ann;

    }
}
