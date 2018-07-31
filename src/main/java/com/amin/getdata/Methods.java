package com.amin.getdata;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Methods {
    public static void main(String[] args) {

        try {
            getStationNumber("config/" + Starter.COUNTRIES + ".conf", "config/" + Starter.COUNTRIES + "-stations.conf");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void getStationNumber(String pathConfg, String stationConf) throws IOException {
        FileReader reader = new FileReader(pathConfg);
        Scanner scanner = new Scanner(reader);
        File fileStations = new File(System.getProperty("user.dir") + File.separator + stationConf);
        if (fileStations.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }
        FileWriter writer = new FileWriter(fileStations);

        while (scanner.hasNextLine()) {
            String sl = scanner.nextLine();
            try {
                String stations = sl.substring(32, 37);
//Write Content
                if (!stations.contains(" ") && isNumeric(stations)) {
                    writer.write(stations + "\n");
                    writer.flush();
                }

            } catch (Exception e) {

            }
        }
        writer.close();
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }


    public static void writeFallenUrls(String fallenUrl) {
        File fileStations = new File(System.getProperty("user.dir") + File.separator + "config/fallenUrls.conf");
        try {
            if (fileStations.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileStations,true);
            writer.append(fallenUrl + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void writeFallenUrls(String fallenUrl,String fallUrlPathFile) {
        File fileStations = new File(fallUrlPathFile);
        try {
            if (fileStations.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileStations,true);
            writer.append(fallenUrl + "\r\n");
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}

