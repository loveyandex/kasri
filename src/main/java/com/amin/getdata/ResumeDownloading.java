package com.amin.getdata;

import com.amin.config.C;
import com.amin.config.MathTerminology;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class ResumeDownloading {

    public static void main(String[] args) {
        ArrayList<DeatailsDownloading> deatailsDownloadings = new ResumeDownloading().checkAndFindDowaloads();
        System.err.println(new Gson().toJson(deatailsDownloadings));

    }


    public ArrayList<DeatailsDownloading> checkAndFindDowaloads() {
        System.out.println(C.DATA_PATH);
        File rootDirectory = new File(C.DATA_PATH);
        if (rootDirectory.isDirectory()) {
            File[] countries = rootDirectory.listFiles();
            ArrayList<DeatailsDownloading> deatailsDownloadings = neverDowns(countries);
            System.err.println(new Gson().toJson(deatailsDownloadings));
            for (int i = 0; i < countries.length; i++) {
                File country = countries[i];
                if (country.isDirectory()) {
                    String name = country.getName();
                    File[] yearsOfCountry = country.listFiles();
                    ArrayList<DeatailsDownloading.Year> _yearsOfCountry = analysYears(yearsOfCountry);
                    deatailsDownloadings.add(0, new DeatailsDownloading(name, _yearsOfCountry, C.NowYear));

                }
            }
            return deatailsDownloadings;
        }
        throw new RuntimeException("why code has reached here!!!");
    }

    private ArrayList<DeatailsDownloading.Year> analysYears(File[] yearsOfCountry) {
        ArrayList<DeatailsDownloading.Year> years = new ArrayList<>();

        IntStream.range(C.FIRST_YEAR, C.NowYear + 1).forEach(year -> {
            File fileOFYear = getFileOFYear(year, yearsOfCountry);
            if (fileOFYear == null) {
                years.add(new DeatailsDownloading.Year().setFromMonth(1).setYear(year));
                ///todo add this year from 1 to 12 month
            } else {
                int fiestMonthYear = findFiestMonthYear(fileOFYear);
                years.add(new DeatailsDownloading.Year().setFromMonth(fiestMonthYear).setYear(year));
            }
        });
        return years;
    }

    private ArrayList<DeatailsDownloading.Year> addAllYears() {
        ArrayList<DeatailsDownloading.Year> years = new ArrayList<>();

        IntStream.range(C.FIRST_YEAR, C.NowYear + 1).forEach(year ->
                years.add(new DeatailsDownloading.Year().setFromMonth(1).setYear(year)));
        return years;
    }

    private int findFiestMonthYear(File fileOFYear) {

        File[] months = fileOFYear.listFiles();
        int maxMonth = maxMonth(months);
        if (maxMonth < 12)
            return maxMonth;
        else {
            File maxMonthfile = new File(months[0].getParent(), "month_" + maxMonth);
            File beforemaxMonthfile = new File(months[0].getParent(), "month_" + (maxMonth - 1));
            try {
                int abs = ( maxMonthfile.listFiles().length - beforemaxMonthfile.listFiles().length);
                if (abs > 5 ) {
                    return 11
                            ;
                } else  if (abs(abs) > 5 && abs<0) {
                    return 12;
                }
                else {
                    return 13;
                }

            } catch (java.lang.NullPointerException e) {
                return 13;
            }

        }
    }


    private File getFileOFYear(int year, File[] yearsOfCountry) {
        for (File yearOfCountry : yearsOfCountry) {
            String year_ = yearOfCountry.getName().replaceAll("year_", "");
            int year_int = Integer.parseInt(year_);
            if (year_int == year)
                return yearOfCountry;
        }
        return null;
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
                final String nameOfCOuntry = file.getName().replaceAll(".conf", "");
                boolean booo = false;
                for (int i = 0; i < country.length; i++) {
                    boolean contains = country[i].getName().contains(nameOfCOuntry);
                    if (contains) {
                        booo = true;
                        break;
                    }
                }
                if (!booo)
                    deatailsDownloadings.add(new DeatailsDownloading(nameOfCOuntry, addAllYears(), C.NowYear));
            }

        return deatailsDownloadings;
    }


}
