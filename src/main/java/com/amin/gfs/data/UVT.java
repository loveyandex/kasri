package com.amin.gfs.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * created By gOD on 8/22/2019 2:55 AM
 * u v temp velosity
 */

public class UVT {
    public static void main(String[] args) throws IOException {
        Date datew = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(datew);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date date1 = calendar.getTime();
        String date = formatter.format(date1);


        String[] clocks = {"00", "06", "12", "18"};

        for (int i = 0; i < clocks.length; i++) {
            String clock = clocks[i];

            String time = clock;

            String uv = "https://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t" + time + "z.pgrb2.1p00.f000&lev_planetary_boundary_layer=on&var_UGRD=on&var_VGRD=on&leftlon=0&rightlon=360&toplat=90&bottomlat=-90&dir=%2Fgfs."
                    + date
                    + "%2F" + time;
            String temp = "https://nomads.ncep.noaa.gov/cgi-bin/filter_gfs_1p00.pl?file=gfs.t" + time + "z.pgrb2.1p00.f000&lev_surface=on&var_TMP=on&leftlon=0&rightlon=360&toplat=90&bottomlat=-90&dir=%2Fgfs."
                    + date
                    + "%2F" + time;


            System.out.println(uv);
            URL website = new URL(uv);
            String dt=date+"_"+time;



            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("nws-data/uv_" + dt + ".f000");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            System.out.println("writted uv" + time);


            System.out.println(temp);
            URL website2 = new URL(temp);

            ReadableByteChannel rbce = Channels.newChannel(website2.openStream());
            FileOutputStream fos2 = new FileOutputStream("nws-data/temp_" + dt + ".f000");
            fos2.getChannel().transferFrom(rbce, 0, Long.MAX_VALUE);

            System.out.println("written temp" + time);

        }

    }
}
