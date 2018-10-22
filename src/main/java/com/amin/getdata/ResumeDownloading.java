package com.amin.getdata;

import com.amin.config.C;
import com.amin.config.MathTerminology;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class ResumeDownloading {

    public static void main(String[] args) {
        new ResumeDownloading().check();
    }


    public void check() {
        System.out.println(C.DATA_PATH);
        File rootDirectory = new File(C.DATA_PATH);
        if (rootDirectory.isDirectory()) {
            File[] files = rootDirectory.listFiles();
            ArrayList<DeatailsDownloading> deatailsDownloadings = neverDowns(files);
            System.err.println(new Gson().toJson(deatailsDownloadings));
            for (int i = 0; i < files.length; i++) {
                File country = files[i];
                if (country.isDirectory()) {
                    String name = country.getName();
                    if (isConsistOf(country)) {
                        boolean completedDownloading = isCompletedDownloading(country);
                        if (!completedDownloading) {
                            int getlastYear = getlastYear(country);
                            int getlastMonth = getlastMonth(country);
                            DeatailsDownloading deatailsDownloading = new DeatailsDownloading(name, getlastMonth, getlastYear, C.NowYear);
                            System.out.println(new Gson().toJson(deatailsDownloading));
                        }
                        continue;

                    }

                }

            }

        }


    }

    private int getlastMonth(File country) {
        File[] years = country.listFiles();
        int maxYear = minYear(years);
        File mazyeafile = new File(years[0].getParent(), "year_" + maxYear);
        File[] months = mazyeafile.listFiles();
        int maxMonth = maxMonth(months);
        return maxMonth;
    }

    private int getlastYear(File country) {
        File[] years = country.listFiles();
        return minYear(years);
    }

    private boolean isCompletedDownloading(File country) {
        System.out.println(country.getPath());
        File[] years = country.listFiles();
        int minYear = minYear(years);
        File minyearfile = new File(years[0].getParent(), "year_" + minYear);
        File[] months = minyearfile.listFiles();
        int maxMonth = maxMonth(months);
        if (maxMonth < 12)
            return false;
        else {
            File maxMonthfile = new File(months[0].getParent(), "month_" + maxMonth);
            File beforemaxMonthfile = new File(months[0].getParent(), "month_" + (maxMonth - 1));
            if (beforemaxMonthfile.listFiles().length == maxMonthfile.listFiles().length) {
                return true;
            } else if (abs(beforemaxMonthfile.listFiles().length - maxMonthfile.listFiles().length) < 5) {
                return true;
            } else {
//                throw new RuntimeException("how to possible lengths defferrent");
                return false;
            }
        }
    }

    private int minYear(File[] years) {
        double[] allyears = new double[years.length];

        for (int i = 0; i < years.length; i++) {
            File year = years[i];
            String year_ = year.getName().replaceAll("year_", "");
            allyears[i] = Double.parseDouble(year_);
        }
        int maxyear = (int) MathTerminology.min(allyears);

        return maxyear;
    }

    private int maxMonth(File[] months) {
        double[] allmonth = new double[months.length];
        for (int i = 0; i < months.length; i++) {
            File month = months[i];
            String month_ = month.getName().replaceAll("month_", "");
            allmonth[i] = Double.parseDouble(month_);
        }
        int maxmonth = (int) MathTerminology.max(allmonth);
        return maxmonth;
    }

    private boolean isConsistOf(File country) {
        String name = country.getName();
        File rootfile = new File("config/states");
        final File[] listFiles = rootfile.listFiles();
        for (File file : listFiles)
            if (file.isFile()) {
                final String s = file.getName().replaceAll(".conf", "");
                boolean contains = name.contains(s);
                if (contains)
                    return true;
                else
                    continue;

            }
        return false;
    }

    private ArrayList<DeatailsDownloading> neverDowns(File[] country) {
        ArrayList<DeatailsDownloading> deatailsDownloadings = new ArrayList<>();

        File rootfile = new File("config/states");
        final File[] listFiles = rootfile.listFiles();

        for (File file : listFiles)
            if (file.isFile()) {
                final String s = file.getName().replaceAll(".conf", "");
                boolean booo = false;
                for (int i = 0; i < country.length; i++) {
                    boolean contains = country[i].getName().contains(s);
                    if (contains) {
                        booo = true;
                        break;
                    }
                }
                if (!booo)
                    deatailsDownloadings.add(new DeatailsDownloading(s, 1, C.FIRST_YEAR, C.NowYear));
            }

        return deatailsDownloadings;
    }


}
