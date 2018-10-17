package com.amin.ui.web.controllers;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

/**
 * is created by aMIN on 10/14/2018 at 12:56 AM
 */
public class F {
    public static void main(String[] args) {
        final File f = new File("F:/apps/jvm/kasri/assets", "gh");
        try {
            final MultiLayerNetwork network = MultiLayerNetwork.load(f, true);
            // Test the addition of 2 numbers (Try different numbers here)
            final INDArray input = Nd4j.create(new double[]{0.19304347826086957}, new int[]{1, 1});
            INDArray out = network.output(input, false);
            System.err.println(out);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
