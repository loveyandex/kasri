package com.amin.analysis;

import com.amin.config.C;
import com.amin.ui.dialogs.Dialog;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * is created by aMIN on 6/5/2018 at 22:11
 */
public class Mapping {

    static public void createCSVFILEFORStations(String Dirpath, String fileName) throws IOException {

        FileReader reader = new FileReader(Dirpath + File.separator + fileName);
        Scanner scanner = new Scanner(reader);
        String total = "";
        int[] exps = new int[]{23, 28, 36, 40, 44, 49, 53, 58};
        int counter = 0;
        while (scanner.hasNextLine()) {
            counter++;
            String line = scanner.nextLine();
            if (counter == 1 || line.length() < 33 || line.toUpperCase().contains("STATION"))
                continue;
            else {
                String x = line.substring(3, 19).replaceAll(" ", "^");
                line = line.substring(19, line.length());
                line = "   " + x + line;
                for (int i = 0; i < exps.length; i++)
                    try {
                        if (line.charAt(exps[i]) == ' ') {
                            StringBuilder stringBuilder = new StringBuilder(line);
                            stringBuilder.setCharAt(exps[i], C.INSTEAD_PARAM_LESS);
                            line = stringBuilder.toString();
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        for (int j = i; j < exps.length; j++) {
                            StringBuilder ana = new StringBuilder(line);
                            int analength = ana.length();
                            int desire = exps[j];
                            for (int k = analength; k < desire; k++) {
                                ana.insert(k, " ");
                            }
                            ;
                            ana.insert(ana.length(), C.INSTEAD_PARAM_LESS);

                            line = ana.toString();
                        }
                        break;
                    }


                line = line.replace(" ", ";");
                String s = line;
                for (; ; )
                    if (s.contains(";;"))
                        s = s.replaceAll(";;", ";");
                    else
                        break;
                if (s.charAt(0) == ';')
                    s = s.replaceFirst(";", "");
                if (s.charAt(s.length() - 1) == ';')
                    s = s.substring(0, s.length() - 1);
                total += s + "\r\n";
            }

        }
//        System.out.println(total);
        RawMining.writeInFileInOnce(Dirpath, fileName + ".csv", new StringBuilder(total), true);
    }


    static public Map<String, String> MapStationNumTOCities(String pathConfigCSV) throws FileNotFoundException {
        Map<String, String> mapStationNumberName = new HashMap<>();
        FileReader reader = new FileReader(pathConfigCSV);
        Scanner scanner = new Scanner(reader);


        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            String[] strings = nextLine.split(";");
            String stationName = strings[0].replaceAll(C.STATION_NAME_SIMBOL, C.SPACE);
            String stationNumber = strings[3];
            mapStationNumberName.put(stationName, stationNumber);
        }

        return mapStationNumberName;
    }


    static public Map<String, String> MapLatLongToNearestCities(String pathConfigCSV) throws FileNotFoundException {
        Map<String, String> mapStationNumberName = new HashMap<>();
        FileReader reader = new FileReader(pathConfigCSV);
        Scanner scanner = new Scanner(reader);


        while (scanner.hasNextLine()) {

            String nextLine = scanner.nextLine();
            String[] features = nextLine.split(";");
            String stationName = features[0].replaceAll(C.STATION_NAME_SIMBOL, C.SPACE);
            String stationNumber = features[3];
            mapStationNumberName.put(stationName, stationNumber);

        }

        return mapStationNumberName;
    }





    public static ArrayList<String> readFileLines(String path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        FileReader reader = new FileReader(path);
        Scanner scanner = new Scanner(reader);
        String total = "";
        while (scanner.hasNextLine())
            lines.add(scanner.nextLine());
        return lines;

    }

    public static ArrayList<String> getFileLines(String path) throws FileNotFoundException {
        return readFileLines(path);
    }


    public static class LatLong {

        public void getLatLongStations() {

            File file = new File("config");
            file.mkdirs();
            File[] files = file.listFiles();

            for (File f:files) {
                if (f.isFile()) {
                    if (f.getName().contains(".conf.csv")) {
                        try {
                            writeInFileInOnce("config", "countries.configfile.conf", new StringBuilder(f.getName()+"\r\n"), true);
                        } catch (IOException e) {
                            Dialog.createExceptionDialog(e);
                        }
                    }

                }
            }
        }


        public static void main(String[] args) {
            new LatLong().getLatLongStations();
        }

        public static boolean writeInFileInOnce(String pathDirToSave, String childFileName, StringBuilder stringBuilder, boolean append) throws IOException {
            File dir = new File(pathDirToSave);
            dir.mkdirs();
            File fileTosave = new File(dir, childFileName);
            System.out.println("saved in "+fileTosave.getPath());
            fileTosave.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileTosave, append));
            writer.write(stringBuilder.toString());
            writer.flush();


            return true;
        }




    }
















    public static void main(String[] args) {
        try {
            Mapping.createCSVFILEFORStations("config", "iran.conf");
            Map<String, String> stationNumTOCities = Mapping.MapStationNumTOCities("config/iran.conf.csv");
            for (Map.Entry map : stationNumTOCities.entrySet()) {
                System.out.println(String.format("%s %s", map.getKey(), map.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
