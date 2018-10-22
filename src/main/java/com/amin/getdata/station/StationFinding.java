package com.amin.getdata.station;

import com.opencsv.CSVWriter;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * is created by aMIN on 7/31/2018 at 3:47 AM
 */
public class StationFinding {
    public static void main(String[] args) throws IOException {

        String containet = "naconf,samer,pac,nz,ant,np,europe,africa,seasia,mideast";
        String[] cntns = containet.split(",");

        for (int i = 0; i < cntns.length; i++) {


            String url = "http://weather.uwyo.edu/upperair/" + cntns[i] + ".html";
            Connection connect = org.jsoup.Jsoup.connect(url);
            Document document = connect.get();
            Element body = document.body();
            Element map = body.getElementsByTag("map").get(0);
            Elements areaElements = map.getElementsByTag("area");
            areaElements.remove(areaElements.size() - 1);
            int finalI = i;
            areaElements.forEach(element -> {
                String title = element.attr("title");
                String[] split = title.split(" ");
                String stnnum = split[0];
                String stnName = title.replaceAll(stnnum, "");


                try (
                        Writer writer2 = Files.newBufferedWriter(Paths.get("config/regins/" + cntns[finalI] + "-stationnumbers.conf"), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                        Writer writer11 = Files.newBufferedWriter(Paths.get("config/regins/" + cntns[finalI] + "-withname-stationnumbers.conf"), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                        Writer writer1 = Files.newBufferedWriter(Paths.get("config/regins/" + cntns[finalI] + "-withname-stationnumbers.conf"), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                        Writer writer = Files.newBufferedWriter(Paths.get("config/regins/" + cntns[finalI] + "-stationnumbers.conf"), StandardCharsets.UTF_8, StandardOpenOption.APPEND);

                        CSVWriter csvWriter = new CSVWriter(writer,
                                CSVWriter.DEFAULT_SEPARATOR,
                                CSVWriter.NO_QUOTE_CHARACTER,
                                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                CSVWriter.DEFAULT_LINE_END);
                        CSVWriter csvWriter1 = new CSVWriter(writer1,
                                CSVWriter.DEFAULT_SEPARATOR,
                                CSVWriter.NO_QUOTE_CHARACTER,
                                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                CSVWriter.DEFAULT_LINE_END);
                ) {
                    csvWriter.writeNext(new String[]{stnnum});
                    csvWriter1.writeNext(new String[]{stnnum, stnName});
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });


        }
    }
}
