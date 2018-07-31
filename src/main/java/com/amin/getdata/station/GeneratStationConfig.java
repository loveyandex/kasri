package com.amin.getdata.station;

import com.amin.ui.main.features.wholeyear.MyWriter;

import java.io.FileNotFoundException;
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
        FileReader reader = new FileReader("config/stations-temp.conf");
        Scanner scanner = new Scanner(reader);
        String total = "";
        int[] exps = new int[]{23, 28, 36, 40, 44, 49, 53, 58};
        int counter = 0;
        MyWriter myWriter = null;
        while (scanner.hasNextLine()) {
            counter++;
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
                    myWriter.appendStringInFile(stationNameline+"\n");
                } else
                    continue;
            }
            try {
                if (scanner.hasNextLine())
                    myWriter.appendStringInFile(scanner.nextLine()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
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
