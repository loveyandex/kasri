package com.jsoupway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Process {

    public static void getData() {
        Elements elements = null;
        Stack<String> urls = new Stack<>();
        Document earth = null;
        try {
            earth = Jsoup.connect("http://weather.uwyo.edu/cgi-bin/sounding?region=naconf&TYPE=TEXT%3ALIST&YEAR=2015&MONTH=03&FROM=all&TO=3112&STNM=76225").get();
            String text = earth.body().getElementsByTag("pre").text();
            File file = new File("e:\\data.dat");
            file.createNewFile();
            OutputStreamWriter inputStreamReader = new OutputStreamWriter(new FileOutputStream(file));
            inputStreamReader.write(text);
            inputStreamReader.flush();
            inputStreamReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String baseUrl = "http://weather.uwyo.edu/cgi-bin/sounding?";

    public static String setLasturl(String region, String TYPE, String Year, String Month, String From, String To, String Station) {
        return baseUrl + "region=" + region + "&TYPE=" + TYPE + "&Year" + Year + "&Month=" + "&From=" + From + "&To=" + To + "&STNM=" + Station;
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

    public static void main(String[] args) {
//        Process.getData();
        try {
            Process.getStationNumber("config/iran.conf", "config/iran-stations.conf");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
