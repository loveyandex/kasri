package com.jsoupway;

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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String stationOne = scanner.nextLine();
            String url11=setLasturl("mideast",
                    "TEXT:LIST", year, mounth, "all", "0100", stationOne);

            try {
                earth = Jsoup.connect(url11).get();
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
                try {
                    NotifyBot.getNotifyBot().sendMessage(new SendMessage()
                            .setChatId("145464749")
                            .setText(e.toString()+"\n\n"+e.getMessage()+"\n\n"
                                    +e.getLocalizedMessage()+" \n"+url11));
                } catch (TelegramApiException e1) {
                    e1.printStackTrace();
                }
                System.out.println("king error");
            }

            System.out.println("end of line while");
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
//"G:/Program Files/AMinAbvall/kasridata
        for (int i = 0; i < years.size(); i++) {
            for (int j = 1; j <= 12; j++) {
                System.out.println("year is > " + years.get(i) + " month: > " + j + "  is started dowing");
                Process.getData(Frame.ABSOLUTE_ROOT_PATH + "/"+Frame.COUNTRY+"/year_" + years.get(i) + "/month_" + String.valueOf(j),
                        "config/"+Frame.COUNTRY+"-stations.conf", years.get(i), String.valueOf(j));

                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
