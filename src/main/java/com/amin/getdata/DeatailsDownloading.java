package com.amin.getdata;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

@Getter
@Setter
public class DeatailsDownloading {
    private ArrayList<Year> years;
    String countryName;


    public DeatailsDownloading(String countryName, ArrayList<Year> years, int toYear) {
        this.countryName = countryName;
        this.years = years;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class Year {
        int year;
        int fromMonth;
        final int toMonth = 12;
    }
}
