package com.amin.analysis.oldmapping;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * is created by aMIN on 5/25/2018 at 21:38
 */

public class OldMapping {

    public static ArrayList<ArrayList<String>> getWindSpeedCol(String dayDir, String fileName) throws IOException {

        FileReader reader = new FileReader(dayDir+File.separator+fileName);
        Scanner scanner = new Scanner(reader);

        ArrayList<ArrayList<String>> points=new ArrayList<>();


        String total = "";
        while (scanner.hasNextLine()) {
            ArrayList<String> point=new ArrayList<>();
            String line = scanner.nextLine();
            String[] split = line.split(";");
            total += split[1]+";"+split[6] +";"+split[7] + "\r\n";
            point.add(split[1]);
            point.add(split[7] );
            points.add(point);
        }
        System.out.println(total);

//        RawMining.writeInFileInOnce(dayDir, "WindSpeed"+fileName , new StringBuilder(total), true);
        return points;
    }


    public static ArrayList<ArrayList<String>> getCol1Col2(String dayfilePAth, int col1, int col2) throws IOException {

        FileReader reader = new FileReader(dayfilePAth);
        Scanner scanner = new Scanner(reader);

        ArrayList<ArrayList<String>> points=new ArrayList<>();
        String line="";
        while (scanner.hasNextLine()) {
                ArrayList<String> point = new ArrayList<>();
              line = scanner.nextLine();
                String[] split = line.split(";");
                point.add(split[col1]);
                point.add(split[col2]);
                points.add(point);
        }
        return points;
    }


    public static void main(String[] args) {
        try {
            OldMapping.getWindSpeedCol("assets/data", "00Z_01_Jan_2017.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
