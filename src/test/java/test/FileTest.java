package test;

import org.junit.Test;

import java.io.File;

/**
 * is created by aMIN on 6/1/2018 at 04:45
 */
public class FileTest {

    @Test
    public void test1() {
        File file=new File("G:\\Program Files\\AMinAbvall\\kasridata\\iran\\year_2017\\month_2\\4075432323.data");
        System.out.println(file.mkdir());
    }
}
