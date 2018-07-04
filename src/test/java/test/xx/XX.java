package test.xx;

import org.apache.commons.io.FileUtils;

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
}
