package test.analys;

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

    }
}
