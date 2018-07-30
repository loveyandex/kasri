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
    @Test
    public void test2(){
        int runs = 10000000; // enough to run for 2-10 seconds.
        long start = 0;
        start = System.nanoTime();
        for(int i=-10000;i<1000000*runs;i++) {
            // do test
        }
        System.out.println(start);
        long time = System.nanoTime() - start;
        double t=time/1e9d;
        System.out.printf("Each XXXXX took an average of %f s%n",t);
    }

}
