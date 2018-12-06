package com.amin.neuralNetwork.regression.load;

import com.amin.analysis.Mapping;
import com.amin.config.MathTerminology;
import com.google.gson.Gson;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationGaussian;
import org.encog.engine.network.activation.ActivationReLU;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.lma.LevenbergMarquardtTraining;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.util.simple.EncogUtility;

import javax.crypto.MacSpi;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This example implements a Fahlman Encoder.  Though probably not invented by Scott
 * Fahlman, such encoders were used in many of his papers, particularly:
 * <p>
 * "An Empirical Study of Learning Speed in Backpropagation Networks"
 * (Fahlman,1988)
 * <p>
 * It provides a very simple way of evaluating classification neural networks.
 * Basically, the input and output neurons are the same in count.  However,
 * there is a smaller number of hidden neurons.  This forces the neural
 * network to learn to encode the patterns from the input neurons to a
 * smaller vector size, only to be expanded again to the outputs.
 * <p>
 * The training data is exactly the size of the input/output neuron count.
 * Each training element will have a single column set to 1 and all other
 * columns set to zero.  You can also perform in "complement mode", where
 * the opposite is true.  In "complement mode" all columns are set to 1,
 * except for one column that is 0.  The data produced in "complement mode"
 * is more difficult to train.
 * <p>
 * Fahlman used this simple training data to benchmark neural networks when
 * he introduced the Quickprop algorithm in the above paper.
 */
public class FahlmanEncoder {
    public static final int INPUT_OUTPUT_COUNT = 2;
    public static final int HIDDEN_COUNT = 5;
    public static final int TRIES = 2500;
    public static final boolean COMPL = false;
    public static double XOR_INPUT[][] = {{1.0, 0.0}, {0.0, 0.0},
            {0.0, 1.0}, {1.0, 1.0}};

    /**
     * The ideal data necessary for XOR.
     */
    public static double XOR_IDEAL[][] = {{1.0}, {0.0}, {1.0}, {0.0}};

    public static MLDataSet generateTraining(int inputCount, boolean compl) {
        double[][] input = new double[INPUT_OUTPUT_COUNT][INPUT_OUTPUT_COUNT];
        double[][] ideal = new double[INPUT_OUTPUT_COUNT][INPUT_OUTPUT_COUNT];

        for (int i = 0; i < inputCount; i++) {
            for (int j = 0; j < inputCount; j++) {
                if (compl) {
                    input[i][j] = (j == i) ? 0.0 : 1.0;
                } else {
                    input[i][j] = (j == i) ? 1.0 : 0.0;
                }

                ideal[i][j] = 0.5 * input[i][j];
            }
        }

        return new BasicMLDataSet(input, ideal);
    }

    public static void main(String[] args) throws IOException {
        MLDataSet trainingData = traning();

        System.err.println(new Gson().toJson(trainingData));

//        System.exit(0);
        MLMethod method = EncogUtility.simpleFeedForward(INPUT_OUTPUT_COUNT,
                HIDDEN_COUNT, HIDDEN_COUNT, 1, false);


        // create a neural network, without using a factory
        BasicNetwork network = new BasicNetwork();
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 2));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 10));
        network.addLayer(new BasicLayer(new ActivationTANH(), true, 1));
        network.getStructure().finalizeStructure();
        network.reset();
        new ConsistentRandomizer(-1, 1, 120).randomize(network);
        System.out.println(network.dumpWeights());

//
//        LevenbergMarquardtTraining train = new LevenbergMarquardtTraining(network, trainingData);
//        EncogUtility.trainToError(train, 0.001);


        final Backpropagation train = new Backpropagation(network, trainingData, 0.01, 0.9);
        train.fixFlatSpot(false);

        int epoch = 1;

        do {
            train.iteration(111);
            System.out
                    .println("Epoch #" + epoch + " Error:" + train.getError());
            epoch++;
        } while (train.getError() > 1e-7);






        for (MLDataPair pair : trainingData) {

            final MLData output = ((BasicNetwork) network).compute(pair.getInput());
            System.err.println("amin " + output.size());

            System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
                    + ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
        }
        Encog.getInstance().shutdown();
    }


    public static BasicMLDataSet traning() throws IOException {


        final ArrayList<ArrayList<String>> col1Col2Data = Mapping.LatLong.getColsData("assets/d.csv",
                ",", 0, 1, 2);


        System.err.println(new Gson().toJson(col1Col2Data));

        final int nSamples = col1Col2Data.size();

        final double[][] inps = new double[nSamples][2];
        final double[][] outs = new double[nSamples][1];

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
        }
        final double maxinput = MathTerminology.max(input1);
        final double maxinput2 = MathTerminology.max(input2);
        final double maxSum = Math.abs(MathTerminology.min(sum));
        System.out.println(maxinput + " maxinput1");
        System.out.println(maxinput2 + " maxinput2");
        System.out.println(maxSum + "maxsum");

        for (int i = 0; i < sum.length; i++) {
            sum[i] /= maxSum;
            input1[i] /= maxinput;
            input2[i] /= maxinput2;

            inps[i] = new double[]{input1[i], input2[i]};
            outs[i] = new double[]{(sum[i])};
        }

        return new BasicMLDataSet(inps, outs);


    }


}
