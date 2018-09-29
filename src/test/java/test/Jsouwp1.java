package test;

import com.opencsv.CSVWriter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * is created by aMIN on 7/16/2018 at 21:10
 */
public class Jsouwp1 {
    private static final String STRING_ARRAY_SAMPLE = "./dollar-euro.csv";

    public static void main(String[] args) throws IOException {
        String url = "http://varzesh3.com";

        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Element body = document.body();
        Element knowledge = body.getElementsByTag("span").get(0);
        String eu = knowledge.text();
        System.out.println(eu);
    }


}

