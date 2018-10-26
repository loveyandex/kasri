package test.index;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * is created by aMIN on 10/23/2018 at 6:54 PM
 */
public class New {
    public static void main(String[] args) {
        try {
            final long l = System.nanoTime();
            final String first1 = "F:\\apps\\pdf\\king.pdf";
            final byte[] bytes = Files.readAllBytes(Paths.get(first1));
            final String first = "F:\\apps\\pdf\\king.pdf";
            final byte[] bytes1 = Files.readAllBytes(Paths.get(first));
            System.out.println(Arrays.equals(bytes, bytes1));
            final long l1 = System.nanoTime();
            System.out.println((l1 - l) / 1e6d);
            final long l13 = System.nanoTime();
            System.out.println(FileUtils.contentEquals(new File(first), new File(first1)));
            final long l134 = System.nanoTime();

            System.out.println((-l13 + l134) / 1e6d);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
