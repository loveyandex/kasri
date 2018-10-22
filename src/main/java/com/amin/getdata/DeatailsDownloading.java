package com.amin.getdata;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeatailsDownloading {
    String countryName;
    int fromMonth;
    final int toMonth=12;
    int fromYear;
    int toYear;

    public DeatailsDownloading(String countryName, int fromMonth, int fromYear, int toYear) {
        this.countryName = countryName;
        this.fromMonth = fromMonth;
        this.fromYear = fromYear;
        this.toYear = toYear;
    }
}
