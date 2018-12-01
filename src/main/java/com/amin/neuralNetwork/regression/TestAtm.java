package com.amin.neuralNetwork.regression;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * is created by aMIN on 12/1/2018 at 11:40 PM
 */
public class TestAtm {
    public static void main(String[] args) throws IOException {
        MultiLayerNetwork net = MultiLayerNetwork.load(new File("assets/gg.net"), false);
        for (int i = 255; i < 256; i++) {
             double inp = i*108 / 32575d;
            inp=27733d/32575d;
            System.err.println(inp);
            final INDArray input = Nd4j.create(new double[]{inp}, new int[]{1, 1});
            INDArray out = net.output(input, false);
            System.out.println("input: " + input);
            System.out.println(out);
            System.out.println(out.data().asDouble()[0] * 78);
        }
    }

}
