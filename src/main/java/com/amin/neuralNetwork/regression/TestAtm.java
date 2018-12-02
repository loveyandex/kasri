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
        double[] d = new double[]{ 990, 1165, 1555, 1980, 2398, 2471, 2620, 2760, 3104, 3534, 3836, 4048, 4223, 4521, 4694, 4857};

        double[] ff = new double[]{990,1165,1555,1980,2398,2471,2620,2760,3104,3534,3836,4048,4223,4521,4694,4857,4982,5010,5024,5249,5291,5435,5493,5537,5552,5685,5700,5805,6192,6223,6976,7330,7581,7617,7690,7989,9069,9290,9312,9445,9490,9626,9740,10193,10369,10470,10943,11425,11513,11849,11880,12307,12375,12621,12913,12988,13219,13259,13460,13710,13752,13881,14146,14329,14566,14662,15323,15484,15539,15998,16240,16429,17178,17324,17550,18062,18460,18918,19101,19111,19209,19511,19917,20051,20520,20896,21202,21298,21820,22008,22040,22385,22538,22893,23078,23466,23629,23670,23884,24335,24577,24828,25361,25645,25763,25945,26260,26597,26952,27026,27330,27733,27986,28164,28254,28634,29089,29141,29524,29696,29755,30183,30311,30376,30507,30990,31353,31738,32575};

        MultiLayerNetwork net = MultiLayerNetwork.load(new File("assets/gg.net"), false);

        for (int i = 0; i < ff.length; i++) {
            double inp = ff[i];
            System.out.println(inp) ;
            final INDArray input = Nd4j.create(new double[]{inp/32575d}, new int[]{1, 1});
            INDArray out = net.output(input, false);
            System.out.println(out.data().asDouble()[0] * 78.0d);
        }




    }

}
