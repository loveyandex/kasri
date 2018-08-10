package test.analys;

import jsat.io.JSATData;
import jsat.math.Complex;
import jsat.math.MathTricks;
import lombok.experimental.var;

/**
 * is created by aMIN on 7/18/2018 at 20:52
 */
public class F {
    public static void main(String[] args) {
        jsat.math.Complex cd=new Complex(323.3,2323.23);
        var divide = cd.divide(new Complex(32, 32));
        System.out.println(divide);

        double maxpos = max(-242434493, 23, 23, 23, 23, 23, 2, 3, 23, 5, 3, 432, 543, 52, 356, 45, 423, 56324, 23, 4234, 6, 57, 432, 4, 5);
        System.out.println(maxpos);

    }

    public static double max(double... vals) {
        double m = -1.0D / 0.0;
        double[] var3 = vals;
        int var4 = vals.length;
        int var6 = -1;

        for(int var5 = 0; var5 < var4; ++var5) {
            double v = var3[var5];
            m = Math.max(v, m);
        }

        return m;
    }
}
