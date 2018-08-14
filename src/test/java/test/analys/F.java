package test.analys;

import com.amin.ui.scripts.ScriptAPP;
import jsat.io.JSATData;
import jsat.math.Complex;
import jsat.math.MathTricks;
import lombok.experimental.var;

/**
 * is created by aMIN on 7/18/2018 at 20:52
 */
public class F {
    public static void main(String[] args) {
        ScriptAPP.scripting("onday 40800 10 26 WIND_SPEED m/s 20000 1973 2017 iran__islamic_rep");


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
