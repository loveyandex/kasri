package com.amin.getdata.station;

import com.amin.io.MyWriter;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * is created by aMIN on 7/31/2018 at 4:58 AM
 */
public class GeneratStationConfig {
    public static void main(String[] args) throws IOException {
        String fileName = null;
        String Dirpath = null;
        FileReader reader = new FileReader("config/stationsL.conf");
        Scanner scanner = new Scanner(reader);

        MyWriter myWriter = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.isEmpty()) {

                if (scanner.hasNextLine()) {

                    if (myWriter != null){
                        myWriter.close();
                    }

                    String stationNameline = scanner.nextLine();
                    String stationname = stationNameline.substring(0, stationNameline.length() - 9);
                    String stationNameStandard = stationname
                            .replaceAll(",", "_").replaceAll("'", "_")
                            .replaceAll("\\\\","_").replaceAll("/","_")
                            .replaceAll("\"","")
                            .replaceAll("  ", " ").replaceAll(" ", "_");
                    String data = stationNameline.substring(stationNameline.length() - 9, stationNameline.length());
                    System.out.println(stationname);
                    System.out.println(stationNameStandard);
                    maked = stationNameStandard;
                    remove_fromlast();
                    System.out.println(maked);
                    myWriter = new MyWriter("config/states", maked.toLowerCase() + ".conf", true);
                    myWriter.appendStringInNewLine(stationNameline);
                } else
                    continue;
            } else {
                try {
                    myWriter.appendStringInFile(line+ "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    static String maked = "";

    static void remove_fromlast() {
        if (maked.charAt(maked.length() - 1) == '_') {
            maked = maked.substring(0, maked.length() - 1);
            remove_fromlast();
        }
    }


}
