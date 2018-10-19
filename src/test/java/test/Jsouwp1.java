package test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * is created by aMIN on 7/16/2018 at 21:10
 */
public class Jsouwp1 {
    private static final String STRING_ARRAY_SAMPLE = "./dollar-euro.csv";

    public static void main(String[] args) throws IOException {
        String url = "http://pornhub.com";

        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Element body = document.body();
        final Elements spans = body.getElementsByTag("span");
        Element knowledge = spans.get(0);


        spans.forEach(element -> {
            System.out.println(element.text());
        });


        String eu = knowledge.text();
        System.out.println(eu);
    }


}

