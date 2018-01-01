package com.jsoupway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Stack;

public class Process {

    public static void getData() {
        Elements elements = null;
        Stack<String> urls = new Stack<>();
        Document earth = null;
        try {
            earth = Jsoup.connect("http://weather.uwyo.edu/cgi-bin/sounding?region=naconf&TYPE=TEXT%3ALIST&YEAR=2018&MONTH=01&FROM=all&TO=0100&STNM=71845").get();
            earth.body().getElementsByTag("pre");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
