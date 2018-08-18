package test.text;

import org.junit.Test;

/**
 * is created by aMIN on 7/31/2018 at 5:31 AM
 */
public class Analy {
    @Test
    public void test(){
        String s = "fff  gg ";
        String s1 = s.replaceAll("  ", " ").replaceAll(" ", "_");
        System.out.println(s1);

        String ss = "dsdsd/\\dsd";
        String s2 = ss.replaceAll("/\\\\", ";");
        System.out.println(s2);

    }

    @Test
    public void test3434() {
        s="s                       f         g";
        rem();
        System.out.println(s);
    }

    public String s;

    private void rem() {
        s = s.replaceAll("  ", " ");
        if (s.contains("  "))
            rem();
        else return;
    }


}
