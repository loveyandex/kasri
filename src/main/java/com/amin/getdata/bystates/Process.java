package com.amin.getdata.bystates;


import com.amin.IO.MyReader;
import com.amin.notify.Noti;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
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
            if (Starter.mustStop)
                break;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String stationOne = scanner.nextLine();
            String url11 = setLasturl("TEXT:LIST", year, mounth, "all", "0100", stationOne);

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

                System.out.println(url11);

                File dirTOSave = new File(pathDirToSave);
                dirTOSave.mkdirs();
                File fileTosave = new File(dirTOSave, "/" + stationOne + ".data");
                fileTosave.createNewFile();
                System.out.println(fileTosave.getAbsolutePath());

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileTosave));
                outputStreamWriter.write(text);
                outputStreamWriter.flush();
                outputStreamWriter.close();

            } catch (org.jsoup.UncheckedIOException ee) {
                System.out.println(ee.toString());
                System.out.println("queen error");
                Methods.writeFallenUrls(     "config/fallenUrls3.conf",CrashedCountry, url11);
                new Thread(() -> {
                    while (true){
                        try {
                            Noti.sendmsg("172.24.65.93",8687,ee.toString());

                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                continue;
            }catch (Exception e) {
                System.out.println(e.getMessage());
                Methods.writeFallenUrls(CrashedCountry,url11);
                System.out.println("king error");
                continue;
            }

            System.out.println("end of line while");
            text = "";
        }
    }








    public static String setLasturl(String region, String TYPE, String Year, String Month, String From, String To, String Station) {
        return baseUrl + "region=" + region + "&TYPE=" + TYPE +
                "&YEAR=" + Year + "&MONTH=" + Month + "&FROM=" + From + "&TO=" + To + "&STNM=" + Station;
    }


    public static String setLasturl( String TYPE, String Year, String Month, String From, String To, String Station) {
        return baseUrl + "TYPE=" + TYPE +
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

    private ArrayList<String> getyears() throws FileNotFoundException {
        return MyReader.readFileLines("config/years.conf");
    }


    public static void start() {
        Stack<String> years = getYears();
        outerloop:
        for (int k = 0; k < Starter.COUNTRIES.length; k++) {

            try {
                Methods.getStationNumber("config/states/" + Starter.COUNTRIES[k] + ".conf"
                        , "config/stations/" + Starter.COUNTRIES[k] + "-stations.conf");
            } catch (IOException e) {
                e.printStackTrace();
            }

//"G:/Program Files/AMinAbvall/kasridata

            for (int i = 0; i < years.size(); i++) {
                for (int j = 1; j <= 12; j++) {
                    if (Starter.mustStop)
                        break outerloop;
                    System.out.println("year is > " + years.get(i) + " month: > " + j + "  is started dowing");
                    CrashedCountry = Starter.COUNTRIES[k];

                    String pathDirToSave = Starter.ABSOLUTE_ROOT_PATH + "/" + Starter.COUNTRIES[k] + "/year_" + years.get(i) + "/month_" + String.valueOf(j);
                    Process.getData(pathDirToSave,
                                "config/stations/" + Starter.COUNTRIES[k] + "-stations.conf"
                                , years.get(i)
                                , String.valueOf(j));
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                }
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        System.out.println("finished all stations");

    }
    private static String CrashedCountry="";

    public static void main(String[] args) {
//        start();
//        getData2("G:/alternative/newmew","40745","1997","5");
    }

    @Override
    public void run() {

        start();
        System.out.printf("end of run");
    }


}



