package com.amin.getdata;

import com.amin.ui.dialogs.Dialog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Process implements Runnable {

    public static String baseUrl = "http://weather.uwyo.edu/cgi-bin/sounding?";

    public static void getData(String pathDirToSave, String stationsPath, String year, String mounth) {
        String text = "";
        Elements elements = null;
        Stack<String> urls = new Stack<>();
        Document document = null;
        FileReader reader = null;
        try {
            reader = new FileReader(stationsPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String stationOne = scanner.nextLine();
            String url11 = setLasturl("mideast",
                    "TEXT:LIST", year, mounth, "all", "0100", stationOne);

            try {
                document = Jsoup.connect(url11).get();
                Elements h2 = document.body().getElementsByTag("h2");
                Elements preElements = document.body().getElementsByTag("pre");
                int i = 0, j = 0;
                while (i < h2.size()) {
                    j = 2 * i;
                    text += h2.get(i) + "\n" + "<item1>" + "\n" + preElements.get(j).text() + "\n" + "</item1>" + "\n" + "<item2>" + "\n" + preElements.get(j + 1).text() + "\n" + "</item2>" + "\n";
                    i++;
                }

                System.out.println(setLasturl("mideast",
                        "TEXT:LIST", year, mounth, "all", "0100", stationOne));

                File dirTOSave = new File(pathDirToSave);
                dirTOSave.mkdirs();
                File fileTosave = new File(dirTOSave, "/" + stationOne + ".data");
                fileTosave.createNewFile();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileTosave));
                outputStreamWriter.write(text);
                outputStreamWriter.flush();
                outputStreamWriter.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                Methods.writeFallenUrls(url11);
                System.out.println("king error");
                continue;
            }

            System.out.println("end of line while");
            text = "";
        }
    }


    public static void getDataFrom(String pathDirToSave, String stationsPath, String region, String year, String mounth) {
        String text = "";
        Elements elements = null;
        Stack<String> urls = new Stack<>();
        Document document = null;
        FileReader reader = null;
        try {
            reader = new FileReader(stationsPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String stationOne = scanner.nextLine();
            String url11 = setLasturl(region,
                    "TEXT:LIST", year, mounth, "all", "0100", stationOne);

            try {
                document = Jsoup.connect(url11).get();
                Elements h2 = document.body().getElementsByTag("h2");
                Elements preElements = document.body().getElementsByTag("pre");
                int i = 0, j = 0;
                while (i < h2.size()) {
                    j = 2 * i;
                    text += h2.get(i) + "\n" + "<item1>" + "\n" + preElements.get(j).text() + "\n" + "</item1>" + "\n" + "<item2>" + "\n" + preElements.get(j + 1).text() + "\n" + "</item2>" + "\n";
                    i++;
                }

                System.out.println(setLasturl("mideast",
                        "TEXT:LIST", year, mounth, "all", "0100", stationOne));

                File dirTOSave = new File(pathDirToSave);
                dirTOSave.mkdirs();
                File fileTosave = new File(dirTOSave, "/" + stationOne + ".data");
                fileTosave.createNewFile();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileTosave));
                outputStreamWriter.write(text);
                outputStreamWriter.flush();
                outputStreamWriter.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                Methods.writeFallenUrls(url11);
                System.out.println("king error");
                continue;
            }

            System.out.println("end of line while");
            text = "";
        }
    }

    public static void getData2(String pathDirToSave, String stationNumber, String year, String mounth) {
        String text = "";
        Document document;

        String stationOne = stationNumber;
        String url11 = setLasturl("mideast",
                "TEXT:LIST", year, mounth, "all", "0100", stationOne);

        try {
            document = Jsoup.connect(url11).get();
            Elements h2 = document.body().getElementsByTag("h2");
            Elements preElements = document.body().getElementsByTag("pre");
            int i = 0, j = 0;
            while (i < h2.size()) {
                j = 2 * i;
                text += h2.get(i) + "\n" + "<item1>" + "\n" + preElements.get(j).text() + "\n" + "</item1>" + "\n" + "<item2>" + "\n" + preElements.get(j + 1).text() + "\n" + "</item2>" + "\n";
                i++;
            }
            ;
            System.out.println(setLasturl("mideast",
                    "TEXT:LIST", year, mounth, "all", "0100", stationOne));

            File dirTOSave = new File(pathDirToSave);
            dirTOSave.mkdirs();
            File fileTosave = new File(dirTOSave, "/" + stationOne + ".data");
            fileTosave.createNewFile();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileTosave));
            outputStreamWriter.write(text);
            outputStreamWriter.flush();
            outputStreamWriter.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Methods.writeFallenUrls(url11);
            System.out.println("king error");
        }

        System.out.println("end of line while");
        text = "";

    }


    public static String setLasturl(String region, String TYPE, String Year, String Month, String From, String To, String Station) {
        return baseUrl + "region=" + region + "&TYPE=" + TYPE +
                "&YEAR=" + Year + "&MONTH=" + Month + "&FROM=" + From + "&TO=" + To + "&STNM=" + Station;
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

    public static void start() {
        Stack<String> years = getYears();
        for (int k = 0; k < Starter.COUNTRIES.length; k++) {

            try {
                Methods.getStationNumber("config/states/" + Starter.COUNTRIES[k] + ".conf", "config/stations/" + Starter.COUNTRIES[k] + "-stations.conf");
            } catch (IOException e) {
                e.printStackTrace();
            }

//"G:/Program Files/AMinAbvall/kasridata
            for (int i = 0; i < years.size(); i++) {
                for (int j = 1; j <= 12; j++) {
                    if (!Starter.terminateThread) {
                        System.out.println("year is > " + years.get(i) + " month: > " + j + "  is started dowing");
                        Process.getData(Starter.ABSOLUTE_ROOT_PATH + "/" + Starter.COUNTRIES[k] + "/year_" + years.get(i) + "/month_" + String.valueOf(j),
                                "config/stations/" + Starter.COUNTRIES[k] + "-stations.conf", years.get(i), String.valueOf(j));
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Dialog.createExceptionDialog(new RuntimeException("gettng RawData stopped !!!"));
                    }
                }
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public static void main(String[] args) {
//        start();
        getData2("G:/alternative/newmew", "40745", "1997", "5");
    }

    @Override
    public void run() {
        start();
    }


}



