package com.jsoupway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Stack;

public class Process {

    public static void getData() {
        Elements elements = null;
        Stack<String> urls = new Stack<>();
        Document earth = null;
        try {
            earth = Jsoup.connect("http://weather.uwyo.edu/cgi-bin/sounding?region=naconf&TYPE=TEXT%3ALIST&YEAR=2015&MONTH=03&FROM=all&TO=3112&STNM=76225").get();
            String text=earth.body().getElementsByTag("pre").text();
            File file=new File("e:\\data.dat");
            file.createNewFile();
            OutputStreamWriter inputStreamReader=new OutputStreamWriter(new FileOutputStream(file));
            inputStreamReader.write(text);
            inputStreamReader.flush();
            inputStreamReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        Process.getData();
    }
}
