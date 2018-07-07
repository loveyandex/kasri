package test.xx;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class XX {

    public static void main(String[] args) {
        try {
            FileUtils.copyFile(new File("\\\\bsc5\\E\\csvs\\iran\\year_2006\\month_4\\40745\\00Z_02_Apr_2006.csv")
                    ,new File("C:\\Users\\95129032\\Desktop\\g34.csv"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testbreak(){
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 22; j++) {
                System.out.println(j);
                System.out.println(i);
            }
        }
    }

    @Test
    public void test2(){
        System.out.println("asymmetrik".toUpperCase());
    }
}
