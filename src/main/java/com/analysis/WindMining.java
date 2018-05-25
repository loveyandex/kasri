package com.analysis;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * is created by aMIN on 5/25/2018 at 21:38
 */

public class WindMining {


    public static void getWindSpeedCol(String dayFile, String fileName) throws IOException {

        FileReader reader = new FileReader(dayFile);
        Scanner scanner = new Scanner(reader);

        String total = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(";");
            System.out.println(split[7]);
            total += split[1]+";"+split[6] +";"+split[7] + "\r\n";
        }

        RawMining.writeInFileInOnce(System.getProperty("user.dir") + "/assets", fileName+"WindSpeed" + ".csv", new StringBuilder(total), true);

    }


    public static void main(String[] args) {
        try {
            WindMining.getWindSpeedCol("assets/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
