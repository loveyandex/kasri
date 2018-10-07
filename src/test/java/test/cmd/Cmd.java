package test.cmd;

import org.junit.Test;

import java.io.IOException;

/**
 * is created by aMIN on 10/4/2018 at 7:34 PM
 */
public class Cmd {
    @Test
    public void test2() throws IOException {
        final String command = String.format("cmd /k   explorer /select ,%s", "c:\\libs");
        System.out.println(command);
        Runtime.getRuntime().exec(command);


    }

}
