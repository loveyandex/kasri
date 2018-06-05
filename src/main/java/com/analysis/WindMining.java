package com.analysis;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * is created by aMIN on 5/25/2018 at 21:38
 */

public class WindMining {


    public static ArrayList<ArrayList<String>> getWindSpeedCol(String dayFile, String fileName) throws IOException {

        FileReader reader = new FileReader(dayFile);
        Scanner scanner = new Scanner(reader);

        ArrayList<ArrayList<String>> points=new ArrayList<>();


        String total = "";
        while (scanner.hasNextLine()) {
            ArrayList<String> point=new ArrayList<>();
            String line = scanner.nextLine();
            String[] split = line.split(";");
            System.out.println(split[7]);
            total += split[1]+";"+split[6] +";"+split[7] + "\r\n";
            point.add(split[1]);
            point.add(split[7] );
            points.add(point);
        }

        RawMining.writeInFileInOnce(System.getProperty("user.dir") + "/assets/data", fileName+"WindSpeed" + ".csv", new StringBuilder(total), true);
        return points;
    }


    public static void main(String[] args) {
        try {
            WindMining.getWindSpeedCol("assets/data/00Z_08 _Jan _2017.csv", "00Z_08 _Jan _2017");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
