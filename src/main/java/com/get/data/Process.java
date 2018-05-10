package com.get.data;

import com.Application;
import com.telegram.bot.notify.NotifyBot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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

            } catch (IOException e) {
                System.out.println(e);
                Methods.writeFallenUrls(url11);
                try {
                    NotifyBot.getNotifyBot().sendMessage(new SendMessage()
                            .setChatId("145464749")
                            .setText(e.toString() + "\n\n" + e.getMessage() + "\n\n"
                                    + e.getLocalizedMessage() + " \n" + url11));
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
                System.out.println("king error");
            }

            System.out.println("end of line while");
            text = "";
        }
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
        for (int k = 0; k < Application.COUNTRIES.length; k++) {

        try {
            Methods.getStationNumber("config/"+ Application.COUNTRIES[k] +".conf","config/"+ Application.COUNTRIES[k] +"-stations.conf");
        } catch (IOException e) {
            e.printStackTrace();
        }

//"G:/Program Files/AMinAbvall/kasridata
        for (int i = 0; i < years.size(); i++) {
            for (int j = 1; j <= 12; j++) {
                System.out.println("year is > " + years.get(i) + " month: > " + j + "  is started dowing");
                Process.getData(Application.ABSOLUTE_ROOT_PATH + "/" + Application.COUNTRIES[k] + "/year_" + years.get(i) + "/month_" + String.valueOf(j),
                        "config/" + Application.COUNTRIES[k] + "-stations.conf", years.get(i), String.valueOf(j));

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

}

    public static void main(String[] args) {
        start();
    }

    @Override
    public void run() {
        start();
    }
}
