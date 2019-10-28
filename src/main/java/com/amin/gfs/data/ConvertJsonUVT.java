package com.amin.gfs.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * created By gOD on 8/22/2019 3:53 AM
 */

public class ConvertJsonUVT {
    public static void main(String[] args) throws IOException {





        File file = new File("nws-data/");
        for (File listFile : file.listFiles()) {
            String outo = "nws-json/" + listFile.getName() + ".json";

            String path = listFile.getPath();
            Process exec = Runtime.getRuntime().exec("cmd.exe /k start & F:\\apps\\startup\\grib2json\\src\\bin\\grib2jso.cmd -n -d  -o "
                    + outo + "  " + path);

        }


//        InputStream inputStream = exec.getInputStream();
//        Scanner scanner = new Scanner(inputStream);
//        while (scanner.hasNextLine()) {
//            System.out.println(scanner.nextLine());
//        }
//        scanner.close();
//        inputStream.close();
//        exec.destroy();


    }
}
