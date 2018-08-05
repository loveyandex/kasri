package com.amin.IO;

import com.amin.IO.MyWriter;
import lombok.val;

import java.io.File;
import java.io.IOException;

/**
 * is created by aMIN on 8/5/2018 at 11:14 PM
 */
public class Exce {
    public static void main(String[] args) throws IOException {
        MyWriter myWriter = new MyWriter("config", "old-exception-stations.conf", true);
        File file = new File("config/old-stations");
        if (file.isDirectory()) {
            val files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                final val file1 = files[i];
                if (file1.getName().contains(".conf.csv"))
                {
                    final String x = file1.getName().replaceAll(".conf.csv", "");
                    File file2=new File("config/states",x+".conf");
                    file2.delete();
                }


            }
        }
    }
}
