package org.jfree4;

import com.amin.analysis.Mapping;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * is created by aMIN on 12/6/2018 at 7:04 PM
 */
public class TestAra {
    @Test
    public void test1() throws IOException {

        final ArrayList<ArrayList<String>> col1Col2Data = Mapping.LatLong.getColsData("assets/dd.csv",
                ",", 0, 1, 2);


        System.err.println(new Gson().toJson(col1Col2Data));

        final int nSamples = col1Col2Data.size();

        final double[][] inps = new double[nSamples][2];
        final double[][] outs = new double[nSamples][1];

        double[] sum = new double[nSamples];
        double[] input1 = new double[nSamples];
        double[] input2 = new double[nSamples];

        for (int i = 0; i < col1Col2Data.size(); i++) {
            final String inp1 = col1Col2Data.get(i).get(0);
            final String inp2 = col1Col2Data.get(i).get(1);
            final String out = col1Col2Data.get(i).get(2);
            input1[i] = Double.parseDouble(inp1);
            input2[i] = Double.parseDouble(inp2);
            sum[i] = Double.parseDouble(out);
            inps[i] = new double[]{input1[i], input2[i]};
            outs[i] = new double[]{sum[i]};
        }


    }

    @Test
    public void test2() {
        double XOR_INPUT[][] = {{1.0, 0.0}, {0.0, 0.0, 2.0},
                {0.0, 1.0}, {1.0, 1.0}};

        System.out.println(XOR_INPUT[3]);


    }


}
