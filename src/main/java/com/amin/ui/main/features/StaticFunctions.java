package com.amin.ui.main.features;

import java.util.ArrayList;
import java.util.Vector;

/**
 * is created by aMIN on 10/14/2018 at 3:09 AM
 */
public class StaticFunctions {


    protected Double intrapolateinAllFeature(double height, ArrayList<ArrayList<Double>> heightAndFeature) {

        Double knotdesire = null;
        double heightdesire =height ;
        final Vector<Double> heigthsVector = new Vector<>();
        final Vector<Double> knotsVector = new Vector<>();

        heightAndFeature.forEach(doubles -> {

            double h = (doubles.get(0));
            double knot = (doubles.get(1));
            heigthsVector.add(h);
            knotsVector.add(knot);

        });

        for (int i = 0; i < heigthsVector.size() - 1; i++) {
            double hi = heigthsVector.get(i);
            double hiplus = heigthsVector.get(i + 1);
            double knoti = knotsVector.get(i);
            double knotiplus = knotsVector.get(i + 1);
            if ((heightdesire - hi) * (heightdesire - hiplus) <= 0) {
                knotdesire = (knotiplus - knoti) * (heightdesire - hi) / (hiplus - hi) + (knoti);
                break;
            }

        }
        return knotdesire;
    }


}
