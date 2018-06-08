package test.analys;

import com.config.C;
import org.junit.Test;

/**
 * is created by aMIN on 6/6/2018 at 01:38
 */
public class T {
    @Test
    public void test(){
        String line = "   ADANA/INCIRLIK A LTAG        17350  37 00N  035 25E   66   X     T          6 TR";
        System.out.println(line);
        String x = line.substring(3, 19).replaceAll(" ", "^");
        line=line.substring(19,line.length());
        line="   "+x+line;
        System.out.println(x);
        System.out.println(line);

        System.out.println("   ABADAN INTL AIRP OIAA        40831".length());

    }


    @Test
    public void test2(){
        String string="ANKAR^^K";
        System.out.println(string.replaceAll("[?^]",C.SPACE));

        int num = Integer.parseInt("000008");
        String text = (num < 10 ? "0" : "") + num;
        System.out.println(text);
    }
}
