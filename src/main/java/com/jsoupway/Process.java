package com.jsoupway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Process {

    public static String baseUrl = "http://weather.uwyo.edu/cgi-bin/sounding?";

    public static void getData(String pathDirToSave, String stationsPath, String year, String mounth) {
        Elements elements = null;
        Stack<String> urls = new Stack<>();
        Document earth = null;
        FileReader reader = null;
        try {
            reader = new FileReader(stationsPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String stationOne = scanner.nextLine();
            try {
                earth = Jsoup.connect(setLasturl("mideast",
                        "TEXT:LIST", year, mounth, "all", "0100", stationOne)).get();
                String text = earth.body().getElementsByTag("pre").text();
                System.out.println(setLasturl("mideast",
                        "TEXT:LIST", year, mounth, "all", "0100", stationOne));
                File dirTOSave = new File(pathDirToSave);
                dirTOSave.mkdirs();
                File fileTosave = new File(dirTOSave, "/" + stationOne + ".data");
                fileTosave.createNewFile();

                OutputStreamWriter inputStreamReader = new OutputStreamWriter(new FileOutputStream(fileTosave));
                inputStreamReader.write(text);
                inputStreamReader.flush();
                inputStreamReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static String setLasturl(String region, String TYPE, String Year, String Month, String From, String To, String Station) {
        return baseUrl + "region=" + region + "&TYPE=" + TYPE +
                "&YEAR=" + Year + "&MONTH=" + Month + "&FROM=" + From + "&TO=" + To + "&STNM=" + Station;
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

    public static Stack<String> getYears() {
        FileReader reader = null;
        Stack<String> years = new Stack<>();
        try {
            reader = new FileReader("config/years.conf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String year = scanner.nextLine();
            years.push(year);
        }
        return years;
    }

    public static void main(String[] args) {
        Stack<String> years = getYears();

        for (int i = 0; i < years.size(); i++)
            for (int j = 1; j <= 12; j++)
                Process.getData("G:/AMinAbvall/kasridata/iran/year_" + years.get(i) + "/month_" + String.valueOf(j),
                        "config/iran-stations.conf", years.get(i), String.valueOf(j));


    }
}
