package com.amin.gfs.data;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

/**
 * created By aMIN on 7/15/2019 2:35 AM
 */

public class Download {

    OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.header("status"));
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        //F:\apps\startup\grib2json\src\bin\grib2jso.cmd -n -d  -o ./sd.json god.f000

        Element body = Jsoup.connect("https://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?dir=%2Fgfs.20190727%2F00").get().body();

        Elements elementsByAttributeValue = body.getElementsByAttributeValue("type", "checkbox");

        ArrayList<String> levels = new ArrayList<>();
        ArrayList<String> vars = new ArrayList<>();

        for (Element element : elementsByAttributeValue) {
            String name = element.attr("name");
            if (name.contains("lev_")) {
                System.out.println(element.attr("name"));
                levels.add(name);
            } else if (name.contains("var_")) {
                System.err.println(element.attr("name"));
                vars.add(name);

            }
        }

//        System.exit(0);


        String path0_18z = "https://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t18z.pgrb2.1p00.f000";
        String path0_12z = "https://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t12z.pgrb2.1p00.f000";

        String year = "2018";
        String month = "07";
        String day = "27";
        String hour = "18";
        String dir = "&dir=%2Fgfs." + year + month + day + "%2F" + hour;

        String levv = "&all_lev=on";
        levv = "";
        for (int i = 0; i < ((int) (15)); i++) {
            levv += "&" + levels.get(i) + "=on";
        }
        String varr = "&var_TMP=on";
        for (int i = 0; i < ((int) (Math.random() * 4)); i++) {
            varr += "&" + vars.get(i) + "=on";
        }

        System.out.println("levv " + levv);
        System.out.println("varss " + varr);

        String others = "&leftlon=0&rightlon=360&toplat=90&bottomlat=-90";


        String spec = path0_18z + levv + varr + others + others + dir;
        System.out.println(spec);
        URL website = new URL(spec);

        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream("d.f000");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
