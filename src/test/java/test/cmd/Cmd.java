package test.cmd;

import java.io.IOException;

/**
 * is created by aMIN on 10/4/2018 at 7:34 PM
 */
public class Cmd {
    public static void run(String s) throws IOException {
        Runtime.getRuntime().exec(s);
    }
}
