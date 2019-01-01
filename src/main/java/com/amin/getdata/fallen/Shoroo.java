package com.amin.getdata.fallen;

import com.amin.analysis.Mapping;
import com.amin.config.C;
import com.amin.getdata.bystates.Process;
import com.amin.io.MyReader;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

public class Shoroo {

    public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException, InterruptedException {
        MyReader myReader = new MyReader("config/fallenUrls2.conf");
        Scanner scanner = myReader.getScanner();
        int ini = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            ini++;
            if ( ini> 85000) {
                System.err.println(ini);

                String[] split = line.split(";");
                String countryy = split[0];
                String url = split[1];
                Map<String, List<String>> stringListMap = splitQuery(new URL(url));

                String year = stringListMap.get("YEAR").get(0);
                String month = stringListMap.get("MONTH").get(0);
                String stnm = stringListMap.get("STNM").get(0);

                String pathDirToSave = C.DATA_PATH + "/" + countryy + "/year_" + (year) + "/month_" + month;
                Process.getDataStreight(pathDirToSave, url, stnm, year, month);

                System.out.println(countryy);
                System.out.println(url);

                Thread.sleep(13000);

            }
        }
    }


    public static Map<String, List<String>> splitQuery(URL url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
        final String[] pairs = url.getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }


}
