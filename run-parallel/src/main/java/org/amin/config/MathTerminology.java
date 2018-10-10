package org.amin.config;

/**
 * is created by aMIN on 7/19/2018 at 02:47
 */
public class MathTerminology {
    public static final String MAXVALUE="max value";
    public static final String MINVALUE = "min value";
    public static final String VARIENCE = "Varience";
    public static final String STANDARDDEVIATION = "Standard deviation";
    public static final String AVARAGE = "average";

    public static double min(double... vals) {
        double m = +1.0D / 0.0;
        double[] var3 = vals;
        int var4 = vals.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            double v = var3[var5];
            m = Math.min(v, m);
        }

        return m;
    }

}
