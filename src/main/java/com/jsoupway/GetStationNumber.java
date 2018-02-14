package com.jsoupway;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GetStationNumber {
    public static void main(String[] args) {

        try {
            getStationNumber("config/iraq.conf","config/iraq-stations.conf");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void getStationNumber(String pathConfg, String stationConf) throws IOException {
        FileReader reader = new FileReader(pathConfg);
        Scanner scanner = new Scanner(reader);
        File fileStations = new File(stationConf);
        FileWriter writer = new FileWriter(fileStations);
        if (fileStations.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }

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

}
