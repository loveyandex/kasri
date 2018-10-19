package com.amin.database;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import java.io.File;
import java.sql.SQLException;

/**
 * is created by aMIN on 6/12/2018 at 02:59
 */
public class RUN {

    public static void main(String[] args) {

        dropalltable("iran__islamic_rep");
    }


    public static void loadinOneTable(String country) {

        try {
            File folder = new File("G:/lastdir/" + country);


            final File[] yearsfolder = folder.listFiles();
            for (File yearfolder : yearsfolder) {

                final File[] monthesfolder = yearfolder.listFiles();

                for (File monthfolder : monthesfolder) {

                    final File[] stationsfolder = monthfolder.listFiles();

                    for (File stationFolder : stationsfolder) {

                        final File[] datas = stationFolder.listFiles();
                        ;

                        for (File dataFile : datas) {

                            if (dataFile.isFile()) {
                                if (dataFile.getName().contains(".csv.csv"))
                                    continue;
                                final String namefile = dataFile.getName().replaceAll(".csv", "");
                                System.out.println(namefile);
                                final String stationFolderName = stationFolder.getName();
                                final String tablename = country + "_" + stationFolderName + "_" + namefile;

                                final String year_ = yearfolder.getName().replaceAll("year_", "");
                                final String day_ = namefile.substring(4, 6);
                                String month_ = monthfolder.getName().replaceAll("month_", "");
//                                month_ = String.format("%02d", Integer.parseInt(month_));

                                final String insertAllTableToOne = Queries.insertAllTableToOne;

                                final String zone = namefile.substring(0, 3);
                                final String query = String.format(insertAllTableToOne,
                                        country, stationFolderName, Integer.parseInt(year_),
                                        Integer.parseInt(month_), Integer.parseInt(day_)
                                        , zone, country + "_" + stationFolderName + "_" + namefile);
                                try {
                                    System.out.println(query);

                                    Driver.getDriver().getConnection().createStatement().execute(query);

                                } catch (MySQLSyntaxErrorException e) {
                                    e.printStackTrace();

                                }

                            }

                        }
                    }

                }


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void deletealltable(String country) {

        try {
            File folder = new File("G:/lastdir/" + country);


            final File[] yearsfolder = folder.listFiles();
            for (File yearfolder : yearsfolder) {

                final File[] monthesfolder = yearfolder.listFiles();

                for (File monthfolder : monthesfolder) {

                    final File[] stationsfolder = monthfolder.listFiles();

                    for (File stationFolder : stationsfolder) {

                        final File[] datas = stationFolder.listFiles();
                        ;

                        for (File dataFile : datas) {

                            if (dataFile.isFile()) {
                                if (dataFile.getName().contains(".csv.csv"))
                                    continue;
                                final String namefile = dataFile.getName().replaceAll(".csv", "");
                                System.out.println(namefile);
                                final String stationFolderName = stationFolder.getName();
                                final String tablename = country + "_" + stationFolderName + "_" + namefile;

                                final String year_ = yearfolder.getName().replaceAll("year_", "");
                                final String day_ = namefile.substring(4, 6);
                                String month_ = monthfolder.getName().replaceAll("month_", "");
//                                month_ = String.format("%02d", Integer.parseInt(month_));

                                final String deleteAllTable = Queries.deleteAllTable;

                                final String zone = namefile.substring(0, 3);
                                final String query = String.format(deleteAllTable, country + "_" + stationFolderName + "_" + namefile);
                                try {
                                    System.out.println(query);

                                    Driver.getDriver().getConnection().createStatement().execute(query);

                                } catch (MySQLSyntaxErrorException e) {
                                    e.printStackTrace();

                                }

                            }

                        }
                    }

                }


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void dropalltable(String country) {

        try {
            File folder = new File("G:/lastdir/" + country);


            final File[] yearsfolder = folder.listFiles();
            for (File yearfolder : yearsfolder) {

                final File[] monthesfolder = yearfolder.listFiles();

                for (File monthfolder : monthesfolder) {

                    final File[] stationsfolder = monthfolder.listFiles();

                    for (File stationFolder : stationsfolder) {

                        final File[] datas = stationFolder.listFiles();
                        ;

                        for (File dataFile : datas) {

                            if (dataFile.isFile()) {
                                if (dataFile.getName().contains(".csv.csv"))
                                    continue;
                                final String namefile = dataFile.getName().replaceAll(".csv", "");
                                System.out.println(namefile);
                                final String stationFolderName = stationFolder.getName();
                                final String tablename = country + "_" + stationFolderName + "_" + namefile;

                                final String year_ = yearfolder.getName().replaceAll("year_", "");
                                final String day_ = namefile.substring(4, 6);
                                String month_ = monthfolder.getName().replaceAll("month_", "");
//                                month_ = String.format("%02d", Integer.parseInt(month_));

                                final String deleteAllTable = Queries.dropAlltable;

                                final String zone = namefile.substring(0, 3);
                                final String query = String.format(deleteAllTable, country + "_" + stationFolderName + "_" + namefile);
                                try {
//                                    System.out.println(query);

                                    Driver.getDriver().getConnection().createStatement().execute(query);

                                } catch (MySQLSyntaxErrorException e) {
                                    e.printStackTrace();

                                }

                            }

                        }
                    }

                }


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
