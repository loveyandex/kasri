package com.amin.neuralNetwork.regression.load;

import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;

import static com.amin.neuralNetwork.regression.load.EncogPersistence.FILENAME;
import static com.amin.neuralNetwork.regression.load.EncogPersistence.XOR_IDEAL;
import static com.amin.neuralNetwork.regression.load.EncogPersistence.XOR_INPUT;

public class Load2 {
    public static void main(String[] args) {

        BasicNetwork network = (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(FILENAME));
        BasicMLDataSet mlDataPairs = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);
        for (MLDataPair mlDataPair : mlDataPairs) {
            MLData outi = network.compute(mlDataPair.getInput());
            System.out.println(outi);
        }

    }
}
