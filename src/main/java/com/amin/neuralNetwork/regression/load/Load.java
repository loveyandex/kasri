package com.amin.neuralNetwork.regression.load;

import com.amin.neuralNetwork.regression.RegressionMathFunctions;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * is created by aMIN on 9/18/2018 at 7:31 PM
 */
public class Load {
    public static void main(String[] args) {
        try {
            final MultiLayerNetwork net = MultiLayerNetwork.load(new File("latlongtemp.net"), true);

            final INDArray input = Nd4j.create(new double[]{-5.111111}, new int[]{1}).reshape(1, 1);
            final INDArray input2 = Nd4j.create(new double[]{-5.111111}, new int[]{1}).reshape(1, 1);

            final int rows = 10;
            final INDArray x2 = Nd4j.linspace(20.0, 25.0, rows).reshape(rows, 1);
            final INDArray x1 = Nd4j.linspace(32, 37.2, rows).reshape(rows, 1);
            INDArray inputNDArray = Nd4j.hstack(x2,x1);
            System.err.println(inputNDArray);

            final INDArray output = net.output(inputNDArray, false);
            System.out.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
