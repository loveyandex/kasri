package com.amin.analysis;

import com.amin.config.C;
import com.amin.database.Driver;
import com.amin.ui.dialogs.Dialog;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * is created by aMIN on 6/5/2018 at 22:11
 */
public class Mapping {

    final static Connection connection = Driver.getDriver().getConnection();

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
            String LAT = features[3];
            String LONG = features[3];
            mapStationNumberName.put(stationName, stationNumber);

        }

        return mapStationNumberName;
    }

    public static ArrayList<String> readFileLines(String path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        FileReader reader = new FileReader(path);
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine())
            lines.add(scanner.nextLine());
        return lines;

    }

    public static ArrayList<String> getFileLines(String path) throws FileNotFoundException {
        return readFileLines(path);
    }

    public static void main(String[] args) throws IOException {

        String abspathfile = "config/countries.configfile.conf";
        final ArrayList<String> fileLines = getFileLines(abspathfile);
        fileLines.forEach(Mapping::mapForOldFolder);

    }

    static void map(String rootparent, String fn) {
        final ArrayList<ArrayList<String>> latLongForAContryCities;

        latLongForAContryCities = LatLong.getLatLongForAContryCities(rootparent, fn);
        latLongForAContryCities
                .forEach(strings -> {
                    try {

                        final PreparedStatement preparedStatement = connection.prepareStatement("insert into station_latlong (station,country, citiname, lati ,longi) values (?,?,?,?,?);");

                        preparedStatement.setString(1, strings.get(0));
                        preparedStatement.setString(2, strings.get(1));
                        preparedStatement.setString(3, strings.get(2));
                        preparedStatement.setString(4, strings.get(3));
                        preparedStatement.setString(5, strings.get(4));
                        preparedStatement.executeUpdate();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    static void mapForOldFolder(String fn) {
        map("config/old-stations", fn);

    }

    public static class LatLong {

        public static ArrayList<ArrayList<String>> getCol1Col2Data(String absfilepath, int col1, int col2) throws IOException {

            FileReader reader = new FileReader(absfilepath);
            Scanner scanner = new Scanner(reader);

            ArrayList<ArrayList<String>> points = new ArrayList<>();
            while (scanner.hasNextLine()) {
                ArrayList<String> point = new ArrayList<>();
                String line = scanner.nextLine();
                String[] split = line.split(";");
                point.add(split[col1]);
                point.add(split[col2]);
                points.add(point);
            }
            return points;
        }

        public static ArrayList<ArrayList<String>> getCol1Col2Data(String absfilepath, int col1, int col2, String sparate) throws IOException {

            FileReader reader = new FileReader(absfilepath);
            Scanner scanner = new Scanner(reader);

            ArrayList<ArrayList<String>> points = new ArrayList<>();
            while (scanner.hasNextLine()) {
                ArrayList<String> point = new ArrayList<>();
                String line = scanner.nextLine();
                String[] split = line.split(sparate);
                point.add(split[col1]);
                point.add(split[col2]);
                points.add(point);
            }
            return points;
        }

        public static ArrayList<ArrayList<String>> getColsData(String absfilepath, int... cols) throws IOException {

            FileReader reader = new FileReader(absfilepath);
            Scanner scanner = new Scanner(reader);

            ArrayList<ArrayList<String>> points = new ArrayList<>();
            while (scanner.hasNextLine()) {
                ArrayList<String> point = new ArrayList<>();
                String line = scanner.nextLine();
                String[] split = line.split(";");
                for (int i = 0; i < cols.length; i++) {
                    point.add(split[cols[i]]);
                }
                points.add(point);
            }
            return points;
        }

        public static ArrayList<ArrayList<String>> getColsData(String absfilepath, String sep, int... cols) throws IOException {

            FileReader reader = new FileReader(absfilepath);
            Scanner scanner = new Scanner(reader);

            ArrayList<ArrayList<String>> points = new ArrayList<>();
            while (scanner.hasNextLine()) {
                ArrayList<String> point = new ArrayList<>();
                String line = scanner.nextLine();
                String[] split = line.split(sep);
                for (int i = 0; i < cols.length; i++) {
                    point.add(split[cols[i]]);
                }
                points.add(point);
            }
            scanner.close();
            reader.close();
            return points;
        }

        static public ArrayList<String> getLinesFile(String absfilepath) throws IOException {
            FileReader reader = new FileReader(absfilepath);
            Scanner scanner = new Scanner(reader);

            ArrayList<String> lines = new ArrayList<>();
            while (scanner.hasNextLine())
                lines.add(scanner.nextLine());

            return lines;
        }

        public static void writeStringInFile(String pathDirToSave, String childFileName, StringBuilder stringBuilder, boolean append) throws IOException {
            File dir = new File(pathDirToSave);
            dir.mkdirs();
            File fileTosave = new File(dir, childFileName);
            fileTosave.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(fileTosave, append);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.write(stringBuilder.toString());
            writer.flush();
            writer.close();
            fileOutputStream.close();
//            System.out.println("written in " + fileTosave.getPath());
        }

        public static void writeStringInFile(String pathDirToSave, String childFileName, String s, boolean append) throws IOException {
            File dir = new File(pathDirToSave);
            dir.mkdirs();
            File fileTosave = new File(dir, childFileName);
            fileTosave.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(fileTosave, append);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            writer.write(s);
            writer.flush();
            writer.close();
            fileOutputStream.close();
            System.out.println("king write in " + fileTosave.getPath());
        }

        public static ArrayList<ArrayList<String>> getLatLongForAContryCities(final String rootparent, String countryconfigfilename) {
            ArrayList<ArrayList<String>> colsData = null;
            try {
                colsData = getColsData(rootparent + File.separator + countryconfigfilename, 0, 3, 4, 5, 6, 7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<ArrayList<String>> output = new ArrayList<>();

            for (int j = 0; j < colsData.size(); j++) {
                String citiname = colsData.get(j).get(0).replaceAll("\\^", " ");
                String citistationnumber = colsData.get(j).get(1);
                System.out.println(citiname);
                double citiLat = Double.parseDouble(colsData.get(j).get(2))
                        + (Double.parseDouble(colsData.get(j).get(3).replaceAll("N", ""))) / 60.0d;
                double citiLong = Double.parseDouble(colsData.get(j).get(4))
                        + (Double.parseDouble(colsData.get(j).get(5).replaceAll("E", ""))) / 60.0d;


                ArrayList inoutput = new ArrayList() {{
                    String csn = citistationnumber.replaceAll(" ", "_");
                    if (csn.equals("&"))
                        csn = "NULL";
                    add(csn);
                    add(countryconfigfilename.replaceAll(".conf.csv", ""));
                    add(citiname);
                    add(String.valueOf(citiLat));
                    add(String.valueOf(citiLong));

                }};
                output.add(inoutput);

                System.out.println(citiLat);
                System.out.println(citiLong);

            }
            return output;


        }

        public void createBigConfigFile() {

            File file = new File("config");
            file.mkdirs();
            File[] files = file.listFiles();

            for (File f : files) {
                if (f.isFile()) {
                    if (f.getName().contains(".conf.csv")) {
                        try {
                            writeStringInFile("config", "countries.configfile.conf", new StringBuilder(f.getName() + "\r\n"), true);
                        } catch (IOException e) {
                            Dialog.createExceptionDialog(e);
                        }
                    }

                }
            }
        }

        public void delimiteCSVColsWriteFile(boolean append, String pathDirToSave, String childFileName, String... colsvalue) throws IOException {
            File dir = new File(pathDirToSave);
            dir.mkdirs();
            File fileTosave = new File(dir, childFileName);
            System.out.println("saved in " + fileTosave.getPath());
            fileTosave.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileTosave, append));
            writer.write(String.join(";", colsvalue) + "\r\n");
            writer.flush();
        }

    }

}
