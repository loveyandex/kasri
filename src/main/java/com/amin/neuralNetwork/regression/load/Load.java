package com.amin.neuralNetwork.regression.load;

import com.amin.neuralNetwork.regression.RegressionMathFunctions;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
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
            final MultiLayerNetwork net = MultiLayerNetwork.load(new File("assets/", "net.net"), true);
            final INDArray input = Nd4j.create(new double[]{-5.111111}, new int[]{1}).reshape(1, 1);
            final int rows = 100;
            final INDArray x = Nd4j.linspace(-10, 10, rows).reshape(rows, 1);


            final INDArray output = net.output(x, false);
            System.out.println(output);
            RegressionMathFunctions.plot(x, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
